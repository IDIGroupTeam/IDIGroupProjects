-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 25, 2017 at 11:10 AM
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
-- Table structure for table `work_history`
--

CREATE TABLE `idigroup`.`work_history` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `FROM_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TO_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TITLE` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `DEPARTMENT` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALARY` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `ACHIEVEMENT` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `APPRAISE` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `work_history`
--

INSERT INTO `idigroup`.`work_history` (`EMPLOYEE_ID`, `FROM_DATE`, `TO_DATE`, `TITLE`, `DEPARTMENT`, `COMPANY`, `SALARY`, `ACHIEVEMENT`, `APPRAISE`) VALUES
(1, '2017-08-01', '2017-10-01', 'CTV', 'CNTT', 'IDI', '2.000.000', 'Chua co', 'Nhan xet o day '),
(1, '2017-10-01', '2017-11-01', 'NV', 'KTh', 'IDI', '5.000.000', 'Khong', 'Chuyen tu phong CNTT sang');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `work_history`
--
ALTER TABLE `idigroup`.`work_history`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`FROM_DATE`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
