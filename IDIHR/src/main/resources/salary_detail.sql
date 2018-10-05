-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2018 at 11:14 AM
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
  `PAYED_INSURANCE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salary_detail`
--

INSERT INTO `salary_detail` (`EMPLOYEE_ID`, `ACTUAL_SALARY`, `OVER_TIME_N`, `OVER_TIME_W`, `OVER_TIME_H`, `BOUNUS`, `SUBSIDIZE`, `ADVANCE_PAYED`, `TAX_PERSONAL`, `MONTH`, `YEAR`, `COMMENT`, `OVER_TIME_SALARY`, `PAYED_INSURANCE`) VALUES
(5, '9945002.0', '5', '2', '', '500,000', '600,000', '1,000,000', '150,000', 10, 2018, '', '625002.0', NULL),
(8, '8038886.0', '1', '1', '1', '300000', '600000', '1000000', '150000', 9, 2018, '', '288886.0', NULL),
(8, '8532607.0', '1', '1', '1', '500,000', '200,000', '300,000', '150,000', 10, 2018, '', '282607.0', NULL),
(68, '4173912.0', '0', '4', '', '', '', '', '', 10, 2018, '', '173912.0', NULL),
(69, '3760000.0', '3', NULL, NULL, '200000', '500000', '', '', 8, 2018, '', NULL, NULL),
(69, '3773342.0', '4', '4', '4', '200000', '500000', '', '150000', 9, 2018, '', '433342.0', '210000.0'),
(70, '2973368.0', '1', '0', '1', '100000', '0', '200000', '0', 10, 2018, '', '73368.0', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `salary_detail`
--
ALTER TABLE `salary_detail`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`MONTH`,`YEAR`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
