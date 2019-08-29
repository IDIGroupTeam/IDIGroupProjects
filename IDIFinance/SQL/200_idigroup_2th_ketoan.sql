SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE KHACH_HANG ( `MA_KH` INT NOT NULL AUTO_INCREMENT , `KH_KH` VARCHAR(255) NOT NULL, `TEN_KH` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_KH`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `KHACH_HANG` (`MA_KH`, `TEN_KH`, `MA_THUE`, `DIA_CHI`, `EMAIL`, `SDT`, `WEBSITE`) VALUES
(1, 'Khách vãng lai', '', '', '', '', '');

CREATE TABLE NHA_CUNG_CAP ( `MA_NCC` INT NOT NULL AUTO_INCREMENT , `KH_NCC` VARCHAR(255) NOT NULL, `TEN_NCC` VARCHAR(255) NOT NULL , `MA_THUE` VARCHAR(100) NOT NULL , `DIA_CHI` VARCHAR(255) NOT NULL , `EMAIL` VARCHAR(100) NOT NULL , `SDT` VARCHAR(20) NOT NULL , `WEBSITE` VARCHAR(255) NOT NULL, PRIMARY KEY (`MA_NCC`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `TAI_KHOAN_DANH_MUC` ( `MA_TK` VARCHAR(10) NOT NULL , `TEN_TK` VARCHAR(255) NOT NULL , `MA_TK_CHA` VARCHAR(10) NULL , `SO_DU` TINYINT(1) NOT NULL DEFAULT -1, `MA_TK_NH` INT NULL, `LUONG_TINH` BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY (`MA_TK`)) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
-- Theo thông tư 200 gốc
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
UPDATE `TAI_KHOAN_DANH_MUC` SET `LUONG_TINH`=1 WHERE MA_TK LIKE ('131%') OR MA_TK LIKE ('138%') OR MA_TK LIKE ('331%') OR MA_TK LIKE ('333%') OR MA_TK LIKE ('334%') OR MA_TK LIKE ('338%') OR MA_TK LIKE ('421%')

-- Hệ thống tài khoản đã sửa đổi trên cơ sở hệ thống tài khoản của thông tư 200
INSERT INTO `TAI_KHOAN_DANH_MUC` (`MA_TK`, `TEN_TK`, `MA_TK_CHA`, `SO_DU`, `MA_TK_NH`, `LUONG_TINH`) VALUES
('111', 'Tiền mặt', NULL, -1, NULL, 0),
('1111', 'Tiền Việt Nam', '111', -1, NULL, 0),
('1112', 'Ngoại tệ', '111', -1, NULL, 0),
('1113', 'Vàng tiền tệ', '111', -1, NULL, 0),
('112', 'Tiền gửi ngân hàng', NULL, -1, NULL, 0),
('1121', 'Tiền Việt Nam', '112', -1, NULL, 0),
('1122', 'Ngoại tệ', '112', -1, NULL, 0),
('1123', 'Vàng tiền tệ', '112', -1, NULL, 0),
('113', 'Tiền đang chuyển', NULL, -1, NULL, 0),
('1131', 'Tiền việt nam', '113', -1, NULL, 0),
('1132', 'Ngoại tệ', '113', -1, NULL, 0),
('121', 'Chứng khoán kinh doanh', NULL, -1, NULL, 0),
('1211', 'Cổ phiếu', '121', -1, NULL, 0),
('1212', 'Trái phiếu, tín phiếu', '121', -1, NULL, 0),
('1218', 'Chứng khoán và công cụ tài chính khác', '121', -1, NULL, 0),
('128', 'Đầu tư nắm giữ đến ngày đáo hạn', NULL, -1, NULL, 0),
('1281', 'Tiền gửi có kỳ hạn', '128', -1, NULL, 0),
('12811', 'Tiền gửi có kỳ hạn - ngắn hạn', '1281', -1, NULL, 0),
('12812', 'Tiền gửi có kỳ hạn - dài hạn', '1281', -1, NULL, 0),
('12813', 'Tiền gửi có kỳ hạn - tương đương tiền', '1281', -1, NULL, 0),
('1282', 'Trái phiếu', '128', -1, NULL, 0),
('12821', 'Trái phiếu - ngắn hạn', '1282', -1, NULL, 0),
('12822', 'Trái phiếu - dài hạn', '1282', -1, NULL, 0),
('1283', 'Cho vay', '128', -1, NULL, 0),
('12831', 'Cho vay - ngắn hạn', '1283', -1, NULL, 0),
('12832', 'Cho vay - dài hạn', '1283', -1, NULL, 0),
('1288', 'Các khoản đầu tư khác nắm giữ đến ngày đáo hạn', '128', -1, NULL, 0),
('12881', 'Các khoản đầu tư khác - ngắn hạn', '1288', -1, NULL, 0),
('12882', 'Các khoản đầu tư khác - dài hạn', '1288', -1, NULL, 0),
('12883', 'Các khoản đầu tư khác - tương đương tiền', '1288', -1, NULL, 0),
('131', 'Phải thu khách hàng', NULL, -1, NULL, 1),
('1311', 'Phải thu khách hàng - ngắn hạn', '131', -1, NULL, 1),
('1312', 'Phải thu khách hàng - dài hạn', '131', -1, NULL, 1),
('133', 'Thuế GTGT được khấu trừ', NULL, -1, NULL, 0),
('1331', 'Thuế GTGT được khấu trừ của h/hóa, dịch vụ', '133', -1, NULL, 0),
('1332', 'Thuế GTGT được khấu trừ của TSCĐ', '133', -1, NULL, 0),
('136', 'Phải thu nội bộ', NULL, -1, NULL, 0),
('1361', 'Vốn kinh doanh ở các đơn vị trực thuộc', '136', -1, NULL, 0),
('1362', 'Phải thu nội bộ về chênh lệch tỷ giá', '136', -1, NULL, 0),
('13621', 'Phải thu nội bộ về chênh lệch tỷ giá - ngắn hạn', '1362', -1, NULL, 0),
('13622', 'Phải thu nội bộ về chênh lệch tỷ giá - dài hạn', '1362', -1, NULL, 0),
('1363', 'Phải thu nội bộ về chi phí đi vay đủ điều kiện được vốn hóa', '136', -1, NULL, 0),
('13631', 'Phải thu nội bộ về chi phí vay đủ điều kiện vốn hoá - ngắn hạn', '1363', -1, NULL, 0),
('13632', 'Phải thu nội bộ về chi phí vay đủ điều kiện vốn hoá - dài hạn', '1363', -1, NULL, 0),
('1368', 'Phải thu nội bộ khác', '136', -1, NULL, 0),
('13681', 'Phải thu nội bộ khác - ngắn hạn', '1368', -1, NULL, 0),
('13682', 'Phải thu nội bộ khác - dài hạn', '1368', -1, NULL, 0),
('138', 'Phải thu khác', NULL, -1, NULL, 1),
('1381', 'Tài sản thiếu chờ xử lý', '138', -1, NULL, 1),
('1385', 'Phải thu về cổ phần hóa', '138', -1, NULL, 1),
('13851', 'Phải thu về cổ phần hoá - ngắn hạn', '1385', -1, NULL, 1),
('13852', 'Phải thu về cổ phần hoá - dài hạn', '1385', -1, NULL, 1),
('1388', 'Phải thu khác', '138', -1, NULL, 1),
('13881', 'Phải thu khác - ngắn hạn', '1388', -1, NULL, 1),
('13882', 'Phải thu khác - dài hạn', '1388', -1, NULL, 1),
('141', 'Tạm ứng', NULL, -1, NULL, 0),
('1411', 'Tạm ứng - ngắn hạn', '141', -1, NULL, 0),
('1412', 'Tạm ứng - dài hạn', '141', -1, NULL, 0),
('151', 'Hàng mua đang đi đường', NULL, -1, NULL, 0),
('152', 'Nguyên liệu, vật liệu', NULL, -1, NULL, 0),
('153', 'Công cụ, dụng cụ', NULL, -1, NULL, 0),
('1531', 'Công cụ, dụng cụ', '153', -1, NULL, 0),
('1532', 'Bao bì luân chuyển', '153', -1, NULL, 0),
('1533', 'Đồ dùng cho thuê', '153', -1, NULL, 0),
('1534', 'Thiết bị, phụ tùng thay thế', '153', -1, NULL, 0),
('154', 'Chi phí sản xuất kinh doanh dở dang', NULL, -1, NULL, 0),
('1541', 'Chi phí sản xuất kinh doanh dở dang - ngắn hạn', '154', -1, NULL, 0),
('1542', 'Chi phí sản xuất kinh doanh dở dang - dài hạn', '154', -1, NULL, 0),
('155', 'Thành phẩm', NULL, -1, NULL, 0),
('1551', 'Thành phẩm nhập kho', '155', -1, NULL, 0),
('1557', 'Thành phẩm bất động sản', '155', -1, NULL, 0),
('156', 'Hàng hóa', NULL, -1, NULL, 0),
('1561', 'Giá mua hàng hóa', '156', -1, NULL, 0),
('1562', 'Chi phí thu mua hàng hóa', '156', -1, NULL, 0),
('1567', 'Hàng hóa bất động sản', '156', -1, NULL, 0),
('157', 'Hàng gởi đi bán', NULL, -1, NULL, 0),
('158', 'Hàng hóa kho bảo thuế', NULL, -1, NULL, 0),
('161', 'Chi sự nghiệp', NULL, -1, NULL, 0),
('1611', 'Chi sự nghiệp năm trước', '161', -1, NULL, 0),
('1612', 'Chi sự nghiệp năm nay', '161', -1, NULL, 0),
('171', 'Giao dịch mua bán lại trái phiếu chính phủ', '', -1, NULL, 1),
('211', 'Tài sản cố định hữu hình', NULL, -1, NULL, 0),
('2111', 'Nhà cửa, vật kiến trúc', '211', -1, NULL, 0),
('2112', 'Máy móc, thiết bị', '211', -1, NULL, 0),
('2113', 'Phương tiện vận tải, truyền dẫn', '211', -1, NULL, 0),
('2114', 'Thiết bị, dụng cụ quản lý', '211', -1, NULL, 0),
('2115', 'Cây lâu năm, súc vật làm việc và cho sản phẩm', '211', -1, NULL, 0),
('2118', 'TSCĐ khác', '211', -1, NULL, 0),
('212', 'TSCĐ thuê tài chính', NULL, -1, NULL, 0),
('2121', 'TSCĐ hữu hình thuê tài chính', '212', -1, NULL, 0),
('2122', 'TSCĐ vô hình thuê tài chính', '212', -1, NULL, 0),
('213', 'Tài sản số định vô hình', NULL, -1, NULL, 0),
('2131', 'Quyền sử dụng đất', '213', -1, NULL, 0),
('2132', 'Quyền phát hành', '213', -1, NULL, 0),
('2133', 'Bản quyền, bằng sáng chế', '213', -1, NULL, 0),
('2134', 'Nhãn hiệu hàng hóa', '213', -1, NULL, 0),
('2135', 'Chương trình phần mềm', '213', -1, NULL, 0),
('2136', 'Giấy phép và giấy phép nhượng quyền', '213', -1, NULL, 0),
('2138', 'TSCĐ vô hình khác', '213', -1, NULL, 0),
('214', 'Hao mòn TSCĐ', NULL, -1, NULL, 0),
('2141', 'Hao mòn TSCĐ HH', '214', -1, NULL, 0),
('2142', 'Hao mòn TSCĐ thuê tài chính', '214', -1, NULL, 0),
('2143', 'Hao mòn TSCĐ VH', '214', -1, NULL, 0),
('2147', 'Hao mòn BĐS đầu tư', '214', -1, NULL, 0),
('217', 'Bất động sản đầu tư', NULL, -1, NULL, 0),
('221', 'Đầu tư vào công ty con', NULL, -1, NULL, 0),
('222', 'Đầu tư vào công ty liên doanh, liên kết', NULL, -1, NULL, 0),
('228', 'Đầu tư khác', NULL, -1, NULL, 0),
('2281', 'Đầu tư góp vốn vào đơn vị khác', '228', -1, NULL, 0),
('2288', 'Đầu tư khác', '228', -1, NULL, 0),
('22881', 'Đầu tư khác - ngắn hạn', '2288', -1, NULL, 0),
('22882', 'Đầu tư khác - dài hạn', '2288', -1, NULL, 0),
('229', 'Dự phòng tổn thất tài sản', NULL, -1, NULL, 0),
('2291', 'Dự phòng giảm giá chứng khoán kinh doanh', '229', -1, NULL, 0),
('2292', 'Dự phòng tổn thất đầu tư vào đơn vị khác', '229', -1, NULL, 0),
('2293', 'Dự phòng phải thu khó đòi', '229', -1, NULL, 0),
('22931', 'Dự phòng phải thu khó đòi - ngắn hạn', '2293', -1, NULL, 0),
('22932', 'Dự phòng phải thu khó đòi - dài hạn', '2293', -1, NULL, 0),
('2294', 'Dự phòng giảm giá hàng tồn kho', '229', -1, NULL, 0),
('22941', 'Dự phòng giảm giá hàng tồn kho - ngắn hạn', '2294', -1, NULL, 0),
('22942', 'Dự phòng giảm giá hàng tồn kho - dài hạn', '2294', -1, NULL, 0),
('22943', 'Dự phòng giảm giá hàng tồn kho - dài hạn khác', '2294', -1, NULL, 0),
('241', 'Xây dựng cơ bản dở dang', NULL, -1, NULL, 0),
('2411', 'Mua sắm TSCĐ', '241', -1, NULL, 0),
('2412', 'XD CB DD', '241', -1, NULL, 0),
('2413', 'Sữa chữa lớn TSCĐ', '241', -1, NULL, 0),
('242', 'Chi phí trả trước', NULL, -1, NULL, 0),
('2421', 'Chi phí trả trước - ngắn hạn', '242', -1, NULL, 0),
('2422', 'Chi phí trả trước - dài hạn', '242', -1, NULL, 0),
('243', 'TS thuế thu nhập hoãn lại', NULL, -1, NULL, 0),
('244', 'Cầm cố, thế chấp, ký quỹ ký cược', NULL, -1, NULL, 0),
('2441', 'Cầm cố, thế chấp, ký quỹ, ký cược - ngắn hạn', '244', -1, NULL, 0),
('2442', 'Cầm cố, thế chấp, ký quỹ, ký cược - dài hạn', '244', -1, NULL, 0),
('331', 'Phải trả cho người bán', NULL, 1, NULL, 1),
('3311', 'Phải trả cho người bán - ngắn hạn', '331', -1, NULL, 1),
('3312', 'Phải trả cho người bán - dài hạn', '331', -1, NULL, 1),
('333', 'Thuế và các khoản phải nộp nhà nước', NULL, 1, NULL, 1),
('3331', 'Thuế GTGT phải nộp', '333', 1, NULL, 1),
('33311 ', 'Thuế GTGT đầu ra', '33311', 1, NULL, 1),
('33312 ', 'Thuế GTGT hàng nhập khẩu', '33312', 1, NULL, 1),
('3332', 'Thuế tiêu thụ đặc biệt', '333', 1, NULL, 1),
('3333', 'Thuế xuất nhập khẩu', '333', 1, NULL, 1),
('3334', 'Thuế thu nhập doanh nghiệp', '333', 1, NULL, 1),
('3335', 'Thuế thu nhập các nhân', '333', 1, NULL, 1),
('3336', 'Thuế tài nguyên', '333', 1, NULL, 1),
('3337', 'Thuế nhà đất, tiền thuê đất', '333', 1, NULL, 1),
('3338', 'Thuế bảo vệ môi trường và các loại thuế khác', '333', 1, NULL, 1),
('33381', 'Thuế bảo vệ môi trường', '3338', 1, NULL, 1),
('33382', 'Các loại thuế khác', '3338', 1, NULL, 1),
('3339', 'Phí, lệ phí và các khoản phải nộp khác', '333', 1, NULL, 1),
('334', 'Phải trả người lao động', NULL, 1, NULL, 1),
('3341', 'Phải trả công nhân viên', '334', 1, NULL, 1),
('3348', 'Phải trả người lao động khác', '334', 1, NULL, 1),
('33481', 'Phải trả người lao động khác - ngắn hạn', '3348', -1, NULL, 1),
('33482', 'Phải trả người lao động khác - dài hạn', '3348', -1, NULL, 1),
('335', 'Chi phí phải trả', NULL, 1, NULL, 0),
('3351', 'Chi phí phải trả - ngắn hạn', '335', -1, NULL, 0),
('3352', 'Chi phí phải trả - dài hạn', '335', -1, NULL, 0),
('336', 'Phải trả nội bộ', NULL, 1, NULL, 0),
('3361', 'Phải trả nội bộ về vốn kinh doanh', '336', 1, NULL, 0),
('3362', 'Phải trả nội bộ về chênh lệch tỷ giá', '336', 1, NULL, 0),
('33621', 'Phải trả nội bộ về chênh lệch tỷ giá - ngắn hạn', '3362', -1, NULL, 0),
('33622', 'Phải trả nội bộ về chênh lệch tỷ giá - dài hạn', '3362', -1, NULL, 0),
('3363', 'PTNB về CP đi vay đủ điều kiện được vốn hóa', '336', 1, NULL, 0),
('33631', 'PTNB về CP đi vay đủ điều kiện được vốn hóa - ngắn hạn', '3363', -1, NULL, 0),
('33632', 'PTNB về CP đi vay đủ điều kiện được vốn hóa - dài hạn', '3363', -1, NULL, 0),
('3368', 'Phải trả nội bộ khác', '336', 1, NULL, 0),
('33681', 'Phải trả nội bộ khác - ngắn hạn', '3368', -1, NULL, 0),
('33682', 'Phải trả nội bộ khác - dài hạn', '3368', -1, NULL, 0),
('337', 'Thanh toán theo tiến độ kế hoạch hợp đồng xd', '', 1, NULL, 1),
('338', 'Phải trả, phải nộp khác', NULL, 1, NULL, 1),
('3381', 'TS thừa chờ xử lý', '338', 1, NULL, 1),
('3382', 'Kinh phí công đoàn', '338', 1, NULL, 1),
('3383', 'Bảo hiểm xã hội', '338', 1, NULL, 1),
('3384', 'Bảo hiểm y tế', '338', 1, NULL, 1),
('3385', 'Phải trả cổ phần hóa', '338', 1, NULL, 1),
('3386', 'Bảo hiểm thất nghiệp', '338', 1, NULL, 1),
('3387', 'Doanh thu chưa thực hiện', '338', 1, NULL, 1),
('33871', 'Doanh thu chưa thực hiện - ngắn hạn', '3387', 1, NULL, 1),
('33872', 'Doanh thu chưa thực hiện - dài hạn', '3387', 1, NULL, 1),
('3388', 'Phải trả phải nộp khác', '338', 1, NULL, 1),
('33881', 'Phải trả phải nộp khác - ngắn hạn', '3388', -1, NULL, 1),
('33882', 'Phải trả phải nộp khác - dài hạn', '3388', -1, NULL, 1),
('341', 'Vay và nợ thuê tài chính', NULL, 1, NULL, 0),
('3411', 'Các khoản đi vay', '341', 1, NULL, 0),
('3412', 'Nợ thuê tài chính', '341', 1, NULL, 0),
('343', 'Trái phiếu phát hành', NULL, 1, NULL, 0),
('3431', 'Trái phiếu thường', '343', 1, NULL, 0),
('34311', 'Mệnh giá trái phiếu', '3431', 1, NULL, 0),
('343111', 'Mệnh giá trái phiếu - ngắn hạn', '34311', 1, NULL, 0),
('343112', 'Mệnh giá trài phiếu - dài hạn', '34311', 1, NULL, 0),
('34312', 'Chiết khấu trái phiếu', '3431', 1, NULL, 0),
('34313', 'Phụ trội trái phiếu', '3431', 1, NULL, 0),
('3432', 'Trái phiếu chuyển đổi', '343', 1, NULL, 0),
('344', 'Nhận ký quỹ, ký cược', NULL, 1, NULL, 0),
('3441', 'Nhận ký quỹ, ký cược - ngắn hạn', '344', 1, NULL, 0),
('3442', 'Nhận ký quỹ, ký cược - dài hạn', '344', 1, NULL, 0),
('347', 'Thuế thu nhập  hoãn lại phải trả', NULL, 1, NULL, 0),
('352', 'Dự phòng phải trả', NULL, 1, NULL, 0),
('3521', 'Dự phòng bảo hành sản phẩm hàng hóa', '352', 1, NULL, 0),
('3522', 'Dự phòng bảo hành công trình xây dựng', '352', 1, NULL, 0),
('3523', 'Dự phòng tái cơ cấu doanh nghiệp', '352', 1, NULL, 0),
('3524', 'Dự phòng phải trả khác', '352', 1, NULL, 0),
('353', 'Qũy khen thưởng, phúc lợi', NULL, 1, NULL, 0),
('3531', 'Quỹ khen thưởng', '353', 1, NULL, 0),
('3532', 'Quỹ phúc lợi', '353', 1, NULL, 0),
('3533', 'Quỹ phúc lợi đã hình thành TSCD', '353', 1, NULL, 0),
('3534', 'Quỹ thưởng ban điều hành Cty', '353', 1, NULL, 0),
('356', 'Quỹ phát triển khoa học và công nghệ', NULL, 1, NULL, 0),
('3561', 'Quỹ phát triển khoa học và công nghệ', '356', 1, NULL, 0),
('3562', 'Quỹ PT KH và CN đã hình thành TSCD', '356', 1, NULL, 0),
('357', 'Qũy bình ổn giá', NULL, 1, NULL, 0),
('411', 'Vốn đầu tư của chủ sở hữu', NULL, 1, NULL, 0),
('4111', 'Vốn góp của chủ sở hữu', '411', 1, NULL, 0),
('41111', 'Cổ phiếu phổ thông có quyền biểu quyết', '4111', 1, NULL, 0),
('41112', 'Cổ phiếu ưu đãi', '4111', 1, NULL, 0),
('4112', 'Thặng dư cổ phần', '411', 1, NULL, 0),
('4113', 'Quyền chọn chuyển đổi trái phiếu', '411', 1, NULL, 0),
('4118', 'Vốn khác', '411', 1, NULL, 0),
('412', 'Chênh lệch đánh giá lại TS', NULL, 1, NULL, 0),
('413', 'Chênh lệch tỷ giá hối đoái', NULL, 1, NULL, 0),
('4131', 'Chênh lệch tỷ giá hối đoái do đánh giá lại các khoản mục tiền tệ có gốc ngoại tệ', '413', 1, NULL, 0),
('4132', 'Chênh lệch tỷ giá hối đoái trong giai đoạn trước hoạt động', '413', 1, NULL, 0),
('414', 'Quỹ đầu tư phát triển', NULL, 1, NULL, 0),
('417', 'Qũy hỗ trợ sắp xếp doanh nghiệp', NULL, 1, NULL, 0),
('418', 'Các quỹ khác thuộc VCSH', NULL, 1, NULL, 0),
('419', 'Cổ phiếu quỹ', NULL, 1, NULL, 0),
('421', 'Lợi nhuận chưa phân phối', NULL, 1, NULL, 1),
('4211', 'Lợi nhuận chưa phân phối năm trước', '421', 1, NULL, 1),
('4212', 'Lợi nhuận chưa phân phối năm nay', '421', 1, NULL, 1),
('441', 'Nguồn vốn đầu tư XD CB', NULL, 1, NULL, 0),
('461', 'Nguồn kinh phí sự nghiệp', NULL, 1, NULL, 0),
('466', 'Nguồn kinh phí đã hình thành TSCĐ', NULL, 1, NULL, 0),
('511', 'Doanh thu bán hàng và cung cấp dịch vụ', NULL, 1, NULL, 0),
('5111', 'Doanh thu bán hàng hóa', '511', 1, NULL, 0),
('5112', 'Doanh thu bán các thành phẩm', '511', 1, NULL, 0),
('5113', 'Doanh thu cung cấp dịch vụ', '511', 1, NULL, 0),
('5114', 'Doanh thu trợ cấp trợ giá', '511', 1, NULL, 0),
('5117', 'Doanh thu kinh doanh bất động sản đầu tư', '511', 1, NULL, 0),
('5118', 'Doanh thu khác', '511', 1, NULL, 0),
('515', 'Doanh thu hoạt động tài chính', NULL, 1, NULL, 0),
('521', 'Các khoản giảm trừ doanh thu', NULL, 1, NULL, 0),
('5211', 'Chiết khấu thương mại', '521', 1, NULL, 0),
('5212', 'Giảm giá hàng bán', '521', 1, NULL, 0),
('5213', 'Hàng bán bị trả lại', '521', 1, NULL, 0),
('611', 'Mua hàng (áp dụng cho pp KKĐK)', NULL, -1, NULL, 0),
('6111', 'Mua nguyên liệu, vật liệu', '611', -1, NULL, 0),
('6112', 'Mua hàng hóa', '611', -1, NULL, 0),
('621', 'Chi phí nguyên liệu, vật liệu trực tiếp', NULL, -1, NULL, 0),
('622', 'Chi nhân công trực tiêp', NULL, -1, NULL, 0),
('623', 'Chi phí sử dụng máy thi công', NULL, -1, NULL, 0),
('6231', 'Chi phí nhân công', '623', -1, NULL, 0),
('6232', 'Chi phí vật liệu', '623', -1, NULL, 0),
('6233', 'Chi phí dụng cụ sản xuất', '623', -1, NULL, 0),
('6234', 'Chi phí khấu hao máy thi công', '623', -1, NULL, 0),
('6237', 'Chi phí dịch vụ mua ngoài', '623', -1, NULL, 0),
('6238', 'Chi phí bằng tiền khác', '623', -1, NULL, 0),
('627', 'Chi phí sản xuất chung', NULL, -1, NULL, 0),
('6271', 'Chi phí nhân viên phân xưởng', '627', -1, NULL, 0),
('6272', 'Chi phí nguyên, vật liệu', '627', -1, NULL, 0),
('6273', 'Chi phí dụng cụ sản xuất', '627', -1, NULL, 0),
('6274', 'Chi phí khấu hao TSCĐ', '627', -1, NULL, 0),
('6277', 'Chi phí dịch vụ mua ngoài', '627', -1, NULL, 0),
('6278', 'Chi phí bằng tiền khác', '627', -1, NULL, 0),
('631', 'Giá thành sản xuất ( theo pp KKĐK)', NULL, -1, NULL, 0),
('632', 'Giá vốn hàng bán', NULL, -1, NULL, 0),
('635', 'Chi phí tài chính', NULL, -1, NULL, 0),
('641', 'Chi phí bán hàng', NULL, -1, NULL, 0),
('6411', 'Chi phí nhân viên', '641', -1, NULL, 0),
('6412', 'Chi phí nguyên vật liệu, bao bì', '641', -1, NULL, 0),
('6413', 'Chi phí dụng cụ, đồ dùng', '641', -1, NULL, 0),
('6414', 'Chi phí khấu hao TSCĐ', '641', -1, NULL, 0),
('6415', 'Chi phí bảo hành', '641', -1, NULL, 0),
('6417', 'Chi phí dịch vụ mua ngoài', '641', -1, NULL, 0),
('6418', 'Chi phí bằng tiền khác', '641', -1, NULL, 0),
('642', 'Chi phí quản lý doanh nghiệp', NULL, -1, NULL, 0),
('6421', 'Chi phí nhân viên quản lý', '642', -1, NULL, 0),
('6422', 'Chi phí vật liệu quản lý', '642', -1, NULL, 0),
('6423', 'Chi phí đồ dùng văn phòng', '642', -1, NULL, 0),
('6424', 'Chi phí khấu hao TSCĐ', '642', -1, NULL, 0),
('6425', 'Thuế, phí và lệ phí', '642', -1, NULL, 0),
('6426', 'Chi phí dự phòng', '642', -1, NULL, 0),
('6427', 'Chi phí dịch vụ mua ngoài', '642', -1, NULL, 0),
('6428', 'Chi phí bằng tiền khác', '642', -1, NULL, 0),
('711', 'Thu hập khác', NULL, -1, NULL, 0),
('811', 'Chi phí khác', NULL, -1, NULL, 0),
('821', 'Chi phí thuế TNDN', NULL, -1, NULL, 0),
('8211', 'Chi phí thuế TNDN hiện hành', '821', -1, NULL, 0),
('8212', 'Chi phí thuế TNDN hoãn lại', '821', -1, NULL, 0),
('911', 'Xác định kết quả kinh doanh', NULL, -1, NULL, 0);

CREATE TABLE `CHUNG_TU` (`MA_CT` int(11) NOT NULL AUTO_INCREMENT, `SO_CT` int(11) DEFAULT NULL, `LOAI_CT` VARCHAR(100) DEFAULT NULL, `TINH_CHAT` TINYINT NOT NULL DEFAULT '0', `NGAY_LAP` date NULL, `NGAY_HT` date NOT NULL, `LY_DO` varchar(255) NOT NULL, `LOAI_TIEN` varchar(10) NOT NULL DEFAULT 'VND', `TY_GIA` DOUBLE NOT NULL DEFAULT '1', `KEM_THEO` int(11) DEFAULT NULL, `MA_DT` int(11) NULL, `LOAI_DT` TINYINT(1) NULL, `NGUOI_NOP` varchar(255) NULL, `MA_KHO` INT NULL, `NGAY_TAO` DATE NULL, `NGAY_SUA` DATE NULL, `NGAY_TT` DATE NULL, `TRANG_THAI` TINYINT NULL, PRIMARY KEY (`MA_CT`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `NGOAI_TE` ( `MA_NT` VARCHAR(10) NOT NULL, `TEN_NT` VARCHAR(255) NOT NULL, `MUA_TM` DOUBLE NOT NULL, `MUA_CK` DOUBLE NULL, `BAN_RA` DOUBLE NOT NULL, `THEO_DOI` TINYINT(1) NOT NULL DEFAULT '1', PRIMARY KEY (`MA_NT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `NGOAI_TE` (`MA_NT`, `TEN_NT`, `MUA_TM`, `MUA_CK`, `BAN_RA`) VALUES
('EUR', 'EURO', 26633, 26714, 26952),
('JPY', 'JAPANESE YEN', 199, 201, 203),
('USD', 'US DOLLAR', 22680, 22680, 22750),
('VND', 'Việt Nam Đồng', 1, 1, 1),
('VANG', 'Vàng bạc đá quý', 36300, 36300, 36500);

CREATE TABLE `CDKT_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `MA_TK_GOC` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
-- Theo thông tư 200 
--INSERT INTO `CDKT_TAIKHOAN` VALUES 
--('111','111','111',-1),('111','112','112',-1),('111','113','113',-1),('112','1281','1281',-1),
--('112','1288','1288',-1),('121','121','121',-1),('122','2291','2291',1),('123','1281','1281',-1),
--('123','1282','1282',-1),('123','1288','1288',-1),('131','131','131',-1),('132','331','331',-1),
--('133','1362','1362',-1),('133','1363','1363',-1),('133','1368','1368',-1),('134','337','337',-1),
--('135','1283','1283',-1),('136','1385','1385',-1),('136','1388','1388',-1),('136','141','141',-1),
--('136','244','244',-1),('136','334','334',-1),('136','338','338',-1),('137','2293','2293',1),
--('139','1381','1381',-1),('141','151','151',-1),('141','152','152',-1),('141','153','153',-1),
--('141','154','154',-1),('141','155','155',-1),('141','156','156',-1),('141','157','157',-1),
--('141','158','158',-1),('149','2294','2294',1),('151','242','242',-1),('152','133','133',-1),
--('153','333','333',-1),('154','171','171',-1),('155','2288','2288',-1),('211','131','131',-1),
--('212','331','331',-1),('213','1361','1361',-1),('214','1362','1362',-1),('214','1363','1363',-1),
--('214','1368','1368',-1),('215','1283','1283',-1),('216','1385','1385',-1),('216','1388','1388',-1),
--('216','141','141',-1),('216','244','244',-1),('216','334','334',-1),('216','338','338',-1),
--('219','2293','2293',1),('222','211','211',-1),('223','2141','2141',1),('225','212','212',-1),
--('226','2142','2142',1),('228','213','213',-1),('229','2143','2143',1),('231','217','217',-1),
--('232','2147','2147',1),('241','2294','2294',1),('242','241','241',-1),('251','221','221',-1),
--('252','222','222',-1),('253','2281','2281',-1),('254','2292','2292',1),('255','1281','1281',-1),
--('255','1282','1282',-1),('255','1288','1288',-1),('261','242','242',-1),('262','243','243',-1),
--('263','1534','1534',-1),('263','2294','2294',1),('268','2288','2288',-1),('311','331','331',1),
--('312','131','131',1),('313','333','333',1),('314','334','334',1),('315','335','335',1),
--('316','3362','3362',1),('316','3363','3363',1),('316','3368','3368',1),('317','337','337',1),
--('318','3387','3387',1),('319','138','138',1),('319','338','338',1),('319','344','344',1),
--('320','341','341',1),('320','34311','34311',1),('321','352','352',1),('322','353','353',1),
--('323','357','357',1),('324','171','171',1),('331','331','331',1),('332','131','131',1),
--('333','335','335',1),('334','3361','3361',1),('335','3362','3362',1),('335','3363','3363',1),
--('335','3368','3368',1),('336','3387','3387',1),('337','338','338',1),('337','344','344',1),
--('338','341','341',1),('338','34311','34311',1),('338','34312','34312',-1),('338','34313','34313',1),
--('339','3432','3432',1),('340','41112','41112',1),('341','347','347',1),('342','352','352',1),
--('343','356','356',1),('4111','4111','4111',1),('411a','41111','41111',1),('411b','41112','41112',1),
--('412','4112','4112',1),('413','4113','4113',1),('414','4118','4118',1),('415','412','412',1),
--('417','413','413',1),('418','414','414',1),('419','417','417',1),('420','418','418',1),
--('421a','4211','4211',1),('421a','4212','4212',1),('421b','4212','4212',1),('422','441','441',1),
--('431','161','161',-1),('431','461','461',1),('432','466','466',1);

-- Theo hệ thống tài khoản đã sửa đổi từ hệ thống tài khoản gốc thông tư 200
INSERT INTO `CDKT_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `MA_TK_GOC`, `SO_DU`) VALUES
('111', '111', '111', -1),('111', '112', '112', -1),('111', '113', '113', -1),('112', '12813', '1281', -1),
('112', '12883', '1288', -1),('121', '121', '121', -1),('122', '2291', '2291', 1),('123', '12811', '1281', -1),
('123', '12821', '1282', -1),('123', '12881', '1288', -1),('131', '1311', '131', -1),('132', '3311', '331', -1),
('133', '13621', '1362', -1),('133', '13631', '1363', -1),('133', '13681', '1368', -1),('134', '337', '337', -1),
('135', '12831', '1283', -1),('136', '13851', '1385', -1),('136', '13881', '1388', -1),('136', '1411', '141', -1),
('136', '2441', '244', -1),('136', '33481', '334', -1),('136', '33881', '338', -1),('137', '22931', '2293', 1),
('139', '1381', '1381', -1),('141', '151', '151', -1),('141', '152', '152', -1),('141', '153', '153', -1),
('141', '1541', '154', -1),('141', '155', '155', -1),('141', '156', '156', -1),('141', '157', '157', -1),
('141', '158', '158', -1),('149', '22941', '2294', 1),('151', '2421', '242', -1),('152', '133', '133', -1),
('153', '333', '333', -1),('154', '171', '171', -1),('155', '22881', '2288', -1),('211', '1312', '131', -1),
('212', '3312', '331', -1),('213', '1361', '1361', -1),('214', '13622', '1362', -1),('214', '13632', '1363', -1),
('214', '13682', '1368', -1),('215', '12832', '1283', -1),('216', '13852', '1385', -1),('216', '13882', '1388', -1),
('216', '1412', '141', -1),('216', '2442', '244', -1),('216', '33482', '334', -1),('216', '33882', '338', -1),
('219', '22932', '2293', 1),('222', '211', '211', -1),('223', '2141', '2141', 1),('225', '212', '212', -1),
('226', '2142', '2142', 1),('228', '213', '213', -1),('229', '2143', '2143', 1),('231', '217', '217', -1),
('232', '2147', '2147', 1),('241', '22942', '2294', 1),('242', '241', '241', -1),('251', '221', '221', -1),
('252', '222', '222', -1),('253', '2281', '2281', -1),('254', '2292', '2292', 1),('255', '12812', '1281', -1),
('255', '12822', '1282', -1),('255', '12882', '1288', -1),('261', '2422', '242', -1),('262', '243', '243', -1),
('263', '1534', '1534', -1),('263', '22943', '2294', 1),('268', '22882', '2288', -1),('311', '3311', '331', 1),
('312', '1311', '131', 1),('313', '333', '333', 1),('314', '334', '334', 1),('315', '3351', '335', 1),
('316', '33621', '3362', 1),('316', '33631', '3363', 1),('316', '33681', '3368', 1),('317', '337', '337', 1),
('318', '33871', '3387', 1),('319', '138', '138', 1),('319', '33881', '338', 1),('319', '3441', '344', 1),
('320', '341', '341', 1),('320', '343111', '34311', 1),('321', '352', '352', 1),('322', '353', '353', 1),
('323', '357', '357', 1),('324', '171', '171', 1),('331', '3312', '331', 1),('332', '1312', '131', 1),
('333', '3352', '335', 1),('334', '3361', '3361', 1),('335', '33622', '3362', 1),('335', '33632', '3363', 1),
('335', '33682', '3368', 1),('336', '33872', '3387', 1),('337', '33882', '338', 1),('337', '3442', '344', 1),
('338', '341', '341', 1),('338', '343112', '34311', 1),('338', '34312', '34312', -1),('338', '34313', '34313', 1),
('339', '3432', '3432', 1),('340', '41112', '41112', 1),('341', '347', '347', 1),('342', '352', '352', 1),
('343', '356', '356', 1),('4111', '4111', '4111', 1),('411a', '41111', '41111', 1),('411b', '41112', '41112', 1),
('412', '4112', '4112', 1),('413', '4113', '4113', 1),('414', '4118', '4118', 1),('415', '412', '412', 1),
('417', '413', '413', 1),('418', '414', '414', 1),('419', '417', '417', 1),('420', '418', '418', 1),
('421a', '4211', '4211', 1),('421a', '4212', '4212', 1),('421b', '4212', '4212', 1),('422', '441', '441', 1),
('431', '161', '161', -1),('431', '461', '461', 1),('432', '466', '466', 1);

