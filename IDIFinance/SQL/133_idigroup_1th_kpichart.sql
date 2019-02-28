-- CÂU TRUY VẤN CHO PHẦN KPI CHART
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `BALANCE_ASSET_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1, PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
INSERT INTO `BALANCE_ASSET_ITEM` (`ASSET_CODE`, `ASSET_NAME`, `ASSET_PARENT`, `RULE`, `NOTE`, `SO_DU`) VALUES
('110', 'Tiền và các khoản tương đương tiền', '200', NULL, NULL, -1),
('120', 'Đầu tư tài chính', '200', '210=121+122+123+124', NULL, -1),
('121', 'Chứng khoán kinh doanh', '120', NULL, NULL, -1),
('122', 'Đầu tư nắm giữ đến ngày đáo hạn', '120', NULL, NULL, -1),
('123', 'Đầu tư góp vốn vào đơn vị khác', '120', NULL, NULL, -1),
('124', 'Dự phòng tổn thất đầu tư tài chính', '120', NULL, '(*)', -1),
('130', 'Các khoản phải thu', '200', '130=131+132+133+134+135+136', NULL, -1),
('131', 'Phải thu của khách hàng', '130', NULL, NULL, -1),
('132', 'Trả trước cho người bán', '130', NULL, NULL, -1),
('133', 'Vốn kinh doanh ở đơn vị trực thuộc', '130', NULL, NULL, -1),
('134', 'Phải thu khác', '130', NULL, NULL, -1),
('135', 'Tài sản thiếu chờ xử lý', '130', NULL, NULL, -1),
('136', 'Dự phòng phải thu khó đòi', '130', NULL, '(*)', -1),
('140', 'Hàng tồn kho', '200', '140=141+142', NULL, -1),
('141', 'Hàng tồn kho', '140', NULL, NULL, -1),
('142', 'Dự phòng giảm giá hàng tồn kho', '140', NULL, '(*)', -1),
('150', 'Tài sản cố định', '200', '150 = 151+152', NULL, -1),
('151', 'Nguyên giá', '150', NULL, NULL, -1),
('152', 'Giá trị hao mòn lũy kế', '150', NULL, '(*)', -1),
('160', 'Bất động sản đầu tư', '200', '160 = 161+162', NULL, -1),
('161', 'Nguyên giá', '160', NULL, NULL, -1),
('162', 'Giá trị hao mòn lũy kế', '160', NULL, '(*)', -1),
('170', 'Xây dựng cơ bản dở dang', '200', NULL, NULL, -1),
('180', 'Tài sản khác', '200', '180 = 181+182', NULL, -1),
('181', 'Thuế GTGT được khấu trừ', '180', NULL, NULL, -1),
('182', 'Tài sản khác', '180', NULL, NULL, -1),
('200', 'Tổng cộng tài sản', NULL, '200=110+120+130+140+150+160+170+180', NULL, -1),
('300', 'Nợ phải trả', '500', '300 = 311+312+313+314+315+316+317+318+319+320', NULL, 1),
('311', 'Phải trả người bán', '300', NULL, NULL, 1),
('312', 'Người mua trả tiền trước', '300', NULL, NULL, 1),
('313', 'Thuế và các khoản phải nộp nhà nước', '300', NULL, NULL, 1),
('314', 'Phải trả người lao động', '300', NULL, NULL, 1),
('315', 'Phải trả khác', '300', NULL, NULL, 1),
('316', 'Vay và nợ thuê tài chính', '300', NULL, NULL, 1),
('317', 'Phải trả nội bộ về vốn kinh doanh', '300', NULL, NULL, 1),
('318', 'Dự phòng phải trả', '300', NULL, NULL, 1),
('319', 'Quỹ khen thưởng phúc lợi', '300', NULL, NULL, 1),
('320', 'Quỹ phát triển khoa học và công nghệ', '300', NULL, NULL, 1),
('400', 'Vốn chủ sử hữu', '500', '400=411+412+413+414+415+416+417', NULL, 1),
('411', 'Vốn góp của chủ sở hữu', '400', NULL, NULL, 1),
('412', 'Thặng dư vốn cổ phần', '400', NULL, NULL, 1),
('413', 'Vốn khác của chủ sở hữu', '400', NULL, NULL, 1),
('414', 'Cổ phiết quỹ', '400', NULL, '(*)', 1),
('415', 'Chênh lệch tỷ giá hối đoán', '400', NULL, NULL, 1),
('416', 'Các quỹ thuộc vốn chủ sở hữu', '400', NULL, NULL, 1),
('417', 'Lợi nhuận sau thuế chưa phân phối', '400', NULL, NULL, 1),
('500', 'Tổng cộng nguồn vốn', NULL, '500=300+400', NULL, 1);
CREATE TABLE `BALANCE_ASSET_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1', `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `SALE_RESULT_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1 , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
INSERT INTO `SALE_RESULT_ITEM` (`ASSET_CODE`, `ASSET_NAME`, `ASSET_PARENT`, `RULE`, `NOTE`, `SO_DU`) VALUES
('01', 'Doanh thu bán hàng và cung cấp dịch vụ', '10', NULL, NULL, -1),
('02', 'Các khoản giảm trừ doanh thu', '10', NULL, NULL, -1),
('10', 'Doanh thu thuần về bán hàng và cung cấp dịch vụ', '20', '01 - 02', NULL, -1),
('11', 'Giá vốn hàng bán', '20', NULL, NULL, -1),
('20', 'Lợi nhuận gộp về bán hàng và cung cấp dịch vụ', '30', '10 - 11', NULL, -1),
('21', 'Doanh thu hoạt động tài chính', '30', NULL, NULL, -1),
('22', 'Chi phí tài chính', '30', NULL, NULL, -1),
('23', 'Chi phí lãi vay', NULL, NULL, NULL, -1),
('24', 'Chi phí quản lý kinh doan', '30', NULL, NULL, -1),
('30', 'Lợi nhuận thuần từ hoạt động kinh doanh', '50', '20 + 21 - 22 - 24', NULL, -1),
('31', 'Thu nhập khác', '40', NULL, NULL, -1),
('32', 'Chi phí khác', '40', NULL, NULL, -1),
('40', 'Lợi nhuận khác', '50', '31 - 32', NULL, -1),
('50', 'Tổng lợi nhuận kế toán trước thuế', '60', '30 + 40', NULL, -1),
('51', 'Chi phí thuế thu nhập DN', '60', NULL, NULL, -1),
('60', 'Lợi nhuận sau thuế thu nhập doanh nghiệp', NULL, '50 - 51', NULL, -1);
CREATE TABLE `SALE_RESULT_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1' , `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `CASH_FLOW_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1 , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
-- Thêm lưu chuyển tiền tệ theo phương pháp trực tiếp
INSERT INTO `CASH_FLOW_ITEM` VALUES 
('01','Tiền thu từ bán hàng, cung cấp dịch vụ và doanh thu khác','20','',NULL,-1),
('02','Tiền chi trả cho người cung cấp hàng hoá, dịch vụ','20','',NULL,-1),
('03','Tiền chi trả cho người lao động','20','',NULL,-1),
('04','Tiền lãi vay đã trả','20','',NULL,-1),
('05','Thuế thu nhập doanh nghiệp đã nộp','20','',NULL,-1),
('06','Tiền thu khác từ hoạt động kinh doanh','20','',NULL,-1),
('07','Tiền chi khác cho hoạt động kinh doanh','20','',NULL,-1),
('20','Lưu chuyển tiền thuần từ hoạt động kinh doanh','50','01+02+03+04+05+06+07',NULL,-1),
('21','Tiền chi để mua sắm, xây dựng TSCĐ, BĐSĐT và các tài sản dài hạn khác','30','',NULL,-1),
('22','Tiền thu từ thanh lý, nhượng bán TSCĐ, BĐSĐT và các tài sản dài hạn khác','30','',NULL,-1),
('23','Tiền chi cho vay, đầu tư góp vốn vào đơn vị khác','30','',NULL,-1),
('24','Tiền thu hồi cho vay, đầu tư góp vốn vào đơn vị khác','30','',NULL,-1),
('25','Tiền thu lãi cho vay, cổ tức và lợi nhuận được chia','30','',NULL,-1),
('30','Lưu chuyển tiền thuần từ hoạt động đầu tư','50','21+22+23+24+25',NULL,-1),
('31','Tiền thu từ phát sinh cổ phiếu, nhận vốn góp của chủ sở hữu','40','',NULL,-1),
('32','Tiền trả lại vốn góp cho các chủ sở hữu, mua lại cổ phiếu của doanh nghiệp đã phát hành','40','',NULL,-1),
('33','Tiền thu từ đi vay','40','',NULL,-1),
('34','Tiền trả nợ gốc vay và nợ thuê tài chính','40','',NULL,-1),
('35','Cổ tức, lợi nhuận đã trả cho chủ sở hữu','40','',NULL,-1),
('40','Lưu chuyển tiền thuần từ hoạt động tài chính','50','31+32+33+34+35',NULL,-1),
('50','Lưu chuyển tiền thuần trong kỳ','70','50 = 20 + 30 + 40',NULL,-1),
('60','Tiền và tương đương tiền đầu kỳ','70','',NULL,-1),
('61','Ảnh hưởng của thay đổi tỷ giá hối đoái quy đổi ngoại tệ','70','',NULL,-1),
('70','Tiền và tương đương tiền cuối kỳ',NULL,'70 = 50 + 60 + 61',NULL,-1);

CREATE TABLE `CASH_FLOW_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1', `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `KPI_GROUP` ( `GROUP_ID` INT NOT NULL AUTO_INCREMENT , `GROUP_NAME` VARCHAR(255) NOT NULL , PRIMARY KEY (`GROUP_ID`) ) ENGINE = InnoDB;
INSERT INTO `KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng thanh toán'), ('Khả năng hoạt động'), ('Khả năng sinh lợi'), ('Khả năng cân đối nợ');

CREATE TABLE `KPI_CHART` ( `CHART_ID` INT NOT NULL AUTO_INCREMENT , `CHART_TITLE` VARCHAR(255) NOT NULL , `CHART_TITLE_EN` VARCHAR(255) NULL , `GROUP_ID` INT NOT NULL, HOME_FLAG TINYINT(1) NOT NULL DEFAULT 0 , THRESHOLD DOUBLE NOT NULL DEFAULT 0, PRIMARY KEY (`CHART_ID`) ) ENGINE = InnoDB;
INSERT INTO `KPI_CHART` (`CHART_ID`, `CHART_TITLE`, `CHART_TITLE_EN`, `GROUP_ID`, `HOME_FLAG`, `THRESHOLD`) VALUES
(1, 'Khả năng thanh toán tức thời', 'Current ratio', 1, 1, 1),
(2, 'Khả năng thanh toán nhanh', 'Quick ratio', 1, 1, 1),
(3, 'Khả năng thanh toán bằng tiền', 'Cash ratio', 1, 1, 1),
(4, 'Vòng quay khoản phải thu', 'Collection turnover - receivables turnover', 2, 1, 1),
(5, 'Kỳ thu tiền bình quân', 'Average collection period', 2, 1, 1),
(6, 'Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị sổ sách', 'Inventeries turnover', 2, 1, 1),
(7, 'Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị thị trường', 'Inventeries turnover', 2, 1, 1),
(8, 'Số ngày tồn kho bình quân theo giá trị sổ sách', 'Average age of inventories -days in inventories', 2, 0, 0),
(9, 'Số ngày tồn kho bình quân theo giá trị thị trường', 'Average age of inventories -days in inventories', 2, 0, 0),
(10, 'Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách', 'Operating cycle', 2, 0, 0),
(11, 'Chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường', 'Operating cycle', 2, 0, 0),
(12, 'Vòng quay khoản phải trả', 'Payment turnover', 2, 0, 0),
(13, 'Kỳ phải trả bình quân', 'Average payment period', 2, 0, 0),
(14, 'Chu kỳ luân chuyển tiền theo giá trị sổ sách', 'Cash conversion cycle', 2, 0, 0),
(15, 'Chu kỳ luân chuyển tiền theo giá trị thị trường', 'Cash conversion cycle', 2, 0, 0),
(16, 'Khả năng thanh toán lãi vay', 'Interest coverage', 2, 0, 0),
(17, 'Hiệu suất sử dụng tổng tài sản (vòng quay tổng tài sản)', 'Total assets utility (total assets turnover)', 3, 1, 1),
(18, 'Hiệu suất sử dụng tài sản cố định', 'Fixed assets turnover', 3, 1, 1),
(19, 'Hiệu suất sử dụng trên vòng quay vốn lưu động', 'Working capital turnover', 3, 1, 1),
(20, 'Tỷ suất lợi nhuận gộp (lợi nhuận gộp biên)', 'Gross profit margin', 3, 0, 0),
(21, 'Tỷ suất lợi nhuận ròng (lợi nhuận ròng biên)', 'Net profit margin (profit margin on sales)', 3, 0, 0),
(22, 'Suất sinh lời trên tổng tài sản (doanh lợi tổng tài sản)', 'Return on assets', 3, 0, 0),
(23, 'Suất sinh lời trên vốn chủ sở hữu (doanh lợi vốn chủ sở hữu)', 'Return on assets', 3, 0, 0),
(24, 'Suất sinh lời vốn đầu tư', 'Return on investment', 3, 0, 0),
(25, 'Khả năng sinh lời cơ bản', 'Basic earning power', 3, 0, 0),
(26, 'Tỷ số lợi nhuận tích lũy', NULL, 3, 0, 0),
(27, 'Tỷ số tăng trưởng bền vững', NULL, 3, 0, 0),
(28, 'Hệ số nợ (tỷ số nợ)', 'Debt ratio', 4, 1, 0.1),
(29, 'Đòn bẩy tài chính', 'Debt ratio', 4, 1, 1),
(30, 'Tỷ số khả năng trả nợ', 'Debt ratio', 4, 1, 1000);

CREATE TABLE `KPI_MEASURE` ( `MEASURE_ID` INT NOT NULL AUTO_INCREMENT , `MEASURE_NAME` VARCHAR(255) NOT NULL , `EXPRESSION` VARCHAR(255) NULL , `CHART_ID` INT NOT NULL , `TYPE_CHART` INT NOT NULL DEFAULT '1', PRIMARY KEY (`MEASURE_ID`) ) ENGINE = InnoDB;

-- Các biểu đồ trong nhóm khả năng thanh toán
INSERT INTO `KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES 
('Khả năng thanh toán tức thời', 'CĐKT.100.CK/CĐKT.310.CK', 1),
('Khả năng thanh toán nhanh', '(CĐKT.100.CK-CĐKT.140.CK)/CĐKT.310.CK', 2),
('Khả năng thanh toán bằng tiền', 'CĐKT.110.CK/CĐKT.310.CK', 3);

