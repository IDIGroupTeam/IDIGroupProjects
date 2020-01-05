CREATE TABLE `BAO_CAO_TAI_CHINH_KY_KPI` ( `MA_BCTC` INT NOT NULL , `MA_KY_KPI` INT NOT NULL , PRIMARY KEY (`MA_BCTC`, `MA_KY_KPI`)) ENGINE = InnoDB;

INSERT INTO `CAU_HINH` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES ('MST', 'Mã số thuế', '1234567890', 1);

CREATE TABLE `BAO_CAO_TAI_CHINH_CHI_TIET_CDPS` ( `MA_BCTC_CON` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL , `NO_DAU_KY` DOUBLE NOT NULL DEFAULT 0, `CO_DAU_KY` DOUBLE NOT NULL DEFAULT 0, `NO_PHAT_SINH` DOUBLE NOT NULL DEFAULT 0, `CO_PHAT_SINH` DOUBLE NOT NULL DEFAULT 0, PRIMARY KEY (`MA_BCTC_CON`, `MA_TK`)) ENGINE = InnoDB;
