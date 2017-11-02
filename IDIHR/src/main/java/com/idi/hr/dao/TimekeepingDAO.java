package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.Timekeeping;
import com.idi.hr.bean.WorkHistory;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.TimekeepingMapper;
import com.idi.hr.mapper.WorkHistoryMapper;

public class TimekeepingDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(TimekeepingDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public TimekeepingDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get TIMEKEEPING from DB
	 * 
	 * @return List of TIMEKEEPING object
	 * @throws Exception
	 */
	public List<Timekeeping> getTimekeepings() {

		String sql = hr.getProperty("GET_TIMEKEEPING").toString();
		log.info("GET_TIMEKEEPING query: " + sql);
		TimekeepingMapper mapper = new TimekeepingMapper();

		List<Timekeeping> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get WorkHistory by employee id
	 * 
	 * @param employee
	 *            id
	 * @return WorkHistory object
	 */
	public List<WorkHistory> getWorkHistorys(int employeeId) {

		String sql = hr.get("GET_WORK_HISTORY_BY_EMPLOYEE").toString();
		log.info("GET_WORK_HISTORY_BY_EMPLOYEE query: " + sql);
		Object[] params = new Object[] { employeeId };

		WorkHistoryMapper mapper = new WorkHistoryMapper();

		List<WorkHistory> list = jdbcTmpl.query(sql, params, mapper);
		return list;

	}

	/**
	 * get WorkHistory by employee id
	 * 
	 * @param employee
	 *            id, fromDate
	 * @return WorkHistory object
	 */
	public WorkHistory getWorkHistory(int employeeId, String fromDate) {

		String sql = hr.get("GET_WORK_HISTORY").toString();
		log.info("GET_WORK_HISTORY query: " + sql);
		Object[] params = new Object[] { employeeId, fromDate };

		WorkHistoryMapper mapper = new WorkHistoryMapper();

		WorkHistory workHistory = jdbcTmpl.queryForObject(sql, params, mapper);

		return workHistory;

	}

	/**
	 * Insert a WorkHistory into database
	 * 
	 * @param WorkHistory
	 */
	public void insertWorkHistory(WorkHistory workHistory) throws Exception {
		try {

			log.info("Thêm mới thông tin lịch sử làm việc ....");
			String sql = hr.getProperty("INSERT_WORK_HISTORY").toString();
			log.info("INSERT_WORK_HISTORY query: " + sql);
			Object[] params = new Object[] { workHistory.getEmployeeId(), workHistory.getFromDate(),
					workHistory.getToDate(), workHistory.getTitle(), workHistory.getDepartment(),
					workHistory.getCompany(), workHistory.getSalary(), workHistory.getAchievement(),
					workHistory.getAppraise() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a WorkHistory into database
	 * 
	 * @param WorkHistory
	 */
	public void updateWorkHistory(WorkHistory workHistory) throws Exception {
		try {

			log.info("Cập nhật thông tin lịch sử làm việc cho MNV: " + workHistory.getEmployeeId() + " từ ngày "
					+ workHistory.getFromDate() + "đến ngày" + workHistory.getToDate());
			// update
			String sql = hr.getProperty("UPDATE_WORK_HISTORY").toString();
			log.info("UPDATE_WORK_HISTORY query: " + sql);
			Object[] params = new Object[] { workHistory.getToDate(), workHistory.getTitle(),
					workHistory.getDepartment(), workHistory.getCompany(), workHistory.getSalary(),
					workHistory.getAchievement(), workHistory.getAppraise(), workHistory.getEmployeeId(),
					workHistory.getFromDate() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Delete a WorkHistory from database
	 * 
	 * @param employeeId, fromDate
	 */
	public void deleteWorkHistory(int employeeId, String fromDate) throws Exception {
		try {

			log.info("Xóa thông tin lịch sử làm việc của MNV : " + employeeId + " từ ngày " + fromDate);
			// delete
			String sql = hr.getProperty("DELETE_WORK_HISTORY").toString();
			log.info("DELETE_WORK_HISTORY query: " + sql);
			Object[] params = new Object[] { employeeId, fromDate };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

}
