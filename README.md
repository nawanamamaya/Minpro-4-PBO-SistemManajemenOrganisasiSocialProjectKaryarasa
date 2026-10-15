# Sistem Manajemen Organisasi Social Project Karyarasa

## Deskripsi Singkat Program

Sistem manajemen program sosial Karyarasa adalah aplikasi konsol berbasis Java yang dikembangkan dengan konsep Object-Oriented Programming (OOP), pola MVC (Model-View-Controller), dan terhubung ke database MySQL menggunakan JDBC (Java Database Connectivity).

Program ini membantu mencatat dan mengelola data program sosial (donasi maupun edukasi) beserta data relawan/pengaju yang mengajukan program tersebut. Melalui sistem ini, pengguna dapat:

1. menambahkan program sosial baru beserta data pengajunya,
2. melihat daftar seluruh program yang tersimpan di database,
3. menghapus program berdasarkan ID,
4. memperbarui anggaran suatu program.

Setiap program memiliki dua kategori spesifik, yaitu Program Donasi (berfokus pada pengumpulan barang/dana) dan Program Edukasi (berfokus pada pelatihan/sesi pembelajaran), yang keduanya merupakan turunan dari satu program sosial umum (ProgramSosial).

Seluruh data disimpan secara permanen di database karyarasa, sehingga data tidak hilang ketika program dihentikan.

Cara Menjalankan
1. Import file karyarasa.sql ke MySQL (misalnya lewat phpMyAdmin) sehingga database karyarasa beserta tabelnya terbentuk.
2. Pastikan MySQL aktif di localhost:3306. Sesuaikan URL, USER, dan PASS pada config/Koneksi.java bila berbeda.
3. (Opsional) Jalankan config/TestKoneksi.java untuk memastikan program sudah terhubung ke database.
4. Jalankan MainApp.java.

## Penjelasan Struktur Package
<img width="265" height="391" alt="image" src="https://github.com/user-attachments/assets/ee8e59f3-ed19-40a7-a273-f17d690682ce" />

1. Package com.mycompany.karyarasa: berisi MainApp sebagai entry point. Class ini memeriksa koneksi database, membuat objek Scanner, KaryarasaView, dan KaryarasaController, lalu menjalankan perulangan menu utama.
2. Package config: berisi Koneksi yang bertugas membuat dan menyediakan koneksi ke database MySQL, serta TestKoneksi yang digunakan untuk menguji apakah koneksi berhasil.
3. Package model: berisi blueprint entitas data dan aturan datanya, yaitu ProgramSosial (abstract), ProgramDonasi, ProgramEdukasi, Relawan, serta interface Disalurkan dan Dilaksanakan. Package ini juga berisi ProgramSosialDAO (Data Access Object) yang menangani seluruh operasi database (CRUD) agar query SQL terpisah dari controller.
4. Package view: berisi KaryarasaView, yang bertugas khusus menampilkan antarmuka konsol (menu, pesan, dan daftar program).
5. Package controller: berisi KaryarasaController, yang menghubungkan View dan Model. Controller mengatur input (Scanner), memvalidasi input (angka, ID unik, rentang pilihan), lalu memanggil method dari ProgramSosialDAO (untuk data) atau KaryarasaView (untuk tampilan).

## Penjelasan Alur Program
**Inisialisasi**


Program dimulai dari MainApp. Pertama, MainApp memanggil Koneksi.getConnection(). Jika koneksi gagal (hasilnya null), program menampilkan pesan "Gagal terhubung ke Database. Program dihentikan." lalu berhenti. Jika berhasil, MainApp membuat objek Scanner, KaryarasaView, dan KaryarasaController. Saat KaryarasaController dibuat, objek ProgramSosialDAO ikut dibuat untuk mengakses database. Seluruh data program diambil langsung dari database, sehingga tidak ada lagi data awal yang di-hardcode.

### Menu Utama
<img width="259" height="124" alt="image" src="https://github.com/user-attachments/assets/d3f2aa44-34f0-4e07-85d1-2f614fa4a10c" />

Program menampilkan menu yang berulang menggunakan while, dengan 5 pilihan: (1) menambah program sosial sekaligus data relawan/pengaju, (2) menampilkan daftar program, (3) menghapus program, (4) memperbarui anggaran program, dan (5) keluar dari program. Input menu divalidasi dengan try-catch (NumberFormatException) sehingga hanya angka yang diterima dan program tidak berhenti mendadak ketika pengguna salah input. Pilihan di luar 1-5 akan menampilkan pesan "Pilihan tidak valid!".

### Tambah Program
<img width="259" height="100" alt="image" src="https://github.com/user-attachments/assets/3892308d-cdbd-4e72-9ecc-ab64a315c8fc" />

