-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2018 at 12:15 PM
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
-- Table structure for table `work_status`
--

CREATE TABLE `work_status` (
  `STATUS_ID` varchar(16) NOT NULL,
  `STATUS_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `work_status`
--

INSERT INTO `work_status` (`STATUS_ID`, `STATUS_NAME`, `DESCRIPTION`) VALUES
('ChinhThuc', 'Chính thức', NULL),
('CongTac', 'Cộng tác', NULL),
('DaThoiViec', 'Đã thôi việc', NULL),
('NghiKhongLuong', 'Nghỉ không lương', NULL),
('NghiOm', 'Nghỉ ốm', NULL),
('NghiThaiSan', 'Nghỉ thai sản', NULL),
('ThoiVu', 'Thời vụ', NULL),
('ThuViec', 'Thử việc', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `work_status`
--
ALTER TABLE `work_status`
  ADD PRIMARY KEY (`STATUS_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

//update work status in employee info table
update idigroup.employee_info set work_status="ChinhThuc" where work_status="Chính thức";
update idigroup.employee_info set work_status="CongTac" where work_status="Cộng tác";
update idigroup.employee_info set work_status="DaThoiViec" where work_status="Đã thôi việc";
update idigroup.employee_info set work_status="NghiKhongLuong" where work_status="Nghỉ không lương";
update idigroup.employee_info set work_status="NghiOm" where work_status="Nghỉ ốm";
update idigroup.employee_info set work_status="NghiThaiSan" where work_status="Nghỉ thai sản";
update idigroup.employee_info set work_status="ThoiVu" where work_status="Thời vụ";
update idigroup.employee_info set work_status="ThuViec" where work_status="Thử việc";

