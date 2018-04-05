package com.codingExcercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codingExcercise.model.HeartBeat;
import com.codingExcercise.model.IpAddress;
import com.codingExcercise.util.IpGenerator;
import com.google.common.cache.Cache;

@Service
public class AllocationService{

	
	
	@Value("${custom.property.startIP}")
	private String start = "";
	
	@Value("${custom.property.endIP}")
	private	String end = "";
	
	@Value("${custom.property.timeout}")
	private  long timeout;
	
	@Value("${custom.property.timeoutUnit}")
	private String timeoutUnit;
 
//	
//	@Value("${custom.property.timeout}")
//	private long timeout;
//	
//	@Value("${custom.property.timeoutUnit}")
//	private String timeoutUnit;
// 
//	
//	public AllocationService() {
////	   ipPool = 
////		buildCache();
//	}


//	static RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {
//
//		public void onRemoval(RemovalNotification<Object, Object> removal) {
//		    IpAddress ip = (IpAddress)removal.getValue();
//		    ip.setAssigned(0);
//		    ipPool.add(0,ip);
//		}
//		};
//		
//		private void buildCache() {
//	        cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
//	                .maximumSize(1000).removalListener(removalListener)
//	                .build(new CacheLoader<String,IpAddress>() {
//	                    @Override
//	                    public IpAddress load(String queryKey) throws Exception {
//	                        return createExpensiveGraph(queryKey);
//	                    }
//	                });
//	    }
//
//	
//	private IpAddress createExpensiveGraph(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
	public String allocate(String macAddress) {
		String allocatedIp = "";
		
		if(!getIpPool().isEmpty() && !getIpPool().get(0).isAssigned()){
			IpAddress ip = getIpPool().remove(0);
			allocatedIp = ip.getIpAddress();
			ip.setAssigned(true);
			getIpPool().add(ip);
		}   
		else{
			allocatedIp = getUniqueIp();
//			getCache().put(macAddress, new IpAddress(allocatedIp, true));
		}
		getCache().put(macAddress, new IpAddress(allocatedIp, true));
		return allocatedIp;
	}
	
	private String getUniqueIp(){
		String allocatedIp = IpGenerator.generateIP(start,end);
		IpAddress ip = new IpAddress(allocatedIp,true);
		if(getIpPool().contains(ip)){
			allocatedIp = getUniqueIp();
		}
		return allocatedIp;
	}
	
	public List<HeartBeat> getAllMapping() {
		
		List<HeartBeat> list = new ArrayList<>();
		for (Entry<String, IpAddress> entry : getCache().asMap().entrySet())
		{ 
			HeartBeat obj = new HeartBeat();
			obj.setMacAddress(entry.getKey());  
			obj.setAllcatedIpAddress(entry.getValue().getIpAddress());
			list.add(obj);
		}
		
		return list;
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
		return CacheService.getIpPool();
	}

	public Cache<String, IpAddress> getCache() {
		return CacheService.getCache(timeout,timeoutUnit);
	}


	
}
