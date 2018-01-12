package com.idi.hr.controller;

import java.io.File;
import java.io.IOException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.hr.bean.Department;
import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.dao.DepartmentDAO;
import com.idi.hr.dao.EmployeeDAO;
import com.idi.hr.dao.LeaveDAO;
import com.idi.hr.dao.TimekeepingDAO;
import com.idi.hr.common.Utils;

@Controller
public class KPIController {
	private static final Logger log = Logger.getLogger(TimekeepingController.class.getName());

	@Autowired
	private TimekeepingDAO timekeepingDAO;

	@Autowired
	private LeaveDAO leaveDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@RequestMapping(value = { "/KPI/" }, method = RequestMethod.GET)
	public String prepareReport(Model model, LeaveReport leaveReport) {
		try {
			model.addAttribute("reportForm", leaveReport);

			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees("all");
			model.addAttribute("employeeMap", employeeMap);

			// get list leave type
			Map<String, String> leaveTypeMap = this.leaveTypesForReport();
			model.addAttribute("leaveTypeMap", leaveTypeMap);

			model.addAttribute("formTitle", "Tùy chọn dữ liệu cần cho báo cáo");
			model.addAttribute("chart", "/charts/workStatusChart.png");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "reportByKPI";
	}

	@RequestMapping(value = "/KPI/generateReport", method = RequestMethod.POST)
	public String generateReport(Model model, HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("generateReport") @Validated LeaveReport leaveReport,
			final RedirectAttributes redirectAttributes) {
		try {
			// ArrayList<LeaveReport> list = new ArrayList<>();
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");			
			
			Date date = new Date();// your date
			//String currentDate = dateFormat.format(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);			
			
			String year = leaveReport.getYearReport();
			System.err.println("year: " + year);
			String month = leaveReport.getMonthReport();
			int employeeId = leaveReport.getEmployeeId();
			String typeReport = leaveReport.getLeaveTypeReport();
			String department = leaveReport.getDepartment();
			// get list department
			Map<String, String> departmentMap = this.dataForDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			Map<String, String> employeeMap = this.employees(department);

			model.addAttribute("employeeMap", employeeMap);

			// get list leave type
			Map<String, String> leaveTypeMap = this.leaveTypesForReport();
			model.addAttribute("leaveTypeMap", leaveTypeMap);
			model.addAttribute("reportForm", leaveReport);
			log.info("Report for " + typeReport);

			// Ti le Lao dong theo phong ban
			if (typeReport.equalsIgnoreCase("TLLDTP")) {
				List<Department> listDepartment = departmentDAO.getDepartments();
				// tinh so luong nhan vien theo phong ban
				// drawPieChart
				response.setContentType("image/png");
				model.addAttribute("formTitle", "Biểu đồ tỷ lệ lao động theo phòng ban");
				DefaultPieDataset dpd = new DefaultPieDataset();
				//
				List<Department> listD = new ArrayList<Department>();
				for (int i = 0; i < listDepartment.size(); i++) {
					Department dept = new Department();
					dept = listDepartment.get(i);
					String deptId = dept.getDepartmentId();
					int deptSize = employeeDAO.getEmployeesByDepartment(deptId).size();
					dept.setNumberOfMember(deptSize);
					listD.add(dept);
					//
					dpd.setValue(dept.getDepartmentName() + ":" + deptSize, deptSize);
				}

				JFreeChart chart = createPieChart(dpd, "Biểu đồ tỷ lệ lao động theo phòng ban");
				try {

					String rootPath = request.getSession().getServletContext().getRealPath("/");
					File dir = new File(rootPath + "charts/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(dir + "/departmentChart.png");
					model.addAttribute("chart", "/charts/departmentChart.png");
					ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return "reportByKPI";
			} else if (typeReport.equalsIgnoreCase("DM")) {
				model.addAttribute("formTitle", "Biểu đồ thông kê số lần đi muộn");
				Map<String, Integer> values = new LinkedHashMap<String, Integer>();
				System.err.println("ve bieu do di muon " + month);
				if (month.isEmpty()) {
					System.err.println("ve bieu do di muon khong chon thang " + cal.get(Calendar.YEAR) +"|" + Integer.parseInt(year));
					int maxMonth = 12;
					if (Integer.parseInt(year) <= cal.get(Calendar.YEAR)) {
						if(Integer.parseInt(year) == cal.get(Calendar.YEAR)) //chi check voi nm hien tai
							maxMonth = cal.get(Calendar.MONTH) + 1;
						//voi nhung nam truoc thi = 12
						System.err.println("ve bieu do di muon, thang hien tail " + maxMonth);
						for (int i = 1; i <= maxMonth; i++) {							
							values.put("Tháng " + i, Integer.parseInt(timekeepingDAO.getTimekeepingReport(year, String.valueOf(i), employeeId, typeReport)));
							System.err.println("ve bieu do di muon " + month);
						}
					}						
				} else {
					System.err.println("ve bieu do di muon thang" + month);
					values.put("Tháng " + month, Integer.parseInt(timekeepingDAO.getTimekeepingReport(year, month, employeeId, typeReport)));
				}

				// Create Dataset
				CategoryDataset dataset = createDatasetI(values);

				// Create chart
				JFreeChart chart = ChartFactory.createBarChart("Biểu đồ thông kê số lần đi muộn ", // Chart Title
						"Năm " + year, // Category axis
						"Số lần đi muộn", // Value axis
						dataset, PlotOrientation.VERTICAL, true, true, false);
				
				try {
					String rootPath = request.getSession().getServletContext().getRealPath("/");
					File dir = new File(rootPath + "charts/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(dir + "/comeLateChart.png");
					model.addAttribute("chart", "/charts/comeLateChart.png");
					ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return "reportByKPI";
			} else if (typeReport.equalsIgnoreCase("VS")) {
				model.addAttribute("formTitle", "Biểu đồ thông kê số lần về sớm ");
				Map<String, Integer> values = new LinkedHashMap<String, Integer>();
				System.err.println("ve bieu do ve som " + month);
				if (month.isEmpty()) {
					System.err.println("ve bieu do ve som khong chon thang cua nam hien tai" + cal.get(Calendar.YEAR) +"|" + Integer.parseInt(year));
					int maxMonth = 12;
					if (Integer.parseInt(year) <= cal.get(Calendar.YEAR)) {
						if(Integer.parseInt(year) == cal.get(Calendar.YEAR)) //chi check voi nm hien tai
							maxMonth = cal.get(Calendar.MONTH) + 1;
						//voi nhung nam truoc thi = 12
						System.err.println("ve bieu do ve som, thang hien tail " + maxMonth);
						for (int i = 1; i <= maxMonth; i++) {							
							values.put("Tháng " + i, Integer.parseInt(timekeepingDAO.getTimekeepingReport(year, String.valueOf(i), employeeId, typeReport)));
							System.err.println("ve bieu do ve som " + month);
						}
					}else {
						model.addAttribute("message", "Vui lòng chọn thời gian báo cáo thích hợp ...");
					}
				} else {
					System.err.println("ve bieu do ve som thang" + month);
					values.put("Tháng " + month, Integer.parseInt(timekeepingDAO.getTimekeepingReport(year, month, employeeId, typeReport)));
				}

				// Create Dataset
				CategoryDataset dataset = createDatasetI(values);

				// Create chart
				JFreeChart chart = ChartFactory.createBarChart("Biểu đồ thông kê số lần về sớm ", // Chart Title
						"Năm " + year, // Category axis
						"Số lần về sớm", // Value axis
						dataset, PlotOrientation.VERTICAL, true, true, false);
				
				try {
					String rootPath = request.getSession().getServletContext().getRealPath("/");
					File dir = new File(rootPath + "charts/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(dir + "/leaveSoonChart.png");
					model.addAttribute("chart", "/charts/leaveSoonChart.png");
					ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return "reportByKPI";
			} else if(typeReport.equalsIgnoreCase("TTLD")) {
				Map<String, String> items = Utils.workStatusMap();
				Map<String, Integer> memberWorkStatus = new LinkedHashMap<String, Integer>();
				for (Map.Entry<String, String> entry : items.entrySet()) {
					// System.out.println("Item : " + entry.getKey() + " Count : " +
					// entry.getValue());
					memberWorkStatus.put(entry.getValue(),
							employeeDAO.countMemberByWorkStatus(entry.getKey(), department));
				}
				if (!leaveReport.getDepartment().equalsIgnoreCase("all"))
					model.addAttribute("formTitle", "Biểu đồ trạng thái lao động phòng " + department);
				else
					model.addAttribute("formTitle", "Biểu đồ trạng thái lao động");

				// drawPieChart
				response.setContentType("image/png");
				// Map<String, String> items = workStatusMap();
				DefaultPieDataset dpd = new DefaultPieDataset();
				for (Map.Entry<String, String> entry : items.entrySet()) {
					// System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
					dpd.setValue(entry.getValue() + ":"
							+ employeeDAO.countMemberByWorkStatus(entry.getKey(), department),
							employeeDAO.countMemberByWorkStatus(entry.getKey(), department));
				}
				JFreeChart chart = null;
				if (!leaveReport.getDepartment().equalsIgnoreCase("all"))
					chart = createPieChart(dpd, "Biểu đồ trạng thái LĐ phòng " + department);
				else
					chart = createPieChart(dpd, "Biểu đồ trạng thái LĐ");

				try {
					String rootPath = request.getSession().getServletContext().getRealPath("/");
					File dir = new File(rootPath + "charts/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(dir + "/workStatusChart.png");
					model.addAttribute("chart", "/charts/workStatusChart.png");
					ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return "reportByKPI";
			} else {
				String leaveTypeName = leaveDAO.getLeaveType(typeReport); 			
				
				if(typeReport.startsWith("LT"))
					model.addAttribute("formTitle", "Biểu đồ thông kê số giờ " + leaveTypeName);	
				else
					model.addAttribute("formTitle", "Biểu đồ thông kê số lần " + leaveTypeName);
				
				Map<String, Float> values = new LinkedHashMap<String, Float>();
				//System.err.println("ve bieu do "+ typeReport + month);
				if (month.isEmpty()) {
					System.err.println("ve bieu do ve som khong chon thang " + cal.get(Calendar.YEAR) +"|" + Integer.parseInt(year));
					int maxMonth = 12;
					if (Integer.parseInt(year) <= cal.get(Calendar.YEAR)) {
						if(Integer.parseInt(year) == cal.get(Calendar.YEAR)) //chi check voi nm hien tai
							maxMonth = cal.get(Calendar.MONTH) + 1;
						//voi nhung nam truoc thi = 12
						
						//System.err.println("ve bieu do ve som, thang hien tail " + maxMonth);
						for (int i = 1; i <= maxMonth; i++) {	
							if(typeReport.startsWith("LT") || typeReport.startsWith("KCC")) { //|| leaveDAO.getLeaveReport(year,  String.valueOf(i), employeeId, typeReport) == 0) {
								values.put("Tháng " + i, (float)leaveDAO.getLeaveReport(year,  String.valueOf(i), employeeId, typeReport));
							}else {
								if(leaveDAO.getLeaveReport(year, month, employeeId, typeReport) > 0) 
									values.put("Tháng " + i, (float)leaveDAO.getLeaveReport(year,  String.valueOf(i), employeeId, typeReport)/8);
								else
									values.put("Tháng " + i, (float)leaveDAO.getLeaveReport(year,  String.valueOf(i), employeeId, typeReport));
							}
							//System.err.println("ve bieu do "+ typeReport + month);
						}
					}						
				} else {
					//neu muon co the ve them thang nien truoc va thang nien sau trong th chi chon 1 thang cu the, neu thang <12, thang = 12 chi 1 thang nien truoc
					System.err.println("ve bieu do ve "+ typeReport + month);
					if(typeReport.startsWith("LT") || typeReport.startsWith("KCC") || leaveDAO.getLeaveReport(year, month, employeeId, typeReport) == 0) {
						if(Integer.parseInt(month) < 12 && Integer.parseInt(month) > 1) {
							values.put("Tháng " + (Integer.parseInt(month) - 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) - 1)), employeeId, typeReport));
							values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport));
							values.put("Tháng " +  (Integer.parseInt(month) + 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) + 1)), employeeId, typeReport));
						}else if(Integer.parseInt(month) == 1){
							values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport));
						}else {
							values.put("Tháng " + (Integer.parseInt(month) - 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) - 1)), employeeId, typeReport));
							values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport));							
						}
					}else {
						if(leaveDAO.getLeaveReport(year, month, employeeId, typeReport) > 0) {
							if(Integer.parseInt(month) < 12 && Integer.parseInt(month) > 1) {
								values.put("Tháng " + (Integer.parseInt(month) - 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) - 1)), employeeId, typeReport)/8);
								values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport)/8);
								values.put("Tháng " +  (Integer.parseInt(month) + 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) + 1)), employeeId, typeReport)/8);
							}else if(Integer.parseInt(month) == 1){
								values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport));
							}else {
								values.put("Tháng " + (Integer.parseInt(month) - 1), (float)leaveDAO.getLeaveReport(year, String.valueOf((Integer.parseInt(month) - 1)), employeeId, typeReport)/8);
								values.put("Tháng " + month, (float)leaveDAO.getLeaveReport(year, month, employeeId, typeReport)/8);							
							}
						}
					}
				}

				// Create Dataset
				CategoryDataset dataset = createDataset(values);

				// Create chart
				JFreeChart chart = null;
				if(typeReport.startsWith("LT"))
					chart = ChartFactory.createBarChart("Biểu đồ thông kê số giờ " + leaveTypeName, // Chart Title
					"Năm " + year, // Category axis
					"Số giờ " + leaveTypeName, // Value axis
					dataset, PlotOrientation.VERTICAL, true, true, false);	
				else
					chart = ChartFactory.createBarChart("Biểu đồ thông kê số lần " + leaveTypeName, // Chart Title
					"Năm " + year, // Category axis
					"Số lần " + leaveTypeName, // Value axis
					dataset, PlotOrientation.VERTICAL, true, true, false);
				
				try {
					String rootPath = request.getSession().getServletContext().getRealPath("/");
					File dir = new File(rootPath + "charts/");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(dir + "/" + typeReport + "Chart.png");
					model.addAttribute("chart", "/charts/" + typeReport + "Chart.png");
					ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return "reportByKPI";				
			}	
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "reportByKPI";
	}

	private Map<String, String> employees(String department) {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		try {
			List<EmployeeInfo> list = null;
			if (!department.equalsIgnoreCase("all"))
				list = employeeDAO.getEmployeesByDepartment(department);
			else
				list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id.toString(),
						"Mã NV " + id + ", " + employee.getFullName() + ", phòng " + employee.getDepartment());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}

	private Map<String, String> leaveTypesForReport() {
		Map<String, String> leaveTypeMap = new LinkedHashMap<String, String>();
		try {
			List<LeaveType> list = leaveDAO.getLeaveTypesForReport();
			LeaveType leaveType = new LeaveType();
			for (int i = 0; i < list.size(); i++) {
				leaveType = (LeaveType) list.get(i);
				String leaveId = leaveType.getLeaveId();
				if (!leaveId.endsWith("2"))
					leaveTypeMap.put(leaveId, leaveType.getLeaveName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return leaveTypeMap;
	}

	private Map<String, String> dataForDepartments() {
		Map<String, String> departmentMap = new LinkedHashMap<String, String>();
		try {
			List<Department> list = departmentDAO.getDepartments();
			Department department = new Department();
			for (int i = 0; i < list.size(); i++) {
				department = (Department) list.get(i);
				departmentMap.put(department.getDepartmentId(), department.getDepartmentName());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return departmentMap;
	}

	private JFreeChart createPieChart(PieDataset pdSet, String chartTitle) {
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}({2})", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}

	private CategoryDataset createDataset(Map<String, Float> values) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Float> entry : values.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			dataset.addValue(entry.getValue(), entry.getKey(), "" );
		}
		return dataset;
	}
	
	private CategoryDataset createDatasetI(Map<String, Integer> values) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Integer> entry : values.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			dataset.addValue(entry.getValue(), entry.getKey(), "");
		}
		return dataset;
	}
}
