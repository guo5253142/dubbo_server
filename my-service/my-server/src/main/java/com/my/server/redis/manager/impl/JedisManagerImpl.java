package com.my.server.redis.manager.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.common.tools.DateUtil;
import com.my.common.tools.MD5;
import com.my.server.redis.manager.JedisManager;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
@Component
public class JedisManagerImpl implements JedisManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ShardedJedisPool jedisPool ;
	private String cacheKeyPrefix = "fq_";
	@Override
	public boolean set(String cacheKey, String cacheObj, Long cacheSecond) {
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			logger.info("缓存set调用key:[{}],cacheSecond[{}]",cacheKey,cacheSecond);
			if (cacheSecond == null) {
				jedis.set(getMd5Key(cacheKey), cacheObj);
			}else{
				jedis.setex(getMd5Key(cacheKey),cacheSecond.intValue(),cacheObj);
			}
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.set运行时异常,cacheKey:%s,cacheSecond:%s",
					new Object[] { cacheKey,cacheSecond });
			logger.error(errorMsg, e);
			return false;
		}finally{
			returnResource(jedis);
		}
		return true;
	}
	
	@Override
	public boolean del(String cacheKey) {
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			logger.info("缓存del调用key:[{}]",cacheKey);
			jedis.del(getMd5Key(cacheKey));
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.del运行时异常,cacheKey:%s",
					new Object[] { cacheKey });
			logger.error(errorMsg, e);
			return false;
		}finally{
			returnResource(jedis);
		}
		return true;
	}
	
	@Override
	public boolean expire(String cacheKey, Long cacheSecond) {
		ShardedJedis jedis = null;
		try {
			logger.info("缓存set调用key:[{}],cacheSecond[{}]",cacheKey,cacheSecond);
		    jedis = jedisPool.getResource();
		
			if (cacheSecond != null) {
				jedis.expire(getMd5Key(cacheKey), cacheSecond.intValue());
			}
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.expire运行时异常,cacheKey:%s,cacheSecond:%s",
					new Object[] { cacheKey,cacheSecond });
			logger.error(errorMsg, e);
			return false;
		}finally{
			returnResource(jedis);
		}
		return true;
	}

	@Override
	public String get(String cacheKey) {
		ShardedJedis jedis  = null;
		try {
			logger.info("缓存get调用key:[{}]",cacheKey);
		    jedis = jedisPool.getResource();
			return jedis.get(getMd5Key(cacheKey));
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.get运行时异常,cacheKey:%s",
					new Object[] { cacheKey });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}
		return null;
	}

	@Override
	public boolean lock(String lockObj) {
		ShardedJedis jedis = null;
		try {
			logger.info("缓存lock调用lockObj:[{}]",lockObj);
		    jedis = jedisPool.getResource();
			Long lockFlag = jedis.incr(getMd5Key(lockObj));
			if (lockFlag == 1) {
				//锁时间 1小时没解锁，自动失效
				jedis.expire(getMd5Key(lockObj),60*60);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.lock运行时异常,lockObj:%s",
					new Object[] { lockObj });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}
		return false;
	}

	@Override
	public void unlock(String lockObj) {
		ShardedJedis jedis = null;
		try {
			logger.info("缓存lockObj调用unlockObj:[{}]",lockObj);
		    jedis = jedisPool.getResource();
			jedis.del(getMd5Key(lockObj));
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.unlock运行时异常,lockObj:%s",
					new Object[] { lockObj });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}

	}

	@Override
	public boolean isFirst(String bizKey) {
		ShardedJedis jedis = null;
		try {
			logger.info("缓存isFirst调用bizKey:[{}]",bizKey);
		    jedis = jedisPool.getResource();
			Long lockFlag = jedis.incr(getMd5Key(bizKey));
			//并发防止重复 key缓存10分钟
			jedis.expire(getMd5Key(bizKey),10*60);
			if (lockFlag == 1) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.isFirst运行时异常,bizKey:%s",
					new Object[] { bizKey });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}
		return false;
	}
	@Override
	public long incrByDate(String bizFlag) {
		ShardedJedis jedis = null;
		Long incr = 0L;
		try {
			String key = "incrByDate_" + bizFlag + DateUtil.toYYMMDD(Calendar.getInstance());
			logger.info("缓存incrByDate调用key:[{}]",key);
		    jedis = jedisPool.getResource();
		    incr = jedis.incr(getMd5Key(key));
			jedis.expire(getMd5Key(key),24*60*60);
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.incrByDate运行时异常,bizKey:%s",
					new Object[] { bizFlag });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}
		return incr;
	}

	@Override
	public long incr(String cacheKey) {
		ShardedJedis jedis = null;
		Long incr = 0L;
		try {
		    jedis = jedisPool.getResource();
		    incr = jedis.incr(getMd5Key(cacheKey));
		} catch (Exception e) {
			String errorMsg = String.format("接口JedisServiceImpl.incr运行时异常,cacheKey:%s",
					new Object[] { cacheKey });
			logger.error(errorMsg, e);
		}finally{
			returnResource(jedis);
		}
		return incr;
	}
	
	private String getMd5Key(String cacheKey){
		return MD5.encode(cacheKeyPrefix + cacheKey, "utf-8");
	}
	/**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(ShardedJedis redis) {
        if (redis != null) {
        	redis.close();
        }
    }

}
