package com.idi.task.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.task.bean.EmployeeInfo;
import com.idi.task.common.PropertiesManager;
import com.idi.task.mapper.EmployeeMapper;

public class EmployeeDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(EmployeeDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public EmployeeDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager properties = new PropertiesManager("task.properties");

	public EmployeeDAO() {}
	
	/**
	 * Get employees from DB
	 * 
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployees() {

		String sql = properties.getProperty("GET_EMPLOYEES").toString();
		log.info("GET_EMPLOYEES query: " + sql);
		
		EmployeeMapper mapper = new EmployeeMapper();

		List<EmployeeInfo> list = jdbcTmpl.query(sql, mapper);

		return list;

	}	
	
	/**
	 * Get all employees from DB
	 * 
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getAllEmployees() {

		String sql = properties.getProperty("GET_ALL_EMPLOYEES").toString();
		log.info("GET_EMPLOYEES query: " + sql);
		
		EmployeeMapper mapper = new EmployeeMapper();

		List<EmployeeInfo> list = jdbcTmpl.query(sql, mapper);

		return list;

	}	
	
	/**
	 * Get employees for chose subscriber 
	 * @param taskId
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployeesForSub(String subscriber) {

		String sql = properties.getProperty("GET_EMPLOYEES_FOR_SUB").toString();
		if(subscriber != null && subscriber.length() > 0)
			sql = sql + " AND E.EMPLOYEE_ID NOT IN (" + subscriber + ")";
		log.info("GET_EMPLOYEES_FOR_SUB query: " + sql);
		
		EmployeeMapper mapper = new EmployeeMapper();
		List<EmployeeInfo> list = jdbcTmpl.query(sql, mapper);

		return list;
	}
	
	/**
	 * Get employees subscriber
	 * @param taskId
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployeesSub(String subscriber) {

		String sql = properties.getProperty("GET_TASK_SUBSCRIBER").toString();
		if(subscriber != null && subscriber.length() > 0)
			sql = sql + " AND E.EMPLOYEE_ID IN (" + subscriber + ")";
		log.info("GET_TASK_SUBSCRIBER query: " + sql);
		
		EmployeeMapper mapper = new EmployeeMapper();
		List<EmployeeInfo> list = jdbcTmpl.query(sql, mapper);

		return list;
	}
	
	/**
	 * Get employees from DB
	 * @param department
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployeesByDepartment(String department) {

		String sql = properties.getProperty("GET_EMPLOYEES_BY_DEPARTMENT").toString();
		log.info("GET_EMPLOYEES_BY_DEPARTMENT query: " + sql);
		Object[] params = new Object[] {department};
		EmployeeMapper mapper = new EmployeeMapper();

		List<EmployeeInfo> list = jdbcTmpl.query(sql, params, mapper);

		return list;
	}
	
	/**
	 * get the list over of all the tasks from DB
	 * @return List of employee 
	 */
	public List<EmployeeInfo> getListOwner(){
		String sql = properties.getProperty("GET_OWNER_OF_TASKS").toString();
		log.info("GET_OWNER_OF_TASKS query: " + sql);
		EmployeeMapper mapper = new EmployeeMapper();
		List<EmployeeInfo> list = jdbcTmpl.query(sql, mapper);

		return list;
	}	

}
