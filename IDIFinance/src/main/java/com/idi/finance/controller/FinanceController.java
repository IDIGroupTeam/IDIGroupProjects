package com.idi.finance.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.idi.finance.bean.BalanceSheet;
import com.idi.finance.utils.ExcelProcessor;

@Controller
public class FinanceController {
	private static final Logger logger = Logger.getLogger(FinanceController.class);

	@RequestMapping("/")
	public String finance(Model model) {
		// Đọc thông tin một biểu đồ mặc định và vẽ biểu đồ đó ra
		
		return "finance";
	}

	@RequestMapping("kpi")
	public String kpi(Model model) {
		return "finance";
	}

	@RequestMapping("/update")
	public String update(Model model) {
		return "update";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @RequestParam("file") MultipartFile file) {
		if (file != null && file.getSize() > 0) {
			System.out.println(file.getName() + " - " + file.getSize());
			try {
				List<BalanceSheet> financies = ExcelProcessor.readExcel(file.getInputStream());

				return "redirect:/";
			} catch (Exception e) {
				String comment = "Không thể đọc excel file " + file.getName()
						+ ". Có thể file bị lỗi, không đúng định dạng, hoặc đường truyền chậm, xin mời thử lại.";
				model.addAttribute("comment", comment);
				return "update";
			}
		} else {
			String comment = "Hãy chọn file exel dữ liệu kế toán.";
			model.addAttribute("comment", comment);
			return "update";
		}
	}
}
