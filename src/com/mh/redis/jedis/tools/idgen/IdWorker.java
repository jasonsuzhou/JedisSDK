package com.mh.redis.jedis.tools.idgen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;

import com.mh.redis.jedis.core.JedisClient;

public class IdWorker {

	private IdConfig idConfig;
	private JedisClient cli;
	private String redisKey;
	private Queue<Long> queue;

	public IdWorker(JedisClient cli, String type) {
		this.cli = cli;
		this.redisKey = "mh:idgen:" + type;
		idConfig = new IdConfig();
		idConfig.setPrefix(type);
		if (idConfig.isCache()) {
			queue = new ConcurrentLinkedQueue<Long>();
		}
	}

	public String next() {
		StringBuilder sb = new StringBuilder();
		int idLength = idConfig.getLength()
				- (idConfig.getPrefix().length() + idConfig.getSuffix().length() + idConfig.getDatePattern().length());
		String uniqueId = Long.toString(nextNumber());
		String strUniqueId = uniqueId.length() > idLength ? uniqueId.substring(uniqueId.length() - idLength)
				: StringUtils.leftPad(uniqueId, idLength, "0");
		Date date = new Date();
		DateFormat fomat = new SimpleDateFormat(idConfig.getDatePattern());
		return sb.append(idConfig.getPrefix()).append(fomat.format(date)).append(strUniqueId)
				.append(idConfig.getSuffix()).toString();
	}

	private long nextNumber() {
		long next;
		if (!idConfig.isCache()) {
			next = incrAndCompare(1);
		} else {
			Long val = queue.poll();
			if (val == null) {
				fullFillQueue(idConfig.getCacheSize());
				next = nextNumber();
			} else {
				next = val;
			}
		}
		return next;
	}

	private long incrAndCompare(int size) {
		long val = cli.STRINGS.incrBy(redisKey, size);
		if (val > Long.MAX_VALUE) {
			resetCache();
		}
		return val;
	}

	private void resetCache() {
		String lockKey = redisKey + ":lock";
		String val = cli.STRINGS.get(lockKey);
		if (val != null && val.length() > 0) {
			return;
		}
		cli.STRINGS.setEx(lockKey, 60, "true");
		cli.STRINGS.del(redisKey, lockKey);
	}

	private void fullFillQueue(int size) {
		synchronized (queue) {
			CacheRange range = fetch(size);
			for (long i = range.getMin(); i < range.getMax(); i++) {
				queue.add(i);
			}
		}
	}

	private CacheRange fetch(int size) {
		Long val = incrAndCompare(size);
		return new CacheRange(val - size, val);
	}

}
