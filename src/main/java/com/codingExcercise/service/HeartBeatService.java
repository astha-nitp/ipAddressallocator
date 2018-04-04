package com.codingExcercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingExcercise.model.IpAddress;
import com.google.common.cache.Cache;

@Service
public class HeartBeatService{

	@Autowired
	private Cache<String,IpAddress> cache;
	
	public Boolean refresh(String macAddress, String allcatedIpAddress) {
		
		IpAddress ip = cache.asMap().get(macAddress);
		if(ip != null){
			cache.asMap().put(macAddress, ip);
			return true;
		}
		else
			return false;
	}

	public Cache<String,IpAddress> getCache() {
		return cache;
	}

	public void setCache(Cache<String,IpAddress> cache) {
		this.cache = cache;
	}

}
