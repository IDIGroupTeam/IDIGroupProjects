SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `idigroup`.`HANG_HOA_NHOM` ( `MA_NHOM_HH` INT NOT NULL AUTO_INCREMENT , `TEN_NHOM_HH` VARCHAR(255) NOT NULL , `MA_NHOM_HH_CHA` INT NULL , PRIMARY KEY (`MA_NHOM_HH`)) ENGINE = InnoDB;
INSERT INTO `hang_hoa_nhom` (`TEN_NHOM_HH`, `MA_NHOM_HH_CHA`) VALUES ('Công cụ dụng cụ', NULL), ('Hàng hóa', NULL), ('Nguyên vật liệu', NULL), ('Xăng', 3);

CREATE TABLE `idigroup`.`HANG_HOA_DANH_MUC` ( `MA_HH` INT NOT NULL AUTO_INCREMENT , `TEN_HH` VARCHAR(255) NOT NULL , `MA_DV` INT NOT NULL , `MA_NHOM_HH` INT NULL , PRIMARY KEY (`MA_HH`)) ENGINE = InnoDB;
INSERT INTO `hang_hoa_danh_muc` (`TEN_HH`, `MA_DV`, `MA_NHOM_HH`) VALUES ('Toyota', 1, 2), ('Xăng 92', 7, 4);

CREATE TABLE `idigroup`.`HANG_HOA_DON_VI` ( `MA_DV` INT NOT NULL AUTO_INCREMENT , `TEN_DV` VARCHAR(50) NOT NULL , `MO_TA` VARCHAR(255) NULL , PRIMARY KEY (`MA_DV`)) ENGINE = InnoDB;
INSERT INTO `hang_hoa_don_vi` (`TEN_DV`, `MO_TA`) VALUES ('Chiếc', NULL), ('Cm', 'Centimet'), ('Dm', 'Decimet'), ('Gói', NULL), ('Cái', NULL), ('Lọ', NULL);
