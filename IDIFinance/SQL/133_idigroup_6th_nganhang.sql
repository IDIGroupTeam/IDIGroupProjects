SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `NGAN_HANG` ( `MA_NH` INT NOT NULL AUTO_INCREMENT , `TEN_VT` VARCHAR(50) NOT NULL , `TEN_DD` VARCHAR(255) NOT NULL , `TEN_TA` VARCHAR(255) NULL , `DIA_CHI_HSC` VARCHAR(255) NULL , `BIEU_TUONG` VARCHAR(255) NULL , `MO_TA` VARCHAR(255) NULL , PRIMARY KEY (`MA_NH`)) ENGINE = InnoDB;
INSERT INTO `NGAN_HANG` (`TEN_VT`, `TEN_DD`) VALUES 
('BIDV', 'Ngân hàng Đầu tư và Phát triển Việt Nam'),
('Vietcombank', 'Ngân hàng TMCP Ngoại thương Việt Nam'),
('Vietinbank', 'Ngân hàng TMCP Công Thương Việt Nam'),
('Agribank', 'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam'),
('ACB', 'Ngân hàng TMCP Á Châu'),
('Sacombank', 'Ngân hàng TMCP Sài Gòn Thương tín'),
('SeABank', 'Ngân hàng TMCP Đông Nam Á'),
('Techcombank', 'Ngân hàng TMCP Kỹ Thương Việt Nam'),
('MB', 'Ngân hàng TMCP Quân đội'),
('Eximbank', 'Ngân hàng TMCP Xuất Nhập Khẩu Việt Nam'),
('LienVietPostBank', 'Ngân hàng  Bưu điện Liên Việt'),
('DongABank', 'Ngân hàng TMCP Đông Á'),
('ABBank', 'Ngân hàng TMCP An Bình'),
('MaritimeBank', 'Ngân hàng TMCP Hàng hải Việt Nam'),
('VPBank', 'Ngân hàng Việt Nam Thịnh Vượng'),
('VIB', 'Ngân hàng TMCP Quốc Tế Việt Nam'),
('SHB', 'Ngân hàng TMCP Sài Gòn Hà Nội'),
('PNB', 'Ngân hàng TMCP Phương Nam'),
('MHB', 'Ngân hàng phát triển nhà ĐBSCL'),
('ANZVL', 'Ngân hàng TNHH một thành viên ANZ (Việt Nam)'),
('IVB', 'Ngân hàng TNHH Indovina'),
('VID Public Bank', 'Ngân hàng VID public');

CREATE TABLE `NGAN_HANG_TAI_KHOAN` ( `MA_TK` INT NOT NULL AUTO_INCREMENT , `SO_TK` VARCHAR(100) NOT NULL , `MA_NH` INT NOT NULL , `CHI_NHANH` VARCHAR(255) NULL , `DIA_CHI` VARCHAR(255) NULL , `CHU_TK` VARCHAR(255) NULL , `MO_TA` VARCHAR(255) NULL , PRIMARY KEY (`MA_TK`)) ENGINE = InnoDB;
INSERT INTO `NGAN_HANG_TAI_KHOAN` (`MA_TK`, `SO_TK`, `MA_NH`, `CHI_NHANH`, `DIA_CHI`, `CHU_TK`, `MO_TA`) VALUES 
(NULL, '123456789', '1', 'BIDV Cầu Giấy', 'Số 1, Đường Trần Đăng Ninh, Cầu Giấy, Hà Nội', 'Trần Đông Hải', ''),
(NULL, '987654321', '2', 'VCB Mỹ Đình', 'Tòa nhà Toyota Mỹ Đình, Số 15, Đường Phạm Hùng, Nam Từ Liêm, Hà Nội', 'Trần Đông Hải', '');


