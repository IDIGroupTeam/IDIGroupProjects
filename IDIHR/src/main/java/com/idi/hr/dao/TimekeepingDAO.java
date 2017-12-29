package com.idi.hr.dao;

import java.util.Date;
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

		String sql = hr.getProperty("GET_TIMEKEEPING_INFO").toString();
		log.info("GET_TIMEKEEPING_INFO query: " + sql);
		TimekeepingMapper mapper = new TimekeepingMapper();

		List<Timekeeping> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get Timekeeping by employee id
	 * 
	 * @param employeeid,
	 *            Date, timeIn
	 * @return Timekeeping object
	 */
	public Timekeeping getTimekeeping(int employeeId, Date date, String timeIn) {

		String sql = hr.get("GET_TIMEKEEPING").toString();
		log.info("GET_TIMEKEEPING query: " + sql);
		Object[] params = new Object[] { employeeId, date, timeIn };

		TimekeepingMapper mapper = new TimekeepingMapper();

		Timekeeping timekeeping = jdbcTmpl.queryForObject(sql, params, mapper);

		return timekeeping;

	}

	/**
	 * CheckTimekeepingExisting
	 * @param employeeId
	 * @param date
	 * @param timeIn
	 * @return
	 */
	public String checkTimekeepingExisting(int employeeId, Date date, String timeIn) {

		String sql = hr.get("CHECK_EXISTING_TIMEKEEPING").toString();
		log.info("CHECK_EXISTING_TIMEKEEPING query: " + sql);
		Object[] params = new Object[] { employeeId, date, timeIn };

		String existing = jdbcTmpl.queryForObject(sql, String.class, params);
		
		return existing;

	}
	
	/**
	 * Insert a Timekeeping into database
	 * 
	 * @param timekeepings
	 */
	public void insertOrUpdate(List<Timekeeping> timekeepings) throws Exception {
		try {
			log.info("Cập nhật thông tin chấm công ....");
			String sql = hr.getProperty("INSERT_TIMEKEEPING").toString();
			log.info("INSERT_TIMEKEEPING query: " + sql);
			System.err.println(timekeepings.size());
			for (int i = 0; i < timekeepings.size(); i++) {
				Timekeeping timekeepingDTO = new Timekeeping();
				timekeepingDTO = timekeepings.get(i);
				try {
					String timekeepingExist = checkTimekeepingExisting(timekeepingDTO.getEmployeeId(), timekeepingDTO.getDate(),
							timekeepingDTO.getTimeIn());					
					if (Integer.parseInt(timekeepingExist) > 0) {
						log.info("The record for employeeId=" + timekeepingDTO.getEmployeeId() + ", date="
								+ timekeepingDTO.getDate() + ", check in time=" + timekeepingDTO.getTimeIn()
								+ " is existing, do not insert ...");
					}else {
						System.err.println("insert Ma NV: " + timekeepingDTO.getEmployeeId() + "|" + timekeepingDTO.getDate() + "|" + timekeepingDTO.getTimeIn());
						Object[] params = new Object[] { timekeepingDTO.getEmployeeId(), timekeepingDTO.getDate(),
								timekeepingDTO.getTimeIn(), timekeepingDTO.getTimeOut(), timekeepingDTO.getComeLateM(),
								timekeepingDTO.getLeaveSoonM(), timekeepingDTO.getComeLateA(),
								timekeepingDTO.getLeaveSoonA(), timekeepingDTO.getComment() };
						jdbcTmpl.update(sql, params);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
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
	 * @param employeeId,
	 *            fromDate
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
	
	/**
	 * get time keeping for report, come late, leave soon
	 * 
	 * @param year
	 * @param month
	 * @param employeeId
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */
	public String getTimekeepingReport(String year, String month, int employeeId, String leaveType) throws Exception {
		String countNumber = "";
		String sqlCL = hr.getProperty("GET_TIMEKEEPING_COME_LATE_FOR_REPORT").toString();
		String sqlLS = hr.getProperty("GET_TIMEKEEPING_LEAVE_SOON_FOR_REPORT").toString();
		String sql="";
		if(leaveType.equalsIgnoreCase("DM"))
			sql = sqlCL;
		else if(leaveType.equalsIgnoreCase("VS"))
			sql = sqlLS;
		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		if (employeeId > 0)
			sql = sql + " AND EMPLOYEE_ID = " + employeeId + " ";		

		log.info("GET_TIMEKEEPING_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year };
		countNumber = jdbcTmpl.queryForObject(sql, String.class, params);
		
		System.err.println(leaveType + ": " + countNumber);
		
		return countNumber;
	}
}
