-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2017 at 01:09 PM
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
-- Table structure for table `employee_info`
--

CREATE TABLE `employee_info` (
  `EMPLOYEE_ID` varchar(5) CHARACTER SET latin1 NOT NULL,
  `FULL_NAME` varchar(32) CHARACTER SET latin1 NOT NULL,
  `GENDER` varchar(6) CHARACTER SET latin1 NOT NULL,
  `JOB_TITLE` varchar(45) CHARACTER SET latin1 NOT NULL,
  `WORK_STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `DOB` date NOT NULL,
  `MARITAL_STATUS` varchar(7) CHARACTER SET latin1 NOT NULL,
  `LOGIN_ACCOUNT` varchar(45) CHARACTER SET latin1 NOT NULL,
  `PERSONAL_ID` varchar(12) CHARACTER SET latin1 NOT NULL,
  `ISSUE_DATE` date NOT NULL,
  `DEPARTMENT` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PHONE_NO` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  `JOIN_DATE` date NOT NULL,
  `OFFICIAL_JOIN_DATE` date DEFAULT NULL,
  `EMAIL` varchar(45) CHARACTER SET latin1 NOT NULL,
  `TERMINATION_DATE` date DEFAULT NULL,
  `REASON_FOR_LEAVE` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `CURRENT_ADDRESS` varchar(255) CHARACTER SET latin1 NOT NULL,
  `PERMANENT_ADDRESS` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `NOTE` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `NATION` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IMAGE` mediumblob,
  `EMER_NAME` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `EMER_PHONE_NO` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_NO` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_NAME` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_BRANCH` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SALARY` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `HEALTH_INSU_NO` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PERCENT_SOCI_INSU` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `employee_info`
--

INSERT INTO `employee_info` (`EMPLOYEE_ID`, `FULL_NAME`, `GENDER`, `JOB_TITLE`, `WORK_STATUS`, `DOB`, `MARITAL_STATUS`, `LOGIN_ACCOUNT`, `PERSONAL_ID`, `ISSUE_DATE`, `DEPARTMENT`, `PHONE_NO`, `JOIN_DATE`, `OFFICIAL_JOIN_DATE`, `EMAIL`, `TERMINATION_DATE`, `REASON_FOR_LEAVE`, `CURRENT_ADDRESS`, `PERMANENT_ADDRESS`, `NOTE`, `NATION`, `IMAGE`, `EMER_NAME`, `EMER_PHONE_NO`, `BANK_NO`, `BANK_NAME`, `BANK_BRANCH`, `SALARY`, `SALA_SOCI_INSU`, `SOCIAL_INSU_NO`, `HEALTH_INSU_NO`, `PERCENT_SOCI_INSU`) VALUES
('00001', 'Nguyen Van Truong', 'Nam', 'Cong Tac Vien CNTT', '', '1982-10-30', 'Married', 'truongnv', '01234567890', '2017-08-01', 'cntt', '0912345678', '2017-08-01', '2017-08-01', 'truongnv.idigroup@gmail.com', NULL, NULL, 'Hoàng Mai-HN', NULL, NULL, 'VN', NULL, '', '', '', '', '', '', '', '', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee_info`
--
ALTER TABLE `employee_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
