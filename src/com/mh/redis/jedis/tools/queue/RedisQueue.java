package com.mh.redis.jedis.tools.queue;

import com.mh.redis.jedis.core.JedisClient;

/**
 * 先进先出队列
 * 
 * @author Jason
 *
 */
public class RedisQueue {
	private JedisClient cli;
	private String queueName;

	public RedisQueue(JedisClient client, String queueName) {
		this.cli = client;
		this.queueName = "mh:queue:" + queueName;
		RedisQueueFactory.addQueue(queueName, this);
	}

	public void consume(RedisQueueProcessor processor) throws Exception {
		if (processor != null) {
			String value = null;
			while (true) {
				value = popWhenHasValue();
				processor.process(value);
			}
		}
	}

	public void push(String value) {
		this.cli.LISTS.lpush(queueName, value);
	}

	/**
	 * queue里面没值就返回null
	 * 
	 * @return
	 */
	public String pop() {
		return this.cli.LISTS.rpop(queueName);
	}

	/**
	 * timeout为0表示一直阻塞直到queue里面有值才返回
	 * 
	 * @return
	 */
	public String popWhenHasValue() {
		return this.cli.LISTS.brpop(queueName, 0);
	}

}
