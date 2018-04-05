package com.codingExcercise.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.codingExcercise.cache.CacheConfig;
import com.codingExcercise.model.IpAddress;
import com.codingExcercise.util.IpGenerator;

@Service
public class AllocationService{

	private List<IpAddress> ipPool;
	
	
	public AllocationService() {
		this.ipPool = new LinkedList<IpAddress>();
	}

	@Value("${custom.property.startIP}")
	private String start = "";
	
	@Value("${custom.property.endIP}")
	private	String end = "";
	
	@Cacheable(cacheNames=CacheConfig.CACHE,key="{#macAddress}")
	public String allocate(String macAddress) {
		String allocatedIp = "";
		
		if(ipPool.size()>0 && ipPool.get(0).isAssigned() == 0){
			IpAddress ip = ipPool.remove(0);
			allocatedIp = ip.getIpAddress();
			ip.setAssigned(1);
			ipPool.add(ip);
		}   
		else{
			allocatedIp = IpGenerator.generateIP(start,end);
		}
		return allocatedIp;
	}
	
	
	

	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}

	public List<IpAddress> getIpPool() {
		return ipPool;
	}

	public void setIpPool(List<IpAddress> ipPool) {
		this.ipPool = ipPool;
	}
//
//	public long getTimeout() {
//		return timeout;
//	}
//
//	public void setTimeout(long timeout) {
//		this.timeout = timeout;
//	}

//	public Cache<String, IpAddress> getCache() {
//		return cache;
//	}
//
//	public void setCache(Cache<String, IpAddress> cache) {
//		this.cache = cache;
//	}

	
}
