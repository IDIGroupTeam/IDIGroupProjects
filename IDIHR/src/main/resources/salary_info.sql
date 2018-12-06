-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2018 at 11:15 AM
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
-- Table structure for table `salary_info`
--

CREATE TABLE `salary_info` (
  `EMPLOYEE_ID` int(11) NOT NULL,
  `SALARY` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `BANK_NO` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NAME` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_BRANCH` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salary_info`
--

INSERT INTO `salary_info` (`EMPLOYEE_ID`, `SALARY`, `BANK_NO`, `COMMENT`, `BANK_NAME`, `BANK_BRANCH`) VALUES
(5, '10000000', '0111222011332', 'C', 'VCB', 'Hoàn kiếm'),
(8, '8000000', '0011002200123', '', 'VCB', 'Hoàn kiếm'),
(62, '5000000', '001110033355', '', 'VCB', 'Hoàn kiếm'),
(68, '4000000', '', 'Sẽ điều chỉnh tăng lương trong kỳ tới', 'VCB', ''),
(69, '4000000', '0011002200333', 'Cộng tác viên. Không đóng BHXH', 'VCB', 'Hội sở'),
(70, '3000000', NULL, 'Không tham gia BHXH', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `salary_info`
--
ALTER TABLE `salary_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
