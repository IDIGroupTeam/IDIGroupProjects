-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 04, 2017 at 09:01 AM
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
-- Table structure for table `process_insurance`
--

CREATE TABLE `idigroup`.`process_insurance` (
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY_PAY` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `FROM_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TO_DATE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `idigroup`.`process_insurance`
--

INSERT INTO `idigroup`.`process_insurance` (`SOCIAL_INSU_NO`, `SALA_SOCI_INSU`, `COMPANY_PAY`, `FROM_DATE`, `TO_DATE`, `COMMENT`) VALUES
('222221112222', '5.000.000', 'IDI', '2017-08-01', '2017-08-31', ''),
('222221112222', '5.500.000', 'IDI', '2017-09-01', '2017-09-30', ''),
('222221112222', '6.000.000', 'IDI', '2017-10-01', '', 'Nhan vien moi tu 1/8/17');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `idigroup`.`process_insurance`
--
ALTER TABLE `idigroup`.`process_insurance`
  ADD PRIMARY KEY (`SOCIAL_INSU_NO`,`FROM_DATE`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
