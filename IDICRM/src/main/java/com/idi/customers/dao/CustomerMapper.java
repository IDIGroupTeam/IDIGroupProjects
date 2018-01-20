package com.idi.customers.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.idi.customers.bean.Customer;

public class CustomerMapper implements RowMapper<Customer> {
	public Customer mapRow (ResultSet rs, int rowNum) throws SQLException {
		try {
			Customer cust = new Customer();
			cust.setMaKH(rs.getInt("Ma_KH"));
			cust.setKhachhang(rs.getString("TEN_KH"));
			cust.setSoDT(rs.getString("SDT"));
			cust.setDiachi(rs.getString("DIA_CHI"));	
			cust.setEmail(rs.getString("EMAIL"));
			cust.setMaSThue(rs.getString("MA_THUE"));
			cust.setWebsite(rs.getString("WEBSITE"));
			return cust;
		}catch  (Exception e) {
			return null;
		}
	}

}
