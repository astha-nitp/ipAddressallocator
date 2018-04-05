package com.codingExcercise.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codingExcercise.model.IpAddress;
import com.codingExcercise.service.AllocationService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Configuration
@EnableCaching
public class CacheConfig {

	
	 public final static String CACHE = "ipCache";

		@Value("${custom.property.timeout}")
		private long timeout;
		
		@Value("${custom.property.timeoutUnit}")
		private String timeoutUnit;
	 
	 @Autowired
	 AllocationService allocationService;
	 
//	    @Bean
//	    public Cache cacheOne() {
//	        return new GuavaCache(CACHE, CacheBuilder.newBuilder()
//	                .expireAfterWrite(3, TimeUnit.SECONDS).removalListener(removalListener)
//	                .build(new CacheLoader<String, IpAddress>() {}));
//	    }
//	
	    

		RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {

			public void onRemoval(RemovalNotification<Object, Object> removal) {
			    IpAddress ip = (IpAddress)removal.getValue();
			    ip.setAssigned(0);
			    allocationService.getIpPool().add(0,ip);
			}
			};

			@Bean
			public CacheBuilder<Object, Object> cacheBuilder() {
			    CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.from("maximumSize=1000").
			    		expireAfterWrite(timeout, TimeUnit.valueOf(timeoutUnit)).
			    		removalListener(removalListener);

			    return cacheBuilder;
			}

			@Autowired
			private CacheBuilder<Object, Object> cacheBuilder;

			@Bean
			public CacheManager cacheManager() {
			    GuavaCacheManager cacheManager = new GuavaCacheManager();
			    cacheManager.setCacheBuilder(cacheBuilder);
			    cacheManager.setAllowNullValues(false);
			    return cacheManager;
			}
			
//		private Cache<String,IpAddress> cache = CacheBuilder.newBuilder()
//			    .maximumSize(1000) .expireAfterWrite(timeout, TimeUnit.valueOf(timeoutUnit)).
//			    removalListener(removalListener).build(new CacheLoader<String, IpAddress>() {
//		             
//					@Override
//					public IpAddress load(String arg0) throws Exception {
//						return createExpensiveGraph(arg0);
//					}
//		             }); 
//		
//		
//		private IpAddress createExpensiveGraph(String key) {
//			// TODO Auto-generated method stub
//			return null;
//		}
}
