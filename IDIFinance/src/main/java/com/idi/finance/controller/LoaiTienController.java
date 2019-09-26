package com.idi.finance.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idi.finance.bean.DungChung;
import com.idi.finance.bean.kyketoan.KyKeToan;
import com.idi.finance.utils.PropCont;

@Controller
public class LoaiTienController {
	private static final Logger logger = Logger.getLogger(LoaiTienController.class);

	@Autowired
	DungChung dungChung;

	@Autowired
	PropCont props;

	@RequestMapping("/loaitien/danhsach")
	public String danhSachLoaiTien(Model model) {
		try {
			// Lấy danh sách các nhóm KPI từ csdl để tạo các tab
			model.addAttribute("kpiGroups", dungChung.getKpiGroups());
			KyKeToan kyKeToan = dungChung.getKyKeToan();
			if (kyKeToan == null) {
				return "koKyKeToanMacDinh";
			}

			model.addAttribute("kyKeToan", kyKeToan);
			model.addAttribute("tab", "tabDSLT");
			return "danhSachLoaiTien";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
