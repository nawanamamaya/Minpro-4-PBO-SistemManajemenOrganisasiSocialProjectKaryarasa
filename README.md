# Sistem Manajemen Organisasi Social Project Karyarasa

## Deskripsi Singkat Program

Sistem manajemen program social Karyarasa adalah sistem manajemen program sosial yang dikembangkan menggunakan Bahasa Java. 
Program ini dapat membantu mencatat dan mengelola data program sosial (donasi maupun edukasi) beserta data relawan/pengaju yang ingin mengajukan program tersebut.
Melalui sistem ini, pengguna dapat menambahkan program sosial baru, melihat daftar seluruh program yang terdaftar, menghapus program berdasarkan ID, serta memperbarui anggaran suatu program. Setiap program memiliki dua kategor spesifik, yaitu Program Donasi (berfokus pada pengumpulan barang/dana) dan Program Edukasi (berfokus pada pelatihan/sesi pembelajaran), yang di mana keduanya merupakan turunan dari satu program sosial umum.

## Penjelasan Struktur Package
<img width="278" height="223" alt="image" src="https://github.com/user-attachments/assets/51ea04fe-a9af-496d-8a6d-60f06c4cbf6a" />

1. Package com.mycompany.karyarasa: berisi MainApp sebagai entry point. Class ini membuat objek Scanner, KaryarasaView, dan KaryarasaController, lalu menjalankan perulangan menu utama.
2. Package model: berisi blueprint entitas data dan aturan datanya, yaitu ProgramSosial (abstract), ProgramDonasi, ProgramEdukasi, Relawan, serta interface Disalurkan dan Dilaksanakan.
3. Package view: berisi KaryarasaView, yang bertugas khusus menampilkan antarmuka konsol (menu, pesan, dan daftar program).
4. Package controller: berisi KaryarasaController, yang menghubungkan View dan Model. Controller mengatur input (Scanner), mengelola koleksi data (ArrayList<ProgramSosial>), memvalidasi ID unik, serta memanggil method dari Model atau View.

## Penjelasan Alur Program
**Inisialisasi**

<img width="502" height="85" alt="image" src="https://github.com/user-attachments/assets/713c09db-9406-47d8-922d-4a25073f6fe2" />

Program dimulai dari MainApp yang membuat objek Scanner, KaryarasaView, dan KaryarasaController. Saat KaryarasaController dibuat, method muatDataAwal() otomatis dipanggil untuk mengisi dua data awal, yaitu satu ProgramDonasi dan satu ProgramEdukasi, ke dalam ArrayList<ProgramSosial> sehingga data langsung tampil tanpa perlu diinput manual.

### Menu Utama
<img width="324" height="130" alt="image" src="https://github.com/user-attachments/assets/445bec07-a80b-4d62-8bdd-38efd355807a" />

Program menampilkan menu yang berulang menggunakan while, dengan 5 pilihan: (1) menambah program sosial sekaligus data relawan/pengaju, (2) menampilkan daftar program, (3) menghapus program, (4) memperbarui anggaran program, dan (5) keluar dari program. Input menu divalidasi dengan try-catch (NumberFormatException) sehingga hanya angka yang diterima dan program tidak berhenti mendadak ketika pengguna salah input.

### Tambah Program
<img width="259" height="100" alt="image" src="https://github.com/user-attachments/assets/3892308d-cdbd-4e72-9ecc-ab64a315c8fc" />

Pengguna pertama-tama mengisi data pengaju: nama, kontak, asal lembaga, dan jabatan. Data ini disimpan dalam objek Relawan.

<img width="368" height="271" alt="image" src="https://github.com/user-attachments/assets/0e9b25a8-6873-4467-abc5-82882b59ecf5" />

