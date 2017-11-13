package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.EmployeeInfo;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.EmployeeMapper;

public class EmployeeDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(EmployeeDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public EmployeeDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * get employee by employeeId
	 * 
	 * @param employeeId
	 * @return employee object
	 * @throws AppException
	 *             If having any checked exception
	 * @throws SysException
	 *             If runtime exception happen
	 */
	public EmployeeInfo getEmployee(String employeeId) {

		String sql = hr.get("GET_EMPLOYEE_INFO").toString();
		log.info("GET_EMPLOYEE_INFO query: " + sql);
		Object[] params = new Object[] { employeeId };

		EmployeeMapper mapper = new EmployeeMapper();

		EmployeeInfo employee = jdbcTmpl.queryForObject(sql, params, mapper);

		return employee;

	}
	
	/**
	 * get employee by employeeId
	 * 
	 * @param account
	 * @return int
	 *
	 */
	public int getAccount(String account) {

		String sql = hr.get("CHECK_ACCOUNT_DUPLICATE").toString();
		log.info("CHECK_ACCOUNT_DUPLICATE query: " + sql);
		Object[] params = new Object[] { account };
		
		String accountNumber = jdbcTmpl.queryForObject(sql, String.class, params);
		
		//System.err.println(accountNumber);
		return Integer.parseInt(accountNumber);

	}

	/**
	 * Insert or update a employee into database
	 * 
	 * @param employeeInfo
	 * @throws AppException
	 *             If having any checked exception
	 * @throws SysException
	 *             If runtime exception happen
	 */
	public void insertOrUpdateEmployee(EmployeeInfo employeeInfo) throws Exception {
		try {
			if (employeeInfo.getEmployeeId() > 0) {
				// update
				String sql = hr.getProperty("UPDATE_EMPLOYEE_INFO").toString();
				log.info("UPDATE_EMPLOYEE_INFO query: " + sql);
				Object[] params = new Object[] { employeeInfo.getFullName(),
						employeeInfo.getGender(), employeeInfo.getJobTitle(), employeeInfo.getWorkStatus(),
						employeeInfo.getDOB(), employeeInfo.getMaritalStatus(), employeeInfo.getLoginAccount(),
						employeeInfo.getPersonalId(), employeeInfo.getIssueDate(), employeeInfo.getDepartment(),
						employeeInfo.getPhoneNo(), employeeInfo.getJoinDate(), employeeInfo.getOfficalJoinDate(),
						employeeInfo.getEmail(), employeeInfo.getTerminationDate(), employeeInfo.getReasonforLeave(),
						employeeInfo.getCurrentAdress(), employeeInfo.getPermanentAdress(), employeeInfo.getNote(),
						employeeInfo.getNation(),// employeeInfo.getImage(), 
						employeeInfo.getEmerName(),
						employeeInfo.getEmerPhoneNo(), employeeInfo.getBankNo(), employeeInfo.getBankName(),
						employeeInfo.getBankBranch(), employeeInfo.getImagePath(), employeeInfo.getSalarySocicalInsu(),
						employeeInfo.getSocicalInsuNo(), employeeInfo.getHealthInsuNo(),
						employeeInfo.getPercentSocicalInsu(), employeeInfo.getEmployeeId()};
				jdbcTmpl.update(sql, params);
			} else {
				// insert
				String sql = hr.getProperty("INSERT_EMPLOYEE_INFO").toString();
				log.info("INSERT_EMPLOYEE_INFO query: " + sql);
				Object[] params = new Object[] { employeeInfo.getFullName(),
						employeeInfo.getGender(), employeeInfo.getJobTitle(), employeeInfo.getWorkStatus(),
						employeeInfo.getDOB(), employeeInfo.getMaritalStatus(), employeeInfo.getLoginAccount(),
						employeeInfo.getPersonalId(), employeeInfo.getIssueDate(), employeeInfo.getDepartment(),
						employeeInfo.getPhoneNo(), employeeInfo.getJoinDate(), employeeInfo.getOfficalJoinDate(),
						employeeInfo.getEmail(), employeeInfo.getTerminationDate(), employeeInfo.getReasonforLeave(),
						employeeInfo.getCurrentAdress(), employeeInfo.getPermanentAdress(), employeeInfo.getNote(),
						employeeInfo.getNation(),// employeeInfo.getImage(), 
						employeeInfo.getEmerName(),
						employeeInfo.getEmerPhoneNo(), employeeInfo.getBankNo(), employeeInfo.getBankName(),
						employeeInfo.getBankBranch(), employeeInfo.getImagePath(), employeeInfo.getSalarySocicalInsu(),
						employeeInfo.getSocicalInsuNo(), employeeInfo.getHealthInsuNo(),
						employeeInfo.getPercentSocicalInsu() };
				jdbcTmpl.update(sql, params);

				// if (employeeInfo.getImage() != null) {
				// stmt.setBinaryStream(++i, new ByteArrayInputStream(employeeInfo.getImage()),
				// employeeInfo.getImage().length);
				// }
			}

		} catch (Exception e) {
			log.error(e, e);
			throw e;

		}
	}

	/**
	 * Get employees from DB
	 * 
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployees() {

		String sql = hr.getProperty("GET_EMPLOYEES").toString();
		log.info("GET_EMPLOYEES query: " + sql);
		Object[] params = new Object[] {};
		EmployeeMapper mapper = new EmployeeMapper();

		List<EmployeeInfo> list = jdbcTmpl.query(sql, params, mapper);

		return list;

	}

	
	/**
	 * Get employees from DB
	 * @param department
	 * @return List of employee
	 * @throws Exception
	 */
	public List<EmployeeInfo> getEmployeesByDepartment(String department) {

		String sql = hr.getProperty("GET_EMPLOYEES_BY_DEPARTMENT").toString();
		log.info("GET_EMPLOYEES_BY_DEPARTMENT query: " + sql);
		Object[] params = new Object[] {department};
		EmployeeMapper mapper = new EmployeeMapper();

		List<EmployeeInfo> list = jdbcTmpl.query(sql, params, mapper);

		return list;

	}
}
