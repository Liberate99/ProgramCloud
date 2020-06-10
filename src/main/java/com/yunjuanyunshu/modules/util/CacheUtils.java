/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yunjuanyunshu.modules.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.List;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {

	// 单例模式
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	private static CacheManager cacheManager = CacheManager.getInstance();

	/**  缓存名称，跟ehcache.xml文件配置的cache相对应
	 */
//	public static enum CacheName{sysCache,userCache};

	/**	添加缓存
	 * @param key
	 * @param value
	 */
	public static void put(String key,Object value){
		Cache cache = cacheManager.getCache("userCache");
		Element element = new Element(key, value);
		cache.put(element);
	}


	/**	获取缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		Cache cache = cacheManager.getCache("userCache");
		Element element = cache.get(key);
		if(element != null){
			return element.getObjectValue();
		}
		return null;
	}
	/**	添加缓存
	 * @param key
	 * @param value
	 */
	public static void putSys(String key,Object value){
		Cache cache = cacheManager.getCache("sysCache");
		Element element = new Element(key, value);
		cache.put(element);
	}


	/**	获取缓存
	 * @param key
	 * @return
	 */
	public static Object getSys(String key){
		Cache cache = cacheManager.getCache("sysCache");
		Element element =cache.get(key);
		if(element!=null){
			return element.getObjectValue();
		}
		return null;
	}

	/**
	 * 从缓存中移除
	 * @param key
	 */
	public static void remove(String key) {
		getCache("userCache").remove(key);
	}

	/**
	 * 从缓存中移除所有
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		Cache cache = getCache(cacheName);
		List keys = cache.getKeys();
		for (Iterator<String> it = keys.iterator(); it.hasNext();){
			cache.remove(it.next());
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}

	/**
	 * 获得一个Cache，没有则显示日志。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			throw new RuntimeException("当前系统中没有定义“"+cacheName+"”这个缓存。");
		}
		return cache;
	}
}
