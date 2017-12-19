package com.mh.redis.jedis.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisClientFactory {

	private static Map<String, JedisClient> allClient = new HashMap<String, JedisClient>();
	private static List<String> lsInstanceKeys = new ArrayList<String>();

	/**
	 * 内部方法，请勿调用
	 * 
	 * @param factoryKey
	 *            是有server id+":"+database id 组成
	 * @param client
	 */
	public static void create(String factoryKey, JedisClient client) {
		allClient.put(factoryKey, client);
		lsInstanceKeys.add(factoryKey);
	}

	/**
	 * 通过指定的redis server id 和 database id获取对应的redis客户端操作工具类
	 * 
	 * @param serverId
	 *            server id 在配置文件中对应server节点的id属性
	 * @param database
	 *            使用redis instance的哪个database，在配置文件中对应instance的database属性
	 * @return
	 */
	public static JedisClient get(String serverId, int database) {
		return allClient.get(serverId + ":" + database);
	}

	/**
	 * 应用退出的时候这个方法必须要调用
	 */
	public static synchronized void destoryAll() {
		if (!allClient.isEmpty()) {
			Set<String> keys = allClient.keySet();
			Iterator<String> iter = keys.iterator();
			JedisClient client = null;
			while (iter.hasNext()) {
				client = allClient.get(iter.next());
				client.destory();
			}
		}
	}

}
