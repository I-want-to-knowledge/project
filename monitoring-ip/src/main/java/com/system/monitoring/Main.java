package com.system.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.monitoring.core.service.blacktask.BlacklistStoredService;
import com.system.monitoring.core.service.blacktask.BlacklistStoredServiceImpl;

/**
 * add ip black list
 *
 * @author YanZhen
 * 2018-09-14 16:23:37
 * Main
 */
public class Main {
	private static Logger LOG = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		LOG.info("========Start checking es...========");
		BlacklistStoredService service = new BlacklistStoredServiceImpl();
		service.esSubmitRedis();
		LOG.info("========The inspection is over and the project is closed!========");
	}
}
