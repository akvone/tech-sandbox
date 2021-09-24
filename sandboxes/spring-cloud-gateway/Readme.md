# Initial configuration
Replace `spring.cloud.gateway.routes[0].uri` in `application.yaml`.
You may use https://getsandbox.com/ for example. 

# Important
* Use `-Dreactor.netty.http.server.accessLogEnabled=true` to have access logs. 
  See https://docs.spring.io/spring-cloud-gateway/docs/3.0.4/reference/html/#reactor-netty-access-logs