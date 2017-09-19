------------------------- version 1 -------------------------
CREATE TABLE `idigroup`.`BALANCE_ASSET` ( `ASSETS_ID` INT NOT NULL AUTO_INCREMENT , `ASSETS_NAME` VARCHAR(255) NOT NULL , RULE VARCHAR(255) NULL ,`ASSETS_CODE` VARCHAR(10) NOT NULL , `NOTE` VARCHAR(255) NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `ASSETS_PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSETS_ID`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`SALE_RESULT` ( `ASSETS_ID` INT NOT NULL AUTO_INCREMENT , `ASSETS_NAME` VARCHAR(255) NOT NULL , RULE VARCHAR(255) NULL ,`ASSETS_CODE` VARCHAR(10) NOT NULL , `NOTE` VARCHAR(255) NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `ASSETS_PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSETS_ID`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

------------------------- version 2 -------------------------
CREATE TABLE `idigroup`.`BALANCE_ASSET_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`BALANCE_ASSET_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`SALE_RESULT_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`SALE_RESULT_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`KPI_GROUP` ( `GROUP_ID` INT NOT NULL AUTO_INCREMENT , `GROUP_NAME` VARCHAR(255) NOT NULL , PRIMARY KEY (`GROUP_ID`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`, `ORDER`) VALUES ('Khả năng thanh toán', '1');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`, `ORDER`) VALUES ('Khả năng hoạt động', '2');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`, `ORDER`) VALUES ('Khả năng sinh lợi', '3');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`, `ORDER`) VALUES ('Khả năng cân đối nợ', '4');

CREATE TABLE `idigroup`.`KPI_CHART` ( `CHART_ID` INT NOT NULL AUTO_INCREMENT , `CHART_TITLE` VARCHAR(255) NOT NULL , `CHART_TITLE_EN` VARCHAR(255) NULL , `GROUP_ID` INT NOT NULL, HOME_FLAG TINYINT(1) NOT NULL DEFAULT 0 , THRESHOLD DOUBLE NOT NULL DEFAULT 0, PRIMARY KEY (`CHART_ID`) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`KPI_MEASURE` ( `MEASURE_ID` INT NOT NULL AUTO_INCREMENT , `MEASURE_NAME` VARCHAR(255) NOT NULL , `EXPRESSION` VARCHAR(255) NULL , `CHART_ID` INT NOT NULL , `TYPE_CHART` INT NOT NULL DEFAULT '1', PRIMARY KEY (`MEASURE_ID`) ) ENGINE = InnoDB;

-- Các biểu đồ trong nhóm khả năng thanh toán
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán tức thời', 'CĐKT.100.CK/CĐKT.310.CK', 31);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán nhanh', '(CĐKT.100.CK-CĐKT.140.CK)/CĐKT.310.CK', 32);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán bằng tiền', 'CĐKT.110.CK/CĐKT.310.CK', 33);

-- Các biểu đồ trong nhóm khả năng hoạt động 
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay khoản phải thu', 'HĐKD.10.CK/(((CĐKT.130.ĐK+CĐKT.210.ĐK)+(CĐKT.130.CK+CĐKT.210.CK))/2)', 34);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Kỳ thu tiền bình quân', '365/KPI.4', 35);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị sổ sách', 'HĐKD.11.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 36);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị thị trường', 'HĐKD.10.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 37);

-- Các biểu đồ trong nhóm khả năng sinh lợi


-- Các biểu đồ trong nhóm khả năng cân đối nợ

