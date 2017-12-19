package com.mh.redis.jedis.config.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedisInstanceBean {

	private int database;
	private RedisPoolBean pool;

	public int getDatabase() {
		return database;
	}

	@XmlAttribute
	public void setDatabase(int database) {
		this.database = database;
	}

	public RedisPoolBean getPool() {
		return pool;
	}

	@XmlElement(name = "pool")
	public void setPool(RedisPoolBean pool) {
		this.pool = pool;
	}

}
