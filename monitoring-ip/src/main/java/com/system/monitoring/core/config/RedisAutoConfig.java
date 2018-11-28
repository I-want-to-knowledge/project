package com.system.monitoring.core.config;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis init
 *
 * @author YanZhen
 * 2018-09-07 16:51:30
 * RedisAutoConfig
 */
public class RedisAutoConfig {

	public static JedisPool jedispool() {
		RedisYML redisYML = new RedisYML();
		
		// 设置最大等待时间
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxWaitMillis(redisYML.getPool().getMaxWait());
		
		// 链接 Redis
		return new JedisPool(
				jedisPoolConfig,
				redisYML.getHost(), redisYML.getPort(), redisYML.getTimeout(),
				redisYML.getPassword(), redisYML.getDatabase());
	}
}
