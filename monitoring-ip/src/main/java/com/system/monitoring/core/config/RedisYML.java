package com.system.monitoring.core.config;

import java.util.Map;

import com.system.monitoring.core.util.GetProperties;

/**
 * redis配置
 *
 * @author YanZhen 2018-08-31 14:39:16 RedisProperties
 */
public class RedisYML {

	private String host;// 主机名
	private int port;// 端口号
	private String password;// 密码
	private int database;// 
	private int timeout;// 链接超时
	private Pool pool;
	
	public RedisYML() {
		Map<String, String> config = GetProperties.getConfig("jedis");
		
		this.host = config.get("host");
		this.port = Integer.valueOf(config.get("port"));
		this.password = config.get("password");
		this.database = Integer.valueOf(config.get("database"));
		this.timeout = Integer.valueOf(config.get("timeout"));
		this.pool = new Pool(Integer.valueOf(config.get("pool.max_wait")));
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public int getDatabase() {
		return database;
	}

	public int getTimeout() {
		return timeout;
	}

	public Pool getPool() {
		return pool;
	}

	public static class Pool {
		private int maxWait;// 池等待时间
		
		public Pool(int maxWait) {
			this.maxWait = maxWait;
		}
		
		public int getMaxWait() {
			return maxWait;
		}
	}
}
