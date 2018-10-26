package com.idi.task.login.dao;

import com.idi.task.login.bean.UserLogin;
import com.idi.task.login.bean.UserRoleBean;

public interface UserRoleDAO {
 public UserRoleBean getUserRoleByidUser(int idUser);
 
 public UserLogin getAUserLoginFull(String username) ;
}
