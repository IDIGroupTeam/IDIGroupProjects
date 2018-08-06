package com.idi.hr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.idi.hr.bean.LeaveReport;
import com.idi.hr.bean.Timekeeping;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.TimekeepingMapper;

public class TimekeepingDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(TimekeepingDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	@Autowired
	private JavaMailSender mailSender;

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
	public List<Timekeeping> getTimekeepings(String fromDate, String toDate, String dept, String eId) {
		String sql = "";
		Object[] params = null;
		if (toDate != null) {
			sql = hr.getProperty("GET_TIMEKEEPINGS").toString();
			if (dept != null && !dept.equalsIgnoreCase("all"))
				sql = sql + " AND E.DEPARTMENT = '" + dept + "'";
			if (eId != null && Integer.parseInt(eId) > 0)
				sql = sql + " AND E.EMPLOYEE_ID = " + eId;
			log.info("GET_TIMEKEEPINGS query: " + sql);
			params = new Object[] { fromDate, toDate };
		} else {
			sql = hr.getProperty("GET_TIMEKEEPING_INFO").toString();
			log.info("GET_TIMEKEEPING_INFO query: " + sql);
			params = new Object[] { fromDate };
		}

		TimekeepingMapper mapper = new TimekeepingMapper();
		List<Timekeeping> list = jdbcTmpl.query(sql, params, mapper);
		System.err.println(156/60);
		System.err.println(156%60);
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
	 * 
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
	 * Checking is employee or not
	 * 
	 * @param id,
	 *            name
	 * @return int
	 *
	 */
	public int isEmployee(int id, String name) {

		String sql = hr.get("IS_EMPLOYEE").toString();
		log.info("IS_EMPLOYEE query: " + sql);
		Object[] params = new Object[] { id, name };

		int number = jdbcTmpl.queryForObject(sql, Integer.class, params);

		return number;
	}

	/**
	 * Insert list Timekeeping into database
	 * 
	 * @param timekeepings
	 */
	public void insert(List<Timekeeping> timekeepings) throws Exception {
		try {
			log.info("Cập nhật thông tin chấm công ....");
			String sql = hr.getProperty("INSERT_TIMEKEEPING").toString();
			log.info("INSERT_TIMEKEEPING query: " + sql);
			System.err.println(timekeepings.size());
			for (int i = 0; i < timekeepings.size(); i++) {
				Timekeeping timekeepingDTO = new Timekeeping();
				timekeepingDTO = timekeepings.get(i);
				try {
					String timekeepingExist = checkTimekeepingExisting(timekeepingDTO.getEmployeeId(),
							timekeepingDTO.getDate(), timekeepingDTO.getTimeIn());
					if (Integer.parseInt(timekeepingExist) > 0) {
						log.info("The record for employeeId=" + timekeepingDTO.getEmployeeId() + ", date="
								+ timekeepingDTO.getDate() + ", check in time=" + timekeepingDTO.getTimeIn()
								+ " is existing, do not insert ...");
					} else {
						if (isEmployee(timekeepingDTO.getEmployeeId(), timekeepingDTO.getEmployeeName()) > 0) {
							log.info("insert Ma NV: " + timekeepingDTO.getEmployeeId() + "|"
									+ timekeepingDTO.getEmployeeName() + "|" + timekeepingDTO.getDate() + "|"
									+ timekeepingDTO.getTimeIn());
							Object[] params = new Object[] { timekeepingDTO.getEmployeeId(), timekeepingDTO.getDate(),
									timekeepingDTO.getTimeIn(), timekeepingDTO.getTimeOut(), timekeepingDTO.getWorkedTime(),
									timekeepingDTO.getComeLateM(), timekeepingDTO.getLeaveSoonM(),
									timekeepingDTO.getComeLateA(), timekeepingDTO.getLeaveSoonA() }; // ,
																										// timekeepingDTO.getComment()
																										// };
							jdbcTmpl.update(sql, params);
						} else {
							// Gửi mail thông báo thông tin nhân viên từ máy chấm công không khớp
							MimeMessage mimeMessage = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							String htmlMsg = "Thông tin mã và tên nhân viên từ máy chấm công không khớp với thông tin được tạo trên hệ thống phần mềm. <br/>\n "
									+ "Ngày: " + timekeepingDTO.getDate() + " <br/>\n " + "Mã nhân viên: "
									+ timekeepingDTO.getEmployeeId() + " <br/>\n " + "Tên nhân viên: "
									+ timekeepingDTO.getEmployeeName() + "<br/> <br/> \n"
									+ "Được inport bởi: (lấy từ phần phân quyền) <e-mail> lúc "
									+ Calendar.getInstance().getTime();
							mimeMessage.setContent(htmlMsg, "text/html; charset=UTF-8");
							helper.setTo("idigroupbsc@gmail.com");
							helper.setSubject("Cảnh báo: Thông tin nhân viên không khớp");
							helper.setFrom("IDIHRNotReply");
							mailSender.send(mimeMessage);
						}
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
	 * Update a Timekeeping into database
	 * 
	 * @param timekeepings
	 */
	public void update(Timekeeping timekeeping) throws Exception {
		try {
			log.info("Cập nhật thông tin chấm công phát sinh....");
			try {
				String timekeepingExist = checkTimekeepingExisting(timekeeping.getEmployeeId(), timekeeping.getDate(),
						timekeeping.getTimeIn());
				if (Integer.parseInt(timekeepingExist) > 0) {
					log.info("The record for employeeId=" + timekeeping.getEmployeeId() + ", date="
							+ timekeeping.getDate() + ", check in time=" + timekeeping.getTimeIn()
							+ " is existing, updating ...");
					String sql = hr.getProperty("UPDATE_TIMEKEEPING").toString();
					log.info("UPDATE_TIMEKEEPING query: " + sql);
					Object[] params = new Object[] { timekeeping.getTimeOut(), timekeeping.getComeLateM(),
							timekeeping.getLeaveSoonM(), timekeeping.getComeLateA(), timekeeping.getLeaveSoonA(),
							timekeeping.getComment(), timekeeping.getEmployeeId(), timekeeping.getDate(),
							timekeeping.getTimeIn() };
					jdbcTmpl.update(sql, params);
				} else {
					log.info("insert Ma NV: " + timekeeping.getEmployeeId() + "|" + timekeeping.getDate() + "|"
							+ timekeeping.getTimeIn());
					String sql = hr.getProperty("INSERT_TIMEKEEPING").toString();
					log.info("INSERT_TIMEKEEPING query: " + sql);
					if (timekeeping.getTimeIn() == null) {
						PropertiesManager hr = new PropertiesManager("hr.properties");
						if (timekeeping.getComment().equalsIgnoreCase("Request leave soon morning")) {
							timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_MORNING").toString());
							System.err.println("Request leave soon morning " + timekeeping.getTimeIn());
						} else if (timekeeping.getComment().equalsIgnoreCase("Request leave soon afternoon")) {
							timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_AFTERNOON").toString());
							System.err.println("Request leave soon afternoon " + timekeeping.getTimeIn());
						}
					}
					Object[] params = new Object[] { timekeeping.getEmployeeId(), timekeeping.getDate(),
							timekeeping.getTimeIn(), // tam thoi bo 26/2/2018
							timekeeping.getTimeOut(), timekeeping.getComeLateM(), timekeeping.getLeaveSoonM(),
							timekeeping.getComeLateA(), timekeeping.getLeaveSoonA(), timekeeping.getComment() };
					jdbcTmpl.update(sql, params);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

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
	public LeaveReport getTimekeepingReport(String year, String month, int employeeId, String leaveType)
			throws Exception {

		String sqlCL = hr.getProperty("GET_TIMEKEEPING_COME_LATE_FOR_REPORT").toString();
		String sqlLS = hr.getProperty("GET_TIMEKEEPING_LEAVE_SOON_FOR_REPORT").toString();
		String maxTimeLate = hr.getProperty("COME_LATE_TIME_OVER").toString();
		String sql = "";
		if (leaveType.equalsIgnoreCase("DM")) {
			sql = sqlCL;
			if (month != null && month.length() > 0) {
				sql = sql.replaceAll("%MONTH%", " AND MONTH(T.DATE) = '" + month + "' ");
				sql = sql.replaceAll("%MONTH1%", " AND MONTH(L.DATE) = '" + month + "' ");
			}else {
				sql = sql.replaceAll("%MONTH%", "");
				sql = sql.replaceAll("%MONTH1%", "");
			}	
			if (employeeId > 0) {
				sql = sql.replaceAll("%EMPLOYEE_ID%"," AND T.EMPLOYEE_ID = " + employeeId + " ");
				sql = sql.replaceAll("%EMPLOYEE_ID1%"," AND L.EMPLOYEE_ID = " + employeeId + " ");
			}else {
				sql = sql.replaceAll("%EMPLOYEE_ID%","");
				sql = sql.replaceAll("%EMPLOYEE_ID1%","");
			}
			log.info("GET_TIMEKEEPING_COME_LATE_FOR_REPORT query: " + sql);
			
		}else if (leaveType.equalsIgnoreCase("VS")) {
			sql = sqlLS;
			if (month != null && month.length() > 0) {
				sql = sql.replaceAll("%MONTH%", " AND MONTH(T.DATE) = '" + month + "' ");
				sql = sql.replaceAll("%MONTH1%", " AND MONTH(L.DATE) = '" + month + "' ");
			}else {
				sql = sql.replaceAll("%MONTH%", "");
				sql = sql.replaceAll("%MONTH1%", "");
			}
			if (employeeId > 0) {
				sql = sql.replaceAll("%EMPLOYEE_ID%"," AND T.EMPLOYEE_ID = " + employeeId + " ");
				sql = sql.replaceAll("%EMPLOYEE_ID1%"," AND L.EMPLOYEE_ID = " + employeeId + " ");
			}else {
				sql = sql.replaceAll("%EMPLOYEE_ID%","");
				sql = sql.replaceAll("%EMPLOYEE_ID1%","");
			}
			log.info("GET_TIMEKEEPING_LEAVE_SOON_FOR_REPORT query: " + sql);
		}		

		Object[] params = null;
		
		if (leaveType.equalsIgnoreCase("DM"))
			params = new Object[] {year, maxTimeLate, maxTimeLate, year};
		else
			params = new Object[] {year, year};
		
		return jdbcTmpl.query(sql, params, new ResultSetExtractor<LeaveReport>() {
			@Override
			public LeaveReport extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					LeaveReport leaveReport = new LeaveReport();

					if (leaveType.equalsIgnoreCase("DM")) {
						leaveReport.setComeLate(rs.getInt("COUNT"));
						leaveReport.setLateAValue(rs.getInt("LATE_A"));
						leaveReport.setLateMValue(rs.getInt("LATE_M"));
					} else if (leaveType.equalsIgnoreCase("VS")) {
						leaveReport.setLeaveSoon(rs.getInt("COUNT"));
						leaveReport.setLeaveSoonAValue(rs.getInt("LEAVE_SOON_A"));
						leaveReport.setLeaveSoonMValue(rs.getInt("LEAVE_SOON_M"));
					}

					return leaveReport;
				}
				return null;
			}
		});
	}

	/**
	 * get time keeping for report, come late, leave soon
	 * 
	 * @param year
	 * @param month
	 * @param employeeIds
	 * @param leaveType
	 * @return
	 * @throws Exception
	 */
	public String getTimekeepingReport(String year, String month, String employeeIds, String leaveType)	throws Exception {
		String countNumber = "";
		String sqlCL = hr.getProperty("GET_TIMEKEEPING_COME_LATE_FOR_KPI").toString();
		String sqlLS = hr.getProperty("GET_TIMEKEEPING_LEAVE_SOON_FOR_KPI").toString();
		String maxTimeLate = hr.getProperty("COME_LATE_TIME_OVER").toString();
		String sql = "";
		if (leaveType.equalsIgnoreCase("DM")) {			
			sql = sqlCL;			
			
			if (month != null && month.length() > 0) {
				sql = sql.replaceAll("%MONTH%", " AND MONTH(T.DATE) = '" + month + "' ");
				sql = sql.replaceAll("%MONTH1%", " AND MONTH(L.DATE) = '" + month + "' ");
			}else {
				sql = sql.replaceAll("%MONTH%", "");
				sql = sql.replaceAll("%MONTH1%", "");
			}	
			if (employeeIds != null && employeeIds.length() > 0) {
				sql = sql.replaceAll("%EMPLOYEE_ID%"," AND T.EMPLOYEE_ID IN (" + employeeIds + ")" );
				sql = sql.replaceAll("%EMPLOYEE_ID1%"," AND L.EMPLOYEE_ID IN (" + employeeIds + ")" );
			}else {
				sql = sql.replaceAll("%EMPLOYEE_ID%","");
				sql = sql.replaceAll("%EMPLOYEE_ID1%","");
			}						

			log.info("GET_TIMEKEEPING_COME_LATE_FOR_KPI query: " + sql);
			
		} else if (leaveType.equalsIgnoreCase("VS")) {			
			sql = sqlLS;
			
			if (month != null && month.length() > 0) {
				sql = sql.replaceAll("%MONTH%", " AND MONTH(T.DATE) = '" + month + "' ");
				sql = sql.replaceAll("%MONTH1%", " AND MONTH(L.DATE) = '" + month + "' ");
			}else {
				sql = sql.replaceAll("%MONTH%", "");
				sql = sql.replaceAll("%MONTH1%", "");
			}	
			if (employeeIds != null && employeeIds.length() > 0) {
				sql = sql.replaceAll("%EMPLOYEE_ID%"," AND T.EMPLOYEE_ID IN (" + employeeIds + ")" );
				sql = sql.replaceAll("%EMPLOYEE_ID1%"," AND L.EMPLOYEE_ID IN (" + employeeIds + ")" );
			}else {
				sql = sql.replaceAll("%EMPLOYEE_ID%","");
				sql = sql.replaceAll("%EMPLOYEE_ID1%","");
			}	
			
			log.info("GET_TIMEKEEPING_LEAVE_SOON_FOR_KPI query: " + sql);
		}

		Object[] params = null;
		
		if (leaveType.equalsIgnoreCase("DM"))
			params = new Object[] { year, maxTimeLate, maxTimeLate, year };
		else
			params = new Object[] { year, year };

		countNumber = jdbcTmpl.queryForObject(sql, String.class, params);
		System.err.println(leaveType + ": " + countNumber);

		return countNumber;
	}

	/**
	 * Thong ke so lan di muon qua 60' tinh nghi ko phep nua ngay
	 * Tru t/h di cong viec ben ngoai
	 * 
	 * @param year
	 * @param month
	 * @param employeeId
	 * @return
	 * @throws Exception
	 */
	public int countComleLateOver(String year, String month, int employeeId) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("GET_COME_LATE_OVER_FOR_REPORT").toString();
		int maxTimeLate = Integer.parseInt(hr.getProperty("COME_LATE_TIME_OVER").toString());

		if (month != null && month.length() > 0) {
			sql = sql.replaceAll("%MONTH%", " AND MONTH(T.DATE) = '" + month + "' ");
			sql = sql.replaceAll("%MONTH1%", " AND MONTH(L.DATE) = '" + month + "' ");
		}else {
			sql = sql.replaceAll("%MONTH%", "");
			sql = sql.replaceAll("%MONTH1%", "");
		}
		if (employeeId > 0) {
			sql = sql.replaceAll("%EMPLOYEE_ID%"," AND T.EMPLOYEE_ID = " + employeeId + " ");
			sql = sql.replaceAll("%EMPLOYEE_ID1%"," AND L.EMPLOYEE_ID = " + employeeId + " ");
		}else {
			sql = sql.replaceAll("%EMPLOYEE_ID%","");
			sql = sql.replaceAll("%EMPLOYEE_ID1%","");
		}		

		log.info("GET_COME_LATE_OVER_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year, maxTimeLate, maxTimeLate, year };

		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;

		System.err.println("month: " + month + ", year " + year + ", employeeId: " + employeeId + "|" + countNumber);

		return countNumber;
	}

	/**
	 * Thong ke so lan di muon qua 60' tinh nghi ko phep nua ngay
	 * 
	 * @param year
	 * @param month
	 * @param employeeIds
	 * @return
	 * @throws Exception
	 */
/*	public int countComleLateOver(String year, String month, String employeeIds) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("GET_COME_LATE_OVER_FOR_REPORT").toString();
		String maxTimeLate = hr.getProperty("COME_LATE_TIME_OVER").toString();
		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		if (employeeIds != null && employeeIds.length() > 0)
			sql = sql + " AND EMPLOYEE_ID IN (" + employeeIds + ")";

		log.info("GET_COME_LATE_OVER_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year, maxTimeLate, maxTimeLate };

		if (jdbcTmpl.queryForObject(sql, Integer.class, params) != null)
			countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);
		else
			countNumber = 0;

		System.err.println("month: " + month + ", year " + year + ", employeeIds: " + employeeIds + "|" + countNumber);

		return countNumber;
	}*/

	/**
	 * get half worked day
	 * 
	 * @param year
	 * @param month
	 * @param employeeId
	 * @return
	 * @throws Exception
	 */
	public int getWorkedTime(String year, String month, int employeeId) throws Exception {
		int countNumber = 0;
		String sql = hr.getProperty("CHECK_WORKED_TIME").toString();
		log.info("CHECK_WORKED_TIME query: " + sql);

		if (month != null && month.length() > 0)
			sql = sql + " AND MONTH(DATE) = '" + month + "' ";

		Object[] params = new Object[] { year, employeeId };
		countNumber = jdbcTmpl.queryForObject(sql, Integer.class, params);

		System.err.println("So ngay da lam viec: " + month + "|" + year + "|" + employeeId + "|" + countNumber);

		return countNumber;
	}

}
