package model;


public class ProgramDonasi extends ProgramSosial implements Disalurkan {
    private String jenisBarang;
    private double targetDonasi;

 
    private boolean sudahDisalurkan = false;

    public ProgramDonasi(String idProgram, String namaProgram, String tujuan, int jumlahPeserta,
                          String skala, double anggaran, Relawan pengaju,
                          String jenisBarang, double targetDonasi) {

        super(idProgram, namaProgram, tujuan, jumlahPeserta, skala, anggaran, pengaju);
        setJenisBarang(jenisBarang);
        setTargetDonasi(targetDonasi);
    }

    public String getJenisBarang() { return jenisBarang; }
    public double getTargetDonasi() { return targetDonasi; }

    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = (jenisBarang != null && !jenisBarang.trim().isEmpty())
                ? jenisBarang : "Umum";
    }

    public void setTargetDonasi(double targetDonasi) {
        if (targetDonasi >= 0) {
            this.targetDonasi = targetDonasi;
        } else {
            System.out.println("Target donasi tidak boleh negatif!");
            this.targetDonasi = 0;
        }
    }

 
    @Override
    public void tampilkanInfo() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("[KATEGORI: PROGRAM DONASI]");
        cetakDataDasar(); // method konkrit yang dipinjam dari ProgramSosial
        System.out.println("Jenis Barang : " + jenisBarang);
        System.out.println("Target Donasi: Rp " + String.format("%,.2f", targetDonasi));
        System.out.println("----------------------------------------------------------------");
    }


    @Override
    public void salurkan() {
        if (!sudahDisalurkan) {
            sudahDisalurkan = true;
            System.out.println("Donasi '" + namaProgram + "' berhasil disalurkan.");
        } else {
            System.out.println("Donasi '" + namaProgram + "' sudah disalurkan sebelumnya!");
        }
    }

    @Override
    public void batalkanPenyaluran() {
        if (sudahDisalurkan) {
            sudahDisalurkan = false;
            System.out.println("Penyaluran donasi '" + namaProgram + "' telah dibatalkan.");
        } else {
            System.out.println("Donasi '" + namaProgram + "' memang belum disalurkan.");
        }
    }
}