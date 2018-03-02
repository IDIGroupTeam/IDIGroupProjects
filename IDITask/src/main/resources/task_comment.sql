-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2018 at 05:06 AM
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
(1, 11, 0, '2018-02-26 08:07:24', 'Gui mail'),
(1, 12, 0, '2018-03-01 09:53:54', 'comment sô 1'),
(2, 8, 0, '2018-02-01 06:49:33', 'Test comment lan 2'),
(2, 11, 0, '2018-02-26 08:53:45', 'Test gui mail notification'),
(2, 12, 0, '2018-03-01 09:59:24', 'Comment sô 2'),
(3, 8, 0, '2018-02-01 07:28:26', 'Tét comment lần 3 tiếng việt có giấu'),
(3, 11, 0, '2018-02-26 09:08:22', 'Test gui mail notification 1'),
(3, 12, 0, '2018-03-01 10:03:21', 'Comment sô 3'),
(4, 8, 0, '2018-02-01 07:55:03', 'Xong'),
(4, 11, 0, '2018-02-26 09:10:32', 'Test gui mail notification 2'),
(4, 12, 0, '2018-03-01 10:09:44', 'Comment sô 4'),
(5, 8, 0, '2018-02-01 08:15:53', 'xong that ma'),
(5, 11, 0, '2018-02-28 08:03:15', 'gui mail'),
(5, 12, 0, '2018-03-01 10:11:51', 'Comment sô 5'),
(6, 8, 0, '2018-02-01 08:17:11', 'Da bao la xong roi ma'),
(6, 11, 0, '2018-02-28 08:57:54', 'Test gui mail notification 3'),
(6, 12, 0, '2018-03-01 10:19:18', 'Comment sô 6'),
(7, 8, 0, '2018-02-01 10:25:09', 'comment demo cho mr Tuyen'),
(7, 11, 0, '2018-02-28 09:11:18', 'Test gui mail notification 4'),
(7, 12, 0, '2018-03-01 10:35:23', 'Comment sô 6'),
(8, 8, 0, '2018-02-08 10:24:46', 'Hop demo'),
(8, 11, 0, '2018-02-28 09:25:29', 'Test gui mail notification 5'),
(8, 12, 0, '2018-03-01 10:38:41', 'Comment sô 7'),
(9, 11, 0, '2018-02-28 09:39:48', 'gửi mail thông báo = tiếng việt'),
(9, 12, 0, '2018-03-02 04:03:49', 'Gửi mail thông báo ....'),
(10, 11, 0, '2018-02-28 09:49:21', 'Gửi mail thông báo = tiếng việt 1'),
(11, 11, 0, '2018-02-28 10:09:11', 'Gửi mail thông báo = tiếng việt 2'),
(12, 11, 0, '2018-02-28 10:11:39', 'Gửi mail thông báo = tiếng việt 3'),
(13, 11, 0, '2018-02-28 10:12:59', 'Gửi mail thông báo = tiếng việt 4'),
(14, 11, 0, '2018-02-28 10:15:50', 'Gửi mail thông báo = tiếng việt 4'),
(15, 11, 0, '2018-02-28 10:18:18', 'Gửi mail thông báo = tiếng việt 6'),
(16, 11, 0, '2018-02-28 10:23:07', 'Gửi mail thông báo = tiếng việt 7'),
(17, 11, 0, '2018-02-28 10:24:43', 'Gửi mail thông báo = tiếng việt 8'),
(18, 11, 0, '2018-03-01 03:02:13', 'Gửi mail thông báo = tiếng việt 9. table'),
(19, 11, 0, '2018-03-01 03:03:35', 'Gửi mail thông báo = tiếng việt 9 --> table'),
(20, 11, 0, '2018-03-01 03:08:52', 'Gửi mail thông báo = tiếng việt 10 --> table'),
(21, 11, 0, '2018-03-01 03:28:33', 'Gửi mail thông báo = tiếng việt 11 --> table');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `task_comment`
--
ALTER TABLE `task_comment`
  ADD PRIMARY KEY (`COMMENT_INDEX`,`TASK_ID`) USING BTREE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
