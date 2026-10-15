package config;
import java.sql.Connection;

public class TestKoneksi {

    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database...");

        Connection conn = Koneksi.getConnection();

        if (conn != null) {
            System.out.println("BERHASIL! Database siap digunakan.");
        } else {
            System.out.println("GAGAL! Periksa kembali URL, USER, PASS, dan pastikan service MySQL sudah menyala.");
        }
    }
}