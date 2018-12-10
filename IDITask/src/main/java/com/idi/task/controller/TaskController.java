package com.idi.task.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.idi.task.common.PropertiesManager;
import com.idi.task.common.Utils;
import com.idi.task.dao.TaskDAO;
import com.idi.task.form.ReportForm;
import com.idi.task.form.SendReportForm;
import com.idi.task.form.TaskForm;
import com.idi.task.login.bean.UserLogin;
import com.idi.task.login.dao.UserRoleDAO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
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
	
	PropertiesManager properties = new PropertiesManager("task.properties");
	
	public static File fontFile = new File("/home/idi/properties/vuTimes.ttf");
	
	@RequestMapping(value = { "/" })
	public String listTasks(Model model, @ModelAttribute("taskForm") TaskForm form) throws Exception {
		try {
			List<Task> list = null;
			
			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);

			// get list employee id
			model.addAttribute("employeesList", employees("all"));
			
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
					|| (form.getArea() != null && form.getArea() != "") || form.getOwnedBy() > 0 ){
				log.info("Searching for: '" + form.getSearchValue()+ "', '" + form.getArea() + "','" + form.getOwnedBy());
				search = true;
				list = taskDAO.getTasksBySearch(form.getSearchValue(), form.getArea(), form.getOwnedBy());
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
						listTaskForPage.add(task);
					}
				} else if (form.getPageIndex() > 1) {
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()
							* form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						listTaskForPage.add(task);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form
						.getTotalRecords(); i++) {
					Task task = new Task();
					task = list.get(i);
					listTaskForPage.add(task);
				}
			}

			model.addAttribute("taskForm", form);
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có công việc nào được tạo");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message",
						"Không có công việc nào khớp với thông tin: Mã việc/Tên việc/Người được giao/Trạng thái công việc/Mã phòng/Kế hoạch cho tháng = '"
				+ form.getSearchValue() + "', người làm có id = " + form.getOwnedBy() + ", phòng ban có mã = '" + form.getArea() + "'.\n \n Note: id = 0 tức là không chọn ai");

			model.addAttribute("tasks", listTaskForPage);
			model.addAttribute("formTitle", "Danh sách công việc");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTask";
	}

	@RequestMapping(value = "/removeTaskRelated")
	public String removeTaskRelated(Model model, @RequestParam("taskId") int taskId, @RequestParam("taskIdRemove") int taskIdRemove) throws Exception{
		try {

			// Get danh sach cv lien quan
			Task task = new Task();
			task = taskDAO.getTask(taskId);
			String tasksRelated = task.getRelated();
			String tasksRelatedNew = "";
			if ( tasksRelated!= null && tasksRelated.length() > 0) {
				//System.err.println(tasksRelated);
				StringTokenizer st = new StringTokenizer(tasksRelated, ",");
				while(st.hasMoreElements()) {
					String temp = st.nextElement().toString();
					if(!(taskIdRemove == Integer.parseInt(temp))) {
						tasksRelatedNew = tasksRelatedNew + "," + temp;
						//System.err.println("element " + tasksRelatedNew);
					}
				}
				tasksRelatedNew = Utils.cutComma(tasksRelatedNew);
				//if(tasksRelatedNew.startsWith(","))
				//	tasksRelatedNew= tasksRelatedNew.substring(1, tasksRelatedNew.length());
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
			//System.out.println("sub new current: " + subRemove);
			//System.out.println("sub new select: " + subAddNew);

			String subUpdated = "";
			if (sub != null && sub.length() > 0) {
				// Co thay doi (remove bot nguoi lien quan)
				if (subRemove != null) {
					// System.err.println("vi tri remove" + sub.indexOf(subRemove));
					// (sub.indexOf(subRemove), sub.indexOf(subRemove) + subRemove.length());
					//System.out.println(subUpdated);
					if (subAddNew != null) {
						subUpdated = sub.replace(subRemove, subAddNew);
						System.out.println("co remove cu va add them moi " + subUpdated);
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
					//subUpdated = sub + "," + subAddNew;					
				}
			} else {
				if(subAddNew != null)
					System.out.println("Chi add them, truoc chua co " + subUpdated);
					subUpdated = subAddNew;
			}
			if(subUpdated != null  && subUpdated.length() > 0) {
				 subUpdated = Utils.cutComma(subUpdated);
				 taskDAO.updateSubscriber(subUpdated, taskForm.getTaskId());
			}				

			/*
			 * model.addAttribute("sub", sub); if(sub !=null && sub.length() > 0)
			 * model.addAttribute("subscriberList", employeesSub(sub)); else
			 * model.addAttribute("subscriberList", null); //get toan bo sach sach nguoi co
			 * the lua chon model.addAttribute("employeesList", employeesForSub(sub));
			 */

			model.addAttribute("formTitle",	"Quản lý danh sách người liên quan đến công việc mã " + taskForm.getTaskId());
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

	@RequestMapping(value = "/insertNewTask", method = RequestMethod.POST)
	public String insertNewTask(Model model, @ModelAttribute("taskForm") Task task,
			final RedirectAttributes redirectAttributes) throws Exception {
		try {
			//System.err.println("insert new task");

			//inject from Login account
			String username = new LoginController().getPrincipal();
		    log.info("Using usename =" + username +" in insert new task");
		    if (username !=null && username.length() >0 ) {
		    	UserLogin userLogin  =  userRoleDAO.getAUserLoginFull(username);
		    	task.setCreatedBy(userLogin.getUserID());
		    }
			
			Timestamp ts = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			task.setCreationDate(ts);
			task.setUpdateTS(ts);
			taskDAO.insertTask(task);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin công việc thành công!");
			String owner = "Chưa giao cho ai";
			if(task.getOwnedBy() > 0)
				owner = allEmployeesMap().get(task.getOwnedBy());
			String createdName = "";
			if(task.getCreatedBy() > 0)
				createdName = allEmployeesMap().get(task.getCreatedBy());
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			String htmlMsg = "Dear you, <br/>\n<br/>\n"
					+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
					+ "<b>Người làm: " + owner + " </b><br/>\n"
					+ "Công việc thuộc phòng: " + task.getArea() + " <br/>\n " 
					+ "Trạng thái: Tạo mới <br/>\n "
					+ "Kế hoạch cho tháng: " + task.getPlannedFor() + " <br/>\n " 
					+ "Độ ưu tiên: " + task.getPriority()
					+ "<br/> <br/> \n" 
					+ "<b>Người tạo " + createdName + " </b> <e-mail> lúc "	+ ts + " <br/>\n" 
					+ "Mô tả nội dung công việc: "	+ task.getDescription()
					+ "<br/> \n <br/> \n Trân trọng, <br/> \n"
					+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n" ;
			mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
			List<String> mailList = taskDAO.getEmail(task.getOwnedBy() + "," + task.getVerifyBy() + "," + task.getSecondOwned());
			
			for (int i = 0; i < mailList.size(); i++) {
				String sendTo = mailList.get(i);
				//System.err.println("chuan bi send mail");
				if (sendTo != null && sendTo.length() > 0) {
					//System.err.println("send mail cho " + sendTo);
					helper.setTo(sendTo);
					helper.setSubject("[Tạo mới công việc] - " + task.getTaskName());
					helper.setFrom("IDITaskNotReply");
					mailSender.send(mimeMessage);
					//System.err.println("sent");
				}
			}

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(Model model, @ModelAttribute("taskForm") @Validated TaskForm taskForm,
			final RedirectAttributes redirectAttributes) throws Exception {
		try {

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

				//inject from Login account
				String username = new LoginController().getPrincipal();
			    log.info("Using usename =" + username +" in insert new task");
			   
			    if (username !=null && username.length() >0 ) {
			    	userLogin  =  userRoleDAO.getAUserLoginFull(username);
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
			task.setPlannedFor(taskForm.getPlannedFor());
			task.setTimeSpent(taskForm.getTimeSpent());
			task.setTimeSpentType(taskForm.getTimeSpentType());
			task.setEstimate(taskForm.getEstimate());
			task.setEstimateTimeType(taskForm.getEstimateTimeType());
			task.setDescription(taskForm.getDescription());
			task.setReviewComment(taskForm.getReviewComment());

			Task currentTask = new Task();
			currentTask = taskDAO.getTask(taskForm.getTaskId());

			taskDAO.updateTask(task);

			// Gui mail notification
			// Lấy ds email can gui
			String owner = "Chưa giao cho ai";
			if(taskForm.getOwnedBy() > 0)
				owner = allEmployeesMap().get(taskForm.getOwnedBy());
			
			String updatedBy = "";
			if(userLogin.getUserID() > 0)
				updatedBy = allEmployeesMap().get(userLogin.getUserID());
			
			List<String> mailList = taskDAO.getMailList(taskForm.getTaskId());
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			if (taskForm.getDescription().equalsIgnoreCase(currentTask.getDescription())) {
				//System.err.println("Ko thay doi description");
				//System.err.println(
				//"Ko thay doi description:" + taskForm.getDescription() + "|" + currentTask.getDescription());
				String htmlMsg = "";
				if(taskForm.getReviewComment() != null && taskForm.getReviewComment().length() > 0) {
					htmlMsg ="Dear you, <br/>\n <br/>\n"
							+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
							+ "<b>Người làm: " + owner + "</b><br/>\n"
							+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
							+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n " 
							+ "Công việc thuộc phòng: "	+ taskForm.getArea() + " <br/>\n " 
							+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
							+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n " 
							+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n" 
							+ "<b>Người cập nhật: " + updatedBy
							+ "</b> <e-mail> lúc " + task.getUpdateTS() + " <br/>\n"
							+ "<tabel border='1'>"
							+ "<br/>\n<tr><td>Nhận xét/đánh giá của người giám sát: </td><td>" + taskForm.getReviewComment() 
							+ "</tr><tr><td><br/>Nội dung thảo luận: " + taskForm.getContent()
							+ "<br/>\n<br/>Trân trọng, <br/> \n"
							+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n" 
							+ " </td></tr></table><br/> \n";
				}else {
					htmlMsg = "Dear you, <br/>\n <br/>\n"
							+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n"
							+ "<b>Người làm: " + owner + " </b><br/>\n"
							+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
							+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "							
							+ "Công việc thuộc phòng: "	+ taskForm.getArea() + " <br/>\n "
							+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
							+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n " 
							+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n" 
							+ "<b>Người cập nhật: " + updatedBy
							+ "</b><e-mail> lúc " +  task.getUpdateTS() + " <br/>\n"
							+ "<tabel border='1'>"
							+ "<tr><td>Nội dung thảo luận: " + taskForm.getContent()
							+ "<br/>\n<br/>Trân trọng, <br/> \n"
							+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n" 
							+ "</td></tr></table><br/> \n";
				}

				mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
				// mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"),
				// "UTF-8"),"text/html; charset=UTF-8");
				// mimeMessage.setContent(htmlMsg, "text/html");
			} else {
				//System.err.println("Thay doi description");
				String htmlMsg = "";
				if(taskForm.getReviewComment() != null && taskForm.getReviewComment().length() > 0) {
					htmlMsg = "Dear you, <br/>\n <br/>\n"
							+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n" 
							+ "<b>Người làm: " + owner + " </b><br/>\n"
							+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
							+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "							
							+ "Công việc thuộc phòng: "	+ taskForm.getArea() + " <br/>\n " 
							+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
							+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n " 
							+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n" 
							+ "<b>Người cập nhật: " + updatedBy
							+ "</b><e-mail> lúc " +  task.getUpdateTS() + " <br/>\n"
							+ "<tabel border='1'><tr>" 
							+ "<td>Mô tả công việc: </td><td>" + taskForm.getDescription()
							+ " </td></tr><br/> \n "
							+ "+ <tr><td>Nhận xét/đánh giá của người giám sát: " + taskForm.getReviewComment() 
							+ "</td></tr><br/> \n "
							+ "<tr><td>Nội dung thảo luận: " + taskForm.getContent()
							+ "<br/>\n<br/>\n Trân trọng, <br/> \n"
							+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n" 
							+ "</td></tr></table><br/> \n";
				}else {
					htmlMsg = "Dear you, <br/>\n <br/>\n"
							+ "Bạn nhận được mail này vì bạn có liên quan. <br/>\n" 
							+ "<b>Người làm: " + owner + " </b><br/>\n"
							+ "Mã công việc: " + taskForm.getTaskId() + " <br/>\n " 
							+ "Tên công việc: " + taskForm.getTaskName() + " <br/>\n "							
							+ "Công việc thuộc phòng: "	+ taskForm.getArea() + " <br/>\n " 
							+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
							+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n "
							+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n "
							+ "<b>Người cập nhật: " + updatedBy
							+ "</b> <e-mail> lúc " +  task.getUpdateTS() + " <br/>\n"
							+ "<tabel border='1'>"
							+ "<tr><td>Mô tả công việc: </td><td>" + taskForm.getDescription()
							+ "</td></tr><br/>\n "
							+ "<tr><td>Nội dung thảo luận: " + taskForm.getContent()
							+ "<br/>\n<br/>\n Trân trọng, <br/> \n"
							+ "Được gửi từ Phần mềm Quản lý công việc của IDIGroup <br/> \n" 
							+ "</td></tr></table><br/> \n";
				}					
				
				mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
				// mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"),
				// "UTF-8"),"text/html; charset=UTF-8");
				// mimeMessage.setContent(htmlMsg, "text/html");
			}
			//System.err.println("chuan bi send mail " + mailList.size());
			for (int i = 0; i < mailList.size(); i++) {
				String sendTo = mailList.get(i);
				//System.err.println("chuan bi send mail");
				if (sendTo != null && sendTo.length() > 0) {
					//System.err.println("send mail cho " + sendTo);
					helper.setTo(sendTo);
					helper.setSubject("[Mã công việc: " + taskForm.getTaskId() + "] - " + taskForm.getTaskName());
					helper.setFrom("IDITaskNotReply");
					mailSender.send(mimeMessage);
					//System.err.println("sent");
				}
			}
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin công việc thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}

		return "redirect:/editTask?tab=1&taskId=" + taskForm.getTaskId();
	}

	@RequestMapping("/addNewTask")
	public String addNewTask(Model model) throws Exception {
		Task task = new Task();
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
			@RequestParam(required=false, value="taskIds") String taskIds, 
			@ModelAttribute("taskForm") @Validated TaskForm taskForm) throws Exception {
		//System.err.println("current tab "+ tab);
		Task task = new Task();
		//TaskForm taskForm = new TaskForm();

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
			
			int createBy = task.getCreatedBy();
			taskForm.setCreatedBy(createBy);			
			Map<Integer, String> employeeMap = this.allEmployeesMap();			
			if(createBy > 0) {
					taskForm.setCreatedByName(employeeMap.get(createBy));
			}
			//taskForm.setCommentedByName(commentedByName);
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
				//System.err.println(task.getRelated());
				listTaskRelated = taskDAO.getTasksRelated(task.getRelated());
				model.addAttribute("tasksRelated", listTaskRelated);
			} else {
				model.addAttribute("tasksRelated", null);
			}
			
			if(taskIds != null) {
				System.out.println("cv da chon: " + taskIds);
				String relatedUpdated = null;
				if(task.getRelated() != null && task.getRelated().length() > 0){
					//da co cv lien quan + then 
					relatedUpdated = task.getRelated() + "," + taskIds;
				}else {
					//hien tai chua co cv lien quan + them
					relatedUpdated = taskIds;
				}
				//Update task related
				//System.out.println("task related updated: " + relatedUpdated);
				taskDAO.updateRelated(relatedUpdated, taskId);				

				listTaskRelated = taskDAO.getTasksRelated(task.getRelated());
				model.addAttribute("tasksRelated", listTaskRelated);

				//Reset data for task related
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
				if(task.getRelated()!= null )
					list = taskDAO.getTasksBySearchForRelated(taskForm.getSearchValue(), task.getRelated() + "," + taskId);
				else
					list = taskDAO.getTasksBySearchForRelated(taskForm.getSearchValue(), String.valueOf(taskId));
			}
			model.addAttribute("taskForm", taskForm);
			if (list != null && list.size() < 1)// && search)
				model.addAttribute("message",
						"Không có công việc nào khớp với thông tin: '" + taskForm.getSearchValue() + "'");
			model.addAttribute("tasksFound", list);
			model.addAttribute("formTitle",
					"Quản lý danh sách công việc liên quan đến công việc mã " + taskForm.getTaskId());
			
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
				System.err.println("345");
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
	public String prepareReport(Model model) throws Exception {
		ReportForm taskReportForm = new ReportForm();
		model.addAttribute("formTitle", "Lựa chọn thông tin báo cáo công việc");

		// get list department
		Map<String, String> departmentMap = this.listDepartments();
		model.addAttribute("departmentMap", departmentMap);

		// get list employee id
		model.addAttribute("employeesList", employeesMap("all"));

		model.addAttribute("taskReportForm", taskReportForm);

		return "prepareReport";
	}
	
	@RequestMapping("/generateTaskReport")
	public String generateTaskReport(Model model, @ModelAttribute("taskReportForm") @Validated ReportForm taskReportForm) {
		List<Task> list = null;
		list = taskDAO.getTasksForReport(taskReportForm);
		model.addAttribute("reportForm", taskReportForm);
		model.addAttribute("tasks", list);
		model.addAttribute("formTitle", "Thông tin báo cáo công việc");
		return "taskReport";
	}
		
	@RequestMapping("/sendReportForm")
	public String sendReportForm(Model model,  @ModelAttribute("fDate") String fDate, @ModelAttribute("tDate") String tDate,
			@ModelAttribute("eName") String eName, @ModelAttribute("dept") String dept, @ModelAttribute("eId") int eId) throws Exception {
		SendReportForm sendReportForm = new SendReportForm();
		if(eId > 0)
			if(dept != null && !dept.equalsIgnoreCase("all"))
				sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của " + eName + " phòng " + dept);
			else
				sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của " + eName);
		else
			if(dept != null && !dept.equalsIgnoreCase("all"))
				sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của phòng " + dept);
			else
				sendReportForm.setFileName("BCCV từ ngày " + fDate + " đến ngày " + tDate + " của tất cả các phòng ban");
		
		sendReportForm.setSubject(sendReportForm.getFileName() + " --> Gửi từ PM Quản lý công việc");
		List<Task> list = null;
		ReportForm taskReportForm = new ReportForm();
		taskReportForm.setFromDate(fDate);
		taskReportForm.setToDate(tDate);
		taskReportForm.setEmployeeId(eId);
		taskReportForm.setDepartment(dept);
		list = taskDAO.getTasksForReport(taskReportForm);
		model.addAttribute("tasks", list);
		model.addAttribute("formTitle", "Gửi báo cáo công việc");
		model.addAttribute("sendReportForm", sendReportForm);

		return "sendReportForm";
	}
	
	@RequestMapping(value ="/sendReport")
	public String sendReport(Model model, @ModelAttribute("sendReportForm") @Validated SendReportForm sendReportForm, @RequestParam(required=false, value="formTitle") String formTitle) throws Exception {
		log.info("sending report");
		if(formTitle == null) {
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			String path = properties.getProperty("REPORT_PATH");
			
			File file = new File(path + sendReportForm.getFileName() + ".pdf");
		//	log.info("sending report 111111113: " + path +"|" + sendReportForm.getSubject()+"|"+sendReportForm.getFileName());
			if(file.exists()) {
				
				mimeMessage.setFrom("IDITask-NotReply");
				helper.setSubject(sendReportForm.getSubject());
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
				while(st.hasMoreTokens()) {
					
					String sendTo = st.nextToken(";");
					if(sendTo != null && sendTo.length() > 0 && sendTo.contains("@") && sendTo.contains(".com")) {
						log.info("send report cho " + sendTo);
						helper.setTo(sendTo);			
						
						mailSender.send(mimeMessage);
					//System.err.println("sent");
					}			
				}
				//model.addAttribute("isReload","Yes");
				model.addAttribute("formTitle", "Gửi báo cáo công việc");
				return "redirect:/sendReport";
			}else {
				model.addAttribute("formTitle", "Vui lòng export file trước khi gửi báo cáo công việc");
			}
		}else {
			log.info("try to sending report again...");
			model.addAttribute("formTitle", "Gửi báo cáo công việc");
		}	
		return "sentReport";
	}
	
	@RequestMapping("/exportToPDF")
	public String getPDF(Model model, @ModelAttribute("fDate") String fDate, @ModelAttribute("tDate") String tDate,
			@ModelAttribute("eName") String eName, @ModelAttribute("dept") String dept, @ModelAttribute("eId") int eId) throws Exception {
		//System.err.println("export to pdf");
		
		Document document = new Document();
		String path = properties.getProperty("REPORT_PATH");
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String fileName = "";
		if(eId > 0 && !dept.equalsIgnoreCase("all"))
			fileName = "BCCV từ ngày "+ fDate + " đến ngày " + tDate 
			+ " của " + eName + " phòng " + dept + ".pdf";
		else if (eId < 1 && !dept.equalsIgnoreCase("all"))
			fileName = "BCCV từ ngày "+ fDate + " đến ngày " + tDate 
			+ " của phòng " + dept + ".pdf";
		else if (eId > 0 && dept.equalsIgnoreCase("all"))
			fileName = "BCCV từ ngày "+ fDate + " đến ngày " + tDate 
			+ " của " + eName + ".pdf";
		else
			fileName = "BCCV từ ngày "+ fDate + " đến ngày " + tDate + " của tất cả các phòng ban.pdf";
		
		PdfWriter.getInstance(document, new FileOutputStream(dir + "/" + fileName));
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	    Font font = new Font(bf,14); 
		document.open();
		//Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, "UTF-8");
		//Chunk chunk = new Chunk(fileName, font);		 
		//document.add(chunk);
		
		PdfPTable table = new PdfPTable(6);
		addTableHeader(table, font);
		List<Task> list = null;
        ReportForm taskReportForm = new ReportForm();
        taskReportForm.setFromDate(fDate);
        taskReportForm.setToDate(tDate);
        taskReportForm.setDepartment(dept);
        taskReportForm.setEmployeeId(eId);
        taskReportForm.setEmployeeName(eName);
		list = taskDAO.getTasksForReport(taskReportForm);
		
		addRows(table, list);
		//addCustomRows(table);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		document.add(new Paragraph("                     " + fileName.substring(0, fileName.length() - 4), font));		
		document.add(new Paragraph("                     ", font));
		document.add(table);		
		document.add(new Paragraph("               Ngày tạo báo cáo " + currentDate, font));
		
		document.close();
		model.addAttribute("reportForm", taskReportForm);
		model.addAttribute("path", path);
		model.addAttribute("formTitle", "Export báo cáo công việc");
		return "taskExport";
	}

	private void addTableHeader(PdfPTable table, Font font) throws Exception {//throws DocumentException, IOException {
	    Stream.of("Mã việc", "Tên việc", "Người làm", "Trạng thái", "Ngày phải xong", "Nhận xét/đánh giá")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setPhrase(new Phrase(columnTitle, font));	       
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table, List<Task> tasks) throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	    Font font = new Font(bf,12); 
	 
	    //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");        
        
		for(int i = 0; i < tasks.size(); i++) {
			Task task = new Task();
			task = (Task)tasks.get(i);
			
			//String DateToStr = "";
			//if(task.getDueDate() !=null)
			//	DateToStr = format.format(task.getDueDate());

			table.addCell(String.valueOf(task.getTaskId()));
		    table.addCell(new Paragraph(task.getTaskName(), font));
		    table.addCell(new Paragraph(task.getOwnerName(), font));
		    table.addCell(new Paragraph(task.getStatus(), font));
		   // table.addCell(String.valueOf(task.getUpdateTS()));
		    table.addCell(task.getDueDate());	
		    table.addCell(new Paragraph(task.getReviewComment(), font));
		}	        
	}
	
	private List<EmployeeInfo> employeesForSub(String subscriber) throws Exception {
		List<EmployeeInfo> list = null;
		
		list = employeeDAO.getEmployeesForSub(subscriber);
		//System.err.println("list available for subs: "+ list.size());
		return list;
	}

	private List<EmployeeInfo> employeesSub(String subscriber) throws Exception {
		List<EmployeeInfo> list = null;
		list = employeeDAO.getEmployeesSub(subscriber);
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
			employeeMap.put(id.toString(),
					"Mã NV " + id + ", " + employee.getFullName());
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

	// For Ajax
	@RequestMapping("/selection")
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("department") String department) throws Exception {
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
	
/*	@RequestMapping("/lookingTask")
	public @ResponseBody List<Task> getTaskForAddingRelated(@RequestParam("relatedAdding") String relatedAdding) throws Exception {
		System.err.println("AJax");
		List<Task> list = null;
		if (relatedAdding != null && relatedAdding.length() > 0)
			list = taskDAO.getTasksBySearch(relatedAdding);

		return list;
	}*/
}
