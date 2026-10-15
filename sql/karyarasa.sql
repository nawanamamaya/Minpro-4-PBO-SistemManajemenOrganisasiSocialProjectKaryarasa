-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 15, 2026 at 01:35 PM
-- Server version: 8.4.3
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `karyarasa`
--

-- --------------------------------------------------------

--
-- Table structure for table `program_donasi`
--

CREATE TABLE `program_donasi` (
  `id_program` varchar(20) NOT NULL,
  `jenis_barang` varchar(100) NOT NULL,
  `target_donasi` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `program_donasi`
--

INSERT INTO `program_donasi` (`id_program`, `jenis_barang`, `target_donasi`) VALUES
('PD001', 'Sembako', 10000000),
('PD002', 'Baju, makanan, kebutuhan pribadi', 100000000);

-- --------------------------------------------------------

--
-- Table structure for table `program_edukasi`
--

CREATE TABLE `program_edukasi` (
  `id_program` varchar(20) NOT NULL,
  `jumlah_sesi` int NOT NULL,
  `materi_utama` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `program_edukasi`
--

INSERT INTO `program_edukasi` (`id_program`, `jumlah_sesi`, `materi_utama`) VALUES
('PE001', 6, 'Dasar-dasar Internet & Keamanan Data');

-- --------------------------------------------------------

--
-- Table structure for table `program_sosial`
--

CREATE TABLE `program_sosial` (
  `id_program` varchar(20) NOT NULL,
  `nama_program` varchar(100) NOT NULL,
  `tujuan` varchar(255) NOT NULL,
  `jumlah_peserta` int NOT NULL,
  `skala` varchar(30) NOT NULL,
  `anggaran` int NOT NULL,
  `jenis` varchar(10) NOT NULL,
  `pengaju_nama` varchar(100) NOT NULL,
  `pengaju_kontak` varchar(30) NOT NULL,
  `pengaju_lembaga` varchar(100) NOT NULL,
  `pengaju_jabatan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `program_sosial`
--

INSERT INTO `program_sosial` (`id_program`, `nama_program`, `tujuan`, `jumlah_peserta`, `skala`, `anggaran`, `jenis`, `pengaju_nama`, `pengaju_kontak`, `pengaju_lembaga`, `pengaju_jabatan`) VALUES
('PD001', 'Donasi Pangan Ramadhan', 'Membantu warga terdampak kekurangan pangan', 50, 'Lokal', 5000000, 'DONASI', 'Siti Amara', '0812-3456-7890', 'Komunitas Peduli Kasih', 'Ketua Relawan'),
('PD002', 'Peduli Karhutla', 'Membantu korban karhutla', 19, 'Lokal', 30000000, 'DONASI', 'AYA', '083', 'Yayasan Cerdas Bangsa', 'Ketua panitia'),
('PE001', 'Pelatihan Literasi Digital', 'Meningkatkan kemampuan digital anak muda', 30, 'Nasional', 7500000, 'EDUKASI', 'Budi Santoso', '0813-1122-3344', 'Yayasan Cerdas Bangsa', 'Koordinator Program');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `program_donasi`
--
ALTER TABLE `program_donasi`
  ADD PRIMARY KEY (`id_program`);

--
-- Indexes for table `program_edukasi`
--
ALTER TABLE `program_edukasi`
  ADD PRIMARY KEY (`id_program`);

--
-- Indexes for table `program_sosial`
--
ALTER TABLE `program_sosial`
  ADD PRIMARY KEY (`id_program`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `program_donasi`
--
ALTER TABLE `program_donasi`
  ADD CONSTRAINT `program_donasi_ibfk_1` FOREIGN KEY (`id_program`) REFERENCES `program_sosial` (`id_program`) ON DELETE CASCADE;

--
-- Constraints for table `program_edukasi`
--
ALTER TABLE `program_edukasi`
  ADD CONSTRAINT `program_edukasi_ibfk_1` FOREIGN KEY (`id_program`) REFERENCES `program_sosial` (`id_program`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
