package com.is.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

	String message = "Not Connected";
	static final long serialVersionUID = 2L;
	private static ConnectionPool instance = null;
	private static Properties properties = null;
	private static String DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static String botToken;
	private static String botUsername;
	private static String filepath;

	public static ConnectionPool loadProperties() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	private ConnectionPool() {
		try {
			if (properties == null) {
				properties = new Properties();
				properties.load(getClass().getClassLoader().getResourceAsStream("botsets.properties"));

				filepath = properties.getProperty("filepath");
				DRIVER = properties.getProperty("driver");
				DB_URL = properties.getProperty("url");
				USER = properties.getProperty("usr");
				PASS = properties.getProperty("pwd");
				botToken = properties.getProperty("bottoken");
				botUsername = properties.getProperty("botusr");
			}
		} catch (Exception ex) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(ex));
			ex.printStackTrace();
		}
	}

	public static String getBotToken() {
		return botToken;
	}

	public static String getBotUsername() {
		return botUsername;
	}

	public static String getFilePath() {
		return filepath;
	}

	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName(DRIVER).getDeclaredConstructor().newInstance();
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			c.setAutoCommit(false);
		} catch (Exception e) {
			ISLogger.getLogger().error(getPstr(e));
			e.printStackTrace();
		}
		return c;
	}

	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			ISLogger.getLogger().error(getPstr(e));
		}
	}

	public static void close(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			ISLogger.getLogger().error(getPstr(e));
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			ISLogger.getLogger().error(getPstr(e));
		}
	}

	public static String getPstr(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}