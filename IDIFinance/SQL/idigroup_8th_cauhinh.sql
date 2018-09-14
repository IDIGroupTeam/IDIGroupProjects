CREATE TABLE `CAU_HINH` ( `MA` VARCHAR(255) NOT NULL , `TEN` VARCHAR(255) NOT NULL , `GIA_TRI` VARCHAR(255) NOT NULL , PRIMARY KEY (`MA`)) ENGINE = InnoDB;
INSERT INTO `cau_hinh` (`MA`, `TEN`, `GIA_TRI`) VALUES
('CHU_TICH', 'Chủ tịch Hội đồng quản trị', 'NGUYỄN CÔNG ĐIỂM'),
('DIA_CHI', 'Địa chỉ', 'Số 10, Võng Thị, Quận Tây Hồ, Thành phố Hà Nội'),
('GIAM_DOC', 'Tổng giám đốc', 'NGUYỄN CÔNG ĐIỂM'),
('KE_TOAN_TRUONG', 'Kế toán trưởng', 'ĐỖ VINH'),
('TEN_CONG_TY', 'Tên công ty', 'Tập đoàn IDI'),
('THU_KHO', 'Thủ kho', 'ĐỖ HẢI'),
('THU_QUY', 'Thủ quỹ', 'ĐỖ VINH');