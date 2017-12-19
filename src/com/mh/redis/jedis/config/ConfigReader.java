package com.mh.redis.jedis.config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.mh.redis.jedis.config.bean.RedisServersBean;

public class ConfigReader {

	/**
	 * 把配置文件内容转换成对象
	 * 
	 * @param configPath
	 *            配置文件的路径地址
	 * @return
	 */
	public static RedisServersBean getRedisConfig(String configPath) {
		try {
			File file = new File(configPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(RedisServersBean.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (RedisServersBean) jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
