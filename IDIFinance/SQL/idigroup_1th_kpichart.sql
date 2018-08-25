-- CÂU TRUY VẤN CHO PHẦN KPI CHART
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `idigroup`.`BALANCE_ASSET_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1, PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
INSERT INTO `BALANCE_ASSET_ITEM` (`ASSET_CODE`, `ASSET_NAME`, `ASSET_PARENT`, `RULE`, `NOTE`, `SO_DU`) VALUES
('100', 'Tài sản ngắn hạn', '270', '(100=110+120+130+140+150)', NULL, -1),
('110', 'Tiền và các khoản tương đương tiền', '100', '(110=111+112)', NULL, -1),
('111', 'Tiền', '110', '', 'V.01', -1),
('112', 'Các khoản tương đương tiền', '110', '', NULL, -1),
('120', 'Các khoản đầu tư tài chính ngắn hạn', '100', '(120=121+122+123)', 'V.02', -1),
('121', 'Chứng khoán kinh doanh', '120', '', NULL, -1),
('122', 'Dự phòng giảm giá chứng khoán kinh doanh', '120', '(*)', NULL, -1),
('123', 'Đầu tư nắm giữ đến ngày đáo hạn', '120', '', NULL, -1),
('130', 'Các khoản phải thu ngắn hạn', '100', '(130 = 131+132+133+134+135+139)', NULL, -1),
('131', 'Phải thu ngắn hạn của khách hàng', '130', '', NULL, -1),
('132', 'Trả trước cho người bán ngắn hạn', '130', '', NULL, -1),
('133', 'Phải thu nội bộ ngắn hạn', '130', '', NULL, -1),
('134', 'Phải thu theo tiến độ kế hoạch hợp đồng xây dựng', '130', '', NULL, -1),
('135', 'Phải thu về cho vay ngắn hạn', '130', '', NULL, -1),
('136', 'Các khoản phải thu ngắn hạn khác', '130', '', 'V.03', -1),
('137', 'Tài sản thiếu chờ xử lý', '130', '', NULL, -1),
('139', 'Dự phòng phải thu ngắn hạn khó đòi', '130', '(*)', NULL, -1),
('140', 'Hàng tồn kho', '100', '(140=141+149)', NULL, -1),
('141', 'Hàng tồn kho', '140', '', 'V.04', -1),
('149', 'Dự phòng giảm giá hàng tồn kho', '140', '(*)', NULL, -1),
('150', 'Tài sản ngắn hạn khác', '100', '(150 = 151+152+154+158)', NULL, -1),
('151', 'Chi phí trả trước ngắn hạn', '150', '', NULL, -1),
('152', 'Thuế gtgt được khấu trừ', '150', '', NULL, -1),
('153', 'Thuế và các khoản khách phải thu nhà nước', '150', '', 'V.05', -1),
('154', 'Giao dịch mua bán lại trái phiếu chính phủ', '150', '', NULL, -1),
('155', 'Tài sản ngắn hạn khác', '150', '', NULL, -1),
('200', 'Tài sản dài hạn', '270', '(200=210+220+240+250+260+269)', NULL, -1),
('210', 'Các khoản phải thu dài hạn', '200', '(210 = 211 + 212 + 213 + 218 + 219)', NULL, -1),
('211', 'Phải thu dài hạn của khách hàng', '210', '', NULL, -1),
('212', 'Trả trước cho người bán dài hạn', '210', '', NULL, -1),
('213', 'Vốn kinh doanh ở đơn vị trực thuộc', '210', '', NULL, -1),
('214', 'Phải thu dài hạn nội bộ', '210', '', 'V.06', -1),
('215', 'Phải thu về cho vay dài hạn', '210', '', NULL, -1),
('216', 'Phải thu dài hạn khác', '210', '', 'V.07', -1),
('219', 'Dự phòng phải thu dài hạn khó đòi', '210', '(*)', NULL, -1),
('220', 'Tài sản cố định', '200', '(220 = 221 + 224 + 227 + 230)', NULL, -1),
('221', 'Tài sản cố định hữu hình', '220', '(221 = 222 + 223)', 'V.08', -1),
('222', 'Nguyên giá', '221', '', NULL, -1),
('223', 'Gía trị hao mòn luỹ kế', '221', '(*)', NULL, -1),
('224', 'Tài sản cố định thuê tài chính', '220', '(224 = 225 + 226)', 'V.09', -1),
('225', 'Nguyên giá', '224', '', NULL, -1),
('226', 'Gía trị hao mòn luỹ kế', '224', '(*)', NULL, -1),
('227', 'Tài sản cố định vô hình', '220', '(227 = 228 + 229)', 'V.10', -1),
('228', 'Nguyên giá', '227', '', NULL, -1),
('229', 'Gía trị hao mòn luỹ kế', '227', '(*)', NULL, -1),
('230', 'Bất động sản đầu tư', '200', '(240 = 241 + 242)', 'V.12', -1),
('231', 'Nguyên giá', '230', '', NULL, -1),
('232', 'Gía trị hao mòn luỹ kế', '230', '(*)', NULL, -1),
('240', 'Tài sản dở dang dài hạn', '200', '', 'V.11', -1),
('241', 'Chi phí sản xuất, kinh doanh dở dang dài hạn', '240', '', NULL, -1),
('242', 'Chi phí xây dựng cơ bản dở dang', '240', '', NULL, -1),
('250', 'Đầu tư tài chính dài hạn', '200', '(250 = 251 + 252 + 258 + 259)', NULL, -1),
('251', 'Đầu tư vào công ty con', '250', '', NULL, -1),
('252', 'Đầu tư vào công ty liên kết, liên doanh', '250', '', NULL, -1),
('253', 'Đầu tư góp vốn vào đơn vị khác', '250', '', 'V.13', -1),
('254', 'Dự phòng giảm giá đầu tư tài chính dài hạn', '250', '', NULL, -1),
('255', 'Đầu tư nắm giữ đến ngày đáo hạn', '250', '', NULL, -1),
('260', 'Tài sản dài hạn khác', '200', '(260 = 261 + 262 + 268)', NULL, -1),
('261', 'Chi phí trả trước dài hạn', '260', '', 'V.14', -1),
('262', 'Tài sản thuế thu nhập hoãn lại', '260', '', 'V.21', -1),
('263', 'Thiết bị, vật tư, phụ tùng thay thế dài hạn', '260', '', NULL, -1),
('268', 'Tài sản dài hạn khác', '260', '', NULL, -1),
('269', 'Lợi thế thương mại', '200', '', NULL, -1),
('270', 'Tổng cộng tài sản', NULL, '(270 = 100 + 200)', NULL, -1),
('300', 'Nợ phải trả', '440', '(300 = 310 + 330)', NULL, 1),
('310', 'Nợ ngắn hạn', '300', '(310 = 311 + 312 +  ... + 319 + 320 + 323)', NULL, 1),
('311', 'Phải trả người bán ngắn hạn', '310', '', 'V.15', 1),
('312', 'Người mua trả tiền trước ngắn hạn', '310', '', NULL, 1),
('313', 'Thuế và các khoản phải nộp nhà nước', '310', '', NULL, 1),
('314', 'Phải trả người lao động', '310', '', 'V.16', 1),
('315', 'Chi phí phải trả ngắn hạn', '310', '', NULL, 1),
('316', 'Phải trả nội bộ ngắn hạn', '310', '', 'V.17', 1),
('317', 'Phải trả theo tiến độ kế hoạch hợp đồng xây dựng', '310', '', NULL, 1),
('318', 'Doanh thu chưa thực hiện ngắn hạn', '310', '', NULL, 1),
('319', 'Phải trả ngắn hạn khác', '310', '', NULL, 1),
('320', 'Vay và nợ thuê tài chính ngắn hạn', '310', '', 'V.18', 1),
('321', 'Dự phòng phải trả ngắn hạn', '310', '', NULL, 1),
('322', 'Quỹ khen thưởng phúc lợi', '310', '', NULL, 1),
('323', 'Quỹ bình ổn giá', '310', '', NULL, 1),
('324', 'Giao dịch mua bán lại trái phiếu chính phủ', '310', '', NULL, 1),
('330', 'Nợ dài hạn', '300', '(330 = 331 + 332 + ... + 338 + 339)', NULL, 1),
('331', 'Phải trả người bán dài hạn', '330', '', NULL, 1),
('332', 'Người mua trả tiền trước dài hạn', '330', '', NULL, 1),
('333', 'Chi phí phải trả dài hạn', '330', '', 'V.19', 1),
('334', 'Phải trả nội bộ về vốn kinh doanh', '330', '', NULL, 1),
('335', 'Phải trả dài hạn nội bộ', '330', '', NULL, 1),
('336', 'Doanh thu chưa thực hiện dài hạn', '330', '', NULL, 1),
('337', 'Phải trả dài hạn khác', '330', '', 'V.20', 1),
('338', 'Vay và nợ thuê tài chính dài hạn', '330', '', NULL, 1),
('339', 'Trái phiếu chuyển đổi', '330', '', 'V.21', 1),
('340', 'Cổ phiếu ưu đãi', '330', '', NULL, 1),
('341', 'Thuế thu nhập hoãn lại phải trả', '330', '', NULL, 1),
('342', 'Dự phòng phải trả dài hạn', '330', '', NULL, 1),
('343', 'Quỹ phát triển khoa học và công nghệ', '330', '', NULL, 1),
('400', 'Vốn chủ sử hữu', '440', '(400=410+430)', NULL, 1),
('410', 'Vốn chủ sở hữu', '400', '', NULL, 1),
('411', 'Vốn đầu tư của chủ sở hữu', '410', '', NULL, 1),
('4111', 'Vốn góp của chủ sở hữu', '411', NULL, NULL, 1),
('411a', 'Cổ phiếu phổ thông có quyền biểu quyết', '411', NULL, NULL, 1),
('411b', 'Cổ phiết ưu đãi', '411', NULL, NULL, 1),
('412', 'Thặng dư vốn cổ phần', '410', '', NULL, 1),
('413', 'Quyền chọn chuyển đổi trái phiếu', '410', '', NULL, 1),
('414', 'Vốn khác của chủ sở hữu', '410', '', NULL, 1),
('415', 'Cổ phiếu quỹ', '410', '(*)', NULL, 1),
('416', 'Chênh lệch đánh giá lại tài sản', '410', '', NULL, 1),
('417', 'Chênh lệch tỷ giá hối đoái', '410', '', NULL, 1),
('418', 'Quỹ đầu tư phát triển', '410', '', NULL, 1),
('419', 'Quỹ hỗ trợ sắp xếp doanh nghiệp', '410', '', NULL, 1),
('420', 'Quỹ khác thuộc vốn chủ sở hữu', '410', '', NULL, 1),
('421', 'Lợi nhuận sau thuế chưa phân phối', '410', '', NULL, 1),
('421a', 'Lnst chưa phân phối lũy kế đến cuối kỳ trước', '421', '', NULL, 1),
('421b', 'Lnst chưa phân phối kỳ này', '421', '', NULL, 1),
('422', 'Nguồn vốn đầu tư xdcb', '410', '', NULL, 1),
('430', 'Nguồn kinh phí và quỹ khác', '400', '', NULL, 1),
('431', 'Nguồn kinh phí', '430', '', 'V.23', 1),
('432', 'Nguồn kinh phí đã hình thành tscđ', '430', '', NULL, 1),
('439', 'Lợi ích cổ đông thiểu số', '400', '', NULL, 1),
('440', 'Tổng cộng nguồn vốn', NULL, '(440=300+400)', NULL, 1);
CREATE TABLE `idigroup`.`BALANCE_ASSET_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1', `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`SALE_RESULT_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1 , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
INSERT INTO `SALE_RESULT_ITEM` (`ASSET_CODE`, `ASSET_NAME`, `ASSET_PARENT`, `RULE`, `NOTE`, `SO_DU`) VALUES
('01', 'Doanh thu bán hàng và cung cấp dịch vụ', '10', NULL, NULL, -1),
('02', 'Các khoản giảm trừ', '10', NULL, NULL, -1),
('10', 'Doanh thu thuần về bán hàng và cung cấp dịch vụ', '20', '01 - 02', NULL, -1),
('11', 'Giá vốn hàng bán', '20', NULL, NULL, -1),
('20', 'Lợi nhuận gộp về bán hàng và cung cấp dịch vụ', '30', '10 - 11', NULL, -1),
('21', 'Doanh thu hoạt động tài chính', '30', NULL, NULL, -1),
('22', 'Chi phí tài chính', '30', NULL, NULL, -1),
('23', 'Lãi vay phải trả', NULL, NULL, NULL, -1),
('24', 'Chi phí bán hàng', '30', NULL, NULL, -1),
('25', 'Chi phí quản lý doanh nghiệp', '30', NULL, NULL, -1),
('30', 'Lợi nhuận thuần từ hoạt động kinh doanh', '50', '20 + (21 - 22) - (24 + 25)', NULL, -1),
('31', 'Thu nhập khác', '40', NULL, NULL, -1),
('32', 'Chi phí khác', '40', NULL, NULL, -1),
('40', 'Lợi nhuận khác', '50', '31 - 32', NULL, -1),
('41', 'Phần lãi (lỗ) trong liên doanh/liên kết', NULL, NULL, NULL, -1),
('50', 'Tổng lợi nhuận trước thuế', '60', '30 + 40', NULL, -1),
('51', 'Chi phí thuế thu nhập DN hiện hành', '60', NULL, NULL, -1),
('52', 'Chi phí thuế thu nhập DN hoãn lại', '60', NULL, NULL, -1),
('60', 'Lợi nhuận sau thuế', NULL, '50 - 51 - 52', NULL, -1),
('70', 'Lãi cơ bản trên cổ phiếu', NULL, NULL, NULL, -1),
('71', 'Lãi suy giảm trên cổ phiếu', NULL, NULL, NULL, -1);
CREATE TABLE `idigroup`.`SALE_RESULT_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1' , `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`CASH_FLOW_ITEM` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `ASSET_NAME` VARCHAR(255) NOT NULL , `ASSET_PARENT` VARCHAR(10) NULL, `RULE` VARCHAR(255) NULL, `NOTE` VARCHAR(255) NULL , SO_DU TINYINT(1) NOT NULL DEFAULT -1 , PRIMARY KEY (`ASSET_CODE`(10)) ) ENGINE = InnoDB;
INSERT INTO `CASH_FLOW_ITEM` VALUES 
('01','Lợi nhuận trước thuế',NULL,'',NULL,-1),
('02','Khấu hao tscđ và bđsđt',NULL,'',NULL,-1),
('03','Các khoản dự phòng',NULL,'',NULL,-1),
('04','Lãi, lỗ chênh lệch tỷ giá hối đoái do đánh giá lại các khoản mục tiền tệ có gốc ngoại tệ',NULL,'',NULL,-1),
('05','Lãi, lỗ từ hoạt động đầu tư',NULL,'',NULL,-1),
('06','Chi phí lãi vay',NULL,'',NULL,-1),
('07','Thu nhập từ thanh lý các khoản đầu tư trong công ty liên doanh',NULL,'',NULL,-1),
('08','Lợi nhuận từ hoạt động kinh doanh trước thay đổi vốn lưu động',NULL,'',NULL,-1),
('09','Tăng, giảm các khoản phải thu',NULL,'',NULL,-1),
('10','Tăng, giảm hàng tồn kho',NULL,'',NULL,-1),
('11','Tăng, giảm các khoản phải trả (không kể lãi vay phải trả, thuế thu nhập doanh nghiệp phải nộp)',NULL,'',NULL,-1),
('12','Tăng, giảm chi phí trả trước',NULL,'',NULL,-1),
('13','Tăng, giảm chứng khoán kinh doanh',NULL,'',NULL,-1),
('14','Tiền lãi vay đã trả',NULL,'',NULL,-1),
('15','Thuế thu nhập doanh nghiệp đã nộp',NULL,'',NULL,-1),
('16','Tiền thu khác từ hoạt động kinh doanh',NULL,'',NULL,-1),
('17','Tiền chi khác cho hoạt động kinh doanh',NULL,'',NULL,-1),
('20','Lưu chuyển tiền thuần từ hoạt động kinh doanh',NULL,'',NULL,-1),
('21','Tiền chi để mua sắm, xây dựng tscđ và các tài sản dài hạn khác',NULL,'',NULL,-1),
('22','Tiền thu từ thanh lý, nhượng bán tscđ và các tài sản dài hạn khác',NULL,'',NULL,-1),
('23','Tiền chi cho vay, mua các công cụ nợ của đơn vị khác',NULL,'',NULL,-1),
('24','Tiền thu hồi cho vay, bán lại các công cụ nợ của đơn vị khác',NULL,'',NULL,-1),
('25','Tiền chi đầu tư góp vốn vào đơn vị khác',NULL,'',NULL,-1),
('26','Tiền thu hồi đầu tư góp vốn vào đơn vị khác',NULL,'',NULL,-1),
('27','Tiền thu lãi cho vay, cổ tức và lợi nhuận được chia',NULL,'',NULL,-1),
('28','Giảm/(tăng) tiền gửi ngân hàng có kỳ hạn',NULL,'',NULL,-1),
('30','Lưu chuyển tiền thuần từ hoạt động đầu tư',NULL,'',NULL,-1),
('31','Tiền thu từ phát hành cổ phiếu, nhận vốn góp của chủ sở hữu',NULL,'',NULL,-1),
('32','Tiền trả lại vốn góp cho các chủ sở hữu, mua lại cổ phiếu của doanh nghiệp đã phát hành',NULL,'',NULL,-1),
('33','Tiền thu từ đi vay',NULL,'',NULL,-1),
('34','Tiền trả nợ gốc vay',NULL,'',NULL,-1),
('35','Tiền trả nợ gốc thuê tài chính',NULL,'',NULL,-1),
('36','Cổ tức, lợi nhuận đã trả cho chủ sở hữu',NULL,'',NULL,-1),
('40','Lưu chuyển tiền thuần từ hoạt động tài chính',NULL,'',NULL,-1),
('50','Lưu chuyển tiền thuần trong kỳ',NULL,'(50 = 20 + 30 + 40)',NULL,-1),
('60','Tiền và tương đương tiền đầu kỳ',NULL,'',NULL,-1),
('61','Ảnh hưởng của thay đổi tỷ giá hối đoái quy đổi ngoại tệ',NULL,'',NULL,-1),
('70','Tiền và tương đương tiền cuối kỳ',NULL,'(70 = 50 + 60 + 61)','VII.34',-1);
CREATE TABLE `idigroup`.`CASH_FLOW_DATA` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `PERIOD_TYPE` TINYINT(4) NOT NULL DEFAULT '1', `PERIOD` DATE NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSET_CODE`, `PERIOD_TYPE` , `PERIOD`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`KPI_GROUP` ( `GROUP_ID` INT NOT NULL AUTO_INCREMENT , `GROUP_NAME` VARCHAR(255) NOT NULL , PRIMARY KEY (`GROUP_ID`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng thanh toán');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng hoạt động');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng sinh lợi');
INSERT INTO `idigroup`.`KPI_GROUP` (`GROUP_NAME`) VALUES ('Khả năng cân đối nợ');

CREATE TABLE `idigroup`.`KPI_CHART` ( `CHART_ID` INT NOT NULL AUTO_INCREMENT , `CHART_TITLE` VARCHAR(255) NOT NULL , `CHART_TITLE_EN` VARCHAR(255) NULL , `GROUP_ID` INT NOT NULL, HOME_FLAG TINYINT(1) NOT NULL DEFAULT 0 , THRESHOLD DOUBLE NOT NULL DEFAULT 0, PRIMARY KEY (`CHART_ID`) ) ENGINE = InnoDB;
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
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Số ngày tồn kho bình quân theo giá trị sổ sách', '365/KPI.6', 8);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Số ngày tồn kho bình quân theo giá trị thị trường', '365/KPI.7', 9);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách', 'KPI.5+KPI.8', 10);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Chu kỳ hoạt động của doanh nghiệp theo giá trị thị trường', 'KPI.5+KPI.9', 11);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Vòng quay khoản phải trả', 'HĐKD.11.CK/(((CĐKT.310.ĐK+CĐKT.330.ĐK)+(CĐKT.310.CK+CĐKT.330.CK))/2)', 12);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Kỳ phải trả bình quân', '365/KPI.12', 13);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Chu kỳ luân chuyển tiền theo giá trị sổ sách', 'KPI.10/KPI.13', 14);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Chu kỳ luân chuyển tiền theo giá trị thị trường', 'KPI.11/KPI.13', 15);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng thanh toán lãi vay', 'HĐKD.50.CK/HĐKD.23.CK', 16);

-- Các biểu đồ trong nhóm khả năng sinh lợi
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng tổng tài sản (vòng quay tổng tài sản)', 'HĐKD.10.CK/((CĐKT.270.ĐK+CĐKT.270.CK)/2)', 17);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng tài sản cố định', 'HĐKD.10.CK/((CĐKT.220.ĐK+CĐKT.220.CK)/2)', 18);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hiệu suất sử dụng trên vòng quay vốn lưu động', 'HĐKD.10.CK/((CĐKT.100.ĐK+CĐKT.100.CK)/2)', 19);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ suất lợi nhuận gộp (Lợi nhuận gộp biên)', 'HĐKD.20.CK/HĐKD.10.CK', 20);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ suất lợi nhuận ròng (Lợi nhuận ròng biên)', 'HĐKD.60.CK/(HĐKD.01.CK+HĐKD.21.CK)', 21);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Suất sinh lời trên tổng tài sản (Doanh lợi tổng tài sản)', 'HĐKD.60.CK/CĐKT.270.CK', 22);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Suất sinh lời trên vốn chủ sở hữu (Doanh lợi vốn chủ sở hữu)', 'HĐKD.60.CK/CĐKT.400.CK', 23);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Suất sinh lời vốn đầu tư', 'HĐKD.60.CK/HĐKD.30.CK', 24); -- Lưu chuyển tiền tệ, phải sửa code
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Khả năng sinh lời cơ bản', 'HĐKD.50.CK/CĐKT.270.CK', 25);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ số lợi nhuận tích lũy', 'HĐKD.60.CK/(HĐKD.60.CK-HĐKD.36.CK)', 26); -- Lưu chuyển tiền tệ, phải sửa code
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ số tăng trưởng bền vững', '(HĐKD.60.CK-HĐKD.36.CK)/CĐKT.400.CK', 27); -- Lưu chuyển tiền tệ, phải sửa code

-- Các biểu đồ trong nhóm khả năng cân đối nợ
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Hệ số nợ (tỷ số nợ)', 'CĐKT.300.CK/CĐKT.270.CK', 28);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Đòn bẩy tài chính', 'CĐKT.270.CK/CĐKT.400.CK', 29);
INSERT INTO `idigroup`.`KPI_MEASURE` (`MEASURE_NAME`, `EXPRESSION`, `CHART_ID`) VALUES ('Tỷ số khả năng trả nợ', 'CĐKT.270.CK/1000000', 30);

