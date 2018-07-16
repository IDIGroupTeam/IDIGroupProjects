-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2018 at 09:20 AM
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

ALTER TABLE `task` ADD `REVIEW_COMMENT` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL ;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`TASK_ID`, `TASK_NAME`, `CREATED_BY`, `OWNED_BY`, `SECOND_OWNER`, `VERIFY_BY`, `UPDATE_TS`, `UPDATE_ID`, `RESOLVED_BY`, `CREATION_DATE`, `DUE_DATE`, `RESOLUTION_DATE`, `TYPE`, `AREA`, `PRIORITY`, `STATUS`, `PLANED_FOR`, `TIME_SPENT`, `TIME_SPENT_TYPE`, `ESTIMATE`, `ESTIMATE_TIME_TYPE`, `DESCRIPTION`) VALUES
(1, 'Xay dung chuc nang quan ly cong viec', 0, 1, 6, 5, '2018-01-29 07:07:32', 0, 0, '2018-01-25 07:49:31', '2018-01-31', NULL, NULL, 'CNTT', '2', 'Đang làm', '2018-01', 0, 'm', 40, 'h', 'Update time type\r\n1\r\n2\r\n3\r\n4\r\n5\r\n6'),
(4, 'Xay dung chuc nang quan ly cong viec cho tap doan ', 0, 2, 8, 1, '2018-01-29 10:42:14', 0, 0, '2018-01-26 08:49:55', '2018-01-10', NULL, NULL, 'CNTT', '1', 'Đang làm', '2018-02', 30, 'm', 40, 'h', ''),
(5, 'Xây dựng chức năng quản lý công việc cho tập đoàn IDI', 0, 1, 8, 2, '2018-01-29 10:49:18', 0, 0, '2018-01-26 08:58:34', '2018-04-30', NULL, NULL, 'CNTT', '1', 'Mới tạo', '2018-05', 1, 'd', 8, 'w', ''),
(6, 'Xay dung chuc nang quan ly cong viec cho tap doan IDI1', 0, 6, NULL, NULL, '2018-01-26 09:20:11', NULL, NULL, '2018-01-26 09:20:11', '2018-01-31', NULL, NULL, 'HC', '3', 'Mới', '2018-01', NULL, NULL, 40, NULL, ''),
(7, 'Nhap phieu xuat kho', 0, 6, 5, 9, '2018-01-29 07:09:16', 0, 0, '2018-01-29 06:44:07', '2018-01-29', NULL, NULL, 'KT', '2', 'Chờ đánh giá', '2018-01', 15, 'm', 30, 'm', ''),
(8, 'Nhập phiếu nhập kho', 0, 5, 5, 5, '2018-02-01 07:55:03', 0, 0, '2018-01-29 06:54:33', '2018-01-29', NULL, NULL, 'KT', '1', 'Đã xong', '2018-01', 30, 'm', 30, NULL, 'Mo ta cong viec o day'),
(9, 'Lam bao cao tai chinh quy 1', 0, 4, 5, 2, '2018-01-29 07:10:21', NULL, NULL, '2018-01-29 07:10:21', '2018-03-30', NULL, NULL, 'KT', '1', 'Mới', '2018-03', NULL, NULL, 40, 'h', '');

-- --------------------------------------------------------

--
-- Table structure for table `task_comment`
--

CREATE TABLE `task_comment` (
  `COMMENT_INDEX` bigint(3) NOT NULL,
  `TASK_ID` bigint(6) NOT NULL,
  `COMMENTED_BY` bigint(5) DEFAULT NULL,
  `COMMENT_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CONTENT` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_comment`
--

INSERT INTO `task_comment` (`COMMENT_INDEX`, `TASK_ID`, `COMMENTED_BY`, `COMMENT_TIME`, `CONTENT`) VALUES
(1, 8, 0, '2018-02-01 06:14:48', 'test comment lan 1'),
(2, 8, 0, '2018-02-01 06:49:33', 'Test comment lan 2'),
(3, 8, 0, '2018-02-01 07:28:26', 'Tét comment lần 3 tiếng việt có giấu'),
(4, 8, 0, '2018-02-01 07:55:03', 'Xong'),
(5, 8, 0, '2018-02-01 08:15:53', 'xong that ma'),
(6, 8, 0, '2018-02-01 08:17:11', 'Da bao la xong roi ma');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`TASK_ID`);

--
-- Indexes for table `task_comment`
--
ALTER TABLE `task_comment`
  ADD PRIMARY KEY (`COMMENT_INDEX`,`TASK_ID`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `TASK_ID` bigint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
