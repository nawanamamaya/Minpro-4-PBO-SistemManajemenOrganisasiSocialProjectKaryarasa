package controller;

import model.ProgramSosial;
import model.ProgramDonasi;
import model.ProgramEdukasi;
import model.ProgramSosialDAO;
import model.Relawan;
import view.KaryarasaView;
import java.util.Scanner;


public class KaryarasaController {
    private ProgramSosialDAO dao = new ProgramSosialDAO();
    private Scanner scanner;
    private KaryarasaView view;

    public KaryarasaController(Scanner scanner, KaryarasaView view) {
        this.scanner = scanner;
        this.view = view;
    }

    public void tambahProgram() {
        view.tampilkanPesan("\n--- 1. INFORMASI PENGAJU / RELAWAN ---");
        System.out.print("Nama Pengaju       : ");
        String nama = scanner.nextLine();
        System.out.print("Kontak (No HP/WA)  : ");
        String kontak = scanner.nextLine();
        System.out.print("Asal Lembaga       : ");
        String asalLembaga = scanner.nextLine();
        System.out.print("Jabatan di Lembaga : ");
        String jabatan = scanner.nextLine();
        Relawan pengajuBaru = new Relawan(nama, kontak, asalLembaga, jabatan);

        view.tampilkanPesan("\n--- 2. DETAIL PROGRAM SOSIAL ---");
        String idProgram = mintaIdUnik();
        System.out.print("Nama Program       : ");
        String namaProgram = scanner.nextLine();
        System.out.print("Tujuan Program     : ");
        String tujuan = scanner.nextLine();
        int jumlahPeserta = mintaAngkaInt("Jumlah Peserta     : ");
        System.out.print("Skala Program (cth: Lokal/Nasional): ");
        String skala = scanner.nextLine();
        double anggaran = mintaAngkaDouble("Anggaran (Rp)      : ");

        System.out.println("\nPilih Jenis Program:");
        System.out.println("1. Program Donasi");
        System.out.println("2. Program Edukasi");
        int tipe = mintaAngkaIntRentang("Pilihan (1/2)      : ", 1, 2);

        ProgramSosial programBaru;
        if (tipe == 2) {
            int jumlahSesi = mintaAngkaInt("Jumlah Sesi        : ");
            System.out.print("Materi Utama       : ");
            String materi = scanner.nextLine();
            programBaru = new ProgramEdukasi(idProgram, namaProgram, tujuan, jumlahPeserta,
                    skala, anggaran, pengajuBaru, jumlahSesi, materi);
        } else {
            System.out.print("Jenis Barang       : ");
            String jenisBarang = scanner.nextLine();
            double target = mintaAngkaDouble("Target Donasi (Rp) : ");
            programBaru = new ProgramDonasi(idProgram, namaProgram, tujuan, jumlahPeserta,
                    skala, anggaran, pengajuBaru, jenisBarang, target);
        }

        dao.tambahProgram(programBaru);
    }

    public void tampilkanProgram() {
        view.tampilkanDaftarProgram(dao.getAllProgram());
    }

    public void hapusProgram() {
        System.out.print("Masukkan ID Program yang akan dihapus: ");
        String idTarget = scanner.nextLine();
        dao.hapusProgram(idTarget);
    }

    public void updateAnggaran() {
        System.out.print("Masukkan ID Program yang akan diupdate anggarannya: ");
        String idTarget = scanner.nextLine();

        ProgramSosial program = dao.cariProgramById(idTarget);

        if (program != null) {
            double anggaranBaru = mintaAngkaDouble("Anggaran Baru (Rp): ");
            program.setAnggaran(anggaranBaru);   // validasi setter tetap jalan
            dao.updateAnggaran(program.getIdProgram(), program.getAnggaran());
        } else {
            view.tampilkanPesan("Program dengan ID tersebut tidak ditemukan.");
        }
    }

    // ==================== Helper Validasi Input ====================

    private String mintaIdUnik() {
        String id;
        while (true) {
            System.out.print("ID Program         : ");
            id = scanner.nextLine();
            if (id == null || id.trim().isEmpty()) {
                System.out.println("ID Program tidak boleh kosong!");
                continue;
            }
            if (dao.cariProgramById(id.trim()) != null) {
                System.out.println("ID Program sudah digunakan, gunakan ID lain!");
                continue;
            }
            break;
        }
        return id.trim();
    }

    private int mintaAngkaInt(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka bulat!");
            }
        }
    }

    private int mintaAngkaIntRentang(String label, int min, int max) {
        while (true) {
            int nilai = mintaAngkaInt(label);
            if (nilai >= min && nilai <= max) {
                return nilai;
            }
            System.out.println("Pilihan harus antara " + min + " dan " + max + "!");
        }
    }

    private double mintaAngkaDouble(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka!");
            }
        }
    }
}