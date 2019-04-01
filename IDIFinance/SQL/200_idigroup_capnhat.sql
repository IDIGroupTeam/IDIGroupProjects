ALTER TABLE `HANG_HOA_NHOM` CHANGE `MA_NHOM_HH_CHA` `MA_NHOM_HH_CHA` INT(11) NULL DEFAULT '0';

INSERT INTO `CAU_HINH` (`MA`, `TEN`, `GIA_TRI`, `NHOM`) VALUES 
('TAI_KHOAN_CONG_NO', 'Tài khoản công nợ', '131;141;331;334', 3), 
('TAI_KHOAN_KHO_VTHH', 'Tài khoản kho vật tư hàng hóa', '152;153;154;155;156', 3), 
('CHE_DO_KE_TOAN', 'Chế độ kế toán', 'Thông tư 133', 1);

ALTER TABLE `KY_KE_TOAN_SO_DU` ADD `SO_LUONG` DOUBLE NOT NULL DEFAULT '0' AFTER `CO_CUOI_KY`;
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `NO_DAU_KY` `NO_DAU_KY` DOUBLE NOT NULL DEFAULT '0';
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `CO_DAU_KY` `CO_DAU_KY` DOUBLE NOT NULL DEFAULT '0';
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `NO_CUOI_KY` `NO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0';
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `CO_CUOI_KY` `CO_CUOI_KY` DOUBLE NOT NULL DEFAULT '0';
ALTER TABLE `KY_KE_TOAN_SO_DU` CHANGE `MA_DV` `MA_DV` INT(11) NOT NULL DEFAULT '0';

ALTER TABLE `HANG_HOA_KHO_HANG` DROP `MA_LO`;

ALTER TABLE `NGHIEP_VU_KE_TOAN` ADD `MA_DT` INT NULL AFTER `ASSET_CODE`, ADD `LOAI_DT` TINYINT(1) NULL AFTER `MA_DT`;