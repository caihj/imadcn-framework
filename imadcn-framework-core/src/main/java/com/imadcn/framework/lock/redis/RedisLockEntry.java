package com.imadcn.framework.lock.redis;

import java.util.concurrent.Semaphore;

import com.imadcn.framework.lock.redis.pubsub.PubSubEntry;

/**
 * 锁资源监听实例
 * @author imadcn
 */
public class RedisLockEntry implements PubSubEntry<RedisLockEntry>{
	
	private String channelName; // 监听渠道
	private int counter; // 计数器
    private final Semaphore latch; // 锁定/解锁信号量 ，使用信号量，一时做锁定等待操作，而是解锁while(true)死循环暂用资源过大的问题
    private String tag; // 描述性tag

    public RedisLockEntry(String channelName) {
        this.latch = new Semaphore(0);
        this.channelName = channelName;
        this.tag = channelName + ":" + Thread.currentThread().getId();
    }

    public void acquire() {
        counter++;
    }

    public int release() {
        return --counter;
    }

    public Semaphore getLatch() {
        return latch;
    }

	public String getChannelName() {
		return channelName;
	}

	public String getTag() {
		return tag;
	}
}


