# demo-spring-cloud

### 微服务
* 服务地址如何维护
* 如何对目标服务的调用实现负载均衡
* 目标服务是否存活（心跳）

### Eureka 服务发现、注册

* 原理:Server端+Client端
* Register:服务注册，参与服务注册的实例首先需要向Eureka服务器注册信息
* renew:续租、心跳（客户端发给服务端）
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

### Ribbon 负载均衡

* 负载均衡算法： 

### Open Feign

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
* 