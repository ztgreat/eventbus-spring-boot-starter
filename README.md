#基于aliyun消息队列的spring boot starter<br>

##用法<br>

###step1：在项目的pom.xml文件中添加如下依赖
```
    <dependency>
        <groupId>com.deepexi</groupId>
        <artifactId>deepexi-aliyunmq-spring-boot-starter</artifactId>
        <version>2.0.0.Release</version>
    </dependency>
    <!--
        版本号说明:
            Alpha: 开发版
            Beta: 测试版
            RC: 稳定版
            Release: 最终发布版
     -->
```
###step2：在配置文件application.properties里面添加配置信息(配置会自动提示且有注释说明)
```
# 阿里云rocketMQ配置(目前仅实现RocketMQ)
deepexi.aliyunmq.access-key = 阿里云账号AccessKeyId
deepexi.aliyunmq.secret-key = 阿里云账号AccessKeySecret
deepexi.aliyunmq.rocketmq.group-id = 消息组ID（由阿里云控制台创建）
deepexi.aliyunmq.rocketmq.namesrv-addr = 消息队列实例TCP接入点（阿里云控制台可知）
# 订阅方式：CLUSTERING-集群订阅；BROADCASTING-广播订阅
deepexi.aliyunmq.rocketmq.message-model = clustering

###### 以下是默认配置，可根据业务场景修改 ######
# 发送超时时间，单位毫秒
#deepexi.aliyunmq.rocketmq.timeout-millis=3000
# 消费线程数量
#deepexi.aliyunmq.rocketmq.consume-thread-nums=64
# 每条消息消费的最大超时时间,超过这个时间,这条消息将会被视为消费失败,等下次重新投递再次消费,每个业务需要设置一个合理的值,单位(分钟)
#deepexi.aliyunmq.rocketmq.consume-timeout=15
# 每次批量消费的最大消息数量,允许自定义范围为[1, 32],实际消费数量可能小于该值
#deepexi.aliyunmq.rocketmq.consume-message-batch-max-size=1
```
###step3：发送消息<br>
####3.1、注入RocketMQService
```    
    @Resource
    private RocketMQService rocketMQService;
```        
####3.2、RocketMQService中API说明
```
    /**
     * 发送普通消息（可靠同步发送）
     * <p>
     * 同步发送是指消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     * @return SendResult
     */
    SendResult sendMessage(Message message);

    /**
     * 发送普通消息（可靠异步发送）
     * <p>
     * 异步发送是指发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式
     * 消息队列 RocketMQ 的异步发送，需要用户实现异步发送回调接口（SendCallback）
     * 消息发送方在发送了一条消息后，不需要等待服务器响应即可返回，进行第二条消息发送
     * 发送方通过回调接口接收服务器响应，并对响应结果进行处理
     *
     * @param message  消息体（com.aliyun.openservices.ons.api.Message）
     * @param callback 回调接口
     */
    void sendMessage(Message message, SendCallback callback);

    /**
     * 发送普通消息（单向发送）
     * <p>
     * 单向发送特点为发送方只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答
     * 此方式发送消息的过程耗时非常短，一般在微秒级别
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     */
    void sendOneWayMessage(Message message);

    /**
     * 发送顺序消息
     *
     * @param message     消息体（com.aliyun.openservices.ons.api.Message）
     * @param shardingKey 分区顺序消息中区分不同分区的关键字段
     * @return SendResult
     */
    SendResult sendOrderMessage(Message message, String shardingKey);

    /**
     * 发送延时消息
     *
     * @param message         消息体（com.aliyun.openservices.ons.api.Message）
     * @param delayTimeMillis 时长(单位：毫秒),最大延迟时间为7天
     * @return SendResult
     */
    SendResult sendDelayMessage(Message message, Long delayTimeMillis);

    /**
     * 发送定时消息
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     * @param date    指定时间,最大延迟时间为7天
     * @return SendResult
     */
    SendResult sendDateMessage(Message message, Date date);

    /**
     * 发送事务消息
     * <p>
     * 该方法用来发送一条事务型消息. 一条事务型消息发送分为三个步骤:
     * <ol>
     * <li>本服务实现类首先发送一条半消息到到消息服务器;</li>
     * <li>通过<code>executer</code>执行本地事务;</li>
     * <li>根据上一步骤执行结果, 决定发送提交或者回滚第一步发送的半消息;</li>
     * </ol>
     *
     * @param message  消息体（com.aliyun.openservices.ons.api.Message）
     * @param checker  事务检测器，回查本地事务，由Broker回调Producer
     * @param executer 本地事务执行器
     * @param o        应用自定义参数，该参数可以传入本地事务执行器
     * @return SendResult
     */
    SendResult sendTransactionMessage(Message message, LocalTransactionChecker checker, LocalTransactionExecuter executer, Object o);
```            
###step4：订阅消息配置<br>
####4.1、自定义Listener业务类实现com.aliyun.openservices.ons.api.MessageListener接口（根据业务场景可有多个Listener实现来处理不同的业务）
```
public class TestListener implements MessageListener {
    private static Logger log = LoggerFactory.getLogger(TestListener.class);

    /**
     * 订阅回调方法
     
     * @param message 消息体
     * @param context 每次消费消息的上下文，供将来扩展使用
     *
     * @return Action.CommitMessage - 消费成功，继续消费下一条消息
     *         Action.ReconsumeLater - 消费失败，告知服务器稍后再投递这条消息，继续消费其他消息
     */
    @Override
    public Action consume(Message message, ConsumeContext context) {
        
        消息处理.....
        
        return Action.CommitMessage;
    }
}
```
####4.2、定义Listener配置类实现org.springframework.boot.ApplicationRunner，并注入RocketMQListener
```
@Configuration
public class RocketMQConfig implements ApplicationRunner {
    @Resource
    private RocketMQListener rocketMQListener;
    
    /**
     * 该方法在springboot应用完全启动后执行的方法，此处用来做MQ消息订阅配置
     */    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 添加完多个Listener后一定要调用start()方法开启订阅
         */    
        rocketMQListener.addListener("topic1", "tag1", new TestListener())
                        .addListener("topic2", "tag2", new XxxListener())
                        .addListener("topic3", "tag3", new XxxtListener())
                        ....可添加多个Listener
                        .start();
    }
}
```
####4.3、RocketMQListener中API说明
```
    /**
     * 监听消息
     *
     * @param topic    消息主题,不可重复订阅主题
     *
     * @param tag      订阅过滤表达式字符串，ONS服务器依据此表达式进行过滤。只支持或运算<br>
     *                 eg: "tag1 || tag2 || tag3"<br>
     *                 如果tag等于null或者*，则表示全部订阅
     *
     * @param listener 消息监听器，Consumer注册消息监听器来订阅消息<br>
     *                 该接口会被{@link Consumer}的多个线程并发调用, 用户需要保证并发安全性<br>
     *                 网络抖动等不稳定的情形可能会带来消息重复，对重复消息敏感的业务可对消息做幂等处理.
     *
     * @return RocketMQListener
     */
    RocketMQListener addListener(String topic, String tag, MessageListener listener);

    /**
     * 开启所有订阅
     */
    void start();

    /**
     * 关闭所有订阅
     */
    void shutdown();

    /**
     * 获取所有订阅信息
     *
     * @return Map<String, String>
     */
    Map<String, String> getSubscribeInfo();

    /**
     * 获取某个标签
     *
     * @param topic 主题
     * @return tag
     */
    String getTag(String topic);

    /**
     * 获取所有订阅的主题
     *
     * @return Set<String>
     */
    Set<String> getTopics();

    /**
     * 取消对某个主题的订阅
     *
     * @param topic 主题
     */
    void cancelListener(String topic);
```
###step5：JsonSerialize工具类<br>
```
    /**
    * 对象序列化
    *
    * @param body Object
    * @return byte[]
    */
   JsonSerialize.serialize(Object body);
    /**
    * 字符串序列化
    *
    * @param body String
    * @return byte[]
    */
   JsonSerialize.serialize(String body);
    /**
    * 字符串反序列化
    *
    * @param body
    * @return String
    */
   JsonSerialize.deSerialize(byte[] body);
    /**
    * 对象反序列化
    *
    * @param body
    * @param clazz
    * @param <T>
    * @return Object
    */
   JsonSerialize.deSerialize(byte[] body, Class<T> clazz);
```