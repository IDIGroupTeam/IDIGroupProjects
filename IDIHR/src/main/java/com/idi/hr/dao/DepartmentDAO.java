package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.Department;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.DepartmentMapper;

public class DepartmentDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(DepartmentDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public DepartmentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get departments from DB
	 * 
	 * @return List of department
	 * @throws Exception
	 */
	public List<Department> getDepartments() {

		String sql = hr.getProperty("GET_DEPARTMENTS").toString();
		log.info("GET_DEPARTMENTS query: " + sql);
		DepartmentMapper mapper = new DepartmentMapper();

		List<Department> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get department by departmentId
	 * 
	 * @param departmentId
	 * @return department object
	 */
	public Department getDepartment(String departmentId) {

		String sql = hr.get("GET_DEPARTMENT").toString();
		log.info("GET_DEPARTMENT query: " + sql);
		Object[] params = new Object[] { departmentId };

		DepartmentMapper mapper = new DepartmentMapper();

		Department department = jdbcTmpl.queryForObject(sql, params, mapper);

		return department;

	}

	/**
	 * Insert a department into database
	 * 
	 * @param department
	 */
	public void insertDepartment(Department department) throws Exception {
		try {

			log.info("Insert new department ....");
			String sql = hr.getProperty("INSERT_DEPARTMENT").toString();
			log.info("INSERT_DEPARTMENT query: " + sql);
			Object[] params = new Object[] { department.getDepartmentId(), department.getDepartmentName(),
					department.getDesc() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a department into database
	 * 
	 * @param department
	 */
	public void updateDepartment(Department department) throws Exception {
		try {

			log.info("Update department " + department.getDepartmentId() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_DEPARTMENT").toString();
			log.info("UPDATE_DEPARTMENT query: " + sql);
			Object[] params = new Object[] { department.getDepartmentName(), department.getDesc(),
					department.getDepartmentId() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

}
