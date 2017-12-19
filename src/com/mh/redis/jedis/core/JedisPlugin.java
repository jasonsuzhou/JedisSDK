package com.mh.redis.jedis.core;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import com.mh.redis.jedis.config.bean.RedisInstanceBean;
import com.mh.redis.jedis.config.bean.RedisPoolBean;
import com.mh.redis.jedis.config.bean.RedisServerBean;
import com.mh.redis.jedis.config.bean.RedisServersBean;

/**
 * 如果应用程序需要和redis服务器打交道，那可以启用这个插件<br/>
 * {@link #enable()} 或者 {@link #enable(File)} 来初始化插件<br/>
 * 插件初始化完毕，可以直接调用
 * {@link com.xuya.commons.utils.jedis.JedisClientFactory#get(String, int)}
 * 拿到redis客户端操作工具类 可以调用
 * 
 * @author jason.yao
 *
 */
public class JedisPlugin {

	private List<RedisInstance> lsRedisInstance = new ArrayList<RedisInstance>();

	private JedisPlugin() {
	}

	/**
	 * 如果先调用这个方法你必须再调用 {@link #addRedisInstance(RedisInstance)} 或者
	 * {@link #addRedisInstance(RedisInstance, JedisPoolParams)} 方法初始化redis服务器信息
	 * 
	 * @return
	 */
	public static JedisPlugin enable() {
		return new JedisPlugin();
	}

	/**
	 * 可以通过{@link com.mh.redis.jedis.config.ConfigReader#getRedisConfig(String)}获得RedisServersBean对象
	 * 
	 * @param configBean
	 * @return
	 */
	public static JedisPlugin enable(RedisServersBean configBean) {
		JedisPlugin jedisPlugin = enable();
		initJedisClients(jedisPlugin, configBean);
		return jedisPlugin;
	}

	/**
	 * 通过配置文件初始化redis客户端插件，配置文件请参考classpath:jedis_config.xml<br/>
	 * 通过配置文件初始化后可以直接调用
	 * {@link com.mh.redis.jedis.core.JedisClientFactory#get(String, int)}
	 * 得到操作redis的工具类
	 * 
	 * @param configureFile
	 * @return
	 */
	public static JedisPlugin enable(File configureFile) throws Exception {
		JedisPlugin jedisPlugin = enable();
		JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RedisServersBean servers = (RedisServersBean) jaxbUnmarshaller.unmarshal(configureFile);
		initJedisClients(jedisPlugin, servers);
		return jedisPlugin;
	}

	/**
	 * 通过配置文件初始化redis客户端插件，配置文件请参考classpath:jedis_config.xml<br/>
	 * 通过配置文件初始化后可以直接调用
	 * {@link com.mh.redis.jedis.core.JedisClientFactory#get(String, int)}
	 * 得到操作redis的工具类
	 * 
	 * @param configInputStream
	 * @return
	 */
	public static JedisPlugin enable(InputStream configInputStream) throws Exception {
		JedisPlugin jedisPlugin = enable();
		JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		RedisServersBean servers = (RedisServersBean) jaxbUnmarshaller.unmarshal(configInputStream);
		initJedisClients(jedisPlugin, servers);
		return jedisPlugin;
	}

	/**
	 * sample code
	 * 
	 * <pre>
	 * JedisPoolParams params = new JedisPoolParams();
	 * params.setMaxIdle(200);
	 * params.setMaxTotal(500);
	 * params.setTestOnBorrow(true);
	 * params.setTestOnReturn(false);
	 * params.setTimeoutMillis(3000);
	 * 
	 * RedisInstance instance = new RedisInstance();
	 * instance.setId("aliredis01");
	 * instance.setDatabase(0);
	 * instance.setHost("localhost");
	 * instance.setPort(6379);
	 * instance.setPass("");
	 * instance.setPoolConfig(params);
	 *
	 * addRedisInstance(instance);
	 * </pre>
	 * 
	 * @param instance
	 */
	public JedisPlugin addRedisInstance(RedisInstance instance) {
		validate(instance);
		buildRedisInstance(instance);
		return this;
	}

	/**
	 * sample code
	 * 
	 * <pre>
	 * JedisPoolParams params = new JedisPoolParams();
	 * params.setMaxIdle(200);
	 * params.setMaxTotal(500);
	 * params.setTestOnBorrow(true);
	 * params.setTestOnReturn(false);
	 * params.setTimeoutMillis(3000);
	 * 
	 * RedisInstance instance = new RedisInstance();
	 * instance.setId("aliredis01");
	 * instance.setDatabase(0);
	 * instance.setHost("localhost");
	 * instance.setPort(6379);
	 * instance.setPass("");
	 *
	 * addRedisInstance(instance, params);
	 * </pre>
	 * 
	 * @param instance
	 */
	public JedisPlugin addRedisInstance(RedisInstance instance, JedisPoolParams poolConfig) {
		validate(instance);
		buildRedisInstance(instance);
		return this;
	}

	/**
	 * 应用关闭的时候调用这个方法用来释放所有redis相关的资源，必须调用
	 * 
	 * @see JedisClientFactory#destoryAll()
	 */
	public static void unplug() {
		JedisClientFactory.destoryAll();
	}

	private void buildRedisInstance(RedisInstance redisInstance) {
		String factoryKey = redisInstance.getFactoryKey();
		JedisClient client = redisInstance.getJedisClient();
		JedisClientFactory.create(factoryKey, client);
		lsRedisInstance.add(redisInstance);
	}

	private static JedisPoolParams buildJedisPool(RedisPoolBean pool) {
		JedisPoolParams params = new JedisPoolParams();
		params.setMaxIdle(pool.getMaxIdle());
		params.setMaxTotal(pool.getMaxTotal());
		params.setTestOnBorrow(pool.isTestOnBorrow());
		params.setTestOnReturn(pool.isTestOnReturn());
		params.setTimeoutMillis(pool.getTimeoutMillis());
		return params;
	}

	private static void initJedisClients(JedisPlugin jedisPlugin, RedisServersBean servers) {
		List<RedisServerBean> lsServer = servers.getServer();
		if (lsServer != null && !lsServer.isEmpty()) {
			for (RedisServerBean server : lsServer) {
				List<RedisInstanceBean> lsInstance = server.getInstance();
				if (lsInstance != null && !lsInstance.isEmpty()) {
					RedisInstance instance = null;
					for (RedisInstanceBean instanceBean : lsInstance) {
						instance = new RedisInstance();
						instance.setId(server.getId());
						instance.setHost(server.getHost());
						instance.setPort(server.getPort());
						instance.setPass(server.getPass());
						instance.setDatabase(instanceBean.getDatabase());
						instance.setPoolConfig(buildJedisPool(instanceBean.getPool()));
						jedisPlugin.addRedisInstance(instance);
					}
				}
			}
		}
	}

	private void validate(RedisInstance instance) {
		if (null == instance) {
			throw new RuntimeException("RedisInstance cannot be null");
		}
		if (null == instance.getPoolConfig()) {
			throw new RuntimeException("RedisInstance.poolConfig cannot be null");
		}
		if (StringUtils.isBlank(instance.getId())) {
			throw new RuntimeException("RedisInstance.id cannot be null");
		}
		if (StringUtils.isBlank(instance.getHost())) {
			throw new RuntimeException("RedisInstance.host cannot be null");
		}
	}

}
