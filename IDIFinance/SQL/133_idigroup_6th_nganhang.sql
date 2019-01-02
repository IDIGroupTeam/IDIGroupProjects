SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `NGAN_HANG` ( `MA_NH` INT NOT NULL AUTO_INCREMENT , `TEN_VT` VARCHAR(50) NOT NULL , `TEN_DD` VARCHAR(255) NOT NULL , `TEN_TA` VARCHAR(255) NULL , `DIA_CHI_HSC` VARCHAR(255) NULL , `BIEU_TUONG` VARCHAR(255) NULL , `MO_TA` VARCHAR(255) NULL , PRIMARY KEY (`MA_NH`)) ENGINE = InnoDB;
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('BIDV', 'Ngân hàng Đầu tư và Phát triển Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Vietcombank', 'Ngân hàng TMCP Ngoại thương Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Vietinbank', 'Ngân hàng TMCP Công Thương Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Agribank', 'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('ACB', 'Ngân hàng TMCP Á Châu');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Sacombank', 'Ngân hàng TMCP Sài Gòn Thương tín');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('SeABank', 'Ngân hàng TMCP Đông Nam Á');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Techcombank', 'Ngân hàng TMCP Kỹ Thương Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('MB', 'Ngân hàng TMCP Quân đội');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('Eximbank', 'Ngân hàng TMCP Xuất Nhập Khẩu Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('LienVietPostBank', 'Ngân hàng  Bưu điện Liên Việt');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('DongABank', 'Ngân hàng TMCP Đông Á');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('ABBank', 'Ngân hàng TMCP An Bình');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('MaritimeBank', 'Ngân hàng TMCP Hàng hải Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('VPBank', 'Ngân hàng Việt Nam Thịnh Vượng');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('VIB', 'Ngân hàng TMCP Quốc Tế Việt Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('SHB', 'Ngân hàng TMCP Sài Gòn Hà Nội');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('PNB', 'Ngân hàng TMCP Phương Nam');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('MHB', 'Ngân hàng phát triển nhà ĐBSCL');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('ANZVL', 'Ngân hàng TNHH một thành viên ANZ (Việt Nam)');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('IVB', 'Ngân hàng TNHH Indovina');
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES('VID Public Bank', 'Ngân hàng VID public');

CREATE TABLE `NGAN_HANG_TAI_KHOAN` ( `MA_TK` INT NOT NULL AUTO_INCREMENT , `SO_TK` VARCHAR(100) NOT NULL , `MA_NH` INT NOT NULL , `CHI_NHANH` VARCHAR(255) NULL , `DIA_CHI` VARCHAR(255) NULL , `CHU_TK` VARCHAR(255) NULL , `MO_TA` VARCHAR(255) NULL , PRIMARY KEY (`MA_TK`)) ENGINE = InnoDB;
INSERT INTO `NGAN_HANG_TAI_KHOAN` (`MA_TK`, `SO_TK`, `MA_NH`, `CHI_NHANH`, `DIA_CHI`, `CHU_TK`, `MO_TA`) VALUES (NULL, '123456789', '1', 'BIDV Cầu Giấy', 'Số 1, Đường Trần Đăng Ninh, Cầu Giấy, Hà Nội', 'Trần Đông Hải', '');
INSERT INTO `NGAN_HANG_TAI_KHOAN` (`MA_TK`, `SO_TK`, `MA_NH`, `CHI_NHANH`, `DIA_CHI`, `CHU_TK`, `MO_TA`) VALUES (NULL, '987654321', '2', 'VCB Mỹ Đình', 'Tòa nhà Toyota Mỹ Đình, Số 15, Đường Phạm Hùng, Nam Từ Liêm, Hà Nội', 'Trần Đông Hải', '');

