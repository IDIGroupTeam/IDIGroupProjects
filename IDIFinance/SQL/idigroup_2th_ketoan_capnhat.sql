CREATE TABLE `idigroup`.`KY_KE_TOAN` ( `MA_KKT` INT NOT NULL AUTO_INCREMENT , `TEN_KKT` VARCHAR(255) NOT NULL , `BAT_DAU` DATE NOT NULL , `KET_THUC` DATE NOT NULL , `TRANG_THAI` TINYINT NOT NULL , `MAC_DINH` TINYINT NOT NULL, PRIMARY KEY (`MA_KKT`)) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`KY_KE_TOAN_SO_DU` (`MA_KKT` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL , `MA_DT` INT NULL, `NO_DAU_KY` DOUBLE NULL, `CO_DAU_KY` DOUBLE NULL, `NO_CUOI_KY` DOUBLE NULL, `CO_CUOI_KY` DOUBLE NULL, PRIMARY KEY (`MA_KKT`, `MA_TK`)) ENGINE = InnoDB;

CREATE TABLE `idigroup`.`NGHIEP_VU_KE_TOAN` ( `MA_NVKT` INT NOT NULL AUTO_INCREMENT , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` double NOT NULL, `SO_DU` TINYINT(1) NOT NULL DEFAULT '-1', `LY_DO` VARCHAR(255) NULL, `ASSET_CODE` VARCHAR(10) NULL, PRIMARY KEY (`MA_NVKT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`CHUNG_TU_HANG_HOA` ( `MA_CT` INT NOT NULL , `MA_HH` INT NOT NULL ,  `MA_KHO` INT NULL, `SO_LUONG_CT` DOUBLE NULL, `SO_LUONG_TN` DOUBLE NULL, `DON_GIA` DOUBLE NULL, `MA_GIA_KHO` INT NOT NULL, `CHIEU` TINYINT NOT NULL DEFAULT '1', PRIMARY KEY( `MA_CT`, `MA_HH`)) ENGINE = InnoDB;

-- Bảng đối tác có thể thay thế bảng khách hàng/nhà cung cấp, nên bảng khach_hang và nha_cung_cap sau này có thể xóa đi
-- CREATE TABLE `idigroup`.DOI_TAC ( `MA_DT` INT NOT NULL AUTO_INCREMENT , `TEN_DT` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_DT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
-- INSERT INTO `doi_tac` (`MA_DT`, `TEN_DT`, `MA_THUE`, `DIA_CHI`, `EMAIL`, `SDT`, `WEBSITE`) VALUES(1, 'Khách vãng lai', '', '', '', '', '');

ALTER TABLE `CHUNG_TU` ADD `MA_KHO` INT NULL AFTER `NGUOI_NOP`;
ALTER TABLE `CHUNG_TU` ADD `NGAY_TAO` DATE NULL AFTER `MA_KHO`, ADD `NGAY_SUA` DATE NULL AFTER `NGAY_TAO`;
ALTER TABLE `CHUNG_TU` ADD `TINH_CHAT` TINYINT NOT NULL DEFAULT '0' AFTER `LOAI_CT`;
ALTER TABLE `CHUNG_TU` ADD `TRANG_THAI` TINYINT NULL AFTER `NGAY_SUA`;
ALTER TABLE `CHUNG_TU` ADD `NGAY_TT` DATE NULL AFTER `NGAY_SUA`;
ALTER TABLE `CHUNG_TU` CHANGE `NGAY_LAP` `NGAY_LAP` DATE NULL;

CREATE TABLE `idigroup`.`CHUNG_TU_NVKT` ( `MA_CT` INT NOT NULL , `MA_HH` INT NULL , `MA_NVKT` INT NOT NULL, `LOAI_TK` TINYINT NULL, `THUE_SUAT` DOUBLE NULL ) ENGINE = InnoDB;
ALTER TABLE `CHUNG_TU_NVKT` ADD `NHOM_DK` TINYINT NOT NULL DEFAULT '0' AFTER `MA_NVKT`;
ALTER TABLE `CHUNG_TU_NVKT` ADD `MA_KC` INT NULL AFTER `MA_HH`;

-- Cập nhật tài khoản lưỡng tính trong tai_khoan_danh_muc
ALTER TABLE `TAI_KHOAN_DANH_MUC` ADD `MA_TK_NH` INT NULL AFTER `SO_DU`;
ALTER TABLE `TAI_KHOAN_DANH_MUC` ADD `LUONG_TINH` BOOLEAN NOT NULL DEFAULT FALSE AFTER `MA_TK_NH`;
UPDATE `idigroup`.`TAI_KHOAN_DANH_MUC` SET `LUONG_TINH`=1 WHERE MA_TK LIKE ('131%') OR MA_TK LIKE ('138%') OR MA_TK LIKE ('331%') OR MA_TK LIKE ('333%') OR MA_TK LIKE ('334%') OR MA_TK LIKE ('338%') OR MA_TK LIKE ('421%')

CREATE TABLE `idigroup`.`BUT_TOAN_KET_CHUYEN` ( `MA_KC` INT NOT NULL AUTO_INCREMENT , `TEN_KC` VARCHAR(255) NOT NULL , `MA_TK_NO` VARCHAR(10) NOT NULL , `MA_TK_CO` INT VARCHAR(10) NOT NULL , `CONG_THUC` VARCHAR(255) NOT NULL, `MO_TA` VARCHAR(500) NULL, `THU_TU` TINYINT NOT NULL, `LOAI_KC` TINYINT NOT NULL, PRIMARY KEY (`MA_KC`)) ENGINE = InnoDB;
INSERT INTO `BUT_TOAN_KET_CHUYEN` (`MA_KC`, `TEN_KC`, `MA_TK_NO`, `MA_TK_CO`, `CONG_THUC`, `MO_TA`, `THU_TU`, `LOAI_KC`) VALUES
(1, 'Bút toán kết chuyển thuế', '33311 ', '1331', '1331.NO', '', 1, 1),
(2, 'Bút toán nộp tiền thuế', '33311 ', '1111', '33311.CO', '', 2, 1),
(3, 'Kết chuyển các khoản giảm trừ doanh thu', '511', '521', '521.NO', '', 3, 1),
(4, 'Kết chuyển doanh thu thuần trong kỳ', '511', '911', '511.CO', '', 4, 1),
(5, 'Kết chuyển doanh thu hoạt động tài chính', '515', '911', '515.CO', '', 5, 1),
(6, 'Kết chuyển chi phí tài chính', '911', '635', '635.NO', '', 6, 1),
(7, 'Kết chuyển giá vốn xuất bán trong kì', '911', '632', '632.NO', '', 7, 1),
(8, 'Kết chuyển chi phí bán hàng', '911', '642', '642.NO', '', 9, 1),
(9, 'Kết chuyển chi phí khác', '911', '811', '811.NO', '', 10, 1),
(10, 'Kết chuyển thu nhập khác', '711', '911', '711.CO', '', 11, 1),
(11, 'Kết chuyển chi phí thuế thu nhập doanh nghiệp hiện hành', '911', '8211', '8211.NO', '', 12, 1),
(12, 'Kết chuyển số chênh lệch chi phí thuế thu nhập hoãn lại', '911', '8212', '8212.NO-8212.CO', '', 13, 1),
(13, 'Kết chuyển chi phí bán hàng', '911', '641', '641.NO', '', 8, 1),
(14, 'Kết chuyển kết quả hoạt động kinh doanh trong kỳ vào lợi nhuận sau thuế chưa phân phối', '911', '4212', '911.CO-911.NO', '', 14, 1);

DROP TABLE `CHUNG_TU_TAI_KHOAN`

-- Query để sinh bảng kết quả sản xuất kinh doanh
CREATE TABLE `idigroup`.`KQHDKD_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`KQHDKD_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES 
('01', '511', 1), 
('02', '521', 1), 
('11', '632', 1), 
('21', '515', -1), 
('22', '635', 1), 
-- ('23', '635', -1), 
('25', '641', 1), 
('26', '642', 1), 
('31', '711', -1), 
('32', '811', 1), 
('51', '8211', 1), 
('52', '8212', 1)





