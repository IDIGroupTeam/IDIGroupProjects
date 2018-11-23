-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2018 at 05:37 AM
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

CREATE TABLE `leave_type` (
  `LEAVE_ID` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `LEAVE_NAME` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `leave_type`
--

INSERT INTO `leave_type` (`LEAVE_ID`, `LEAVE_NAME`, `COMMENT`) VALUES
('CT', 'Công tác', NULL),
('CT2', 'Công tác nửa ngày', NULL),
('CVBNCGC', 'Công việc bên ngoài cuối giờ chiều', NULL),
('CVBNCGS', 'Công việc bên ngoài cuối giờ sáng', NULL),
('CVBNDGC', 'Công việc bên ngoài đầu giờ chiều', NULL),
('CVBNDGS', 'Công việc bên ngoài đầu giờ sáng', NULL),
('DMC', 'Xin phép đi muộn chiều', NULL),
('DMS', 'Xin phép đi muộn sáng', NULL),
('HT', 'Học tập', NULL),
('HT2', 'Học tập nửa ngày', NULL),
('KCC', 'Không chấm công', NULL),
('LTCT', 'Làm thêm ngày cuối tuần', NULL),
('LTNL', 'Làm thêm ngày lễ', NULL),
('LTNT', 'Làm thêm ngày thường', NULL),
('NKL', 'Nghỉ không lương', NULL),
('NKL2', 'Nghỉ không lương nửa ngày', NULL),
('NKP', 'Nghỉ không phép', NULL),
('NKP2', 'Nghỉ không phép nủa ngày', NULL),
('NP', 'Nghỉ phép', NULL),
('NP2', 'Nghỉ phép nửa ngày', NULL),
('O', 'Ốm điều dưỡng', NULL),
('O2', 'Ốm điều dưỡng nửa ngày', NULL),
('VSC', 'Xin phép về sớm chiều', NULL),
('VSS', 'Xin phép về sớm sáng', NULL),
('X', 'Lương thời gian', NULL),
('X2', 'Lương nửa thời gian', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leave_type`
--
ALTER TABLE `leave_type`
  ADD PRIMARY KEY (`LEAVE_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
