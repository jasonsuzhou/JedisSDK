# 简单易用的Redis Java客户端，基于Jedis封装
---
## 配置文件详解
```xml
<?xml version="1.0" encoding="UTF-8"?>
<servers> <!-- 配置redis服务器信息，可以有多个 -->
	<server id="1" host="127.0.0.1" port="6379" pass=""> <!-- id唯一，host是服务器地址，port是redis端口，pass是redis密码 -->
		<instance database="0"> <!-- 配置每个redis database，可以有多个instance -->
			<!-- 连接池的配置信息 -->
			<!-- 
					maxIdle:最大空闲连接数
					maxTotal:最大连接数
			 -->
			<pool maxIdle="1" maxTotal="5" testOnBorrow="true" testOnReturn="false" timeoutMillis="3000"></pool>
		</instance>
	</server>
</servers>
```

## 如何使用
- 传入配置文件地址,初始化配置文件

```java
RedisServersBean configBean = com.mh.redis.jedis.config.ConfigReader.getRedisConfig(String configPath);

```
- 拿到初始化后的对象就可以启用Redis工具了

```java
JedisPlugin.enable(configBean);

```
- 初始化之后就可以使用JedisClientFactory获取工具类

```java
JedisClient cli = JedisClientFactory.get("这里填配置文件的server id", 这里填配置文件的database值);
cli.STRINGS.set("key1", "hello world");
String expected = cli.STRINGS.get("key1");
// 更多操作请参看
com.mh.redis.jedis.core.JedisClient
```

- 应用退出的时候请一定要调用如下方法来断开与服务器的连接

```java
JedisPlugin.unplug();

```
