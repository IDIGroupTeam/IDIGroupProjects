SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `KHACH_HANG` ( `MA_KH` INT NOT NULL AUTO_INCREMENT , `KH_KH` VARCHAR(255) NOT NULL, `TEN_KH` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_KH`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `KHACH_HANG` (`MA_KH`, `TEN_KH`, `MA_THUE`, `DIA_CHI`, `EMAIL`, `SDT`, `WEBSITE`) VALUES
(1, 'Khách vãng lai', '', '', '', '', '');

CREATE TABLE NHA_CUNG_CAP ( `MA_NCC` INT NOT NULL AUTO_INCREMENT , `KH_NCC` VARCHAR(255) NOT NULL, `TEN_NCC` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_NCC`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `TAI_KHOAN_DANH_MUC` ( `MA_TK` VARCHAR(10) NOT NULL , `TEN_TK` VARCHAR(255) NOT NULL , `MA_TK_CHA` VARCHAR(10) NULL , `SO_DU` TINYINT(1) NOT NULL DEFAULT -1, `MA_TK_NH` INT NULL, `LUONG_TINH` BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY (`MA_TK`)) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
-- Theo thông tư 133 gốc
INSERT INTO `TAI_KHOAN_DANH_MUC` (`MA_TK`, `TEN_TK`, `MA_TK_CHA`, `SO_DU`) VALUES
('111', 'Tiền mặt', NULL, -1),
('1111', 'Tiền Việt Nam', '111', -1),
('1112', 'Ngoại tệ', '111', -1),
('112', 'Tiền gửi ngân hàng', NULL, -1),
('1121', 'Tiền Việt Nam', '112', -1),
('1122', 'Ngoại tệ', '112', -1),
('121', 'Chứng khoán kinh doanh', NULL, -1),
('1211', 'Cổ phiếu', '121', -1),
('1212', 'Trái phiếu', '121', -1),
('128', 'Đầu tư nắm giữ đến ngày đáo hạn', NULL, -1),
('1281', 'Tiền gửi có kỳ hạn', '128', -1),
('1288', 'Các khoản đầu tư khác nắm giữ đến ngày đáo hạn', '128', -1),
('131', 'Phải thu khách hàng', NULL, -1),
('133', 'Thuế GTGT được khấu trừ', NULL, -1),
('1331', 'Thuế GTGT được khấu trừ của h/hóa, dịch vụ', '133', -1),
('1332', 'Thuế GTGT được khấu trừ của TSCĐ', '133', -1),
('136', 'Phải thu nội bộ', NULL, -1),
('1361', 'Vốn kinh doanh ở các đơn vị trực thuộc', '136', -1),
('1368', 'Phải thu nội bộ khác', '136', -1),
('138', 'Phải thu khác', NULL, -1),
('1381', 'Tài sản thiếu chờ xử lý', '138', -1),
('1386', 'Cầm cố, thế chấp, ký quỹ, ký cược', '138', -1),
('1388', 'Phải thu khác', '138', -1),
('141', 'Tạm ứng', NULL, -1),
('151', 'Hàng mua đang đi đường', NULL, -1),
('152', 'Nguyên liệu, vật liệu', NULL, -1),
('153', 'Công cụ, dụng cụ', NULL, -1),
('154', 'Chi phí sản xuất, kinh doanh dở dang', NULL, -1),
('155', 'Thành phẩm', NULL, -1),
('156', 'Hàng hóa', NULL, -1),
('157', 'Hàng gửi đi bán', NULL, -1),
('211', 'Tài sản cố định', NULL, -1),
('2111', 'TSCĐ hữu hình', '211', -1),
('2112', 'TSCĐ thuê tài chính', '211', -1),
('2113', 'TSCĐ vô hình', '211', -1),
('214', 'Hao mòn tài sản cố định', NULL, -1),
('2141', 'Hao mòn TSCĐ hữu hình', '214', -1),
('2142', 'Hao mòn TSCĐ thuê tài chính', '214', -1),
('2143', 'Hao mòn TSCĐ vô hình', '214', -1),
('2147', 'Hao mòn bất động sản đầu tư', '214', -1),
('217', 'Bất động sản đầu tư', NULL, -1),
('228', 'Đầu tư góp vốn vào đơn vị khác', NULL, -1),
('2281', 'Đầu tư vào công ty liên doanh, liên kết', '228', -1),
('2288', 'Đầu tư khác', '228', -1),
('229', 'Dự phòng tổn thất tài sản', NULL, -1),
('2291', 'Dự phòng giảm giá chứng khoán kinh doanh', '229', -1),
('2292', 'Dự phòng tổn thất đầu tư vào đơn vị khác', '229', -1),
('2293', 'Dự phòng phải thu khó đòi', '229', -1),
('2294', 'Dự phòng giảm giá hàng tồn kho', '229', -1),
('241', 'Xây dựng cơ bản dở dang', NULL, -1),
('2411', 'Mua sắm TSCĐ', '241', -1),
('2412', 'Xây dựng cơ bản', '241', -1),
('2413', 'Sữa chữa lớn TSCĐ', '241', -1),
('242', 'Chi phí trả trước', NULL, -1),
('331', 'Phải trả cho người bán', NULL, 1),
('333', 'Thuế và các khoản phải nộp nhà nước', NULL, 1),
('3331', 'Thuế GTGT phải nộp', '333', 1),
('33311', 'Thuế GTGT đầu ra', '33311', 1),
('33312', 'Thuế GTGT hàng nhập khẩu', '33312', 1),
('3332', 'Thuế tiêu thụ đặc biệt', '333', 1),
('3333', 'Thuế xuất nhập khẩu', '333', 1),
('3334', 'Thuế thu nhập doanh nghiệp', '333', 1),
('3335', 'Thuế thu nhập cá nhân', '333', 1),
('3336', 'Thuế tài nguyên', '333', 1),
('3337', 'Thuế nhà đất, tiền thuê đất', '333', 1),
('3338', 'Thuế bảo vệ môi trường và các loại thuế khác', '333', 1),
('33381', 'Thuế bảo vệ môi trường', '3338', 1),
('33382', 'Các loại thuế khác', '3338', 1),
('3339', 'Phí, lệ phí và các khoản phải nộp khác', '333', 1),
('334', 'Phải trả người lao động', NULL, 1),
('335', 'Chi phí phải trả', NULL, 1),
('336', 'Phải trả nội bộ', NULL, 1),
('3361', 'Phải trả nội bộ về vốn kinh doanh', '336', 1),
('3368', 'Phải trả nội bộ khác', '336', 1),
('338', 'Phải trả, phải nộp khác', NULL, 1),
('3381', 'Tài sản thừa chờ xử lý', '338', 1),
('3382', 'Kinh phí công đoàn', '338', 1),
('3383', 'Bảo hiểm xã hội', '338', 1),
('3384', 'Bảo hiểm y tế', '338', 1),
('3385', 'Bảo hiểm thất nghiệp', '338', 1),
('3386', 'Nhận ký quý, ký cược', '338', 1),
('3387', 'Doanh thu chưa thực hiện', '338', 1),
('3388', 'Phải trả phải nộp khác', '338', 1),
('341', 'Vay và nợ thuê tài chính', NULL, 1),
('3411', 'Các khoản đi vay', '341', 1),
('3412', 'Nợ thuê tài chính', '341', 1),
('352', 'Dự phòng phải trả', NULL, 1),
('3521', 'Dự phòng bảo hành sản phẩm hàng hóa', '352', 1),
('3522', 'Dự phòng bảo hành công trình xây dựng', '352', 1),
('3524', 'Dự phòng phải trả khác', '352', 1),
('353', 'Qũy khen thưởng, phúc lợi', NULL, 1),
('3531', 'Quỹ khen thưởng', '353', 1),
('3532', 'Quỹ phúc lợi', '353', 1),
('3533', 'Quỹ phúc lợi đã hình thành TSCD', '353', 1),
('3534', 'Quỹ thưởng ban điều hành Cty', '353', 1),
('356', 'Quỹ phát triển khoa học và công nghệ', NULL, 1),
('3561', 'Quỹ phát triển khoa học và công nghệ', '356', 1),
('3562', 'Quỹ phát triển khoa học và công nghệ đã hình thành TSCD', '356', 1),
('411', 'Vốn đầu tư của chủ sở hữu', NULL, 1),
('4111', 'Vốn góp của chủ sở hữu', '411', 1),
('41111', 'Cổ phiếu phổ thông có quyền biểu quyết', '4111', 1),
('41112', 'Cổ phiếu ưu đãi', '4111', 1),
('4112', 'Thặng dư vốn cổ phần', '411', 1),
('4118', 'Vốn khác', '411', 1),
('413', 'Chênh lệch tỷ giá hối đoái', NULL, 1),
('418', 'Các quỹ thuộc vốn chủ sở hữu', NULL, 1),
('419', 'Cổ phiếu quỹ', NULL, 1),
('421', 'Lợi nhuận sau thuế chưa phân phối', NULL, 1),
('4211', 'Lợi nhuận sau thuế chưa phân phối năm trước', '421', 1),
('4212', 'Lợi nhuận sau thuế chưa phân phối năm nay', '421', 1),
('511', 'Doanh thu bán hàng và cung cấp dịch vụ', NULL, 1),
('5111', 'Doanh thu bán hàng hóa', '511', 1),
('5112', 'Doanh thu bán các thành phẩm', '511', 1),
('5113', 'Doanh thu cung cấp dịch vụ', '511', 1),
('5118', 'Doanh thu khác', '511', 1),
('515', 'Doanh thu hoạt động tài chính', NULL, 1),
('521', 'Các khoản giảm trừ doanh thu', NULL, 1),
('5211', 'Chiết khấu thương mại', '521', 1),
('5212', 'Hàng bán bị trả lại', '521', 1),
('5213', 'Giảm giá hàng bán', '521', 1),
('611', 'Mua hàng', NULL, -1),
('631', 'Giá thành sản xuất', NULL, -1),
('632', 'Giá vốn hàng bán', NULL, -1),
('635', 'Chi phí tài chính', NULL, -1),
('642', 'Chi phí quản lý kinh doanh', NULL, -1),
('6421', 'Chi phí bán hàng', '642', -1),
('6422', 'Chi phí quản lý doanh nghiệp', '642', -1),
('711', 'Thu hập khác', NULL, -1),
('811', 'Chi phí khác', NULL, -1),
('821', 'Chi phí thuế thu nhập doanh nghiệp', NULL, -1),
('911', 'Xác định kết quả kinh doanh', NULL, -1);
UPDATE `TAI_KHOAN_DANH_MUC` SET `LUONG_TINH`=1 WHERE MA_TK LIKE ('131%') OR MA_TK LIKE ('138%') OR MA_TK LIKE ('331%') OR MA_TK LIKE ('333%') OR MA_TK LIKE ('334%') OR MA_TK LIKE ('338%') OR MA_TK LIKE ('421%');

CREATE TABLE `CHUNG_TU` (`MA_CT` int(11) NOT NULL AUTO_INCREMENT, `SO_CT` int(11) DEFAULT NULL, `LOAI_CT` VARCHAR(100) DEFAULT NULL, `TINH_CHAT` TINYINT NOT NULL DEFAULT '0', `NGAY_LAP` date NULL, `NGAY_HT` date NOT NULL, `LY_DO` varchar(255) NOT NULL, `LOAI_TIEN` varchar(10) NOT NULL DEFAULT 'VND', `TY_GIA` DOUBLE NOT NULL DEFAULT '1', `KEM_THEO` int(11) DEFAULT NULL, `MA_DT` int(11) NULL, `LOAI_DT` TINYINT(1) NULL, `NGUOI_NOP` varchar(255) NULL, `MA_KHO` INT NULL, `NGAY_TAO` DATE NULL, `NGAY_SUA` DATE NULL, `NGAY_TT` DATE NULL, `TRANG_THAI` TINYINT NULL, PRIMARY KEY (`MA_CT`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `NGOAI_TE` ( `MA_NT` VARCHAR(10) NOT NULL, `TEN_NT` VARCHAR(255) NOT NULL, `MUA_TM` DOUBLE NOT NULL, `MUA_CK` DOUBLE NULL, `BAN_RA` DOUBLE NOT NULL, `THEO_DOI` TINYINT(1) NOT NULL DEFAULT '1', PRIMARY KEY (`MA_NT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `NGOAI_TE` (`MA_NT`, `TEN_NT`, `MUA_TM`, `MUA_CK`, `BAN_RA`) VALUES
('EUR', 'EURO', 26633, 26714, 26952),
('JPY', 'JAPANESE YEN', 199, 201, 203),
('USD', 'US DOLLAR', 22680, 22680, 22750),
('VND', 'Việt Nam Đồng', 1, 1, 1),
('VANG', 'Vàng bạc đá quý', 36300, 36300, 36500);

CREATE TABLE `CDKT_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `MA_TK_GOC` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
--  Theo mẫu B01a
INSERT INTO `CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `MA_TK_GOC`, `SO_DU`) VALUES
('110', '111', '111', -1),('110', '112', '112', -1),('110', '1281', '1281', -1),('110', '1288', '1288', -1),('121', '121', '121', -1),('122', '1281', '1281', -1),('122', '1288', '1288', -1),('123', '228', '228', -1),('124', '2291', '2291', 1),('124', '2292', '2292', 1),('131', '131', '131', -1),('132', '331', '331', -1),('133', '1361', '1361', -1),('134', '1288', '1288', -1),('134', '1368', '1368', -1),('134', '1386', '1386', -1),('134', '1388', '1388', -1),('134', '334', '334', -1),('134', '338', '338', -1),('134', '141', '141', -1),('135', '1381', '1381', -1),('136', '2293', '2293', 1),('141', '151', '151', -1),('141', '152', '152', -1),('141', '153', '153', -1),('141', '154', '154', -1),('141', '155', '155', -1),('141', '156', '156', -1),('141', '157', '157', -1),('142', '2294', '2294', 1),('151', '211', '211', -1),('152', '2141', '2141', 1),('152', '2142', '2142', 1),('152', '2143', '2143', 1),('161', '217', '217', -1),('162', '2147', '2147', 1),('170', '241', '241', -1),('181', '133', '133', -1),('182', '242', '242', -1),('182', '333', '333', -1),('311', '331', '331', 1),('312', '131', '131', 1),('313', '333', '333', 1),('314', '334', '334', 1),('315', '335', '335', 1),('315', '3368', '3368', 1),('315', '338', '338', 1),('315', '1388', '1388', 1),('316', '341', '341', 1),('316', '4111', '4111', 1),('317', '3361', '3361', 1),('318', '352', '352', 1),('319', '353', '353', 1),('320', '356', '356', 1),('411', '4111', '4111', 1),('412', '4112', '4112', 1),('413', '4118', '4118', 1),('414', '419', '419', -1),('416', '418', '418', -1),('417', '421', '421', -1);

CREATE TABLE `KY_KE_TOAN` ( `MA_KKT` INT NOT NULL AUTO_INCREMENT , `TEN_KKT` VARCHAR(255) NOT NULL , `BAT_DAU` DATE NOT NULL , `KET_THUC` DATE NOT NULL , `TRANG_THAI` TINYINT NOT NULL , `MAC_DINH` TINYINT NOT NULL, PRIMARY KEY (`MA_KKT`)) ENGINE = InnoDB;
CREATE TABLE `KY_KE_TOAN_SO_DU` (`MA_KKT` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL , `MA_DT` INT NOT NULL, `LOAI_DT` TINYINT(1) NOT NULL, `MA_HH` INT NOT NULL, `MA_KHO` INT NOT NULL, `NO_DAU_KY` DOUBLE NOT NULL DEFAULT '0', `NO_DAU_KY_NT` DOUBLE NOT NULL DEFAULT '0', `CO_DAU_KY` DOUBLE NOT NULL DEFAULT '0', `CO_DAU_KY_NT` DOUBLE NOT NULL DEFAULT '0', `NO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0', `NO_CUOI_KY_NT` DOUBLE NOT NULL DEFAULT '0', `CO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0', `CO_CUOI_KY_NT` DOUBLE NOT NULL DEFAULT '0', `LOAI_TIEN` VARCHAR(10) NOT NULL DEFAULT 'VND', SO_LUONG` DOUBLE NOT NULL DEFAULT '0', `MA_DV` INT NOT NULL DEFAULT '0', PRIMARY KEY (`MA_KKT`,`MA_TK`,`MA_DT`,`LOAI_DT`,`MA_HH`,`MA_KHO`)) ENGINE = InnoDB;

CREATE TABLE `NGHIEP_VU_KE_TOAN` ( `MA_NVKT` INT NOT NULL AUTO_INCREMENT , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` DOUBLE NOT NULL, `SO_DU` TINYINT(1) NOT NULL DEFAULT '-1', `LY_DO` VARCHAR(255) NULL, `ASSET_CODE` VARCHAR(10) NULL, `MA_DT` INT NULL, `LOAI_DT` TINYINT(1) NULL, PRIMARY KEY (`MA_NVKT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `NGHIEP_VU_KE_TOAN` (`MA_NVKT`, `MA_TK`, `SO_TIEN`, `SO_DU`, `LY_DO`, `ASSET_CODE`) VALUES ('1', '0', '0', '-1', NULL, NULL);
CREATE TABLE `CHUNG_TU_HANG_HOA` ( `MA_CT` INT NOT NULL , `MA_HH` INT NOT NULL ,  `MA_KHO` INT NULL, `SO_LUONG_CT` DOUBLE NULL, `SO_LUONG_TN` DOUBLE NULL, `DON_GIA` DOUBLE NULL, `MA_GIA_KHO` INT NOT NULL, `CHIEU` TINYINT NOT NULL DEFAULT '1', PRIMARY KEY(`MA_CT`, `MA_HH`, `MA_KHO`, `MA_GIA_KHO`, `CHIEU`)) ENGINE = InnoDB;
CREATE TABLE `CHUNG_TU_NVKT` ( `MA_CT` INT NOT NULL , `MA_HH` INT NULL , `MA_KC` INT NULL, `MA_NVKT` INT NOT NULL, `NHOM_DK` TINYINT NOT NULL DEFAULT '0', `LOAI_TK` TINYINT NULL, `THUE_SUAT` DOUBLE NULL ) ENGINE = InnoDB;
CREATE TABLE `BUT_TOAN_KET_CHUYEN` ( `MA_KC` INT NOT NULL AUTO_INCREMENT , `TEN_KC` VARCHAR(255) NOT NULL , `MA_TK_NO` VARCHAR(10) NOT NULL , `MA_TK_CO` VARCHAR(10) NOT NULL , `CONG_THUC` VARCHAR(255) NOT NULL, `MO_TA` VARCHAR(500) NULL, `THU_TU` TINYINT NOT NULL, `LOAI_KC` TINYINT NOT NULL, PRIMARY KEY (`MA_KC`)) ENGINE = InnoDB;
INSERT INTO `BUT_TOAN_KET_CHUYEN` (`MA_KC`, `TEN_KC`, `MA_TK_NO`, `MA_TK_CO`, `CONG_THUC`, `MO_TA`, `THU_TU`, `LOAI_KC`) VALUES
(1, 'Bút toán kết chuyển thuế', '33311', '1331', '1331.NO', '', 1, 1),
(2, 'Bút toán nộp tiền thuế', '33311', '1111', '33311.CO', '', 2, 1),
(3, 'Kết chuyển doanh thu thuần trong kỳ', '511', '911', '511.CO', '', 4, 1),
(4, 'Kết chuyển doanh thu hoạt động tài chính', '515', '911', '515.CO', '', 5, 1),
(5, 'Kết chuyển chi phí tài chính', '911', '635', '635.NO', '', 6, 1),
(6, 'Kết chuyển giá vốn xuất bán trong kì', '911', '632', '632.NO', '', 7, 1),
(7, 'Kết chuyển chi phí bán hàng', '911', '642', '642.NO', '', 9, 1),
(8, 'Kết chuyển chi phí khác', '911', '811', '811.NO', '', 10, 1),
(9, 'Kết chuyển thu nhập khác', '711', '911', '711.CO', '', 11, 1),
(10, 'Kết chuyển chi phí thuế thu nhập doanh nghiệp hiện hành', '911', '8211', '8211.NO', '', 12, 1),
(11, 'Kết chuyển số chênh lệch chi phí thuế thu nhập hoãn lại', '911', '8212', '8212.NO-8212.CO', '', 13, 1),
(12, 'Kết chuyển kết quả hoạt động kinh doanh trong kỳ vào lợi nhuận sau thuế chưa phân phối', '911', '4212', '911.CO-911.NO', '', 14, 1);

-- Query để sinh bảng kết quả sản xuất kinh doanh
CREATE TABLE `KQHDKD_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
INSERT INTO `KQHDKD_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES 
('01', '511', 1), 
('02', '511', -1), 
('11', '632', 1), 
('21', '515', -1), 
('22', '635', 1), 
-- ('23', '635', -1), 
('24', '642', 1), 
('31', '711', -1), 
('32', '811', 1), 
('51', '821', 1);

CREATE TABLE `CASH_FLOW_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, `MA_TK_DU` VARCHAR(10) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) ) ENGINE = InnoDB;
INSERT INTO `CASH_FLOW_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) VALUES 
('01', '111', -1, '511'), 
('01', '111', -1, '131'),
('01', '111', -1, '515'),
('01', '111', -1, '121'),
('01', '112', -1, '511'), 
('01', '112', -1, '131'),
('01', '112', -1, '515'),
('01', '112', -1, '121'),
('02', '111', 1, '331'),-- GHI SỐ ÂM -- CÓ CẦN THÊM CÁC TK 151->158 ?
('02', '112', 1, '331'),-- GHI SỐ ÂM -- CÓ CẦN THÊM CÁC TK 151->158 ?
('03', '111', 1, '334'),-- GHI SỐ ÂM
('03', '112', 1, '334'),-- GHI SỐ ÂM
('04', '111', 1, '335'),-- GHI SỐ ÂM
('04', '111', 1, '635'),-- GHI SỐ ÂM
('04', '111', 1, '242'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('04', '112', 1, '335'),-- GHI SỐ ÂM
('04', '112', 1, '635'),-- GHI SỐ ÂM
('04', '112', 1, '242'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('05', '111', 1, '3334'),-- GHI SỐ ÂM
('05', '112', 1, '3334'),-- GHI SỐ ÂM
('06', '111', -1, '711'), 
('06', '111', -1, '133'),
('06', '111', -1, '141'),
('06', '111', -1, '138'), -- VÀ CÁC TK KHÁC
('06', '112', -1, '711'), 
('06', '112', -1, '133'),
('06', '112', -1, '141'),
('06', '112', -1, '138'), -- VÀ CÁC TK KHÁC
('07', '111', 1, '811'),-- GHI SỐ ÂM
('07', '111', 1, '138'),-- GHI SỐ ÂM
('07', '111', 1, '333'),-- GHI SỐ ÂM
('07', '111', 1, '338'),-- GHI SỐ ÂM
('07', '111', 1, '352'),-- GHI SỐ ÂM
('07', '111', 1, '353'),-- GHI SỐ ÂM
('07', '111', 1, '356'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('07', '112', 1, '811'),-- GHI SỐ ÂM
('07', '112', 1, '138'),-- GHI SỐ ÂM
('07', '112', 1, '333'),-- GHI SỐ ÂM
('07', '112', 1, '338'),-- GHI SỐ ÂM
('07', '112', 1, '352'),-- GHI SỐ ÂM
('07', '112', 1, '353'),-- GHI SỐ ÂM
('07', '112', 1, '356'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('21', '111', 1, '211'),-- GHI SỐ ÂM
('21', '111', 1, '217'),-- GHI SỐ ÂM
('21', '111', 1, '241'),-- GHI SỐ ÂM
('21', '112', 1, '211'),-- GHI SỐ ÂM
('21', '112', 1, '217'),-- GHI SỐ ÂM
('21', '112', 1, '241'),-- GHI SỐ ÂM
('21', '3411', 1, '211'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '3411', 1, '217'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '3411', 1, '241'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '211'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '217'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '241'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('22', '111', -1, '711'), -- THU
('22', '111', -1, '5118'), -- THU
('22', '111', -1, '131'), -- THU
('22', '112', -1, '711'), -- THU
('22', '112', -1, '5118'), -- THU
('22', '112', -1, '131'), -- THU
('22', '111', 1, '632'), -- CHI
('22', '111', 1, '811'), -- CHI
('22', '112', 1, '632'), -- CHI
('22', '112', 1, '811'), -- CHI
('23', '111', 1, '128'),-- GHI SỐ ÂM
('23', '111', 1, '228'),-- GHI SỐ ÂM
('23', '111', 1, '331'),-- GHI SỐ ÂM
('23', '112', 1, '128'),-- GHI SỐ ÂM
('23', '112', 1, '228'),-- GHI SỐ ÂM
('23', '112', 1, '331'),-- GHI SỐ ÂM
('24', '111', -1, '128'),
('24', '111', -1, '228'),
('24', '111', -1, '131'),
('24', '112', -1, '128'),
('24', '112', -1, '228'),
('24', '112', -1, '131'),
('25', '111', 1, '515'),-- GHI SỐ ÂM
('25', '112', 1, '515'),-- GHI SỐ ÂM
('31', '111', -1, '411'),
('31', '112', -1, '411'),
('32', '111', 1, '411'),-- GHI SỐ ÂM
('32', '111', 1, '419'),-- GHI SỐ ÂM
('32', '112', 1, '411'),-- GHI SỐ ÂM
('32', '112', 1, '419'),-- GHI SỐ ÂM
('33', '111', -1, '3411'),
('33', '111', -1, '4111'), -- VÀ CÁC TK KHÁC
('33', '112', -1, '3411'),
('33', '112', -1, '4111'), -- VÀ CÁC TK KHÁC
('34', '111', 1, '341'),-- GHI SỐ ÂM
('34', '111', 1, '4111'),-- GHI SỐ ÂM
('34', '112', 1, '341'),-- GHI SỐ ÂM
('34', '112', 1, '4111'),-- GHI SỐ ÂM
('35', '111', 1, '421'),-- GHI SỐ ÂM
('35', '111', 1, '338'),-- GHI SỐ ÂM
('35', '112', 1, '421'),-- GHI SỐ ÂM
('35', '112', 1, '338'),-- GHI SỐ ÂM
('61', '128', -1, '413'),
('61', '111', 1, '413'),-- GHI SỐ ÂM
('61', '112', 1, '413');-- GHI SỐ ÂM

CREATE TABLE `BAO_CAO_TAI_CHINH` ( `MA_BCTC` INT NOT NULL AUTO_INCREMENT , `TIEU_DE` VARCHAR(255) NOT NULL , `LOAI_TG` INT NOT NULL , `BAT_DAU` DATE NOT NULL , `KET_THUC` DATE NOT NULL , `NGUOI_LAP` VARCHAR(255) NOT NULL , `GIAM_DOC` VARCHAR(255) NOT NULL , `NGAY_LAP` DATE NOT NULL , `MA_KKT` INT NOT NULL, PRIMARY KEY (`MA_BCTC`)) ENGINE = InnoDB;
CREATE TABLE `BAO_CAO_TAI_CHINH_CON` ( `MA_BCTC_CON` INT NOT NULL AUTO_INCREMENT, `MA_BCTC` INT NOT NULL , `LOAI_BCTC` INT NOT NULL , PRIMARY KEY (`MA_BCTC_CON`)) ENGINE = InnoDB;
CREATE TABLE `BAO_CAO_TAI_CHINH_CHI_TIET` ( `MA_BCTC_CON` INT NOT NULL , `ASSET_CODE` VARCHAR(10) NOT NULL , `GIA_TRI` DOUBLE NOT NULL , `GIA_TRI_KY_TRUOC` DOUBLE NOT NULL, PRIMARY KEY (`MA_BCTC_CON`, `ASSET_CODE`)) ENGINE = InnoDB;
CREATE TABLE `BAO_CAO_TAI_CHINH_CDPS_CHI_TIET` ( `MA_BCTC_CON` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL , `PHAT_SINH` DOUBLE NOT NULL , `DAU_KY` DOUBLE NOT NULL, PRIMARY KEY (`MA_BCTC_CON`, `MA_TK`)) ENGINE = InnoDB;
CREATE TABLE `BAO_CAO_TAI_CHINH_KY_KPI` ( `MA_BCTC` INT NOT NULL , `MA_KY_KPI` INT NOT NULL , PRIMARY KEY (`MA_BCTC`, `MA_KY_KPI`)) ENGINE = InnoDB;
