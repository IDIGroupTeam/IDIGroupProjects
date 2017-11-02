package com.idi.home.controller;

import java.util.Locale;

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
		model.addAttribute("name", "Phần mềm quản trị IDIGroup");
		strDes = "Xin chào Bạn! \n" + "Phần mềm đang trong giai đoạn xây dựng \n"
				+ "Chúng tôi rất cần sự đóng góp ý kiến xây dựng của bạn \n"
				+ "Bạn có thể trải nghiệm tại các Module Nhân sự, Module Tài chính & Kế toán";
		model.addAttribute("description", strDes);
		return "home";
	}

	@RequestMapping(value = "/thongbao")
	public String thongbao(Model model) {
		return "thongbao";
	}

	@RequestMapping(value = "/listVanban")
	public String listVanban(Model model) {
		return "listVanban";
	}

	@RequestMapping(value = "/listPicture")
	public String listPicture(Model model) {
		return "listPicture";
	}

	@RequestMapping(value = "/listAudioVideo")
	public String listAudioVideo(Model model) {
		return "listAudioVideo";
	}

	// public String home(Locale locale, Model model)
	@RequestMapping(value = "/quychedautu")
	// public @ResponseBody String quychedautu(Model model) {
	public String quychedautu(Locale locale, Model model) {
		return "quychedautu";
	}

}
