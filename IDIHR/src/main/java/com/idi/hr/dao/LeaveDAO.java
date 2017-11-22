package com.idi.hr.dao;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.LeaveInfo;
import com.idi.hr.bean.LeaveType;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.LeaveInfoMapper;
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
			Object[] params = new Object[] { leaveInfo.getEmployeeId(), leaveInfo.getDate(), leaveInfo.getLeaveType(),
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
	public void deleteLeaveInfo(int employeeId, Date date, String leaveType) throws Exception {
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

}
