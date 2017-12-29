package com.idi.customers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CRMController {
	private static final Logger logger = Logger.getLogger(CRMController.class);

	
	@RequestMapping(value = { "/", "/home" })
	public String welcome(Model model) {
		String strDes =null;
		logger.info("Inside welcome() method");
		model.addAttribute("name", "Phần mềm quản trị IDIGroup");
		strDes="Xin chào Bạn! \n"
				+"Phần mềm đang trong giai đoạn xây dựng \n"
				+"Chúng tôi rất cần sự đóng góp ý kiến xây dựng của bạn \n"
				+"Bạn có thể trải nghiệm tại các Module Nhân sự, Module Tài chính & Kế toán";
		model.addAttribute("description", strDes);
		return "home";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) {
		return "login";
	}
	@RequestMapping(value="/thongbao")
	public String thongbao(Model model) {
		return "thongbao";
	}
}
