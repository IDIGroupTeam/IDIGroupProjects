-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2018 at 02:45 AM
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
-- Table structure for table `salary_detail`
--

CREATE TABLE `salary_detail` (
  `EMPLOYEE_ID` int(11) NOT NULL,
  `ACTUAL_SALARY` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OVER_TIME_N` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OVER_TIME_W` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OVER_TIME_H` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BOUNUS` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SUBSIDIZE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADVANCE_PAYED` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TAX_PERSONAL` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MONTH` int(4) NOT NULL,
  `YEAR` int(4) NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OVER_TIME_SALARY` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYED_INSURANCE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WORK_COMPLETE` int(4) NOT NULL DEFAULT '100'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salary_detail`
--

INSERT INTO `salary_detail` (`EMPLOYEE_ID`, `ACTUAL_SALARY`, `OVER_TIME_N`, `OVER_TIME_W`, `OVER_TIME_H`, `BOUNUS`, `SUBSIDIZE`, `ADVANCE_PAYED`, `TAX_PERSONAL`, `MONTH`, `YEAR`, `COMMENT`, `OVER_TIME_SALARY`, `PAYED_INSURANCE`, `WORK_COMPLETE`) VALUES
(5, '9945002.0', '5', '2', '', '500,000', '600,000', '1,000,000', '150,000', 10, 2018, '', '625002.0', NULL, 100),
(5, '1.132E7', '', '', '', '500,000', '600,000', '1,000,000', '150,000', 11, 2018, '', '0.0', '630000.0', 100),
(8, '8038886.0', '1', '1', '1', '300000', '600000', '1000000', '150000', 9, 2018, '', '288886.0', NULL, 100),
(8, '8532607.0', '1', '1', '1', '500,000', '200,000', '300,000', '150,000', 10, 2018, '', '282607.0', NULL, 100),
(8, '8450000.0', '', '', '', '500,000', '600,000', '500,000', '150,000', 11, 2018, '', '0.0', NULL, 100),
(62, '4200000.0', '', '', '', '200,000', '', '', '', 11, 2018, '', '0.0', NULL, 80),
(68, '4173912.0', '0', '4', '', '', '', '', '', 10, 2018, '', '173912.0', NULL, 100),
(68, '4000000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100),
(69, '3760000.0', '3', NULL, NULL, '200000', '500000', '', '', 8, 2018, '', NULL, NULL, 100),
(69, '3773342.0', '4', '4', '4', '200000', '500000', '', '150000', 9, 2018, '', '433342.0', '210000.0', 100),
(70, '2973368.0', '1', '0', '1', '100000', '0', '200000', '0', 10, 2018, '', '73368.0', NULL, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `salary_detail`
--
ALTER TABLE `salary_detail`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`MONTH`,`YEAR`) USING BTREE;

ALTER TABLE `salary_detail`  ADD `WORKED_DAY` VARCHAR(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL  AFTER `WORK_COMPLETE`;  

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
