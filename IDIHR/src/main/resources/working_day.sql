-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 22, 2018 at 08:01 AM
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
-- Table structure for table `working_day`
--

CREATE TABLE IDIGROUP.`working_day` (
  `MONTH` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `WORK_DAY` float NOT NULL,
  `FOR_COMPANY` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `UPDATE_ID` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `UPDATE_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `COMMENT` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `working_day`
--

INSERT INTO IDIGROUP.`working_day` (`MONTH`, `WORK_DAY`, `FOR_COMPANY`, `UPDATE_ID`, `UPDATE_TS`, `COMMENT`) VALUES
('2017-10', 24, 'IDI', NULL, '2018-03-21 10:29:11', ''),
('2018-02', 23, 'IDI', NULL, '2018-03-20 10:18:51', 'updated again'),
('2018-03', 23, 'IDI', '', '2018-03-20 10:03:06', 'Truong test ....'),
('2018-04', 23, 'IDI', '', '2018-03-20 10:03:06', 'Sua so ngay tu 22 -> 23');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `working_day`
--
ALTER TABLE IDIGROUP.`working_day`
  ADD PRIMARY KEY (`MONTH`,`FOR_COMPANY`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
