-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 27, 2019 at 11:51 AM
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
  `BASIC_SALARY` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EXCHANGE_RATE` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
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
  `ARREARS` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAY_STATUS` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `salary_detail`
--

INSERT INTO `salary_detail` (`EMPLOYEE_ID`, `BASIC_SALARY`, `EXCHANGE_RATE`, `ACTUAL_SALARY`, `OVER_TIME_N`, `OVER_TIME_W`, `OVER_TIME_H`, `BOUNUS`, `SUBSIDIZE`, `ADVANCE_PAYED`, `TAX_PERSONAL`, `MONTH`, `YEAR`, `COMMENT`, `OVER_TIME_SALARY`, `PAYED_INSURANCE`, `WORK_COMPLETE`, `WORKED_DAY`, `OTHER`, `ARREARS`, `PAY_STATUS`) VALUES
(5, '12000000', NULL, '1.1775E7', '', '', '', '500000', '', '200000', '', 1, 2019, '', '0.0', '525000.0', 100, '', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '1.1475E7', '', '', '', '', '', '', '', 2, 2019, '', '0.0', '525000.0', 100, '', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '1.2675E7', '', '', '', '', '', '', '', 3, 2019, '', '0.0', '525000.0', 110, '', '', '', 'Chưa trả lương'),
(5, '12000000', NULL, '9075000.0', '', '', '', '', '', '', '', 4, 2019, '', '0.0', '525000.0', 80, '21', '', '', ''),
(5, '12000000', NULL, '1.1475E7', '', '', '', '', '', '', '', 6, 2019, '', '0.0', '525000.0', 100, '', '', '', ''),
(5, '12000000', NULL, '1.1425E7', '', '', '', '500000', '600000', '1000000', '150000', 8, 2018, '', '0.0', '525000.0', 100, '', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '1.1185E7', '', '', '', '500000', '600000', '1000000', '150000', 9, 2018, '', '0.0', '525000.0', 105, '21', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '1.508369E7', '5', '2', '', '5000000', '600000', '1000000', '150000', 10, 2018, '', '749996.0', '525000.0', 105, '19', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '1.1425E7', '', '', '', '500000', '600000', '1000000', '150000', 11, 2018, '', '0.0', '525000.0', 100, '', '', '', 'Đã trả lương'),
(5, '12000000', NULL, '9855433.0', '2', '', '', '', '', '100000', '150000', 12, 2018, '', '195651.0', '525000.0', 100, '20', '', '', 'Đã trả lương'),
(8, '9900000', NULL, '9900000.0', '', '', '', '', '', '', '', 1, 2019, '', '0.0', NULL, 100, '', '', '', NULL),
(8, '9900000', NULL, '9900000.0', '', '', '', '', '', '', '', 3, 2019, '', '0.0', NULL, 100, '', '', '', 'Đã trả lương'),
(8, '9900000', NULL, '9823500.0', '', '', '', '', '', '', '', 4, 2019, '', '0.0', '472500.0', 104, '', '', '', ''),
(8, NULL, NULL, '9927500.0', '', '', '', '500000', '', '', '', 5, 2019, 'Tinh luong thang 6, ...  truoc thang khi tinh cho thang 5 tinh nhu thang moi --> tinh cho thang 5', '0.0', '472500.0', 100, '', '', '', ''),
(8, NULL, NULL, '9927500.0', '', '', '', '500000', '', '', '', 6, 2019, 'Tinh luong thang 6, ...  truoc thang khi tinh cho thang 5 tinh nhu thang moi', '0.0', '472500.0', 100, '', '', '', ''),
(8, '9900000.0', NULL, '9727500.0', '', '', '', '500000', '', '200000', '', 7, 2019, 'Tinh luong thang 7, ...  truoc thang khi tinh cho thang 5 tinh nhu thang moi', '0.0', '472500.0', 100, '23', '', '', ''),
(8, '9900000', NULL, '9832000.0', '1', '1', '1', '300000', '600000', '1000000', '150000', 9, 2018, '', '357500.0', '472500.0', 103, '', '', '', ''),
(8, '9900000', NULL, '1.0649726E7', '1', '1', '1', '500000', '200000', '300000', '', 10, 2018, '', '349726.0', NULL, 100, '', '', '', NULL),
(8, '9900000', NULL, '9877500.0', '', '', '', '500000', '600000', '500000', '150000', 11, 2018, '', '0.0', '472500.0', 100, '', '', '', ''),
(11, '1.155E7', '23100', '1.1935001E7', '', '', '', '500000', '300000', '200000', '215000', 4, 2019, '', '0.0', NULL, 100, '', '', '', ''),
(11, '1.155E7', '23100', '1.1950001E7', '', '', '', '500000', '100000', '200000', '', 5, 2019, '', '0.0', NULL, 100, '', '', '', ''),
(11, '1.155E7', '23100', '1.2050001E7', '', '', '', '500000', '', '', '', 6, 2019, '', '0.0', NULL, 100, '', '', '', ''),
(14, '12000000', NULL, '1.2E7', '', '', '', '', '', '', '', 4, 2019, '', '0.0', NULL, 100, '', '', '', NULL),
(16, '8500000', NULL, '8850000.0', '', '', '', '', '', '', '', 1, 2019, '', '0.0', NULL, 100, '22', '1000000', '650000', NULL),
(16, '8500000', NULL, '9000000.0', '', '', '', '500000', '', '', '', 8, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(16, '8500000', NULL, '8500000.0', '', '', '', '', '', '', '', 9, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(16, '8500000', NULL, '8500000.0', '', '', '', '500000', '', '500000', '', 10, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(16, '8500000', NULL, '9000000.0', '', '', '', '500000', '', '', '', 11, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(16, '8500000', NULL, '9056919.0', '1', '', '', '500000', '', '', '', 12, 2018, '', '56919.0', NULL, 100, NULL, NULL, NULL, NULL),
(18, '5000000', NULL, '3666667.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 110, '18', '', '', ''),
(18, '5000000', NULL, '3053571.2', '', '', '', '', '', '', '', 12, 2018, '', '0.0', NULL, 90, '19', '', '', 'Đã trả lương'),
(37, '', NULL, '8500000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, '18.5', NULL, NULL, NULL),
(37, '8500000', NULL, '8000000.0', '', '', '', '', '', '500000', '', 12, 2018, '', '0.0', NULL, 100, '', '', '', NULL),
(62, '12000000', NULL, '4200000.0', '', '', '', '200000', '', '', '', 11, 2018, '', '0.0', NULL, 80, NULL, NULL, NULL, NULL),
(62, '12000000', NULL, '4521739.0', '', '', '', '500000', '', '', '', 12, 2018, '', '0.0', NULL, 100, '18.5', NULL, NULL, NULL),
(68, '12000000', NULL, '4000000.0', '0', '4', '', '', '', '', '', 10, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(68, '14000000', NULL, '1.4E7', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, '', '', '', ''),
(69, '4500000', NULL, '4551000.0', '', '', '', '', '', '', '', 4, 2019, '', '0.0', '399000.0', 110, '', '', '', NULL),
(69, '4500000', NULL, '4801000.0', '3', '', '', '200000', '500000', '', '', 8, 2018, '', '0.0', '399000.0', 100, '', '', '', ''),
(69, '4500000', NULL, '5301000.0', '4', '4', '4', '200000', '500000', '', '150000', 9, 2018, '', '650000.0', '399000.0', 100, '', '', '', ''),
(69, '4500000', NULL, '4709970.5', '1', '1', '1', '500000', '600000', '500000', '150000', 10, 2018, '', '158971.0', '399000.0', 100, '', '', '', NULL),
(69, '4500000', NULL, '4101000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', '399000.0', 100, '', '', '', ''),
(69, '4500000', NULL, '209970.5', '1', '1', '1', '500000', '600000', '500000', '150000', 12, 2018, '', '158971.0', '399000.0', 0, '', '', '', ''),
(70, '12000000', NULL, '3500000.0', '', '', '', '500000', '', '', '', 9, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(70, '12000000', NULL, '2973368.0', '1', '0', '1', '100000', '0', '200000', '0', 10, 2018, '', '73368.0', NULL, 100, NULL, NULL, NULL, NULL),
(70, '12000000', NULL, '3000000.0', '', '', '', '', '', '', '', 11, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL),
(70, '12000000', NULL, '3000000.0', '', '', '', '', '', '', '', 12, 2018, '', '0.0', NULL, 100, NULL, NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `salary_detail`
--
ALTER TABLE `salary_detail`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`MONTH`,`YEAR`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
