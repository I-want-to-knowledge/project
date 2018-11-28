package com.system.monitoring.core.service.elasticsearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.monitoring.core.config.ElasticsearchYML;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

	private Logger LOG = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
	
	private ElasticsearchYML esYML = new ElasticsearchYML();
	
	@Override
	public List<String> esUserDefinedQuery() {
		RestClientFactory restClientFactory = new RestClientFactory(esYML);
		// 例子
		// {"aggregations": 
		// {"arg": {
		// 	"doc_count_error_upper_bound": 0,
		// 	"sum_other_doc_count": 149,
		// 	"buckets": [{"key": "222.134.64.2","doc_count": 2229}]}
		// }}
		String result = restClientFactory.httpRequest(new Date());
		if (result == null || result.isEmpty()) {
			LOG.info("没有响应参数！");
			return null;
		} else {
			LOG.info("========es response parameter : {}========", result);
		}
		List<String> ips = null;
		JSONObject jb = JSONObject.parseObject(result);
		JSONObject aggrJson = jb.getJSONObject("aggregations");
		if (aggrJson != null) {
			JSONObject argJson = aggrJson.getJSONObject(esYML.getArg());
			JSONArray buckets = argJson.getJSONArray("buckets");
			if (buckets != null) {
				ips = new ArrayList<>();
				for (Object obj : buckets) {
					JSONObject ipsJson = JSONObject.parseObject(obj.toString());
					Integer docCount = ipsJson.getInteger("doc_count");
					if (docCount != null && docCount > esYML.getVisitLimit()) {
						String ip = ipsJson.getString("key");
						if (ip != null && !ip.isEmpty()) {
							ips.add(ip);
						}
					}
				}
			}
		}
		return ips;
	}

}
