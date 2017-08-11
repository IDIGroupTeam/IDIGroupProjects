package com.idi.finance.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.idi.finance.dao.FinanceDAO;

public class FinanceDAOImpl implements FinanceDAO {
	private JdbcTemplate jdbcTmpl;

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}
}
