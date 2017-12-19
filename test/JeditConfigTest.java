import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

import com.mh.redis.jedis.config.bean.RedisServersBean;

public class JeditConfigTest {
	
	@Test
	public void testLoadConfig() throws Exception {
		File file = new File(this.getClass().getResource("/jedis_config.xml").getFile());
		JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		RedisServersBean servers = (RedisServersBean) jaxbUnmarshaller.unmarshal(file);
		Assert.assertEquals(2, servers.getServer().size());
	}

}
