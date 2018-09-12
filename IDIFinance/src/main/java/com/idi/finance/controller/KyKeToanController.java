package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.idi.finance.form.KyKeToanForm;
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

	@RequestMapping("/kyketoan/xem/{maKyKt}")
	public String xemKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
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

	@RequestMapping("/kyketoan/sua/{maKyKt}")
	public String suaKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
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

	@RequestMapping("/kyketoan/dong/{maKyKt}")
	public String dongKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
		try {
			// Đóng một kỳ kế toán đang mở
			kyKeToanDAO.dongMoKyKeToan(maKyKt, 0);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/mo/{maKyKt}")
	public String moKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
		try {
			// Mở một kỳ kế toán đang đóng
			kyKeToanDAO.dongMoKyKeToan(maKyKt, 1);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/macdinh/{maKyKt}")
	public String macDinhKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
		try {
			// Đặt mặc định kỳ kế toán
			kyKeToanDAO.macDinhKyKeToan(maKyKt);

			return "redirect:/kyketoan/xem/" + maKyKt;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/xoa/{maKyKt}")
	public String xoaKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
		try {
			// Xóa các nghiệp vụ kế toán trong kỳ, xóa số dư đầu kỳ, xóa kỳ kế toán
			kyKeToanDAO.xoaKyKeToan(maKyKt);

			return "redirect:/kyketoan/danhsach";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/kyketoan/soduky/chuyen/{maKyKt}")
	public String chuyenSoDuKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
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

							// Cộng số dư đầu kỳ để tính ra số dư cuối kỳ
							SoDuKy soDuDauKy = kyKeToanDAO.laySoDuKy(loaiTaiKhoan.getMaTk(), kyKeToanTruoc.getMaKyKt());

							// Tính số dư 4212 của kỳ trước để ghi vào 4211 của kỳ này
							double noDauKy = noPhatSinh + soDuDauKy.getNoDauKy() - coPhatSinh - soDuDauKy.getCoDauKy();

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

				logger.info("Công nợ khách hàng, nhà cung cấp, nhân viên");
				logger.info("Công nợ khách hàng ...");
				List<SoDuKy> khachHangDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.KHACH_HANG);
				List<SoDuKy> phaiThuKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.KHACH_HANG,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiTraKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.KHACH_HANG,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				List<SoDuKy> khachHangCNDs = new ArrayList<>();
				khachHangCNDs.addAll(phaiThuKhDs);
				khachHangCNDs.addAll(phaiTraKhDs);
				Iterator<SoDuKy> kHIter = khachHangCNDs.iterator();
				while (kHIter.hasNext()) {
					SoDuKy soDuKy = kHIter.next();
					soDuKy.setKyKeToan(kyKeToan);
				}

				if (khachHangDs != null) {
					kHIter = khachHangDs.iterator();
					while (kHIter.hasNext()) {
						SoDuKy soDuKy = kHIter.next();

						Iterator<SoDuKy> cnIter = khachHangCNDs.iterator();
						while (cnIter.hasNext()) {
							SoDuKy soDuKyTmpl = cnIter.next();

							if (soDuKy.equals(soDuKyTmpl)) {
								soDuKy.tron(soDuKyTmpl);
								break;
							}
						}
					}
				}

				logger.info("Công nợ nhà cung cấp ...");
				List<SoDuKy> nhaCcDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHA_CUNG_CAP);
				List<SoDuKy> phaiThuNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHA_CUNG_CAP,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiTraNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHA_CUNG_CAP,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				List<SoDuKy> nhaCCCNDs = new ArrayList<>();
				nhaCCCNDs.addAll(phaiThuNccDs);
				nhaCCCNDs.addAll(phaiTraNccDs);
				Iterator<SoDuKy> nhaCCIter = nhaCCCNDs.iterator();
				while (nhaCCIter.hasNext()) {
					SoDuKy soDuKy = nhaCCIter.next();
					soDuKy.setKyKeToan(kyKeToan);
				}

				if (nhaCcDs != null) {
					nhaCCIter = nhaCcDs.iterator();
					while (nhaCCIter.hasNext()) {
						SoDuKy soDuKy = nhaCCIter.next();

						Iterator<SoDuKy> cnIter = nhaCCCNDs.iterator();
						while (cnIter.hasNext()) {
							SoDuKy soDuKyTmpl = cnIter.next();

							if (soDuKy.equals(soDuKyTmpl)) {
								soDuKy.tron(soDuKyTmpl);
								break;
							}
						}
					}
				}

				logger.info("Công nợ nhân viên ...");
				List<SoDuKy> nhanVienDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHAN_VIEN);
				List<SoDuKy> phaiThuNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHAN_VIEN,
						LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
				List<SoDuKy> phaiTraNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHAN_VIEN,
						LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

				List<SoDuKy> nhanVienCNDs = new ArrayList<>();
				nhanVienCNDs.addAll(phaiThuNvDs);
				nhanVienCNDs.addAll(phaiTraNvDs);
				Iterator<SoDuKy> nhanVienIter = nhanVienCNDs.iterator();
				while (nhanVienIter.hasNext()) {
					SoDuKy soDuKy = nhanVienIter.next();
					soDuKy.setKyKeToan(kyKeToan);
				}

				if (nhanVienDs != null) {
					nhanVienIter = nhanVienDs.iterator();
					while (nhanVienIter.hasNext()) {
						SoDuKy soDuKy = nhanVienIter.next();

						Iterator<SoDuKy> cnIter = nhanVienCNDs.iterator();
						while (cnIter.hasNext()) {
							SoDuKy soDuKyTmpl = cnIter.next();

							if (soDuKy.equals(soDuKyTmpl)) {
								soDuKy.tron(soDuKyTmpl);
								break;
							}
						}
					}
				}

				// Thêm số dư kỳ công nợ vào csdl
				List<SoDuKy> soDuKyDs = new ArrayList<>();
				soDuKyDs.addAll(khachHangDs);
				soDuKyDs.addAll(nhaCcDs);
				soDuKyDs.addAll(nhanVienDs);
				Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
				while (soDuKyIter.hasNext()) {
					SoDuKy soDuKy = soDuKyIter.next();
					// soDuKy.setKyKeToan(kyKeToan);
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

	@RequestMapping(value = "/kyketoan/soduky/xuat/{maKyKt}", method = RequestMethod.GET)
	public void xuatSoDuKyKeToan(HttpServletRequest req, HttpServletResponse res, @PathVariable("maKyKt") int maKyKt,
			Model model) {
		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);
		// Kiểm tra kỳ có mở không, chỉ có mở mới kết chuyển được
		if (kyKeToan == null) {
			return;
		}

		List<SoDuKy> soDuKyDs = new ArrayList<>();

		try {
			// Đọc dữ liệu về số dư cuối kỳ
			// Với số dư các tài khoản
			// Lấy danh sách tài khoản
			logger.info("Chuyển số dư các tài khoản ...");
			List<LoaiTaiKhoan> taiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();
			Iterator<LoaiTaiKhoan> iter = taiKhoanDs.iterator();
			while (iter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = iter.next();

				try {
					if (loaiTaiKhoan.getMaTk().trim().equals(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY)) {
						// Với tài khoản 4212 thì ko chuyển gì
						continue;
					} else if (loaiTaiKhoan.getMaTk().trim().equals(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_TRUOC)) {
						// Với tài khoản 4211 thì sẽ chuyển sang 4211
						double noPhatSinh = soKeToanDAO.tongPhatSinh(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY,
								LoaiTaiKhoan.NO, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

						double coPhatSinh = soKeToanDAO.tongPhatSinh(LoaiTaiKhoan.LOI_NHUAN_CHUA_PHAN_PHOI_KY_NAY,
								LoaiTaiKhoan.CO, kyKeToan.getBatDau(), kyKeToan.getKetThuc());

						// Cộng số dư đầu kỳ để tính ra số dư cuối kỳ
						SoDuKy soDuDauKy = kyKeToanDAO.laySoDuKy(loaiTaiKhoan.getMaTk(), kyKeToan.getMaKyKt());

						// Tính số dư 4212 của kỳ này để ghi vào 4211 của kỳ sau
						double noDauKy = noPhatSinh + soDuDauKy.getNoDauKy() - coPhatSinh - soDuDauKy.getCoDauKy();

						logger.info(loaiTaiKhoan.getMaTk() + ": " + noDauKy);
						SoDuKy soDuKy = new SoDuKy();
						soDuKy.setKyKeToan(kyKeToan);
						soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

						if (noDauKy > 0) {
							// Nếu số dư lớn hơn 0 thì mới cần chuyển
							// copy số dư cuối kỳ này sang thành số dư đầu kỳ mới
							soDuKy.setNoDauKy(noDauKy);
							soDuKy.setCoDauKy(0);
							soDuKyDs.add(soDuKy);
						} else if (noDauKy < 0) {
							soDuKy.setNoDauKy(0);
							soDuKy.setCoDauKy(noDauKy * -1);
							soDuKyDs.add(soDuKy);
						}
					} else {
						// Còn các tài khoản còn lại thì chuyển bình thường
						// Lấy nợ đầu kỳ - có đầu kỳ + nợ phát sinh - có phát sinh
						// Hoặc lấy số dư cuối kỳ này
						// Tính tổng phát sinh kỳ này
						double noPhatSinh = soKeToanDAO.tongPhatSinh(loaiTaiKhoan.getMaTk(), LoaiTaiKhoan.NO,
								kyKeToan.getBatDau(), kyKeToan.getKetThuc());

						double coPhatSinh = soKeToanDAO.tongPhatSinh(loaiTaiKhoan.getMaTk(), LoaiTaiKhoan.CO,
								kyKeToan.getBatDau(), kyKeToan.getKetThuc());

						SoDuKy soDuKy = new SoDuKy();
						soDuKy.setKyKeToan(kyKeToan);
						soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);

						// Cộng số dư đầu kỳ để tính ra số dư cuối kỳ
						SoDuKy soDuKyTruoc = kyKeToanDAO.laySoDuKy(loaiTaiKhoan.getMaTk(), kyKeToan.getMaKyKt());

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
								noDauKy = soDuKyTruoc.getNoDauKy() - soDuKyTruoc.getCoDauKy() + noPhatSinh - coPhatSinh;
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
							logger.info(
									loaiTaiKhoan.getMaTk() + ": " + soDuKy.getNoDauKy() + " " + soDuKy.getCoDauKy());
							soDuKyDs.add(soDuKy);
						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
			}

			logger.info("Công nợ khách hàng, nhà cung cấp, nhân viên");
			logger.info("Công nợ khách hàng ...");
			List<SoDuKy> khachHangDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.KHACH_HANG);
			List<SoDuKy> phaiThuKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.KHACH_HANG,
					LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
			List<SoDuKy> phaiTraKhDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.KHACH_HANG,
					LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

			List<SoDuKy> khachHangCNDs = new ArrayList<>();
			khachHangCNDs.addAll(phaiThuKhDs);
			khachHangCNDs.addAll(phaiTraKhDs);
			Iterator<SoDuKy> kHIter = khachHangCNDs.iterator();
			while (kHIter.hasNext()) {
				SoDuKy soDuKy = kHIter.next();
				soDuKy.setKyKeToan(kyKeToan);
			}

			if (khachHangDs != null) {
				kHIter = khachHangDs.iterator();
				while (kHIter.hasNext()) {
					SoDuKy soDuKy = kHIter.next();

					Iterator<SoDuKy> cnIter = khachHangCNDs.iterator();
					while (cnIter.hasNext()) {
						SoDuKy soDuKyTmpl = cnIter.next();

						if (soDuKy.equals(soDuKyTmpl)) {
							soDuKy.tron(soDuKyTmpl);
							break;
						}
					}
				}
			}

			logger.info("Công nợ nhà cung cấp ...");
			List<SoDuKy> nhaCcDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHA_CUNG_CAP);
			List<SoDuKy> phaiThuNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHA_CUNG_CAP,
					LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
			List<SoDuKy> phaiTraNccDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHA_CUNG_CAP,
					LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

			List<SoDuKy> nhaCCCNDs = new ArrayList<>();
			nhaCCCNDs.addAll(phaiThuNccDs);
			nhaCCCNDs.addAll(phaiTraNccDs);
			Iterator<SoDuKy> nhaCCIter = nhaCCCNDs.iterator();
			while (nhaCCIter.hasNext()) {
				SoDuKy soDuKy = nhaCCIter.next();
				soDuKy.setKyKeToan(kyKeToan);
			}

			if (nhaCcDs != null) {
				nhaCCIter = nhaCcDs.iterator();
				while (nhaCCIter.hasNext()) {
					SoDuKy soDuKy = nhaCCIter.next();

					Iterator<SoDuKy> cnIter = nhaCCCNDs.iterator();
					while (cnIter.hasNext()) {
						SoDuKy soDuKyTmpl = cnIter.next();

						if (soDuKy.equals(soDuKyTmpl)) {
							soDuKy.tron(soDuKyTmpl);
							break;
						}
					}
				}
			}

			logger.info("Công nợ nhân viên ...");
			List<SoDuKy> nhanVienDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(maKyKt, DoiTuong.NHAN_VIEN);
			List<SoDuKy> phaiThuNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHAN_VIEN,
					LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
			List<SoDuKy> phaiTraNvDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, DoiTuong.NHAN_VIEN,
					LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

			List<SoDuKy> nhanVienCNDs = new ArrayList<>();
			nhanVienCNDs.addAll(phaiThuNvDs);
			nhanVienCNDs.addAll(phaiTraNvDs);
			Iterator<SoDuKy> nhanVienIter = nhanVienCNDs.iterator();
			while (nhanVienIter.hasNext()) {
				SoDuKy soDuKy = nhanVienIter.next();
				soDuKy.setKyKeToan(kyKeToan);
			}

			if (nhanVienDs != null) {
				nhanVienIter = nhanVienDs.iterator();
				while (nhanVienIter.hasNext()) {
					SoDuKy soDuKy = nhanVienIter.next();

					Iterator<SoDuKy> cnIter = nhanVienCNDs.iterator();
					while (cnIter.hasNext()) {
						SoDuKy soDuKyTmpl = cnIter.next();

						if (soDuKy.equals(soDuKyTmpl)) {
							soDuKy.tron(soDuKyTmpl);
							break;
						}
					}
				}
			}

			// Sau đó ghi ra file xlsx

			// Rồi trả về client
			byte[] bytes = null;

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			res.setContentLength(bytes.length);
			// res.setHeader("Content-disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = res.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/kyketoan/soduky/nhap/{maKyKt}", method = RequestMethod.GET)
	public String nhapSoDuKyKeToan(@PathVariable("maKyKt") int maKyKt, Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);
		// Kiểm tra kỳ có mở không, chỉ có mở mới kết chuyển được
		if (kyKeToan == null) {
			return "redirect:/kyketoan/danhsach";
		}

		model.addAttribute("kyKeToan", kyKeToan);
		model.addAttribute("tab", "tabKKT");
		return "nhapSoDuKyKeToan";
	}

	@RequestMapping(value = "/kyketoan/soduky/luusoduky", method = RequestMethod.POST)
	public String lauSoDuKyKeToan(@ModelAttribute("mainFinanceForm") KyKeToanForm form, Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
		if (kyKeToan == null) {
			return "redirect:/kyketoan/danhsach";
		}

		logger.info("Hi " + form.getFile());

		return "redirect:/kyketoan/xem/" + kyKeToan.getMaKyKt();
	}
}
