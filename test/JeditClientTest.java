import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mh.redis.jedis.config.bean.RedisServersBean;
import com.mh.redis.jedis.core.JedisClient;
import com.mh.redis.jedis.core.JedisClientFactory;
import com.mh.redis.jedis.core.JedisPlugin;

public class JeditClientTest {
	private static JedisPlugin plugin =null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		File file = new File("./test/jedis_config.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		RedisServersBean servers = (RedisServersBean) jaxbUnmarshaller.unmarshal(file);
		plugin = JedisPlugin.enable(servers);
	}
	
	@Test
	public void testPutString() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		cli.STRINGS.set("key1", "hello world");
		String expected = cli.STRINGS.get("key1");
		Assert.assertEquals(expected, "hello world");
	}
	
	@AfterClass
	public static void tearDown() {
		if (plugin != null) {
			JedisPlugin.unplug();
		}
	}

}
