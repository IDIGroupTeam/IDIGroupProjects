package com.idi.task.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MailListMapping implements RowMapper<String>  {
	public String mapRow(ResultSet rs, int nowNum) throws SQLException {
		String creater = rs.getString("CREATED_BY");
		String owner = rs.getString("OWNED_BY");
		String backup = rs.getString("SECOND_OWNER");
		String verify = rs.getString("VERIFY_BY");
		String subscriber = rs.getString("SUBSCRIBER");

		return new String(creater + "," + owner + ","+ backup + "," + verify + "," + subscriber);
	}

}