CREATE TABLE `KY_KE_TOAN` ( `MA_KKT` INT NOT NULL AUTO_INCREMENT , `TEN_KKT` VARCHAR(255) NOT NULL , `BAT_DAU` DATE NOT NULL , `KET_THUC` DATE NOT NULL , `TRANG_THAI` TINYINT NOT NULL , `MAC_DINH` TINYINT NOT NULL, PRIMARY KEY (`MA_KKT`)) ENGINE = InnoDB;
CREATE TABLE `KY_KE_TOAN_SO_DU` (`MA_KKT` INT NOT NULL , `MA_TK` VARCHAR(10) NOT NULL , `MA_DT` INT NOT NULL, `LOAI_DT` TINYINT(1) NOT NULL, `MA_HH` INT NOT NULL, `MA_KHO` INT NOT NULL, `NO_DAU_KY` DOUBLE NOT NULL DEFAULT '0', `NO_DAU_KY_NT` DOUBLE NOT NULL DEFAULT '0', `CO_DAU_KY` DOUBLE NOT NULL DEFAULT '0', `CO_DAU_KY_NT` DOUBLE NOT NULL DEFAULT '0', `NO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0', `NO_CUOI_KY_NT` DOUBLE NOT NULL DEFAULT '0', `CO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0', `CO_CUOI_KY_NT` DOUBLE NOT NULL DEFAULT '0', `LOAI_TIEN` VARCHAR(10) NOT NULL DEFAULT 'VND', SO_LUONG` DOUBLE NOT NULL DEFAULT '0', `MA_DV` INT NOT NULL DEFAULT '0', PRIMARY KEY (`MA_KKT`,`MA_TK`,`MA_DT`,`LOAI_DT`,`MA_HH`,`MA_KHO`)) ENGINE = InnoDB;

CREATE TABLE `NGHIEP_VU_KE_TOAN` ( `MA_NVKT` INT NOT NULL AUTO_INCREMENT , `MA_TK` VARCHAR(10) NOT NULL ,  `SO_TIEN` DOUBLE NOT NULL, `SO_TIEN_NT` DOUBLE NOT NULL DEFAULT '0', `SO_DU` TINYINT(1) NOT NULL DEFAULT '-1', `LY_DO` VARCHAR(255) NULL, `ASSET_CODE` VARCHAR(10) NULL, `MA_DT` INT NULL, `LOAI_DT` TINYINT(1) NULL, PRIMARY KEY (`MA_NVKT`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
INSERT INTO `NGHIEP_VU_KE_TOAN` (`MA_NVKT`, `MA_TK`, `SO_TIEN`, `SO_DU`, `LY_DO`, `ASSET_CODE`) VALUES ('1', '0', '0', '-1', NULL, NULL);
CREATE TABLE `CHUNG_TU_HANG_HOA` ( `MA_CT` INT NOT NULL , `MA_HH` INT NOT NULL ,  `MA_KHO` INT NULL, `SO_LUONG_CT` DOUBLE NULL, `SO_LUONG_TN` DOUBLE NULL, `DON_GIA` DOUBLE NULL, `MA_GIA_KHO` INT NOT NULL, `CHIEU` TINYINT NOT NULL DEFAULT '1', PRIMARY KEY(`MA_CT`, `MA_HH`, `MA_KHO`, `MA_GIA_KHO`, `CHIEU`)) ENGINE = InnoDB;
CREATE TABLE `CHUNG_TU_NVKT` ( `MA_CT` INT NOT NULL , `MA_HH` INT NULL , `MA_KC` INT NULL, `MA_NVKT` INT NOT NULL, `NHOM_DK` TINYINT NOT NULL DEFAULT '0', `LOAI_TK` TINYINT NULL, `THUE_SUAT` DOUBLE NULL ) ENGINE = InnoDB;
CREATE TABLE `BUT_TOAN_KET_CHUYEN` ( `MA_KC` INT NOT NULL AUTO_INCREMENT , `TEN_KC` VARCHAR(255) NOT NULL , `MA_TK_NO` VARCHAR(10) NOT NULL , `MA_TK_CO` VARCHAR(10) NOT NULL , `CONG_THUC` VARCHAR(255) NOT NULL, `MO_TA` VARCHAR(500) NULL, `THU_TU` TINYINT NOT NULL, `LOAI_KC` TINYINT NOT NULL, PRIMARY KEY (`MA_KC`)) ENGINE = InnoDB;
INSERT INTO `BUT_TOAN_KET_CHUYEN` (`MA_KC`, `TEN_KC`, `MA_TK_NO`, `MA_TK_CO`, `CONG_THUC`, `MO_TA`, `THU_TU`, `LOAI_KC`) VALUES
(1, 'Bút toán kết chuyển thuế', '33311', '1331', '1331.NO', '', 1, 1),
(2, 'Bút toán nộp tiền thuế', '33311', '1111', '33311.CO', '', 2, 1),
(3, 'Kết chuyển các khoản giảm trừ doanh thu', '511', '521', '521.NO', '', 3, 1),
(4, 'Kết chuyển doanh thu thuần trong kỳ', '511', '911', '511.CO', '', 4, 1),
(5, 'Kết chuyển doanh thu hoạt động tài chính', '515', '911', '515.CO', '', 5, 1),
(6, 'Kết chuyển chi phí tài chính', '911', '635', '635.NO', '', 6, 1),
(7, 'Kết chuyển giá vốn xuất bán trong kì', '911', '632', '632.NO', '', 7, 1),
(8, 'Kết chuyển chi phí bán hàng', '911', '642', '642.NO', '', 9, 1),
(9, 'Kết chuyển chi phí khác', '911', '811', '811.NO', '', 10, 1),
(10, 'Kết chuyển thu nhập khác', '711', '911', '711.CO', '', 11, 1),
(11, 'Kết chuyển chi phí thuế thu nhập doanh nghiệp hiện hành', '911', '8211', '8211.NO', '', 12, 1),
(12, 'Kết chuyển số chênh lệch chi phí thuế thu nhập hoãn lại', '911', '8212', '8212.NO-8212.CO', '', 13, 1),
(13, 'Kết chuyển chi phí bán hàng', '911', '641', '641.NO', '', 8, 1),
(14, 'Kết chuyển kết quả hoạt động kinh doanh trong kỳ vào lợi nhuận sau thuế chưa phân phối', '911', '4212', '911.CO-911.NO', '', 14, 1);

-- Query để sinh bảng kết quả sản xuất kinh doanh
CREATE TABLE `KQHDKD_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`) ) ENGINE = InnoDB;
INSERT INTO `KQHDKD_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`) VALUES 
('01', '511', 1), 
('02', '511', -1), 
('11', '632', 1), 
('21', '515', -1), 
('22', '635', 1), 
-- ('23', '635', -1), 
('25', '641', 1), 
('26', '642', 1), 
('31', '711', -1), 
('32', '811', 1), 
('51', '8211', 1), 
('52', '8212', 1)

