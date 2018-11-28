package com.system.monitoring.core.service.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.system.monitoring.core.config.RedisAutoConfig;
import com.system.monitoring.core.model.Constant.IpListKey;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

/**
 * redis
 *
 * @author YanZhen
 * 2018-08-31 14:33:53
 * RedisServiceImpl
 */
@Service
public class RedisServiceImpl implements RedisService {
	
	private Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

	private JedisPool jedisPool = RedisAutoConfig.jedispool();
	
	@Override
	public boolean addBlackList(List<String> ips) {
		try (Jedis jedis = jedisPool.getResource()) {
			
			// 判断key是否存在，存在及删除
			String ipBlacklistKey = IpListKey.IP_BLACKLIST_KEY;
			if (jedis.exists(ipBlacklistKey)) {
				removeBlackList(ipBlacklistKey, null);
			}
			
			// 查看是否有黑名单
			if (ips == null || ips.isEmpty()) {// 没有结果直接返回
				LOG.info("========There is no IP to add!========");
				return false;
			}
			
			// "192.168.10.69", "192.168.10.101"
			// 插入黑名单
			byte[][] ipEncode = encodeMany(ips, jedis);
			if (ipEncode.length == 0) {
				LOG.info("========The whitelist already exists!========");
				return false;
			}
			Long sadd = jedis.sadd(SafeEncoder.encode(ipBlacklistKey), ipEncode);
			if (sadd == null || sadd == 0) {
				return false;
			} else {
				return true;
			}
		} finally {
			jedisPool.close();
		}
		
	}
	
	@Override
	public boolean addWhiteList(String ip) {
		try (Jedis jedis = jedisPool.getResource()) {
			// "192.168.10.69", "192.168.10.101"
			// 插入白名单
			Long sadd = jedis.sadd(IpListKey.IP_WHITELIST_KEY, ip);
			if (sadd == null || sadd == 0) {
				LOG.info("========white list add failure! ip = [{}]========", ip);
				return false;
			} else {
				LOG.info("========white list add succeed! ip = [{}]========", ip);
				return true;
			}
		} finally {
			jedisPool.close();
		}
	}
	
	/**
	 * 转数组
	 *
	 * 2018-08-31 19:54:07
	 * @param ips
	 * @param jedis 
	 * @return byte[][]
	 */
	public byte[][] encodeMany(List<String> ips, Jedis jedis) {
		// 过滤白名单
		List<String> newIps = new ArrayList<>();
		for (String ip : ips) {
			if (!jedis.sismember(IpListKey.IP_WHITELIST_KEY, ip)) {
				LOG.info("========black list ip = [{}]========", ip);
				newIps.add(ip);
			}
		}
		
		// 转byte[]
    byte[][] many = new byte[newIps.size()][];
    for (int i = 0; i < newIps.size(); i++) {
    		many[i] = SafeEncoder.encode(newIps.get(i));
    }
    return many;
  }

	@Override
	public boolean removeBlackList(String key, String ip) {
		if (key == null) {// TODO:
			key = IpListKey.IP_BLACKLIST_KEY;
		}
		try (Jedis jedis = jedisPool.getResource()) {
			Long numtag = null;
			if (ip == null || ip.isEmpty()) {
				numtag = jedis.del(key);
			} else {
				numtag = jedis.srem(key, ip);
			}
			if (numtag > 0) {
				LOG.info("========remove succeed! state:[{}]========", numtag);
				return true;
			}
			LOG.info("========remove failure! state:[{}]========", numtag);
		} finally {
			jedisPool.close();
		}
		return false;
	}

	
}
