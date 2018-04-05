# ipAddressallocator
Dynamically allocates unique Ip addresse on request.

Following are the endpoints exposed by the app.

1. Alloacte IP 
Method - GET 

URl - http://localhost:8080/allocateAddress/<mac Address>

Response - <allocated IP address>

2. Heartbeat message

Method - POST

Url - http://localhost:8080/heartBeat

Request body -  
{
	"macAddress":"545223",
	"allcatedIpAddress":"192.111.111.84"
}


IP address range and Expiry time of allocated IP can be configured in application.properties file in following properties like-
custom.property.timeout=5
custom.property.timeoutUnit=HOURS
custom.property.startIP=192.111.111.1
custom.property.endIP=192.111.111.100
