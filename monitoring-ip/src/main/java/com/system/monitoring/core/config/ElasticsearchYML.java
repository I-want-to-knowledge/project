package com.system.monitoring.core.config;

import java.util.Map;

import com.system.monitoring.core.util.GetProperties;

/**
 * es配置
 *
 * @author YanZhen 2018-08-31 14:39:16 RedisProperties
 */
public class ElasticsearchYML {

	private String schema;// 请求模式
	private String host;// 主机地址
	private int port;// 端口
	private String requestUri;// 请求地址

	private String limitUri;// 限制请求地址
	private String arg;// 标签
	private int visitLimit;// 访问限制数

	private int connectTimeOut;// 链接超时
	private int socketTimeOut;// 链接超时
	private int connectionRequestTimeOut;// 请求超时
	private int maxConnectNum;// 最大连接数
	private int maxConnectPerRoute;// 最大链接数
	
	public ElasticsearchYML() {
		Map<String, String> config = GetProperties.getConfig("es");
		this.schema = config.get("schema");
		this.host = config.get("host");
		this.port = Integer.valueOf(config.get("port"));
		this.requestUri = config.get("request_uri");
		this.limitUri = config.get("limit_uri");
		this.arg = config.get("arg");
		this.visitLimit = Integer.valueOf(config.get("visit_limit"));
		this.connectTimeOut = Integer.valueOf(config.get("connect_time_out"));
		this.socketTimeOut = Integer.valueOf(config.get("socket_time_out"));
		this.connectionRequestTimeOut = Integer.valueOf(config.get("connection_request_time_out"));
		this.maxConnectNum = Integer.valueOf(config.get("max_connect_num"));
		this.maxConnectPerRoute = Integer.valueOf(config.get("max_connect_per-route"));
	}

	/**
	 * @return 返回 schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return 返回 host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return 返回 port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return 返回 requestUri
	 */
	public String getRequestUri() {
		return requestUri;
	}

	/**
	 * @return 返回 limitUri
	 */
	public String getLimitUri() {
		return limitUri;
	}

	/**
	 * @return 返回 arg
	 */
	public String getArg() {
		return arg;
	}

	/**
	 * @return 返回 visitLimit
	 */
	public int getVisitLimit() {
		return visitLimit;
	}

	/**
	 * @return 返回 connectTimeOut
	 */
	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	/**
	 * @return 返回 socketTimeOut
	 */
	public int getSocketTimeOut() {
		return socketTimeOut;
	}

	/**
	 * @return 返回 connectionRequestTimeOut
	 */
	public int getConnectionRequestTimeOut() {
		return connectionRequestTimeOut;
	}

	/**
	 * @return 返回 maxConnectNum
	 */
	public int getMaxConnectNum() {
		return maxConnectNum;
	}

	/**
	 * @return 返回 maxConnectPerRoute
	 */
	public int getMaxConnectPerRoute() {
		return maxConnectPerRoute;
	}
}
