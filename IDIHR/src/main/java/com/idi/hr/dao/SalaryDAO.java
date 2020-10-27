package com.idi.hr.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.hr.bean.Salary;
import com.idi.hr.bean.SalaryDetail;
import com.idi.hr.bean.SalaryReport;
import com.idi.hr.bean.SalaryReportPerEmployee;
import com.idi.hr.common.PropertiesManager;
import com.idi.hr.mapper.SalaryDetailMapper;
import com.idi.hr.mapper.SalaryMapper;
import com.idi.hr.mapper.SalaryReportMapper;
import com.idi.hr.mapper.SalaryReportPerEmployeeMapper;

public class SalaryDAO extends JdbcDaoSupport {

	private static final Logger log = Logger.getLogger(SalaryDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public SalaryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	PropertiesManager hr = new PropertiesManager("hr.properties");

	/**
	 * Get list Salary from DB
	 * 
	 * @return List of Salary
	 * @throws Exception
	 */
	public List<Salary> getSalarys() {

		String sql = hr.getProperty("GET_LIST_SALARY_INFO").toString();
		log.info("GET_LIST_SALARY_INFO query: " + sql);
		SalaryMapper mapper = new SalaryMapper();

		List<Salary> list = jdbcTmpl.query(sql, mapper);
		return list;

	}

	/**
	 * get Salary by employeeId
	 * 
	 * @param employeeId
	 * @return Salary object
	 */
	public Salary getSalary(int employeeId) {

		String sql = hr.get("GET_SALARY_INFO").toString();
		log.info("GET_SALARY_INFO query: " + sql);
		Object[] params = new Object[] { employeeId };

		SalaryMapper mapper = new SalaryMapper();

		Salary salary = jdbcTmpl.queryForObject(sql, params, mapper);

		return salary;
	}

	/**
	 * Insert a Salary into database
	 * 
	 * @param Salary
	 */
	public void insertSalary(Salary salary) throws Exception {
		try {

			log.info("Thêm mới thông tin lương của nhân viên ....");
			String sql = hr.getProperty("INSERT_SALARY_INFO").toString();
			log.info("INSERT_SALARY_INFO query: " + sql);
			Object[] params = new Object[] { salary.getEmployeeId(), salary.getSalary().replaceAll(",", ""), 
					salary.getMoneyType(), salary.getBankNo(),	salary.getBankName(), salary.getBankBranch(), salary.getDesc() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update a Salary into database
	 * 
	 * @param Salary
	 */
	public void updateSalary(Salary salary) throws Exception {
		try {
			log.info("Cập nhật thông tin lương của NV có mã NV: " + salary.getEmployeeId() + " ....");
			// update
			String sql = hr.getProperty("UPDATE_SALARY_INFO").toString();
			log.info("UPDATE_SALARY_INFO query: " + sql);
			Object[] params = new Object[] { salary.getSalary().replaceAll(",", ""), salary.getMoneyType(), salary.getBankNo(), 
					salary.getBankName(), salary.getBankBranch(), salary.getDesc(), salary.getEmployeeId() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	// ------------------------------Salary for moth -------------------------//
	/**
	 * Get list SalaryDetail from DB
	 * 
	 * @return List of SalaryDetail
	 * @param employeeId
	 * @throws Exception
	 */
	public List<SalaryDetail> getSalaryDetails(int employeeId) {
		String sql = hr.getProperty("GET_LIST_SALARY_DETAIL").toString();
		log.info("GET_LIST_SALARY_DETAIL query: " + sql);
		Object[] params = new Object[] { employeeId };
		SalaryDetailMapper mapper = new SalaryDetailMapper();

		List<SalaryDetail> list = jdbcTmpl.query(sql, params, mapper);
		return list;
	}

	/**
	 * get SalaryDetail detail for month
	 * 
	 * @param employeeId, month, year
	 * @return SalaryDetail object
	 */
	public SalaryDetail getSalaryDetail(int employeeId, int month, int year) {

		SalaryDetail salaryDetail = null;
		if (month > 0 && year > 0) {
			String sql = hr.get("GET_SALARY_DETAIL").toString();
			log.info("GET_SALARY_DETAIL query: " + sql);
			Object[] params = new Object[] { employeeId, month, year };
			SalaryDetailMapper mapper = new SalaryDetailMapper();
			salaryDetail = jdbcTmpl.queryForObject(sql, params, mapper);
		} else {
			String sql = hr.get("GET_SALARY_FOR_INSERT").toString();
			log.info("GET_SALARY_FOR_INSERT query: " + sql);
			Object[] params = new Object[] { employeeId };
			SalaryDetailMapper mapper = new SalaryDetailMapper();
			salaryDetail = jdbcTmpl.queryForObject(sql, params, mapper);
		}		

		if(salaryDetail.getPercentEmployeePay() != null && salaryDetail.getPercentEmployeePay().length() > 0 && salaryDetail.getPercentEmployeePay().contains(","))
			salaryDetail.setPercentEmployeePay(salaryDetail.getPercentEmployeePay().replaceAll(",", "."));
		
		return salaryDetail;
	}

	/**
	 * 
	 * @param employeeId
	 * @param month
	 * @param year
	 * @return
	 */
	public SalaryReport getSalaryReport(int employeeId, String month, String year) {

		SalaryReport salaryReport = null;
		String sql = hr.get("GET_SUMMARY_SALARY").toString();
		if (month != null && month.length() > 0 && Integer.parseInt(month) > 0) {
			sql = sql + " AND MONTH=" + month;			
		}
		if (employeeId > 0) {
			sql = sql + " AND EMPLOYEE_ID=" + employeeId;
		}
		log.info("GET_SUMMARY_SALARY query: " + sql);
		
		Object[] params = new Object[] { year };
		SalaryReportMapper mapper = new SalaryReportMapper();
		salaryReport = jdbcTmpl.queryForObject(sql, params, mapper);

		return salaryReport;
	}

	/**
	 * @param dept
	 * @param year
	 * @return
	 */
	public List<SalaryReportPerEmployee> getSalaryReportDetail(String year, String dept) {

		String sql = hr.get("GET_SUMMARY_SALARY_DETAIL_FOR_YEAR").toString();
		
		if(dept != null && !dept.equalsIgnoreCase("all"))
			sql = sql.replaceAll("%DEPT%", " AND E.DEPARTMENT = '" + dept + "' ");
		else
			sql = sql.replaceAll("%DEPT%", " ");
		
		log.info("GET_SUMMARY_SALARY_DETAIL_FOR_YEAR query: " + sql);
		 
		Object[] params = new Object[] { year };
		SalaryReportPerEmployeeMapper mapper = new SalaryReportPerEmployeeMapper();
		List<SalaryReportPerEmployee> list = jdbcTmpl.query(sql, params, mapper); 

		return list;
	}
	
	/**
	 * countMonthPerYearByEmp
	 * @param empId
	 * @param year
	 * @return number
	 */
	public int countMonthPerYearByEmp(int empId, String year) {

		String sql = hr.get("COUNT_MONTH_PER_YEAR_BY_EMPLOYEE").toString();
		log.info("COUNT_MONTH_PER_YEAR_BY_EMPLOYEE query: " + sql);
		Object[] params = new Object[] {empId, year};
		
		String numberEmployee = jdbcTmpl.queryForObject(sql, String.class, params);
		
		return Integer.parseInt(numberEmployee);
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @param dept
	 * @return
	 */
	public List<SalaryDetail> getSalaryReportDetail(String month, String year, String dept) {

		String sql = hr.get("GET_SUMMARY_SALARY_DETAIL").toString();
		
		if(dept != null && !dept.equalsIgnoreCase("all"))
			sql = sql.replaceAll("%DEPT%", " AND E.DEPARTMENT = '" + dept + "' ");
		else
			sql = sql.replaceAll("%DEPT%", " ");
		
		log.info("GET_SUMMARY_SALARY_DETAIL query: " + sql);
		
		Object[] params = new Object[] { month, year };
		SalaryDetailMapper mapper = new SalaryDetailMapper();
		List<SalaryDetail> list = jdbcTmpl.query(sql, params, mapper); 

		return list;
	}
	
	/**
	 * Insert a SalaryDetail into database
	 * 
	 * @param SalaryDetail
	 */
	public void insertSalaryDetail(SalaryDetail salaryDetail) throws Exception {
		try {

			log.info("Thêm thông tin chi tiết lương tháng của nhân viên....");
			String sql = hr.getProperty("INSERT_SALARY_DETAIL").toString();
			log.info("INSERT_SALARY_DETAIL query: " + sql);
			//System.err.println("salary " + salaryDetail.getSalary());
			// Tính lương thực nhận
			float finalSalary = 0;
			if(salaryDetail.getSalaryForWorkedDay() != null) {
				finalSalary = Float.valueOf(salaryDetail.getSalaryForWorkedDay());
			}else {
				 finalSalary = Float.valueOf(salaryDetail.getBasicSalary());
			}
			if(salaryDetail.getWorkComplete() >= 0) {
				finalSalary=finalSalary*salaryDetail.getWorkComplete()/100;
				//System.err.println(8000000*120/100);
			}
			// Tăng
			float salaryPerHour = 0;
			if (salaryDetail.getSalaryPerHour() > 0)
				salaryPerHour = salaryDetail.getSalaryPerHour();
			if (salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getBounus().replaceAll(",", ""));
			if (salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getSubsidize().replaceAll(",", ""));
			
			if (salaryDetail.getOverTimeN() != null && salaryDetail.getOverTimeN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeN()) * (float) 1.5;
			}
			if (salaryDetail.getOverTimeW() != null && salaryDetail.getOverTimeW().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeW()) * 2;
			}
			if (salaryDetail.getOverTimeH() != null && salaryDetail.getOverTimeH().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeH()) * 3;
			}
			
			if (salaryDetail.getOverTimeNN() != null && salaryDetail.getOverTimeNN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeNN()) * (float) 1.8;
			}
			if (salaryDetail.getOverTimeWN() != null && salaryDetail.getOverTimeWN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeWN()) * (float) 2.3;
			}
			if (salaryDetail.getOverTimeHN() != null && salaryDetail.getOverTimeHN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeHN()) * (float) 3.3;
			}
			
			if (salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0) {
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getOther().replaceAll(",", ""));
			}
			// Giảm
			if (salaryDetail.getArrears() != null && salaryDetail.getArrears().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getArrears().replaceAll(",", ""));
			if (salaryDetail.getTaxPersonal() != null && salaryDetail.getTaxPersonal().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getTaxPersonal().replaceAll(",", ""));
			if (salaryDetail.getAdvancePayed() != null && salaryDetail.getAdvancePayed().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getAdvancePayed().replaceAll(",", ""));
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getPayedInsurance());

			salaryDetail.setFinalSalary(String.valueOf(finalSalary));

			//System.err.println("OverTimeSalary " + salaryDetail.getOverTimeSalary());

			if(salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
				salaryDetail.setBounus(salaryDetail.getBounus().replaceAll(",", ""));
			if(salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
				salaryDetail.setSubsidize(salaryDetail.getSubsidize().replaceAll(",", ""));
			if(salaryDetail.getAdvancePayed() != null && salaryDetail.getAdvancePayed().length() > 0)
				salaryDetail.setAdvancePayed(salaryDetail.getAdvancePayed().replaceAll(",", ""));
			if(salaryDetail.getTaxPersonal() != null && salaryDetail.getTaxPersonal().length() > 0)
				salaryDetail.setTaxPersonal(salaryDetail.getTaxPersonal().replaceAll(",", ""));
			if(salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0)
				salaryDetail.setOther(salaryDetail.getOther().replaceAll(",", ""));
			if(salaryDetail.getArrears() != null && salaryDetail.getArrears().length() > 0)
				salaryDetail.setArrears(salaryDetail.getArrears().replaceAll(",", ""));
			//update ... lay salary o bang salary info sang bang salary detail lam basic salary
			Object[] params = new Object[] { salaryDetail.getEmployeeId(), salaryDetail.getOverTimeN(),
					salaryDetail.getOverTimeW(), salaryDetail.getOverTimeH(), salaryDetail.getOverTimeNN(),
					salaryDetail.getOverTimeWN(), salaryDetail.getOverTimeHN(), salaryDetail.getOverTimeSalary(),
					salaryDetail.getBounus(), salaryDetail.getSubsidize(), salaryDetail.getAdvancePayed(),
					salaryDetail.getTaxPersonal(), salaryDetail.getBasicSalary(), salaryDetail.getExchangeRate(),
					salaryDetail.getFinalSalary(), salaryDetail.getMonth(), salaryDetail.getYear(), salaryDetail.getDesc(), 
					salaryDetail.getPayedInsurance(), salaryDetail.getcPayedInsur(), salaryDetail.getWorkComplete(), 
					salaryDetail.getWorkedDay(), salaryDetail.getOther(), salaryDetail.getArrears(), salaryDetail.getPayStatus() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			try {
				e.printStackTrace();
				updateSalaryDetail(salaryDetail);
			} catch (Exception ex) {
				log.error(ex, ex);
				throw ex;
			}
		}
	}

	/**
	 * Update a SalaryDetail into database
	 * 
	 * @param SalaryDetail
	 */
	public void updateSalaryDetail(SalaryDetail salaryDetail) throws Exception {
		try {

			log.info("Cập nhật thông tin chi tiết lương tháng " + salaryDetail.getMonth() + ", "
					+ salaryDetail.getYear() + ", mã NV " + salaryDetail.getEmployeeId());
			String sql = hr.getProperty("UPDATE_SALARY_DETAIL").toString();
			log.info("UPDATE_SALARY_DETAIL query: " + sql);

			// Tính lương thực nhận
			float finalSalary = 0;
			if(salaryDetail.getSalaryForWorkedDay() != null) {
				finalSalary = Float.valueOf(salaryDetail.getSalaryForWorkedDay());
			}else {
				 finalSalary = Float.valueOf(salaryDetail.getBasicSalary());
			}
			if(salaryDetail.getWorkComplete() >= 0) {
				finalSalary = finalSalary*salaryDetail.getWorkComplete()/100;				
			}
			//System.out.printf("%.2f", finalSalary);
			
			// Tang
			float salaryPerHour = 0;
			if (salaryDetail.getSalaryPerHour() > 0)
				salaryPerHour = salaryDetail.getSalaryPerHour();
			if (salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getBounus().replaceAll(",", ""));
			if (salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getSubsidize().replaceAll(",", ""));
			
			if (salaryDetail.getOverTimeN() != null && salaryDetail.getOverTimeN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeN()) * (float) 1.5;
			}
			if (salaryDetail.getOverTimeW() != null && salaryDetail.getOverTimeW().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeW()) * 2;
			}
			if (salaryDetail.getOverTimeH() != null && salaryDetail.getOverTimeH().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeH()) * 3;
			}
			
			if (salaryDetail.getOverTimeNN() != null && salaryDetail.getOverTimeNN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeNN()) * (float) 1.8;
			}
			if (salaryDetail.getOverTimeWN() != null && salaryDetail.getOverTimeWN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeWN()) * (float) 2.3;
			}
			if (salaryDetail.getOverTimeHN() != null && salaryDetail.getOverTimeHN().length() > 0) {
				finalSalary = finalSalary + salaryPerHour * Float.valueOf(salaryDetail.getOverTimeHN()) * (float) 3.3;
			}
			
