package com.idi.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.bean.DungChung;
import com.idi.finance.dao.CauHinhDAO;
import com.idi.finance.dao.TaiKhoanDAO;

@Controller
public class CauHinhController {
	private static final Logger logger = Logger.getLogger(CauHinhController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	CauHinhDAO cauHinhDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Value("${PHIEU_THU_DS_TK_NO}")
	private String PHIEU_THU_DS_TK_NO;

	@Value("${PHIEU_CHI_DS_TK_CO}")
	private String PHIEU_CHI_DS_TK_CO;

	@Value("${BAO_NO_DS_TK_NO}")
	private String BAO_NO_DS_TK_NO;

	@Value("${BAO_CO_DS_TK_CO}")
	private String BAO_CO_DS_TK_CO;

	@Value("${MUA_HANG_DS_TK_KHO_NO}")
	private String MUA_HANG_DS_TK_KHO_NO;

	@Value("${MUA_HANG_DS_TK_CONG_NO_CO}")
	private String MUA_HANG_DS_TK_CONG_NO_CO;

	@Value("${MUA_HANG_DS_TK_GTGT_NO}")
	private String MUA_HANG_DS_TK_GTGT_NO;

	@Value("${MUA_HANG_DS_TK_TTDB_CO}")
	private String MUA_HANG_DS_TK_TTDB_CO;

	@Value("${BAN_HANG_DS_TK_CONG_NO_NO}")
	private String BAN_HANG_DS_TK_CONG_NO_NO;

	@Value("${BAN_HANG_DS_TK_DOANH_THU_CO}")
	private String BAN_HANG_DS_TK_DOANH_THU_CO;

	@Value("${BAN_HANG_DS_TK_GIA_VON_NO}")
	private String BAN_HANG_DS_TK_GIA_VON_NO;

	@Value("${BAN_HANG_DS_TK_KHO_NO}")
	private String BAN_HANG_DS_TK_KHO_NO;

	@Value("${BAN_HANG_DS_TK_GTGT_CO}")
	private String BAN_HANG_DS_TK_GTGT_CO;

	@Value("${BAN_HANG_DS_TK_XK_CO}")
	private String BAN_HANG_DS_TK_XK_CO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		// Form mục tiêu
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}

		if (target.getClass() == CauHinh.class) {
			// binder.setValidator(balanceSheetValidator);
		}
	}

	@RequestMapping("/cauhinh/danhsach")
	public String danhSachCauHinh(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());

			List<CauHinh> cauHinhChungDs = cauHinhDAO.danhSachCauHinh(CauHinh.NHOM_CHUNG);

			List<CauHinh> cauHinhTkDs = cauHinhDAO.danhSachCauHinh(CauHinh.NHOM_TK);

			// List<LoaiTaiKhoan> loaiTaiKhoanDs = taiKhoanDAO.danhSachTaiKhoan();

			model.addAttribute("cauHinhChungDs", cauHinhChungDs);
			model.addAttribute("cauHinhTkDs", cauHinhTkDs);
			// model.addAttribute("loaiTaiKhoanDs", loaiTaiKhoanDs);
			model.addAttribute("tab", "tabDSCH");
			return "danhSachCauHinh";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/cauhinh/capnhat")
	public @ResponseBody CauHinh cauHinhCapNhat(@RequestParam("maCh") String maCh,
			@RequestParam("giaTri") String giaTri) {
		logger.info("maCh " + maCh + ". giaTri " + giaTri);

		CauHinh cauHinh = new CauHinh();
		cauHinh.setMa(maCh);
		cauHinh.setGiaTri(giaTri);
		cauHinhDAO.capNhatCauHinh(cauHinh);

		return cauHinh;
	}
}
