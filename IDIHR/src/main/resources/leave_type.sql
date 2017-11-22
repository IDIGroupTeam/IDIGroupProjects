-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2017 at 09:30 AM
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
-- Table structure for table `leave_type`
--

CREATE TABLE `idigroup`.`leave_type` (
  `LEAVE_ID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `LEAVE_NAME` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `leave_type`
--

INSERT INTO `idigroup`.`leave_type` (`LEAVE_ID`, `LEAVE_NAME`, `COMMENT`) VALUES
('CT', 'Công tác', NULL),
('CT/2', 'Công tác nửa ngày', NULL),
('HT', 'Học tập', NULL),
('HT/2', 'Học tập nửa ngày', NULL),
('KCC', 'Không chấm công', NULL),
('KCC/2', 'Không chấm công nửa ngày', NULL),
('LTCT', 'Làm thêm ngày cuối tuần', NULL),
('LTNL', 'Làm thêm ngày lễ', NULL),
('LTNT', 'Làm thêm ngày thường', NULL),
('NKL', 'Nghỉ không lương', NULL),
('NKL/2', 'Nghỉ không lương nửa ngày', NULL),
('NP', 'Nghỉ phép', NULL),
('NP/2', 'Nghỉ phép nửa ngày', NULL),
('O', 'Ốm điều dưỡng', NULL),
('O/2', 'Ốm điều dưỡng nửa ngày', NULL),
('X', 'Lương thời gian', NULL),
('X/2', 'Lương nửa thời gian', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leave_type`
--
ALTER TABLE `idigroup`.`leave_type`
  ADD PRIMARY KEY (`LEAVE_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
