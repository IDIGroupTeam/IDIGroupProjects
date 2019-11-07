package com.idi.hr.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.form.EmployeeForm;

@Controller
public class DepartmentController {

	private static final Logger log = Logger.getLogger(DepartmentController.class.getName());

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@RequestMapping(value = { "/department/" }, method = RequestMethod.GET)
	public String ListDepartments(Model model, HttpServletResponse response,  HttpServletRequest request) {
		try {
			List<Department> list = departmentDAO.getDepartments();

			// tinh so luong nhan vien theo phong ban
			// drawPieChart
			response.setContentType("image/png");
			DefaultPieDataset dpd = new DefaultPieDataset();
			//
			List<Department> listD = new ArrayList<Department>();
			for (int i = 0; i < list.size(); i++) {
				Department dept = new Department();
				dept = list.get(i);
				String deptId = dept.getDepartmentId();
				int deptSize = employeeDAO.getEmployeesByDepartment(deptId).size();
				dept.setNumberOfMember(deptSize);
				listD.add(dept);
				//
				dpd.setValue(dept.getDepartmentName() + ": " + deptSize, deptSize);
			}

			JFreeChart chart = createChart(dpd, "Biểu đồ tỷ lệ lao động theo phòng ban");
			try {

				String rootPath = request.getSession().getServletContext().getRealPath("/");
				File dir = new File(rootPath + "charts/");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				File file = new File(dir + "/departmentChart.png");
				// System.err.println(dir);
				ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			model.addAttribute("departments", listD);
			model.addAttribute("formTitle", "Danh sách phòng ban");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listDepartment";
	}

	private JFreeChart createChart(PieDataset pdSet, String chartTitle) {

		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet, true, true, true);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		//plot.setIgnoreZeroValues(true);
		//plot.setCircular(true);
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

	@RequestMapping(value = "/department/addDepartment", method = RequestMethod.POST)
	public String addDepartment(Model model, @ModelAttribute("departmentForm") @Validated Department department,
			final RedirectAttributes redirectAttributes) {
		try {
			departmentDAO.insertDepartment(department);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Insert department successful");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/department/";
	}

	@RequestMapping(value = "/department/updateDepartment", method = RequestMethod.POST)
	public String updateDepartment(Model model, @ModelAttribute("departmentForm") @Validated Department department,
			final RedirectAttributes redirectAttributes) {
		try {
			departmentDAO.updateDepartment(department);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Update department successful");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/department/";
	}

	private String departmentForm(Model model, Department department) {
		model.addAttribute("departmentForm", department);
		String actionform = "";
		if (department.getDepartmentId() != null) {
			model.addAttribute("formTitle", "Sửa thông tin phòng ban");
			actionform = "formDepartment";
		} else {
			model.addAttribute("formTitle", "Thêm mới phòng ban");
			actionform = "insertDepartment";
		}
		System.err.println(actionform);
		return actionform;
	}

	@RequestMapping("/department/insertDepartment")
	public String addDepartment(Model model) {
		Department department = new Department();
		return this.departmentForm(model, department);
	}

	@RequestMapping("/department/editDepartment")
	public String editDepartment(Model model, @RequestParam("departmentId") String departmentId) {
		Department department = null;
		if (departmentId != null) {
			department = this.departmentDAO.getDepartment(departmentId);
		}
		if (department == null) {
			return "redirect:/department/";
		}

		return this.departmentForm(model, department);
	}

	@RequestMapping(value = { "/department/listEmployeeOfDepartment" }, method = RequestMethod.GET)
	public String listEmployeeOfDepartment(Model model, @RequestParam("departmentId") String departmentId) {
		try {			
			Date date = new Date();// your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);		
			int currentQuarter = cal.get(Calendar.MONTH)/3 + 1;
			model.addAttribute("quarter", currentQuarter);
			
			List<EmployeeInfo> list = employeeDAO.getEmployeesByDepartment(departmentId);
			model.addAttribute("employees", list);
			EmployeeForm employeeForm = new EmployeeForm();
			model.addAttribute("employeeForm", employeeForm);
			model.addAttribute("formTitle", "Danh sách nhân viên phòng " + departmentId);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listEmployee"; // "listEmployeeOfDepartment";
	}
}
