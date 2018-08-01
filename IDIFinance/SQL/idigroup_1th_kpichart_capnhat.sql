-- CÂU TRUY VẤN CHO PHẦN KPI CHART
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- Query để sinh bảng kết quả sản xuất kinh doanh
INSERT INTO `SALE_RESULT_ITEM` (`ASSET_CODE`, `ASSET_NAME`, `ASSET_PARENT`, `RULE`, `NOTE`, `SO_DU`) VALUES
('01', 'Doanh thu bán hàng và cung cấp dịch vụ', NULL, NULL, NULL, -1),
('02', 'Các khoản giảm trừ', NULL, NULL, NULL, -1),
('10', 'Doanh thu thuần về bán hàng và cung cấp dịch vụ', NULL, '10 = 01 - 02', NULL, -1),
('11', 'Giá vốn hàng bán', NULL, NULL, NULL, -1),
('20', 'Lợi nhuận gộp về bán hàng và cung cấp dịch vụ', NULL, '20 = 10 - 11', NULL, -1),
('21', 'Doanh thu hoạt động tài chính', NULL, NULL, NULL, -1),
('22', 'Chi phí tài chính', NULL, NULL, NULL, -1),
('23', 'Lãi vay phải trả', '22', NULL, NULL, -1),
('24', 'Chi phí bán hàng', NULL, NULL, NULL, -1),
('25', 'Chi phí quản lý doanh nghiệp', NULL, NULL, NULL, -1),
('30', 'Lợi nhuận thuần từ hoạt động kinh doanh', NULL, '30 = 20 + (21 - 22) - (24 + 25)', NULL, -1),
('31', 'Thu nhập khác', NULL, NULL, NULL, -1),
('32', 'Chi phí khác', NULL, NULL, NULL, -1),
('40', 'Lợi nhuận khác', NULL, '40 = 31 - 32', NULL, -1),
('41', 'Phần lãi (lỗ) trong liên doanh/liên kết', NULL, NULL, NULL, -1),
('50', 'Tổng lợi nhuận trước thuế', NULL, '50 = 30 + 40', NULL, -1),
('51', 'Chi phí thuế thu nhập DN hiện hành', NULL, NULL, NULL, -1),
('52', 'Chi phí thuế thu nhập DN hoãn lại', NULL, NULL, NULL, -1),
('60', 'Lợi nhuận sau thuế', NULL, '60 = 50 - 51 - 52', NULL, -1),
('70', 'Lãi cơ bản trên cổ phiếu', NULL, NULL, NULL, -1),
('71', 'Lãi suy giảm trên cổ phiếu', NULL, NULL, NULL, -1)
