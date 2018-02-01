package com.idi.task.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.task.bean.Task;
import com.idi.task.bean.TaskComment;
import com.idi.task.common.PropertiesManager;
import com.idi.task.mapper.TaskCommentMapper;
import com.idi.task.mapper.TaskMapper;

public class TaskDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(TaskDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public TaskDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager properties = new PropertiesManager("task.properties");

	/**
	 * Get list Task from DB
	 * 
	 * @return List of Task
	 * @throws Exception
	 */
	public List<Task> getTasks() {

		String sql = properties.getProperty("GET_TASKS").toString();
		log.info("GET_TASKS query: " + sql);
		TaskMapper mapper = new TaskMapper();

		List<Task> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get task by task id
	 * 
	 * @param task id
	 * @return Task object
	 */
	public Task getTask(int taskId) {

		String sql = properties.get("GET_TASK").toString();
		log.info("GET_TASK query: " + sql);
		Object[] params = new Object[] { taskId };

		TaskMapper mapper = new TaskMapper();

		Task task = jdbcTmpl.queryForObject(sql, params, mapper);
		return task;

	}

	/**
	 * Insert a task into database
	 * 
	 * @param Task
	 */
	public void insertTask(Task task) throws Exception {
		try {

			log.info("Thêm mới thông tin task ....");
			String sql = properties.getProperty("INSERT_TASK").toString();
			log.info("INSERT_TASK query: " + sql);
			System.err.println(task.getTaskName() +"|"+ task.getCreatedBy() +"|"+ task.getOwnedBy() +"|"+ 
					task.getDueDate() +"|"+ 
					task.getType() +"|"+ task.getArea() +"|"+ task.getPriority() +"|"+ task.getPlannedFor() +"|"+
					task.getEstimate() +"|"+ task.getDescription());
			Object[] params = new Object[] { task.getTaskName(), task.getCreatedBy(), task.getOwnedBy(), 
					task.getSecondOwned(),  task.getVerifyBy(),
					task.getDueDate(), task.getType(), task.getArea(), task.getPriority(), task.getPlannedFor(),
					task.getEstimate(), task.getEstimateTimeType(), task.getDescription()};
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a task into database
	 * 
	 * @param task
	 */
	public void updateTask(Task task) throws Exception {
		try {

			log.info("Cập nhật thông tin công viêc");
			// update
			String sql = properties.getProperty("UPDATE_TASK").toString();
			log.info("UPDATE_TASK query: " + sql);
			//se lam sau khi xong fan quyen
			//if(task.getStatus().equalsIgnoreCase("Xong")) {
			//	task.setResolutionDate("");
			//	task.setResolvedBy("");
			//}
			
			System.err.println(task.getTaskName() +"|"+ task.getCreatedBy() +"|"+ task.getOwnedBy() +"|"+ 
					task.getDueDate() +"|"+ task.getSecondOwned() + "|" + task.getVerifyBy() +"|"+ 
					task.getType() +"|"+ task.getArea() +"|"+ task.getPriority() +"|"+ task.getPlannedFor() +"|"+
					task.getTimeSpent() +"|"+ task.getEstimate() +"|"+ task.getDescription() +"|"+ task.getTaskId());
			
			Object[] params = new Object[] { task.getTaskName(), task.getOwnedBy(), task.getSecondOwned(),
					task.getVerifyBy(), task.getUpdateId(),
					task.getResolvedBy(), task.getDueDate(), task.getResolutionDate(),
					task.getType(), task.getArea(), task.getPriority(), task.getStatus(), task.getPlannedFor(),  
					task.getTimeSpent(), task.getTimeSpentType(), task.getEstimate(), task.getEstimateTimeType(), task.getDescription(), task.getTaskId() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	//------------------- Task comment ------------------//
	
	/**
	 * Get list Task comment from DB
	 * 
	 * @return List of Task
	 * @throws Exception
	 */
	public List<TaskComment> getTaskComments(int taskId) {

		String sql = properties.getProperty("GET_TASK_COMMENTS").toString();
		log.info("GET_TASK_COMMENTS query: " + sql);
		TaskCommentMapper mapper = new TaskCommentMapper();
		Object[] params = new Object[] {taskId};
		List<TaskComment> list = jdbcTmpl.query(sql,params, mapper);
		return list;
	}
	
	/**
	 * get max comment index currenly 
	 * @param taskId
	 * @return number
	 */
	public int getMaxCommentIndex(int taskId) {
		int maxNunber = 0;
		try {			
			String sql = properties.get("GET_MAX_COMMENT_INDEX").toString();
			log.info("GET_MAX_COMMENT_INDEX query: " + sql);
			Object[] params = new Object[] { taskId };	
			maxNunber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		}catch(Exception e) {
			//lan dau se va day vi tra ve null
			System.err.println("lan dau se va day vi tra ve null");
			return 0;
		}
		return maxNunber;
	}
	
	/**
	 * Insert a task comment into database
	 * 
	 * @param taskComment
	 */
	public void insertTaskComment(TaskComment taskComment) throws Exception {
		try {
			log.info("Thêm mới thông tin task comment....");
			String sql = properties.getProperty("INSERT_TASK_COMMENT").toString();
			log.info("INSERT_TASK_COMMENT query: " + sql);
			Object[] params = new Object[] { taskComment.getCommentIndex(), taskComment.getTaskId(),
					taskComment.getCommentedBy(), taskComment.getContent()};
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}
}
