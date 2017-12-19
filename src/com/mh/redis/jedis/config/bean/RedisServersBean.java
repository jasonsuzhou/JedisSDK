package com.mh.redis.jedis.config.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "servers")
public class RedisServersBean {

	private List<RedisServerBean> server;

	public List<RedisServerBean> getServer() {
		return server;
	}

	@XmlElement(name="server")
	public void setServer(List<RedisServerBean> server) {
		this.server = server;
	}
	
}
