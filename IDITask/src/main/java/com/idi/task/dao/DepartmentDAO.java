package com.idi.task.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.task.bean.Department;
import com.idi.task.common.PropertiesManager;
import com.idi.task.mapper.DepartmentMapper;

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

	PropertiesManager properties = new PropertiesManager("task.properties");

	/**
	 * Get departments from DB
	 * 
	 * @return List of department
	 * @throws Exception
	 */
	public List<Department> getDepartments() {

		String sql = properties.getProperty("GET_DEPARTMENTS").toString();
		log.info("GET_DEPARTMENTS query: " + sql);
		DepartmentMapper mapper = new DepartmentMapper();

		List<Department> list = jdbcTmpl.query(sql, mapper);
		return list;

	}
}