Pengguna pertama-tama mengisi data pengaju: nama, kontak, asal lembaga, dan jabatan. Data ini disimpan dalam objek Relawan.

<img width="325" height="255" alt="image" src="https://github.com/user-attachments/assets/7b77682d-9067-47fa-9863-442f559ed09d" />

Selanjutnya pengguna mengisi detail program: ID, nama, tujuan, jumlah peserta, skala, dan anggaran. ID divalidasi oleh method mintaIdUnik() agar tidak kosong dan belum dipakai (dicek ke database lewat dao.cariProgramById()). Input angka divalidasi oleh mintaAngkaInt() dan mintaAngkaDouble() yang akan terus meminta ulang sampai input berupa angka.

Pengguna lalu memilih jenis program (pilihan dibatasi 1-2 oleh mintaAngkaIntRentang()):

1. Edukasi: diminta jumlah sesi dan materi utama, lalu dibuat objek ProgramEdukasi.
2. Donasi: diminta jenis barang dan target donasi, lalu dibuat objek ProgramDonasi.

Objek yang dibuat dikirim ke dao.tambahProgram() untuk disimpan ke database (data umum ke tabel program_sosial, data spesifik ke tabel program_donasi atau program_edukasi).

### Tampilkan Program
<img width="306" height="351" alt="image" src="https://github.com/user-attachments/assets/2dd92b92-35fa-4642-9542-af391c70c9a9" />

Controller memanggil dao.getAllProgram() untuk mengambil seluruh program dari database (hasil JOIN tiga tabel), lalu mengirimkannya ke KaryarasaView.tampilkanDaftarProgram(). Tampilan tiap program berbeda sesuai jenisnya (Donasi menampilkan jenis barang dan target donasi, Edukasi menampilkan jumlah sesi dan materi utama). Jika belum ada data, ditampilkan pesan bahwa belum ada program sosial.

### Hapus Program
<img width="208" height="217" alt="image" src="https://github.com/user-attachments/assets/4870b827-7f62-4961-8226-cbeba1871315" />

Pengguna memasukkan ID program yang ingin dihapus. Controller memanggil dao.hapusProgram() yang menjalankan perintah DELETE berdasarkan ID. Jika ada baris yang terhapus, ditampilkan pesan sukses; jika tidak, ditampilkan pesan bahwa ID tidak ditemukan.

### Update Anggaran
<img width="256" height="122" alt="image" src="https://github.com/user-attachments/assets/dbd27540-afe5-41ae-a36c-a0c8b0e03eef" />

Pengguna memasukkan ID program. Controller mencari program lewat dao.cariProgramById(). Jika ditemukan, pengguna memasukkan anggaran baru yang divalidasi lewat setAnggaran() (nilai negatif diubah menjadi 0), lalu nilai tersebut disimpan ke database melalui dao.updateAnggaran(). Jika ID tidak ditemukan, ditampilkan pesan "Program dengan ID tersebut tidak ditemukan."

### Keluar Program
<img width="308" height="151" alt="image" src="https://github.com/user-attachments/assets/ba3307b4-994c-4213-8c59-1860a1ce07e7" />

Menghentikan perulangan utama, menampilkan pesan terima kasih, menutup Scanner, dan mengakhiri program.

## Penjelasan Penerapan Encapsulation dan Inheritance
### Enkapsulasi
**Penerapan Access Modifier**

<img width="622" height="292" alt="image" src="https://github.com/user-attachments/assets/e95f0e26-e02b-4335-aeaf-9e26f56e641d" />

<img width="641" height="241" alt="image" src="https://github.com/user-attachments/assets/e684a1c4-3251-4cef-9eea-ebac3bbcf662" />

<img width="633" height="193" alt="image" src="https://github.com/user-attachments/assets/2b96ff4e-fdf7-47d2-86c1-0a14b87e1fa2" />

<img width="628" height="204" alt="image" src="https://github.com/user-attachments/assets/3c5d315a-505f-4d20-ba8e-b0c4b1eeb9ed" />

Atribut pada ProgramSosial menggunakan protected agar dapat diakses oleh subclass, sedangkan atribut pada ProgramDonasi, ProgramEdukasi, dan Relawan menggunakan private. Atribut idProgram dibuat final sehingga tidak dapat diubah setelah objek dibuat.

**Getter dan Setter Bersyarat**

<img width="464" height="114" alt="image" src="https://github.com/user-attachments/assets/1a7b0cdf-b582-415c-9ae5-d36afd20f635" />

<img width="426" height="112" alt="image" src="https://github.com/user-attachments/assets/a60e31ff-0580-4022-a6fc-deee0816de68" />

Pada setAnggaran() dan setTargetDonasi() terdapat pengecekan nilai tidak boleh negatif. Jika negatif, sistem mencetak pesan error dan mengatur nilai menjadi 0.

