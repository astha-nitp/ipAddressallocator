package com.codingExcercise.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codingExcercise.model.HeartBeat;
import com.codingExcercise.model.IpAddress;
import com.codingExcercise.util.IpGenerator;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Service
public class AllocationService{

	private static List<IpAddress> ipPool; 
	
	private Cache<String, IpAddress> cache;
	
	@Value("${custom.property.startIP}")
	private String start = "";
	
	@Value("${custom.property.endIP}")
	private	String end = "";
	
	@Value("${custom.property.timeout}")
	private long timeout;
	
	@Value("${custom.property.timeoutUnit}")
	private String timeoutUnit;
 
	
	public AllocationService() {
	   ipPool = new LinkedList<IpAddress>();
		buildCache();
	}


	static RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {

		public void onRemoval(RemovalNotification<Object, Object> removal) {
		    IpAddress ip = (IpAddress)removal.getValue();
		    ip.setAssigned(0);
		    ipPool.add(0,ip);
		}
		};
		
		private void buildCache() {
	        cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
	                .maximumSize(1000).removalListener(removalListener)
	                .build(new CacheLoader<String,IpAddress>() {
	                    @Override
	                    public IpAddress load(String queryKey) throws Exception {
	                        return createExpensiveGraph(queryKey);
	                    }
	                });
	    }

	
	private IpAddress createExpensiveGraph(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
			cache.put(macAddress, new IpAddress(allocatedIp, 1));
		}
		return allocatedIp;
	}
	
	
	public List<HeartBeat> getAllMapping() {
		
		List<HeartBeat> list = new ArrayList<>();
		for (Entry<String, IpAddress> entry : cache.asMap().entrySet())
		{ 
			HeartBeat obj = new HeartBeat();
			obj.setMacAddress(entry.getKey());  
			obj.setAllcatedIpAddress(entry.getValue().getIpAddress());
			list.add(obj);
		}
		
		return list;
	}
	
	
//	public String getIP(String mac){
//		GuavaCacheManager cacheManager = new GuavaCacheManager();
//		Cache cache = cacheManager.getCache(CacheConfig.CACHE);
//		IpAddress ip = cache.get(mac, IpAddress.class);
//		return ip.getIpAddress();
//	}
//	

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

	public Cache<String, IpAddress> getCache() {
		return cache;
	}

	public void setCache(Cache<String, IpAddress> cache) {
		this.cache = cache;
	}

//	public Cache<String, IpAddress> getCache() {
//		return cache;
//	}
//
//	public void setCache(Cache<String, IpAddress> cache) {
//		this.cache = cache;
//	}

	
}