Selanjutnya pengguna mengisi detail program: ID (dicek agar unik dan tidak kosong), nama, tujuan, jumlah peserta, skala, dan anggaran. Pengguna lalu memilih jenis program. Jika memilih Edukasi, diminta jumlah sesi dan materi utama, lalu dibuat objek ProgramEdukasi. Jika memilih Donasi, diminta jenis barang dan target donasi, lalu dibuat objek ProgramDonasi. Objek yang dibuat disimpan ke ArrayList<ProgramSosial>.

### Tampilkan Program
<img width="260" height="324" alt="image" src="https://github.com/user-attachments/assets/2904b197-2492-4c33-b5c9-1401d1693454" />

Sistem menampilkan seluruh program yang tersimpan. Tampilan tiap program berbeda sesuai jenisnya (Donasi menampilkan jenis barang dan target donasi, Edukasi menampilkan jumlah sesi dan materi utama).

### Hapus Program
<img width="282" height="253" alt="image" src="https://github.com/user-attachments/assets/14300c42-0703-460c-a88f-e446b6751d22" />

Pengguna memasukkan ID program yang ingin dihapus. Sistem mencari program berdasarkan ID (tidak peka huruf besar/kecil), lalu menghapusnya dari ArrayList jika ditemukan.

### Update Anggaran
<img width="299" height="139" alt="image" src="https://github.com/user-attachments/assets/234aeba3-e108-460f-aaf1-bfcd87ffd14a" />

Pengguna memasukkan ID program, lalu nilai anggaran baru yang divalidasi dan diperbarui melalui setAnggaran().

### Keluar Program
<img width="426" height="204" alt="image" src="https://github.com/user-attachments/assets/b4efa021-8ee9-482c-9913-afba7aac3070" />

Menghentikan perulangan utama, menutup Scanner, dan mengakhiri program.

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

## Penjelasan Letak Penerapan Nilai Tambah

**Interface**

<img width="257" height="67" alt="image" src="https://github.com/user-attachments/assets/932aa73e-8c4d-4275-8350-63ee2e5f6091" />

<img width="230" height="84" alt="image" src="https://github.com/user-attachments/assets/d67d708f-ea3c-4559-9be1-30772f105f19" />

Nilai tambah yang diterapkan adalah interface pada package model:
1. Disalurkan: menggunakan method salurkan(), batalkanPenyaluran() dan diimplementasikan ProgramDonasi. Maksudnya adalah untuk Kemampuan menyalurkan/membatalkan penyaluran donasi
2. Dilaksanakan: Menggunakan method laksanakan() dan diimplementasikan ProgramEdukasi. Maksudnya adalah untuk Kemampuan melaksanakan program edukasi


<img width="462" height="45" alt="image" src="https://github.com/user-attachments/assets/bf3764d6-9e7c-4538-b3f7-e3e23f54496b" />

<img width="557" height="261" alt="image" src="https://github.com/user-attachments/assets/9fc88a7e-3ea2-4dee-b8a6-ded2fb175047" />

<img width="475" height="47" alt="image" src="https://github.com/user-attachments/assets/8b8aafa3-4191-485f-8f2d-e30521d84a55" />
   
<img width="543" height="73" alt="image" src="https://github.com/user-attachments/assets/115bb9e4-5591-404c-b20c-086122134533" />

Letak penerapan:
1. model/Disalurkan.java dan model/Dilaksanakan.java: deklarasi interface.
2. model/ProgramDonasi.java: class ProgramDonasi extends ProgramSosial implements Disalurkan.
3. model/ProgramEdukasi.java: class ProgramEdukasi extends ProgramSosial implements Dilaksanakan.

Dengan interface, perilaku yang hanya dimiliki jenis program tertentu dipisahkan dari class abstrak ProgramSosial. Donasi memiliki konsep "disalurkan" dan edukasi memiliki konsep "dilaksanakan"; keduanya dijamin memiliki method tersebut oleh kontrak interface. Selain itu, sebuah class dapat meng-extend satu superclass sekaligus meng-implement interface.




