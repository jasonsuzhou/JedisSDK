package com.mh.redis.jedis.core;

import java.io.Serializable;

public abstract class RedisServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5182444278153605102L;

	/**
	 * 唯一标识一台server的信息，主键，不得重复
	 */
	protected String id;
	protected String host;
	protected int port;
	protected String pass;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
