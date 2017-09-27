--------------------------------------- CÂU TRUY VẤN CHO PHẦN KPI CHART ---------------------------------------

CREATE TABLE `idigroup`.`BALANCE_ASSET_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`BALANCE_ASSET_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`SALE_RESULT_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`SALE_RESULT_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`KPI_GROUP` ( `GROUP_ID` INT NOT NULL AUTO_INCREMENT , `GROUP_NAME` VARCHAR(255) NOT NULL , PRIMARY KEY (`GROUP_ID`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng thanh toán');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng hoạt động');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng sinh lợi');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng cân đối nợ');

CREATE TABLE `idigroup`.`KPI_CHART` ( `CHART_ID` INT NOT NULL AUTO_INCREMENT , `CHART_TITLE` VARCHAR(255) NOT NULL , `CHART_TITLE_EN` VARCHAR(255) NULL , `GROUP_ID` INT NOT NULL, HOME_FLAG TINYINT(1) NOT NULL DEFAULT 1 , THRESHOLD DOUBLE NOT NULL DEFAULT 0.5, PRIMARY KEY (`CHART_ID`) ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`KPI_MEASURE` ( `MEASURE_ID` INT NOT NULL AUTO_INCREMENT , `MEASURE_NAME` VARCHAR(255) NOT NULL , `EXPRESSION` VARCHAR(255) NULL , `CHART_ID` INT NOT NULL , `TYPE_CHART` INT NOT NULL DEFAULT '1', PRIMARY KEY (`MEASURE_ID`) ) ENGINE = InnoDB;

-- Các biểu đồ trong nhóm khả năng thanh toán
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán tức thời', 'CĐKT.100.CK/CĐKT.310.CK', 1);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán nhanh', '(CĐKT.100.CK-CĐKT.140.CK)/CĐKT.310.CK', 2);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán bằng tiền', 'CĐKT.110.CK/CĐKT.310.CK', 3);

-- Các biểu đồ trong nhóm khả năng hoạt động 
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay khoản phải thu', 'HĐKD.10.CK/(((CĐKT.130.ĐK+CĐKT.210.ĐK)+(CĐKT.130.CK+CĐKT.210.CK))/2)', 4);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Kỳ thu tiền bình quân', '365/KPI.4', 5);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị sổ sách', 'HĐKD.11.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 6);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị thị trường', 'HĐKD.10.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 7);

-- Các biểu đồ trong nhóm khả năng sinh lợi
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng tổng tài sản (vòng quay tổng tài sản)', 'HĐKD.10.CK/((CĐKT.270.ĐK+CĐKT.270.CK)/2)', 17);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng tài sản cố định', 'HĐKD.10.CK/((CĐKT.220.ĐK+CĐKT.220.CK)/2)', 18);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng trên vòng quay vốn lưu động', 'HĐKD.10.CK/((CĐKT.100.ĐK+CĐKT.100.CK)/2)', 19);

-- Các biểu đồ trong nhóm khả năng cân đối nợ
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hệ số nợ (tỷ số nợ)', 'CĐKT.300.CK/CĐKT.270.CK', 28);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Đòn bẩy tài chính', 'CĐKT.270.CK/CĐKT.400.CK', 29);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ số khả năng trả nợ', 'CĐKT.270.CK/1000000', 30);

------------------------------------------------------------------------------------------------------------