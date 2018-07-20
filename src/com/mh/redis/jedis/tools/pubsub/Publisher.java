package com.mh.redis.jedis.tools.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Publisher {
	private final JedisPool jedisPool;
	private final String channelName;

	public Publisher(JedisPool jedisPool, String channelName) {
		this.jedisPool = jedisPool;
		this.channelName = channelName;
	}

	public void pub(String messaage) {
		Jedis jedis = jedisPool.getResource();
		jedis.publish(channelName, messaage);
	}
}
