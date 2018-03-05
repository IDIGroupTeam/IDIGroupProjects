package com.idi.hr.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

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
	public List<Timekeeping> getTimekeepings(String date) {

		String sql = hr.getProperty("GET_TIMEKEEPING_INFO").toString();
		log.info("GET_TIMEKEEPING_INFO query: " + sql);
		Object[] params = new Object[] { date };
		TimekeepingMapper mapper = new TimekeepingMapper();

		List<Timekeeping> list = jdbcTmpl.query(sql, params, mapper);
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
	 * Checking is employee or not
	 * 
	 * @param id, name
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
					String timekeepingExist = checkTimekeepingExisting(timekeepingDTO.getEmployeeId(), timekeepingDTO.getDate(),
							timekeepingDTO.getTimeIn());					
					if (Integer.parseInt(timekeepingExist) > 0) {
						log.info("The record for employeeId=" + timekeepingDTO.getEmployeeId() + ", date="
								+ timekeepingDTO.getDate() + ", check in time=" + timekeepingDTO.getTimeIn()
								+ " is existing, do not insert ...");
					}else {
						if(isEmployee(timekeepingDTO.getEmployeeId(), timekeepingDTO.getEmployeeName()) > 0) {
							log.info("insert Ma NV: " + timekeepingDTO.getEmployeeId() + "|" + timekeepingDTO.getEmployeeName() + "|" + timekeepingDTO.getDate() + "|" + timekeepingDTO.getTimeIn());
							Object[] params = new Object[] { timekeepingDTO.getEmployeeId(), timekeepingDTO.getDate(),
									timekeepingDTO.getTimeIn(), timekeepingDTO.getTimeOut(), timekeepingDTO.getComeLateM(),
									timekeepingDTO.getLeaveSoonM(), timekeepingDTO.getComeLateA(),
									timekeepingDTO.getLeaveSoonA(), timekeepingDTO.getComment() };
							jdbcTmpl.update(sql, params);
						}else {
							// Gửi mail thông báo thông tin nhân viên từ máy chấm công không khớp 
							MimeMessage mimeMessage = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							String htmlMsg = "Thông tin mã và tên nhân viên từ máy chấm công không khớp với thông tin được tạo trên hệ thống phần mềm. <br/>\n "
									+ "Ngày: " + timekeepingDTO.getDate() + " <br/>\n "
									+ "Mã nhân viên: " + timekeepingDTO.getEmployeeId() + " <br/>\n "
									+ "Tên nhân viên: " + timekeepingDTO.getEmployeeName() + "<br/> <br/> \n"
									+ "Được inport bởi: (lấy từ phần phân quyền) <e-mail> lúc " + Calendar.getInstance().getTime();
							mimeMessage.setContent(htmlMsg,"text/html; charset=UTF-8");
							helper.setTo("tuyenpx.idigroup@gmail.com");
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
					Object[] params = new Object[] {timekeeping.getTimeOut(), timekeeping.getComeLateM(),
							timekeeping.getLeaveSoonM(), timekeeping.getComeLateA(), timekeeping.getLeaveSoonA(),
							timekeeping.getComment(), timekeeping.getEmployeeId(), timekeeping.getDate(),
							timekeeping.getTimeIn() };
					jdbcTmpl.update(sql, params);
				}else {
					log.info("insert Ma NV: " + timekeeping.getEmployeeId() + "|" + timekeeping.getDate() + "|" + timekeeping.getTimeIn());
					String sql = hr.getProperty("INSERT_TIMEKEEPING").toString();
					log.info("INSERT_TIMEKEEPING query: " + sql);
					if(timekeeping.getTimeIn() == null) {
						PropertiesManager hr = new PropertiesManager("hr.properties");
						if (timekeeping.getComment().equalsIgnoreCase("Request leave soon morning")) {
							timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_MORNING").toString());	
							System.err.println("Request leave soon morning" + timekeeping.getTimeIn());
						}
						else if (timekeeping.getComment().equalsIgnoreCase("Request leave soon afternoon")) {
							timekeeping.setTimeIn(hr.getProperty("TIME_CHECK_IN_AFTERNOON").toString());
							System.err.println("Request leave soon afternoon" + timekeeping.getTimeIn());
						}						
					}						
					Object[] params = new Object[] { timekeeping.getEmployeeId(), timekeeping.getDate(),
							timekeeping.getTimeIn(),// tam thoi bo 26/2/2018
							timekeeping.getTimeOut(), timekeeping.getComeLateM(),
							timekeeping.getLeaveSoonM(), timekeeping.getComeLateA(),
							timekeeping.getLeaveSoonA(), timekeeping.getComment() };
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
	public String getTimekeepingReport(String year, String month, String employeeIds, String leaveType) throws Exception {
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

		if (employeeIds != null && employeeIds.length() > 0)
			sql = sql + " AND EMPLOYEE_ID IN (" + employeeIds + ")";		

		log.info("GET_TIMEKEEPING_FOR_REPORT query: " + sql);

		Object[] params = new Object[] { year };
		countNumber = jdbcTmpl.queryForObject(sql, String.class, params);
		
		System.err.println(leaveType + ": " + countNumber);
		
		return countNumber;
	}
	
}
