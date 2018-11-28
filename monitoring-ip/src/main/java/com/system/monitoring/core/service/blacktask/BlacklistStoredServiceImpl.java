package com.system.monitoring.core.service.blacktask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.system.monitoring.core.service.elasticsearch.ElasticsearchService;
import com.system.monitoring.core.service.elasticsearch.ElasticsearchServiceImpl;
import com.system.monitoring.core.service.redis.RedisService;
import com.system.monitoring.core.service.redis.RedisServiceImpl;

@Service
public class BlacklistStoredServiceImpl implements BlacklistStoredService {
	private Logger LOG = LoggerFactory.getLogger(BlacklistStoredServiceImpl.class);
	
	private ElasticsearchService elasticsearchService = new ElasticsearchServiceImpl();
	
	private RedisService redisService = new RedisServiceImpl();

	@Override
	public void esSubmitRedis() {
		// 拉取es里的数据放入redis
		if (redisService.addBlackList(elasticsearchService.esUserDefinedQuery())) {
			LOG.info("========Redis add black list succeed!========");
		} else {
			LOG.info("========Redis add black list failure!========");
		}
	}

}
