# 基于EventBus的spring boot starter<br>

## 用法<br>

### step1：在项目的pom.xml文件中添加如下依赖
```
    <dependency>
        <groupId>com.deepexi</groupId>
        <artifactId>deepexi-eventbus-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
```
### step2：在配置文件application.properties里面添加配置信息(配置会自动提示且有注释说明)
```
# 是否开启EventBus
deepexi.eventbus.enabled=true
# 线程池名称
deepexi.eventbus.executor.pool-name=deepexi-EventBus
# 线程池核心线程池大小
deepexi.eventbus.executor.core-pool-size=18
# 最大线程池大小
deepexi.eventbus.executor.maximum-pool-size=50
# 线程池的时长
deepexi.eventbus.executor.keep-alive-second=60
# 线程池等待队列长度
deepexi.eventbus.executor.queue-size=1024
```
### step3：注册使用EventBus<br>
#### 3.1、注册Listener到EvenBus，必须为Spring的Bean
```    
    @EventBusListener
    @Service
    public class DingMessageServiceImpl implement DingMessageService {
        // 订阅某个对象事件
        @Subscribe
        @Override
        public void listen(Event e) {
        }
    }
```        
#### 3.2、发送EventBus事件
```
    @Service
    public class DingMessageServiceImpl implement DingMessageService {
        // 注入EventBus实例
        @Resource
        private EventBus eventBus;
        
        @Override
        public void sendMessage(){
            eventBus.post(new String("Msg"));
        }
    }
```       