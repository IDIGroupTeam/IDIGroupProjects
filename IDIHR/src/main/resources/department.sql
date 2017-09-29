-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 05, 2017 at 11:21 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `idigroup`
--

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `idigroup`.`department` (
  `DEPARTMENT_ID` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `DEPARTMENT_NAME` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `department`
--

INSERT INTO `idigroup`.`department` (`DEPARTMENT_ID`, `DEPARTMENT_NAME`, `DESCRIPTION`) VALUES
('CNTT', 'Công nghệ thông tin', NULL),
('KHDT', 'Kế hoạch và đầu tư ', NULL),
('KT', 'kế toán', NULL),
('kTH', 'Kỹ thuật ', NULL),
('XNK', 'Xuất nhập khẩu', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `department`
--
ALTER TABLE `idigroup`.`department`
  ADD PRIMARY KEY (`DEPARTMENT_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
