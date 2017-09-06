package com.idi.hr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.idi.hr.bean.JobTitle;

public class JobTitleMapper implements RowMapper<JobTitle> {

	public JobTitle mapRow(ResultSet rs, int nowNum) throws SQLException {
		String titleId = rs.getString("TITLE_ID");
		String titleName = rs.getString("TITLE_NAME");
		String description = rs.getString("DESCRIPTION");

		return new JobTitle(titleId, titleName, description);
	}

}
