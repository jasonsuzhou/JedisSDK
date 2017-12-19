package com.mh.redis.jedis.tools.counter;

import java.math.BigDecimal;

import com.mh.redis.jedis.core.JedisClient;

public class RedisCounter {

	private JedisClient cli;
	private String counterName;

	public RedisCounter(JedisClient cli, String counterName) {
		this.cli = cli;
		this.counterName = counterName;
		initValue();
	}

	public int increase(Integer number) {
		Long value = Long.valueOf(number);
		long result = increase(value);
		return (int) result;
	}

	public int decrease(Integer number) {
		Long value = Long.valueOf(number);
		long result = increase(value);
		return (int) result;
	}

	public long increase(Long number) {
		return this.cli.STRINGS.incrBy(counterName, number);
	}

	public long decrease(Long number) {
		return this.cli.STRINGS.decrBy(counterName, number);
	}

	public BigDecimal increase(BigDecimal number) {
		long value = number.longValue();
		long result = decrease(value);
		return new BigDecimal(result);
	}

	public BigDecimal decrease(BigDecimal number) {
		long value = number.longValue();
		long result = decrease(value);
		return new BigDecimal(result);
	}

	public int getCurrent() {
		String result = this.cli.STRINGS.get(counterName);
		if (result == null) {
			initValue();
			return 0;
		}
		return Integer.valueOf(result);
	}

	public long getCurrentToLong() {
		String result = this.cli.STRINGS.get(counterName);
		if (result == null) {
			initValue();
			return 0;
		}
		return Long.valueOf(result);
	}

	public BigDecimal getCurrentToBigDecimal() {
		String result = this.cli.STRINGS.get(counterName);
		if (result == null) {
			initValue();
			return new BigDecimal("0");
		}
		return new BigDecimal(result);
	}

	public void initValue(String number) {
		this.cli.STRINGS.set(counterName, number);
	}

	public long clear() {
		return this.cli.STRINGS.del(counterName);
	}

	private void initValue() {
		this.cli.STRINGS.set(counterName, "0");
	}

}
