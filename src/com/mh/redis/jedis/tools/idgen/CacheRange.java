package com.mh.redis.jedis.tools.idgen;

public class CacheRange {
	private long min;
	private long max;

	public CacheRange(long min, long max) {
		this.min = min;
		this.max = max;
	}

	public long getMax() {
		return max;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMin() {
		return min;
	}

	public void setMax(long max) {
		this.max = max;
	}
}