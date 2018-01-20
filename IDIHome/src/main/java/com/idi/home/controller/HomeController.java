package com.idi.home.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping(value = { "/", "/home" })
	public String welcome(Model model) {
		String strDes = null;
		logger.info("Inside welcome() method in HomeController");
		model.addAttribute("name", "Phần Mềm Hệ Thống Quản Trị IDIGroup");
		strDes = "<span>Xin chào Bạn! <br>" + "Phần mềm đang trong giai đoạn xây dựng <br> "
				+ "Chúng tôi rất cần sự đóng góp ý kiến xây dựng của bạn <br>"
				+ "Bạn có thể trải nghiệm các chức năng tại các Module Nhân sự, Module Tài chính & Kế toán <br>"
				+ "Bạn có thể xem và lấy các tài liệu của IDIGroup từ mục Tài liệu <br></span>";
		model.addAttribute("description", strDes);
		return "home";
	}
	@RequestMapping(value = "/thongbao")
	public String thongbao(Model model) {
		return "thongbao";
	}

	@RequestMapping(value = "/listTailieu")
	public String listVanban(Model model) {
		return "listTailieu";
	}

	@RequestMapping(value = "/listPicture")
	public String listPicture(Model model) {
		return "listPicture";
	}

	@RequestMapping(value = "/listAudioVideo")
	public String listAudioVideo(Model model) {
		return "listAudioVideo";
	}
	@RequestMapping(value="/lienhe") 
	public String lienhe(Model model) {
		return "lienhe";
	}
	@RequestMapping(value="/vechungtoi") 
	public String vechungtoi(Model model) {
		return "vechungtoi";
	}
	@RequestMapping(value="/tintuc") 
	public String tintuc(Model model) {
		return "tintuc";
	}
}   
