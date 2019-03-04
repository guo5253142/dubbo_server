package com.my.server.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.common.redis.service.JedisService;
import com.my.server.redis.manager.JedisManager;


/**
 * redis接口实现
 * @author guopeng1
 * 2017年9月22日13:36:41
 */
@Service("jedisServiceImpl")
public class JedisServiceImpl implements JedisService {
	@Autowired
	private JedisManager jedisManager;
	@Override
	public boolean set(String cacheKey, String cacheObj, Long cacheSecond) {
		return jedisManager.set(cacheKey, cacheObj, cacheSecond);
	}

	@Override
	public String get(String cacheKey) {
		return jedisManager.get(cacheKey);
	}
	
	@Override
	public boolean del(String cacheKey){
		return jedisManager.del(cacheKey);
	}
	
	@Override
	public boolean expire(String cacheKey, Long cacheSecond) {
		return jedisManager.expire(cacheKey, cacheSecond);
	}

	@Override
	public boolean lock(String lockObj) {
		return jedisManager.lock(lockObj);
	}

	@Override
	public void unlock(String lockObj) {
		jedisManager.unlock(lockObj);
	}

	@Override
	public boolean isFirst(String bizKey) {
		return jedisManager.isFirst(bizKey);
	}

	@Override
	public long incrByDate(String bizFlag) {
		return jedisManager.incrByDate(bizFlag);
	}

	@Override
	public long incr(String cacheKey) {
		return jedisManager.incr(cacheKey);
	}
	
}
