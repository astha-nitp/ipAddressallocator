package com.codingExcercise.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codingExcercise.model.IpAddress;
import com.codingExcercise.util.IpGenerator;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Service
public class AllocationService{

//	private final IPRepository repository;
	private List<IpAddress> ipPool = new LinkedList<IpAddress>();;
	
	@Value("${custom.property.startIP}")
	private String start = "";
	
	@Value("${custom.property.endIP}")
	private	String end = "";
	
	@Value("${custom.property.timeout}")
	private long timeout;
	
	@Value("${custom.property.timeoutUnit}")
	private String timeoutUnit;

	
	RemovalListener<String,IpAddress> removalListener = new RemovalListener<String,IpAddress>() {

		@Override
		public void onRemoval(RemovalNotification<String, IpAddress> removal) {
		    IpAddress ip = removal.getValue();
		    ip.setAssigned(0);
			ipPool.add(0,ip);
		}
		};

	
	private Cache<String,IpAddress> cache = CacheBuilder.newBuilder()
		    .maximumSize(1000) .expireAfterWrite(timeout, TimeUnit.valueOf(timeoutUnit)).
		    removalListener(removalListener).build(new CacheLoader<String, IpAddress>() {
	             
				@Override
				public IpAddress load(String arg0) throws Exception {
					return createExpensiveGraph(arg0);
				}
	             }); 
	
	
	private IpAddress createExpensiveGraph(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public String allocate(String macAddress) {
		String allocatedIp = "";
		
		if(ipPool.get(0).isAssigned() == 0){
			IpAddress ip = ipPool.remove(0);
			allocatedIp = ip.getIpAddress();
			ip.setAssigned(1);
			ipPool.add(ip);
		}   
		else{
			allocatedIp = IpGenerator.generateIP(start,end);
			cache.asMap().put(macAddress, new IpAddress(allocatedIp, 1));
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

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public Cache<String, IpAddress> getCache() {
		return cache;
	}

	public void setCache(Cache<String, IpAddress> cache) {
		this.cache = cache;
	}

	
}
