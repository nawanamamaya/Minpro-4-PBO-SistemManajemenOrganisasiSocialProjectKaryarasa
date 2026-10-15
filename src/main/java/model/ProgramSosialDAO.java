package model;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramSosialDAO {


    private static final String SQL_SELECT_GABUNGAN =
            "SELECT ps.*, pd.jenis_barang, pd.target_donasi, pe.jumlah_sesi, pe.materi_utama "
            + "FROM program_sosial ps "
            + "LEFT JOIN program_donasi pd ON ps.id_program = pd.id_program "
            + "LEFT JOIN program_edukasi pe ON ps.id_program = pe.id_program ";

    // ================CREATE================
    public void tambahProgram(ProgramSosial p) {
        String sqlInduk = "INSERT INTO program_sosial (id_program, nama_program, tujuan, "
                + "jumlah_peserta, skala, anggaran, jenis, pengaju_nama, pengaju_kontak, "
                + "pengaju_lembaga, pengaju_jabatan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();


        try (PreparedStatement pstmt = conn.prepareStatement(sqlInduk)) {
            Relawan r = p.getPengaju();
            String jenis = (p instanceof ProgramDonasi) ? "DONASI" : "EDUKASI";

            pstmt.setString(1, p.getIdProgram());
            pstmt.setString(2, p.getNamaProgram());
            pstmt.setString(3, p.getTujuan());
            pstmt.setInt(4, p.getJumlahPeserta());
            pstmt.setString(5, p.getSkala());
            pstmt.setDouble(6, p.getAnggaran());
            pstmt.setString(7, jenis);
            pstmt.setString(8, r.getNama());
            pstmt.setString(9, r.getKontak());
            pstmt.setString(10, r.getAsalLembaga());
            pstmt.setString(11, r.getJabatan());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah program - " + e.getMessage());
            return;
        }

        if (p instanceof ProgramDonasi) {
            ProgramDonasi donasi = (ProgramDonasi) p;
            String sqlDonasi = "INSERT INTO program_donasi (id_program, jenis_barang, target_donasi) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sqlDonasi)) {
                pstmt.setString(1, donasi.getIdProgram());
                pstmt.setString(2, donasi.getJenisBarang());
                pstmt.setDouble(3, donasi.getTargetDonasi());
                pstmt.executeUpdate();
                System.out.println("Program sosial beserta data pengaju berhasil ditambahkan!");
            } catch (SQLException e) {
                System.out.println("Gagal menambah data donasi - " + e.getMessage());
            }
        } else {
            ProgramEdukasi edukasi = (ProgramEdukasi) p;
            String sqlEdukasi = "INSERT INTO program_edukasi (id_program, jumlah_sesi, materi_utama) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sqlEdukasi)) {
                pstmt.setString(1, edukasi.getIdProgram());
                pstmt.setInt(2, edukasi.getJumlahSesi());
                pstmt.setString(3, edukasi.getMateriUtama());
                pstmt.executeUpdate();
                System.out.println("Program sosial beserta data pengaju berhasil ditambahkan!");
            } catch (SQLException e) {
                System.out.println("Gagal menambah data edukasi - " + e.getMessage());
            }
        }
    }

    //=============READ (berdasarkan ID)=============
    public ProgramSosial cariProgramById(String idProgram) {
        String sql = SQL_SELECT_GABUNGAN + "WHERE ps.id_program = ?";
        ProgramSosial program = null;
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idProgram);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    program = petakanBaris(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari program - " + e.getMessage());
        }
        return program;
    }

    //=============READ (semua)=============
    public ArrayList<ProgramSosial> getAllProgram() {
        ArrayList<ProgramSosial> listProgram = new ArrayList<>();
        String sql = SQL_SELECT_GABUNGAN + "ORDER BY ps.id_program";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listProgram.add(petakanBaris(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data program - " + e.getMessage());
        }
        return listProgram;
    }

    //=============UPDATE=============
    public void updateAnggaran(String idProgram, double anggaranBaru) {
        String sql = "UPDATE program_sosial SET anggaran=? WHERE id_program=?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, anggaranBaru);
            pstmt.setString(2, idProgram);

            int barisBerubah = pstmt.executeUpdate();
            if (barisBerubah > 0) {
                System.out.println("Anggaran program berhasil diperbarui!");
            } else {
                System.out.println("Program dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengubah anggaran - " + e.getMessage());
        }
    }

    // =============DELETE============= 
    public void hapusProgram(String idProgram) {
        String sql = "DELETE FROM program_sosial WHERE id_program=?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idProgram);

            int barisBerubah = pstmt.executeUpdate();
            if (barisBerubah > 0) {
                System.out.println("Program sosial berhasil dihapus!");
            } else {
                System.out.println("Program dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus program - " + e.getMessage());
        }
    }


    private ProgramSosial petakanBaris(ResultSet rs) throws SQLException {
        Relawan pengaju = new Relawan(
                rs.getString("pengaju_nama"),
                rs.getString("pengaju_kontak"),
                rs.getString("pengaju_lembaga"),
                rs.getString("pengaju_jabatan"));

        if (rs.getString("jenis").equals("DONASI")) {
            return new ProgramDonasi(
                    rs.getString("id_program"), rs.getString("nama_program"),
                    rs.getString("tujuan"), rs.getInt("jumlah_peserta"),
                    rs.getString("skala"), rs.getDouble("anggaran"), pengaju,
                    rs.getString("jenis_barang"), rs.getDouble("target_donasi"));
        } else {
            return new ProgramEdukasi(
                    rs.getString("id_program"), rs.getString("nama_program"),
                    rs.getString("tujuan"), rs.getInt("jumlah_peserta"),
                    rs.getString("skala"), rs.getDouble("anggaran"), pengaju,
                    rs.getInt("jumlah_sesi"), rs.getString("materi_utama"));
        }
    }
}