package com.mh.redis.jedis.tools.queue;

public interface RedisQueueProcessor {
	
	void process(String value) throws Exception;

}