<img width="475" height="113" alt="image" src="https://github.com/user-attachments/assets/52954b68-aa9d-43d3-9c9a-32aa9eed2c4e" />

<img width="532" height="116" alt="image" src="https://github.com/user-attachments/assets/dee82eb3-40a2-4971-87c9-a975236e7087" />

Pada setNamaProgram() dan setNama() terdapat pengecekan agar input tidak null atau kosong. Constructor juga memanggil setter (bukan assignment langsung), sehingga validasi tetap berlaku saat objek pertama kali dibuat.

**Inheritance (Pewarisan)**

<img width="615" height="264" alt="image" src="https://github.com/user-attachments/assets/7842b746-9d10-4327-a4c0-acc393485574" />

<img width="481" height="302" alt="image" src="https://github.com/user-attachments/assets/558a6b1b-a2c1-4fb2-835a-34834b041c8a" />

<img width="538" height="335" alt="image" src="https://github.com/user-attachments/assets/44bff472-543e-4985-a7bd-163334726d27" />

<img width="585" height="206" alt="image" src="https://github.com/user-attachments/assets/23705e07-78d2-4387-8f25-28147ae752e3" />

Superclass: ProgramSosial menyimpan atribut umum (ID, nama, tujuan, peserta, skala, anggaran, dan objek Relawan pengaju) serta method konkret cetakDataDasar() yang dipakai ulang oleh subclass.

<img width="640" height="199" alt="image" src="https://github.com/user-attachments/assets/43f3e6d4-c151-46d6-94fb-fbe0b4e28a73" />

Subclass 1: ProgramDonasi extends ProgramSosial, menambahkan atribut jenisBarang dan targetDonasi

<img width="635" height="156" alt="image" src="https://github.com/user-attachments/assets/5988d5d0-5eef-4986-a466-5302b0d479cf" />

Subclass 2: ProgramEdukasi extends ProgramSosial, menambahkan atribut jumlahSesi dan materiUtama.

Penggunaan super(): pada constructor masing-masing subclass, super(...) memanggil constructor superclass agar atribut utama terinisialisasi dengan benar.

Selain itu, ProgramSosial memiliki relasi has-a (composition) dengan Relawan melalui atribut pengaju.

## Penjelasan Penerapan Polymorphism dan Abstraction

**Abstraction (Abstraksi)**

<img width="666" height="120" alt="image" src="https://github.com/user-attachments/assets/05643500-52ed-4a13-9519-e6c63102d15b" />

<img width="521" height="118" alt="image" src="https://github.com/user-attachments/assets/586db8cc-e055-4946-abc6-d1f358951812" />

1. Abstract class: ProgramSosial dideklarasikan abstract, sehingga tidak dapat diinstansiasi langsung (new ProgramSosial(...) tidak diperbolehkan). Class ini menjadi kerangka umum bagi semua jenis program sosial.
2. Abstract method: public abstract void tampilkanInfo(); tidak memiliki isi dan wajib di-override oleh setiap subclass konkret. Ini memastikan setiap jenis program pasti punya cara menampilkan informasinya sendiri.
3. ProgramSosial juga memiliki method konkret (cetakDataDasar(), getter, setter) yang diwariskan ke subclass.

**Polymorphism (Polimorfisme)**

Program menerapkan dua jenis polimorfisme sekaligus.

1. Method Overriding
   
   <img width="578" height="128" alt="image" src="https://github.com/user-attachments/assets/73de485f-e92c-497e-8d5d-2a35a95f3585" />

   <img width="575" height="127" alt="image" src="https://github.com/user-attachments/assets/777d55d0-3c59-4f71-8eb0-91ecc4e293b1" />

   Method abstrak tampilkanInfo() di ProgramSosial di-override oleh ProgramDonasi dan ProgramEdukasi. Masing-masing subclass memanggil cetakDataDasar() (method konkret milik superclass) untuk mencetak data umum, lalu mencetak data spesifiknya sendiri.
   Method dari interface juga di-override: ProgramDonasi meng-override salurkan() dan batalkanPenyaluran(), sedangkan ProgramEdukasi meng-override laksanakan().

   <img width="598" height="207" alt="image" src="https://github.com/user-attachments/assets/a17a05a9-a6a3-4065-8ae3-33bf9253c319" />

   Dynamic method dispatch terjadi di class KaryarasaView. Meskipun ArrayList bertipe ProgramSosial, saat perulangan memanggil daftarProgram.get(i).tampilkanInfo(), Java secara dinamis menjalankan method milik ProgramDonasi atau ProgramEdukasi sesuai wujud asli objeknya.

