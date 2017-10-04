--CREATE TABLE `idigroup`.`INSURANCE` ( `EMPLOYEE_ID` BIGINT(5) NOT NULL, `SOCIAL_INSU_NO` VARCHAR(12) NOT NULL , `SALA_SOCI_INSU` VARCHAR(12) NOT NULL , `PERCENT_SOCI_INSU` VARCHAR(6) NOT NULL , `FROM_DATE` VARCHAR(12) NOT NULL , `TO_DATE` VARCHAR(12) NULL DEFAULT NULL , `PLACE` VARCHAR(64) NOT NULL , `COMPANY_PAY`	varchar(64) NOT NULL, `STATUS` varchar(32) NOT NULL, `HEALTH_INSU_NO` varchar(12) NOT NULL, `HEALTH_INSU_PLACE` varchar(64)	NOT NULL, `COMMENT` VARCHAR(256) NULL DEFAULT NULL , PRIMARY KEY (`SOCIAL_INSU_NO`,`SALA_SOCI_INSU`,`HEALTH_INSU_NO`)) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;

-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 29, 2017 at 11:25 AM
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
-- Table structure for table `idigroup`.`insurance`
--

CREATE TABLE `idigroup`.`insurance` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `PERCENT_SOCI_INSU_C` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `PERCENT_SOCI_INSU_E` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `PLACE` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `STATUS` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `HEALTH_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `HEALTH_INSU_PLACE` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `SALARY_ZONE` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `PAY_TYPE` varchar(12) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `insurance`
--

INSERT INTO `idigroup`.`INSURANCE` (`EMPLOYEE_ID`, `SOCIAL_INSU_NO`, `SALA_SOCI_INSU`, `PERCENT_SOCI_INSU_C`, `PERCENT_SOCI_INSU_E`, `PLACE`, `COMMENT`, `STATUS`, `HEALTH_INSU_NO`, `HEALTH_INSU_PLACE`, `SALARY_ZONE`, `PAY_TYPE`) VALUES
(1, '222221112222', '5.000.000', '16', '9', 'BH Quan Tay Ho', 'Ghi chu o day', 'Dang nop', '123321123321', 'BV Thanh Nhan, HN', 'Vung 1: 300000', 'Theo quy'),
(2, '222221112223', '5.000.000', '16', '9', 'BH Quan Tay Ho', '', 'Dang nghi thai san', '123321123322', 'BV Thanh Nhan, HN', 'Vung 1: 300000', 'Theo thang');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `insurance`
--
ALTER TABLE `idigroup`.`INSURANCE`
  ADD PRIMARY KEY (`SOCIAL_INSU_NO`,`HEALTH_INSU_NO`) USING BTREE,
  ADD UNIQUE KEY `HEALTH_INSU_NO` (`HEALTH_INSU_NO`),
  ADD UNIQUE KEY `SOCIAL_INSU_NO` (`SOCIAL_INSU_NO`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- --------------------------------------------------------

--
-- Table structure for table `idigroup`.`insurance`
--

CREATE TABLE `idigroup`.`process_insurance` (
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY_PAY` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `FROM_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TO_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

