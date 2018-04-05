package com.codingExcercise.model;

public class IpAddress implements Comparable<IpAddress>{
	
	private String ipAddress;
	
	private Boolean isAssigned;

	public IpAddress(String ipAddress, Boolean isAssigned) {
		super();
		this.ipAddress = ipAddress;
		this.isAssigned = isAssigned;
	}

	public Boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(Boolean isAssigned) {
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

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IpAddress other = (IpAddress) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		return true;
	}

	
	
}
