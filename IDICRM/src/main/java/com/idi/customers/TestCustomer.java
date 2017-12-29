package com.idi.customers;

import java.io.File;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.idi.customers.bean.Customer;
import com.idi.customers.dao.CustomerDAO;
import com.idi.customers.dao.CustomerJDBCTemplate;

public class TestCustomer {
public static void main (String[] args) {
	File f = new File("src/main/webapp/WEB-INF/spring-servlet.xml");
	System.out.println( f.getAbsolutePath() + "Exist test: " + f.exists());
	/*
	ApplicationContext context = new ClassPathXmlApplicationContext("src/main/webapp/WEB-INF/spring-servlet.xml");
	CustomerJDBCTemplate custJDBCTemp =( CustomerJDBCTemplate) context.getBean("custTmpl");
	System.out.println("Start to list records");
	custJDBCTemp.create("Tuyen", "0904555345");
	
	List<Customer> cust  =custJDBCTemp.listCustomer();
	for ( Customer record:cust) {
		System.out.print("Name" +record.getKhachhang());
	}
}
*/
	 CustomerDAO custDao = new CustomerDAO();
	 Customer cust = custDao.getCustomer(1);
	 System.out.println("Print Cust" + cust.getDiachi());
	 
	 
 } 
}
