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
import com.mh.redis.jedis.tools.idgen.IdWorker;

public class IdWorkerTest {
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
	public void testSingle() {
		JedisClient cli = JedisClientFactory.get("1", 0);
		IdWorker worker = new IdWorker(cli, "RR");
		for (int i = 0; i < 10000; i++) {
			String id = worker.next();
			System.out.println(id + " " + id.length());
		}
	}

	@Test
	public void testMulti() throws InterruptedException {
		JedisClient cli = JedisClientFactory.get("1", 0);
		IdWorker worker = new IdWorker(cli, "RR");
		Thread t1 = new Thread(() -> {
			while (true) {
				System.out.println(worker.next());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		});
		Thread t2 = new Thread(() -> {
			while (true) {
				System.out.println(worker.next());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		});
		Thread t3 = new Thread(() -> {
			while (true) {
				System.out.println(worker.next());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		});
		Thread t4 = new Thread(() -> {
			while (true) {
				System.out.println(worker.next());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		});
		Thread t5 = new Thread(() -> {
			while (true) {
				System.out.println(worker.next());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		});
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

		Thread.currentThread().join();
	}

	@AfterClass
	public static void tearDown() {
		if (plugin != null) {
			JedisPlugin.unplug();
		}
	}
}
