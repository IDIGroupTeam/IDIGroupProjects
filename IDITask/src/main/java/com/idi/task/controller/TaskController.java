package com.idi.task.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.idi.task.dao.TaskDAO;
import com.idi.task.form.TaskForm;

import javax.mail.internet.MimeMessage;

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
	private JavaMailSender mailSender;

	@RequestMapping(value = { "/" })
	public String listTasks(Model model, @ModelAttribute("taskForm") TaskForm form) {
		try {
			List<Task> list = null;

			// Paging:
			// Number records of a Page: Default: 25
			// Page Index: Default: 1
			// Total records
			// Total of page
			if (form.getNumberRecordsOfPage() == 0) {
				form.setNumberRecordsOfPage(5); //set 5 for testing
			}

			if (form.getPageIndex() == 0) {
				form.setPageIndex(1);
			}

			boolean search = false;
			if (form.getSearchValue() != null && form.getSearchValue().length() > 0) {
				log.info("Searching for: " + form.getSearchValue());
				search = true;
				list = taskDAO.getTasksBySearch(form.getSearchValue());
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
					for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getPageIndex()	* form.getNumberRecordsOfPage(); i++) {
						Task task = new Task();
						task = list.get(i);
						listTaskForPage.add(task);
					}
				}
			} else if (form.getPageIndex() == totalPages) {
				for (int i = ((form.getPageIndex() - 1) * form.getNumberRecordsOfPage()); i < form.getTotalRecords(); i++) {
					Task task = new Task();
					task = list.get(i);
					listTaskForPage.add(task);
				}
			}

			model.addAttribute("taskForm", form);
			if (list != null && list.size() < 1 && !search)
				model.addAttribute("message", "Chưa có công việc nào được tạo");
			else if (list != null && list.size() < 1 && search)
				model.addAttribute("message", "Không có công việc nào khớp với thông tin: '" + form.getSearchValue() + "'");
			
			model.addAttribute("tasks", listTaskForPage);
			model.addAttribute("formTitle", "Danh sách công việc");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTask";
	}

	@RequestMapping(value = "/updateSubscriber", method = RequestMethod.POST)
	public String Subscriber(Model model, @ModelAttribute("taskForm") TaskForm taskForm) {
		try {
			System.err.println(taskForm.getTaskId());			
			model.addAttribute("formTitle", "Quản lý danh sách người liên quan");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "updateSubscriber";
	}
	
/*	@RequestMapping(value = "/searchTask")
	public String searchTask(Model model, @RequestParam("searchValue") String searchValue,
			@ModelAttribute("taskForm") TaskForm taskForm) {
		try {
			List<Task> list = null;
			// TaskForm taskForm = new TaskForm();
			model.addAttribute("taskForm", taskForm);
			if (searchValue != null && searchValue.length() > 0) {
				list = taskDAO.getTasksBySearch(searchValue);
			} else {
				list = taskDAO.getTasks();
			}
			if (list.size() < 1)
				model.addAttribute("message", "Không có công việc nào khớp với thông tin: '" + searchValue + "'");
			model.addAttribute("tasks", list);
			model.addAttribute("formTitle", "Danh sách công việc được tìm");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTask";
	}*/

	@RequestMapping(value = "/insertNewTask", method = RequestMethod.POST)
	public String insertNewTask(Model model, @ModelAttribute("taskForm") Task task,
			final RedirectAttributes redirectAttributes) {
		try {
			//System.err.println("insert new task");
			taskDAO.insertTask(task);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin công việc thành công!");
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			String htmlMsg = "Công việc thuộc phòng: " + task.getArea() + " <br/>\n "
					+ "Trạng thái: Tạo mới <br/>\n "
					+ "Kế hoạch cho tháng: " + task.getPlannedFor() + " <br/>\n "
					+ "Độ ưu tiên: " + task.getPriority() + "<br/> <br/> \n"
					+ "<b>Người tạo " +  task.getCreatedBy() +" </b> <e-mail> lúc " + Calendar.getInstance().getTime() +" <br/>\n"
					+ "Mô tả nội dung công việc: " + task.getDescription();
			mimeMessage.setContent(htmlMsg,"text/html; charset=UTF-8");
			helper.setTo("truongnv.idigroup@gmail.com");
			helper.setSubject("[Tạo mới công việc] - "+ task.getTaskName());
			helper.setFrom("IDITaskNotReply");
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(Model model, @ModelAttribute("taskForm") @Validated TaskForm taskForm,
			final RedirectAttributes redirectAttributes) {
		try {

			// Xu ly cho task comment
			TaskComment taskComment = new TaskComment();
			int currentMaxCommentIndex = 0;
			if (taskForm.getContent() != null && taskForm.getContent().length() > 0) {
				currentMaxCommentIndex = taskDAO.getMaxCommentIndex(taskForm.getTaskId());
				currentMaxCommentIndex = currentMaxCommentIndex + 1;
				taskComment.setCommentIndex(currentMaxCommentIndex);
				taskComment.setTaskId(taskForm.getTaskId());
				taskComment.setCommentedBy(taskForm.getCommentedBy());
				taskComment.setContent(taskForm.getContent());

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
			task.setUpdateTS(taskForm.getUpdateTS());
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
			
			Task currentTask = new Task(); 
			currentTask = taskDAO.getTask(taskForm.getTaskId());

			taskDAO.updateTask(task);
			
			//Gui mail notification			
			//Lấy ds email can gui
			List<String> mailList = taskDAO.getMailList(taskForm.getTaskId());
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			if(taskForm.getDescription().equalsIgnoreCase(currentTask.getDescription())) {
				System.err.println("Ko thay doi description");
				System.err.println("Ko thay doi description:"+ taskForm.getDescription() +"|"+currentTask.getDescription());
				String htmlMsg = "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
						+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n "
						+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
						+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n "
						+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
						+ "<b>Người cập nhật " +  taskForm.getCommentedBy() +" </b> <e-mail> lúc " + Calendar.getInstance().getTime() +" <br/>\n"
						+ "<tabel border='1'><tr>"
						+ "<td>Nội dung thảo luận: " + taskForm.getContent() + " </td></tr></table><br/> \n" ;
				mimeMessage.setContent(htmlMsg,"text/html; charset=UTF-8");
				//mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"), "UTF-8"),"text/html; charset=UTF-8");
				//mimeMessage.setContent(htmlMsg, "text/html");
			}else {
				System.err.println("Thay doi description");
				String htmlMsg = "Mã công việc: " + taskForm.getTaskId() + " <br/>\n "
						+ "Công việc thuộc phòng: " + taskForm.getArea() + " <br/>\n "
						+ "Trạng thái: " + taskForm.getStatus() + " <br/>\n "
						+ "Kế hoạch cho tháng: " + taskForm.getPlannedFor() + " <br/>\n "
						+ "Độ ưu tiên: " + taskForm.getPriority() + "<br/> \n"
						+ "<b>Người cập nhật " +  taskForm.getCommentedBy() +" </b> <e-mail> lúc " + Calendar.getInstance().getTime() +" <br/>\n"
						+ "<tabel border='1'><tr>"
						+ "<td>Mô tả công việc: </td><td>" + taskForm.getDescription() + " </td></tr><br/> \n"
						+ "<tr><td>Nội dung thảo luận: " + taskForm.getContent() + " </td></tr></table><br/> \n" ;
				mimeMessage.setContent(htmlMsg,"text/html; charset=UTF-8");
				//mimeMessage.setContent(new String(htmlMsg.getBytes("UTF-8"), "UTF-8"),"text/html; charset=UTF-8");
				//mimeMessage.setContent(htmlMsg, "text/html");
			}
			System.err.println("chuan bi send mail " + mailList.size());
			for(int i = 0; i < mailList.size(); i++) {
				String sendTo = mailList.get(i);
				System.err.println("chuan bi send mail");
				if(sendTo != null && sendTo.length() > 0) {
					System.err.println("send mail cho " + sendTo);
					helper.setTo(sendTo);
					helper.setSubject("[Mã công việc: " + taskForm.getTaskId() +"] - "+ taskForm.getTaskName());
					helper.setFrom("IDITaskNotReply");
					mailSender.send(mimeMessage);
					System.err.println("sent");
				}				
			}			
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin công việc thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}

		return "redirect:/editTask?taskId=" + taskForm.getTaskId();
	}

	@RequestMapping("/addNewTask")
	public String addNewTask(Model model) {
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
	public String editTask(Model model, @RequestParam("taskId") int taskId) {
		Task task = new Task();
		TaskForm taskForm = new TaskForm();

		if (taskId > 0) {

			// get list department
			Map<String, String> departmentMap = this.listDepartments();
			model.addAttribute("departmentMap", departmentMap);

			task = taskDAO.getTask(taskId);
			// get info from task bean put to task form
			taskForm.setTaskId(task.getTaskId());
			taskForm.setTaskName(task.getTaskName());
			taskForm.setCreatedBy(task.getCreatedBy());
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

			// For comment
			model.addAttribute("listComment", taskDAO.getTaskComments(taskId));

			// get list employee id
			if (task.getArea() != null && task.getArea().length() > 0)
				model.addAttribute("employeesList", employees(task.getArea()));
			else
				model.addAttribute("employeesList", employees("all"));

			// System.err.println(task.getTaskName());
			model.addAttribute("formTitle", "Sửa thông tin công việc");
			model.addAttribute("taskForm", taskForm);

		} else {
			return "redirect:/";
		}

		return "updateTask";
	}

	/*
	 * private String taskForm(Model model, Task task) {
	 * 
	 * // get list department Map<String, String> departmentMap =
	 * this.listDepartments(); model.addAttribute("departmentMap", departmentMap);
	 * 
	 * String actionform = ""; if (task.getTaskName() != null) {
	 * model.addAttribute("formTitle", "Sửa thông tin công việc"); // get list
	 * employee id //Map<String, String> employeeMap = this.employees("all");
	 * model.addAttribute("employeesList", employees(task.getArea())); actionform =
	 * "updateTask"; } else { model.addAttribute("formTitle",
	 * "Thêm mới thông tin công việc"); // get list employee id //Map<String,
	 * String> employeeMap = this.employees("all");
	 * model.addAttribute("employeesList", employees("all")); actionform =
	 * "addNewTask"; }
	 * 
	 * model.addAttribute("taskForm", task);
	 * 
	 * return actionform; }
	 */

	/*
	 * private Map<Integer, String> employees() { Map<Integer, String> employeeMap =
	 * new LinkedHashMap<Integer, String>(); try { List<EmployeeInfo> list = null;
	 * list = employeeDAO.getEmployees(); EmployeeInfo employee = new
	 * EmployeeInfo(); for (int i = 0; i < list.size(); i++) { employee =
	 * (EmployeeInfo) list.get(i); Integer id = employee.getEmployeeId();
	 * employeeMap.put(id, "Mã NV " + id + ", " + employee.getFullName() + ", " +
	 * employee.getJobTitle()); }
	 * 
	 * } catch (Exception e) { log.error(e, e); e.printStackTrace(); } return
	 * employeeMap; }
	 */

	private List<EmployeeInfo> employees(String department) {
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}

	private Map<String, String> listDepartments() {
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
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("area") String department) {
		System.err.println("AJax");
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all"))
			list = employeeDAO.getEmployeesByDepartment(department);
		else
			list = employeeDAO.getEmployees();

		return list;
	}
}