package com.system.monitoring.core.service.elasticsearch;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.monitoring.core.config.ElasticsearchYML;

/**
 * elastic配置
 *
 * @author YanZhen 2018-08-30 15:49:01 RestClientFactory
 */
public class RestClientFactory {
	private Logger LOG = LoggerFactory.getLogger(RestClientFactory.class);
	
	private ElasticsearchYML esYML;
	public RestClientFactory(ElasticsearchYML esYML) {
		this.esYML = esYML;
	}
	
	/**
	 * http直接请求es
	 *
	 * 2018-09-03 16:26:19
	 * @return String
	 */
	public String httpRequest(Date date) {
		String result = "";
		String url = esYML.getSchema() + "://" + esYML.getHost() + ":" + esYML.getPort() + esYML.getRequestUri();
		LOG.info("request url:{}", url);
		try {
			result = Request.Post(url)
			.addHeader("Content-Type", "application/json")
			.bodyString(getParams(date), ContentType.APPLICATION_JSON)
			.connectTimeout(2000).socketTimeout(2000)
			.execute()
			.returnContent()
			.asString(Charset.forName("utf-8"));
		} catch (Exception e) {
			LOG.error("请求ElasticSearch错误！", e);
		}
		return result;
	}
	
	/**
	 * 请求es的参数
	 *
	 * 2018-09-03 19:34:43
	 * @param date
	 * @return String
	 */
	private String getParams(Date date) {
		return "{\"size\":0,\"aggs\":"
					+ "{\"" + esYML.getArg() + "\":{\"terms\":{\"field\":\"geoip.ip\",\"size\":10,"
						+ "\"min_doc_count\":" + esYML.getVisitLimit() + "}}},"// \"order\":{\"_count\":\"desc\"}
					+ "\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":["
					+ "{\"range\":{\"@timestamp\":{\"gte\":" + getDayTime(date, 0, 0, 0, 0) + ",\"lte\":" + getDayTime(date, 23, 59, 59, 999)
					+ ",\"format\":\"epoch_millis\"}}},"
					+ "{\"match_phrase\":{\"uri.keyword\":{\"query\":\"" + esYML.getLimitUri() + "\"}}}]}}}";
	}

	/**
	 * 获取一天的开始/结束时间
	 *
	 * 2018-09-03 19:44:46
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return long
	 */
	private Long getDayTime(Date date, int hour, int minute, int second, int millisecond) {
		if (date == null) {
			return null;
		}
		
		// 获取当天的时间段
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, hour);
		ca.set(Calendar.MINUTE, minute);
		ca.set(Calendar.SECOND, second);
		ca.set(Calendar.MILLISECOND, millisecond);
		return ca.getTimeInMillis();
	}
}
