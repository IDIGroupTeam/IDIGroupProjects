package com.idi.task.login.dao;

import java.util.List;

import com.idi.task.login.bean.RoleBean;

public interface RoleDAO {
 public List <RoleBean> getAllRole();
 public RoleBean getRoleByRoleID (int roleID);
}
