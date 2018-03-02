-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2018 at 05:08 AM
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
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `TASK_ID` bigint(6) NOT NULL,
  `TASK_NAME` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `CREATED_BY` bigint(5) DEFAULT NULL,
  `OWNED_BY` bigint(5) DEFAULT NULL,
  `SECOND_OWNER` bigint(5) DEFAULT NULL,
  `SUBSCRIBER` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERIFY_BY` bigint(5) DEFAULT NULL,
  `UPDATE_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_ID` bigint(5) DEFAULT NULL,
  `RESOLVED_BY` bigint(5) DEFAULT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DUE_DATE` date DEFAULT NULL,
  `RESOLUTION_DATE` timestamp NULL DEFAULT NULL,
  `TYPE` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AREA` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRIORITY` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` varchar(16) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Mới',
  `PLANED_FOR` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TIME_SPENT` float DEFAULT NULL,
  `TIME_SPENT_TYPE` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ESTIMATE` float DEFAULT NULL,
  `ESTIMATE_TIME_TYPE` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`TASK_ID`, `TASK_NAME`, `CREATED_BY`, `OWNED_BY`, `SECOND_OWNER`, `SUBSCRIBER`, `VERIFY_BY`, `UPDATE_TS`, `UPDATE_ID`, `RESOLVED_BY`, `CREATION_DATE`, `DUE_DATE`, `RESOLUTION_DATE`, `TYPE`, `AREA`, `PRIORITY`, `STATUS`, `PLANED_FOR`, `TIME_SPENT`, `TIME_SPENT_TYPE`, `ESTIMATE`, `ESTIMATE_TIME_TYPE`, `DESCRIPTION`) VALUES
(1, 'Xay dung chuc nang quan ly cong viec', 0, 1, 0, NULL, 0, '2018-02-09 03:51:25', 0, 0, '2018-01-25 07:49:31', '2018-01-31', NULL, NULL, 'CNTT', '2', 'Đang làm', '2018-01', 30, 'h', 40, 'h', 'Update time type\r\n1\r\n2\r\n3\r\n4\r\n5\r\n6'),
(4, 'Xay dung chuc nang quan ly cong viec cho tap doan ', 0, 2, 8, NULL, 1, '2018-01-29 10:42:14', 0, 0, '2018-01-26 08:49:55', '2018-01-10', NULL, NULL, 'CNTT', '1', 'Đang làm', '2018-02', 30, 'm', 40, 'h', ''),
(5, 'Xây dựng chức năng quản lý công việc cho tập đoàn IDI', 0, 1, 8, NULL, 2, '2018-02-08 09:03:41', 0, 0, '2018-01-26 08:58:34', '2018-04-30', NULL, NULL, 'CNTT', '1', 'Đang làm', '2018-05', 3, 'h', 8, 'h', ''),
(6, 'Xay dung chuc nang quan ly cong viec cho tap doan IDI1', 0, 6, NULL, NULL, NULL, '2018-01-26 09:20:11', NULL, NULL, '2018-01-26 09:20:11', '2018-01-31', NULL, NULL, 'HC', '3', 'Mới', '2018-01', NULL, NULL, 40, NULL, ''),
(7, 'Nhap phieu xuat kho', 0, 0, 5, NULL, 0, '2018-02-09 03:46:35', 0, 0, '2018-01-29 06:44:07', '2018-01-29', NULL, NULL, 'KT', '2', 'Chờ đánh giá', '2018-01', 15, 'h', 30, 'h', ''),
(8, 'Nhập phiếu nhập kho', 0, 5, 4, NULL, 4, '2018-02-08 10:24:46', 0, 0, '2018-01-29 06:54:33', '2018-01-29', NULL, NULL, 'KT', '1', 'Đã xong', '2018-01', 30, 'm', 30, 'm', 'Mo ta cong viec o day'),
(9, 'Lam bao cao tai chinh quy 1', 0, 4, 5, NULL, 0, '2018-02-08 10:17:16', 0, 0, '2018-01-29 07:10:21', '2018-03-30', NULL, NULL, 'KT', '2', 'Mới tạo', '2018-03', NULL, 'h', 40, 'm', ''),
(10, 'Gửi thư mời tông  kết cuối năm ', 0, 9, 6, NULL, 6, '2018-02-08 09:07:05', 0, 0, '2018-02-08 08:25:50', '2018-02-08', NULL, NULL, 'HC', '1', 'Mới tạo', '2018-02', NULL, 'm', 2, 'h', ''),
(11, 'Nhập phiếu nhập kho cho bia', 0, 4, 5, '1,2,3,5', 5, '2018-03-01 09:06:52', 0, 0, '2018-02-09 03:59:08', '2018-02-10', NULL, NULL, 'KT', '1', 'Mới tạo', '2018-02', NULL, 'm', 1, 'h', 'Test kieu estimate time (h) --> thay doi noi dung co thay doi ma1'),
(12, 'Xay dung chuc nang quan ly cong viec cho tap doan 1', 0, 1, 0, NULL, 2, '2018-03-02 04:03:49', 0, 0, '2018-02-28 10:50:05', '2018-03-03', NULL, NULL, 'CNTT', '1', 'Đang làm', '2018-03', 1, 'h', 40, 'h', 'Tao thu de test chuc nang gui mail + tiếng việt');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`TASK_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `TASK_ID` bigint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
