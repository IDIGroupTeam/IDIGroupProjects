-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2017 at 09:29 AM
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
-- Table structure for table `leave_info`
--

CREATE TABLE `idigroup`.`leave_info` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `DATE` date NOT NULL,
  `LEAVE_TYPE` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `TIME_VALUE` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `leave_info`
--

INSERT INTO `idigroup`.`leave_info` (`EMPLOYEE_ID`, `DATE`, `LEAVE_TYPE`, `TIME_VALUE`, `COMMENT`) VALUES
(1, '2017-11-22', 'LTNT', '3', 'Làm thêm 3h ngày thường'),
(2, '2017-10-21', 'NKL', '', ''),
(3, '2017-10-01', 'CT', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leave_info`
--
ALTER TABLE `idigroup`.`leave_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`DATE`,`LEAVE_TYPE`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
