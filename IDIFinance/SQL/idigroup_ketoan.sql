CREATE TABLE `idigroup`.KHACH_HANG ( `MA_KH` INT NOT NULL AUTO_INCREMENT , `TEN_KH` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_KH`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.NHA_CUNG_CAP ( `MA_NCC` INT NOT NULL AUTO_INCREMENT , `TEN_NCC` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_NCC`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`NGOAI_TE` ( `MA_NT` VARCHAR(10) NOT NULL , `TEN_NT` VARCHAR(255) NOT NULL , `MUA_TM` DOUBLE NOT NULL , `MUA_CK` DOUBLE NULL ,`	BAN_RA` DOUBLE NOT NULL , PRIMARY KEY (`MA_NT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`TAI_KHOAN_DANH_MUC` ( `MA_TK` VARCHAR(10) NOT NULL , `TEN_TK` VARCHAR(255) NOT NULL , `MA_TK_CHA` VARCHAR(10) NULL , `SO_DU` TINYINT NOT NULL DEFAULT 1) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`CHUNG_TU_DANH_MUC` ( `MA_CT` VARCHAR(100) NOT NULL , `TEN_CT` VARCHAR(255) NOT NULL , PRIMARY KEY (`MA_CT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`CHUNG_TU_TAI_KHOAN` ( `MA_CT` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` double NOT NULL, `SO_DU` TINYINT NOT NULL DEFAULT '1', LY_DO VARCHAR(255) NULL, PRIMARY KEY (`MA_CT`, `MA_TK`, `SO_DU`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`CHUNG_TU` (`MA_CT` int(11) NOT NULL AUTO_INCREMENT, `SO_CT` int(11) DEFAULT NULL, `LOAI_CT` varchar(100) DEFAULT NULL, `NGAY_LAP` date NOT NULL, `NGAY_HT` date NOT NULL, `LY_DO` varchar(255) NOT NULL, `SO_TIEN` double NOT NULL, `LOAI_TIEN` varchar(10) NOT NULL DEFAULT 'VND', `TY_GIA` double NOT NULL DEFAULT '1', `KEM_THEO` int(11) DEFAULT NULL, `MA_DT` int(11) NOT NULL, `LOAI_DT` TINYINT NOT NULL, `NGUOI_NOP` varchar(255) NOT NULL, PRIMARY KEY (`MA_CT`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ngoai_te` (`MA_NT`, `TEN_NT`, `MUA_TM`, `MUA_CK`, `BAN_RA`) VALUES
('EUR', 'EURO', 26633, 26714, 26952),
('JPY', 'JAPANESE YEN', 199, 201, 203),
('USD', 'US DOLLAR', 22680, 22680, 22750),
('VND', 'Việt Nam Đồng', 1, 1, 1);


