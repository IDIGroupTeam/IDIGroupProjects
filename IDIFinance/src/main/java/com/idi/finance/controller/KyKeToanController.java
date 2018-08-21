package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.chungtu.DoiTuong;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.validator.KyKeToanValidator;

@Controller
public class KyKeToanController {
	private static final Logger logger = Logger.getLogger(KyKeToanController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	SoKeToanDAO soKeToanDAO;

	@Autowired
	KyKeToanDAO kyKeToanDAO;

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	NhaCungCapDAO nhaCungCapDAO;

	@Autowired
	NhanVienDAO nhanVienDAO;

	@Autowired
	KyKeToanValidator kyKeToanValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == KyKeToan.class) {
			binder.setValidator(kyKeToanValidator);
		}
	}

	@RequestMapping("/kyketoan/danhsach")
	public String danhSachKyKeToan(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			// Lấy danh sách kỳ kế toán
			List<KyKeToan> kyKeToanDs = kyKeToanDAO.danhSachKyKeToan();

			model.addAttribute("kyKeToanDs", kyKeToanDs);
			model.addAttribute("tab", "tabKKT");
			return "danhSachKyKeToan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/xem/{id}")
	public String xemKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);
			if (kyKeToan == null) {
				return "redirect:/kyketoan/danhsach";
			}
			model.addAttribute("kyKeToan", kyKeToan);

			// Lấy số dư đầu và cuối kỳ của các nhóm tài khoản
			// Nhóm tài khoản CCDC, VT, HH

			// Nhóm tài khoản theo đối tượng (chỉ áp dụng với 131 và 331)
			// Có 3 loại đối tượng: khách hàng, nhà cung cấp và nhân viên
			// Với khách vãng lai thì không cần
			// Số dư đầu kỳ khách hàng
			List<SoDuKy> khachHangDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.KHACH_HANG);

			// Số dư đầu kỳ nhà cung cấp
			List<SoDuKy> nhaCcDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHA_CUNG_CAP);

			// Số dư đầu kỳ công nợ nhân viên
			List<SoDuKy> nhanVienDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHAN_VIEN);

			// Nhóm tài khoản tiền gửi ngân hàng: chi tiết theo từng ngân hàng

			// Nhóm tài khoản kế toán khác như tiền mặt, ...
			List<SoDuKy> taiKhoanDs = kyKeToanDAO.danhSachSoDuKy(maKyKt);

			model.addAttribute("khachHangDs", khachHangDs);
			model.addAttribute("nhaCcDs", nhaCcDs);
			model.addAttribute("nhanVienDs", nhanVienDs);
			model.addAttribute("taiKhoanDs", taiKhoanDs);

			model.addAttribute("tab", "tabKKT");
			return "xemKyKeToan";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/sua/{id}")
	public String suaKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);
			if (kyKeToan == null) {
				return "redirect:/kyketoan/danhsach";
			}

			return chuanBiFormKyKeToan(model, kyKeToan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/taomoi")
	public String taoMoiKyKeToan(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KyKeToan kyKeToan = new KyKeToan();

			Date homNay = new Date();
			kyKeToan.setBatDau(homNay);
			kyKeToan.setKetThuc(homNay);

			return chuanBiFormKyKeToan(model, kyKeToan);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/kyketoan/luu", method = RequestMethod.POST)
	public String luuTaoMoiKyKeToan(@ModelAttribute("mainFinanceForm") @Validated KyKeToan kyKeToan,
			BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				return chuanBiFormKyKeToan(model, kyKeToan);
			}

			logger.info("Lưu kỳ kế toán: " + kyKeToan);

			if (kyKeToan.getMaKyKt() > 0) {
				kyKeToanDAO.capNhatKyKeToan(kyKeToan);
				return "redirect:/kyketoan/xem/" + kyKeToan.getMaKyKt();
			} else {
				kyKeToan.setTrangThai(1);
				kyKeToanDAO.themKyKeToan(kyKeToan);
			}

			return "redirect:/kyketoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String chuanBiFormKyKeToan(Model model, KyKeToan kyKeToan) {
		try {
			model.addAttribute("mainFinanceForm", kyKeToan);

			model.addAttribute("tab", "tabKKT");
			if (kyKeToan.getMaKyKt() > 0) {
				// Đây là trường hợp sửa KKT
				return "suaKyKeToan";
			} else {
				// Đây là trường hợp tạo mới KKT
				return "taoMoiKyKeToan";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/dong/{id}")
	public String dongKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Đóng một kỳ kế toán đang mở
			kyKeToanDAO.dongMoKyKeToan(maKyKt, 0);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/mo/{id}")
	public String moKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Mở một kỳ kế toán đang đóng
			kyKeToanDAO.dongMoKyKeToan(maKyKt, 1);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/macdinh/{id}")
	public String macDinhKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Đặt mặc định kỳ kế toán
			kyKeToanDAO.macDinhKyKeToan(maKyKt);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/xoa/{id}")
	public String xoaKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Xóa các nghiệp vụ kế toán trong kỳ, xóa số dư đầu kỳ, xóa kỳ kế toán
			kyKeToanDAO.xoaKyKeToan(maKyKt);

			return "redirect:/kyketoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/chuyensodu/{id}")
	public String chuyenSoDuKyKeToan(@PathVariable("id") int maKyKt, Model model) {
		try {
			// Tìm kỳ hiện tại
			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);

			// Kiểm tra kỳ có mở không, chỉ có mở mới kết chuyển được
			if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
				return "redirect:/kyketoan/xem/" + maKyKt;
			}

			// Tìm kỳ ngay trước nó
			KyKeToan kyKeToanTruoc = kyKeToanDAO.layKyKeToanTruoc(kyKeToan);
			if (kyKeToanTruoc != null) {
				// Nếu có kỳ trước thì mới chuyển số dư
				logger.info("Chuyển số dư từ kỳ " + kyKeToanTruoc + " sang kỳ " + kyKeToan);

				// Với số dư các tài khoản
				// Lấy danh sách tài khoản
				logger.info("Chuyển số dư các tài khoản ...");
				List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
				Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
				while (iter.hasNext()) {
					LoaiTaiKhoan loaiTaiKhoan = iter.next();

					try {
						if (loaiTaiKhoan.getMaTk().trim().equals(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY)) {
							// Riêng với tài khoản 4212 thì ko chuyển gì
							continue;
						} else if (loaiTaiKhoan.getMaTk().trim()
								.equals(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_TRUOC)) {
							// Riêng với tài khoản 4211 thì sẽ chuyển sang 4211
							double noPhatSinh = soKeToanDAO.tongPhatSinh(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY,
									LoaiTaiKhoan.NO, kyKeToanTruoc.getBatDau(), kyKeToanTruoc.getKetThuc());

							double coPhatSinh = soKeToanDAO.tongPhatSinh(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY,
									LoaiTaiKhoan.CO, kyKeToanTruoc.getBatDau(), kyKeToanTruoc.getKetThuc());

							// TÍnh số dư 4212 của kỳ trước để ghi vào 4211 của kỳ này
							double noDauKy = noPhatSinh - coPhatSinh;

							logger.info(loaiTaiKhoan.getMaTk() + ": " + noDauKy);
							SoDuKy soDuKy = new SoDuKy();
							soDuKy.setKyKeToan(kyKeToan);
							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);
							if (noDauKy > 0) {
								// Nếu số dư khác 0 thì mới cần chuyển
								// copy số dư cuối kỳ trước sang thành số dư đầu kỳ mới

								soDuKy.setNoDauKy(noDauKy);
								soDuKy.setCoDauKy(0);
								kyKeToanDAO.themSoDuDauKy(soDuKy);
							} else if (noDauKy < 0) {
								soDuKy.setNoDauKy(0);
								soDuKy.setCoDauKy(noDauKy * -1);
								kyKeToanDAO.themSoDuDauKy(soDuKy);
							}
						} else {
							// Còn các tài khoản còn lại thì chuyển bình thường
							// Lấy nợ đầu kỳ - có đầu kỳ + nợ phát sinh - có phát sinh
							// Hoặc lấy số dư cuối kỳ trước
							// Tính tổng phát sinh kỳ trước
							double noPhatSinh = soKeToanDAO.tongPhatSinh(loaiTaiKhoan.getMaTk(), LoaiTaiKhoan.NO,
									kyKeToanTruoc.getBatDau(), kyKeToanTruoc.getKetThuc());

							double coPhatSinh = soKeToanDAO.tongPhatSinh(loaiTaiKhoan.getMaTk(), LoaiTaiKhoan.CO,
									kyKeToanTruoc.getBatDau(), kyKeToanTruoc.getKetThuc());

							SoDuKy soDuKy = new SoDuKy();
							soDuKy.setKyKeToan(kyKeToan);
							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

							// Cộng số dư đầu kỳ trước để tính ra số dư cuối kỳ trước
							SoDuKy soDuKyTruoc = kyKeToanDAO.laySoDuKy(loaiTaiKhoan.getMaTk(),
									kyKeToanTruoc.getMaKyKt());

							if (loaiTaiKhoan.isLuongTinh()) {
								// Với tài khoản lưỡng tính
								double noDauKy = noPhatSinh;
								try {
									noDauKy = soDuKyTruoc.getNoDauKy() + noPhatSinh;
								} catch (Exception e) {

								}
								double coDauKy = coPhatSinh;
								try {
									coDauKy = soDuKyTruoc.getCoDauKy() + coPhatSinh;
								} catch (Exception e) {

								}

								soDuKy.setNoDauKy(noDauKy);
								soDuKy.setCoDauKy(coDauKy);
							} else {
								// Với tài khoản bình thường
								double noDauKy = noPhatSinh - coPhatSinh;
								try {
									noDauKy = soDuKyTruoc.getNoDauKy() - soDuKyTruoc.getCoDauKy() + noPhatSinh
											- coPhatSinh;
								} catch (Exception e) {

								}

								if (noDauKy > 0) {
									soDuKy.setNoDauKy(noDauKy);
									soDuKy.setCoDauKy(0);
								} else if (noDauKy < 0) {
									soDuKy.setNoDauKy(0);
									soDuKy.setCoDauKy(noDauKy * -1);
								}
							}

							if (soDuKy.getNoDauKy() != 0 || soDuKy.getCoDauKy() != 0) {
								// Nếu số dư khác 0 thì mới cần chuyển
								// copy số dư cuối kỳ trước sang thành số dư đầu kỳ mới
								logger.info(loaiTaiKhoan.getMaTk() + ": " + soDuKy.getNoDauKy() + " "
										+ soDuKy.getCoDauKy());
								kyKeToanDAO.themSoDuDauKy(soDuKy);
							}
						}
					} catch (Exception e) {
						logger.info(e.getMessage());
					}
				}

				// Với số dự công nợ khách hàng, nhà cung cấp, nhân viên
				// Công nợ khách hàng
				logger.info("Chuyển số dư công nợ khách hàng ...");
				List<SoDuKy> phaiThuKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.KHACH_HANG,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiTraKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.KHACH_HANG,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				// Công nợ nhà cung cấp
				logger.info("Chuyển số dư công nợ nhà cung cấp ...");
				List<SoDuKy> phaiThuNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.NHA_CUNG_CAP,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiTraNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.NHA_CUNG_CAP,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				// Công nợ nhân viên
				logger.info("Chuyển số dư công nợ nhân viên ...");
				List<SoDuKy> phaiTraNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.NHAN_VIEN,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiThuNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToanTruoc, DoiTuong.NHAN_VIEN,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				// Thêm số dư kỳ công nợ vào csdl
				List<SoDuKy> soDuKyDs = new ArrayList<>();
				soDuKyDs.addAll(phaiThuKhDs);
				soDuKyDs.addAll(phaiTraKhDs);
				soDuKyDs.addAll(phaiThuNccDs);
				soDuKyDs.addAll(phaiTraNccDs);
				soDuKyDs.addAll(phaiTraNvDs);
				soDuKyDs.addAll(phaiThuNvDs);
				Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
				while (soDuKyIter.hasNext()) {
					SoDuKy soDuKy = soDuKyIter.next();
					soDuKy.setKyKeToan(kyKeToan);
					logger.info(soDuKy);
					kyKeToanDAO.themSoDuDauKy(soDuKy);
				}
			} else {
				logger.info("Không tồn tài kỳ kế toán trước đó, không cần chuyển số dư.");
			}

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (

		Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
