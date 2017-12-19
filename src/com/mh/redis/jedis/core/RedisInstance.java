package com.mh.redis.jedis.core;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisInstance extends RedisServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041985562835059243L;

	private int database = 0;
	private JedisPoolParams poolConfig;
	private JedisPool jedisPool = null;;

	public RedisInstance() {
	}

	public RedisInstance(String serverId, int database, String host, int port, String pass) {
		this.id = serverId;
		this.database = database;
		this.host = host;
		this.port = port;
		this.pass = pass;
	}

	public RedisInstance(String serverId, int database, String host, int port, String pass,
			JedisPoolParams poolConfig) {
		this.id = serverId;
		this.database = database;
		this.host = host;
		this.port = port;
		this.pass = pass;
		this.poolConfig = poolConfig;
	}

	public JedisClient getJedisClient() {
		if (null == jedisPool) {
			initialJedisPool();
		}
		return new JedisClient(jedisPool, database);
	}

	public synchronized void destoryJedisPool() {
		if (jedisPool != null) {
			jedisPool.close();
		}
	}

	public String getFactoryKey() {
		return this.getId() + ":" + database;
	}

	private synchronized void initialJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(poolConfig.getMaxIdle());
		config.setMaxTotal(poolConfig.getMaxTotal());
		config.setTestOnBorrow(poolConfig.isTestOnBorrow());
		config.setTestOnReturn(poolConfig.isTestOnReturn());
		if (pass == null || "".equals(pass)) {
			jedisPool = new JedisPool(config, host, port, poolConfig.getTimeoutMillis());
		} else {
			jedisPool = new JedisPool(config, host, port, poolConfig.getTimeoutMillis(), pass);
		}
	}

	public JedisPoolParams getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolParams poolConfig) {
		this.poolConfig = poolConfig;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

}
