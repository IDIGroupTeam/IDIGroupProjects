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
		String strDes =null;
		logger.info("Inside welcome() method");
		model.addAttribute("name", "Phần mềm quản trị IDIGroup");
		strDes="Xin chào Ngài! \n"
				+"Phần mềm đang trong giai đoạn xây dựng \n"
				+"Chúng tôi rất cần sự đóng góp ý kiến xây dựng của Ngài \n" ;
		model.addAttribute("description", strDes);
		return "home";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) {
		return "login";
	}
}
