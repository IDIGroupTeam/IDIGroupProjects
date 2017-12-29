package com.idi.customers.dao;

import java.util.List;

import javax.sql.DataSource;

import com.idi.customers.bean.Customer;

public interface InterfaceCustomerDAO {
public void setDataSource (DataSource ds);
public void create (String name, String sdt);
public  Customer getCustomer(Integer id);
public List<Customer> listCustomer ();
}
