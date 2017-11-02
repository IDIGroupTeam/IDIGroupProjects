package com.idi.home.login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class LoginController1 {
@RequestMapping(value="/login",method=RequestMethod.GET)
 public String init(Model model) {
	model.addAttribute("msg","Xin điền thông tin chi tiết của bạn để truy cập");
	//System.out.println("Step init");
	return "login";
}
@RequestMapping(method=RequestMethod.POST)
public String submit(Model model, @ModelAttribute("loginBean") LoginBean loginBean) {
	System.out.println("Step 1");
	if (loginBean !=null && loginBean.getUserName() !=null & loginBean.getPassword() !=null) {
		if (loginBean.getUserName().equals("idigroup") && loginBean.getPassword().equals("idigroup")) {
			model.addAttribute("msg", "Welcome" +loginBean.getUserName());
			System.out.println("Step 2");
			return "success";
		}else {
			model.addAttribute("error", "Thông tin không đúng");
			System.out.println("Step 3");
			return "login";
		}
	}else {
		model.addAttribute("error", "Thông tin không đúng");
		System.out.println("Step 4");
		return "login";
	}
}
/*@RequestMapping(value="/login", method=RequestMethod.POST)
public ModelAndView loginAction(@ModelAttribute("loginBean")LoginBean bean){
    return new ModelAndView("success", "success", "Successful Login");
}*/
}
