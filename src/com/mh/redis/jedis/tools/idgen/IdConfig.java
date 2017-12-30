package com.mh.redis.jedis.tools.idgen;

import java.io.Serializable;

public class IdConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5383678788290247741L;

	/**
	 * max length for the id
	 */
	private static final int DEFAULT_ID_LENGTH = 32;
	/**
	 * min length for the id
	 */
	private static final int MIN_ID_LENGHT = 5;
	/**
	 * default cache size for the id
	 */
	private static final int DEFAULT_CACHE_SIZE = 1000;
	/**
	 * min cache size for the id
	 */
	private static final int MIN_CACHE_SIZE = 10;
	/**
	 * default date time format
	 */
	private static final String DEFAULT_DATE_PATTERN = "yyMMddHHmmss";
	/**
	 * the id prefix
	 */
	private static final String DEFAULT_PREFIX = "";
	/**
	 * the id suffix
	 */
	private static final String DEFAULT_SUFFIX = "";

	private String prefix = DEFAULT_PREFIX;
	private String suffix = DEFAULT_SUFFIX;
	private String datePattern = DEFAULT_DATE_PATTERN;
	private int length = DEFAULT_ID_LENGTH;
	private boolean cache = true;
	private int cacheSize = DEFAULT_CACHE_SIZE;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

}
