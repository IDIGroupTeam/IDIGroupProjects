-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 02, 2019 at 04:21 AM
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
  `WORK_COMPLETE` int(4) NOT NULL DEFAULT '100',
  `WORKED_DAY` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OTHER` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ARREARS` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salary_detail`
--

INSERT INTO `salary_detail` (`EMPLOYEE_ID`, `ACTUAL_SALARY`, `OVER_TIME_N`, `OVER_TIME_W`, `OVER_TIME_H`, `BOUNUS`, `SUBSIDIZE`, `ADVANCE_PAYED`, `TAX_PERSONAL`, `MONTH`, `YEAR`, `COMMENT`, `OVER_TIME_SALARY`, `PAYED_INSURANCE`, `WORK_COMPLETE`, `WORKED_DAY`, `OTHER`, `ARREARS`) VALUES
(5, '1.132E7', '', '', '', '500000', '600000', '1000000', '150000', 8, 2018, '', '0.0', '630000.0', 100, '', NULL, NULL),
(5, '1.132E7', '', '', '', '500000', '600000', '1000000', '150000', 9, 2018, '', '0.0', '630000.0', 100, NULL, NULL, NULL),
(5, '1.2069996E7', '5', '2', '', '500000', '600000', '1000000', '150000', 10, 2018, '', '749996.0', '630000.0', 100, NULL, NULL, NULL),
(5, '1.132E7', '', '', '', '500000', '600000', '1000000', '150000', 11, 2018, '', '0.0', '630000.0', 100, NULL, NULL, NULL),
(5, '1.222E7', '', '', '', '500000', '600000', '100000', '150000', 12, 2018, '', '0.0', '630000.0', 100, '', '', NULL),
(8, '8556943.0', '1', '1', '1', '300000', '600000', '1000000', '150000', 9, 2018, '', '306943.0', NULL, 100, NULL, NULL, NULL),
(8, '9200274.0', '1', '1', '1', '500000', '200000', '300000', '', 10, 2018, '', '300274.0', NULL, 100, '', NULL, NULL),
(8, '8950000.0', '', '', '', '500000', '600000', '500000', '150000', 11, 2018, '', '0.0', NULL, 100, '', NULL, NULL),
(16, '8850000.0', '', '', '', '', '', '', '', 1, 2019, '', '0.0', NULL, 100, '22', '1000000', '650000'),
(16, '9000000.0', '', '', '', '500000', '', '', '', 8, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(16, '8500000.0', '', '', '', '', '', '', '', 9, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(16, '8500000.0', '', '', '', '500000', '', '500000', '', 10, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(16, '9000000.0', '', '', '', '500000', '', '', '', 11, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(16, '9056919.0', '1', '', '', '500000', '', '', '', 12, 2018, '', '56919.0', NULL, 100, NULL, NULL, NULL),
(18, '3333333.5', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, '18', NULL, NULL),
(18, '3303571.2', '', '', '', '', '', '', '', 12, 2018, '', '0.0', NULL, 100, '18.5', NULL, NULL),
(37, '8500000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, '18.5', NULL, NULL),
(37, '8000000.0', '', '', '', '', '', '500000', '', 12, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(62, '4200000.0', '', '', '', '200000', '', '', '', 11, 2018, '', '0.0', NULL, 80, NULL, NULL, NULL),
(62, '4521739.0', '', '', '', '500000', '', '', '', 12, 2018, '', '0.0', NULL, 100, '18.5', NULL, NULL),
(68, '4000000.0', '0', '4', '', '', '', '', '', 10, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(68, '4000000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(69, '4301000.0', '3', '', '', '200000', '500000', '', '', 8, 2018, '', '0.0', '399000.0', 100, NULL, NULL, NULL),
(69, '3773342.0', '4', '4', '4', '200000', '500000', '', '150000', 9, 2018, '', '433342.0', '210000.0', 100, NULL, NULL, NULL),
(69, '4709970.5', '1', '1', '1', '500000', '600000', '500000', '150000', 10, 2018, '', '158971.0', '399000.0', 100, NULL, NULL, NULL),
(69, '3601000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', '399000.0', 100, NULL, NULL, NULL),
(69, '4709970.5', '1', '1', '1', '500000', '600000', '500000', '150000', 12, 2018, '', '158971.0', '399000.0', 100, NULL, NULL, NULL),
(70, '3500000.0', '', '', '', '500000', '', '', '', 9, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(70, '2973368.0', '1', '0', '1', '100000', '0', '200000', '0', 10, 2018, '', '73368.0', NULL, 100, NULL, NULL, NULL),
(70, '3000000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL),
(70, '3000000.0', '', '', '', '', '', '', '', 12, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL);

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
