package com.mh.redis.jedis.config.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="server")
public class RedisServerBean {

	private String id;
	private String host;
	private int port;
	private String pass;
	
	private List<RedisInstanceBean> instance;

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	@XmlAttribute
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	@XmlAttribute
	public void setPort(int port) {
		this.port = port;
	}

	public String getPass() {
		return pass;
	}

	@XmlAttribute
	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<RedisInstanceBean> getInstance() {
		return instance;
	}

	@XmlElement(name="instance")
	public void setInstance(List<RedisInstanceBean> instance) {
		this.instance = instance;
	}
	
	

}
