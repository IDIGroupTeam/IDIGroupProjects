package com.idi.finance.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.idi.finance.dao.KpiChartDAO;

@Controller
public class KyKeToanController {
	private static final Logger logger = Logger.getLogger(KyKeToanController.class);

	@Autowired
	KpiChartDAO kpiChartDAO;
}
