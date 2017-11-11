-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 11, 2017 at 04:22 PM
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
  `FULL_NAME` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `GENDER` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `JOB_TITLE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WORK_STATUS` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MARITAL_STATUS` varchar(32) CHARACTER SET ucs2 COLLATE ucs2_unicode_ci DEFAULT NULL,
  `LOGIN_ACCOUNT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERSONAL_ID` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ISSUE_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPARTMENT` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `JOIN_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `OFFICIAL_JOIN_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `TERMINATION_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REASON_FOR_LEAVE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CURRENT_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERMANENT_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOTE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NATION` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE` mediumblob,
  `EMER_NAME` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMER_PHONE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NAME` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_BRANCH` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE_PATH` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `HEALTH_INSU_NO` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERCENT_SOCI_INSU` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `employee_info`
--

INSERT INTO `employee_info` (`EMPLOYEE_ID`, `FULL_NAME`, `GENDER`, `JOB_TITLE`, `WORK_STATUS`, `DOB`, `MARITAL_STATUS`, `LOGIN_ACCOUNT`, `PERSONAL_ID`, `ISSUE_DATE`, `DEPARTMENT`, `PHONE_NO`, `JOIN_DATE`, `OFFICIAL_JOIN_DATE`, `EMAIL`, `TERMINATION_DATE`, `REASON_FOR_LEAVE`, `CURRENT_ADDRESS`, `PERMANENT_ADDRESS`, `NOTE`, `NATION`, `IMAGE`, `EMER_NAME`, `EMER_PHONE_NO`, `BANK_NO`, `BANK_NAME`, `BANK_BRANCH`, `IMAGE_PATH`, `SALA_SOCI_INSU`, `SOCIAL_INSU_NO`, `HEALTH_INSU_NO`, `PERCENT_SOCI_INSU`) VALUES
(1, 'Nguyễn Văn Trượng', 'male', 'CTV', 'Cong tac vien', '1982-10-30', 'married', 'truongnv', '01234567890', '2016-12-31', 'CNTT', '0912345678', NULL, NULL, 'truongnv.idigroup@gmail.com', NULL, NULL, 'Hoang Mai-HN', 'Hoang Mai', 'Dien nhung thong thin nhan xet/lien quan ban muon note lai tai day', 'Viet Nam', NULL, 'Mai Thi Vo', '0911111111', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/001.jpg', NULL, NULL, NULL, NULL),
(2, 'Pham Xuan Tuyen', 'male', 'TP', 'Thu viec', '1990-11-11', 'married', 'tuyenpx', '11111111111', '2011-11-11', 'CNTT', '09999999999', NULL, NULL, 'tuyenpx.idigroup@gmail.com', NULL, NULL, 'Hoang Mai', 'Hung Yen', 'Edit: Test validate', 'Viet Nam', 0x646f776e6c6f6164202831292e6a7067, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/2.jpg', NULL, NULL, NULL, NULL),
(3, 'Tran Dong Hai', 'female', 'CTV', 'Cong tac vien', '1990-11-11', 'sigle', 'haitd', '11111111111', '2011-11-11', 'CNTT', '09999999321', NULL, NULL, 'haitd.idigroup@gmail.com', NULL, NULL, 'Cau giay', 'Thai binh', 'test upload image', 'Viet Nam', NULL, '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 'Nguyen Van Anh', 'female', 'NV', 'Thu viec', '1992-08-06', 'sigle', 'anhnv', '11111111111', '2016-05-01', 'KT', '09999999234', NULL, NULL, 'anv.idigroup@gmail.com', NULL, NULL, 'Cau giay', 'Hoang mai', '', 'Viet Nam', 0x50656e6775696e732e6a7067, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/4.jpg', NULL, NULL, NULL, NULL),
(5, 'Pham Thi My Le', 'female', 'NV', 'Chinh thuc', '1988-12-30', 'sigle', 'leptm', '', '', 'KT', '09777777777', NULL, NULL, 'leptm.idigroup@gmail.com', NULL, NULL, '', '', '', '', NULL, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Koala.jpg', NULL, NULL, NULL, NULL),
(6, 'Nguyen Van A', 'male', 'NV', 'Thu viec', '', 'widowed', 'anv', '', '', 'HC', '', NULL, NULL, 'a.nv.idigroup@gmail.com', NULL, NULL, '', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'Pham Xuan Tuyen', 'male', 'CTV', 'Thu viec', '', 'sigle', 'tuyenpx1', '', '', 'CNTT', '', '', '', 'tuyenpx1.idigroup@gmail.com', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', ''),
(8, 'Nguyen Van B', 'male', 'CTV', 'Thu viec', '', 'sigle', 'bnv', '', '', 'CNTT', '', '', '', 'bnv.idigroup@gmail.com', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee_info`
--
ALTER TABLE `employee_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`),
  ADD UNIQUE KEY `LOGIN_ACCOUNT` (`LOGIN_ACCOUNT`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee_info`
--
ALTER TABLE `employee_info`
  MODIFY `EMPLOYEE_ID` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
