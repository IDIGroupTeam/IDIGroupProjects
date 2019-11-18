package com.idi.task.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
//import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.idi.task.dao.DepartmentDAO;
import com.idi.task.dao.EmployeeDAO;
import com.idi.task.bean.Department;
import com.idi.task.bean.EmployeeInfo;
import com.idi.task.bean.Task;
import com.idi.task.bean.TaskComment;
import com.idi.task.bean.TaskSummay;
import com.idi.task.common.PropertiesManager;
import com.idi.task.common.Utils;
import com.idi.task.dao.TaskDAO;
import com.idi.task.form.ReportForm;
import com.idi.task.form.SendReportForm;
import com.idi.task.form.TaskForm;
import com.idi.task.login.bean.UserLogin;
import com.idi.task.login.dao.UserRoleDAO;
import com.idi.task.validator.TaskValidator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class TaskController {
	private static final Logger log = Logger.getLogger(TaskController.class.getName());

	@Autowired
	private TaskDAO taskDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	TaskValidator taskValidator;

	PropertiesManager properties = new PropertiesManager("task.properties");

	public static File fontFile = new File("/home/idi/properties/vuTimes.ttf");
	// public static File fontBFile = new
	// File("/home/idi/properties/vni.common.VTIMESB.ttf");

	@RequestMapping(value = { "/" })
	public String listTasks(Model model, @ModelAttribute("taskForm") TaskForm form) throws Exception {
		try {
			List<Task> list = null;

			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id who is owner of the tasks
			model.addAttribute("employeesList", employeesOwner());

			model.addAttribute("statusList", taskDAO.getListStatus());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}

			boolean search = false;
			if ((form.getSearchValue() != null && form.getSearchValue().length() > 0)
					|| (form.getArea() != null && form.getArea() != "") || form.getOwnedBy() > 0
					|| form.getStatus() != null && form.getStatus().length() > 0) {
				log.info("Searching for: '" + form.getSearchValue() + "', phòng: " + form.getArea()
						+ ", Người làm có id: " + form.getOwnedBy() + ", trạng thái công viêc: " + form.getStatus());
				search = true;
				list = taskDAO.getTasksBySearch(form.getSearchValue(), form.getArea(), form.getOwnedBy(),
						form.getStatus());
			} else
				list = taskDAO.getTasks();
			form.setTotalRecords(list.size());

			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<Task> listTaskForPage = new ArrayList<Task>();

			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
							task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
						listTaskForPage.add(task);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
							task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
						listTaskForPage.add(task);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					Task task = new Task();
					task = list.get(i);
					if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
						task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
					listTaskForPage.add(task);
				}
			}

			model.addAttribute("taskForm", form);
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có công việc nào được tạo");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message",
						"Không có công việc nào khớp với thông tin: Mã việc/Tên việc/Người được giao/Trạng thái công việc/Mã phòng/Kế hoạch cho tháng = '"
								+ form.getSearchValue() + "', người làm có id = " + form.getOwnedBy()
								+ ", phòng ban có mã = '" + form.getArea() + "', trạng thái công việc = '"
								+ form.getStatus() + "'.\n \n (Note: id = 0 tức là không chọn ai)");

			model.addAttribute("tasks", listTaskForPage);
			model.addAttribute("formTitle", "Danh sách tất cả công việc");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTask";
	}

	@RequestMapping(value = { "/listTasksOwner" })
	public String listTasksOwner(Model model, @ModelAttribute("taskForm") TaskForm form) throws Exception {
		try {
			// System.err.println("owner .....");
			// inject from Login account
			int userId = -1;
			String username = new LoginController().getPrincipal();
			// System.err.println("user name: " + username);
			if (username != null && username.length() > 0 && !username.equalsIgnoreCase("anonymousUser")) {
				UserLogin userLogin = userRoleDAO.getAUserLoginFull(username);
				userId = userLogin.getUserID();
			} else {
				return "/login";
			}
			// System.err.println("user userId: " + userId);
			List<Task> list = null;

			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id who is owner of the tasks
			model.addAttribute("employeesList", employeesOwner());

			model.addAttribute("statusList", taskDAO.getListStatus());

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(25);
			}

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}

			boolean search = false;
			if ((form.getSearchValue() != null && form.getSearchValue().length() > 0)
					|| form.getStatus() != null && form.getStatus().length() > 0) {
				log.info("Searching for: '" + form.getSearchValue() + "', trạng thái công viêc: " + form.getStatus());
				search = true;
				list = taskDAO.getTasksBySearch(form.getSearchValue(), null, userId, form.getStatus());
			} else
				list = taskDAO.getTasksOwner(userId);
			form.setTotalRecords(list.size());

			int totalPages = form.getTotalRecords() % form.getNumberRecordsOfPage() > 0
					? form.getTotalRecords() / form.getNumberRecordsOfPage() + 1
					: form.getTotalRecords() / form.getNumberRecordsOfPage();
			form.setTotalPages(totalPages);

			List<Task> listTaskForPage = new ArrayList<Task>();

			if (form.getPageIndex() < totalPages) {
				if (form.getPageIndex() == 1) {
					for (int i = 0; i < form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
							task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
						listTaskForPage.add(task);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
							task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
						listTaskForPage.add(task);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					Task task = new Task();
					task = list.get(i);
					if(task.getPlannedFor() != null && task.getPlannedFor().length() > 0)
						task.setPlanned(task.getPlannedFor().substring(5,7) + "-" + task.getPlannedFor().substring(0,4));
					listTaskForPage.add(task);
				}
			}

			model.addAttribute("taskForm", form);
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có công việc nào được giao cho bạn");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message",
						"Không có công việc nào khớp với thông tin: Mã việc/Tên việc/Người được giao/Trạng thái công việc/Mã phòng/Kế hoạch cho tháng = '"
								+ form.getSearchValue() + "', trạng thái công việc = '" + form.getStatus()
								+ "' được giao cho bạn");

			model.addAttribute("tasks", listTaskForPage);
			model.addAttribute("formTitle", "Danh sách công việc được giao cho bạn");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTaskOwner";
	}

	@RequestMapping(value = "/removeTaskRelated")
	public String removeTaskRelated(Model model, @RequestParam("taskId") int taskId,
			@RequestParam("taskIdRemove") int taskIdRemove) throws Exception {
		try {

			// Get danh sach cv lien quan
			Task task = new Task();
			task = taskDAO.getTask(taskId);
			String tasksRelated = task.getRelated();
			String tasksRelatedNew = "";
			if (tasksRelated != null && tasksRelated.length() > 0) {
				// System.err.println(tasksRelated);
				StringTokenizer st = new StringTokenizer(tasksRelated, ",");
				while (st.hasMoreElements()) {
					String temp = st.nextElement().toString();
					if (!(taskIdRemove == Integer.parseInt(temp))) {
						tasksRelatedNew = tasksRelatedNew + "," + temp;
						// System.err.println("element " + tasksRelatedNew);
					}
				}
				tasksRelatedNew = Utils.cutComma(tasksRelatedNew);
				// if(tasksRelatedNew.startsWith(","))
				// tasksRelatedNew= tasksRelatedNew.substring(1, tasksRelatedNew.length());
				taskDAO.updateRelated(tasksRelatedNew, taskId);
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}

		model.addAttribute("active3", "active");
		model.addAttribute("tabActive1", "tab-pane");
		model.addAttribute("tabActive2", "tab-pane");
		model.addAttribute("tabActive3", "tab-pane active");

		return "redirect:/editTask?tab=3&taskId=" + taskId;
	}

	@RequestMapping(value = "/updateSub")
	public String updateSub(Model model, @ModelAttribute("taskForm") TaskForm taskForm) throws Exception {
		try {
			// get danh sach subscriber hien tai
			String sub = taskDAO.getTaskSubscriber(taskForm.getTaskId());
			String subRemove = taskForm.getSubscriber();
			String subAddNew = taskForm.getForSubscriber();
			// System.out.println("sub new current: " + subRemove);
			// System.out.println("sub new select: " + subAddNew);

			String subUpdated = "";
			if (sub != null && sub.length() > 0) {
				// Co thay doi (remove bot nguoi lien quan)
				if (subRemove != null) {
					// System.err.println("vi tri remove" + sub.indexOf(subRemove));
					// (sub.indexOf(subRemove), sub.indexOf(subRemove) + subRemove.length());
					// System.out.println(subUpdated);
					if (subAddNew != null) {
						subUpdated = sub.replace(subRemove, subAddNew);
						System.out.println("Co remove cu va add them moi " + subUpdated);
					} else {
						subUpdated = sub.replace(subRemove, "0");
						System.out.println("Chi remove bot di va khong add them " + subUpdated);
					}
				} else {
					if (subAddNew != null) {
						System.out.println("Chi add them, truoc co roi " + subUpdated);
						log.info("Thông tin nguoi liên quan không có gì cập nhật ... subUpdated = subAddNew");
						subUpdated = sub + "," + subAddNew;
					} else {
						log.info("Thông tin nguoi liên quan không có gì cập nhật ...");
					}
					// subUpdated = sub + "," + subAddNew;
				}
			} else {
				if (subAddNew != null)
					System.out.println("Chi add them, truoc chua co " + subUpdated);
				subUpdated = subAddNew;
			}
			if (subUpdated != null && subUpdated.length() > 0) {
				subUpdated = Utils.cutComma(subUpdated);
				taskDAO.updateSubscriber(subUpdated, taskForm.getTaskId());
			}

			Task task = taskDAO.getTask(taskForm.getTaskId());

			// inject from Login account
			String username = new LoginController().getPrincipal();
			UserLogin userLogin = new UserLogin();
			log.info("Using usename =" + username + " in update subcriber");
			if (username != null && username.length() > 0) {
				userLogin = userRoleDAO.getAUserLoginFull(username);
			}

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			// allEmployeesMap();
			String htmlMsg = "Dear you, <br/>\n<br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
					+ "<b>Người làm: " + allEmployeesMap().get(task.getOwnedBy()) + " </b><br/>\n"
					+ "<b>Người thêm người liên quan: " + allEmployeesMap().get(userLogin.getUserID()) + " </b><br/>\n"
					+ "Công việc thuộc phòng: " + task.getArea() + " <br/>\n " + "Trạng thái: Tạo mới <br/>\n "
					+ "Kế hoạch cho tháng: " + task.getPlannedFor() + " <br/>\n " + "Độ ưu tiên: " + task.getPriority()
					+ "<br/> <br/> \n" + "Mô tả nội dung công việc: " + task.getDescription()
					+ "<br/> \n <br/> \n Trân trọng, <br/> \n"
					+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n";
			mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
			List<String> mailList = taskDAO.getEmail(subUpdated);

			for (int i = 0; i < mailList.size(); i++) {
				String sendTo = mailList.get(i);
				// System.err.println("chuan bi send mail");
				if (sendTo != null && sendTo.length() > 0) {
					// System.err.println("send mail cho " + sendTo);
					helper.setTo(sendTo);
					helper.setSubject("[Thêm người liên quan cho công việc] - " + task.getTaskName());
					helper.setFrom("IDITaskNotReply");
					mailSender.send(mimeMessage);
					// System.err.println("sent");
				}
			}

			/*
			 * model.addAttribute("sub", sub); if(sub !=null && sub.length() > 0)
			 * model.addAttribute("subscriberList", employeesSub(sub)); else
			 * model.addAttribute("subscriberList", null); //get toan bo sach sach nguoi co
			 * the lua chon model.addAttribute("employeesList", employeesForSub(sub));
			 */

			model.addAttribute("formTitle",
					"Quản lý danh sách người liên quan đến công việc mã " + taskForm.getTaskId());
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}

		return "redirect:/editTask?tab=2&taskId=" + taskForm.getTaskId();
	}

	/*
	 * @RequestMapping(value = "/searchTask") public String searchTask(Model
	 * model, @RequestParam("searchValue") String searchValue,
	 * 
	 * @ModelAttribute("taskForm") TaskForm taskForm) { try { List<Task> list =
	 * null; // TaskForm taskForm = new TaskForm(); model.addAttribute("taskForm",
	 * taskForm); if (searchValue != null && searchValue.length() > 0) { list =
	 * taskDAO.getTasksBySearch(searchValue); } else { list = taskDAO.getTasks(); }
	 * if (list.size() < 1) model.addAttribute("message",
	 * "Không có công việc nào khớp với thông tin: '" + searchValue + "'");
	 * model.addAttribute("tasks", list); model.addAttribute("formTitle",
	 * "Danh sách công việc được tìm"); } catch (Exception e) { log.error(e, e);
	 * e.printStackTrace(); } return "listTask"; }
	 */

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == Task.class) {
			dataBinder.setValidator(taskValidator);
		}
	}

	@RequestMapping(value = "/insertNewTask", method = RequestMethod.POST)
	public String insertNewTask(Model model, @ModelAttribute("taskForm") @Validated Task task, BindingResult result,
			final RedirectAttributes redirectAttributes) throws Exception {
		try {
			// System.err.println("insert new task");
			if (task.getDueDate() != null && task.getDueDate().length() > 10) {
				if (result.hasErrors()) {
					// System.err.println("co loi validate");
					return this.addNewTask(model, task);
				}
			}
			if (taskDAO.taskIsExits(task.getTaskName())) {
				log.info("Da ton tai task name nay ");
				if (result.hasErrors()) {
					// System.err.println("co loi validate");
					return this.addNewTask(model, task);
				}
			} else {
				// inject from Login account
				String username = new LoginController().getPrincipal();
				log.info("Using usename =" + username + " in insert new task");
				if (username != null && username.length() > 0) {
					UserLogin userLogin = userRoleDAO.getAUserLoginFull(username);
					task.setCreatedBy(userLogin.getUserID());
				}

				Timestamp ts = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				// System.out.println("Month: "+ task.getMonth() +", Year: " + task.getYear());
				if (Integer.parseInt(task.getMonth()) < 10 && Integer.parseInt(task.getMonth()) > 0)
					task.setPlannedFor(task.getYear() + "-0" + task.getMonth());
				else if (Integer.parseInt(task.getMonth()) > 9)
					task.setPlannedFor(task.getYear() + "-" + task.getMonth());
				task.setCreationDate(ts);
				task.setUpdateTS(ts);
				if(task.getDueDate() != null && task.getDueDate().length() > 0 && task.getDueDate().contains("/"))
					task.setDueDate(Utils.convertDateToStore(task.getDueDate()));
				taskDAO.insertTask(task);

				// Add message to flash scope
				redirectAttributes.addFlashAttribute("message", "Thêm thông tin công việc thành công!");
				String owner = "Chưa giao cho ai";
				if (task.getOwnedBy() > 0)
					owner = allEmployeesMap().get(task.getOwnedBy());
				String createdName = "";
				if (task.getCreatedBy() > 0)
					createdName = allEmployeesMap().get(task.getCreatedBy());
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				String htmlMsg = "Dear you, <br/>\n<br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
						+ "<b>Người làm: " + owner + " </b><br/>\n" + "Công việc thuộc phòng: " + task.getArea()
						+ " <br/>\n " + "Trạng thái: Tạo mới <br/>\n " + "Kế hoạch cho tháng: " + task.getPlannedFor()
						+ " <br/>\n " + "Độ ưu tiên: " + task.getPriority() + "<br/> <br/> \n" + "<b>Người tạo "
						+ createdName + " </b> <e-mail> lúc " + ts + " <br/>\n" + "Mô tả nội dung công việc: "
						+ task.getDescription() + "<br/> \n <br/> \n Trân trọng, <br/> \n"
						+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n";
				mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
				List<String> mailList = taskDAO
						.getEmail(task.getOwnedBy() + "," + task.getVerifyBy() + "," + task.getSecondOwned());

				for (int i = 0; i < mailList.size(); i++) {
					String sendTo = mailList.get(i);
					// System.err.println("chuan bi send mail");
					if (sendTo != null && sendTo.length() > 0) {
						// System.err.println("send mail cho " + sendTo);
						helper.setTo(sendTo);
						helper.setSubject("[Tạo mới công việc] - " + task.getTaskName());
						helper.setFrom("IDITaskNotReply");
						mailSender.send(mimeMessage);
						// System.err.println("sent");
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(Model model, @ModelAttribute("taskForm") @Validated TaskForm taskForm,
			BindingResult result, final RedirectAttributes redirectAttributes) throws Exception {
		try {
			if (taskForm.getDueDate() != null && taskForm.getDueDate().length() > 10) {
				model.addAttribute("message", "Vui lòng nhập ngày phải xong đúng định dạng, ví dụ như 06/24/2019");
				//System.err.println("sai dinh dang ngay fai xong");
			} else {
				// Xu ly cho task comment
				TaskComment taskComment = new TaskComment();
				int currentMaxCommentIndex = 0;
				UserLogin userLogin = new UserLogin();
				if (taskForm.getContent() != null && taskForm.getContent().length() > 0) {
					currentMaxCommentIndex = taskDAO.getMaxCommentIndex(taskForm.getTaskId());
					currentMaxCommentIndex = currentMaxCommentIndex + 1;
					taskComment.setCommentIndex(currentMaxCommentIndex);
					taskComment.setTaskId(taskForm.getTaskId());
					taskComment.setCommentedBy(taskForm.getCommentedBy());
					taskComment.setContent(taskForm.getContent());

					// inject from Login account
					String username = new LoginController().getPrincipal();
					log.info("Using usename = " + username + " in insert new task");

					if (username != null && username.length() > 0) {
						userLogin = userRoleDAO.getAUserLoginFull(username);
						taskComment.setCommentedBy(userLogin.getUserID());
					}

					taskDAO.insertTaskComment(taskComment);
				}

				// Xu ly cho task
				Task task = new Task();
				// get info from taskForm then put to task bean
				task.setTaskId(taskForm.getTaskId());
				task.setTaskName(taskForm.getTaskName());
				task.setCreatedBy(taskForm.getCreatedBy());
				task.setOwnedBy(taskForm.getOwnedBy());
				task.setSecondOwned(taskForm.getSecondOwned());
				task.setVerifyBy(taskForm.getVerifyBy());
				task.setUpdateId(taskForm.getUpdateId()); // auto not edit show only
				task.setUpdateTS(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				task.setResolvedBy(taskForm.getResolvedBy()); // auto not edit show only when completed
				task.setCreationDate(taskForm.getCreationDate());
				task.setDueDate(taskForm.getDueDate());
				task.setResolutionDate(taskForm.getResolutionDate()); // auto not edit show only when completed
				task.setType(taskForm.getType());
				task.setArea(taskForm.getArea()); // viec cua phong kt , cntt, ns, ...
				task.setPriority(taskForm.getPriority());
				task.setStatus(taskForm.getStatus());
				if (taskForm.getPlannedFor() != null && taskForm.getPlannedFor().length() > 0)
					task.setPlannedFor(taskForm.getPlannedFor());
				else {
					if (Integer.parseInt(taskForm.getMonth()) < 10 && Integer.parseInt(taskForm.getMonth()) > 0)
						task.setPlannedFor(taskForm.getYear() + "-0" + taskForm.getMonth());
					else if (Integer.parseInt(taskForm.getMonth()) > 9)
						task.setPlannedFor(taskForm.getYear() + "-" + taskForm.getMonth());
				}
				task.setTimeSpent(taskForm.getTimeSpent());
				task.setTimeSpentType(taskForm.getTimeSpentType());
				task.setEstimate(taskForm.getEstimate());
				task.setEstimateTimeType(taskForm.getEstimateTimeType());
				task.setDescription(taskForm.getDescription());
				task.setReviewComment(taskForm.getReviewComment());

				Task currentTask = new Task();
				currentTask = taskDAO.getTask(taskForm.getTaskId());

				if(task.getDueDate() != null && task.getDueDate().length() > 0 && task.getDueDate().contains("/"))
					task.setDueDate(Utils.convertDateToStore(task.getDueDate()));
				
				taskDAO.updateTask(task);

				// Gui mail notification
				// Lấy ds email can gui
				String owner = "Chưa giao cho ai";
				if (taskForm.getOwnedBy() > 0)
					owner = allEmployeesMap().get(taskForm.getOwnedBy());

				String updatedBy = "";
				if (userLogin.getUserID() > 0)
					updatedBy = allEmployeesMap().get(userLogin.getUserID());

				List<String> mailList = taskDAO.getMailList(taskForm.getTaskId());
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				if (taskForm.getDescription().equalsIgnoreCase(currentTask.getDescription())) {
					// System.err.println("Ko thay doi description");
					// System.err.println(
					// "Ko thay doi description:" + taskForm.getDescription() + "|" +
					// currentTask.getDescription());
					String htmlMsg = "";
					if (taskForm.getReviewComment() != null && taskForm.getReviewComment().length() > 0) {
						if(taskForm.getContent() != null && taskForm.getContent().length() > 0) 
							htmlMsg = "Dear you, <br/>\n <br/>\n "
									+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + "</b><br/>\n "
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n "
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n "
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b> <e-mail> lúc " + task.getUpdateTS()	+ "<br/>\n<tr><td>"
									+ "Nhận xét/đánh giá của người giám sát: </td><td><textarea disabled=\"true\">" + taskForm.getReviewComment() + "</textarea></td></tr><br/><tr><td>"
									+ "Nội dung thảo luận:<textarea disabled=\"true\"> " + taskForm.getContent() + "</textarea><br/>\n<br/>"
									+ "Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ " </td></tr><br/> ";
						else
							htmlMsg = "Dear you, <br/>\n <br/>\n Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + "</b><br/>\n "
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n "
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b> <e-mail> lúc " + task.getUpdateTS()	+ " <br/>\n "
									+ "<br/>\n<tr><td>Nhận xét/đánh giá của người giám sát: </td><td><textarea disabled=\"true\">"	+ taskForm.getReviewComment()+ "</textarea></td></tr><tr><td><br/><br/>"
									+ "Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ " </td></tr><br/>";
					} else {
						if(taskForm.getContent() != null && taskForm.getContent().length() > 0) 
							htmlMsg = "Dear you, <br/>\n <br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b><e-mail> lúc " + task.getUpdateTS()	+ " <br/>\n <tr><td>"
									+ "Nội dung thảo luận:<textarea disabled=\"true\"> " + taskForm.getContent() + "</textarea><br/>\n<br/>"
									+ "Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/> \n";
						else
							htmlMsg = "Dear you, <br/>\n <br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b><e-mail> lúc " + task.getUpdateTS()
									+ "<br/>\n <tr><td><br/>Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/> ";
					}

					mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
					// mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"),
					// "UTF-8"),"text/html; charset=UTF-8");
					// mimeMessage.setContent(htmlMsg, "text/html");
				} else {
					// System.err.println("Thay doi description");
					String htmlMsg = "";
					if (taskForm.getReviewComment() != null && taskForm.getReviewComment().length() > 0) {
						if(taskForm.getContent() != null && taskForm.getContent().length() > 0) 
							htmlMsg = "Dear you, <br/>\n <br/>\n Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n"
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n"
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b><e-mail> lúc " + task.getUpdateTS()	+ "<br/>\n <tr>" 
									+ "<td>Mô tả công việc: </td><td><textarea disabled=\"true\"> "	+ taskForm.getDescription() + " </textarea></td></tr><br/> \n "
									+ "<tr><td>Nhận xét/đánh giá của người giám sát: " + taskForm.getReviewComment()
									+ "</td></tr><br/> \n <tr><td>"
									+ "Nội dung thảo luận: </td> <td><textarea disabled=\"true\">"	+ taskForm.getContent() + "</textarea><br/>\n<br/>\n Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/>";
						else
							htmlMsg = "Dear you, <br/>\n <br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
									+ "<b>Người cập nhật: " + updatedBy + "</b><e-mail> lúc " + task.getUpdateTS()	+ " <br/> \n <tr>" 
									+ "<td>Mô tả công việc: </td><td><textarea disabled=\"true\"> "	+ taskForm.getDescription() + " </textarea></td></tr><br/> \n "
									+ "<tr><td>Nhận xét/đánh giá của người giám sát: " + taskForm.getReviewComment()
									+ "</td></tr><br/> \n <tr><td><br/>\n Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/>";
					} else {
						if(taskForm.getContent() != null && taskForm.getContent().length() > 0) 
							htmlMsg = "Dear you, <br/>\n <br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n "
									+ "<b>Người cập nhật: " + updatedBy + "</b> <e-mail> lúc " + task.getUpdateTS()
									+ "<br/>\n <tr><td>Mô tả công việc: </td><td><textarea disabled=\"true\">"	+ taskForm.getDescription() + "</textarea></td></tr><br/>\n "
									+ "<tr><td>Nội dung thảo luận: <textarea disabled=\"true\">" + taskForm.getContent() + "</textarea><br/>\n<br/>\n "
									+ "Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/>";
						else
							htmlMsg = "Dear you, <br/>\n <br/>\n" + "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
									+ "<b>Người làm: " + owner + " </b><br/>\n" 
									+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
									+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "
									+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n " 
									+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n " 
									+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor()	+ " <br/>\n " 
									+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n "
									+ "<b>Người cập nhật: " + updatedBy + "</b> <e-mail> lúc " + task.getUpdateTS()
									+ "<br/>\n <tr><td>Mô tả công việc: </td><td><textarea disabled=\"true\">"	+ taskForm.getDescription() + "</textarea></td></tr><br/>\n "
									+ "<tr><td><br/>\n Trân trọng, <br/> \n"
									+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n"
									+ "</td></tr><br/>";
					}

					mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
					// mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"),
					// "UTF-8"),"text/html; charset=UTF-8");
					// mimeMessage.setContent(htmlMsg, "text/html");
				}
				// System.err.println("chuan bi send mail " + mailList.size());
				for (int i = 0; i < mailList.size(); i++) {
					String sendTo = mailList.get(i);
					// System.err.println("chuan bi send mail");
					if (sendTo != null && sendTo.length() > 0) {
						// System.err.println("send mail cho " + sendTo);
						helper.setTo(sendTo);
						helper.setSubject("[Mã công việc: " + taskForm.getTaskId() + "] - " + taskForm.getTaskName());
						helper.setFrom("IDITaskNotReply");
						mailSender.send(mimeMessage);
						// System.err.println("sent");
					}
				}
				// Add message to flash scope
				model.addAttribute("message", "Cập nhật thông tin công việc thành công!");
			}
		} catch (Exception e) {
			log.error(e, e);
		}

		return "redirect:/editTask?tab=1&taskId=" + taskForm.getTaskId();
	}

	@RequestMapping("/addNewTask")
	public String addNewTask(Model model, Task task) throws Exception {
		model.addAttribute("formTitle", "Thêm mới thông tin công việc");

		// get list department
		Map<String, String> departmentMap = this.listDepartments();
		model.addAttribute("departmentMap", departmentMap);

		// get list employee id
		model.addAttribute("employeesList", employees("all"));

		model.addAttribute("taskForm", task);

		return "addNewTask";
	}

	@RequestMapping("/editTask")
	public String editTask(Model model, @RequestParam("taskId") int taskId, @RequestParam("tab") int tab,
			@RequestParam(required = false, value = "taskIds") String taskIds,
			@ModelAttribute("taskForm") @Validated TaskForm taskForm) throws Exception {
		// System.err.println("current tab "+ tab);
		Task task = new Task();
		// TaskForm taskForm = new TaskForm();

		if (taskId > 0) {
			// get danh sach subscriber hien tai
			String sub = taskDAO.getTaskSubscriber(taskId);
			model.addAttribute("sub", sub);
			if (sub != null && sub.length() > 0)
				model.addAttribute("subscriberList", employeesSub(sub));
			else
				model.addAttribute("subscriberList", null);

			// get toan bo sach sach nguoi co the lua chon
			model.addAttribute("employeesListS", employeesForSub(sub));

			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);

			task = taskDAO.getTask(taskId);

			// get info from task bean put to task form
			taskForm.setTaskId(task.getTaskId());
			taskForm.setTaskName(task.getTaskName());
			
			if(task.getDueDate() != null && task.getDueDate().length() > 0 && task.getDueDate().contains("-")) {
				taskForm.setDueDate(Utils.convertDateToDisplay(task.getDueDate()));
				task.setDueDate(Utils.convertDateToDisplay(task.getDueDate()));
			}
			
			int createBy = task.getCreatedBy();
			taskForm.setCreatedBy(createBy);
			Map<Integer, String> employeeMap = this.allEmployeesMap();
			if (createBy > 0) {
				taskForm.setCreatedByName(employeeMap.get(createBy));
			}
			// taskForm.setCommentedByName(commentedByName);
			taskForm.setOwnedBy(task.getOwnedBy());
			taskForm.setSecondOwned(task.getSecondOwned());
			taskForm.setVerifyBy(task.getVerifyBy());
			taskForm.setUpdateId(task.getUpdateId()); // auto not edit show only
			taskForm.setUpdateTS(task.getUpdateTS());
			taskForm.setResolvedBy(task.getResolvedBy()); // auto not edit show only when completed
			taskForm.setCreationDate(task.getCreationDate());
			taskForm.setDueDate(task.getDueDate());
			taskForm.setResolutionDate(task.getResolutionDate()); // auto not edit show only when completed
			taskForm.setType(task.getType());
			taskForm.setArea(task.getArea()); // viec cua phong kt , cntt, ns, ...
			taskForm.setPriority(task.getPriority());
			taskForm.setStatus(task.getStatus());
			taskForm.setPlannedFor(task.getPlannedFor());
			taskForm.setTimeSpent(task.getTimeSpent());
			taskForm.setTimeSpentType(task.getTimeSpentType());
			taskForm.setEstimate(task.getEstimate());
			taskForm.setEstimateTimeType(task.getEstimateTimeType());
			taskForm.setDescription(task.getDescription());
			taskForm.setReviewComment(task.getReviewComment());

			// Get danh sach cv lien quan
			List<Task> listTaskRelated;
			if (task.getRelated() != null && task.getRelated().length() > 0) {
				// System.err.println(task.getRelated());
				listTaskRelated = taskDAO.getTasksRelated(task.getRelated());
				model.addAttribute("tasksRelated", listTaskRelated);
			} else {
				model.addAttribute("tasksRelated", null);
			}

			if (taskIds != null) {
				// System.out.println("cv da chon: " + taskIds);
				String relatedUpdated = null;
				if (task.getRelated() != null && task.getRelated().length() > 0) {
					// da co cv lien quan + then
					relatedUpdated = task.getRelated() + "," + taskIds;
				} else {
					// hien tai chua co cv lien quan + them
					relatedUpdated = taskIds;
				}
				// Update task related
				// System.out.println("task related updated: " + relatedUpdated);
				taskDAO.updateRelated(relatedUpdated, taskId);

				listTaskRelated = taskDAO.getTasksRelated(task.getRelated());
				model.addAttribute("tasksRelated", listTaskRelated);

				// Reset data for task related
				taskForm.setSearchValue("");
				taskIds = null;
				return "redirect:/editTask?tab=3&taskId=" + taskForm.getTaskId();
			}

			// For comment
			model.addAttribute("listComment", taskDAO.getTaskComments(taskId));

			// get list employee id
			if (task.getArea() != null && task.getArea().length() > 0)
				model.addAttribute("employeesList", employees(task.getArea()));
			else
				model.addAttribute("employeesList", employees("all"));

			// System.err.println(task.getTaskName());

			model.addAttribute("formTitle", "Cập nhật thông tin công việc mã " + taskId);
			model.addAttribute("taskForm", taskForm);
			List<Task> list = null;
			if (taskForm.getSearchValue() != null && taskForm.getSearchValue().length() > 0) {
				log.info("Searching for: " + taskForm.getSearchValue());
				// search = true;
				if (task.getRelated() != null)
					list = taskDAO.getTasksBySearchForRelated(taskForm.getSearchValue(),
							task.getRelated() + "," + taskId);
				else
					list = taskDAO.getTasksBySearchForRelated(taskForm.getSearchValue(), String.valueOf(taskId));
			}
			model.addAttribute("taskForm", taskForm);
			if (list != null && list.size() < 1)// && search)
				model.addAttribute("message",
						"Không có công việc nào khớp với thông tin: '" + taskForm.getSearchValue() + "'");
			model.addAttribute("tasksFound", list);
			model.addAttribute("formTitle",
					"Thông tin chi tiết của công việc mã " + taskForm.getTaskId());

			if (tab == 1) {
				model.addAttribute("active1", "active");
				model.addAttribute("tabActive1", "tab-pane active");
				model.addAttribute("tabActive2", "tab-pane");
				model.addAttribute("tabActive3", "tab-pane");
			} else if (tab == 2) {
				model.addAttribute("active2", "active");
				model.addAttribute("tabActive1", "tab-pane");
				model.addAttribute("tabActive2", "tab-pane active");
				model.addAttribute("tabActive3", "tab-pane");
			} else if (tab == 3) {
				model.addAttribute("active3", "active");
				model.addAttribute("tabActive1", "tab-pane");
				model.addAttribute("tabActive2", "tab-pane");
				model.addAttribute("tabActive3", "tab-pane active");
			}
		} else {
			return "redirect:/";
		}

		return "updateTask";
	}

	@RequestMapping("/prepareReport")
	public String prepareReport(Model model, @RequestParam(required = false, value = "fDate") String fDate,
			@RequestParam(required = false, value = "tDate") String tDate, 
			@RequestParam(required = false, value = "dept") String dept) throws Exception {
		ReportForm taskReportForm = new ReportForm();
		//, @RequestParam(required = false, value = "eId") String eId
		//fDate=2019-05-26&tDate=2019-08-22&eName=&dept=CNTT&eId=69
		if(fDate != null && fDate.length() > 0)
			if(fDate.contains("/")) {
				taskReportForm.setFromDate(fDate);
			}else
				taskReportForm.setFromDate(Utils.convertDateToDisplay(fDate));
		else {
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String today = ft.format(dNow);
			// System.err.println(today);
			taskReportForm.setToDate(Utils.convertDateToDisplay(today));
			// model.addAttribute("today", today);
		}
		if(tDate != null && tDate.length() > 0)
			if(tDate.contains("/")) {
				taskReportForm.setToDate(tDate);
			}else
				taskReportForm.setToDate(Utils.convertDateToDisplay(tDate));
		if(dept != null && dept.length() > 0)
			taskReportForm.setDepartment(dept);
		/*
		 * if(eId != null && Integer.parseInt(eId) > 0)
		 * taskReportForm.setEmployeeId(Integer.parseInt(eId));
		 */
		
		model.addAttribute("formTitle", "Lựa chọn thông tin báo cáo công việc");

		// get list department
		Map<String, String> departmentMap = this.listDepartments();
		model.addAttribute("departmentMap", departmentMap);

		// get list employee id
		model.addAttribute("employeesList", employeesMap("all"));
		model.addAttribute("taskReportForm", taskReportForm);

		return "prepareReport";
	}

	@RequestMapping(value = "/generateTaskReport", params = "chat")
	public String generateTaskReportToChat(Model model,
			@ModelAttribute("taskReportForm") @Validated ReportForm taskReportForm, HttpServletResponse response,
			HttpServletRequest request) {

		TaskSummay taskSummay = new TaskSummay();
		try {
			//System.err.println(taskReportForm.getFromDate() + "|" + taskReportForm.getToDate());
			String fDate = Utils.convertDateToStore(taskReportForm.getFromDate());
			String tDate = Utils.convertDateToStore(taskReportForm.getToDate());
			
			/*
			 * int totalT = 0; int newT = 0; int inprogressT= 0; int stopedT = 0; int
			 * invalidT = 0; int reviewingT = 0; int completedT = 0;
			 */
			String title = "Biểu đồ thông kê khối lượng công việc";
			if (taskReportForm.getEmployeeId() > 0) {
				taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, taskReportForm.getEmployeeId());
				title = title + " của " + allEmployeesMap().get(taskReportForm.getEmployeeId());
				// listTaskSummary.add(taskSummay);
				/*
				 * }else if(taskReportForm.getDepartment() != null &&
				 * !taskReportForm.getDepartment().equalsIgnoreCase("all")) { List<EmployeeInfo
				 * > employees = employees(taskReportForm.getDepartment());
				 * 
				 * for(int i=0; i < employees.size(); i++) { EmployeeInfo e = new
				 * EmployeeInfo(); e = employees.get(i); TaskSummay taskSummayD =
				 * taskDAO.getSummaryTasksForReport(fDate, tDate, e.getEmployeeId()); totalT =
				 * totalT + taskSummayD.getTaskTotal(); newT = newT + taskSummayD.getTaskNew();
				 * inprogressT = inprogressT + taskSummayD.getTaskInprogess(); stopedT = stopedT
				 * + taskSummayD.getTaskStoped(); invalidT = invalidT +
				 * taskSummayD.getTaskinvalid(); reviewingT = reviewingT +
				 * taskSummayD.getTaskReviewing(); completedT = completedT +
				 * taskSummayD.getTaskCompleted();
				 * 
				 * } taskSummay.setTaskTotal(totalT); taskSummay.setTaskNew(newT);
				 * taskSummay.setTaskInprogess(inprogressT); taskSummay.setTaskStoped(stopedT);
				 * taskSummay.setTaskinvalid(invalidT); taskSummay.setTaskReviewing(reviewingT);
				 * taskSummay.setTaskCompleted(completedT);
				 */
			} else {
				if (taskReportForm.getDepartment().equalsIgnoreCase("all")) {
					title = title + " của tất cả phòng ban";
				} else {
					title = title + " của phòng " + taskReportForm.getDepartment();
				}

				taskSummay = taskDAO.getSummaryTasksForChat(fDate, tDate, taskReportForm.getDepartment());
				/*
				 * List<EmployeeInfo > employees = employees("all"); for(int i=0; i <
				 * employees.size(); i++) { EmployeeInfo e = new EmployeeInfo(); e =
				 * employees.get(i); TaskSummay taskSummayA =
				 * taskDAO.getSummaryTasksForReport(fDate, tDate, e.getEmployeeId()); totalT =
				 * totalT + taskSummayA.getTaskTotal(); newT = newT + taskSummayA.getTaskNew();
				 * inprogressT = inprogressT + taskSummayA.getTaskInprogess(); stopedT = stopedT
				 * + taskSummayA.getTaskStoped(); invalidT = invalidT +
				 * taskSummayA.getTaskinvalid(); reviewingT = reviewingT +
				 * taskSummayA.getTaskReviewing(); completedT = completedT +
				 * taskSummayA.getTaskCompleted();
				 * 
				 * } taskSummay.setTaskTotal(totalT); taskSummay.setTaskNew(newT);
				 * taskSummay.setTaskInprogess(inprogressT); taskSummay.setTaskStoped(stopedT);
				 * taskSummay.setTaskinvalid(invalidT); taskSummay.setTaskReviewing(reviewingT);
				 * taskSummay.setTaskCompleted(completedT);
				 */
			}

			Map<String, Integer> values = new LinkedHashMap<String, Integer>();
			log.info("Ve bieu thong ke khoi luong cong viec ");

			values.put("Mới: " + taskSummay.getTaskNew(), taskSummay.getTaskNew());
			values.put("Đang làm: " + taskSummay.getTaskInprogess(), taskSummay.getTaskInprogess());
			values.put("Đã hủy bỏ: " + taskSummay.getTaskinvalid(), taskSummay.getTaskinvalid());
			values.put("Tạm dừng: " + taskSummay.getTaskStoped(), taskSummay.getTaskStoped());
			values.put("Chờ đánh giá: " + taskSummay.getTaskReviewing(), taskSummay.getTaskReviewing());
			values.put("Đã xong: " + taskSummay.getTaskCompleted(), taskSummay.getTaskCompleted());

			// Create Dataset
			CategoryDataset dataset = createDatasetI(values);

			// lam ro/chi tiet tieu de bao cao cho ....

			// Create chart
			JFreeChart chart = ChartFactory.createBarChart(title, // Chart Title
					"Từ ngày " + Utils.convertDateToDisplay(fDate) + " đến ngày " + Utils.convertDateToDisplay(tDate), // Category axis
					"Số lượng công việc (Tổng số: " + taskSummay.getTaskTotal() + ")", // Value axis
					dataset, PlotOrientation.VERTICAL, true, true, false);

			try {

				String rootPath = request.getSession().getServletContext().getRealPath("/");
				File dir = new File(rootPath + "charts/");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				File file = new File(dir + "/tasksummaryChart.png");
				model.addAttribute("chart", "/charts/tasksummaryChart.png");
				ChartUtilities.saveChartAsJPEG(file, chart, 750, 400);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);
			// get list employee id
			model.addAttribute("employeesList", employeesMap("all"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("taskReportForm", taskReportForm);
		model.addAttribute("chat", 1);
		model.addAttribute("taskSummay", taskSummay);
		model.addAttribute("formTitle", "Biểu đồ thống kê khối lượng công việc");
		return "prepareReport";
	}

	@RequestMapping(value = "/generateTaskReport", params = "summary")
	public String generateTaskReportSummary(Model model,
			@ModelAttribute("taskReportForm") @Validated ReportForm taskReportForm) {
		List<TaskSummay> listTaskSummary = new ArrayList<TaskSummay>();
		try {
			String eName = allEmployeesMap().get(taskReportForm.getEmployeeId());
			taskReportForm.setEmployeeName(eName);
			System.err.println(taskReportForm.getFromDate() + "|" + taskReportForm.getToDate());
			String fDate = Utils.convertDateToStore(taskReportForm.getFromDate());
			String tDate = Utils.convertDateToStore(taskReportForm.getToDate());
			
			if(taskReportForm.getFromDate() != null && taskReportForm.getFromDate().length() > 0 && taskReportForm.getFromDate().contains("/"))
				taskReportForm.setFromDate(Utils.convertDateToStore(taskReportForm.getFromDate()));
			
			if(taskReportForm.getToDate() != null && taskReportForm.getToDate().length() > 0 && taskReportForm.getToDate().contains("/"))
				taskReportForm.setToDate(Utils.convertDateToStore(taskReportForm.getToDate()));
			
			if (taskReportForm.getEmployeeId() > 0) {
				TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, taskReportForm.getEmployeeId());
				
				// tinh tong thoi gian da lam va thoi gian estimate
				List<Task> list = null;
				list = taskDAO.getTasksForReport(taskReportForm, null);			
				float timeEstimateTotal = 0;
				float timeSpentTotal = 0;
				for (int i = 0; i < list.size(); i++) {
					Task taskDTO = new Task();
					taskDTO = list.get(i);
					if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
						if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
							timeSpentTotal = timeSpentTotal
									+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
							timeSpentTotal = timeSpentTotal
									+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
						else
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
					}
					if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
						if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
						else
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
					}
				}
				BigDecimal s = new BigDecimal(timeSpentTotal);
				BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

				BigDecimal e = new BigDecimal(timeEstimateTotal);
				BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
				
				//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
				//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
				taskSummay.setTotalEstimate(estimateTT.toString());
				taskSummay.setTotalSpent(spentTT.toString());
				listTaskSummary.add(taskSummay);
			} else if (taskReportForm.getDepartment() != null
					&& !taskReportForm.getDepartment().equalsIgnoreCase("all")) {
				List<EmployeeInfo> employees = employees(taskReportForm.getDepartment());
				for (int j = 0; j < employees.size(); j++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(j);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());
					
					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);			
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int i = 0; i < list.size(); i++) {
						Task taskDTO = new Task();
						taskDTO = list.get(i);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
					
					//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());
					
					listTaskSummary.add(taskSummay);
				}
			} else {
				List<EmployeeInfo> employees = employees("all");
				for (int i = 0; i < employees.size(); i++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(i);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());
					
					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);			
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int j = 0; j < list.size(); j++) {
						Task taskDTO = new Task();
						taskDTO = list.get(j);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
					
					//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());					
					
					listTaskSummary.add(taskSummay);
					
				}
			}
			
			if(taskReportForm.getFromDate() != null && taskReportForm.getFromDate().length() > 0 && taskReportForm.getFromDate().contains("-"))
				taskReportForm.setFromDate(Utils.convertDateToDisplay(taskReportForm.getFromDate()));
			
			if(taskReportForm.getToDate() != null && taskReportForm.getToDate().length() > 0 && taskReportForm.getToDate().contains("-"))
				taskReportForm.setToDate(Utils.convertDateToDisplay(taskReportForm.getToDate()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("reportForm", taskReportForm);
		model.addAttribute("listTaskSummary", listTaskSummary);
		model.addAttribute("formTitle", "Thông tin thống kê khối lượng công việc");
		return "taskSummaryReport";
	}

	@RequestMapping("/exportSummaryToPDF")
	public String getSummaryReportToPDF(Model model,
			@RequestParam(required = false, value = "fDate") String fDate,
			@RequestParam(required = false, value = "tDate") String tDate, 
			@RequestParam(required = false, value = "eId") String eId, 			
			@RequestParam(required = false, value = "dept") String dept	) throws Exception{		
		
		String fileName = "Thong ke khoi luong cong viec";		
		String title = "Thống kê khối lượng công việc";		
		String path = properties.getProperty("REPORT_PATH");
		File dir = new File(path);
		try {
			int id = 0;
			String eName = "";
			if(eId != null && eId.length() > 0) {
				id = Integer.parseInt(eId);
				eName = allEmployeesMap().get(id);
			}	
			
			List<TaskSummay> listTaskSummary = new ArrayList<TaskSummay>();
			ReportForm taskReportForm = new ReportForm();
			taskReportForm.setFromDate(fDate);
			taskReportForm.setToDate(tDate);
			taskReportForm.setEmployeeId(id);
			taskReportForm.setDepartment(dept);
			if (id > 0) {
				TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, id);				
				// tinh tong thoi gian da lam va thoi gian estimate
				List<Task> list = null;
				list = taskDAO.getTasksForReport(taskReportForm, null);			
				float timeEstimateTotal = 0;
				float timeSpentTotal = 0;
				for (int i = 0; i < list.size(); i++) {
					Task taskDTO = new Task();
					taskDTO = list.get(i);
					if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
						if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
							timeSpentTotal = timeSpentTotal
									+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
							timeSpentTotal = timeSpentTotal
									+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
						else
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
					}
					if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
						if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
						else
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
					}
				}
				BigDecimal s = new BigDecimal(timeSpentTotal);
				BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);
	
				BigDecimal e = new BigDecimal(timeEstimateTotal);
				BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
				
				//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
				//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
				taskSummay.setTotalEstimate(estimateTT.toString());
				taskSummay.setTotalSpent(spentTT.toString());
				listTaskSummary.add(taskSummay);
			} else if (taskReportForm.getDepartment() != null && !taskReportForm.getDepartment().equalsIgnoreCase("all")) {
				List<EmployeeInfo> employees = employees(taskReportForm.getDepartment());
				for (int j = 0; j < employees.size(); j++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(j);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());					
					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);			
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int i = 0; i < list.size(); i++) {
						Task taskDTO = new Task();
						taskDTO = list.get(i);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);
	
					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
					
					//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());
					
					listTaskSummary.add(taskSummay);
				}
			} else {																						
				List<EmployeeInfo> employees = employees("all");
				for (int i = 0; i < employees.size(); i++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(i);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());
					
					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);			
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int j = 0; j < list.size(); j++) {
						Task taskDTO = new Task();
						taskDTO = list.get(j);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal
										+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal
										+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);
	
					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
					
					//System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					//System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" + estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());					
					
					listTaskSummary.add(taskSummay);
				}
			}
			
			Document document = new Document(PageSize.A4.rotate());			
			if (!dir.exists()) {
				dir.mkdirs();
			}			
			
			String fDateStore = Utils.convertDateToStore(fDate);
			String tDateStore = Utils.convertDateToStore(tDate);
			
			if (id > 0 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName + " phong " + dept + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName + " phòng " + dept;
			}else if (id < 1 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua phong " + dept + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của phòng " + dept;
			}else if (id > 0 && dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName;
			}else {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua tat ca cac phong ban.pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của tất cả các phòng ban";
			}
			
			PdfWriter.getInstance(document, new FileOutputStream(dir + "/" + fileName));
			BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font fontB = new Font(bf, 18);
			//Font fontT = new Font(bf, 16);
			Font font = new Font(bf, 14);
			document.open();

			PdfPTable table = new PdfPTable(10);
			addTableHeaderSummaryReportToPDF(table, font);			
	
			addSummaryReportToPDFRows(table, listTaskSummary);
			// addCustomRows(table);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			document.add(new Paragraph(title, fontB));
			document.add(new Paragraph("                       ", font));
			document.add(new Paragraph("                       ", font));
			document.add(table);
			document.add(new Paragraph("                       ", font));
			document.add(new Paragraph("                       Ngày thống kê: "
							+ Utils.convertDateToDisplay(currentDate),
					font));
	
			document.close();
			SendReportForm sendReportForm = new SendReportForm();
			sendReportForm.setFileName(fileName);
			sendReportForm.setSubject(title);
			model.addAttribute("sendReportForm", sendReportForm);
			model.addAttribute("path", path);
			model.addAttribute("formTitle", "Gửi thống kê khối lượng công việc ");
			model.addAttribute("fileSave", fileName + "', đã được export tại thư mục " + dir );
			model.addAttribute("reportForm", taskReportForm);
			model.addAttribute("message", "Thống kê khối lượng công việc được export thành công ra file " + dir + fileName);
		} catch (Exception e) {
			model.addAttribute("isOpen", "Yes");
			model.addAttribute("warning", "Vui lòng tắt file " + dir + fileName + " nếu đang mở trước khi export");
			e.printStackTrace();
		}
		
		return "sendSummaryReport";
	}	
	
	private void addTableHeaderSummaryReportToPDF(PdfPTable table, Font font) throws Exception {
		PdfPCell header = new PdfPCell();		
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Người làm", font));
		table.addCell(header);			
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Mới", font));
		table.addCell(header);		

		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Đang làm", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tạm dừng", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Hủy bỏ", font));
		table.addCell(header);		

		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Chờ đánh giá", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Đã xong", font));
		table.addCell(header);		

		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tổng số", font));
		table.addCell(header);				
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tgian đã làm", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tgian ước lượng", font));
		table.addCell(header);

	}

	private void addSummaryReportToPDFRows(PdfPTable table, List<TaskSummay> taskSummays) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);
		for (int i = 0; i < taskSummays.size(); i++) {
			TaskSummay taskSummay = new TaskSummay();
			taskSummay = (TaskSummay) taskSummays.get(i);

			table.addCell(new Paragraph(taskSummay.getEmployeeName(), font));
			table.addCell(String.valueOf(taskSummay.getTaskNew()));
			table.addCell(String.valueOf(taskSummay.getTaskInprogess()));
			table.addCell(String.valueOf(taskSummay.getTaskStoped()));
			table.addCell(String.valueOf(taskSummay.getTaskinvalid()));
			table.addCell(String.valueOf(taskSummay.getTaskReviewing()));
			table.addCell(String.valueOf(taskSummay.getTaskCompleted()));
			table.addCell(String.valueOf(taskSummay.getTaskTotal()));
			table.addCell(new Paragraph(taskSummay.getTotalSpent()));
			table.addCell(new Paragraph(taskSummay.getTotalEstimate()));
		}
	}	
	
	@RequestMapping(value = "/sendSummaryReport")
	public String sendSummaryReport(Model model, @ModelAttribute("sendReportForm") @Validated SendReportForm sendReportForm,
			@RequestParam(required = false, value = "formTitle") String formTitle) throws Exception {
		log.info("sending SummaryReport");
		try {
			//System.err.println("formTitle " + formTitle);
			if (formTitle == null) {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				String path = properties.getProperty("REPORT_PATH");

				File file = new File(path + sendReportForm.getFileName());
				log.info("sending report: " + path + sendReportForm.getFileName());
				if (file.exists()) {
					mimeMessage.setFrom("IDITask-NotReply");
					helper.setSubject(sendReportForm.getSubject() + " --> Gửi từ PM Quản lý công việc");
					//System.err.println("Subject: " + sendReportForm.getSubject());
					mimeMessage.setContent(sendReportForm.getFileName(), "text/html; charset=UTF-8");

					Multipart multipart = new MimeMultipart();
					BodyPart attach = new MimeBodyPart();
					DataSource source = new FileDataSource(path + sendReportForm.getFileName());
					attach.setDataHandler(new DataHandler(source));
					attach.setFileName(path + sendReportForm.getFileName());

					multipart.addBodyPart(attach);
					BodyPart content = new MimeBodyPart();
					content.setContent("Thống kê khối lượng công việc", "text/html; charset=UTF-8");
					multipart.addBodyPart(content);

					mimeMessage.setContent(multipart, "text/html; charset=UTF-8");

					StringTokenizer st = new StringTokenizer(sendReportForm.getSendTo(), ";");
					while (st.hasMoreTokens()) {

						String sendTo = st.nextToken(";");
						if (sendTo != null && sendTo.length() > 0 && sendTo.contains("@") && sendTo.contains(".com")) {
							log.info("send report to " + sendTo);
							helper.setTo(sendTo);

							mailSender.send(mimeMessage);
							//System.err.println("sent");
						}
					}
					// model.addAttribute("isReload","Yes");
					model.addAttribute("formTitle", "Đã gửi thống kê khối lượng công việc");
					return "sentSummaryReport";
				} else {
					log.info("File ko ton tai hoac chua dc export");
					model.addAttribute("formTitle", "Vui lòng export file trước khi gửi");
				}
			} else {
				log.info("try to sending report again...");
				model.addAttribute("formTitle", "Gửi thống kê khối lượng công việc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sentSummaryReport";
	}	
	
	@RequestMapping("/exportSummaryToExcel")
	public String getSummaryReportToExcel(Model model,
			@RequestParam(required = false, value = "fDate") String fDate,
			@RequestParam(required = false, value = "tDate") String tDate, 
			@RequestParam(required = false, value = "eId") String eId, 			
			@RequestParam(required = false, value = "dept") String dept) throws Exception {
		try {
			int id = 0;
			String eName = "";
			if (eId != null && eId.length() > 0) {
				id = Integer.parseInt(eId);
				eName = allEmployeesMap().get(id);
			}

			List<TaskSummay> listTaskSummary = new ArrayList<TaskSummay>();
			ReportForm taskReportForm = new ReportForm();
			taskReportForm.setFromDate(fDate);
			taskReportForm.setToDate(tDate);
			taskReportForm.setEmployeeId(id);
			taskReportForm.setDepartment(dept);
			if (id > 0) {
				TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, id);
				// tinh tong thoi gian da lam va thoi gian estimate
				List<Task> list = null;
				list = taskDAO.getTasksForReport(taskReportForm, null);
				float timeEstimateTotal = 0;
				float timeSpentTotal = 0;
				for (int i = 0; i < list.size(); i++) {
					Task taskDTO = new Task();
					taskDTO = list.get(i);
					if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
						if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
									* Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
									* Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
						else
							timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
					}
					if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
						if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
							timeEstimateTotal = timeEstimateTotal
									+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
						else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
						else
							timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
					}
				}
				BigDecimal s = new BigDecimal(timeSpentTotal);
				BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

				BigDecimal e = new BigDecimal(timeEstimateTotal);
				BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);

				// System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
				// System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" +
				// estimateTT);
				taskSummay.setTotalEstimate(estimateTT.toString());
				taskSummay.setTotalSpent(spentTT.toString());
				listTaskSummary.add(taskSummay);
			} else if (taskReportForm.getDepartment() != null
					&& !taskReportForm.getDepartment().equalsIgnoreCase("all")) {
				List<EmployeeInfo> employees = employees(taskReportForm.getDepartment());
				for (int j = 0; j < employees.size(); j++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(j);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());
					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int i = 0; i < list.size(); i++) {
						Task taskDTO = new Task();
						taskDTO = list.get(i);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
										* Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
										* Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate())
										* Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate())
										* Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					// System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					// System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" +
					// estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());

					listTaskSummary.add(taskSummay);
				}
			} else {
				List<EmployeeInfo> employees = employees("all");
				for (int i = 0; i < employees.size(); i++) {
					EmployeeInfo emp = new EmployeeInfo();
					emp = employees.get(i);
					TaskSummay taskSummay = taskDAO.getSummaryTasksForReport(fDate, tDate, emp.getEmployeeId());

					// tinh tong thoi gian da lam va thoi gian estimate
					List<Task> list = null;
					taskReportForm.setEmployeeId(emp.getEmployeeId());
					list = taskDAO.getTasksForReport(taskReportForm, null);
					float timeEstimateTotal = 0;
					float timeSpentTotal = 0;
					for (int j = 0; j < list.size(); j++) {
						Task taskDTO = new Task();
						taskDTO = list.get(j);
						if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
							if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
										* Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent())
										* Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
							else
								timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
						}
						if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
							if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate())
										* Float.valueOf(properties.getProperty("w"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate())
										* Float.valueOf(properties.getProperty("d"));
							else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
							else
								timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
						}
					}
					BigDecimal s = new BigDecimal(timeSpentTotal);
					BigDecimal spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					BigDecimal e = new BigDecimal(timeEstimateTotal);
					BigDecimal estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);

					// System.err.println("spent: " + timeSpentTotal + ":" + s + ":" + spentTT);
					// System.err.println("estimate:" + timeEstimateTotal +":" + e + ":" +
					// estimateTT);
					taskSummay.setTotalEstimate(estimateTT.toString());
					taskSummay.setTotalSpent(spentTT.toString());

					listTaskSummary.add(taskSummay);
				}
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			
			String path = properties.getProperty("REPORT_PATH");
			File dir = new File(path);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Thống kê khối lượng công việc");

			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			org.apache.poi.ss.usermodel.Font font = sheet.getWorkbook().createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 14);
			cellStyle.setFont(font);

			Row row1 = sheet.createRow(0);
			Cell cell = row1.createCell(1);

			String fDateStore = Utils.convertDateToStore(fDate);
			String tDateStore = Utils.convertDateToStore(tDate);

			String fileName = "Thong ke khoi luong cong viec";	
			String title = "Thống kê khối lượng công việc";	
			
			if (id > 0 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName + " phong " + dept;
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName + " phòng " + dept;
			}else if (id < 1 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua phong " + dept;
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của phòng " + dept;
			}else if (id > 0 && dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName;
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName;
			}else {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua tat ca cac phong ban";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của tất cả các phòng ban";
			}			

			cell.setCellStyle(cellStyle);
			cell.setCellValue(title);

			int rowNum = 1;
			Row row2 = sheet.createRow(rowNum);
			Cell cell0 = row2.createCell(2);
			CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
			org.apache.poi.ss.usermodel.Font font1 = sheet.getWorkbook().createFont();
			font1.setBold(true);
			font1.setFontHeightInPoints((short) 11);
			cellStyle1.setFont(font1);
			cell0.setCellStyle(cellStyle1);
			cell0.setCellValue("Ngày thống kê: " + Utils.convertDateToDisplay(currentDate));

			// Generate column name
			rowNum = 3;
			Row row = sheet.createRow(rowNum);
			Cell cell11 = row.createCell(0);
			cell11.setCellStyle(cellStyle1);
			cell11.setCellValue("Người làm");
			Cell cell21 = row.createCell(1);
			cell21.setCellStyle(cellStyle1);
			cell21.setCellValue("Mới");
			Cell cell31 = row.createCell(2);
			cell31.setCellStyle(cellStyle1);
			cell31.setCellValue("Đang làm");
			Cell cell41 = row.createCell(3);
			cell41.setCellStyle(cellStyle1);
			cell41.setCellValue("Tạm dừng");
			Cell cell51 = row.createCell(4);
			cell51.setCellStyle(cellStyle1);
			cell51.setCellValue("Hủy bỏ");
			Cell cell61 = row.createCell(5);
			cell61.setCellStyle(cellStyle1);
			cell61.setCellValue("Chờ đánh giá");
			Cell cell71 = row.createCell(6);
			cell71.setCellStyle(cellStyle1);
			cell71.setCellValue("Đã xong");
			Cell cell81 = row.createCell(7);
			cell81.setCellStyle(cellStyle1);
			cell81.setCellValue("Tổng số");
			Cell cell91 = row.createCell(8);
			cell91.setCellStyle(cellStyle1);
			cell91.setCellValue("Tgian đã làm");
			Cell cell101 = row.createCell(9);
			cell101.setCellStyle(cellStyle1);
			cell101.setCellValue("Tgian ước lượng");
			
			// generate values
			rowNum = 4;
			for (int i = 0; i < listTaskSummary.size(); i++) {
				row = sheet.createRow(rowNum++);
				int colNum = 0;
				TaskSummay taskSummay = new TaskSummay();
				taskSummay = listTaskSummary.get(i);
				Cell cell1 = row.createCell(colNum++);
				cell1.setCellValue(taskSummay.getEmployeeName());
				Cell cell2 = row.createCell(colNum++);
				cell2.setCellValue(taskSummay.getTaskNew());
				Cell cell3 = row.createCell(colNum++);
				cell3.setCellValue(taskSummay.getTaskInprogess());
				Cell cell4 = row.createCell(colNum++);
				cell4.setCellValue(taskSummay.getTaskStoped());
				Cell cell5 = row.createCell(colNum++);
				cell5.setCellValue(taskSummay.getTaskinvalid());
				Cell cell6 = row.createCell(colNum++);
				cell6.setCellValue(taskSummay.getTaskReviewing());
				Cell cell7 = row.createCell(colNum++);
				cell7.setCellValue(taskSummay.getTaskCompleted());
				Cell cell8 = row.createCell(colNum++);
				cell8.setCellValue(taskSummay.getTaskTotal());
				Cell cell9 = row.createCell(colNum++);
				cell9.setCellValue((String)taskSummay.getTotalSpent());
				Cell cell10 = row.createCell(colNum++);
				cell10.setCellValue((String)taskSummay.getTotalEstimate());
			}

			try {
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream outputStream = new FileOutputStream(dir + "/" + fileName + ".xlsx");
				workbook.write(outputStream);
				workbook.close();
				model.addAttribute("message", "Thống kê khối lượng công việc được export thành công ra file " + dir + fileName + ".xlsx");
			} catch (FileNotFoundException e) {
				model.addAttribute("warning", "Vui lòng tắt file " + dir + fileName + ".xlsx nếu đang mở trước khi export");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			SendReportForm sendReportForm = new SendReportForm();
			sendReportForm.setFileName(fileName + ".xlsx");
			sendReportForm.setSubject(title);
			model.addAttribute("sendReportForm", sendReportForm);
			model.addAttribute("path", path);
			model.addAttribute("formTitle", "Gửi thống kê khối lượng công việc ");
			model.addAttribute("fileSave", fileName + ".xlsx', đã được export tại thư mục " + dir);
			model.addAttribute("reportForm", taskReportForm);

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "sendSummaryReport";
	}
	
	@RequestMapping(value = "/generateTaskReport", params = "generateTaskReport")
	public String generateTaskReport(Model model,
			@ModelAttribute("taskReportForm") @Validated ReportForm taskReportForm) throws Exception {
		
		List<Task> listCurrent = null;
		
		if(taskReportForm.getFromDate() != null && taskReportForm.getFromDate().length() > 0 && taskReportForm.getFromDate().contains("/"))
			taskReportForm.setFromDate(Utils.convertDateToStore(taskReportForm.getFromDate()));
		
		if(taskReportForm.getToDate() != null && taskReportForm.getToDate().length() > 0 && taskReportForm.getToDate().contains("/"))
			taskReportForm.setToDate(Utils.convertDateToStore(taskReportForm.getToDate()));
		
		listCurrent = taskDAO.getTasksForReport(taskReportForm, "'Đang làm','Tạm dừng','Hủy bỏ','Chờ đánh giá','Đã xong'");

		List<Task> listNext = null;
		listNext = taskDAO.getTasksForReport(taskReportForm, "'Mới','Mới tạo','Đang làm','Chờ đánh giá'");
		
		// inject from Login account

		LoginController lc = new LoginController(); 
		if(lc.getPrincipal() != null) {
			String username = lc.getPrincipal();
			log.info("Using usename =" + username);
			if (username != null && username.length() > 0) {
				taskReportForm.setSender(username);
			}
		}		 

		if(taskReportForm.getFromDate() != null && taskReportForm.getFromDate().length() > 0 && taskReportForm.getFromDate().contains("-"))
			taskReportForm.setFromDate(Utils.convertDateToDisplay(taskReportForm.getFromDate()));
		
		if(taskReportForm.getToDate() != null && taskReportForm.getToDate().length() > 0 && taskReportForm.getToDate().contains("-"))
			taskReportForm.setToDate(Utils.convertDateToDisplay(taskReportForm.getToDate()));
		
		// get list employee email
		//model.addAttribute("employeeEmailMap", employeesEmail("all"));
		model.addAttribute("reportForm", taskReportForm);
		model.addAttribute("tasks", listCurrent);
		model.addAttribute("tasksNext", listNext);
		model.addAttribute("formTitle", "Thông tin báo cáo công việc");
		return "taskReport";
	}

	private boolean isTaskId = false;
	private boolean isEstimateTime = false;
	private boolean isUpdatedTime = false;
	private boolean isdueDate = false;
	private boolean isDes = false;
	private String unSelected = "";
	
	@RequestMapping("/sendReportForm")
	public String sendReportForm(Model model, @ModelAttribute("fDate") String fDate,
			@ModelAttribute("tDate") String tDate, @ModelAttribute("eName") String eName,
			@ModelAttribute("dept") String dept, @ModelAttribute("eId") int eId) throws Exception {
		SendReportForm sendReportForm = new SendReportForm();
		if (eId > 0)
			if (dept != null && !dept.equalsIgnoreCase("all"))
				sendReportForm.setFileName(
						"BCCV từ ngày " + fDate + " đến ngày " + tDate + " của " + eName + " phòng " + dept);
			else
				sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của " + eName);
		else if (dept != null && !dept.equalsIgnoreCase("all"))
			sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của phòng " + dept);
		else
			sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của tất cả các phòng ban");

		sendReportForm.setSubject(sendReportForm.getFileName() + " --> Gửi từ PM Quản lý công việc");
		//List<Task> list = null;
		ReportForm taskReportForm = new ReportForm();
		taskReportForm.setFromDate(fDate);
		taskReportForm.setToDate(tDate);
		taskReportForm.setEmployeeId(eId);
		taskReportForm.setDepartment(dept);
		taskReportForm.setUnSelect(unSelected);
		//list = taskDAO.getTasksForReport(taskReportForm);
		
		List<Task> listCurrent = null;
		listCurrent = taskDAO.getTasksForReport(taskReportForm, "'Đang làm','Tạm dừng','Hủy bỏ','Chờ đánh giá','Đã xong'");

		List<Task> listNext = null;
		listNext = taskDAO.getTasksForReport(taskReportForm, "'Mới','Mới tạo','Đang làm','Chờ đánh giá'");
		
		if(isTaskId)
			model.addAttribute("isTaskId", "Y");
		if(isDes) 
			model.addAttribute("isDes", "Y");		
		if(isEstimateTime)
			model.addAttribute("isEstimateTime", "Y");
		if(isUpdatedTime) 
			model.addAttribute("isUpdatedTime", "Y");
		if(isdueDate)
			model.addAttribute("isdueDate", "Y");
		
		if(taskReportForm.getFromDate() != null && taskReportForm.getFromDate().length() > 0 && taskReportForm.getFromDate().contains("-"))
			taskReportForm.setFromDate(Utils.convertDateToDisplay(taskReportForm.getFromDate()));
		
		if(taskReportForm.getToDate() != null && taskReportForm.getToDate().length() > 0 && taskReportForm.getToDate().contains("-"))
			taskReportForm.setToDate(Utils.convertDateToDisplay(taskReportForm.getToDate()));
		
		// get list employee email
		model.addAttribute("employeeEmailMap", employeesEmail("all"));
		
		//model.addAttribute("tasks", list);
		model.addAttribute("tasks", listCurrent);
		model.addAttribute("tasksNext", listNext);
		model.addAttribute("formTitle", "Gửi báo cáo công việc");
		model.addAttribute("sendReportForm", sendReportForm);

		return "sendReportForm";
	}

	@RequestMapping(value = "/sendReport")
	public String sendReport(Model model, @ModelAttribute("sendReportForm") @Validated SendReportForm sendReportForm,
			@RequestParam(required = false, value = "formTitle") String formTitle) throws Exception {
		log.info("sending report");
		try {
			if (formTitle == null) {

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				String path = properties.getProperty("REPORT_PATH");

				File file = new File(path + sendReportForm.getFileName() + ".pdf");
				// log.info("sending report: " + path +"|" +
				// sendReportForm.getSubject()+"|"+sendReportForm.getFileName());
				if (file.exists()) {

					mimeMessage.setFrom("IDITask-NotReply");
					helper.setSubject(sendReportForm.getSubject());
					System.err.println("Subject: " + sendReportForm.getSubject());
					mimeMessage.setContent(sendReportForm.getFileName(), "text/html; charset=UTF-8");

					Multipart multipart = new MimeMultipart();
					BodyPart attach = new MimeBodyPart();
					DataSource source = new FileDataSource(path + sendReportForm.getFileName() + ".pdf");
					attach.setDataHandler(new DataHandler(source));
					attach.setFileName(path + sendReportForm.getFileName() + ".pdf");

					multipart.addBodyPart(attach);
					BodyPart content = new MimeBodyPart();
					content.setContent("Báo cáo công việc", "text/html; charset=UTF-8");
					multipart.addBodyPart(content);

					mimeMessage.setContent(multipart, "text/html; charset=UTF-8");

					StringTokenizer st = new StringTokenizer(sendReportForm.getSendTo(), ";");
					while (st.hasMoreTokens()) {

						String sendTo = st.nextToken(";");
						if (sendTo != null && sendTo.length() > 0 && sendTo.contains("@") && sendTo.contains(".com")) {
							log.info("send report cho " + sendTo);
							helper.setTo(sendTo);

							mailSender.send(mimeMessage);
							// System.err.println("sent");
						}
					}
					// model.addAttribute("isReload","Yes");
					model.addAttribute("formTitle", "Gửi báo cáo công việc");
					return "redirect:/sendReport";
				} else {
					model.addAttribute("formTitle", "Vui lòng export file trước khi gửi báo cáo công việc");
				}
			} else {
				log.info("try to sending report again...");
				model.addAttribute("formTitle", "Gửi báo cáo công việc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sentReport";
	}	

	@RequestMapping("/exportToPDF")
	public String getPDF(Model model, @ModelAttribute("taskReportForm") @Validated ReportForm taskReportForm)
			throws Exception {
		try {
			isTaskId = false;
			isEstimateTime = false;
			isUpdatedTime = false;
			isdueDate = false;
			isDes = false;
			
			// Document document = new Document();
			Document document = new Document(PageSize.A4.rotate());
			String path = properties.getProperty("REPORT_PATH");
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			//System.err.println("iD check: " + taskReportForm.getIdCheck());
			//System.err.println("estimateCheck: " + taskReportForm.getEstimateCheck());
			int columnAdded = 0;
			if(taskReportForm.getIdCheck() != null && taskReportForm.getIdCheck().equalsIgnoreCase("Y")) {
				columnAdded = columnAdded + 1;
				isTaskId = true;
			}	
			if(taskReportForm.getEstimateCheck() != null && taskReportForm.getEstimateCheck().equalsIgnoreCase("Y")) {
				columnAdded = columnAdded + 1;
				isEstimateTime = true;
			}	
			if(taskReportForm.getUpdateTimeCheck() != null && taskReportForm.getUpdateTimeCheck().equalsIgnoreCase("Y")) {
				columnAdded = columnAdded + 1;
				isUpdatedTime = true;
			}	
			if(taskReportForm.getDueDateCheck() != null && taskReportForm.getDueDateCheck().equalsIgnoreCase("Y")) {
				columnAdded = columnAdded + 1;
				isdueDate = true;
			}
			if(taskReportForm.getDesCheck() != null && taskReportForm.getDesCheck().equalsIgnoreCase("Y")) {
				columnAdded = columnAdded + 1;
				isDes = true;
			}
			
			int eId = taskReportForm.getEmployeeId();
			String dept = taskReportForm.getDepartment();
			String fDate = taskReportForm.getFromDate();
			String tDate = taskReportForm.getToDate();
			String eName = allEmployeesMap().get(taskReportForm.getEmployeeId());
			taskReportForm.setEmployeeName(eName);

			String fDateStore = Utils.convertDateToStore(fDate);
			taskReportForm.setFromDate(fDateStore);
			String tDateStore = Utils.convertDateToStore(tDate);
			taskReportForm.setToDate(tDateStore);
			
			String fileName = "BCCV";	
			String title = "BCCV";	
			
			if (eId > 0 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName + " phong " + dept + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName + " phòng " + dept;
			}else if (eId < 1 && !dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua phong " + dept + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của phòng " + dept;
			}else if (eId > 0 && dept.equalsIgnoreCase("all")) {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua " + eName + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của " + eName;
			}else {
				fileName = fileName + " tu ngay " + fDateStore + " den ngay " + tDateStore + " cua tat ca cac phong ban" + ".pdf";
				title = title + " từ ngày " + fDate + " đến ngày " + tDate + " của tất cả các phòng ban";
			}

			PdfWriter.getInstance(document, new FileOutputStream(dir + "/" + fileName));
			BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			// BaseFont bfB = BaseFont.createFont(fontBFile.getAbsolutePath(),
			// BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontB = new Font(bf, 18);
			// Font fontB14 = new Font(bfB,14);
			Font font = new Font(bf, 14);
			document.open();
			// Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, "UTF-8");
			// Chunk chunk = new Chunk(fileName, font);
			// document.add(chunk);

			PdfPTable table = new PdfPTable(5 + columnAdded);
			addTableHeader(table, font);
			//List<Task> list = null;
			//List<Task> listTask = null;
			//list = taskDAO.getTasksForReport(taskReportForm);
			List<Task> listCurrent = null;
			listCurrent = taskDAO.getTasksForReport(taskReportForm, "'Đang làm','Tạm dừng','Hủy bỏ','Chờ đánh giá','Đã xong'");

			List<Task> listNext = null;
			listNext = taskDAO.getTasksForReport(taskReportForm, "'Mới','Mới tạo','Đang làm','Chờ đánh giá'");
			
			
			unSelected = taskReportForm.getUnSelect();
			// tinh tong thoi gian da lam va tg estimate
			float timeEstimateTotal = 0;
			float timeSpentTotal = 0;
			for (int i = 0; i < listCurrent.size(); i++) {
				Task taskDTO = new Task();
				taskDTO = listCurrent.get(i);
				if (taskDTO.getTimeSpent() != null && taskDTO.getTimeSpent().length() > 0) {
					if (taskDTO.getTimeSpentType().equalsIgnoreCase("w"))
						timeSpentTotal = timeSpentTotal
								+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("w"));
					else if (taskDTO.getTimeSpentType().equalsIgnoreCase("d"))
						timeSpentTotal = timeSpentTotal
								+ Float.valueOf(taskDTO.getTimeSpent()) * Float.valueOf(properties.getProperty("d"));
					else if (taskDTO.getTimeSpentType().equalsIgnoreCase("m"))
						timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent()) / 60;
					else
						timeSpentTotal = timeSpentTotal + Float.valueOf(taskDTO.getTimeSpent());
				}
				if (taskDTO.getEstimate() != null && taskDTO.getEstimate().length() > 0) {
					if (taskDTO.getEstimateTimeType().equalsIgnoreCase("w"))
						timeEstimateTotal = timeEstimateTotal
								+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("w"));
					else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("d"))
						timeEstimateTotal = timeEstimateTotal
								+ Float.valueOf(taskDTO.getEstimate()) * Float.valueOf(properties.getProperty("d"));
					else if (taskDTO.getEstimateTimeType().equalsIgnoreCase("m"))
						timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate()) / 60;
					else
						timeEstimateTotal = timeEstimateTotal + Float.valueOf(taskDTO.getEstimate());
				}
			}
			
			BigDecimal spentTT = new BigDecimal(0);
			BigDecimal estimateTT = new BigDecimal(0);
			BigDecimal percentCurrent = new BigDecimal(0);
			if(timeEstimateTotal > 0 && timeSpentTotal > 0) {
				BigDecimal s = new BigDecimal(timeSpentTotal);
				spentTT = s.setScale(1, BigDecimal.ROUND_HALF_EVEN);
	
				BigDecimal e = new BigDecimal(timeEstimateTotal);
				estimateTT = e.setScale(1, BigDecimal.ROUND_HALF_EVEN);
	
				float percent = (timeSpentTotal / timeEstimateTotal) * 100;
				BigDecimal p = new BigDecimal(percent);
				percentCurrent = p.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			}
			// System.err.println("time estimate:= " + estimateTT + "/time spent: " +
			// spentTT);

			addRows(table, listCurrent);
			// addCustomRows(table);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			document.add(new Paragraph("                     " + title, fontB));
			document.add(new Paragraph("                     ", font));
			document.add(new Paragraph("I) Kết quả thực hiện                 ", font));
			document.add(new Paragraph("                       ", font));
			document.add(table);
			document.add(
					new Paragraph("                       Tổng thời gian thực tế đã làm / Tổng thời gian ước lượng: "
									+ spentTT + " giờ / " + estimateTT + " giờ, tương đương " + percentCurrent + "%",
							font));	
			document.add(new Paragraph("                     ", font));
			document.add(new Paragraph("II) Kế hoạch                         ", font));
			document.add(new Paragraph("                       ", font));
			PdfPTable table1 = new PdfPTable(5 + columnAdded);
			addTableHeader(table1, font);
			addRows(table1, listNext);
			document.add(table1);
			document.add(new Paragraph("                     ", font));
			document.add(new Paragraph("III) Đánh giá thực hiện kế hoạch: ", font));
			document.add(new Paragraph(taskReportForm.getSummary(), font));
			document.add(new Paragraph("                       ", font));
					
			document.add(new Paragraph("VI) Ý kiến/ Đề xuất: ", font));
			document.add(new Paragraph(taskReportForm.getComment(), font));
			document.add(new Paragraph("                       ", font));
			document.add(new Paragraph("                       ", font));
			document.add(new Paragraph(
					"                                                                                                                                  Ngày tạo báo cáo: "
							+ Utils.convertDateToDisplay(currentDate),
					font));
			document.add(new Paragraph(
					"                                                                                                                                  Người báo cáo: " 
							+ taskReportForm.getSender(),
					font));

			document.close();
			
			taskReportForm.setFromDate(fDate);
			taskReportForm.setToDate(tDate);
			
			model.addAttribute("reportForm", taskReportForm);
			model.addAttribute("path", path);
			model.addAttribute("formTitle", "Export báo cáo công việc");
		} catch (Exception e) {
			model.addAttribute("isOpen", "Yes");
			e.printStackTrace();
		}
		return "taskExport";
	}

	private void addTableHeader(PdfPTable table, Font font) throws Exception {
		/*
		 * Stream.of("Tên việc", "Người làm", "Trạng thái", "Thời gian ước lượng",
		 * "Thời gian đã làm", "Ngày phải xong",
		 * "Nhận xét/đánh giá").forEach(columnTitle -> { PdfPCell header = new
		 * PdfPCell(); header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		 * header.setPhrase(new Phrase(columnTitle, font)); table.addCell(header); });
		 */
		
		//Stream.of("Tên việc", "Người làm", "Trạng thái", "Thời gian ước lượng", "Thời gian đã làm", "Ngày phải xong",
		//		"Nhận xét/đánh giá").forEach(columnTitle -> {
		PdfPCell header = new PdfPCell();		
		//System.err.println("isTaskId: " + isTaskId);
		if(isTaskId) {			
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Mã việc", font));
			table.addCell(header);
		}			
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Tên việc", font));
		table.addCell(header);
		
		if(isDes) {
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Mô tả", font));
			table.addCell(header);
		}
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Người làm", font));
		table.addCell(header);
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Trạng thái", font));
		table.addCell(header);
		
		if(isEstimateTime) {
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Thời gian ước lượng", font));
			table.addCell(header);
		}
		
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Thời gian đã làm", font));
		table.addCell(header);
		
		if(isUpdatedTime) {
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Cập nhật gần nhất", font));
			table.addCell(header);
		}					
		
		if(isdueDate) {
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setPhrase(new Phrase("Ngày phải xong", font));
			table.addCell(header);
		}			
		header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		header.setPhrase(new Phrase("Nhận xét/đánh giá", font));
		table.addCell(header);
		//		});
	}

	private void addRows(PdfPTable table, List<Task> tasks) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);

		// SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		for (int i = 0; i < tasks.size(); i++) {
			Task task = new Task();
			task = (Task) tasks.get(i);
			if(isTaskId)
				table.addCell(String.valueOf(task.getTaskId()));
			table.addCell(new Paragraph(task.getTaskName(), font));
			if(isDes)
				table.addCell(new Paragraph(task.getDescription(), font));
			if(task.getOwnerName() != null && task.getOwnerName().length() > 0)
				table.addCell(new Paragraph(task.getOwnerName(), font));
			else
				table.addCell(new Paragraph("Chưa giao cho ai", font));
			table.addCell(new Paragraph(task.getStatus(), font));
			if(isEstimateTime) {
				if (task.getEstimate() != null)
					if (task.getEstimateTimeType() != null)
						table.addCell(new Paragraph(
								task.getEstimate() + " " + timeStypeMap().get(task.getEstimateTimeType()), font));
					else
						table.addCell(new Paragraph(task.getEstimate() + " phút", font));
				else
					table.addCell(new Paragraph("0 phút ", font));
			}
			if (task.getTimeSpent() != null)
				if (task.getTimeSpentType() != null)
					table.addCell(new Paragraph(task.getTimeSpent() + " " + timeStypeMap().get(task.getTimeSpentType()),
							font));
				else
					table.addCell(new Paragraph(task.getTimeSpent() + " phút", font));
			else
				table.addCell(new Paragraph("0 phút", font));
			if(isUpdatedTime)
				table.addCell(String.valueOf(task.getUpdateTS()));
			if(isdueDate)
				table.addCell(task.getDueDate());
			table.addCell(new Paragraph(task.getReviewComment(), font));
		}
	}

	private List<EmployeeInfo> employeesForSub(String subscriber) throws Exception {
		List<EmployeeInfo> list = null;
		list = employeeDAO.getEmployeesForSub(subscriber);
		return list;
	}

	private List<EmployeeInfo> employeesSub(String subscriber) throws Exception {
		List<EmployeeInfo> list = null;
		list = employeeDAO.getEmployeesSub(subscriber);
		return list;
	}

	private List<EmployeeInfo> employeesOwner() throws Exception {
		List<EmployeeInfo> list = null;
		list = employeeDAO.getListOwner();

		return list;
	}

	private List<EmployeeInfo> employees(String department) throws Exception {
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}

	private Map<String, String> employeesEmail(String department) throws Exception {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		EmployeeInfo employee = new EmployeeInfo();
		for (int i = 0; i < list.size(); i++) {
			employee = (EmployeeInfo) list.get(i);
			String email = employee.getEmail();
			employeeMap.put(email, employee.getFullName() + ": " + email);
		}

		return employeeMap;
	}

	private Map<Integer, String> allEmployeesMap() throws Exception {
		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
		List<EmployeeInfo> list = null;
		list = employeeDAO.getAllEmployees();

		EmployeeInfo employee = new EmployeeInfo();
		for (int i = 0; i < list.size(); i++) {
			employee = (EmployeeInfo) list.get(i);
			Integer id = employee.getEmployeeId();
			employeeMap.put(id, employee.getFullName());
		}

		return employeeMap;
	}

	private Map<String, String> employeesMap(String department) throws Exception {
		Map<String, String> employeeMap = new LinkedHashMap<String, String>();
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		EmployeeInfo employee = new EmployeeInfo();
		for (int i = 0; i < list.size(); i++) {
			employee = (EmployeeInfo) list.get(i);
			Integer id = employee.getEmployeeId();
			employeeMap.put(id.toString(), "Mã NV " + id + ", " + employee.getFullName());
		}

		return employeeMap;
	}

	private Map<String, String> listDepartments() throws Exception {
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

	public static Map<String, String> timeStypeMap() {
		Map<String, String> timeStype = new LinkedHashMap<String, String>();
		timeStype.put("m", "Phút");
		timeStype.put("h", "Giờ");
		timeStype.put("d", "Ngày");
		timeStype.put("w", "Tuần");
		return timeStype;
	}

	private CategoryDataset createDatasetI(Map<String, Integer> values) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Map.Entry<String, Integer> entry : values.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			dataset.addValue(entry.getValue(), entry.getKey(), "");
		}
		return dataset;
	}

	// For Ajax
	@RequestMapping("/selection")
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("department") String department)
			throws Exception {
		System.out.println("AJax");
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}

	@RequestMapping("/selectionArea")
	public @ResponseBody List<EmployeeInfo> employeesByArea(@RequestParam("area") String area) throws Exception {
		System.out.println("AJax");
		List<EmployeeInfo> list = null;
		if (!area.equalsIgnoreCase(""))
			list = employeeDAO.getEmployeesByDepartment(area);
		else
			list = employeeDAO.getEmployees();

		return list;
	}

	/*
	 * @RequestMapping("/lookingTask") public @ResponseBody List<Task>
	 * getTaskForAddingRelated(@RequestParam("relatedAdding") String relatedAdding)
	 * throws Exception { System.err.println("AJax"); List<Task> list = null; if
	 * (relatedAdding != null && relatedAdding.length() > 0) list =
	 * taskDAO.getTasksBySearch(relatedAdding);
	 * 
	 * return list; }
	 */
}
