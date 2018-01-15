SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `dia_chi_vung` ( `MA_VUNG_DC` varchar(10) NOT NULL, `TEN_VUNG_DC` varchar(100) NOT NULL, `MA_VUNG_DC_CHA` varchar(10) DEFAULT NULL, `MA_CAP_DC` INT NOT NULL, PRIMARY KEY (`MA_VUNG_DC`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `dia_chi_vung` (`MA_VUNG_DC`, `TEN_VUNG_DC`, `MA_VUNG_DC_CHA`, `MA_CAP_DC`) VALUES
('m1', 'Bắc', NULL, 1), ('m2', 'Trung', NULL, 1), ('m3', 'Nam', NULL, 1),
('01', 'Hà Nội', 'm1', 2), ('48', 'Đà Nẵng', 'm2', 2), ('79', 'Hồ Chí Minh', 'm3', 2);

CREATE TABLE `idigroup`.`DIA_CHI_CAP` ( `MA_CAP_DC` INT NOT NULL AUTO_INCREMENT , `TEN_CAP_DC` VARCHAR(255) NOT NULL , `MO_TA` VARCHAR(255) NOT NULL , `CAP_DC` TINYINT NOT NULL , PRIMARY KEY (`MA_CAP_DC`)) ENGINE = InnoDB;
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Miền', '', '1');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Thành phố', 'Thành phố trực thuộc trung ương', '2');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Tỉnh', '', '2');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Quận', '', '3');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Huyện', '', '3');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Thành phố', 'Thành phố trực thuộc tỉnh', '3');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Thị xã', '', '3');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Thị trấn', '', '4');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Phường', '', '4');
INSERT INTO `DIA_CHI_CAP` ( `TEN_CAP_DC`, `MO_TA`, `CAP_DC`) VALUES ('Xã', '', '4');
