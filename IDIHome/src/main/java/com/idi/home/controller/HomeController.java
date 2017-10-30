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
		logger.info("Inside welcome() method");
		model.addAttribute("name", "Tập đoàn IDI");
		model.addAttribute("description", "Xin chào !");
		return "home";
	}
}
