package com.codingExcercise.model;

public class IpAddress implements Comparable<IpAddress>{
	
	private String ipAddress;
	
	private Integer isAssigned;

	public IpAddress(String ipAddress, Integer isAssigned) {
		super();
		this.ipAddress = ipAddress;
		this.isAssigned = isAssigned;
	}

	public Integer isAssigned() {
		return isAssigned;
	}

	public void setAssigned(Integer isAssigned) {
		this.isAssigned = isAssigned;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public int compareTo(IpAddress o) {
		return o.isAssigned.compareTo(this.isAssigned);
	}

}
