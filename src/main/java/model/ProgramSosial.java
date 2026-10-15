package model;

public abstract class ProgramSosial {
    protected final String idProgram;
    protected String namaProgram;
    protected String tujuan;
    protected int jumlahPeserta;
    protected String skala;
    protected double anggaran;
    protected Relawan pengaju;

    public ProgramSosial(String idProgram, String namaProgram, String tujuan,
                          int jumlahPeserta, String skala, double anggaran, Relawan pengaju) {
        this.idProgram = idProgram;
        setNamaProgram(namaProgram);
        setTujuan(tujuan);
        setJumlahPeserta(jumlahPeserta);
        setSkala(skala);
        setAnggaran(anggaran);
        setPengaju(pengaju);
    }

    // =============================================
    //            Getter (Encapsulation) 
    // =============================================
    public String getIdProgram() { return idProgram; }
    public String getNamaProgram() { return namaProgram; }
    public String getTujuan() { return tujuan; }
    public int getJumlahPeserta() { return jumlahPeserta; }
    public String getSkala() { return skala; }
    public double getAnggaran() { return anggaran; }
    public Relawan getPengaju() { return pengaju; }

    // =============================================
    //       Setter dengan validasi input 
    // =============================================
    public void setNamaProgram(String namaProgram) {
        if (namaProgram != null && !namaProgram.trim().isEmpty()) {
            this.namaProgram = namaProgram;
        } else {
            System.out.println("Nama program tidak boleh kosong!");
            this.namaProgram = "Program Tanpa Nama";
        }
    }

    public void setTujuan(String tujuan) {
        this.tujuan = (tujuan != null && !tujuan.trim().isEmpty()) ? tujuan : "-";
    }

    public void setJumlahPeserta(int jumlahPeserta) {
        if (jumlahPeserta > 0) {
            this.jumlahPeserta = jumlahPeserta;
        } else {
            System.out.println("Jumlah peserta harus lebih dari 0! Diatur ke 1.");
            this.jumlahPeserta = 1;
        }
    }

    public void setSkala(String skala) {
        this.skala = (skala != null && !skala.trim().isEmpty()) ? skala : "Lokal";
    }

    public void setAnggaran(double anggaran) {
        if (anggaran >= 0) {
            this.anggaran = anggaran;
        } else {
            System.out.println("Anggaran tidak boleh negatif!");
            this.anggaran = 0;
        }
    }

    public void setPengaju(Relawan pengaju) {
        this.pengaju = pengaju;
    }

    protected void cetakDataDasar() {
        System.out.println("ID Program   : " + idProgram);
        System.out.println("Nama Program : " + namaProgram);
        System.out.println("Tujuan       : " + tujuan);
        System.out.println("Peserta      : " + jumlahPeserta + " orang | Skala: " + skala);
        System.out.println("Anggaran     : Rp " + String.format("%,.2f", anggaran));
        System.out.println("[Pengaju]      " + pengaju.getNama() + " (" + pengaju.getJabatan()
                + " - " + pengaju.getAsalLembaga() + ")");
        System.out.println("               Kontak: " + pengaju.getKontak());
    }

    
    public abstract void tampilkanInfo();

    public void tampilkanInfo(boolean ringkas) {
        if (ringkas) {
            System.out.println("[" + idProgram + "] " + namaProgram + " - " + skala);
        } else {
            tampilkanInfo();
        }
    }
}