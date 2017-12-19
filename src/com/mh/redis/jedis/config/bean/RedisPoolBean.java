package com.mh.redis.jedis.config.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pool")
public class RedisPoolBean {

	private int maxIdle;
	private int maxTotal;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private int timeoutMillis;

	public int getMaxIdle() {
		return maxIdle;
	}

	@XmlAttribute
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	@XmlAttribute
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	@XmlAttribute
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	@XmlAttribute
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public int getTimeoutMillis() {
		return timeoutMillis;
	}

	@XmlAttribute
	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

}
