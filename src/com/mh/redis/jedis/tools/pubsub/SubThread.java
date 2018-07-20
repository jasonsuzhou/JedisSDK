package com.mh.redis.jedis.tools.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SubThread extends Thread {
	private final JedisPool jedisPool;
	private AbstractSubscriber subscriber;

	private final String channelName;

	public SubThread(JedisPool jedisPool, AbstractSubscriber subscriber) {
		super("SubThread");
		this.jedisPool = jedisPool;
		this.channelName = subscriber.channelName();
		this.subscriber = subscriber;
	}

	@Override
	public void run() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.subscribe(subscriber, channelName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
