package com.is.utils;

import org.apache.log4j.Logger;

public class ISLogger {
	static Logger logger = null;

	public static Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger("Optimist");
		}

		return logger;
	}
}
