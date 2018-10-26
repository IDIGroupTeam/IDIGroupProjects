package com.idi.task.login.dao;

import java.util.List;

import com.idi.task.login.bean.UserBean;

public interface UserDAO {
  public UserBean getUser(String username);
  public int getEmployeeID(String username);
  public void saveOrUpdate (UserBean userBean);
  public void delete(int userID);
  public List<UserBean> listUser();

}