CREATE TABLE `CASH_FLOW_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, `MA_TK_DU` VARCHAR(10) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) ) ENGINE = InnoDB;
INSERT INTO `CASH_FLOW_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) VALUES 
('01', '111', -1, '511'), 
('01', '111', -1, '131'),
('01', '111', -1, '515'),
('01', '111', -1, '121'),
('01', '112', -1, '511'), 
('01', '112', -1, '131'),
('01', '112', -1, '515'),
('01', '112', -1, '121'),
('02', '111', 1, '331'),-- GHI SỐ ÂM -- CÓ CẦN THÊM CÁC TK 151->158 ?
('02', '112', 1, '331'),-- GHI SỐ ÂM -- CÓ CẦN THÊM CÁC TK 151->158 ?
('03', '111', 1, '334'),-- GHI SỐ ÂM
('03', '112', 1, '334'),-- GHI SỐ ÂM
('04', '111', 1, '335'),-- GHI SỐ ÂM
('04', '111', 1, '635'),-- GHI SỐ ÂM
('04', '111', 1, '242'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('04', '112', 1, '335'),-- GHI SỐ ÂM
('04', '112', 1, '635'),-- GHI SỐ ÂM
('04', '112', 1, '242'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('04', '113', 1, '335'),-- GHI SỐ ÂM
('04', '113', 1, '635'),-- GHI SỐ ÂM
('04', '113', 1, '242'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('05', '111', 1, '3334'),-- GHI SỐ ÂM
('05', '112', 1, '3334'),-- GHI SỐ ÂM
('05', '113', 1, '3334'),-- GHI SỐ ÂM -- CÓ TRANG KHÔNG CÓ 113 ?
('06', '111', -1, '711'), 
('06', '111', -1, '133'),
('06', '111', -1, '141'),
('06', '111', -1, '244'), -- VÀ CÁC TK KHÁC
('06', '112', -1, '711'), 
('06', '112', -1, '133'),
('06', '112', -1, '141'),
('06', '112', -1, '244'), -- VÀ CÁC TK KHÁC
('07', '111', 1, '811'),-- GHI SỐ ÂM
('07', '111', 1, '161'),-- GHI SỐ ÂM
('07', '111', 1, '244'),-- GHI SỐ ÂM
('07', '111', 1, '333'),-- GHI SỐ ÂM
('07', '111', 1, '338'),-- GHI SỐ ÂM
('07', '111', 1, '344'),-- GHI SỐ ÂM
('07', '111', 1, '352'),-- GHI SỐ ÂM
('07', '111', 1, '353'),-- GHI SỐ ÂM
('07', '111', 1, '356'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('07', '112', 1, '811'),-- GHI SỐ ÂM
('07', '112', 1, '161'),-- GHI SỐ ÂM
('07', '112', 1, '244'),-- GHI SỐ ÂM
('07', '112', 1, '333'),-- GHI SỐ ÂM
('07', '112', 1, '338'),-- GHI SỐ ÂM
('07', '112', 1, '344'),-- GHI SỐ ÂM
('07', '112', 1, '352'),-- GHI SỐ ÂM
('07', '112', 1, '353'),-- GHI SỐ ÂM
('07', '112', 1, '356'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('07', '113', 1, '811'),-- GHI SỐ ÂM
('07', '113', 1, '161'),-- GHI SỐ ÂM
('07', '113', 1, '244'),-- GHI SỐ ÂM
('07', '113', 1, '333'),-- GHI SỐ ÂM
('07', '113', 1, '338'),-- GHI SỐ ÂM
('07', '113', 1, '344'),-- GHI SỐ ÂM
('07', '113', 1, '352'),-- GHI SỐ ÂM
('07', '113', 1, '353'),-- GHI SỐ ÂM
('07', '113', 1, '356'),-- GHI SỐ ÂM -- VÀ CÁC TK KHÁC
('21', '111', 1, '211'),-- GHI SỐ ÂM
('21', '111', 1, '213'),-- GHI SỐ ÂM
('21', '111', 1, '217'),-- GHI SỐ ÂM
('21', '111', 1, '241'),-- GHI SỐ ÂM
('21', '112', 1, '211'),-- GHI SỐ ÂM
('21', '112', 1, '213'),-- GHI SỐ ÂM
('21', '112', 1, '217'),-- GHI SỐ ÂM
('21', '112', 1, '241'),-- GHI SỐ ÂM
('21', '113', 1, '211'),-- GHI SỐ ÂM
('21', '113', 1, '213'),-- GHI SỐ ÂM
('21', '113', 1, '217'),-- GHI SỐ ÂM
('21', '113', 1, '241'),-- GHI SỐ ÂM
('21', '3411', 1, '211'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '3411', 1, '213'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '3411', 1, '217'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '3411', 1, '241'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '211'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '213'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '217'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('21', '331', 1, '241'),-- GHI SỐ ÂM - NỢ/CÓ ĐẢO LẠI KO ?
('22', '111', -1, '711'), -- THU
('22', '111', -1, '5117'), -- THU
('22', '111', -1, '131'), -- THU
('22', '112', -1, '711'), -- THU
('22', '112', -1, '5117'), -- THU
('22', '112', -1, '131'), -- THU
('22', '113', -1, '711'), -- THU
('22', '113', -1, '5117'), -- THU
('22', '113', -1, '131'), -- THU
('22', '111', 1, '632'), -- CHI
('22', '111', 1, '811'), -- CHI
('22', '112', 1, '632'), -- CHI
('22', '112', 1, '811'), -- CHI
('22', '113', 1, '632'), -- CHI
('22', '113', 1, '811'), -- CHI
('23', '111', 1, '128'),-- GHI SỐ ÂM
('23', '111', 1, '171'),-- GHI SỐ ÂM
('23', '112', 1, '128'),-- GHI SỐ ÂM
('23', '112', 1, '171'),-- GHI SỐ ÂM
('23', '113', 1, '128'),-- GHI SỐ ÂM
('23', '113', 1, '171'),-- GHI SỐ ÂM
('24', '111', -1, '128'),
('24', '111', -1, '171'),
('24', '112', -1, '128'),
('24', '112', -1, '171'),
('24', '113', -1, '128'),
('24', '113', -1, '171'),
('25', '111', 1, '221'),-- GHI SỐ ÂM
('25', '111', 1, '222'),-- GHI SỐ ÂM
('25', '111', 1, '2281'),-- GHI SỐ ÂM
('25', '111', 1, '331'),-- GHI SỐ ÂM
('25', '112', 1, '221'),-- GHI SỐ ÂM
('25', '112', 1, '222'),-- GHI SỐ ÂM
('25', '112', 1, '2281'),-- GHI SỐ ÂM
('25', '112', 1, '331'),-- GHI SỐ ÂM
('25', '113', 1, '221'),-- GHI SỐ ÂM
('25', '113', 1, '222'),-- GHI SỐ ÂM
('25', '113', 1, '2281'),-- GHI SỐ ÂM
('25', '113', 1, '331'),-- GHI SỐ ÂM
('26', '111', -1, '221'),
('26', '111', -1, '222'),
('26', '111', -1, '2281'),
('26', '111', -1, '131'),
('26', '112', -1, '221'),
('26', '112', -1, '222'),
('26', '112', -1, '2281'),
('26', '112', -1, '131'),
('26', '113', -1, '221'),
('26', '113', -1, '222'),
('26', '113', -1, '2281'),
('26', '113', -1, '131'),
('27', '111', -1, '515'),
('27', '112', -1, '515'),
('31', '111', -1, '411'),
('31', '112', -1, '411'),
('31', '113', -1, '411'),
('32', '111', 1, '411'),-- GHI SỐ ÂM
('32', '111', 1, '419'),-- GHI SỐ ÂM
('32', '112', 1, '411'),-- GHI SỐ ÂM
('32', '112', 1, '419'),-- GHI SỐ ÂM
('32', '113', 1, '411'),-- GHI SỐ ÂM
('32', '113', 1, '419'),-- GHI SỐ ÂM
('33', '111', -1, '171'),
('33', '111', -1, '3411'),
('33', '111', -1, '3431'),
('33', '111', -1, '3432'),
('33', '111', -1, '41112'), -- VÀ CÁC TK KHÁC
('33', '112', -1, '171'),
('33', '112', -1, '3411'),
('33', '112', -1, '3431'),
('33', '112', -1, '3432'),
('33', '112', -1, '41112'), -- VÀ CÁC TK KHÁC
('33', '113', -1, '171'),
('33', '113', -1, '3411'),
('33', '113', -1, '3431'),
('33', '113', -1, '3432'),
('33', '113', -1, '41112'), -- VÀ CÁC TK KHÁC -- NGOÀI 111, 112, 113 CÓ THỂ CÒN CÓ TK KHÁC, LÀ TK NÀO ?
('34', '111', 1, '171'),-- GHI SỐ ÂM
('34', '111', 1, '3411'),-- GHI SỐ ÂM
('34', '111', 1, '3431'),-- GHI SỐ ÂM
('34', '111', 1, '3432'),-- GHI SỐ ÂM
('34', '111', 1, '41112'),-- GHI SỐ ÂM
('34', '112', 1, '171'),-- GHI SỐ ÂM
('34', '112', 1, '3411'),-- GHI SỐ ÂM
('34', '112', 1, '3431'),-- GHI SỐ ÂM
('34', '112', 1, '3432'),-- GHI SỐ ÂM
('34', '112', 1, '41112'),-- GHI SỐ ÂM
('34', '113', 1, '171'),-- GHI SỐ ÂM
('35', '111', 1, '3412'),-- GHI SỐ ÂM
('35', '112', 1, '3412'),-- GHI SỐ ÂM
('35', '113', 1, '3412'),-- GHI SỐ ÂM
('36', '111', 1, '421'),-- GHI SỐ ÂM
('36', '111', 1, '338'),-- GHI SỐ ÂM
('36', '112', 1, '421'),-- GHI SỐ ÂM
('36', '112', 1, '338'),-- GHI SỐ ÂM
('36', '113', 1, '421'),-- GHI SỐ ÂM
('36', '113', 1, '338'),-- GHI SỐ ÂM
('61', '111', -1, '4131'),
('61', '112', -1, '4131'),
('61', '113', -1, '4131'),
('61', '128', -1, '4131'),
('61', '111', 1, '4131'),-- GHI SỐ ÂM
('61', '112', 1, '4131'),-- GHI SỐ ÂM
('61', '113', 1, '4131'),-- GHI SỐ ÂM
('61', '128', 1, '4131')-- GHI SỐ ÂM

