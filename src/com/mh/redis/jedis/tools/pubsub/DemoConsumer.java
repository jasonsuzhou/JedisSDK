package com.mh.redis.jedis.tools.pubsub;

public class DemoConsumer extends AbstractSubscriber {

	@Override
	public void consume(String message) {
		System.out.println("received message::" + message);
	}

	@Override
	public String channelName() {
		return "demochannel";
	}

}