2. Method Overloading
   
   <img width="521" height="143" alt="image" src="https://github.com/user-attachments/assets/f1db36db-fdb3-46d2-9c69-4431f46d300c" />

   Pada ProgramSosial terdapat dua method dengan nama sama namun parameter berbeda:
   1. tampilkanInfo(): menampilkan data secara lengkap (abstract, di-override subclass).
   2. tampilkanInfo(boolean ringkas): jika true, hanya menampilkan ID, nama program, dan skala; jika false, memanggil tampilkanInfo() versi lengkap.

## Penjelasan Penerapan JDBC

Program terhubung ke database MySQL karyarasa menggunakan JDBC dengan driver mysql-connector-j versi 8.3.0 yang didaftarkan sebagai dependency pada pom.xml.

1. Koneksi Database (config/Koneksi.java)

   <img width="636" height="355" alt="image" src="https://github.com/user-attachments/assets/34884dfa-5e7f-4891-aaa9-b6625ba78ef9" />

   Class Koneksi menyimpan URL (jdbc:mysql://localhost:3306/karyarasa), USER, dan PASS sebagai konstanta. Method statis getConnection() membuat koneksi lewat
   DriverManager.getConnection() hanya jika koneksi belum ada atau sudah tertutup (conn == null || conn.isClosed()), sehingga satu koneksi dipakai bersama oleh seluruh
   operasi. Jika gagal, SQLException ditangkap dan pesan error dicetak.

   TestKoneksi adalah class kecil dengan main sendiri yang dipakai untuk menguji apakah program berhasil terhubung ke database sebelum aplikasi utama dijalankan. Selain
   itu, MainApp juga memeriksa koneksi di awal dan menghentikan program bila koneksi gagal.

2. Struktur Database
   
   <img width="442" height="431" alt="image" src="https://github.com/user-attachments/assets/8f9e7fdf-0dca-4cc3-a3bf-3dd23d1aef13" />

   Struktur ini mengikuti hierarki pewarisan di Java: tabel induk menyimpan atribut umum ProgramSosial, sedangkan tabel anak menyimpan atribut tambahan milik ProgramDonasi
   dan ProgramEdukasi.

   Kolom id_program pada tabel anak adalah foreign key ke program_sosial dengan aturan ON DELETE CASCADE:

   FOREIGN KEY (id_program) REFERENCES program_sosial (id_program) ON DELETE CASCADE

   Dengan aturan ini, ketika sebuah program dihapus dari program_sosial, baris turunannya di program_donasi atau program_edukasi ikut terhapus otomatis, sehingga tidak ada
   data yatim (orphan) dan program tidak error saat menghapus. File SQL lengkap tersedia di repository (karyarasa.sql).

3. Operasi CRUD (model/ProgramSosialDAO.java)

   <img width="578" height="88" alt="image" src="https://github.com/user-attachments/assets/507df1fa-0e45-41aa-b101-49a6dddacb5c" />

   <img width="502" height="231" alt="image" src="https://github.com/user-attachments/assets/1d24200e-37b7-46a8-886c-3e688af16ead" />

   <img width="491" height="250" alt="image" src="https://github.com/user-attachments/assets/57f3222f-c8ed-44a4-9069-e2611b607d19" />

   <img width="537" height="253" alt="image" src="https://github.com/user-attachments/assets/c4843d1f-b607-42f6-9870-75205a35174d" />

   Seluruh akses database dipusatkan di ProgramSosialDAO (pola Data Access Object), sehingga KaryarasaController tidak berisi query SQL sama sekali.

  **Create (tambah data).** Method `tambahProgram(ProgramSosial p)` menjalankan perintah `INSERT`. Method ini pertama-tama menyimpan data umum program ke tabel `program_sosial`, lalu menyimpan data spesifiknya ke tabel `program_donasi` atau `program_edukasi`. Untuk menentukan tabel anak mana yang dipakai, method ini memeriksa jenis objeknya dengan `instanceof ProgramDonasi`.

**Read (baca data).** Terdapat dua method untuk membaca data. Method `getAllProgram()` menjalankan `SELECT ... LEFT JOIN` untuk mengambil seluruh program dari ketiga tabel sekaligus, dan hasilnya diurutkan berdasarkan `id_program`. Method `cariProgramById(String id)` menjalankan `SELECT ... WHERE id_program = ?` untuk mencari satu program berdasarkan ID. Method ini dipakai untuk memvalidasi ID unik saat menambah program dan untuk memeriksa keberadaan program sebelum anggarannya diperbarui.

**Update (ubah data).** Method `updateAnggaran(String id, double anggaranBaru)` menjalankan perintah `UPDATE` untuk memperbarui kolom `anggaran` pada program dengan ID tertentu.

**Delete (hapus data).** Method `hapusProgram(String id)` menjalankan perintah `DELETE` untuk menghapus program berdasarkan ID. Data turunan program tersebut di tabel `program_donasi` atau `program_edukasi` ikut terhapus otomatis karena foreign key menggunakan `ON DELETE CASCADE`.










   





