			if (salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0) {
				finalSalary = finalSalary + Float.valueOf(salaryDetail.getOther().replaceAll(",", ""));
			}
			// Giảm
			if (salaryDetail.getArrears() != null && salaryDetail.getArrears().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getArrears().replaceAll(",", ""));
			if (salaryDetail.getTaxPersonal() != null && salaryDetail.getTaxPersonal().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getTaxPersonal().replaceAll(",", ""));
			if (salaryDetail.getAdvancePayed() != null && salaryDetail.getAdvancePayed().length() > 0)
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getAdvancePayed().replaceAll(",", ""));
			if (salaryDetail.getSalaryInsurance() != null && salaryDetail.getSalaryInsurance().length() > 0) {
				//System.err.println(salaryDetail.getSalaryInsurance());
				finalSalary = finalSalary - Float.valueOf(salaryDetail.getPayedInsurance());
			}

			salaryDetail.setFinalSalary(String.valueOf(finalSalary));
			//System.err.println("year " + salaryDetail.getYear());
			//System.out.printf("%.2f", finalSalary);
			
			if(salaryDetail.getBounus() != null && salaryDetail.getBounus().length() > 0)
				salaryDetail.setBounus(salaryDetail.getBounus().replaceAll(",", ""));
			if(salaryDetail.getSubsidize() != null && salaryDetail.getSubsidize().length() > 0)
				salaryDetail.setSubsidize(salaryDetail.getSubsidize().replaceAll(",", ""));
			if(salaryDetail.getAdvancePayed() != null && salaryDetail.getAdvancePayed().length() > 0)
				salaryDetail.setAdvancePayed(salaryDetail.getAdvancePayed().replaceAll(",", ""));
			if(salaryDetail.getTaxPersonal() != null && salaryDetail.getTaxPersonal().length() > 0)
				salaryDetail.setTaxPersonal(salaryDetail.getTaxPersonal().replaceAll(",", ""));
			if(salaryDetail.getOther() != null && salaryDetail.getOther().length() > 0)
				salaryDetail.setOther(salaryDetail.getOther().replaceAll(",", ""));
			if(salaryDetail.getArrears() != null && salaryDetail.getArrears().length() > 0)
				salaryDetail.setArrears(salaryDetail.getArrears().replaceAll(",", ""));
			
			Object[] params = new Object[] { salaryDetail.getOverTimeN(), salaryDetail.getOverTimeW(),
					salaryDetail.getOverTimeH(), salaryDetail.getOverTimeNN(), salaryDetail.getOverTimeWN(), 
					salaryDetail.getOverTimeHN(), salaryDetail.getOverTimeSalary(), salaryDetail.getBounus(),
					salaryDetail.getSubsidize(), salaryDetail.getAdvancePayed(), salaryDetail.getTaxPersonal(),
					salaryDetail.getBasicSalary(), salaryDetail.getExchangeRate(), salaryDetail.getFinalSalary(),
					salaryDetail.getDesc(),	salaryDetail.getPayedInsurance(), salaryDetail.getcPayedInsur(), 
					salaryDetail.getWorkComplete(), salaryDetail.getWorkedDay(), salaryDetail.getOther(), salaryDetail.getArrears(),
					salaryDetail.getPayStatus(), salaryDetail.getEmployeeId(), salaryDetail.getMonth(), salaryDetail.getYear() };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

	/**
	 * Update total salary take home
	 * 
	 * @param finalSalary, month, year, employeeId
	 */
	public void updateSalaryTakeHome(String finalSalary, int month, int year, int employeeId) throws Exception {
		try {

			log.info("Cập nhật thông tin lương thực nhận tháng " + month + ", " + year + ", mã NV " + employeeId);
			// update
			String sql = hr.getProperty("UPDATE_SALARY_DETAIL_FINAL").toString();
			log.info("UPDATE_SALARY_DETAIL_FINAL query: " + sql);
			Object[] params = new Object[] { finalSalary, employeeId, month, year };
			jdbcTmpl.update(sql, params);

		} catch (Exception e) {
			log.error(e, e);
			throw e;
		}
	}

}
