package com.idi.task.controller;

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

@Controller
public class TaskController {
	private static final Logger log = Logger.getLogger(TaskController.class.getName());

	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private DepartmentDAO departmentDAO;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String ListTasks(Model model) {
		try {
			List<Task> list = taskDAO.getTasks();
			model.addAttribute("tasks", list);
			model.addAttribute("formTitle", "Danh sách công việc");
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return "listTask";
	}
	
	@RequestMapping(value = "/insertNewTask", method = RequestMethod.POST)
	public String insertNewTask(Model model, @ModelAttribute("taskForm") Task task,
			final RedirectAttributes redirectAttributes) {
		try {
			System.err.println("insert new task");
			taskDAO.insertTask(task);
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Thêm thông tin công việc thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public String updateTask(Model model, @ModelAttribute("taskForm") @Validated TaskForm taskForm,
			final RedirectAttributes redirectAttributes) {
		try {
						
			//Xu ly cho task comment 
			TaskComment taskComment = new TaskComment();
			int currentMaxCommentIndex = 0;
			if(taskForm.getContent() != null && taskForm.getContent().length() > 0) {
				currentMaxCommentIndex = taskDAO.getMaxCommentIndex(taskForm.getTaskId());			
				currentMaxCommentIndex = currentMaxCommentIndex + 1;
				taskComment.setCommentIndex(currentMaxCommentIndex);
				taskComment.setTaskId(taskForm.getTaskId());
				taskComment.setCommentedBy(taskForm.getCommentedBy());
				taskComment.setContent(taskForm.getContent());
				
				taskDAO.insertTaskComment(taskComment);
			}			
			
			//Xu ly cho task
			Task task = new Task();
			//get info from taskForm then put to task bean
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
			task.setDescription(taskForm.getDescription());
			
			taskDAO.updateTask(task);
			
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin công việc thành công!");

		} catch (Exception e) {
			log.error(e, e);
		}
		
		return "redirect:/editTask?taskId=" +taskForm.getTaskId();
	}

	@RequestMapping("/addNewTask")
	public String addNewTask(Model model) {		
		Task task = new Task();
		model.addAttribute("formTitle", "Thêm mới thông tin công việc");
		// get list department
		Map<String, String> departmentMap = this.listDepartments();
		model.addAttribute("departmentMap", departmentMap);	
				
		// get list employee id
		//Map<String, String> employeeMap = this.employees("all");
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
			//get info from task bean put to task form
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
			taskForm.setDescription(task.getDescription());
			
			//For comment
			model.addAttribute("listComment", taskDAO.getTaskComments(taskId));
			
			// get list employee id
			if(task.getArea() != null && task.getArea().length() > 0)
				model.addAttribute("employeesList", employees(task.getArea()));
			else
				model.addAttribute("employeesList", employees("all"));
			
			//System.err.println(task.getTaskName());
			model.addAttribute("formTitle", "Sửa thông tin công việc");
			model.addAttribute("taskForm", taskForm);
			
		}else {
			return "redirect:/";
		}

		return "updateTask";
	}


/*	private String taskForm(Model model, Task task) {
				
		// get list department
		Map<String, String> departmentMap = this.listDepartments();
		model.addAttribute("departmentMap", departmentMap);	
		
		String actionform = "";
		if (task.getTaskName() != null) {
			model.addAttribute("formTitle", "Sửa thông tin công việc");
			// get list employee id
			//Map<String, String> employeeMap = this.employees("all");
			model.addAttribute("employeesList", employees(task.getArea()));
			actionform = "updateTask";
		} else {
			model.addAttribute("formTitle", "Thêm mới thông tin công việc");
			// get list employee id
			//Map<String, String> employeeMap = this.employees("all");
			model.addAttribute("employeesList", employees("all"));
			actionform = "addNewTask";
		}
		
		model.addAttribute("taskForm", task);
		
		return actionform;
	}*/

	
/*	private Map<Integer, String> employees() {
		Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
		try {
			List<EmployeeInfo> list = null;
			list = employeeDAO.getEmployees();
			EmployeeInfo employee = new EmployeeInfo();
			for (int i = 0; i < list.size(); i++) {
				employee = (EmployeeInfo) list.get(i);
				Integer id = employee.getEmployeeId();
				employeeMap.put(id,	"Mã NV " + id + ", " + employee.getFullName() + ", " + employee.getJobTitle());
			}

		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return employeeMap;
	}*/
	
	private List<EmployeeInfo> employees(String department){
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
	
	//For Ajax
	@RequestMapping("/selection")
	public @ResponseBody List<EmployeeInfo> employeesByDepartment(@RequestParam("area") String department){
		System.err.println("AJax");
		List<EmployeeInfo> list = null;
		if (!department.equalsIgnoreCase("all")) 
			list = employeeDAO.getEmployeesByDepartment(department);		
		else
			list = employeeDAO.getEmployees();		
		
		return list;
	}
}
