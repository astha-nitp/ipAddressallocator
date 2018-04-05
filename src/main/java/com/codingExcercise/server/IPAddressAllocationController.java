package com.codingExcercise.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingExcercise.model.HeartBeat;
import com.codingExcercise.service.AllocationService;
import com.codingExcercise.service.HeartBeatService;

@RestController
public class IPAddressAllocationController {

	@Autowired
	private AllocationService allocationService;
	
	@Autowired
	private HeartBeatService heartBeatService;
	
	@RequestMapping("/allocateAddress/{macAddress}")
	public String allocate(@PathVariable String macAddress){
		return allocationService.allocate(macAddress);
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/heartBeat")
	public String refresh(@RequestBody HeartBeat heartbeat){
		heartBeatService.refresh(heartbeat.getMacAddress(),heartbeat.getAllcatedIpAddress());
		return "HeartBeat Updated.";
	}
	
}
