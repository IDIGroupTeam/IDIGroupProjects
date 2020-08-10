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
import com.idi.hr.common.Utils;
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
	public List<Insurance> getInsurances(String searchValue) {

		String sql = hr.getProperty("GET_INSURANCES").toString();
		
		if(searchValue != null) {
			sql = sql.replaceAll("%SEARCH_VALUE%", "AND ( I.EMPLOYEE_ID='" + searchValue.trim() + "' OR E.FULL_NAME LIKE '%" +  searchValue.trim() + "%' OR I.SOCIAL_INSU_NO LIKE '%" + searchValue.trim() + "%' )");
			
		}else {
			sql = sql.replaceAll("%SEARCH_VALUE%", "");
		}
			
		log.info("GET_INSURANCES query: " + sql);
		InsuranceMapper mapper = new InsuranceMapper();

		List<Insurance> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get Insurance by Social Insurance No
	 * 
	 * @param Insurance No
	 * @return Insurance object
	 */
	public Insurance getInsurance(String SocialInsuranceNo) throws Exception{
		Insurance socialInsurance = new Insurance();
		try {
			String sql = hr.get("GET_INSURANCE").toString();
			log.info("GET_INSURANCE query: " + sql);
			Object[] params = new Object[] { SocialInsuranceNo };
	
			InsuranceMapper mapper = new InsuranceMapper();
	
			socialInsurance = jdbcTmpl.queryForObject(sql, params, mapper);
			if(socialInsurance.getPercentSInsuC() != null && socialInsurance.getPercentSInsuC().length() > 0 && socialInsurance.getPercentSInsuC().contains(",")) {
				socialInsurance.setPercentSInsuC(socialInsurance.getPercentSInsuC().replaceAll(",", "."));				
			}if(socialInsurance.getPercentSInsuE() != null && socialInsurance.getPercentSInsuE().length() > 0 && socialInsurance.getPercentSInsuE().contains(",")) {
				socialInsurance.setPercentSInsuE(socialInsurance.getPercentSInsuE().replaceAll(",", "."));
			}
		} catch (Exception e) {			
			socialInsurance.setEmployeeId(0);
		}
		
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
					insurance.getSalarySocicalInsu().replaceAll(",", ""), insurance.getPercentSInsuC(), insurance.getPercentSInsuE(),
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
	public int updateInsurance(Insurance insurance) throws Exception {
		try {

			log.info("Cập nhật thông tin bảo hiểm cho sổ BH số: " + insurance.getSocicalInsuNo() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_INSURANCE").toString();
			log.info("UPDATE_INSURANCE query: " + sql);
			Object[] params = new Object[] { insurance.getEmployeeId(), insurance.getSalarySocicalInsu().replaceAll(",", ""),
					insurance.getPercentSInsuC(), insurance.getPercentSInsuE(), insurance.getPayType(),
					insurance.getSalaryZone(), insurance.getPlace(), insurance.getStatus(), insurance.gethInsuNo(),
					insurance.gethInsuPlace(), insurance.getComment(), insurance.getSocicalInsuNo() };
			jdbcTmpl.update(sql, params);
			return 1;
		} catch (Exception e) {
			log.error(e, e);
			return 0;
		}
	}

	/**
	 * Update SALARY Insurance into database
	 * 
	 * @param Insurance
	 */
	public void updateSalaryInsurance(Insurance insurance) throws Exception {
		try {
			log.info("Cập nhật lương bảo hiểm cho sổ BH số: " + insurance.getSocicalInsuNo() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_INSURANCE_SALARY").toString();
			log.info("UPDATE_INSURANCE_SALARY query: " + sql);
			Object[] params = new Object[] {insurance.getSalarySocicalInsu().replaceAll(",", ""), insurance.getSocicalInsuNo() };
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
			if(processInsurance.getFromDate() != null && processInsurance.getFromDate().length() > 0 && processInsurance.getFromDate().contains("/"))
				processInsurance.setFromDate(Utils.convertDateToStore(processInsurance.getFromDate()));
			if(processInsurance.getToDate() != null && processInsurance.getToDate().length() > 0 && processInsurance.getToDate().contains("/"))
				processInsurance.setToDate(Utils.convertDateToStore(processInsurance.getToDate()));
			Object[] params = new Object[] { processInsurance.getSocicalInsuNo(),
					processInsurance.getSalarySocicalInsu().replaceAll(",", ""), processInsurance.getCompanyPay(),
					processInsurance.getFromDate(), processInsurance.getToDate(), processInsurance.getComment() };
			jdbcTmpl.update(sql, params);
			
			//update Insurance

			Insurance insurance= new Insurance();
			insurance.setSocicalInsuNo(processInsurance.getSocicalInsuNo());
			insurance.setSalarySocicalInsu(processInsurance.getSalarySocicalInsu().replaceAll(",", ""));
			updateSalaryInsurance(insurance);
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
			
			if(processInsurance.getFromDate() != null && processInsurance.getFromDate().length() > 0 && processInsurance.getFromDate().contains("/"))
				processInsurance.setFromDate(Utils.convertDateToStore(processInsurance.getFromDate()));
			if(processInsurance.getToDate() != null && processInsurance.getToDate().length() > 0 && processInsurance.getToDate().contains("/"))
				processInsurance.setToDate(Utils.convertDateToStore(processInsurance.getToDate()));
			
			Object[] params = new Object[] { processInsurance.getSalarySocicalInsu().replaceAll(",", ""), processInsurance.getCompanyPay(),
					processInsurance.getToDate(), processInsurance.getComment(), processInsurance.getSocicalInsuNo(),
					processInsurance.getFromDate() };
			jdbcTmpl.update(sql, params);

			Insurance insurance= new Insurance();
			insurance.setSocicalInsuNo(processInsurance.getSocicalInsuNo());
			insurance.setSalarySocicalInsu(processInsurance.getSalarySocicalInsu().replaceAll(",", ""));
			updateSalaryInsurance(insurance);			
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
