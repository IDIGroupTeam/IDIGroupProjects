package com.idi.hr.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.bean.ValueReport;
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
		//System.err.println(leaveId + ":");
		String leaveTypeName = "";
		leaveTypeName = jdbcTmpl.queryForObject(sql, String.class, params);

		//System.err.println(leaveId + ": " + leaveTypeName);

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
	public float getLeaveTaken(String year, int id) {

		String sql = hr.get("GET_LEAVE_TAKEN").toString();
		log.info("GET_LEAVE_TAKEN query: " + sql);
		Object[] params = new Object[] { id, year };

		float numberDayLeaveTaken = 0;
		if(jdbcTmpl.queryForObject(sql, float.class, params) != null)
			numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, float.class, params);
		//System.err.println(year + "|" + id + "|" + numberDayLeaveTaken);
		return numberDayLeaveTaken;
	}

	
	/**
	 * get number of time request for leave or come late/ leave soon
	 * 
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public int getRequestComeLateUsed(int year, int month, int id) {

		String sql = hr.get("COUNT_COME_LATE_USED").toString();
		if(month > 0)
			sql = sql + " AND MONTH(DATE) = " + month;
		
		log.info("COUNT_COME_LATE_USED query: " + sql);
		Object[] params = new Object[] { id, year };

		int numberDayLeaveTaken = 0;
		if(jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, Integer.class, params);
		System.err.println(year + "|" + id + "|" + month + ": "+ numberDayLeaveTaken);
		return numberDayLeaveTaken;
	}

	
	/**
	 * get number of time take leave with full day
	 * 
	 * @param year
	 * @param leaveId
	 * @return
	 */
	public float getLeaveTakenF(String year, int id) {

		String sql = hr.get("GET_LEAVE_TAKEN_F").toString();
		log.info("GET_LEAVE_TAKEN_F query: " + sql);
		Object[] params = new Object[] { id, year };

		float numberDayLeaveTaken = 0;
		numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, float.class, params);
		//System.err.println(year + "|" + id + "|" + numberDayLeaveTaken);
		return numberDayLeaveTaken;
	}

	/**
	 * get number of time take leave with half day
	 * 
	 * @param year
	 * @param leaveId
	 * @return
	 */
	public float getLeaveTakenH(String year, int id) {

		String sql = hr.get("GET_LEAVE_TAKEN_H").toString();
		log.info("GET_LEAVE_TAKEN_H query: " + sql);
		Object[] params = new Object[] { id, year };

		float numberDayLeaveTaken = 0;
		numberDayLeaveTaken = jdbcTmpl.queryForObject(sql, float.class, params);

		//System.err.println(year + "|" + id + "|" + numberDayLeaveTaken);
		return numberDayLeaveTaken;
	}

	/**
	 * Get leave info from DB
	 * 
	 * @return List of leave info
	 * @throws Exception
	 */
	public List<LeaveInfo> getLeaves(String fromDate, String toDate, String dept, String eId) {
		String sql = "";
		Object[] params = null;
		if(toDate != null) {
			sql = hr.getProperty("GET_LEAVES_FOR_SEARCH").toString();
			if(dept != null && !dept.equalsIgnoreCase("all"))
				sql = sql + " AND E.DEPARTMENT = '"+ dept +"'";
			if(eId != null && Integer.parseInt(eId) > 0)
				sql = sql + " AND E.EMPLOYEE_ID = " + eId;
			sql = sql + " ORDER BY L.EMPLOYEE_ID, L.DATE ";	
			log.info("GET_LEAVES_FOR_SEARCH query: " + sql);
			params = new Object[] { fromDate, toDate };
		}else {
			sql = hr.getProperty("GET_LEAVES").toString();
			log.info("GET_LEAVES query: " + sql);
			params = new Object[] { fromDate };
		}			
		LeaveInfoMapper mapper = new LeaveInfoMapper();		
		List<LeaveInfo> list = jdbcTmpl.query(sql, params, mapper);
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
			if(leaveInfo.getTimeValue() > 0) {
				
			}else {				
				if (leaveType.endsWith("2")) {
					leaveInfo.setTimeValue(4);
					leaveType = leaveType.substring(0, leaveType.length() - 1);
				} else if (leaveType.equalsIgnoreCase("KCC"))
					leaveInfo.setTimeValue(1);
				else if (leaveType.equalsIgnoreCase("DMS") || leaveType.equalsIgnoreCase("DMC")
					|| leaveType.equalsIgnoreCase("VSS")|| leaveType.equalsIgnoreCase("VSC")) {
					leaveInfo.setTimeValue(1);
					//dang can nhac luu leave type la gi
				}else
					leaveInfo.setTimeValue(8);
			}
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
	public void deleteLeaveInfo(int employeeId, String date, String leaveType) throws Exception {
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

		sql = sql + " AND LEAVE_TYPE IN ('" + leaveType +"')";
		
		log.info("GET_LEAVE_INFO_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year };
		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;
		log.info("leaveType: " + leaveType + ", month: " + month + ", year " + year + ", employeeId: " + employeeId + "|" + countNumber);

		return countNumber;
	}

	/**
	 * check cham cong phat sinh da ton tai
	 * @param date
	 * @param employeeId
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */
	public int checkLeaveDuplicate(Date date, int employeeId, String leaveType) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("GET_LEAVE_INFO_FOR_DUPLICATE").toString();

		if (employeeId > 0)
			sql = sql + " AND EMPLOYEE_ID = " + employeeId + "";

		sql = sql + " AND LEAVE_TYPE IN ('" + leaveType +"')";
		
		log.info("GET_LEAVE_INFO_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { date };
		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;
		log.info("leaveType: " + leaveType + ", ngay: " + date + ", employeeId: " + employeeId + "|" + countNumber);

		return countNumber;
	}
	
	/**
	 * count number time/value of working without keeping ... 
	 * @param year
	 * @param month
	 * @param employeeId
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */
	public ValueReport getWorkCount(String year, String month, int employeeId, String leaveType)
			throws Exception {

		String sql = hr.getProperty("GET_WORK_COUNT_FOR_REPORT").toString();
		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		if (employeeId > 0)
			sql = sql + " AND EMPLOYEE_ID = " + employeeId + "";

		sql = sql + " AND LEAVE_TYPE IN ('" + leaveType +"')";	
		
		log.info("GET_WORK_COUNT_FOR_REPORT query: " + sql);
		
		Object[] params = new Object[] { year };

		return jdbcTmpl.query(sql, params, new ResultSetExtractor<ValueReport>() {
			@Override
			public ValueReport extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					
					ValueReport valueReport = new ValueReport();
					
					valueReport.setCount(rs.getInt("COUNT"));
					valueReport.setValue(rs.getFloat("TIME_VALUE"));
					System.err.println(rs.getInt("COUNT")+"|"+ rs.getFloat("TIME_VALUE"));
					return valueReport;
				}
				return null;
			}
		});
	}
	
	/**
	 * get leave info for report
	 * 
	 * @param year
	 * @param month
	 * @param employeeIds
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */

	public int getLeaveReport(String year, String month, String employeeIds, String leaveType) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("GET_LEAVES_INFO_FOR_REPORT").toString();
		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		if (employeeIds != null && employeeIds.length() > 0)
			sql = sql + " AND EMPLOYEE_ID IN (" + employeeIds + ")";

		log.info("GET_LEAVES_INFO_FOR_REPORT query: " + sql);
		
		Object[] params = new Object[] { year, leaveType };
		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;
		log.info("leaveType:" + leaveType + ", " + countNumber);

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
			log.info(" Thêm mới/cập nhật thông tin ngày nghỉ ....");
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
