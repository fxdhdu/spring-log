#spring-log
spring-log 1.0是基于spring aop实现的注解式的日志插件，类似于spring的异步注解
## 使用方法
1. xml定义，即开启日志注解功能
```
<!-- 配置spring log -->
<bean class="com.xjy.spring.log.core.LogAnnotationBeanPostProcessor" />
```
2. 在需要插入日志的服务上加上日志注解
