CREATE TABLE `CAU_HINH` ( `MA` VARCHAR(255) NOT NULL , `TEN` VARCHAR(255) NOT NULL , `GIA_TRI` VARCHAR(255) NOT NULL , `NHOM` INT NOT NULL, PRIMARY KEY (`MA`)) ENGINE = InnoDB;
INSERT INTO `CAU_HINH` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES
('CHU_TICH', 'Chủ tịch Hội đồng quản trị', 'NGUYỄN CÔNG ĐIỂM', 1),
('DIA_CHI', 'Địa chỉ', 'Số 10, Võng Thị, Quận Tây Hồ, Thành phố Hà Nội', 1),
('GIAM_DOC', 'Tổng giám đốc', 'NGUYỄN CÔNG ĐIỂM', 1),
('KE_TOAN_TRUONG', 'Kế toán trưởng', 'ĐỖ VINH', 1),
('TEN_CONG_TY', 'Tên công ty', 'Tập đoàn IDI', 1),
('THU_KHO', 'Thủ kho', 'ĐỖ HẢI', 1),
('THU_QUY', 'Thủ quỹ', 'ĐỖ VINH', 1),
('CHE_DO_KE_TOAN', 'Chế độ kế toán', 'Thông tư 200', 1);

INSERT INTO `CAU_HINH` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES 
('PHIEU_THU_DS_TK_NO', 'Phiếu thu: tài khoản tiền mặt, nợ', '111;1111;1112;1113', 2), 
('PHIEU_CHI_DS_TK_CO', 'Phiếu chi: tài khoản tiền mặt, có', '111;1111;1112;1113', 2), 
('BAO_NO_DS_TK_NO', 'Báo nợ: tài khoản ngân hàng, nợ', '112;1121;1122;1123', 2), 
('BAO_CO_DS_TK_CO', 'Báo có: tài khoản ngân hàng, có', '112;1121;1122;1123', 2), 
('MUA_HANG_DS_TK_KHO_NO', 'Mua hàng: tài khoản kho, nợ', '152;1531;1532;1533;1534;1561', 2), 
('MUA_HANG_DS_TK_CONG_NO_CO', 'Mua hàng: tài khoản công nợ, có', '1111;1112;1121;1122;331;3311;3312', 2), 
('MUA_HANG_DS_TK_GTGT_NO', 'Mua hàng: tài khoản thuế Gtgt, nợ', '133;1331;1332', 2), 
('MUA_HANG_DS_TK_NK_CO', 'Mua hàng: tài khoản thuế nhập khẩu, có', '3333', 2), 
('MUA_HANG_DS_TK_TTDB_CO', 'Mua hàng: tài khoản thuế tiêu thụ đặc biệt, có', '3332', 2), 
('BAN_HANG_DS_TK_CONG_NO_NO', 'Bán hàng: tài khoản công nợ, nợ', '1111;1112;1121;1122;131;1311;1312', 2), 
('BAN_HANG_DS_TK_DOANH_THU_CO', 'Bán hàng: tài khoản doanh thu, có', '5111', 2), 
('BAN_HANG_DS_TK_GIA_VON_NO', 'Bán hàng: tài khoản giá vốn, nợ', '632', 2), 
('BAN_HANG_DS_TK_KHO_NO', 'Bán hàng: tài khoản kho, nợ', '152;156;1561', 2), 
('BAN_HANG_DS_TK_GTGT_CO', 'Bán hàng: tài khoản thuế Gtgt, có', '33311', 2), 
('BAN_HANG_DS_TK_XK_CO', 'Bán hàng: tài khoản thuế xuất khẩu', '3333', 2);

