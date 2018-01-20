package com.idi.hr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesManager extends Properties {

	private static final long serialVersionUID = 1L;
	private String propPath = "";
	private static Logger logger = Logger.getLogger(PropertiesManager.class.getName());

	public String getPropPath() {
		return propPath;
	}

	public void setPropPath(String propPath) {
		this.propPath = propPath;
	}

	/**
	 * Name of the properties file
	 */
	private String name = null;

	/**
	 * Whether to send out email if no exist
	 */
	private boolean isErrorIfNoExist = true;

	/**
	 * PropertiesManager
	 * 
	 * @param propertiesFileName
	 * Name of the properties file to load, sends out warning if it doesn't exist
	 */
	public PropertiesManager(String propertiesFileName, String path) {
		this(propertiesFileName, true, path);
		propPath = path;

	}

	public PropertiesManager(String propertiesFileName) {
		this(propertiesFileName, true);
	}

	public PropertiesManager(String propertiesFileName, boolean isErrorIfNoExist, String path) {
		super();
		this.propPath = path;
		this.name = propertiesFileName;
		this.isErrorIfNoExist = isErrorIfNoExist;
		load(propertiesFileName);
	}

	/**
	 * PropertiesManager
	 * 
	 * @param propertiesFileName         
	 * Name of the properties file to load, sends out warning if it doesn't exist
	 * @param isErrorIfNoExist       
	 * Is an error if it doesn't exist
	 */
	public PropertiesManager(String propertiesFileName, boolean isErrorIfNoExist) {
		super();
		this.name = propertiesFileName;
		this.isErrorIfNoExist = isErrorIfNoExist;
		load(propertiesFileName);
	}

	/**
	 * getName
	 * 
	 * @return Name of the properties file
	 */
	public String getName() {
		return name;
	}

	/**
	 * load
	 * 
	 * @param propertiesFileName
	 * Name of the properties file to load
	 */
	public void load(String propertiesFileName) {
		try {

			InputStream is = null;
			File file = new File(propPath + propertiesFileName);
			boolean fileExists = file.exists();

			if (!fileExists) {
				logger.debug("Can not load the properties file from the relative path: " + propPath);
				propPath = "C:/home/idi/properties/";
				logger.debug("Try to load the properties file directly from the absolute path: " + propPath);
				file = new File(propPath + propertiesFileName);
				fileExists = file.exists();
			}

			if (fileExists) {
				logger.debug("Properties file exist load it from: " + propPath + propertiesFileName);
				is = new FileInputStream(propPath + "/" + propertiesFileName);
			} else {
				// Otherwise, use resource as stream.
				is = this.getClass().getResourceAsStream(propPath + propertiesFileName);
			}

			super.load(is);
			is.close();

		} catch (Exception excep) {
			if (isErrorIfNoExist) {
				String msg = "Unable to load config file: " + propPath + propertiesFileName;
				logger.error(msg);
			}
		}
	}

}
