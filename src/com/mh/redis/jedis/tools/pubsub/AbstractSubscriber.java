package com.mh.redis.jedis.tools.pubsub;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public abstract class AbstractSubscriber extends JedisPubSub {

	public AbstractSubscriber() {
	}

	public void onMessage(String channel, String message) {
		consume(message);
	}

	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO
	}

	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO
	}

	public abstract void consume(String message);

	public abstract String channelName();

	public void start(JedisPool jedisPool) {
		new SubThread(jedisPool, this).start();
	}

}
