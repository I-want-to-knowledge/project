package com.system.monitoring.core.service.elasticsearch;

import java.util.List;

/**
 * es
 *
 * @author YanZhen
 * 2018-09-07 16:51:01
 * ElasticsearchService
 */
public interface ElasticsearchService {

	/**
	 * get ip blacklist
	 *
	 * 2018-09-03 20:50:52
	 * @return List<String>
	 */
	List<String> esUserDefinedQuery();
}
