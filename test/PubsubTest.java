import com.mh.redis.jedis.tools.pubsub.DemoConsumer;
import com.mh.redis.jedis.tools.pubsub.Publisher;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PubsubTest {
	public static void main(String[] args) {
		String redisIp = "127.0.0.1";
		int reidsPort = 6379;
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), redisIp, reidsPort);
		System.out.println(String.format("redis pool is starting, redis ip %s, redis port %d", redisIp, reidsPort));

		DemoConsumer consumer = new DemoConsumer();
		consumer.start(jedisPool);
		
		Publisher publisher = new Publisher(jedisPool, consumer.channelName());
		publisher.pub("hello jason");
	}
}
