package com.codingExcercise.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.codingExcercise.model.IpAddress;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class CacheService {
	
	private static Cache<String, IpAddress> cache;
	
	private static List<IpAddress> ipPool; 
	
	
	public static List<IpAddress> getIpPool(){
        if(ipPool == null){
        	ipPool = new LinkedList<>();
        	System.out.println("ipPool initialized");
        }
        return ipPool;
    }

	
	
	static RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {

		public void onRemoval(RemovalNotification<Object, Object> removal) {
		    if(removal.getCause()== RemovalCause.EXPIRED){
		    	System.out.println("");
			IpAddress ip = (IpAddress)removal.getValue();
		    ip.setAssigned(false);
		    getIpPool().removeIf(p-> p.equals(ip));
		    getIpPool().add(0,ip);
		    }
		}
		};
    
      public static Cache<String, IpAddress> getCache(long timeout, String timeoutUnit){
        if(cache == null){
        	cache = CacheBuilder.newBuilder().expireAfterWrite(timeout, TimeUnit.valueOf(timeoutUnit))
	                .maximumSize(1000).removalListener(removalListener)
	                .build(new CacheLoader<String,IpAddress>() {
	                    @Override
	                    public IpAddress load(String queryKey) throws Exception {
	                        return createSlowMethod(queryKey);
	                    }

	                });
        }
        return cache;
    }
      
      private static IpAddress createSlowMethod(String queryKey) {
			// TODO Auto-generated method stub
			return null;
		}

	

}
