package com.my.server.redis.manager;

public interface JedisManager {
	/**
	 * 设置缓存信息
	 * @param cacheKey
	 * 缓存key
	 * @param cacheObj
	 * 缓存对象,对象的缓存，通过fastjson转换json后存
	 * @param cacheSecond
	 * 缓存秒，null 永久
	 * @return
	 * @create_time 2016年7月11日
	 */
	public boolean set(String cacheKey,String cacheObj,Long cacheSecond);
	/**
	 * 删除缓存信息
	 * @param cacheKey
	 * 缓存key
	 * @return
	 * @create_time 2016年8月23日
	 */
	public boolean del(String cacheKey);
	/**
	 * 获取缓存对象
	 * @param cacheKey
	 * 缓存key
	 * @return
	 * @create_time 2016年7月11日 下午
	 */
	public String get(String cacheKey);
	/**
	 * 设置缓存失效时间
	 * @param cacheKey
	 * 缓存key
	 * @param cacheSecond
	 * 缓存秒，null 永久
	 * @return
	 * @create_time 2016年7月18日
	 */
	public boolean expire(String cacheKey, Long cacheSecond);
	/**
	 * 获取分布式锁
	 * @param lockKey
	 * 锁对象
	 * @return
	 * @create_time 2016年7月11日 
	 */
	public boolean lock(String lockObj);
	/**
	 * 释放锁
	 * @param lockObj
	 * @create_time 2016年7月11日
	 */
	public void unlock(String lockObj);
	/**
	 * 业务并发防止重复
	 * @param bizKey
	 * 业务唯一key
	 * @return
	 * @create_time 2016年7月11日 
	 */
	public boolean isFirst(String bizKey);
	/**
	 * 按天增量
	 * @param bizFlag @link com.sinafenqi.common.redis.domain.constant.BizFlag
	 * @return
	 * @create_time 2016年8月5日 
	 */
	public long incrByDate(String bizFlag);

	/**
	 * 递增
	 * @param cacheKey
	 * @return
	 * @create_time 2017年3月9日 上午10:35:14 
	 */
	public long incr(String cacheKey);
}
