package com.idi.finance.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.doituong.DoiTuong;
import com.idi.finance.bean.doituong.KhachHang;
import com.idi.finance.bean.doituong.NhaCungCap;
import com.idi.finance.bean.doituong.NhanVien;
import com.idi.finance.bean.hanghoa.DonVi;
import com.idi.finance.bean.hanghoa.HangHoa;
import com.idi.finance.bean.hanghoa.KhoHang;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.bean.kyketoan.SoDuKy;
import com.idi.finance.bean.taikhoan.LoaiTaiKhoan;
import com.idi.finance.dao.HangHoaDAO;
import com.idi.finance.dao.KhachHangDAO;
import com.idi.finance.dao.KyKeToanDAO;
import com.idi.finance.dao.NhaCungCapDAO;
import com.idi.finance.dao.NhanVienDAO;
import com.idi.finance.dao.SoKeToanDAO;
import com.idi.finance.dao.TaiKhoanDAO;
import com.idi.finance.form.KyKeToanForm;
import com.idi.finance.utils.PropCont;
import com.idi.finance.validator.KyKeToanValidator;

@Controller
public class KyKeToanController {
	private static final Logger logger = Logger.getLogger(KyKeToanController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

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
	HangHoaDAO hangHoaDAO;

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
		} else if (target.getClass() == SoDuKy.class) {
			binder.setValidator(null);
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
	public String xemKyKeToan(@ModelAttribute("mainFinanceForm") SoDuKy soDuKy, @PathVariable("maKyKt") int maKyKt,
			Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(maKyKt);
			if (kyKeToan == null) {
				return "redirect:/kyketoan/danhsach";
			}
			model.addAttribute("kyKeToan", kyKeToan);

			if (soDuKy == null) {
				soDuKy = new SoDuKy();
			}
			soDuKy.setKyKeToan(kyKeToan);

			// Lấy số dư đầu và cuối kỳ của các nhóm tài khoản
			// Nhóm tài khoản CCDC, VTHH
			// Số dư đầu kỳ tồn kho VTHH
			List<SoDuKy> tonKhoDs = kyKeToanDAO.danhSachSoDuKyTheoHangHoa(maKyKt);

			// Nhóm tài khoản theo đối tượng
			// (chỉ áp dụng với các tài khoản công nợ như 131, 331, 141, 334)
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

			model.addAttribute("tonKhoDs", tonKhoDs);
			model.addAttribute("khachHangDs", khachHangDs);
			model.addAttribute("nhaCcDs", nhaCcDs);
			model.addAttribute("nhanVienDs", nhanVienDs);
			model.addAttribute("taiKhoanDs", taiKhoanDs);

			// LẤY CÁC THÔNG TIN CHUẨN BỊ CHO CÁC MODAL TỌA MỚI
			// Danh sách tài khoản
			List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();

			// Danh sách khách hàng
			List<KhachHang> khDs = khachHangDAO.danhSachKhachHang();

			// Danh sách nhà cung cấp
			List<NhaCungCap> nccDs = nhaCungCapDAO.danhSachNhaCungCap();

			// Danh sách nhân viên
			List<NhanVien> nvDs = nhanVienDAO.danhSachNhanVien();

			// Danh sách tài khoản cộng nợ
			List<LoaiTaiKhoan> congNoTkDs = new ArrayList<>();
			try {
				String[] congNos = props.getCauHinh(PropCont.TAI_KHOAN_CONG_NO).getGiaTri().split(";");
				congNoTkDs = taiKhoanDAO.danhSachTaiKhoan(Arrays.asList(congNos));
			} catch (Exception e) {
			}

			// Danh sách tài khoản kho VTHH
			List<LoaiTaiKhoan> khoTkDs = new ArrayList<>();
			try {
				String[] khos = props.getCauHinh(PropCont.TAI_KHOAN_KHO_VTHH).getGiaTri().split(";");
				khoTkDs = taiKhoanDAO.danhSachTaiKhoan(Arrays.asList(khos));
			} catch (Exception e) {
			}

			// Danh sách hàng hóa
			List<HangHoa> hangHoaDs = hangHoaDAO.danhSachHangHoa();

			// Danh sách kho hàng
			List<KhoHang> khoHangDs = hangHoaDAO.danhSachKhoBai();

			// Danh sách đơn vị hàng hóa
			List<DonVi> donViDs = hangHoaDAO.danhSachDonViHangHoa();

			model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("khDs", khDs);
			model.addAttribute("nccDs", nccDs);
			model.addAttribute("nvDs", nvDs);
			model.addAttribute("congNoTkDs", congNoTkDs);
			model.addAttribute("khoTkDs", khoTkDs);
			model.addAttribute("hangHoaDs", hangHoaDs);
			model.addAttribute("khoHangDs", khoHangDs);
			model.addAttribute("donViDs", donViDs);
			model.addAttribute("mainFinanceForm", soDuKy);
			model.addAttribute("mainFinanceFormKh", soDuKy);
			model.addAttribute("mainFinanceFormNcc", soDuKy);
			model.addAttribute("mainFinanceFormNv", soDuKy);
			model.addAttribute("mainFinanceFormTkVthh", soDuKy);

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
								kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
							} else if (noDauKy < 0) {
								soDuKy.setNoDauKy(0);
								soDuKy.setCoDauKy(noDauKy * -1);
								kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
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
								kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
							}
						}
					} catch (Exception e) {
						logger.info(e.getMessage());
					}
				}

				logger.info("Công nợ khách hàng, nhà cung cấp, nhân viên");
				logger.info("Công nợ khách hàng ...");
				List<SoDuKy> khachHangDs = layCongNoDoiTuong(kyKeToanTruoc, DoiTuong.KHACH_HANG);

				logger.info("Công nợ nhà cung cấp ...");
				List<SoDuKy> nhaCcDs = layCongNoDoiTuong(kyKeToanTruoc, DoiTuong.NHA_CUNG_CAP);

				logger.info("Công nợ nhân viên ...");
				List<SoDuKy> nhanVienDs = layCongNoDoiTuong(kyKeToanTruoc, DoiTuong.NHAN_VIEN);

				// Thêm số dư kỳ công nợ vào csdl
				List<SoDuKy> soDuKyDs = new ArrayList<>();
				soDuKyDs.addAll(khachHangDs);
				soDuKyDs.addAll(nhaCcDs);
				soDuKyDs.addAll(nhanVienDs);
				Iterator<SoDuKy> soDuKyIter = soDuKyDs.iterator();
				while (soDuKyIter.hasNext()) {
					SoDuKy soDuKy = soDuKyIter.next();
					logger.info(soDuKy);
					kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
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

		try {
			// Đọc dữ liệu về số dư cuối kỳ
			// Với số dư các tài khoản
			// Lấy danh sách tài khoản
			logger.info("Chuyển số dư các tài khoản ...");
			List<SoDuKy> soDuKyDs = new ArrayList<>();
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

			logger.info("Danh sách khách hàng ...");
			List<DoiTuong> khachHangDs = khachHangDAO.danhSachDoiTuong();

			logger.info("Công nợ khách hàng ...");
			List<SoDuKy> khachHangCNDs = layCongNoDoiTuong(kyKeToan, DoiTuong.KHACH_HANG);

			logger.info("Danh sách nhà cung cấp ...");
			List<DoiTuong> nhaCcDs = nhaCungCapDAO.danhSachDoiTuong();

			logger.info("Công nợ nhà cung cấp ...");
			List<SoDuKy> nhaCcCNDs = layCongNoDoiTuong(kyKeToan, DoiTuong.NHA_CUNG_CAP);

			logger.info("Công nợ nhân viên ...");
			List<SoDuKy> nhanVienCNDs = layCongNoDoiTuong(kyKeToan, DoiTuong.NHAN_VIEN);

			logger.info("Sau đó ghi ra file xlsx, mỗi loại dữ liệu một sheet");
			// soDuKyDs, khachHangDs, khachHangCNDs, nhaCcDs, nhaCcCNDs, nhanVienCNDs
			XSSFWorkbook wb = new XSSFWorkbook();

			// Tạo font & cellstyle mặc định
			XSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 10);
			font.setFontName("Arial");
			font.setItalic(false);
			font.setBold(false);

			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			// Tạo font & cellstyle cho header
			XSSFFont boldFont = wb.createFont();
			boldFont.setFontHeightInPoints((short) 10);
			boldFont.setFontName("Arial");
			boldFont.setItalic(false);
			boldFont.setBold(true);

			CellStyle boldStyle = wb.createCellStyle();
			boldStyle.setFont(boldFont);

			logger.info("Tạo sheet số dư tài khoản ...");
			XSSFSheet soDuTkSt = wb.createSheet("Số dư tài khoản");

			// Tạo dòng header
			XSSFRow row00 = soDuTkSt.createRow(0);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell000 = row00.createCell(0);
			cell000.setCellStyle(boldStyle);
			cell000.setCellValue("Mã tài khoản");
			XSSFCell cell002 = row00.createCell(1);
			cell002.setCellStyle(boldStyle);
			cell002.setCellValue("Nợ");
			XSSFCell cell003 = row00.createCell(2);
			cell003.setCellStyle(boldStyle);
			cell003.setCellValue("Có");

			// Tạo các dòng dữ liệu
			if (soDuKyDs != null) {
				int i = 1;
				Iterator<SoDuKy> soDuTkIter = soDuKyDs.iterator();
				while (soDuTkIter.hasNext()) {
					SoDuKy soDuKy = soDuTkIter.next();

					// Tạo dòng
					XSSFRow row0i = soDuTkSt.createRow(i);

					// Tạo các cột
					XSSFCell cell0i0 = row0i.createCell(0);
					cell0i0.setCellStyle(style);
					if (soDuKy.getLoaiTaiKhoan() != null) {
						cell0i0.setCellValue(soDuKy.getLoaiTaiKhoan().getMaTk());
					} else {
						cell0i0.setCellValue("");
					}
					XSSFCell cell0i1 = row0i.createCell(1);
					cell0i1.setCellStyle(style);
					cell0i1.setCellValue(soDuKy.getNoDauKy());
					XSSFCell cell0i2 = row0i.createCell(2);
					cell0i2.setCellStyle(style);
					cell0i2.setCellValue(soDuKy.getCoDauKy());

					i++;
				}
			}

			// Resize column's width
			soDuTkSt.autoSizeColumn(0);
			soDuTkSt.autoSizeColumn(1);
			soDuTkSt.autoSizeColumn(2);

			logger.info("Tạo sheet danh sách khách hàng ...");
			XSSFSheet khachHangSt = wb.createSheet("Khách hàng");

			// Tạo dòng header
			XSSFRow row10 = khachHangSt.createRow(0);
			row10.setRowStyle(boldStyle);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell100 = row10.createCell(0);
			cell100.setCellStyle(boldStyle);
			cell100.setCellValue("Mã KH");
			XSSFCell cell101 = row10.createCell(1);
			cell101.setCellStyle(boldStyle);
			cell101.setCellValue("Tên KH");
			XSSFCell cell102 = row10.createCell(2);
			cell102.setCellStyle(boldStyle);
			cell102.setCellValue("Mã số thuế");
			XSSFCell cell103 = row10.createCell(3);
			cell103.setCellStyle(boldStyle);
			cell103.setCellValue("Địa chỉ");
			XSSFCell cell104 = row10.createCell(4);
			cell104.setCellStyle(boldStyle);
			cell104.setCellValue("Email");
			XSSFCell cell105 = row10.createCell(5);
			cell105.setCellStyle(boldStyle);
			cell105.setCellValue("Số điện thoại");
			XSSFCell cell106 = row10.createCell(6);
			cell106.setCellStyle(boldStyle);
			cell106.setCellValue("Website");

			// Tạo các dòng dữ liệu
			if (khachHangDs != null) {
				int i = 1;
				Iterator<DoiTuong> khachHangIter = khachHangDs.iterator();
				while (khachHangIter.hasNext()) {
					DoiTuong khachHang = khachHangIter.next();

					// Tạo dòng
					XSSFRow row1i = khachHangSt.createRow(i);

					// Tạo các cột
					XSSFCell cell1i0 = row1i.createCell(0);
					cell1i0.setCellStyle(style);
					cell1i0.setCellValue(khachHang.getKhDt());
					XSSFCell cell1i1 = row1i.createCell(1);
					cell1i1.setCellStyle(style);
					cell1i1.setCellValue(khachHang.getTenDt());
					XSSFCell cell1i2 = row1i.createCell(2);
					cell1i2.setCellStyle(style);
					cell1i2.setCellValue(khachHang.getMaThue());
					XSSFCell cell1i3 = row1i.createCell(3);
					cell1i3.setCellStyle(style);
					cell1i3.setCellValue(khachHang.getDiaChi());
					XSSFCell cell1i4 = row1i.createCell(4);
					cell1i4.setCellStyle(style);
					cell1i4.setCellValue(khachHang.getEmail());
					XSSFCell cell1i5 = row1i.createCell(5);
					cell1i5.setCellStyle(style);
					cell1i5.setCellValue(khachHang.getSdt());
					XSSFCell cell1i6 = row1i.createCell(6);
					cell1i6.setCellStyle(style);
					cell1i6.setCellValue(khachHang.getWebSite());

					i++;
				}
			}

			// Resize column's width
			khachHangSt.autoSizeColumn(0);
			khachHangSt.autoSizeColumn(1);
			khachHangSt.autoSizeColumn(2);
			khachHangSt.autoSizeColumn(3);
			khachHangSt.autoSizeColumn(4);
			khachHangSt.autoSizeColumn(5);
			khachHangSt.autoSizeColumn(6);

			logger.info("Tạo sheet công nợ khách hàng ...");
			XSSFSheet khachHangCnSt = wb.createSheet("Công nợ KH");

			// Tạo dòng header
			XSSFRow row20 = khachHangCnSt.createRow(0);
			row20.setRowStyle(boldStyle);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell200 = row20.createCell(0);
			cell200.setCellStyle(boldStyle);
			cell200.setCellValue("Mã KH");
			XSSFCell cell201 = row20.createCell(1);
			cell201.setCellStyle(boldStyle);
			cell201.setCellValue("Mã tài khoản");
			XSSFCell cell202 = row20.createCell(2);
			cell202.setCellStyle(boldStyle);
			cell202.setCellValue("Nợ");
			XSSFCell cell203 = row20.createCell(3);
			cell203.setCellStyle(boldStyle);
			cell203.setCellValue("Có");

			// Tạo các dòng dữ liệu
			if (khachHangCNDs != null) {
				int i = 1;
				Iterator<SoDuKy> khachHangCNIter = khachHangCNDs.iterator();
				while (khachHangCNIter.hasNext()) {
					SoDuKy soDuKy = khachHangCNIter.next();

					// Tạo dòng
					XSSFRow row2i = khachHangCnSt.createRow(i);

					// Tạo các cột
					XSSFCell cell2i0 = row2i.createCell(0);
					cell2i0.setCellStyle(style);
					if (soDuKy.getDoiTuong() != null) {
						cell2i0.setCellValue(soDuKy.getDoiTuong().getKhDt());
					} else {
						cell2i0.setCellValue("");
					}
					XSSFCell cell2i1 = row2i.createCell(1);
					cell2i1.setCellStyle(style);
					if (soDuKy.getLoaiTaiKhoan() != null) {
						cell2i1.setCellValue(soDuKy.getLoaiTaiKhoan().getMaTk());
					} else {
						cell2i1.setCellValue("");
					}
					XSSFCell cell2i2 = row2i.createCell(2);
					cell2i2.setCellStyle(style);
					cell2i2.setCellValue(soDuKy.getNoDauKy());
					XSSFCell cell2i3 = row2i.createCell(3);
					cell2i3.setCellStyle(style);
					cell2i3.setCellValue(soDuKy.getCoDauKy());

					i++;
				}
			}

			// Resize column's width
			khachHangCnSt.autoSizeColumn(0);
			khachHangCnSt.autoSizeColumn(1);
			khachHangCnSt.autoSizeColumn(2);
			khachHangCnSt.autoSizeColumn(3);

			logger.info("Tạo sheet danh sách nhà cung cấp ...");
			XSSFSheet nhaCcSt = wb.createSheet("Nhà cung cấp");

			// Tạo dòng header
			XSSFRow row30 = nhaCcSt.createRow(0);
			row30.setRowStyle(boldStyle);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell300 = row30.createCell(0);
			cell300.setCellStyle(boldStyle);
			cell300.setCellValue("Mã NCC");
			XSSFCell cell301 = row30.createCell(1);
			cell301.setCellStyle(boldStyle);
			cell301.setCellValue("Tên NCC");
			XSSFCell cell302 = row30.createCell(2);
			cell302.setCellStyle(boldStyle);
			cell302.setCellValue("Mã số thuế");
			XSSFCell cell303 = row30.createCell(3);
			cell303.setCellStyle(boldStyle);
			cell303.setCellValue("Địa chỉ");
			XSSFCell cell304 = row30.createCell(4);
			cell304.setCellStyle(boldStyle);
			cell304.setCellValue("Email");
			XSSFCell cell305 = row30.createCell(5);
			cell305.setCellStyle(boldStyle);
			cell305.setCellValue("Số điện thoại");
			XSSFCell cell306 = row30.createCell(6);
			cell306.setCellStyle(boldStyle);
			cell306.setCellValue("Website");

			// Tạo các dòng dữ liệu
			if (nhaCcDs != null) {
				int i = 1;
				Iterator<DoiTuong> nhaCcIter = nhaCcDs.iterator();
				while (nhaCcIter.hasNext()) {
					DoiTuong nhaCc = nhaCcIter.next();

					// Tạo dòng
					XSSFRow row3i = nhaCcSt.createRow(i);

					// Tạo các cột
					XSSFCell cell3i0 = row3i.createCell(0);
					cell3i0.setCellStyle(style);
					cell3i0.setCellValue(nhaCc.getKhDt());
					XSSFCell cell3i1 = row3i.createCell(1);
					cell3i1.setCellStyle(style);
					cell3i1.setCellValue(nhaCc.getTenDt());
					XSSFCell cell3i2 = row3i.createCell(2);
					cell3i2.setCellStyle(style);
					cell3i2.setCellValue(nhaCc.getMaThue());
					XSSFCell cell3i3 = row3i.createCell(3);
					cell3i3.setCellStyle(style);
					cell3i3.setCellValue(nhaCc.getDiaChi());
					XSSFCell cell3i4 = row3i.createCell(4);
					cell3i4.setCellStyle(style);
					cell3i4.setCellValue(nhaCc.getEmail());
					XSSFCell cell3i5 = row3i.createCell(5);
					cell3i5.setCellStyle(style);
					cell3i5.setCellValue(nhaCc.getSdt());
					XSSFCell cell3i6 = row3i.createCell(6);
					cell3i6.setCellStyle(style);
					cell3i6.setCellValue(nhaCc.getWebSite());

					i++;
				}
			}

			// Resize column's width
			nhaCcSt.autoSizeColumn(0);
			nhaCcSt.autoSizeColumn(1);
			nhaCcSt.autoSizeColumn(2);
			nhaCcSt.autoSizeColumn(3);
			nhaCcSt.autoSizeColumn(4);
			nhaCcSt.autoSizeColumn(5);
			nhaCcSt.autoSizeColumn(6);

			logger.info("Tạo sheet công nợ nhà cung cấp ...");
			XSSFSheet nhaCcCnSt = wb.createSheet("Công nợ NCC");

			// Tạo dòng header
			XSSFRow row40 = nhaCcCnSt.createRow(0);
			row40.setRowStyle(boldStyle);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell400 = row40.createCell(0);
			cell400.setCellStyle(boldStyle);
			cell400.setCellValue("Mã NCC");
			XSSFCell cell401 = row40.createCell(1);
			cell401.setCellStyle(boldStyle);
			cell401.setCellValue("Mã tài khoản");
			XSSFCell cell402 = row40.createCell(2);
			cell402.setCellStyle(boldStyle);
			cell402.setCellValue("Nợ");
			XSSFCell cell403 = row40.createCell(3);
			cell403.setCellStyle(boldStyle);
			cell403.setCellValue("Có");

			// Tạo các dòng dữ liệu
			if (nhaCcCNDs != null) {
				int i = 1;
				Iterator<SoDuKy> nhaCcCNIter = nhaCcCNDs.iterator();
				while (nhaCcCNIter.hasNext()) {
					SoDuKy soDuKy = nhaCcCNIter.next();

					// Tạo dòng
					XSSFRow row4i = nhaCcCnSt.createRow(i);

					// Tạo các cột
					XSSFCell cell4i0 = row4i.createCell(0);
					cell4i0.setCellStyle(style);
					if (soDuKy.getDoiTuong() != null) {
						cell4i0.setCellValue(soDuKy.getDoiTuong().getKhDt());
					} else {
						cell4i0.setCellValue("");
					}
					XSSFCell cell4i1 = row4i.createCell(1);
					cell4i1.setCellStyle(style);
					if (soDuKy.getLoaiTaiKhoan() != null) {
						cell4i1.setCellValue(soDuKy.getLoaiTaiKhoan().getMaTk());
					} else {
						cell4i1.setCellValue("");
					}
					XSSFCell cell4i2 = row4i.createCell(2);
					cell4i2.setCellStyle(style);
					cell4i2.setCellValue(soDuKy.getNoDauKy());
					XSSFCell cell4i3 = row4i.createCell(3);
					cell4i3.setCellStyle(style);
					cell4i3.setCellValue(soDuKy.getCoDauKy());

					i++;
				}
			}

			// Resize column's width
			nhaCcCnSt.autoSizeColumn(0);
			nhaCcCnSt.autoSizeColumn(1);
			nhaCcCnSt.autoSizeColumn(2);
			nhaCcCnSt.autoSizeColumn(3);

			logger.info("Tạo sheet công nợ nhân viên ...");
			XSSFSheet nhanVienCnSt = wb.createSheet("Công nợ NV");

			// Tạo dòng header
			XSSFRow row50 = nhanVienCnSt.createRow(0);
			row50.setRowStyle(boldStyle);

			// Tạo các cột cho dòng đầu tiên
			XSSFCell cell500 = row50.createCell(0);
			cell500.setCellStyle(boldStyle);
			cell500.setCellValue("Mã NV");
			XSSFCell cell501 = row50.createCell(1);
			cell501.setCellStyle(boldStyle);
			cell501.setCellValue("Mã tài khoản");
			XSSFCell cell502 = row50.createCell(2);
			cell502.setCellStyle(boldStyle);
			cell502.setCellValue("Nợ");
			XSSFCell cell503 = row50.createCell(3);
			cell503.setCellStyle(boldStyle);
			cell503.setCellValue("Có");

			// Tạo các dòng dữ liệu
			if (nhanVienCNDs != null) {
				int i = 1;
				Iterator<SoDuKy> nhanVienCNIter = nhanVienCNDs.iterator();
				while (nhanVienCNIter.hasNext()) {
					SoDuKy soDuKy = nhanVienCNIter.next();

					// Tạo dòng
					XSSFRow row5i = nhanVienCnSt.createRow(i);

					// Tạo các cột
					XSSFCell cell5i0 = row5i.createCell(0);
					cell5i0.setCellStyle(style);
					if (soDuKy.getDoiTuong() != null) {
						cell5i0.setCellValue(soDuKy.getDoiTuong().getMaDt());
					} else {
						cell5i0.setCellValue("");
					}
					XSSFCell cell5i1 = row5i.createCell(1);
					cell5i1.setCellStyle(style);
					if (soDuKy.getLoaiTaiKhoan() != null) {
						cell5i1.setCellValue(soDuKy.getLoaiTaiKhoan().getMaTk());
					} else {
						cell5i1.setCellValue("");
					}
					XSSFCell cell5i2 = row5i.createCell(2);
					cell5i2.setCellStyle(style);
					cell5i2.setCellValue(soDuKy.getNoDauKy());
					XSSFCell cell5i3 = row5i.createCell(3);
					cell5i3.setCellStyle(style);
					cell5i3.setCellValue(soDuKy.getCoDauKy());

					i++;
				}
			}

			// Resize column's width
			nhanVienCnSt.autoSizeColumn(0);
			nhanVienCnSt.autoSizeColumn(1);
			nhanVienCnSt.autoSizeColumn(2);
			nhanVienCnSt.autoSizeColumn(3);

			logger.info("Trả kết quả về máy khác download ...");

			res.reset();
			res.resetBuffer();
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
			res.setHeader("Content-disposition",
					"attachment; filename=" + props.getCauHinh(PropCont.SO_DU_DAU_KY_EXCEL_TEN_FILE).getGiaTri());
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
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
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		KyKeToanForm form = new KyKeToanForm();
		form.setKyKeToan(kyKeToan);

		model.addAttribute("mainFinanceForm", form);
		model.addAttribute("tab", "tabKKT");
		return "nhapSoDuKyKeToan";
	}

	@RequestMapping(value = "/kyketoan/soduky/luusoduky", method = RequestMethod.POST)
	public String luuSoDuKyKeToan(@ModelAttribute("mainFinanceForm") KyKeToanForm form, Model model) {
		// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
		model.addAttribute("kpiGroups", dungChung.getKpiGroups());

		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(form.getKyKeToan().getMaKyKt());
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		if (form != null && form.getSoDuKyFile() != null && form.getSoDuKyFile().getSize() > 0) {
			try {
				XSSFWorkbook wb = new XSSFWorkbook(form.getSoDuKyFile().getInputStream());

				logger.info("Xử lý dữ liệu tại sheet Số dư tài khoản ...");
				XSSFSheet soDuTkSt = wb.getSheetAt(0);
				List<SoDuKy> soDuTkDs = new ArrayList<>();

				Iterator<Row> soDuTkIter = soDuTkSt.rowIterator();
				// Skip the first row
				soDuTkIter.next();
				while (soDuTkIter.hasNext()) {
					try {
						XSSFRow row0i = (XSSFRow) soDuTkIter.next();

						// Đọc cột maTk
						XSSFCell cell0i0 = row0i.getCell(0);
						CellType type = cell0i0.getCellTypeEnum();

						String maTk = cell0i0.getRawValue();
						logger.info("maTk " + maTk);
						// Đọc cột nợ
						XSSFCell cell0i1 = row0i.getCell(1);
						double noDauKy = 0;
						try {
							noDauKy = cell0i1.getNumericCellValue();
						} catch (Exception e) {
						}
						logger.info("noDauKy " + noDauKy);
						// Đọc cột có
						XSSFCell cell0i2 = row0i.getCell(2);
						double coDauKy = 0;
						try {
							coDauKy = cell0i2.getNumericCellValue();
						} catch (Exception e) {
						}
						logger.info("coDauKy " + coDauKy);
						if (maTk != null && !maTk.trim().equals("")) {
							SoDuKy soDuKy = new SoDuKy();

							LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
							loaiTaiKhoan.setMaTk(maTk);

							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);
							soDuKy.setNoDauKy(noDauKy);
							soDuKy.setCoDauKy(coDauKy);

							soDuKy.setKyKeToan(kyKeToan);

							soDuTkDs.add(soDuKy);
							kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}

				logger.info("Xử lý dữ liệu tại sheet Khách hàng ...");
				XSSFSheet khachHangSt = wb.getSheetAt(1);
				List<DoiTuong> khachHangDs = new ArrayList<>();

				Iterator<Row> khachHangIter = khachHangSt.rowIterator();
				// Skip the first row
				khachHangIter.next();
				while (khachHangIter.hasNext()) {
					try {
						XSSFRow row1i = (XSSFRow) khachHangIter.next();

						// Đọc cột Mã KH
						XSSFCell cell1i0 = row1i.getCell(0);
						String khKh = cell1i0.getRawValue();

						// Đọc cột Tên KH
						XSSFCell cell1i1 = row1i.getCell(1);
						String tenKh = cell1i1.getRawValue();

						// Đọc cột Mã số thuế
						XSSFCell cell1i2 = row1i.getCell(2);
						String maThue = cell1i2.getRawValue();

						// Đọc cột Địa chỉ
						XSSFCell cell1i3 = row1i.getCell(3);
						String diaChi = cell1i3.getRawValue();

						// Đọc cột Email
						XSSFCell cell1i4 = row1i.getCell(4);
						String email = cell1i4.getRawValue();

						// Đọc cột Số điện thoại
						XSSFCell cell1i5 = row1i.getCell(5);
						String sdt = cell1i5.getRawValue();

						// Đọc cột Website
						XSSFCell cell1i6 = row1i.getCell(6);
						String webSite = cell1i6.getRawValue();

						if (khKh != null && !khKh.trim().equals("")) {
							KhachHang khachHang = new KhachHang();

							khachHang.setKhKh(khKh);
							khachHang.setTenKh(tenKh);
							khachHang.setMaThue(maThue);
							khachHang.setDiaChi(diaChi);
							khachHang.setEmail(email);
							khachHang.setSdt(sdt);
							khachHang.setWebSite(webSite);

							// khachHangDs.add(khachHang);
							khachHangDAO.luuCapNhatKhachHang(khachHang);
						}
					} catch (Exception e) {
					}
				}

				logger.info("Xử lý dữ liệu tại sheet Công nợ KH ...");
				XSSFSheet khachHangCnSt = wb.getSheetAt(2);
				List<SoDuKy> khachHangCnDs = new ArrayList<>();

				Iterator<Row> khachHangCnIter = khachHangCnSt.rowIterator();
				// Skip the first row
				khachHangCnIter.next();
				while (khachHangCnIter.hasNext()) {
					try {
						XSSFRow row2i = (XSSFRow) khachHangCnIter.next();

						// Đọc cột Mã Kh
						XSSFCell cell2i0 = row2i.getCell(0);
						String khKh = cell2i0.getRawValue();

						// Đọc cột Mã tài khoản
						XSSFCell cell2i1 = row2i.getCell(1);
						String maTk = cell2i1.getRawValue();

						// Đọc cột nợ
						XSSFCell cell2i2 = row2i.getCell(2);
						double noDauKy = 0;
						try {
							noDauKy = cell2i2.getNumericCellValue();
						} catch (Exception e) {
						}

						// Đọc cột có
						XSSFCell cell2i3 = row2i.getCell(3);
						double coDauKy = 0;
						try {
							coDauKy = cell2i3.getNumericCellValue();
						} catch (Exception e) {
						}

						if (khKh != null && !khKh.trim().equals("") && maTk != null && !maTk.trim().equals("")) {
							SoDuKy soDuKy = new SoDuKy();

							DoiTuong doiTuong = new DoiTuong();
							doiTuong.setKhDt(khKh);
							doiTuong.setLoaiDt(DoiTuong.KHACH_HANG);

							LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
							loaiTaiKhoan.setMaTk(maTk);

							soDuKy.setDoiTuong(doiTuong);
							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);
							soDuKy.setNoDauKy(noDauKy);
							soDuKy.setCoDauKy(coDauKy);

							khachHangCnDs.add(soDuKy);
							kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
						}
					} catch (Exception e) {
					}
				}

				logger.info("Xử lý dữ liệu tại sheet Nhà cung cấp ...");
				XSSFSheet nhaCcSt = wb.getSheetAt(3);
				List<DoiTuong> nhaCcDs = new ArrayList<>();

				Iterator<Row> nhaCcIter = nhaCcSt.rowIterator();
				// Skip the first row
				nhaCcIter.next();
				while (nhaCcIter.hasNext()) {
					try {
						XSSFRow row3i = (XSSFRow) nhaCcIter.next();

						// Đọc cột Mã KH
						XSSFCell cell3i0 = row3i.getCell(0);
						String khNcc = cell3i0.getRawValue();

						// Đọc cột Tên KH
						XSSFCell cell3i1 = row3i.getCell(1);
						String tenNcc = cell3i1.getRawValue();

						// Đọc cột Mã số thuế
						XSSFCell cell3i2 = row3i.getCell(2);
						String maThue = cell3i2.getRawValue();

						// Đọc cột Địa chỉ
						XSSFCell cell3i3 = row3i.getCell(3);
						String diaChi = cell3i3.getRawValue();

						// Đọc cột Email
						XSSFCell cell3i4 = row3i.getCell(4);
						String email = cell3i4.getRawValue();

						// Đọc cột Số điện thoại
						XSSFCell cell3i5 = row3i.getCell(5);
						String sdt = cell3i5.getRawValue();

						// Đọc cột Website
						XSSFCell cell3i6 = row3i.getCell(6);
						String webSite = cell3i6.getRawValue();

						if (khNcc != null && !khNcc.trim().equals("")) {
							NhaCungCap nhaCc = new NhaCungCap();

							nhaCc.setKhNcc(khNcc);
							nhaCc.setTenNcc(tenNcc);
							nhaCc.setMaThue(maThue);
							nhaCc.setDiaChi(diaChi);
							nhaCc.setEmail(email);
							nhaCc.setSdt(sdt);
							nhaCc.setWebSite(webSite);

							// nhaCcDs.add(nhaCc);
							nhaCungCapDAO.luuCapNhatNhaCungCap(nhaCc);
						}
					} catch (Exception e) {
					}
				}

				logger.info("Xử lý dữ liệu tại sheet Công nợ NCC ...");
				XSSFSheet nhaCcCnSt = wb.getSheetAt(4);
				List<SoDuKy> nhaCcCnDs = new ArrayList<>();

				Iterator<Row> nhaCcCnIter = nhaCcCnSt.rowIterator();
				// Skip the first row
				nhaCcCnIter.next();
				while (nhaCcCnIter.hasNext()) {
					try {
						XSSFRow row4i = (XSSFRow) nhaCcCnIter.next();

						// Đọc cột Mã Kh
						XSSFCell cell4i0 = row4i.getCell(0);
						String khNcc = cell4i0.getRawValue();

						// Đọc cột Mã tài khoản
						XSSFCell cell4i1 = row4i.getCell(1);
						String maTk = cell4i1.getRawValue();

						// Đọc cột nợ
						XSSFCell cell4i2 = row4i.getCell(2);
						double noDauKy = 0;
						try {
							noDauKy = cell4i2.getNumericCellValue();
						} catch (Exception e) {
						}

						// Đọc cột có
						XSSFCell cell4i3 = row4i.getCell(3);
						double coDauKy = 0;
						try {
							coDauKy = cell4i3.getNumericCellValue();
						} catch (Exception e) {
						}

						if (khNcc != null && !khNcc.trim().equals("") && maTk != null && !maTk.trim().equals("")) {
							SoDuKy soDuKy = new SoDuKy();

							DoiTuong doiTuong = new DoiTuong();
							doiTuong.setKhDt(khNcc);
							doiTuong.setLoaiDt(DoiTuong.NHA_CUNG_CAP);

							LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
							loaiTaiKhoan.setMaTk(maTk);

							soDuKy.setDoiTuong(doiTuong);
							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);
							soDuKy.setNoDauKy(noDauKy);
							soDuKy.setCoDauKy(coDauKy);

							nhaCcCnDs.add(soDuKy);
							kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
						}
					} catch (Exception e) {
					}
				}

				logger.info("Xử lý dữ liệu tại sheet Công nợ NV ...");
				XSSFSheet nhanVienCnSt = wb.getSheetAt(5);
				List<SoDuKy> nhanVienCnDs = new ArrayList<>();

				Iterator<Row> nhanVienCnIter = nhanVienCnSt.rowIterator();
				// Skip the first row
				nhanVienCnIter.next();
				while (nhanVienCnIter.hasNext()) {
					try {
						XSSFRow row5i = (XSSFRow) nhanVienCnIter.next();

						// Đọc cột Mã Kh
						XSSFCell cell5i0 = row5i.getCell(0);
						String maNv = cell5i0.getRawValue();
						int maNvNum = 0;
						try {
							maNvNum = Integer.parseInt(maNv);
						} catch (Exception e) {
						}

						// Đọc cột Mã tài khoản
						XSSFCell cell5i1 = row5i.getCell(1);
						String maTk = cell5i1.getRawValue();

						// Đọc cột nợ
						XSSFCell cell5i2 = row5i.getCell(2);
						double noDauKy = 0;
						try {
							noDauKy = cell5i2.getNumericCellValue();
						} catch (Exception e) {
						}

						// Đọc cột có
						XSSFCell cell5i3 = row5i.getCell(3);
						double coDauKy = 0;
						try {
							coDauKy = cell5i3.getNumericCellValue();
						} catch (Exception e) {
						}

						if (maNvNum != 0 && maTk != null && !maTk.trim().equals("")) {
							SoDuKy soDuKy = new SoDuKy();

							DoiTuong doiTuong = new DoiTuong();
							doiTuong.setMaDt(maNvNum);
							doiTuong.setLoaiDt(DoiTuong.NHAN_VIEN);

							LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
							loaiTaiKhoan.setMaTk(maTk);

							soDuKy.setDoiTuong(doiTuong);
							soDuKy.setLoaiTaiKhoan(loaiTaiKhoan);
							soDuKy.setNoDauKy(noDauKy);
							soDuKy.setCoDauKy(coDauKy);

							nhanVienCnDs.add(soDuKy);
							kyKeToanDAO.themCapNhatSoDuDauKy(soDuKy);
						}
					} catch (Exception e) {
					}
				}

				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/kyketoan/xem/" + kyKeToan.getMaKyKt();
		} else {
			String comment = "Hãy chọn file exel số dư đâu kỳ dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			model.addAttribute("mainFinanceForm", form);
			model.addAttribute("tab", "tabKKT");

			return "nhapSoDuKyKeToan";
		}

	}

	private List<SoDuKy> layCongNoDoiTuong(KyKeToan kyKeToan, int loaiDt) {
		if (kyKeToan == null) {
			return null;
		}

		List<SoDuKy> doiTuongDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(kyKeToan.getMaKyKt(), loaiDt);
		List<SoDuKy> phaiThuDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, loaiDt, LoaiTaiKhoan.PHAI_THU_KHACH_HANG);
		List<SoDuKy> phaiTraDs = kyKeToanDAO.tinhSoDuKyTheoDoiTuong(kyKeToan, loaiDt, LoaiTaiKhoan.PHAI_TRA_NGUOI_BAN);

		List<SoDuKy> doiTuongCNDs = new ArrayList<>();
		doiTuongCNDs.addAll(phaiThuDs);
		doiTuongCNDs.addAll(phaiTraDs);
		Iterator<SoDuKy> doiTuongIter = doiTuongCNDs.iterator();
		while (doiTuongIter.hasNext()) {
			SoDuKy soDuKy = doiTuongIter.next();
			soDuKy.setKyKeToan(kyKeToan);
		}

		if (doiTuongDs != null) {
			doiTuongIter = doiTuongDs.iterator();
			while (doiTuongIter.hasNext()) {
				SoDuKy soDuKy = doiTuongIter.next();

				Iterator<SoDuKy> cnDtIter = doiTuongCNDs.iterator();
				while (cnDtIter.hasNext()) {
					SoDuKy soDuKyTmpl = cnDtIter.next();

					if (soDuKy.equals(soDuKyTmpl)) {
						soDuKy.tron(soDuKyTmpl);
						break;
					}
				}
			}
		}

		List<SoDuKy> doiTuongTmplDs = new ArrayList<>();
		Iterator<SoDuKy> cnDtIter = doiTuongCNDs.iterator();
		while (cnDtIter.hasNext()) {
			SoDuKy soDuKyTmpl = cnDtIter.next();

			doiTuongIter = doiTuongDs.iterator();
			while (doiTuongIter.hasNext()) {
				SoDuKy soDuKy = doiTuongIter.next();

				if (!soDuKyTmpl.equals(soDuKy)) {
					doiTuongTmplDs.add(soDuKyTmpl);
					break;
				}
			}
		}
		doiTuongDs.addAll(doiTuongTmplDs);

		return doiTuongDs;
	}

	@RequestMapping(value = "/kyketoan/soduky/congnokh/luu", method = RequestMethod.POST)
	public String luuTaoMoiKyKeToanSoDuKyCongNoKh(@ModelAttribute("mainFinanceFormKh") SoDuKy soDuKy, Model model) {
		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(soDuKy.getKyKeToan().getMaKyKt());
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		try {
			luuTaoMoiKyKeToanSoDuKyCongNo(kyKeToan, soDuKy);

			return "redirect:/kyketoan/xem/" + soDuKy.getKyKeToan().getMaKyKt();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/kyketoan/soduky/congnoncc/luu", method = RequestMethod.POST)
	public String luuTaoMoiKyKeToanSoDuKyCongNoNcc(@ModelAttribute("mainFinanceFormNcc") SoDuKy soDuKy, Model model) {
		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(soDuKy.getKyKeToan().getMaKyKt());
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		try {
			luuTaoMoiKyKeToanSoDuKyCongNo(kyKeToan, soDuKy);

			return "redirect:/kyketoan/xem/" + soDuKy.getKyKeToan().getMaKyKt();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/kyketoan/soduky/congnonv/luu", method = RequestMethod.POST)
	public String luuTaoMoiKyKeToanSoDuKyCongNoNv(@ModelAttribute("mainFinanceFormNv") SoDuKy soDuKy, Model model) {
		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(soDuKy.getKyKeToan().getMaKyKt());
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		try {
			luuTaoMoiKyKeToanSoDuKyCongNo(kyKeToan, soDuKy);

			return "redirect:/kyketoan/xem/" + soDuKy.getKyKeToan().getMaKyKt();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/kyketoan/soduky/tonkhovthh/luu", method = RequestMethod.POST)
	public String luuTaoMoiKyKeToanSoDuKyTonKhoVthh(@ModelAttribute("mainFinanceFormTkVthh") SoDuKy soDuKy,
			Model model) {
		// Tìm kỳ hiện tại
		KyKeToan kyKeToan = kyKeToanDAO.layKyKeToan(soDuKy.getKyKeToan().getMaKyKt());
		if (kyKeToan == null || kyKeToan.getTrangThai() == KyKeToan.DONG) {
			return "redirect:/kyketoan/danhsach";
		}

		try {
			luuTaoMoiKyKeToanSoDuKyTonKhoVthh(kyKeToan, soDuKy);

			return "redirect:/kyketoan/xem/" + soDuKy.getKyKeToan().getMaKyKt();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private void luuTaoMoiKyKeToanSoDuKyCongNo(KyKeToan kyKeToan, SoDuKy soDuKy) {
		if (kyKeToan == null || soDuKy == null || soDuKy.getLoaiTaiKhoan() == null || soDuKy.getDoiTuong() == null
				|| soDuKy.getKyKeToan() == null) {
			return;
		}

		logger.info("soDuKy mới nhập: " + soDuKy);
		// Lấy số dư kỳ trước đây của tài khoản đang xét
		SoDuKy soDuKyTruoc = kyKeToanDAO.laySoDuKyTheoDoiTuong(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getDoiTuong().getLoaiDt(), soDuKy.getDoiTuong().getMaDt());
		if (soDuKyTruoc == null) {
			soDuKyTruoc = new SoDuKy();
			soDuKyTruoc.setKyKeToan(kyKeToan);
			soDuKyTruoc.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
			soDuKyTruoc.setDoiTuong(soDuKy.getDoiTuong());
		}

		logger.info("soDuKy trước đây: " + soDuKyTruoc);

		// Lấy số dư kỳ của các tài khoản đó
		List<SoDuKy> soDuKyTkDs = kyKeToanDAO.danhSachSoDuKy(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt());

		// Lấy số dư kỳ của các tài khoản đó cho phần công nợ
		List<SoDuKy> soDuKyDtDs = kyKeToanDAO.danhSachSoDuKyTheoDoiTuong(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getDoiTuong().getLoaiDt(), soDuKy.getDoiTuong().getMaDt());

		// Lấy danh sách tài khoản cha/con của tài khoản đang xét
		List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan(soDuKy.getLoaiTaiKhoan().getMaTk());
		// Cập nhật giá trị mới cho tất cả tài khoản cha của tài khoản đang xét
		if (loaiTaiKhoanDs != null) {
			Iterator<LoaiTaiKhoan> loaiTaiKhoanIter = loaiTaiKhoanDs.iterator();
			while (loaiTaiKhoanIter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanIter.next();
				logger.info("==== loaiTaiKhoan: " + loaiTaiKhoan);

				// CẬP NHẬT CHO PHẦN SỐ DƯ TÀI KHOẢN
				SoDuKy soDuKyTk = new SoDuKy();
				soDuKyTk.setKyKeToan(kyKeToan);
				soDuKyTk.setLoaiTaiKhoan(loaiTaiKhoan);

				// Tìm xem trước đó đã có số dư kỳ của tk hiện tại chưa
				if (soDuKyTkDs != null) {
					Iterator<SoDuKy> soDuKyIter = soDuKyTkDs.iterator();
					while (soDuKyIter.hasNext()) {
						SoDuKy soDuKyTkTmpl = soDuKyIter.next();

						if (loaiTaiKhoan.equals(soDuKyTkTmpl.getLoaiTaiKhoan())) {
							soDuKyTk = soDuKyTkTmpl;
							break;
						}
					}
				}

				logger.info("soDuKyTk hiện tại: " + soDuKyTk);
				// Cập nhật dữ liệu mới
				soDuKyTk.setNoDauKy(soDuKyTk.getNoDauKy() + soDuKy.getNoDauKy() - soDuKyTruoc.getNoDauKy());
				soDuKyTk.setCoDauKy(soDuKyTk.getCoDauKy() + soDuKy.getCoDauKy() - soDuKyTruoc.getCoDauKy());
				soDuKyTk.setNoCuoiKy(soDuKyTk.getNoCuoiKy() + soDuKy.getNoCuoiKy() - soDuKyTruoc.getNoCuoiKy());
				soDuKyTk.setCoCuoiKy(soDuKyTk.getCoCuoiKy() + soDuKy.getCoCuoiKy() - soDuKyTruoc.getCoCuoiKy());
				logger.info("soDuKyTk mới: " + soDuKyTk);

				// Cập nhật ngược vào csdl
				kyKeToanDAO.themCapNhatSoDuDauKy(soDuKyTk);

				// CẬP NHẬT CHO PHẦN CÔNG NỢ
				SoDuKy soDuKyDt = new SoDuKy();
				soDuKyDt.setKyKeToan(kyKeToan);
				soDuKyDt.setLoaiTaiKhoan(loaiTaiKhoan);
				soDuKyDt.setDoiTuong(soDuKy.getDoiTuong());

				// Tìm xem trước đó đã có số dư kỳ của tk hiện tại chưa
				if (soDuKyDtDs != null) {
					Iterator<SoDuKy> soDuKyDtIter = soDuKyDtDs.iterator();
					while (soDuKyDtIter.hasNext()) {
						SoDuKy soDuKyDtTmpl = soDuKyDtIter.next();

						if (loaiTaiKhoan.equals(soDuKyDtTmpl.getLoaiTaiKhoan())) {
							soDuKyDt = soDuKyDtTmpl;
							break;
						}
					}
				}

				logger.info("soDuKyDt hiện tại: " + soDuKyDt);
				// Cập nhật dữ liệu mới
				if (soDuKyDt.getLoaiTaiKhoan().equals(soDuKy.getLoaiTaiKhoan())) {
					soDuKyDt.setNoDauKy(soDuKy.getNoDauKy());
					soDuKyDt.setCoDauKy(soDuKy.getCoDauKy());
					soDuKyDt.setNoCuoiKy(soDuKy.getNoCuoiKy());
					soDuKyDt.setCoCuoiKy(soDuKy.getCoCuoiKy());
				} else {
					soDuKyDt.setNoDauKy(soDuKyDt.getNoDauKy() + soDuKy.getNoDauKy() - soDuKyTruoc.getNoDauKy());
					soDuKyDt.setCoDauKy(soDuKyDt.getCoDauKy() + soDuKy.getCoDauKy() - soDuKyTruoc.getCoDauKy());
					soDuKyDt.setNoCuoiKy(soDuKyDt.getNoCuoiKy() + soDuKy.getNoCuoiKy() - soDuKyTruoc.getNoCuoiKy());
					soDuKyDt.setCoCuoiKy(soDuKyDt.getCoCuoiKy() + soDuKy.getCoCuoiKy() - soDuKyTruoc.getCoCuoiKy());
				}

				logger.info("soDuKyDt mới: " + soDuKyDt);

				// Cập nhật ngược vào csdl
				kyKeToanDAO.themCapNhatSoDuDauKy(soDuKyDt);
			}
		}
	}

	private void luuTaoMoiKyKeToanSoDuKyTonKhoVthh(KyKeToan kyKeToan, SoDuKy soDuKy) {
		if (kyKeToan == null || soDuKy == null || soDuKy.getLoaiTaiKhoan() == null || soDuKy.getHangHoa() == null
				|| soDuKy.getKhoHang() == null || soDuKy.getHangHoa().getDonVi() == null) {
			return;
		}

		logger.info("soDuKy mới nhập: " + soDuKy);
		// Lấy số dư kỳ trước đây của tài khoản đang xét
		SoDuKy soDuKyTruoc = kyKeToanDAO.laySoDuKyTheoHangHoa(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getHangHoa().getMaHh(), soDuKy.getKhoHang().getMaKho());
		if (soDuKyTruoc == null) {
			soDuKyTruoc = new SoDuKy();
			soDuKyTruoc.setKyKeToan(kyKeToan);
			soDuKyTruoc.setLoaiTaiKhoan(soDuKy.getLoaiTaiKhoan());
			soDuKyTruoc.setHangHoa(soDuKy.getHangHoa());
			soDuKyTruoc.setKhoHang(soDuKy.getKhoHang());
		}

		logger.info("soDuKy trước đây: " + soDuKyTruoc);

		// Lấy số dư kỳ của các tài khoản đó
		List<SoDuKy> soDuKyTkDs = kyKeToanDAO.danhSachSoDuKy(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt());

		// Lấy số dư kỳ của các tài khoản đó cho phần tồn kho
		List<SoDuKy> soDuKyHhDs = kyKeToanDAO.danhSachSoDuKyTheoHangHoa(soDuKy.getLoaiTaiKhoan().getMaTk(),
				soDuKy.getKyKeToan().getMaKyKt(), soDuKy.getHangHoa().getMaHh(), soDuKy.getKhoHang().getMaKho());

		// Lấy danh sách tài khoản cha/con của tài khoản đang xét
		List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan(soDuKy.getLoaiTaiKhoan().getMaTk());
		// Cập nhật giá trị mới cho tất cả tài khoản cha của tài khoản đang xét
		if (loaiTaiKhoanDs != null) {
			Iterator<LoaiTaiKhoan> loaiTaiKhoanIter = loaiTaiKhoanDs.iterator();
			while (loaiTaiKhoanIter.hasNext()) {
				LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanIter.next();
				logger.info("==== loaiTaiKhoan: " + loaiTaiKhoan);

				// CẬP NHẬT CHO PHẦN SỐ DƯ TÀI KHOẢN
				SoDuKy soDuKyTk = new SoDuKy();
				soDuKyTk.setKyKeToan(kyKeToan);
				soDuKyTk.setLoaiTaiKhoan(loaiTaiKhoan);
				soDuKyTk.setHangHoa(soDuKy.getHangHoa());
				soDuKyTk.setKhoHang(soDuKy.getKhoHang());

				// Tìm xem trước đó đã có số dư kỳ của tk hiện tại chưa
				if (soDuKyTkDs != null) {
					Iterator<SoDuKy> soDuKyIter = soDuKyTkDs.iterator();
					while (soDuKyIter.hasNext()) {
						SoDuKy soDuKyTkTmpl = soDuKyIter.next();

						if (loaiTaiKhoan.equals(soDuKyTkTmpl.getLoaiTaiKhoan())) {
							soDuKyTk = soDuKyTkTmpl;
							break;
						}
					}
				}

				logger.info("soDuKyTk hiện tại: " + soDuKyTk);
				// Cập nhật dữ liệu mới
				soDuKyTk.setNoDauKy(soDuKyTk.getNoDauKy() + soDuKy.getNoDauKy() - soDuKyTruoc.getNoDauKy());
				soDuKyTk.setCoDauKy(soDuKyTk.getCoDauKy() + soDuKy.getCoDauKy() - soDuKyTruoc.getCoDauKy());
				soDuKyTk.setNoCuoiKy(soDuKyTk.getNoCuoiKy() + soDuKy.getNoCuoiKy() - soDuKyTruoc.getNoCuoiKy());
				soDuKyTk.setCoCuoiKy(soDuKyTk.getCoCuoiKy() + soDuKy.getCoCuoiKy() - soDuKyTruoc.getCoCuoiKy());
				logger.info("soDuKyTk mới: " + soDuKyTk);

				// Cập nhật ngược vào csdl
				kyKeToanDAO.themCapNhatSoDuDauKy(soDuKyTk);

				// CẬP NHẬT CHO PHẦN CÔNG NỢ
				SoDuKy soDuKyHh = new SoDuKy();
				soDuKyHh.setKyKeToan(kyKeToan);
				soDuKyHh.setLoaiTaiKhoan(loaiTaiKhoan);
				soDuKyHh.setHangHoa(soDuKy.getHangHoa());
				soDuKyHh.setKhoHang(soDuKy.getKhoHang());

				// Tìm xem trước đó đã có số dư kỳ của tk hiện tại chưa
				if (soDuKyHhDs != null) {
					Iterator<SoDuKy> soDuKyHhIter = soDuKyHhDs.iterator();
					while (soDuKyHhIter.hasNext()) {
						SoDuKy soDuKyHhTmpl = soDuKyHhIter.next();

						if (loaiTaiKhoan.equals(soDuKyHhTmpl.getLoaiTaiKhoan())) {
							soDuKyHh = soDuKyHhTmpl;
							break;
						}
					}
				}

				logger.info("soDuKyHh hiện tại: " + soDuKyHh);
				// Cập nhật dữ liệu mới
				if (soDuKyHh.getLoaiTaiKhoan().equals(soDuKy.getLoaiTaiKhoan())) {
					soDuKyHh.setNoDauKy(soDuKy.getNoDauKy());
					soDuKyHh.setCoDauKy(soDuKy.getCoDauKy());
					soDuKyHh.setNoCuoiKy(soDuKy.getNoCuoiKy());
					soDuKyHh.setCoCuoiKy(soDuKy.getCoCuoiKy());

					soDuKyHh.getHangHoa().setSoLuong(soDuKy.getHangHoa().getSoLuong());
				} else {
					soDuKyHh.setNoDauKy(soDuKyHh.getNoDauKy() + soDuKy.getNoDauKy() - soDuKyTruoc.getNoDauKy());
					soDuKyHh.setCoDauKy(soDuKyHh.getCoDauKy() + soDuKy.getCoDauKy() - soDuKyTruoc.getCoDauKy());
					soDuKyHh.setNoCuoiKy(soDuKyHh.getNoCuoiKy() + soDuKy.getNoCuoiKy() - soDuKyTruoc.getNoCuoiKy());
					soDuKyHh.setCoCuoiKy(soDuKyHh.getCoCuoiKy() + soDuKy.getCoCuoiKy() - soDuKyTruoc.getCoCuoiKy());

					double soLuong = soDuKyHh.getHangHoa().getSoLuong() + soDuKy.getHangHoa().getSoLuong()
							- soDuKyTruoc.getHangHoa().getSoLuong();
					soDuKyHh.getHangHoa().setSoLuong(soLuong);
				}

				logger.info("soDuKyHh mới: " + soDuKyHh);

				// Cập nhật ngược vào csdl
				kyKeToanDAO.themCapNhatSoDuDauKy(soDuKyHh);
			}
		}
	}

	@RequestMapping("/kyketoan/soduky/congno")
	@ResponseBody
	public SoDuKy laySoDuKyCongNo(@RequestParam("maTk") String maTk, @RequestParam("maKkt") int maKkt,
			@RequestParam("loaiDt") int loaiDt, @RequestParam("maDt") int maDt, Model model) {
		logger.info("maTk: " + maTk);
		logger.info("maKkt: " + maKkt);
		logger.info("loaiDt: " + loaiDt);
		logger.info("maDt: " + maDt);

		SoDuKy soDuKy = kyKeToanDAO.laySoDuKyTheoDoiTuong(maTk, maKkt, loaiDt, maDt);
		if (soDuKy == null) {
			soDuKy = new SoDuKy();
		}

		return soDuKy;
	}

	@RequestMapping("/kyketoan/soduky/tonkho")
	@ResponseBody
	public SoDuKy laySoDuKyTonKho(@RequestParam("maTk") String maTk, @RequestParam("maKkt") int maKkt,
			@RequestParam("maHh") int maHh, @RequestParam("maKho") int maKho, Model model) {
		logger.info("maTk: " + maTk);
		logger.info("maKkt: " + maKkt);
		logger.info("maHh: " + maHh);
		logger.info("maKho: " + maKho);

		SoDuKy soDuKy = kyKeToanDAO.laySoDuKyTheoHangHoa(maTk, maKkt, maHh, maKho);
		if (soDuKy == null) {
			soDuKy = new SoDuKy();
		}

		return soDuKy;
	}
}
