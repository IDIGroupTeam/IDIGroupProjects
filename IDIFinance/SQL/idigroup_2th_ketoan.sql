SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE `idigroup`.KHACH_HANG ( `MA_KH` INT NOT NULL AUTO_INCREMENT , `TEN_KH` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_KH`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `KHACH_HANG` (`MA_KH`, `TEN_KH`, `MA_THUE`, `DIA_CHI`, `EMAIL`, `SDT`, `WEBSITE`) VALUES
(1, 'Khách vãng lai', '', '', '', '', '');

CREATE TABLE `idigroup`.NHA_CUNG_CAP ( `MA_NCC` INT NOT NULL AUTO_INCREMENT , `TEN_NCC` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_NCC`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `idigroup`.`TAI_KHOAN_DANH_MUC` ( `MA_TK` VARCHAR(10) NOT NULL , `TEN_TK` VARCHAR(255) NOT NULL , `MA_TK_CHA` VARCHAR(10) NULL , `SO_DU` TINYINT(1) NOT NULL DEFAULT -1, `MA_TK_NH` INT NULL, `LUONG_TINH` BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY (`MA_TK`)) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `TAI_KHOAN_DANH_MUC` (`MA_TK`, `TEN_TK`, `MA_TK_CHA`, `SO_DU`) VALUES
('111', 'Tiền mặt', NULL, -1),
('1111', 'Tiền Việt Nam', '111', -1),
('1112', 'Ngoại tệ', '111', -1),
('1113', 'Vàng tiền tệ', '111', -1),
('112', 'Tiền gửi ngân hàng', NULL, -1),
('1121', 'Tiền Việt Nam', '112', -1),
('1122', 'Ngoại tệ', '112', -1),
('1123', 'Vàng tiền tệ', '112', -1),
('113', 'Tiền đang chuyển', NULL, -1),
('1131', 'Tiền việt nam', '113', -1),
('1132', 'Ngoại tệ', '113', -1),
('121', 'Chứng khoán kinh doanh', NULL, -1),
('1211', 'Cổ phiếu', '121', -1),
('1212', 'Trái phiếu, tín phiếu', '121', -1),
('1218', 'Chứng khoán và công cụ tài chính khác', '121', -1),
('128', 'Đầu tư nắm giữ đến ngày đáo hạn', NULL, -1),
('1281', 'Tiền gửi có kỳ hạn', '128', -1),
('1282', 'Trái phiếu', '128', -1),
('1283', 'Cho vay', '128', -1),
('1288', 'Các khoản đầu tư khác nắm giữ đến ngày đáo hạn', '128', -1),
('131', 'Phải thu khách hàng', NULL, -1),
('133', 'Thuế GTGT được khấu trừ', NULL, -1),
('1331', 'Thuế GTGT được khấu trừ của h/hóa, dịch vụ', '133', -1),
('1332', 'Thuế GTGT được khấu trừ của TSCĐ', '133', -1),
('136', 'Phải thu nội bộ', NULL, -1),
('1361', 'Vốn kinh doanh ở các đơn vị trực thuộc', '136', -1),
('1362', 'Phải thu nội bộ về chênh lệch tỷ giá', '136', -1),
('1363', 'Phải thu nội bộ về chi phí đi vay đủ điều kiện được vốn hóa', '136', -1),
('1368', 'Phải thu nội bộ khác', '136', -1),
('138', 'Phải thu khác', NULL, -1),
('1381', 'Tài sản thiếu chờ xử lý', '138', -1),
('1385', 'Phải thu về cổ phần hóa', '138', -1),
('1388', 'Phải thu khác', '138', -1),
('141', 'Tạm ứng', NULL, -1),
('151', 'Hàng mua đang đi đường', NULL, -1),
('152', 'Nguyên liệu, vật liệu', NULL, -1),
('153', 'Công cụ, dụng cụ', NULL, -1),
('1531', 'Công cụ, dụng cụ', '153', -1),
('1532', 'Bao bì luân chuyển', '153', -1),
('1533', 'Đồ dùng cho thuê', '153', -1),
('1534', 'Thiết bị, phụ tùng thay thế', '153', -1),
('154', 'Chi phí sản xuất kinh doanh dở dang', NULL, -1),
('155', 'Thành phẩm', NULL, -1),
('1551', 'Thành phẩm nhập kho', '155', -1),
('1557', 'Thành phẩm bất động sản', '155', -1),
('156', 'Hàng hóa', NULL, -1),
('1561', 'Giá mua hàng hóa', '156', -1),
('1562', 'Chi phí thu mua hàng hóa', '156', -1),
('1567', 'Hàng hóa bất động sản', '156', -1),
('157', 'Hàng gởi đi bán', NULL, -1),
('158', 'Hàng hóa kho bảo thuế', NULL, -1),
('161', 'Chi sự nghiệp', NULL, -1),
('1611', 'Chi sự nghiệp năm trước', '161', -1),
('1612', 'Chi sự nghiệp năm nay', '161', -1),
('171', 'Giao dịch mua bán lại trái phiếu chính phủ', NULL, -1),
('211', 'Tài sản cố định hữu hình', NULL, -1),
('2111', 'Nhà cửa, vật kiến trúc', '211', -1),
('2112', 'Máy móc, thiết bị', '211', -1),
('2113', 'Phương tiện vận tải, truyền dẫn', '211', -1),
('2114', 'Thiết bị, dụng cụ quản lý', '211', -1),
('2115', 'Cây lâu năm, súc vật làm việc và cho sản phẩm', '211', -1),
('2118', 'TSCĐ khác', '211', -1),
('212', 'TSCĐ thuê tài chính', NULL, -1),
('2121', 'TSCĐ hữu hình thuê tài chính', '212', -1),
('2122', 'TSCĐ vô hình thuê tài chính', '212', -1),
('213', 'Tài sản số định vô hình', NULL, -1),
('2131', 'Quyền sử dụng đất', '213', -1),
('2132', 'Quyền phát hành', '213', -1),
('2133', 'Bản quyền, bằng sáng chế', '213', -1),
('2134', 'Nhãn hiệu hàng hóa', '213', -1),
('2135', 'Chương trình phần mềm', '213', -1),
('2136', 'Giấy phép và giấy phép nhượng quyền', '213', -1),
('2138', 'TSCĐ vô hình khác', '213', -1),
('214', 'Hao mòn TSCĐ', NULL, -1),
('2141', 'Hao mòn TSCĐ HH', '214', -1),
('2142', 'Hao mòn TSCĐ thuê tài chính', '214', -1),
('2143', 'Hao mòn TSCĐ VH', '214', -1),
('2147', 'Hao mòn BĐS đầu tư', '214', -1),
('217', 'Bất động sản đầu tư', NULL, -1),
('221', 'Đầu tư vào công ty con', NULL, -1),
('222', 'Đầu tư vào công ty liên doanh, liên kết', NULL, -1),
('228', 'Đầu tư khác', NULL, -1),
('2281', 'Đầu tư góp vốn vào đơn vị khác', '228', -1),
('2288', 'Đầu tư khác', '228', -1),
('229', 'Dự phòng tổn thất tài sản', NULL, -1),
('2291', 'Dự phòng giảm giá chứng khoán kinh doanh', '229', -1),
('2292', 'Dự phòng tổn thất đầu tư vào đơn vị khác', '229', -1),
('2293', 'Dự phòng phải thu khó đòi', '229', -1),
('2294', 'Dự phòng giảm giá hàng tồn kho', '229', -1),
('241', 'Xây dựng cơ bản dở dang', NULL, -1),
('2411', 'Mua sắm TSCĐ', '241', -1),
('2412', 'XD CB DD', '241', -1),
('2413', 'Sữa chữa lớn TSCĐ', '241', -1),
('242', 'Chi phí trả trước', NULL, -1),
('243', 'TS thuế thu nhập hoãn lại', NULL, -1),
('244', 'Cầm cố, thế chấp, ký quỹ ký cược', NULL, -1),
('331', 'Phải trả cho người bán', NULL, 1),
('333', 'Thuế và các khoản phải nộp nhà nước', NULL, 1),
('3331', 'Thuế GTGT phải nộp', '333', 1),
('33311 ', 'Thuế GTGT đầu ra', '33311', 1),
('33312 ', 'Thuế GTGT hàng nhập khẩu', '33312', 1),
('3332', 'Thuế tiêu thụ đặc biệt', '333', 1),
('3333', 'Thuế xuất nhập khẩu', '333', 1),
('3334', 'Thuế thu nhập doanh nghiệp', '333', 1),
('3335', 'Thuế thu nhập các nhân', '333', 1),
('3336', 'Thuế tài nguyên', '333', 1),
('3337', 'Thuế nhà đất, tiền thuê đất', '333', 1),
('3338', 'Thuế bảo vệ môi trường và các loại thuế khác', '333', 1),
('33381', 'Thuế bảo vệ môi trường', '3338', 1),
('33382', 'Các loại thuế khác', '3338', 1),
('3339', 'Phí, lệ phí và các khoản phải nộp khác', '333', 1),
('334', 'Phải trả người lao động', NULL, 1),
('3341', 'Phải trả công nhân viên', '334', 1),
('3348', 'Phải trả người lao động khác', '334', 1),
('335', 'Chi phí phải trả', NULL, 1),
('336', 'Phải trả nội bộ', NULL, 1),
('3361', 'Phải trả nội bộ về vốn kinh doanh', '336', 1),
('3362', 'Phải trả nội bộ về chênh lệch tỷ giá', '336', 1),
('3363', 'PTNB về CP đi vay đủ điều kiện được vốn hóa', '336', 1),
('3368', 'Phải trả nội bộ khác', '336', 1),
('337', 'Thanh toán theo tiến độ kế hoạch hợp đồng xd', NULL, 1),
('338', 'Phải trả, phải nộp khác', NULL, 1),
('3381', 'TS thừa chờ xử lý', '338', 1),
('3382', 'Kinh phí công đoàn', '338', 1),
('3383', 'Bảo hiểm xã hội', '338', 1),
('3384', 'Bảo hiểm y tế', '338', 1),
('3385', 'Phải trả cổ phần hóa', '338', 1),
('3386', 'Bảo hiểm thất nghiệp', '338', 1),
('3387', 'Doanh thu chưa thực hiện', '338', 1),
('3388', 'Phải trả phải nộp khác', '338', 1),
('341', 'Vay và nợ thuê tài chính', NULL, 1),
('3411', 'Các khoản đi vay', '341', 1),
('3412', 'Nợ thuê tài chính', '341', 1),
('343', 'Trái phiếu phát hành', NULL, 1),
('3431', 'Trái phiếu thường', '343', 1),
('3432', 'Trái phiếu chuyển đổi', '343', 1),
('344', 'Nhận ký quỹ, ký cược', NULL, 1),
('347', 'Thuế thu nhập  hoãn lại phải trả', NULL, 1),
('352', 'Dự phòng phải trả', NULL, 1),
('3521', 'Dự phòng bảo hành sản phẩm hàng hóa', '352', 1),
('3522', 'Dự phòng bảo hành công trình xây dựng', '352', 1),
('3523', 'Dự phòng tái cơ cấu doanh nghiệp', '352', 1),
('3524', 'Dự phòng phải trả khác', '352', 1),
('353', 'Qũy khen thưởng, phúc lợi', NULL, 1),
('3531', 'Quỹ khen thưởng', '353', 1),
('3532', 'Quỹ phúc lợi', '353', 1),
('3533', 'Quỹ phúc lợi đã hình thành TSCD', '353', 1),
('3534', 'Quỹ thưởng ban điều hành Cty', '353', 1),
('356', 'Quỹ phát triển khoa học và công nghệ', NULL, 1),
('3561', 'Quỹ phát triển khoa học và công nghệ', '356', 1),
('3562', 'Quỹ PT KH và CN đã hình thành TSCD', '356', 1),
('357', 'Qũy bình ổn giá', NULL, 1),
('411', 'Vốn đầu tư của chủ sở hữu', NULL, 1),
('4111', 'Vốn góp của chủ sở hữu', '411', 1),
('41111', 'Cổ phiếu phổ thông có quyền biểu quyết', '4111', 1),
('41112', 'Cổ phiếu ưu đãi', '4111', 1),
('4112', 'Thặng dư cổ phần', '411', 1),
('4113', 'Quyền chọn chuyển đổi trái phiếu', '411', 1),
('4118', 'Vốn khác', '411', 1),
('412', 'Chênh lệch đánh giá lại TS', NULL, 1),
('413', 'Chênh lệch tỷ giá hối đoái', NULL, 1),
('4131', 'Chênh lệch tỷ giá hối đoái do đánh giá lại các khoản mục tiền tệ có gốc ngoại tệ', '413', 1),
('4132', 'Chênh lệch tỷ giá hối đoái trong giai đoạn trước hoạt động', '413', 1),
('414', 'Quỹ đầu tư phát triển', NULL, 1),
('417', 'Qũy hỗ trợ sắp xếp doanh nghiệp', NULL, 1),
('418', 'Các quỹ khác thuộc VCSH', NULL, 1),
('419', 'Cổ phiếu quỹ', NULL, 1),
('421', 'Lợi nhuận chưa phân phối', NULL, 1),
('4211', 'Lợi nhuận chưa phân phối năm trước', '421', 1),
('4212', 'Lợi nhuận chưa phân phối năm nay', '421', 1),
('441', 'Nguồn vốn đầu tư XD CB', NULL, 1),
('466', 'Nguồn kinh phí đã hình thành TSCĐ', NULL, 1),
('511', 'Doanh thu bán hàng và cung cấp dịch vụ', NULL, 1),
('5111', 'Doanh thu bán hàng hóa', '511', 1),
('5112', 'Doanh thu bán các thành phẩm', '511', 1),
('5113', 'Doanh thu cung cấp dịch vụ', '511', 1),
('5114', 'Doanh thu trợ cấp trợ giá', '511', 1),
('5117', 'Doanh thu kinh doanh bất động sản đầu tư', '511', 1),
('5118', 'Doanh thu khác', '511', 1),
('515', 'Doanh thu hoạt động tài chính', NULL, 1),
('521', 'Các khoản giảm trừ doanh thu', NULL, 1),
('5211', 'Chiết khấu thương mại', '521', 1),
('5212', 'Giảm giá hàng bán', '521', 1),
('5213', 'Hàng bán bị trả lại', '521', 1),
('611', 'Mua hàng (áp dụng cho pp KKĐK)', NULL, -1),
('6111', 'Mua nguyên liệu, vật liệu', '611', -1),
('6112', 'Mua hàng hóa', '611', -1),
('621', 'Chi phí nguyên liệu, vật liệu trực tiếp', NULL, -1),
('622', 'Chi nhân công trực tiêp', NULL, -1),
('623', 'Chi phí sử dụng máy thi công', NULL, -1),
('6231', 'Chi phí nhân công', '623', -1),
('6232', 'Chi phí vật liệu', '623', -1),
('6233', 'Chi phí dụng cụ sản xuất', '623', -1),
('6234', 'Chi phí khấu hao máy thi công', '623', -1),
('6237', 'Chi phí dịch vụ mua ngoài', '623', -1),
('6238', 'Chi phí bằng tiền khác', '623', -1),
('627', 'Chi phí sản xuất chung', NULL, -1),
('6271', 'Chi phí nhân viên phân xưởng', '627', -1),
('6272', 'Chi phí nguyên, vật liệu', '627', -1),
('6273', 'Chi phí dụng cụ sản xuất', '627', -1),
('6274', 'Chi phí khấu hao TSCĐ', '627', -1),
('6277', 'Chi phí dịch vụ mua ngoài', '627', -1),
('6278', 'Chi phí bằng tiền khác', '627', -1),
('631', 'Giá thành sản xuất ( theo pp KKĐK)', NULL, -1),
('632', 'Giá vốn hàng bán', NULL, -1),
('635', 'Chi phí tài chính', NULL, -1),
('641', 'Chi phí bán hàng', NULL, -1),
('6411', 'Chi phí nhân viên', '641', -1),
('6412', 'Chi phí nguyên vật liệu, bao bì', '641', -1),
('6413', 'Chi phí dụng cụ, đồ dùng', '641', -1),
('6414', 'Chi phí khấu hao TSCĐ', '641', -1),
('6415', 'Chi phí bảo hành', '641', -1),
('6417', 'Chi phí dịch vụ mua ngoài', '641', -1),
('6418', 'Chi phí bằng tiền khác', '641', -1),
('642', 'Chi phí quản lý doanh nghiệp', NULL, -1),
('6421', 'Chi phí nhân viên quản lý', '642', -1),
('6422', 'Chi phí vật liệu quản lý', '642', -1),
('6423', 'Chi phí đồ dùng văn phòng', '642', -1),
('6424', 'Chi phí khấu hao TSCĐ', '642', -1),
('6425', 'Thuế, phí và lệ phí', '642', -1),
('6426', 'Chi phí dự phòng', '642', -1),
('6427', 'Chi phí dịch vụ mua ngoài', '642', -1),
('6428', 'Chi phí bằng tiền khác', '642', -1),
('711', 'Thu hập khác', NULL, -1),
('811', 'Chi phí khác', NULL, -1),
('821', 'Chi phí thuế TNDN', NULL, -1),
('8211', 'Chi phí thuế TNDN hiện hành', '821', -1),
('8212', 'Chi phí thuê TNDN hoãn lại', '821', -1),
('911', 'Xác định kết quả kinh doanh', NULL, -1),
('34311', 'Mệnh giá trái phiếu', '3431', 1),
('34312', 'Chiết khấu trái phiếu', '3431', 1),
('34313', 'Phụ trội trái phiếu', '3431', 1),
('461', 'Nguồn kinh phí sự nghiệp', NULL, 1);

CREATE TABLE `idigroup`.`CHUNG_TU_TAI_KHOAN` ( `MA_CT` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` double NOT NULL, `SO_DU` TINYINT(1) NOT NULL DEFAULT '-1', `LY_DO` VARCHAR(255) NULL, `ASSET_CODE` VARCHAR(10) NULL, PRIMARY KEY (`MA_CT`, `MA_TK`, `SO_DU`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE `idigroup`.`CHUNG_TU` (`MA_CT` int(11) NOT NULL AUTO_INCREMENT, `SO_CT` int(11) DEFAULT NULL, `LOAI_CT` varchar(100) DEFAULT NULL, `TINH_CHAT` TINYINT NOT NULL DEFAULT '0', `NGAY_LAP` date NULL, `NGAY_HT` date NOT NULL, `LY_DO` varchar(255) NOT NULL, `LOAI_TIEN` varchar(10) NOT NULL DEFAULT 'VND', `TY_GIA` double NOT NULL DEFAULT '1', `KEM_THEO` int(11) DEFAULT NULL, `MA_DT` int(11) NULL, `LOAI_DT` TINYINT(1) NULL, `NGUOI_NOP` varchar(255) NULL, `MA_KHO` INT NULL, `NGAY_TAO` DATE NULL, `NGAY_SUA` DATE NULL, `NGAY_TT` DATE NULL, `TRANG_THAI` TINYINT NULL, PRIMARY KEY (`MA_CT`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `idigroup`.`NGOAI_TE` ( `MA_NT` VARCHAR(10) NOT NULL, `TEN_NT` VARCHAR(255) NOT NULL, `MUA_TM` DOUBLE NOT NULL, `MUA_CK` DOUBLE NULL, `BAN_RA` DOUBLE NOT NULL, `THEO_DOI` TINYINT(1) NOT NULL DEFAULT '1', PRIMARY KEY (`MA_NT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `NGOAI_TE` (`MA_NT`, `TEN_NT`, `MUA_TM`, `MUA_CK`, `BAN_RA`) VALUES
('EUR', 'EURO', 26633, 26714, 26952),
('JPY', 'JAPANESE YEN', 199, 201, 203),
('USD', 'US DOLLAR', 22680, 22680, 22750),
('VND', 'Việt Nam Đồng', 1, 1, 1),
('VANG', 'Vàng bạc đá quý', 36300, 36300, 36500);

CREATE TABLE `idigroup`.`CDKT_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('111', '111', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('111', '112', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('111', '113', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('112', '1281', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('112', '1288', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('121', '121', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('122', '2291', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('123', '1281', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('123', '1282', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('123', '1288', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('131', '131', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('132', '331', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('133', '1362', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('133', '1363', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('133', '1368', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('134', '337', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('135', '1283', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '1385', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '1388', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '334', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '338', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '141', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('136', '244', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('137', '2293', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('139', '1381', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '151', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '152', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '153', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '154', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '155', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '156', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '157', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('141', '158', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('149', '2294', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('151', '242', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('152', '133', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('153', '333', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('154', '171', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('155', '2288', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('211', '131', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('212', '331', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('213', '1361', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('214', '1362', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('214', '1363', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('214', '1368', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('215', '1283', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '1385', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '1388', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '334', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '338', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '141', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('216', '244', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('219', '2293', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('222', '211', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('223', '2141', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('225', '212', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('226', '2142', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('228', '213', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('229', '2143', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('231', '217', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('232', '2147', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('241', '2294', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('242', '241', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('251', '221', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('252', '222', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('253', '2281', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('254', '2292', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('255', '1281', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('255', '1282', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('255', '1288', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('261', '242', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('262', '243', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('263', '1534', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('263', '2294', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('268', '2288', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('311', '331', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('312', '131', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('313', '333', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('314', '334', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('315', '335', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('316', '3362', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('316', '3363', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('316', '3368', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('317', '337', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('318', '3387', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('319', '338', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('319', '344', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('319', '138', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('320', '341', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('320', '34311', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('321', '352', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('322', '353', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('323', '357', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('324', '171', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('331', '331', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('332', '131', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('333', '335', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('334', '3361', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('335', '3362', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('335', '3363', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('335', '3368', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('336', '3387', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('337', '338', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('337', '344', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('338', '34311', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('338', '34312', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('338', '34313', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('338', '341', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('339', '3432', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('340', '41112', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('341', '347', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('342', '352', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('343', '356', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('4111', '4111', '1'); -- Tự thêm vào
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('411a', '41111', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('411b', '41112', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('412', '4112', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('413', '4113', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('414', '4118', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('415', '412', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('417', '413', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('418', '414', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('419', '417', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('420', '418', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('421a', '4211', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('421a', '4212', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('421b', '4212', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('422', '441', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('431', '461', '1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('431', '161', '-1');
INSERT INTO `idigroup`.`CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES ('432', '466', '1');

CREATE TABLE `idigroup`.`KY_KE_TOAN` ( `MA_KKT` INT NOT NULL AUTO_INCREMENT , `TEN_KKT` VARCHAR(255) NOT NULL , `BAT_DAU` DATE NOT NULL , `KET_THUC` DATE NOT NULL , `TRANG_THAI` TINYINT NOT NULL , `MAC_DINH` TINYINT NOT NULL, PRIMARY KEY (`MA_KKT`)) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`KY_KE_TOAN_SO_DU` (`MA_KKT` INT NOT NULL , `MA_TK` VARCHAR(10) NULL , `MA_DT` INT NULL, `NO_DAU_KY` DOUBLE NULL, `CO_DAU_KY` DOUBLE NULL, `NO_CUOI_KY` DOUBLE NULL, `CO_CUOI_KY` DOUBLE NULL, PRIMARY KEY (`MA_KKT`, `MA_TK`)) ENGINE = InnoDB;

CREATE TABLE `idigroup`.`NGHIEP_VU_KE_TOAN` ( `MA_NVKT` INT NOT NULL AUTO_INCREMENT , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` double NOT NULL, `SO_DU` TINYINT(1) NOT NULL DEFAULT '-1', `LY_DO` VARCHAR(255) NULL, `ASSET_CODE` VARCHAR(10) NULL, PRIMARY KEY (`MA_NVKT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `idigroup`.`NGHIEP_VU_KE_TOAN` (`MA_NVKT`, `MA_TK`, `SO_TIEN`, `SO_DU`, `LY_DO`, `ASSET_CODE`) VALUES ('1', '0', '0', '-1', NULL, NULL);
CREATE TABLE `idigroup`.`CHUNG_TU_HANG_HOA` ( `MA_CT` INT NOT NULL , `MA_HH` INT NULL ,  `MA_KHO` INT NULL, `SO_LUONG_CT` DOUBLE NULL, `SO_LUONG_TN` DOUBLE NULL, `DON_GIA` DOUBLE NULL, `MA_GIA_KHO` INT NOT NULL, `CHIEU` TINYINT NOT NULL DEFAULT '1', PRIMARY KEY( `MA_CT`, `MA_HH`)) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`CHUNG_TU_NVKT` ( `MA_CT` INT NOT NULL , `MA_HH` INT NULL , `MA_KC` INT NULL, `MA_NVKT` INT NOT NULL, `NHOM_DK` TINYINT NOT NULL DEFAULT '0', `LOAI_TK` TINYINT NULL, `THUE_SUAT` DOUBLE NULL ) ENGINE = InnoDB;
CREATE TABLE `idigroup`.`BUT_TOAN_KET_CHUYEN` ( `MA_KC` INT NOT NULL AUTO_INCREMENT , `TEN_KC` VARCHAR(255) NOT NULL , `MA_TK_NO` VARCHAR(10) NOT NULL , `MA_TK_CO` VARCHAR(10) NOT NULL , `CONG_THUC` VARCHAR(255) NOT NULL, `MO_TA` VARCHAR(500) NULL, `THU_TU` TINYINT NOT NULL, `LOAI_KC` TINYINT NOT NULL, PRIMARY KEY (`MA_KC`)) ENGINE = InnoDB;


