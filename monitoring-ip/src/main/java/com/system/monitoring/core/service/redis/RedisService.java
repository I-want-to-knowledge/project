package com.system.monitoring.core.service.redis;

import java.util.List;

/**
 * redis
 *
 * @author YanZhen
 * 2018-08-31 14:33:39
 * RedisService
 */
public interface RedisService {

	/**
	 * Redis add black list
	 *
	 * 2018-08-31 15:14:50 boolean
	 */
	boolean addBlackList(List<String> ips);
	
	/**
	 * Redis add white list
	 *
	 * 2018-08-31 15:14:50 boolean
	 */
	boolean addWhiteList(String ip);
	
	/**
	 * Redis remove blacklist
	 *
	 * 2018-08-31 19:17:00
	 * @param key ip无值时，删除key
	 * @param ip 删除key下面的ip
	 * @return boolean
	 */
	boolean removeBlackList(String key, String ip);
}