-- Các biểu đồ trong nhóm khả năng hoạt động 
INSERT INTO `KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES 
('Vòng quay khoản phải thu', 'HĐKD.10.CK/(((CĐKT.130.ĐK+CĐKT.210.ĐK)+(CĐKT.130.CK+CĐKT.210.CK))/2)', 4),
('Kỳ thu tiền bình quân', '365/KPI.4', 5),
('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị sổ sách', 'HĐKD.11.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 6),
('Vòng quay hàng tồn kho (chỉ số đo theo năm/quý) theo giá trị thị trường', 'HĐKD.10.CK/((CĐKT.140.ĐK+CĐKT.140.CK)/2)', 7),
('Số ngày tồn kho bình quân theo giá trị sổ sách', '365/KPI.6', 8),
('Số ngày tồn kho bình quân theo giá trị thị trường', '365/KPI.7', 9),
('Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách', 'KPI.5+KPI.8', 10),
('Chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường', 'KPI.5+KPI.9', 11),
('Vòng quay khoản phải trả', 'HĐKD.11.CK/(((CĐKT.310.ĐK+CĐKT.330.ĐK)+(CĐKT.310.CK+CĐKT.330.CK))/2)', 12),
('Kỳ phải trả bình quân', '365/KPI.12', 13),
('Chu kỳ luân chuyển tiền theo giá trị sổ sách', 'KPI.10/KPI.13', 14),
('Chu kỳ luân chuyển tiền theo giá trị thị trường', 'KPI.11/KPI.13', 15),
('Khả năng thanh toán lãi vay', 'HĐKD.50.CK/HĐKD.23.CK', 16);

-- Các biểu đồ trong nhóm khả năng sinh lợi
INSERT INTO `KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES 
('Hiệu suất sử dụng tổng tài sản (vòng quay tổng tài sản)', 'HĐKD.10.CK/((CĐKT.270.ĐK+CĐKT.270.CK)/2)', 17),
('Hiệu suất sử dụng tài sản cố định', 'HĐKD.10.CK/((CĐKT.220.ĐK+CĐKT.220.CK)/2)', 18),
('Hiệu suất sử dụng trên vòng quay vốn lưu động', 'HĐKD.10.CK/((CĐKT.100.ĐK+CĐKT.100.CK)/2)', 19),
('Tỷ suất lợi nhuận gộp (Lợi nhuận gộp biên)', 'HĐKD.20.CK/HĐKD.10.CK', 20),
('Tỷ suất lợi nhuận ròng (Lợi nhuận ròng biên)', 'HĐKD.60.CK/(HĐKD.01.CK+HĐKD.21.CK)', 21),
('Suất sinh lời trên tổng tài sản (Doanh lợi tổng tài sản)', 'HĐKD.60.CK/CĐKT.270.CK', 22),
('Suất sinh lời trên vốn chủ sở hữu (Doanh lợi vốn chủ sở hữu)', 'HĐKD.60.CK/CĐKT.400.CK', 23),
('Suất sinh lời vốn đầu tư', 'HĐKD.60.CK/HĐKD.30.CK', 24), -- Lưu chuyển tiền tệ, phải sửa code
('Khả năng sinh lời cơ bản', 'HĐKD.50.CK/CĐKT.270.CK', 25),
('Tỷ số lợi nhuận tích lũy', 'HĐKD.60.CK/(HĐKD.60.CK-HĐKD.36.CK)', 26), -- Lưu chuyển tiền tệ, phải sửa code
('Tỷ số tăng trưởng bền vững', '(HĐKD.60.CK-HĐKD.36.CK)/CĐKT.400.CK', 27); -- Lưu chuyển tiền tệ, phải sửa code

-- Các biểu đồ trong nhóm khả năng cân đối nợ
INSERT INTO `KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES 
('Hệ số nợ (tỷ số nợ)', 'CĐKT.300.CK/CĐKT.270.CK', 28),
('Đòn bẩy tài chính', 'CĐKT.270.CK/CĐKT.400.CK', 29),
('Tỷ số khả năng trả nợ', 'CĐKT.270.CK/1000000', 30);