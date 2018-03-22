package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.WorkingDay;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.WorkingDayMapper;

public class WorkingDayDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(WorkingDayDAO.class.getName());
	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public WorkingDayDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get workingDays from DB
	 * 
	 * @return List of workingDay
	 * @throws Exception
	 */
	public List<WorkingDay> getWorkingDays() {

		String sql = hr.getProperty("GET_WORKING_DAYS").toString();
		log.info("GET_WORKING_DAYS query: " + sql);
		WorkingDayMapper mapper = new WorkingDayMapper();

		List<WorkingDay> list = jdbcTmpl.query(sql, mapper);
		return list;
	}

	/**
	 * get workingDay by month, forCompany
	 * 
	 * @param month, forCompany
	 * @return workingDay object
	 */
	public WorkingDay getWorkingDay(String month, String forCompany) {
		WorkingDay workingDay = new WorkingDay();
		try {
			String sql = hr.get("GET_WORKING_DAY_FOR_MONTH").toString();
			log.info("GET_WORKING_DAY_FOR_MONTH query: " + sql);
			Object[] params = new Object[] { month, forCompany };
			System.out.println(month +"|"+ forCompany ); 
			WorkingDayMapper mapper = new WorkingDayMapper();
	
			workingDay = jdbcTmpl.queryForObject(sql, params, mapper);
		}catch(Exception e) {
			workingDay.setWorkDayOfMonth("0");
			workingDay.setComment("Tháng này chưa định nghĩa ngày công chuẩn");
		}
		return workingDay;
	}

	/**
	 * Insert a workingDay into database
	 * 
	 * @param workingDay
	 */
	public void insertWorkingDay(WorkingDay workingDay) throws Exception {
		try {
			log.info("Insert new department ....");
			String sql = hr.getProperty("INSERT_WORKING_DAY").toString();
			log.info("INSERT_WORKING_DAY query: " + sql);
			Object[] params = new Object[] { workingDay.getMonth(), workingDay.getWorkDayOfMonth(),
					workingDay.getForCompany(), workingDay.getUpdateId(), workingDay.getComment()};
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a workingDay into database
	 * 
	 * @param workingDay
	 */
	public void updateDepartment(WorkingDay workingDay) throws Exception {
		try {

			log.info("Update department " + workingDay.getMonth() + ", for company " + workingDay.getForCompany() + " ....");
			String sql = hr.getProperty("UPDATE_WORKING_DAY").toString();
			log.info("UPDATE_WORKING_DAY query: " + sql);
			Object[] params = new Object[] {workingDay.getWorkDayOfMonth(), workingDay.getComment(), workingDay.getUpdateId(),
					workingDay.getMonth(), workingDay.getForCompany()};
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}
}
