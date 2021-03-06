package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.LeaveInfoMapper;
import com.idi.hr.mapper.LeaveReportMapper;
import com.idi.hr.mapper.LeaveTypeMapper;

public class LeaveDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(LeaveDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public LeaveDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	// ---LEAVE_TYPE TABLE---//
	/**
	 * Get leave type from DB
	 * 
	 * @return List of leave type
	 * @throws Exception
	 */
	public List<LeaveType> getLeaveTypes() {

		String sql = hr.getProperty("GET_LEAVE_TYPES").toString();
		log.info("GET_LEAVE_TYPES query: " + sql);
		LeaveTypeMapper mapper = new LeaveTypeMapper();

		List<LeaveType> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * Get leave type for report from DB
	 * 
	 * @return List of leave type
	 * @throws Exception
	 */
	public List<LeaveType> getLeaveTypesForReport() {

		String sql = hr.getProperty("GET_LEAVE_TYPES_FOR_REPORT").toString();
		log.info("GET_LEAVE_TYPES_FOR_REPORT query: " + sql);
		LeaveTypeMapper mapper = new LeaveTypeMapper();

		List<LeaveType> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get leave type name by leaveId
	 * 
	 * @param leaveId
	 * @return department object
	 */
	public String getLeaveType(String leaveId) {

		String sql = hr.get("GET_LEAVE_TYPE").toString();
		log.info("GET_LEAVE_TYPE query: " + sql);
		Object[] params = new Object[] { leaveId };
		System.err.println(leaveId + ":");
		String leaveTypeName = "";
		leaveTypeName = jdbcTmpl.queryForObject(sql, String.class, params);

		System.err.println(leaveId + ": " + leaveTypeName);

		return leaveTypeName;

	}

	// ---LEAVE_INFO TABLE---//
	/**
	 * get number of time take leave with full day
	 * 
	 * @param year
	 * @param leaveId
	 * @return
	 */
	public String getLeaveTakenF(String year, int id) {

		String sql = hr.get("GET_LEAVE_TAKEN_F").toString();
		log.info("GET_LEAVE_TAKEN_F query: " + sql);
		Object[] params = new Object[] { id, year };

		String numberDayLeaveTaken = "";
		numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, String.class, params);
		System.err.println(year + "|" + id + "|" + numberDayLeaveTaken);
		return numberDayLeaveTaken;
	}

	/**
	 * get number of time take leave with half day
	 * 
	 * @param year
	 * @param leaveId
	 * @return
	 */
	public String getLeaveTakenH(String year, int id) {

		String sql = hr.get("GET_LEAVE_TAKEN_H").toString();
		log.info("GET_LEAVE_TAKEN_H query: " + sql);
		Object[] params = new Object[] { id, year };

		String numberDayLeaveTaken = "";
		numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, String.class, params);

		System.err.println(year + "|" + id + "|" + numberDayLeaveTaken);

		return numberDayLeaveTaken;
	}

	/**
	 * Get leave info from DB
	 * 
	 * @return List of leave info
	 * @throws Exception
	 */
	public List<LeaveInfo> getLeaves() {

		String sql = hr.getProperty("GET_LEAVES").toString();
		log.info("GET_LEAVES query: " + sql);
		LeaveInfoMapper mapper = new LeaveInfoMapper();

		List<LeaveInfo> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * Insert a LeaveInfo into database
	 * 
	 * @param LeaveInfo
	 */
	public void insertLeaveInfo(LeaveInfo leaveInfo) throws Exception {
		try {

			log.info("Insert new LeaveInfo ....");
			String sql = hr.getProperty("INSERT_LEAVE_INFO").toString();
			log.info("INSERT_LEAVE_INFO query: " + sql);
			String leaveType = leaveInfo.getLeaveType();
			if (leaveType.endsWith("2")) {
				leaveInfo.setTimeValue(4);
				leaveType = leaveType.substring(0, leaveType.length() - 1);
			} else if (leaveType.equalsIgnoreCase("KCC"))
				leaveInfo.setTimeValue(1);
			else
				leaveInfo.setTimeValue(8);
			Object[] params = new Object[] { leaveInfo.getEmployeeId(), leaveInfo.getDate(), leaveType,
					leaveInfo.getTimeValue(), leaveInfo.getComment() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * delete leave info
	 * 
	 * @param employeeId
	 * @param date
	 * @param leaveType
	 * @throws Exception
	 */
	public void deleteLeaveInfo(int employeeId, java.util.Date date, String leaveType) throws Exception {
		try {
			log.info("Xóa thông tin ngày nghỉ của MNV : " + employeeId + " ngày " + date);
			// delete
			String sql = hr.getProperty("DELETE_LEAVE_INFO").toString();
			log.info("DELETE_LEAVE_INFO query: " + sql);
			Object[] params = new Object[] { employeeId, date, leaveType };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * get leave info for report
	 * 
	 * @param year
	 * @param month
	 * @param employeeId
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */

	public int getLeaveReport(String year, String month, int employeeId, String leaveType) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("GET_LEAVE_INFO_FOR_REPORT").toString();
		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		if (employeeId > 0)
			sql = sql + " AND EMPLOYEE_ID = " + employeeId + "";

		log.info("GET_LEAVE_INFO_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year, leaveType };
		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;
		System.err.println("leaveType:" + leaveType + ", " + countNumber);

		return countNumber;
	}

	// ---LEAVE_REPORT TABLE---//

	/**
	 * Insert or update a LeaveReport into database
	 * 
	 * @param leaveReport
	 */
	public void insertOrUpdateLeaveReport(LeaveReport leaveReport) throws Exception {
		try {
			log.info("Insert new or update leaveReport ....");
			String sql = hr.getProperty("UPDATE_LEAVE_REPORT").toString();
			Object[] params = new Object[] { leaveReport.getQuataLeave(), leaveReport.getLeaveUsed(),
					leaveReport.getLeaveRemain(), leaveReport.getSeniority(), leaveReport.getRestQuata(),
					leaveReport.getEmployeeId(), leaveReport.getYearReport() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			try {
				String sql = hr.getProperty("INSERT_LEAVE_REPORT").toString();
				Object[] params = new Object[] { leaveReport.getEmployeeId(), leaveReport.getYearReport(),
						leaveReport.getQuataLeave(), leaveReport.getLeaveUsed(), leaveReport.getLeaveRemain(),
						leaveReport.getSeniority(), leaveReport.getRestQuata() };
				jdbcTmpl.update(sql, params);

			} catch (Exception ex) {

			}
		}
	}

	/**
	 * 
	 * @param employeeId
	 * @param year
	 * @return
	 */
	public LeaveReport getLeaveReport(int employeeId, int year) {

		String sql = hr.get("GET_LEAVE_REPORT").toString();
		log.info("GET_LEAVE_REPORT query: " + sql);
		Object[] params = new Object[] { employeeId, year };

		LeaveReportMapper mapper = new LeaveReportMapper();

		LeaveReport leaveReport = jdbcTmpl.queryForObject(sql, params, mapper);

		return leaveReport;
	}
}
