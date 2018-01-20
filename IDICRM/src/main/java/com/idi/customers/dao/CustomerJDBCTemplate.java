package com.idi.customers.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.customers.bean.Customer;

public class CustomerJDBCTemplate implements InterfaceCustomerDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemp;
    
	@Override
	public void setDataSource(DataSource ds) {
		 this.dataSource =ds;
		 this.jdbcTemp = new JdbcTemplate(ds);

	}

	@Override
	public void create(String name, String sdt) {
		String sqlQuery="insert into Customer(Ma_KH,Ten_KH,Dia_chi) value (?,?,?)";
		jdbcTemp.update(sqlQuery,name,sdt);
		System.out.println("Update customer");
		return;
	}

	@Override
	public Customer getCustomer(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> listCustomer() {
		String sqlList ="select * from Customer";
		List<Customer> listCust =jdbcTemp.query(sqlList, new CustomerMapper());
		return listCust;
	}

}
