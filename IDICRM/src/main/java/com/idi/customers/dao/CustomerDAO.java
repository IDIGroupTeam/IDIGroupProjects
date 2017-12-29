package com.idi.customers.dao;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.idi.customers.bean.Customer;


public class CustomerDAO  extends JdbcDaoSupport  {

	public CustomerDAO()  {
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = Logger.getLogger(CustomerDAO.class.getName());

	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	@Autowired
	public CustomerDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	public Customer getCustomer(Integer maKH) {
		String sql = "Select * from IDIGROUP.KHACH_HANG where Ma_KH=" + maKH  ;
		log.info("SQL:" + sql);
		Customer cust = jdbcTmpl.queryForObject(sql, new Object[] {maKH}, new CustomerMapper());
		return cust;
		
	}

	
}
