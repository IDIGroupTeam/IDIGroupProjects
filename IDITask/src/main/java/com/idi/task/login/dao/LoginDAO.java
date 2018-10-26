package com.idi.task.login.dao;

import com.idi.task.login.bean.UserLogin;

public interface LoginDAO {
 public int userID(String userName);
 public UserLogin getUserLogin(String userName);
 
}
