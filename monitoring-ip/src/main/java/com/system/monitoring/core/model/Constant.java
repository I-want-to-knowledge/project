package com.system.monitoring.core.model;

/**
 * 常量
 *
 * @author YanZhen
 * 2018-09-04 10:16:32
 * Constant
 */
public interface Constant {
	/**
	 * 获取ip黑名单的key
	 *
	 * @author YanZhen
	 * 2018-08-31 09:36:37
	 * IpBlacklistKey
	 */
	interface IpListKey {
		
		/**
		 * Ip black list key
		 */
		final String IP_BLACKLIST_KEY = "ip_list:ip:ip_black_list";
		
		/**
		 * Ip white list key
		 */
		final String IP_WHITELIST_KEY = "ip_list:ip:ip_white_list";
	}
}
