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
import com.mh.redis.jedis.tools.counter.RedisCounter;

public class RedisCounterTest {
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
	public void testAddPassErrorTimes() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisCounter counter = new RedisCounter(cli, "passCounter:user1");
		int current = counter.getCurrent();
		System.out.println(current);
		counter.clear();
	}

	@Test
	public void testAddPassErrorTimes1() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisCounter counter = new RedisCounter(cli, "passCounter:user1");
		int current = counter.getCurrent();
		System.out.println(current);
		counter.clear();
		current = counter.getCurrent();
		System.out.println(current);
	}

	@Test
	public void testAddPassErrorTimes2() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisCounter counter = new RedisCounter(cli, "passCounter:user1");
		int current = counter.getCurrent();
		System.out.println(current);
		counter.clear();
		counter.increase(4);
		current = counter.getCurrent();
		System.out.println(current);
	}

	@Test
	public void testAddPassErrorTimes3() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisCounter counter = new RedisCounter(cli, "passCounter:user1");
		int current = counter.getCurrent();
		System.out.println(current);
		counter.increase(1);
		counter.increase(4);
		current = counter.getCurrent();
		System.out.println(current);
	}

	@Test
	public void testAddPassErrorTimes4() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		RedisCounter counter = new RedisCounter(cli, "passCounter:user1");
		counter.initValue("10");
		int current = counter.getCurrent();
		System.out.println(current);
		counter.decrease(3);
		current = counter.getCurrent();
		System.out.println(current);
		counter.clear();
	}

	@AfterClass
	public static void tearDown() {
		if (plugin != null) {
			JedisPlugin.unplug();
		}
	}
}
