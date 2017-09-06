package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.JobTitle;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.JobTitleMapper;

public class JobTitleDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(JobTitleDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public JobTitleDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get job titles from DB
	 * 
	 * @return List of title
	 * @throws Exception
	 */
	public List<JobTitle> getJobTitles() {

		String sql = hr.getProperty("GET_TITLES").toString();
		log.info("GET_TITLES query: " + sql);
		JobTitleMapper mapper = new JobTitleMapper();

		List<JobTitle> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

}
