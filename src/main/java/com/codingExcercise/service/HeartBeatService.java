package com.codingExcercise.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.codingExcercise.cache.CacheConfig;

@Service
public class HeartBeatService{

//	@Autowired
//	private AllocationService allocationervice;
//	
	
	@CachePut(cacheNames=CacheConfig.CACHE,key="{#macAddress}")
	public Boolean refresh(String macAddress, String allcatedIpAddress) {

		return true;
	}

	

}
