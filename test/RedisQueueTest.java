import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mh.redis.jedis.config.bean.RedisServersBean;
import com.mh.redis.jedis.core.JedisClient;
import com.mh.redis.jedis.core.JedisClientFactory;
import com.mh.redis.jedis.core.JedisPlugin;
import com.mh.redis.jedis.tools.queue.RedisQueue;
import com.mh.redis.jedis.tools.queue.RedisQueueFactory;
import com.mh.redis.jedis.tools.queue.RedisQueueProcessor;

public class RedisQueueTest {
	private static JedisPlugin plugin = null;

	@BeforeClass
	public static void setUp() throws Exception {
		File file = new File("./test/jedis_config.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		RedisServersBean servers = (RedisServersBean) jaxbUnmarshaller.unmarshal(file);
		plugin = JedisPlugin.enable(servers);
	}

	@Test
	public void testPutToQueue() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisQueue queue = new RedisQueue(cli, "order");
		for (int i = 0; i < 1000; i++) {
			queue.push(String.valueOf(i));
		}
	}

	@Test
	public void getFromQueue() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisQueue queue = new RedisQueue(cli, "order");
		while (true) {
			System.out.println(queue.popWhenHasValue());
			System.out.println("waiting...");
		}
	}

	@Test
	public void testConsume() throws Exception {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisQueue queue = new RedisQueue(cli, "order");
		queue.consume(new RedisQueueProcessor() {

			@Override
			public void process(String value) throws Exception {
				System.out.println(value);
				System.out.println("waiting...");
			}
		});
	}

	@Test
	public void testRedisQueueFactory() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		new RedisQueue(cli, "order");
		RedisQueue queue2 = RedisQueueFactory.getQueue("order");
		queue2.push("hello world");
	}

	@AfterClass
	public static void tearDown() {
		if (plugin != null) {
			JedisPlugin.unplug();
		}
	}
}
