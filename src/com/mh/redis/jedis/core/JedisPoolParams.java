package com.mh.redis.jedis.core;

import java.io.Serializable;

public class JedisPoolParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4883790351709410037L;

	private int maxIdle = 200;
	private int maxTotal = 2000;
	private boolean testOnBorrow = true;
	private boolean testOnReturn = false;
	private int timeoutMillis = 3000;

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public int getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

}
