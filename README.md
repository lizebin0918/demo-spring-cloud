# demo-spring-cloud

### Eureka 服务发现

   * 原理:Server端+Client端
   * Register:服务注册，参与服务注册的实例首先需要向Eureka服务器注册信息
   * renew:续租、心跳（客户端发给服务端）
   * fetch registry:拉取注册列表并缓存到本地
   * time Lag：同步时间延迟
   * 同步采用http请求
   
### Ribbon 负载均衡

   * 负载均衡算法： 
