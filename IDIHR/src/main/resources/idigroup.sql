-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 15, 2017 at 11:20 AM
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
-- Table structure for table `department`
--

CREATE TABLE `idigroup`.`department` (
  `DEPARTMENT_ID` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `DEPARTMENT_NAME` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `department`
--

INSERT INTO `idigroup`.`department` (`DEPARTMENT_ID`, `DEPARTMENT_NAME`, `DESCRIPTION`) VALUES
('CNTT', 'Công nghệ thông tin', ''),
('HC', 'Hành chính - Tổng hợp', ''),
('KHDT', 'Kế hoạch và đầu tư ', NULL),
('KT', 'Kế toán', 'test duplicate'),
('KTh', 'Kĩ Thuật', ''),
('XNK', 'Xuất nhập khẩu', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employee_info`
--

CREATE TABLE `idigroup`.`employee_info` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `FULL_NAME` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `GENDER` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `JOB_TITLE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WORK_STATUS` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MARITAL_STATUS` varchar(32) CHARACTER SET ucs2 COLLATE ucs2_unicode_ci DEFAULT NULL,
  `LOGIN_ACCOUNT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERSONAL_ID` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ISSUE_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPARTMENT` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `JOIN_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `OFFICIAL_JOIN_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `TERMINATION_DATE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REASON_FOR_LEAVE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CURRENT_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERMANENT_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOTE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NATION` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE` mediumblob,
  `EMER_NAME` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMER_PHONE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NAME` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_BRANCH` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE_PATH` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `HEALTH_INSU_NO` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERCENT_SOCI_INSU` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `employee_info`
--

INSERT INTO `idigroup`.`employee_info` (`EMPLOYEE_ID`, `FULL_NAME`, `GENDER`, `JOB_TITLE`, `WORK_STATUS`, `DOB`, `MARITAL_STATUS`, `LOGIN_ACCOUNT`, `PERSONAL_ID`, `ISSUE_DATE`, `DEPARTMENT`, `PHONE_NO`, `JOIN_DATE`, `OFFICIAL_JOIN_DATE`, `EMAIL`, `TERMINATION_DATE`, `REASON_FOR_LEAVE`, `CURRENT_ADDRESS`, `PERMANENT_ADDRESS`, `NOTE`, `NATION`, `IMAGE`, `EMER_NAME`, `EMER_PHONE_NO`, `BANK_NO`, `BANK_NAME`, `BANK_BRANCH`, `IMAGE_PATH`, `SALA_SOCI_INSU`, `SOCIAL_INSU_NO`, `HEALTH_INSU_NO`, `PERCENT_SOCI_INSU`) VALUES
(1, 'Nguyễn Văn Trượng', 'male', 'CTV', 'Cong tac vien', '1982-10-30', 'married', 'truongnv', '01234567890', '2017-11-02', 'CNTT', '0912345678', '2017-08-01', '', 'truongnv.idigroup@gmail.com', NULL, NULL, 'Hoang Mai-HN', 'Hoang Mai', '', 'Viet Nam', NULL, 'Mai Thi Vo', '0911111111', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Chrysanthemum.jpg', NULL, NULL, NULL, NULL),
(2, 'Pham Xuan Tuyen', 'male', 'TP', 'Thu viec', '1990-11-11', 'married', 'tuyenpx', '11111111111', '2011-11-11', 'CNTT', '09999999999', '2012-11-11', '', 'tuyenpx.idigroup@gmail.com', NULL, NULL, 'Hoang Mai', 'Hung Yen', 'Edit: Test validate', 'Viet Nam', 0x646f776e6c6f6164202831292e6a7067, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Jellyfish.jpg', NULL, NULL, NULL, NULL),
(3, 'Tran Dong Hai', 'female', 'CTV', 'Cong tac vien', '1990-11-11', 'sigle', 'haitd', '11111111111', '2011-11-11', 'CNTT', '09999999321', '2013-11-11', '', 'haitd.idigroup@gmail.com', NULL, NULL, 'Cau giay', 'Thai binh', 'test upload image', 'Viet Nam', NULL, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Penguins.jpg', NULL, NULL, NULL, NULL),
(4, 'Nguyen Van Anh', 'female', 'NV', 'Thu viec', '1992-08-06', 'sigle', 'anhnv', '11111111111', '2016-05-01', 'KT', '09999999234', '2014-11-11', '', 'anv.idigroup@gmail.com', NULL, NULL, 'Cau giay', 'Hoang mai', '', 'Viet Nam', 0x50656e6775696e732e6a7067, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Tulips.jpg', NULL, NULL, NULL, NULL),
(5, 'Pham Thi My Le', 'female', 'NV', 'Chinh thuc', '1988-12-30', 'sigle', 'leptm', '', '', 'KT', '09777777777', '2015-11-11', NULL, 'leptm.idigroup@gmail.com', NULL, NULL, '', '', '', '', NULL, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Koala.jpg', NULL, NULL, NULL, NULL),
(6, 'Nguyen Van A', 'male', 'NV', 'Thu viec', '1984-12-08', 'widowed', 'anv', '', '', 'HC', '0988123125', '2016-11-11', '', 'a.nv.idigroup@gmail.com', NULL, NULL, '', '', '', '', '', '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Penguins.jpg', NULL, NULL, NULL, NULL),
(7, 'Pham Thi Tuyen', 'male', 'CTV', 'Thu viec', '1979-01-01', 'sigle', 'tuyenpt', '', '', 'CNTT', '', '2017-11-11', '', 'tuyenpt.idigroup@gmail.com', NULL, NULL, '', '', '', '', NULL, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Desert.jpg', NULL, NULL, NULL, NULL),
(8, 'Nguyen Van B', 'male', 'CTV', 'Thu viec', '', 'sigle', 'bnv', '', '', 'CNTT', '', '2016-10-11', '', 'bnv.idigroup@gmail.com', NULL, NULL, '', '', '', '', NULL, '', '', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Jellyfish.jpg', NULL, NULL, NULL, NULL),
(9, 'Nguyen Thi Hai', 'female', 'NV', 'Chinh thuc', '1981-01-01', 'married', 'haint', '123456789012', '2015-01-01', 'HC', '0988123123', '2017-01-11', '', 'haint@gmail.com', NULL, NULL, 'HN', 'Hoang Mai', 'xxx', 'VN', NULL, 'Mai Thi Vo', '0911111111', NULL, NULL, NULL, 'http://localhost:8080/IDIHR/employeeImage/Hydrangeas.jpg', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `insurance`
--

CREATE TABLE `idigroup`.`insurance` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `PERCENT_SOCI_INSU_C` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `PERCENT_SOCI_INSU_E` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `PLACE` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `HEALTH_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `HEALTH_INSU_PLACE` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `SALARY_ZONE` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `PAY_TYPE` varchar(12) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `insurance`
--

INSERT INTO `idigroup`.`insurance` (`EMPLOYEE_ID`, `SOCIAL_INSU_NO`, `SALA_SOCI_INSU`, `PERCENT_SOCI_INSU_C`, `PERCENT_SOCI_INSU_E`, `PLACE`, `COMMENT`, `STATUS`, `HEALTH_INSU_NO`, `HEALTH_INSU_PLACE`, `SALARY_ZONE`, `PAY_TYPE`) VALUES
(1, '222221112222', '5.000.000', '16', '9', 'BH Quận Tây Hồ', 'Ghi chu o day', 'Dang nop', '123321123321', 'BV Thanh Nhàn, HN', '1', 'T'),
(2, '222221112223', '5.000.000', '16', '9', 'BH Quận Tây Hồ', '', 'Dang nghi thai san', '123321123322', 'BV Thanh Nhàn, HN', '1', 'T'),
(3, '222221112225', '5.000.000', '16', '9', 'BH Quận Tây Hồ', '', 'Dang nop', '', '', '1', 'Q');

-- --------------------------------------------------------

--
-- Table structure for table `job_title`
--

CREATE TABLE `idigroup`.`job_title` (
  `TITLE_ID` varchar(12) CHARACTER SET latin1 NOT NULL,
  `TITLE_NAME` varchar(64) CHARACTER SET latin1 NOT NULL,
  `DESCRIPTION` varchar(256) CHARACTER SET latin1 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `job_title`
--

INSERT INTO `idigroup`.`job_title` (`TITLE_ID`, `TITLE_NAME`, `DESCRIPTION`) VALUES
('CTV', 'Cong Tac Vien', ''),
('GD', 'Giam Doc', ''),
('NV', 'Nhan Vien', ''),
('TN', 'Truong Nhom', ''),
('TP', 'Truong Phong', ''),
('TT', 'To Truong', '');

-- --------------------------------------------------------

--
-- Table structure for table `leave_info`
--

CREATE TABLE `idigroup`.`leave_info` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `DATE` date NOT NULL,
  `LEAVE_TYPE` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `TIME_VALUE` int(11) DEFAULT NULL,
  `COMMENT` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `leave_info`
--

INSERT INTO `idigroup`.`leave_info` (`EMPLOYEE_ID`, `DATE`, `LEAVE_TYPE`, `TIME_VALUE`, `COMMENT`) VALUES
(1, '2017-11-16', 'O', 4, ''),
(1, '2017-11-22', 'LTNT', 3, 'Làm thêm 3h ngày thường'),
(1, '2017-12-04', 'NP', 8, ''),
(1, '2017-12-05', 'NP', 4, ''),
(1, '2017-12-12', 'NP', 4, 'Nghi phep nua ngay'),
(1, '2017-12-14', 'CT', 4, 'cong tac nua ngay'),
(2, '2016-11-03', 'NP', 8, ''),
(2, '2017-10-21', 'NKL', 8, ''),
(2, '2017-11-22', 'KCC', 1, ''),
(2, '2017-12-05', 'CT', 4, ''),
(2, '2017-12-05', 'NP', 8, ''),
(3, '2017-10-01', 'CT', 8, ''),
(3, '2017-11-16', 'CT', 4, ''),
(4, '2017-11-10', 'NP', 8, '');

-- --------------------------------------------------------

--
-- Table structure for table `leave_report`
--

CREATE TABLE `idigroup`.`leave_report` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `YEAR` varchar(4) NOT NULL,
  `LEAVE_QUATA` double DEFAULT '12',
  `LEAVE_USED` double DEFAULT NULL,
  `LEAVE_REMAIN` double DEFAULT NULL,
  `SENIORITY` double DEFAULT NULL,
  `REST_QUATA` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
('CT2', 'Công tác nửa ngày', NULL),
('HT', 'Học tập', NULL),
('HT2', 'Học tập nửa ngày', NULL),
('KCC', 'Không chấm công', NULL),
('LTCT', 'Làm thêm ngày cuối tuần', NULL),
('LTNL', 'Làm thêm ngày lễ', NULL),
('LTNT', 'Làm thêm ngày thường', NULL),
('NKL', 'Nghỉ không lương', NULL),
('NKL2', 'Nghỉ không lương nửa ngày', NULL),
('NKP2', 'Nghỉ không phép nủa ngày', NULL),
('NP', 'Nghỉ phép', NULL),
('NP2', 'Nghỉ phép nửa ngày', NULL),
('O', 'Ốm điều dưỡng', NULL),
('O2', 'Ốm điều dưỡng nửa ngày', NULL),
('X', 'Lương thời gian', NULL),
('X2', 'Lương nửa thời gian', NULL),
('CVBNDGS', 'Công việc bên ngoài đầu giờ sáng', NULL),
('CVBNDGC', 'Công việc bên ngoài đầu giờ chiều', NULL),
('CVBNCGS', 'Công việc bên ngoài cuối giờ sáng', NULL),
('CVBNCGC', 'Công việc bên ngoài cuối giờ chiều', NULL);
-- --------------------------------------------------------

--
-- Table structure for table `process_insurance`
--

CREATE TABLE `idigroup`.`process_insurance` (
  `SOCIAL_INSU_NO` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `SALA_SOCI_INSU` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY_PAY` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `FROM_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TO_DATE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `process_insurance`
--

INSERT INTO `idigroup`.`process_insurance` (`SOCIAL_INSU_NO`, `SALA_SOCI_INSU`, `COMPANY_PAY`, `FROM_DATE`, `TO_DATE`, `COMMENT`) VALUES
('222221112222', '5.000.000', 'IDI', '2017-08-01', '2017-08-31', ''),
('222221112222', '5.500.000', 'IDI', '2017-09-01', '2017-09-30', ''),
('222221112222', '6.000.000', 'IDI', '2017-10-01', '', 'Nhan vien moi tu 1/8/17'),
('222221112225', '5.000.000', 'IDI', '2017-10-01', '2017-11-30', '');

-- --------------------------------------------------------

--
-- Table structure for table `timekeeping`
--

CREATE TABLE `idigroup`.`timekeeping` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `DATE` date NOT NULL,
  `TIME_IN` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `TIME_OUT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COME_LATE_M` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COME_LATE_A` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEAVE_SOON_M` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LEAVE_SOON_A` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMENT` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


 ALTER TABLE `timekeeping` ADD `WORKED_TIME` VARCHAR(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL AFTER `TIME_OUT`;
--
-- Dumping data for table `timekeeping`
--

INSERT INTO `idigroup`.`timekeeping` (`EMPLOYEE_ID`, `DATE`, `TIME_IN`, `TIME_OUT`, `COME_LATE_M`, `COME_LATE_A`, `LEAVE_SOON_M`, `LEAVE_SOON_A`, `COMMENT`) VALUES
(2, '2017-10-31', '08:49', '12:04', '0:49', NULL, NULL, NULL, NULL),
(2, '2017-10-31', '14:53', '17:31', NULL, '01:53', NULL, NULL, NULL),
(3, '2017-10-31', '08:00', '12:04', NULL, NULL, NULL, NULL, NULL),
(3, '2017-10-31', '17:37', NULL, NULL, '04:37', NULL, NULL, NULL),
(4, '2017-10-31', '07:40', NULL, NULL, NULL, NULL, NULL, NULL),
(21, '2017-10-31', '07:59', '13:26', NULL, NULL, NULL, '03:26', NULL),
(21, '2017-10-31', '17:27', NULL, NULL, '04:27', NULL, NULL, NULL),
(22, '2017-10-31', '08:01', '17:27', '0:01', NULL, NULL, NULL, NULL),
(23, '2017-10-31', '07:58', '11:58', NULL, NULL, '0:02', NULL, NULL),
(23, '2017-10-31', '14:31', NULL, NULL, '01:31', NULL, NULL, NULL),
(28, '2017-10-31', '07:48', NULL, NULL, NULL, NULL, NULL, NULL),
(31, '2017-10-31', '07:49', '12:01', NULL, NULL, NULL, NULL, NULL),
(31, '2017-10-31', '12:38', '17:30', NULL, NULL, NULL, NULL, NULL),
(33, '2017-10-31', '07:48', '12:03', NULL, NULL, NULL, NULL, NULL),
(33, '2017-10-31', '17:32', NULL, NULL, '04:32', NULL, NULL, NULL),
(35, '2017-10-31', '08:04', '13:08', '0:04', NULL, NULL, '03:08', NULL),
(35, '2017-10-31', '17:29', NULL, NULL, '04:29', NULL, NULL, NULL),
(38, '2017-10-31', '08:00', '12:02', NULL, NULL, NULL, NULL, NULL),
(38, '2017-10-31', '13:59', '17:37', NULL, '0:59', NULL, NULL, NULL),
(39, '2017-10-31', '07:59', '11:58', NULL, NULL, '0:02', NULL, NULL),
(39, '2017-10-31', '13:40', '15:29', NULL, '0:40', NULL, '01:29', NULL),
(41, '2017-10-31', '07:40', '11:57', NULL, NULL, '0:03', NULL, NULL),
(41, '2017-10-31', '13:26', '19:12', NULL, '0:26', NULL, NULL, NULL),
(42, '2017-10-31', '07:54', '12:59', NULL, NULL, NULL, NULL, NULL),
(42, '2017-10-31', '13:27', '17:55', NULL, '0:27', NULL, NULL, NULL),
(43, '2017-10-31', '09:04', '12:12', '01:04', NULL, NULL, NULL, NULL),
(43, '2017-10-31', '13:35', '18:00', NULL, '0:35', NULL, NULL, NULL),
(44, '2017-10-31', '07:57', '12:01', NULL, NULL, NULL, NULL, NULL),
(44, '2017-10-31', '12:29', '16:32', NULL, NULL, NULL, '0:32', NULL),
(45, '2017-10-31', '07:50', '13:59', NULL, NULL, NULL, '03:01', NULL),
(45, '2017-10-31', '17:33', NULL, NULL, '04:33', NULL, NULL, NULL),
(48, '2017-10-31', '07:48', '18:23', NULL, NULL, NULL, NULL, NULL),
(49, '2017-10-31', '07:58', '12:06', NULL, NULL, NULL, NULL, NULL),
(49, '2017-10-31', '13:44', '17:29', NULL, '0:44', NULL, NULL, NULL),
(50, '2017-10-31', '08:44', '12:01', '0:44', NULL, NULL, NULL, NULL),
(50, '2017-10-31', '13:44', '17:44', NULL, '0:44', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `work_history`
--

CREATE TABLE `idigroup`.`work_history` (
  `EMPLOYEE_ID` bigint(5) NOT NULL,
  `FROM_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TO_DATE` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `TITLE` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `DEPARTMENT` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `COMPANY` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALARY` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `ACHIEVEMENT` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `APPRAISE` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `work_history`
--

INSERT INTO `idigroup`.`work_history` (`EMPLOYEE_ID`, `FROM_DATE`, `TO_DATE`, `TITLE`, `DEPARTMENT`, `COMPANY`, `SALARY`, `ACHIEVEMENT`, `APPRAISE`) VALUES
(1, '2017-08-01', '2017-10-01', 'CTV', 'CNTT', 'IDI', '2.000.000', 'Chưa có', 'Nhận xét ở đây '),
(1, '2017-10-01', '2017-11-01', 'NV', 'KTh', 'IDI', '5.000.000', 'Không', 'Chuyển từ phòng CNTT sang');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `department`
--
ALTER TABLE `idigroup`.`department`
  ADD PRIMARY KEY (`DEPARTMENT_ID`);

--
-- Indexes for table `employee_info`
--
ALTER TABLE `idigroup`.`employee_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`),
  ADD UNIQUE KEY `LOGIN_ACCOUNT` (`LOGIN_ACCOUNT`);

--
-- Indexes for table `insurance`
--
ALTER TABLE `idigroup`.`insurance`
  ADD PRIMARY KEY (`SOCIAL_INSU_NO`,`HEALTH_INSU_NO`) USING BTREE,
  ADD UNIQUE KEY `HEALTH_INSU_NO` (`HEALTH_INSU_NO`),
  ADD UNIQUE KEY `SOCIAL_INSU_NO` (`SOCIAL_INSU_NO`);

--
-- Indexes for table `job_title`
--
ALTER TABLE `idigroup`.`job_title`
  ADD PRIMARY KEY (`TITLE_ID`);

--
-- Indexes for table `leave_info`
--
ALTER TABLE `idigroup`.`leave_info`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`DATE`,`LEAVE_TYPE`) USING BTREE;

--
-- Indexes for table `leave_report`
--
ALTER TABLE `idigroup`.`leave_report`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`YEAR`) USING BTREE;

--
-- Indexes for table `leave_type`
--
ALTER TABLE `idigroup`.`leave_type`
  ADD PRIMARY KEY (`LEAVE_ID`);

--
-- Indexes for table `process_insurance`
--
ALTER TABLE `idigroup`.`process_insurance`
  ADD PRIMARY KEY (`SOCIAL_INSU_NO`,`FROM_DATE`) USING BTREE;

--
-- Indexes for table `timekeeping`
--
ALTER TABLE `idigroup`.`timekeeping`
  ADD PRIMARY KEY (`DATE`,`EMPLOYEE_ID`,`TIME_IN`) USING BTREE;

--
-- Indexes for table `work_history`
--
ALTER TABLE `idigroup`.`work_history`
  ADD PRIMARY KEY (`EMPLOYEE_ID`,`FROM_DATE`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee_info`
--
ALTER TABLE `idigroup`.`employee_info`
  MODIFY `EMPLOYEE_ID` bigint(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
