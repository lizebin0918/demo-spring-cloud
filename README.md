# demo-spring-cloud

### 微服务
* 服务地址如何维护
* 如何对目标服务的调用实现负载均衡
* 目标服务是否存活（心跳）
* 为什么要用SpringCloud，而不是dubbo
  * 前者基于HTTP，无状态的，后者基于tcp长连接
  * 前者异构平台，支持跨语言
  * 前者可插拔，AB服务互相调用，如果是dubbo基于长连接，两个服务节点之间是强关联。但是SpringCloud是http弱关联
  * Dubbo 定位服务治理、Spirng Cloud 是一个生态

### Eureka 服务发现、注册

* 原理:Server端（各个节点之间通过心跳同步）+Client端
* Register:服务注册，参与服务注册的实例首先需要向Eureka服务器注册信息
* renew:续租、心跳（**客户端发给服务端**）
* fetch registry:拉取注册列表并缓存到本地
* time Lag：同步时间延迟
* 同步采用http请求
* 环境搭建
> 集群启动成功访问：http://localhost:7000/
> 修改host 
> 127.0.0.1   euk-7000.com
> 127.0.0.1   euk-7001.com
* 同时eureka server的集群也不是类似主从的概念，所有的eureka server都保存了全部的注册表信息，但是大家的角色都是一样的，eureka server中的一个节点叫做一个peer，可以认为eureka server集群中的节点都是peer to peer的模式
* 将eureka的集群机制和zookeeper对比一下，zookeeper的集群一般将角色区分为leader和follower，只有leader才有写的权限，follower只能读，那么当需要数据同步的时候，就需要leader写入，然后将信息同步给其他所有的follower才算完成了数据同步。而eureka server集群则不是这样，集群中的eureka server都是一样的角色，服务可以向任意一个eureka server进行注册，然后由这个eureka server同步给集群中的其他eureka server
* 自我保护机制：客户端每分钟续约数量小于客户端总数的85%时会触发保护机制，不会随便把客户端踢下来
  * 实例数=10个，期望每分钟续租数=20，期望阈值：20*0.85=17个，自我保护少于17时触发
* 客户端会有30秒的缓存列表，会存在调不通的情况

### Ribbon 负载均衡
* 基于客户端的负载均衡
* 从Eureka获取服务列表，如果providor挂了，客户端不会马上发现，存在一定延时
* `LoadBalancerClient`
* 负载均衡算法
  * 区域权衡策略
  * 最低并发策略
  * 轮询策略
  * 随机策略
  * 可用过滤策略
  * 响应时间加权策略
  * 重试策略  

### Open Feign

* 对应module:consumer
* 服务调用
  * 服务发现-DiscoveryClient（Eureka、ZK、Consul、Nacos等）
  * 负载均衡-Ribbon（唯一选择）
  * Feign  
* Feign 
  > Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端 
  > Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。 
  > Feign的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务 
  > Feign支持的注解和用法请参考官方文档：https://github.com/OpenFeign/feign
  > Feign本身不支持Spring MVC的注解，它有一套自己的注解
   
* OpenFeign
 > OpenFeign是Spring Cloud 在Feign的基础上支持了Spring MVC的注解，
 > 可以看做是Feign的扩展，如@RequesMapping等等。
 > OpenFeign的@FeignClient可以解析SpringMVC的@RequestMapping注解下的**接口**，
 > 并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。
 
* Java接口与Rest提供的元信息如何映射
* `@FeignClient`所指定的服务（应用）可能用到了服务发现，一个服务可能对应多个实例
* `@EbableFeginClients`如何感知或加载标注`@FeignClient`的配置类（Bean）
* Fegin请求和响应的内容是如何序列化和反序列化到对应的POJO

### Actuator
* 上报服务信息，spring-admin的数据源

### Hystrix
* 熔断状态：不开、全开、半开状态（类似于写一个random.nextInt() < 5，有一半几率会调用）
* 降级：服务可用，提供最基本的数据（兜底数据），不能直接返回错误，错误是表示服务不可用
* 熔断：计数连续失败次数达到阈值，就不调用服务端了
* 限流和隔离
* 超时情况
  * 本机的网络断了，建立连接就超时了
  * 服务端的业务处理超时了
* 代码:`@FeignClient(name = "服务名", fallback/fallbackFactory = ('服务名' + Back).class)`声明在rpc接口上
* 例如查询一个最新的分类信息，但是降级了，只能返回默认的几个
* 线程隔离和信号量隔离
  * 基于线程池：consumer可以根据provider服务（有可能多个）配置线程数，不至于把consumer自己拖死，但是官方推荐线程隔离
    * 线程池可以有拒绝策略
    * 异步调用，释放tomcat的worker线程
    * 如果发生异常，线程可以隔离
    * 可以根据provider的接口情况来配置调用线程池的大小，如果是容易阻塞的，可以把线程数设置大一点
  * 基于信号量：当前服务的信号量是否达到最大值，有点像令牌。如果超过直接抛异常，性能更好
  
### gateway
* 性能提高
  * 无状态，单点登录
  * 优化线程，异步
  * 限流
  * 权限验证
* 模式：隧道模式和路由模式（穿透，透明的，但是tcp是基于端对端的，如何实现？）

### 链路跟踪
* skywalking

### spring admin
* 应用健康状况

### 配置中心
* spring-config