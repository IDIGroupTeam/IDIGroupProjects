package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.Insurance;
import com.idi.hr.bean.ProcessInsurance;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.InsuranceMapper;
import com.idi.hr.mapper.ProcessInsuranceMapper;

public class InsuranceDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(InsuranceDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public InsuranceDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get Insurances from DB
	 * 
	 * @return List of Insurance
	 * @throws Exception
	 */
	public List<Insurance> getInsurances() {

		String sql = hr.getProperty("GET_INSURANCES").toString();
		log.info("GET_INSURANCES query: " + sql);
		InsuranceMapper mapper = new InsuranceMapper();

		List<Insurance> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get Insurance by Social Insurance No
	 * 
	 * @param Insurance
	 *            No
	 * @return Insurance object
	 */
	public Insurance getInsurance(String SocialInsuranceNo) {

		String sql = hr.get("GET_INSURANCE").toString();
		log.info("GET_INSURANCE query: " + sql);
		Object[] params = new Object[] { SocialInsuranceNo };

		InsuranceMapper mapper = new InsuranceMapper();

		Insurance socialInsurance = jdbcTmpl.queryForObject(sql, params, mapper);

		return socialInsurance;

	}

	/**
	 * Insert a Insurance into database
	 * 
	 * @param Insurance
	 */
	public void insertInsurance(Insurance insurance) throws Exception {
		try {

			log.info("Insert new SocialInsurance ....");
			String sql = hr.getProperty("INSERT_INSURANCE").toString();
			log.info("INSERT_INSURANCE query: " + sql);
			Object[] params = new Object[] { insurance.getEmployeeId(), insurance.getSocicalInsuNo(),
					insurance.getSalarySocicalInsu(), insurance.getPercentSInsuC(), insurance.getPercentSInsuE(),
					insurance.getPayType(), insurance.getSalaryZone(), insurance.getPlace(), insurance.getStatus(),
					insurance.gethInsuNo(), insurance.gethInsuPlace(), insurance.getComment() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a Insurance into database
	 * 
	 * @param Insurance
	 */
	public void updateInsurance(Insurance insurance) throws Exception {
		try {

			log.info("Cập nhật thông tin bảo hiểm cho sổ BH số: " + insurance.getSocicalInsuNo() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_INSURANCE").toString();
			log.info("UPDATE_INSURANCE query: " + sql);
			Object[] params = new Object[] { insurance.getEmployeeId(), insurance.getSalarySocicalInsu(),
					insurance.getPercentSInsuC(), insurance.getPercentSInsuE(), insurance.getPayType(),
					insurance.getSalaryZone(), insurance.getPlace(), insurance.getStatus(), insurance.gethInsuNo(),
					insurance.gethInsuPlace(), insurance.getComment(), insurance.getSocicalInsuNo() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	// ------------------------------process insurance -------------------------//

	/**
	 * Get ProcessInsurances from DB
	 * 
	 * @return List of ProcessInsurance
	 * @param Insurance No
	 * @throws Exception
	 */
	public List<ProcessInsurance> getProcessInsurances(String SocialInsuranceNo) {

		String sql = hr.getProperty("GET_PROCESS_INSURANCES").toString();
		log.info("GET_PROCESS_INSURANCES query: " + sql);
		Object[] params = new Object[] { SocialInsuranceNo };
		ProcessInsuranceMapper mapper = new ProcessInsuranceMapper();

		List<ProcessInsurance> list = jdbcTmpl.query(sql, params, mapper);
		return list;

	}

	/**
	 * get ProcessInsurance by Social Insurance No
	 * 
	 * @param Insurance No, fromDate
	 * @return ProcessInsurance object
	 */
	public ProcessInsurance getProcessInsurance(String SocialInsuranceNo, String fromDate) {

		String sql = hr.get("GET_PROCESS_INSURANCE").toString();
		log.info("GET_PROCESS_INSURANCE query: " + sql);
		Object[] params = new Object[] { SocialInsuranceNo, fromDate };

		ProcessInsuranceMapper mapper = new ProcessInsuranceMapper();

		ProcessInsurance processInsurance = jdbcTmpl.queryForObject(sql, params, mapper);

		return processInsurance;

	}

	/**
	 * Insert a ProcessInsurance into database
	 * 
	 * @param Insurance
	 */
	public void insertProcessInsurance(ProcessInsurance processInsurance) throws Exception {
		try {

			log.info("Thêm thông tin qúa trình đóng BHXH....");
			String sql = hr.getProperty("INSERT_PROCESS_INSURANCE").toString();
			log.info("INSERT_PROCESS_INSURANCE query: " + sql);
			Object[] params = new Object[] { processInsurance.getSocicalInsuNo(),
					processInsurance.getSalarySocicalInsu(), processInsurance.getCompanyPay(),
					processInsurance.getFromDate(), processInsurance.getToDate(), processInsurance.getComment() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a ProcessInsurance into database
	 * 
	 * @param ProcessInsurance
	 */
	public void updateProcessInsurance(ProcessInsurance processInsurance) throws Exception {
		try {

			log.info("Cập nhật thông tin quá trình đóng BHXH. Số sổ: " + processInsurance.getSocicalInsuNo() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_PROCESS_INSURANCE").toString();
			log.info("UPDATE_PROCESS_INSURANCE query: " + sql);
			Object[] params = new Object[] { processInsurance.getSalarySocicalInsu(), processInsurance.getCompanyPay(),
					processInsurance.getToDate(), processInsurance.getComment(), processInsurance.getSocicalInsuNo(),
					processInsurance.getFromDate() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Delete a ProcessInsurance from database
	 * 
	 * @param socicalInsuNo,
	 *            fromDate
	 */
	public void deleteProcessInsurance(String socicalInsuNo, String fromDate) throws Exception {
		try {

			log.info("Xóa thông tin quá trình đóng BHXH. Số sổ: " + socicalInsuNo + " từ tháng: " + fromDate);
			// delete
			String sql = hr.getProperty("DELETE_PROCESS_INSURANCE").toString();
			log.info("DELETE_PROCESS_INSURANCE query: " + sql);
			Object[] params = new Object[] { socicalInsuNo, fromDate };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

}
