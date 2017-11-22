-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2017 at 09:30 AM
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
-- Table structure for table `timekeeping`
--

CREATE TABLE `idigroup`.`timekeeping` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TIME_IN` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `TIME_OUT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COME_LATE_M` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COME_LATE_A` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEAVE_SOON_M` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEAVE_SOON_A` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `timekeeping`
--

INSERT INTO `idigroup`.`timekeeping` (`EMPLOYEE_ID`, `DATE`, `TIME_IN`, `TIME_OUT`, `COME_LATE_M`, `COME_LATE_A`, `LEAVE_SOON_M`, `LEAVE_SOON_A`, `COMMENT`) VALUES
(2, '31/10/2017', '08:49', '12:04', '0:49', NULL, NULL, NULL, NULL),
(2, '31/10/2017', '14:53', '17:31', NULL, '01:53', NULL, NULL, NULL),
(3, '31/10/2017', '08:00', '12:04', NULL, NULL, NULL, NULL, NULL),
(3, '31/10/2017', '17:37', NULL, NULL, '04:37', NULL, NULL, NULL),
(4, '31/10/2017', '07:40', NULL, NULL, NULL, NULL, NULL, NULL),
(21, '31/10/2017', '07:59', '13:26', NULL, NULL, NULL, '03:26', NULL),
(21, '31/10/2017', '17:27', NULL, NULL, '04:27', NULL, NULL, NULL),
(22, '31/10/2017', '08:01', '17:27', '0:01', NULL, NULL, NULL, NULL),
(23, '31/10/2017', '07:58', '11:58', NULL, NULL, '0:02', NULL, NULL),
(23, '31/10/2017', '14:31', NULL, NULL, '01:31', NULL, NULL, NULL),
(28, '31/10/2017', '07:48', NULL, NULL, NULL, NULL, NULL, NULL),
(31, '31/10/2017', '07:49', '12:01', NULL, NULL, NULL, NULL, NULL),
(31, '31/10/2017', '12:38', '17:30', NULL, NULL, NULL, NULL, NULL),
(33, '31/10/2017', '07:48', '12:03', NULL, NULL, NULL, NULL, NULL),
(33, '31/10/2017', '17:32', NULL, NULL, '04:32', NULL, NULL, NULL),
(35, '31/10/2017', '08:04', '13:08', '0:04', NULL, NULL, '03:08', NULL),
(35, '31/10/2017', '17:29', NULL, NULL, '04:29', NULL, NULL, NULL),
(38, '31/10/2017', '08:00', '12:02', NULL, NULL, NULL, NULL, NULL),
(38, '31/10/2017', '13:59', '17:37', NULL, '0:59', NULL, NULL, NULL),
(39, '31/10/2017', '07:59', '11:58', NULL, NULL, '0:02', NULL, NULL),
(39, '31/10/2017', '13:40', '15:29', NULL, '0:40', NULL, '01:29', NULL),
(41, '31/10/2017', '07:40', '11:57', NULL, NULL, '0:03', NULL, NULL),
(41, '31/10/2017', '13:26', '19:12', NULL, '0:26', NULL, NULL, NULL),
(42, '31/10/2017', '07:54', '12:59', NULL, NULL, NULL, NULL, NULL),
(42, '31/10/2017', '13:27', '17:55', NULL, '0:27', NULL, NULL, NULL),
(43, '31/10/2017', '09:04', '12:12', '01:04', NULL, NULL, NULL, NULL),
(43, '31/10/2017', '13:35', '18:00', NULL, '0:35', NULL, NULL, NULL),
(44, '31/10/2017', '07:57', '12:01', NULL, NULL, NULL, NULL, NULL),
(44, '31/10/2017', '12:29', '16:32', NULL, NULL, NULL, '0:32', NULL),
(45, '31/10/2017', '07:50', '13:59', NULL, NULL, NULL, '03:01', NULL),
(45, '31/10/2017', '17:33', NULL, NULL, '04:33', NULL, NULL, NULL),
(48, '31/10/2017', '07:48', '18:23', NULL, NULL, NULL, NULL, NULL),
(49, '31/10/2017', '07:58', '12:06', NULL, NULL, NULL, NULL, NULL),
(49, '31/10/2017', '13:44', '17:29', NULL, '0:44', NULL, NULL, NULL),
(50, '31/10/2017', '08:44', '12:01', '0:44', NULL, NULL, NULL, NULL),
(50, '31/10/2017', '13:44', '17:44', NULL, '0:44', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `idigroup`.`timekeeping`
--
ALTER TABLE `idigroup`.`timekeeping`
  ADD PRIMARY KEY (`DATE`,`EMPLOYEE_ID`,`TIME_IN`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
