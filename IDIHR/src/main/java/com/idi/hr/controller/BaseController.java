package com.idi.hr.controller;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author TruongNV
 */
 
public class BaseController {
     
    public static final String PARAM_BASE_URL = "baseURL";
         
    //get base URL
    public String getBaseURL(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
     
}
