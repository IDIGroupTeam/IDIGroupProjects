ALTER TABLE `KHACH_HANG` ADD `KH_KH` VARCHAR(255) NOT NULL AFTER `MA_KH`;
ALTER TABLE `KHACH_HANG` ADD UNIQUE(`KH_KH`)
ALTER TABLE `NHA_CUNG_CAP` ADD `KH_NCC` VARCHAR(255) NOT NULL AFTER `MA_NCC`;
ALTER TABLE `NHA_CUNG_CAP` ADD UNIQUE(`KH_NCC`)

CREATE TABLE `CAU_HINH` ( `MA` VARCHAR(255) NOT NULL , `TEN` VARCHAR(255) NOT NULL , `GIA_TRI` VARCHAR(255) NOT NULL , `NHOM` INT NOT NULL, PRIMARY KEY (`MA`)) ENGINE = InnoDB;
INSERT INTO `cau_hinh` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES
('CHU_TICH', 'Chủ tịch Hội đồng quản trị', 'NGUYỄN CÔNG ĐIỂM', 1),
('DIA_CHI', 'Địa chỉ', 'Số 10, Võng Thị, Quận Tây Hồ, Thành phố Hà Nội', 1),
('GIAM_DOC', 'Tổng giám đốc', 'NGUYỄN CÔNG ĐIỂM', 1),
('KE_TOAN_TRUONG', 'Kế toán trưởng', 'ĐỖ VINH', 1),
('TEN_CONG_TY', 'Tên công ty', 'Tập đoàn IDI', 1),
('THU_KHO', 'Thủ kho', 'ĐỖ HẢI', 1),
('THU_QUY', 'Thủ quỹ', 'ĐỖ VINH', 1);

INSERT INTO `cau_hinh` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES 
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

ALTER TABLE `KY_KE_TOAN_SO_DU` ADD `MA_HH` INT NOT NULL AFTER `LOAI_DT`, ADD `MA_KHO` INT NOT NULL AFTER `MA_HH`, ADD `SO_LUONG` DOUBLE NULL AFTER `CO_CUOI_KY`;
ALTER TABLE `KY_KE_TOAN_SO_DU` DROP PRIMARY KEY, ADD PRIMARY KEY(`MA_KKT`, `MA_TK`, `MA_DT`, `LOAI_DT`, `MA_HH`, `MA_KHO`);

-- Bỏ lưu chuyển tiền tệ theo phương pháp gián tiếp:
DELETE FROM `CASH_FLOW_ITEM`

-- Thêm lưu chuyển tiền tệ theo phương pháp trực tiếp
INSERT INTO `CASH_FLOW_ITEM` VALUES 
('01','Tiền thu từ bán hàng, cung cấp dịch vụ và doanh thu khác','20','',NULL,-1),
('02','Tiền chi trả cho người cung cấp hàng hoá và dịch vụ','20','',NULL,-1),
('03','Tiền chi trả cho người lao động','20','',NULL,-1),
('04','Tiền lãi vay đã trả','20','',NULL,-1),
('05','Thuế thu nhập doanh nghiệp đã nộp','20','',NULL,-1),
('06','Tiền thu khác từ hoạt động kinh doanh','20','',NULL,-1),
('07','Tiền chi khác cho hoạt động kinh doanh','20','',NULL,-1),
('20','Lưu chuyển tiền thuần từ hoạt động kinh doanh','50','01+02+03+04+05+06+07',NULL,-1),
('21','Tiền chi để mua sắm, xây dựng TSCĐ và các tài sản dài hạn khác','30','',NULL,-1),
('22','Tiền thu từ thanh lý, nhượng bán TSCĐ và các tài sản dài hạn khác','30','',NULL,-1),
('23','Tiền chi cho vay, mua các công cụ nợ của đơn vị khác','30','',NULL,-1),
('24','Tiền thu hồi cho vay, bán lại các công cụ nợ của đơn vị khác','30','',NULL,-1),
('25','Tiền chi đầu tư góp vốn vào đơn vị khác','30','',NULL,-1),
('26','Tiền thu hồi đầu tư góp vốn vào đơn vị khác','30','',NULL,-1),
('27','Tiền thu lãi cho vay, cổ tức và lợi nhuận được chia','30','',NULL,-1),
('30','Lưu chuyển tiền thuần từ hoạt động đầu tư','50','21+22+23+24+25+26+27',NULL,-1),
('31','Tiền thu từ phát sinh cổ phiếu, nhận vốn góp của chủ sở hữu','40','',NULL,-1),
('32','Tiền trả lại vốn góp cho các chủ sở hữu, mua lại cổ phiếu của doanh nghiệp đã phát hành','40','',NULL,-1),
('33','Tiền thu từ đi vay','40','',NULL,-1),
('34','Tiền trả nợ gốc vay','40','',NULL,-1),
('35','Tiền trả nợ gốc thuê tài chính','40','',NULL,-1),
('36','Cổ tức, lợi nhuận đã trả cho chủ sở hữu','40','',NULL,-1),
('40','Lưu chuyển tiền thuần từ hoạt động tài chính','50','31+32+33+34+35+36',NULL,-1),
('50','Lưu chuyển tiền thuần trong kỳ','70','50 = 20 + 30 + 40',NULL,-1),
('60','Tiền và tương đương tiền đầu kỳ','70','',NULL,-1),
('61','Ảnh hưởng của thay đổi tỷ giá hối đoái quy đổi ngoại tệ','70','',NULL,-1),
('70','Tiền và tương đương tiền cuối kỳ',NULL,'70 = 50 + 60 + 61',NULL,-1);

CREATE TABLE `idigroup`.`CASH_FLOW_TAIKHOAN` ( `ASSET_CODE` VARCHAR(10) NOT NULL , `MA_TK` VARCHAR(10) NOT NULL, `SO_DU` TINYINT(1) NOT NULL, `MA_TK_DU` VARCHAR(10) NOT NULL, PRIMARY KEY (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) ) ENGINE = InnoDB;
INSERT INTO `idigroup`.`CASH_FLOW_TAIKHOAN` (`ASSET_CODE`, `MA_TK`, `SO_DU`, `MA_TK_DU`) VALUES 
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

UPDATE `KQHDKD_TAIKHOAN` SET `MA_TK`='511', `SO_DU`=-1 WHERE `ASSET_CODE`='02'

ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `NO_DAU_KY` `NO_DAU_KY` DOUBLE NOT NULL;
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `CO_DAU_KY` `CO_DAU_KY` DOUBLE NOT NULL;
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `NO_CUOI_KY` `NO_CUOI_KY` DOUBLE NOT NULL;
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `CO_CUOI_KY` `CO_CUOI_KY` DOUBLE NOT NULL;
ALTER TABLE `KY_KE_TOAN_SO_DU` ADD `MA_DV` INT NOT NULL AFTER `SO_LUONG`;