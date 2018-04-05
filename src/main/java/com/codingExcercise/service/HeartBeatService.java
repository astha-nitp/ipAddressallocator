package com.codingExcercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingExcercise.model.IpAddress;

@Service
public class HeartBeatService {

	@Autowired
	private AllocationService allocationervice;

	public Boolean refresh(String macAddress, String allcatedIpAddress) {

		IpAddress ip = allocationervice.getCache().asMap().get(macAddress);
		if (ip != null) {
			ip.setIpAddress(allcatedIpAddress);
			allocationervice.getCache().put(macAddress, ip);
			return true;
		}
		return false;
	}

}
