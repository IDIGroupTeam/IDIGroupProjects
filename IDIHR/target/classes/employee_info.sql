-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 06, 2017 at 10:57 AM
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
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `FULL_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `GENDER` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `JOB_TITLE` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `WORK_STATUS` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MARITAL_STATUS` varchar(32) CHARACTER SET ucs2 COLLATE ucs2_unicode_ci DEFAULT NULL,
  `LOGIN_ACCOUNT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERSONAL_ID` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ISSUE_DATE` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPARTMENT` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `JOIN_DATE` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `OFFICIAL_JOIN_DATE` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TERMINATION_DATE` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REASON_FOR_LEAVE` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CURRENT_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERMANENT_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOTE` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `NATION` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE` mediumblob,
  `EMER_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMER_PHONE_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_BRANCH` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALARY` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALA_SOCI_INSU` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SOCIAL_INSU_NO` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `HEALTH_INSU_NO` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERCENT_SOCI_INSU` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `employee_info`
--

INSERT INTO `employee_info` (`EMPLOYEE_ID`, `FULL_NAME`, `GENDER`, `JOB_TITLE`, `WORK_STATUS`, `DOB`, `MARITAL_STATUS`, `LOGIN_ACCOUNT`, `PERSONAL_ID`, `ISSUE_DATE`, `DEPARTMENT`, `PHONE_NO`, `JOIN_DATE`, `OFFICIAL_JOIN_DATE`, `EMAIL`, `TERMINATION_DATE`, `REASON_FOR_LEAVE`, `CURRENT_ADDRESS`, `PERMANENT_ADDRESS`, `NOTE`, `NATION`, `IMAGE`, `EMER_NAME`, `EMER_PHONE_NO`, `BANK_NO`, `BANK_NAME`, `BANK_BRANCH`, `SALARY`, `SALA_SOCI_INSU`, `SOCIAL_INSU_NO`, `HEALTH_INSU_NO`, `PERCENT_SOCI_INSU`) VALUES
(1, 'Nguyen Van Truong', 'Nam', 'CTV', 'Thá»­ viá»c', NULL, 'Äá»c thÃ¢n', 'truongnv', '01234567890', NULL, 'KT', '0912345678', '2017-01-08', '2017-01-08', 'truongnv.idigroup@gmail.com', NULL, '', 'Hoang Mai-HN', '', '', 'Viet Nam', NULL, '', '', '', '', '', '2.000.000', '0', '22221111', '33334444', ''),
(2, 'Pham Xuan Tuyen', 'male', 'TP_CNTT', 'Cá»ng tÃ¡c viÃªn', '1990-11-11', 'married', 'tuyenpx', '11111111111', '2011-11-11', 'CNTT', '09999999999', '2017-01-08', '2017-01-08', 'tuyenpx.idigroup@gmail.com', '', '', 'Hoang Mai', 'Hung Yen', 'Edit: Test validate', 'Viet Nam', NULL, '', '', '', '', '', '5.000.000', '0', '22221111', '33334444', '0'),
(3, 'Tran Dong Hai', 'female', 'CTV', 'Thá»­ viá»c', '1990-11-11', '', 'haitd', '11111111111', '2011-11-11', 'CNTT', '09999999999', '2017-01-08', '2017-01-08', 'haitd.idigroup@gmail.com', '', '', 'Cau giay', 'Thai binh', '', 'Viet Nam', NULL, '', '', '', '', '', '2.000.000', '0', '22221112', '33334445', '0.0'),
(4, 'Nguyen Van A', 'male', 'NV', 'Thá»­ viá»c', '1992-09-06', 'sigle', 'an', '11111111111', '2016-05-01', 'CNTT', '09999999999', '2017-09-06', '2017-09-06', 'anv.idigroup@gmail.com', '', '', 'Cau giay', 'Hoang mai', '', 'Viet Nam', NULL, '', '', '', '', '', '5.000.000', '5.000.000', '22221112', '33334445', '9%');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee_info`
--
ALTER TABLE `employee_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee_info`
--
ALTER TABLE `employee_info`
  MODIFY `EMPLOYEE_ID` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
