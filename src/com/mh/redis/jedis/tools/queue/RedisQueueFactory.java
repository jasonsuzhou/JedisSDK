package com.mh.redis.jedis.tools.queue;

import java.util.HashMap;
import java.util.Map;

public class RedisQueueFactory {

	private static Map<String, RedisQueue> hmRedisQueue = new HashMap<String, RedisQueue>();

	public static RedisQueue getQueue(String queueName) {
		return hmRedisQueue.get(queueName);
	}

	public static void addQueue(String queueName, RedisQueue queue) {
		hmRedisQueue.put(queueName, queue);
	}

}
