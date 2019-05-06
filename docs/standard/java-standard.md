#变量定义
- 必须使用英文命名，禁止使用拼音，英文可以查一下，防止拼写错误。

- 变量使用驼峰命名，由于历史原因代码中非驼峰的，逐步改造过来。

例:taskUserKey

- 包命名全部以 com.company 开始，.后面为对应模块。

    例:com.company.base 基础包，com.company.verify 认证， com.company.home 首页包。

- 类和方法，尽量做到词能达意，禁止不明缩写，变量或者方法名稍长一点没关系。 例:findOrderListByUserIdAndOrderStatusAndCreateTimeBetween

- 常量全部大写，下划线分割。
    例:public final static String TAG_STORY_ADAPTER_PRODUCT

- 方法前缀。
    1) 获取单个对象的方法用 get 做前缀。 

    2) 获取多个对象的方法用 list 做前缀。 

    3) 获取统计值的方法用 count 做前缀。 

    4) 插入的方法用 save/insert 做前缀。

    5) 删除的方法用 remove/delete 做前缀。 

    6) 修改的方法用 update 做前缀。

 

- model 必须继承 SerializableserialVersionUID，serialVersionUID 不要写 1L，让 ide 帮 助生成。

    例:private static final long serialVersionUID = 7586533615511810668L;

- 对应数据库 int 主键的变量定义必须用 Long 型，避免数据库扩容带来的类型转换各种不兼 容问题。

- 不同类型的常量定义放在相对应的静态文件中。
    例:比如缓存的放在 ConstantsCacheKey.java,充值卡类型 ConstantsCard.java。
# 代码格式
- 代码统一使用 Intellij IDEA 默认的格式化方式 。

- 缩进采用 4 个空格缩进，禁止使用 tab 字符，可以修改 ide 配置，将 tab 自动替换为空格。

- IDE 中设置全部文件格式为 UTF-8; IDE 中文件的换行符使用 Unix 格式(LF)， 不要使用 Windows 格式和 MAC 格式。

- 代码不同的业务逻辑用一个空行分割，可以适当写上注释。

- 一个程序文件应小于1000行，尽量小于500行，避免大文件。

- 方法的长度(包括方法体内的注释)小于 80 行。方法的参数应控制在 7 个以内。



#数据库
- 禁止在代码中写 sql，如用 jpa 则必须使用@Query(native=true),原生 sql。

- 非后台服务禁止使用联合查询。

- sql 中的字段必须跟 model 中的属性顺序保持一致，方便增删，避免出现失误。

- 数据库的主键尽量使用 number，避免使用 varchar 做主键。

- 禁止使用 sql 做模糊查询，如有需要可提交运维另出方案评估。

- 所有的 sql 必须走索引，避免全表扫描。

- 一个 Mapper 或者 DAO 中只能访问自己命名对应的表，禁止同一个文件中访问多个库或表。

- @Query 中禁止使用?，使用@Param 显式参数传递。

- sql 中不要使用 to_days 这样的函数在字段值上。

    例:to_days(s.saletime) <= to_days(NOW())，这样增加 sql 的计算量，并且不走索引。
改为 s. s.saletime<=:nowTime, nowTime 在 java 中计算好。

- 一个 sql->一个 dao 中的 method->一个 service 中的 method。

    例:sql:select x1,x2 from xx -> Dao:Xx findOne(xId) ->Service:Xx findOne(xId)
这样做的好处是:1)方法间解耦 2)入口单一，如果做缓存操作，只需要修改 Service 即 可。

- 禁止在数据库中使用存储过程和触发器。

- 尽量减少 in 的使用，必须使用的话要知道 in 里面传递多少个，由运维评估。

- 数据库层面的事务，尽量保持在数据库的操作上，不要放大到整个 service 层面。

- 创建的字段要有默认值，比如 varchar 的要“”，int 要有 0，默认值从 0 开始。0 代表初始化。

- 数据库建表和字段使用小写下划线连接。
    例:kaishu_user:user_id 。

- 禁止使用保留字。
    例:kaishu_user 中用户的状态可以使 user_status 而不是 status。

- 小数类型为 decimal，禁止 float 和 double。

- 状态字段使用 tinyint，而不是使用 int。

- 不同表中，相同字段要保持一致。
    例:user_id 在 kaishu_user 中为 int(11)，在 java 中是 Long,在其他的表中也必须为 int(11),在 java 中统一都要是 Long，不能有的地方用 String。

- 索引要控制不要超过 10 个，sql 尽量采用已有索引结构，避免更多的索引建立与更新。
    多条件查询，要把第一个查询条件设置为覆盖最小范围的。例:user_id=# and user_status=# 而不是:user_status=# and user_id=#

 

#开发约束
- 索引要控制不要超过 10 个，sql 尽量采用已有索引结构，避免更多的索引建立与更新。

- 代码中禁止 new Thread,Runnable，必须使用线程池。

- 线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式。

- Executors 返回的线程池对象的弊端如下: a. FixedThreadPool 和 SingleThreadPool:

- 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。 b. CachedThreadPool 和 ScheduledThreadPool:

- 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。

- 谨慎使用 HashMap，要考虑线程安全，考虑 HashMap 的扩容。

- 日志统一使用 slf4j，看到了写死 logback、log4j 的就改。

- 每个 mapper 都有单独的 service 对应，其他的 service 只能调用对应 service，不能调用与自己名不配对的 mapper。比如在 AService 中调用 AMapper、BMapper，这样是不对的。
应该为 BMapper 建立一个 BService,然后 AService 调用 AMapper、BService。 使用 jpa 的话，Dao 也是一样。

- 可以使用值传递的尽量不用对象传递，比如 ReqOrderVo 中有 userId，直接传递 userId 就可 以了。

- 尽量使用 idea，idea 里尽量减少警告。

- 创建 List 时，如果能够确定大小，初始化时就指定 List 的大小，避免动态扩容。

- 在与数据库对应的 orm model 对象中，字段要设置默认值与数据库中的字段对应。比如 xxName 在数据库中是 xx_name varchar(255) default “”,在 model 里面 private String xxName = “” 这样。 优点是升级的时候，不会因为缓存中的数据在反序列化时出现代码异常。

- try catch 打印异常要打印 e.getMessage,避免 e 对象，如果 Exception 有互相依赖时会引 发死循环。

- 捕获的异常必须处理，禁止捕获异常无任何日志输出。

- svn 或 git 的提交描述，必须写有意义的内容。例:录音 bug 修复，而不是 v 或者 xx。

- 对于一个对象或者事物的判断，统一在一个方法中。
    例:Constants.CONTENT_STORY == ud.getContent_type() || Constants.CONTENT_MAMAWEIKE == ud.getContent_type();可以是 isStory(int type) 这样统一判断，如果需求变更也只需要改变一个方法即可。

 

#缓存规范
- KV缓存仅限于从数据库映射的字段缓存，或者其他的对象级缓存。

- 列表结果的缓存尽量减少使用 KV 的缓存，更换为复杂关系缓存，结合对象缓存，在程序中算出结果。
- 缓存的 KEY 统一管理。

- 命名:
    
    1)所有的缓存变量名大写，缓存 key 大写。
    
    2)public final static String USER_ORDER_ALL_PRODUCT_KEY = "U_O_P_HS:"; 大写的 KV 结尾:是 KV。
    
    3)大写的 S 结尾:是 Set。
    
    4)大写的 HS 结尾:是 HashSet。
    
    5)大写的 ZS 结尾:是 ZSet。
    
    6)大写的 SS 结尾:是 SortSet。

    7)大写的 LP 结尾:是 LPush。

    PT_KV:123 PT 表示 Product KV 表示是 kv 存储 123 表示 ProductId 注释:存储在 product redis

    PT_S:1 PT 表示 Product S 表示 Set 1 表示是故事类型注释:存储在 user redis

    U_O_HS:46 U 表示用户 O 表示 Order HS 表示 HashSet 46 用户 ID 注释:存储在 user redis

    U_O_P_HS:46 U 表示用户 O 表示 Order P 表示 Product HS 表示 HashSet 46 表示用户 ID 注释:存储在 user redis

    T_U_STAR_SS:46 T表示任务U标用用户STAR表示用户星星SS表示SortSet

    T_U_STAR_Z:46 T表示任务U标用用户STAR表示用户星星 Z表示ZSet

        一些数据可能会同时存在不同的 redis 用来做交集，因此不把缓存的实例放在 key 命名中。

    禁止使用单key的大hash存储业务数据，如一个key 存储几十万的field，value，根据进业务特点将大hash拆分为若干个小hash，如缓存用户相关数据，key=userid/500,field=userid%500，500即每个hash存储的field数量，另外field尽量使用数值型，value尽量的短。

- 缓存使用原则:
    缓存优先考虑性能与命中。
   
    关系保存的有两种:一种是公用的，比如一个分类下的商品 ID 列表。一种是个性 化的，比如用户的订单，只有用户自己访问。
    
    获取最终结果由关系查到对象，拼装显示。 
    
    string类型控制在10KB以内，hash、list、set、zset元素个数不要超过5000。
    
    禁止使用 keys 模糊查询，可用 scan 替代并设置limit。


#消息队列命名规范
TOPIC命名：TOPIC_环境+下划线+业务描述（单词间用下划线连接，最多不超过5个单词），如：TOPIC_DEV_ACCOUNT_BIND，意义为开发环境账号绑定TOPIC
生产者命名：PID+下划线+环境+下划线+业务描述（单词间用下滑连接，最多不超过6个单词）,如：PID_DEV_ACCOUNT_BIND，意义为账号绑定的消息生产者id
消费者命名：CID+下划线+环境+下划线+业务描述（单词间用下滑连接，最多不超过6个单词），如：CID_DEV_ACCOUNT_BIND_GIFT，意义为账号绑定送礼物消费者id
各个环境的TOPIC均在阿里云的华北2区创建
注：以上内容全部大写，单词间用下划线连接，实现更好的可读性

环境说明：DEV/TEST/TEST02(压测环境)/GAMMA/PROD

MQ监控告警配置要求
消息队列（Message Queue，简称 MQ）是构建分布式互联网应用的基础设施，通过 MQ 实现的松耦合架构设计可以提高系统可用性以及可扩展性，是适用于现代应用的最佳设计方案。MQ 产品生态丰富，多个子产品线联合打造金融级高可用消息服务以及对物联网的原生支持，覆盖金融保险、(新)零售、物联网、移动互联网、传媒泛娱乐、教育、物流、能源、交通、等行业。

MQ规范：所有的MQ都必须设置【监控告警】，通过监控告警能够第一时间知道业务的健康程度，并做排查然后解决，如下图所示：



MQ日志规范
需要在logback.xml 中添加针对MQ的日志输出规范，不然MQ的日志默认会写入到 启动当前服务的用户家目录下(user.home) 

参考：https://help.aliyun.com/document_detail/43460.html?spm=a2c4g.11186623.6.583.X8GSDd

 

<!-- MQ -->
    <appender name="MQ" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH}/ons.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover -->
            <FileNamePattern>${LOG_PATH}/ons.%d{yyyy-MM-dd}.log</FileNamePattern>
        </rollingPolicy>
        <encoder>
            <Pattern>
                %date{yyyy-MM-dd HH:mm:ss.SSS} %-5level ${PID:- } --- [%thread] %logger{35} [%line] - %msg%n
            </Pattern>
        </encoder>
    </appender>
 
 
    <root level="INFO">
        <!--<appender-ref ref="CONSOLE"/>-->
        <appender-ref ref="dailyRollingFileAppender"/>
        <appender-ref ref="errorRoolingFileAppender"/>
        <appender-ref ref="SENTRY"/>
        <appender-ref ref="MQ"/>
    </root>
#opensearch命名规范
环境_业务含义。全部大写且在阿里云的华北2区创建
如：DEV_USER_MESSAGE_SEARCH 代表开发环境的用户通知搜索

环境说明：DEV/TEST/TEST02(压测环境)/GAMMA/PROD
spring声明式事物使用注意事项
在需要事务管理的地方加@Transactional 注解。@Transactional 注解可以被应用于接口定义和接口方法、类定义和类的 public 方法上。
@Transactional 注解只能应用到 public 可见度的方法上。 如果你在 protected、private 或者 package-visible 的方法上使用 @Transactional 注解，它也不会报错， 但是这个被注解的方法将不会展示已配置的事务设置（说白了就是事务无效）。
注意仅仅 @Transactional 注解的出现不足于开启事务行为，它仅仅 是一种元数据。必须在配置文件中使用配置元素，才真正开启了事务行为。(spring配置文件中,开启声明式事务)
通过 元素的 “proxy-target-class” 属性值来控制是基于接口的还是基于类的代理被创建。如果 “proxy-target-class” 属值被设置为 “true”，那么基于类的代理将起作用（这时需要CGLIB库cglib.jar在CLASSPATH中）。如果 “proxy-target-class” 属值被设置为 “false” 或者这个属性被省略，那么标准的JDK基于接口的代理将起作用。
Spring团队建议在具体的类（或类的方法）上使用 @Transactional 注解，而不要使用在类所要实现的任何接口上。在接口上使用 @Transactional 注解，只能当你设置了基于接口的代理时它才生效。因为注解是 不能继承 的，这就意味着如果正在使用基于类的代理时，那么事务的设置将不能被基于类的代理所识别，而且对象也将不会被事务代理所包装。
@Transactional的事务开启 ，或者是基于接口的 或者是基于类的代理被创建。所以在同一个类中一个无事务的方法调用另一个有事务的方法，事务是不会起作用的。
日志规范
        log.info("[所属业务][功能模块] - 日志说明，参数名称={}", 具体参数)

        避免在程序中使用 System.out 和 System.err 输出信息。 级别规范:

        ➢ trace 任意的跟踪信息，级别最低

        ➢ debug 中间变量内容信息

        ➢ info 程序运行时候的正常操作信息

        ➢ warning 非正常操作，但不影响最终结果的操作信息

        ➢ error 异常操作，影响结果导致无法继续操作的异常信息

        ➢ fatal 致命异常，导致系统无法正常运行的操作



#异常处理规范
异常捕获处理的方式
在一大段代码上加一个try-catch ，是在偷懒。catch尽量区分能确定的异常和未知异常，要做细化处理。
如：
```
try{
    //代码省略
    map.put("message", "success！");  
    map.put("status", 200);  
} catch (UnknownHostException e) {
    //域名错误
    map.put("message", "请输入正确的网址");
    LoggerUtils.fmtError(e, "网址异常[%s]", url);
} catch (SocketTimeoutException e) {
    //超时
    map.put("message", "请求地址超时");
    LoggerUtils.fmtError(e, "请求地址超时[%s]", url);
  
} catch (Exception e) {
    //其他异常
    map.put("message", "请求出现未知异常，请重试！\r\n" + e.getMessage());
    LoggerUtils.fmtError(e, "请求出现未知异常，请重试！[%s]", url);
}
```
运行异常的处理
不要捕获Java类库中定义的继承自 RuntimeException 的运行时异常类，如：IndexOutOfBoundsException / NullPointerException ，数组越界、空指针这类的异常，应该在开发中检查避免，而不是捕获异常。
如：if(null != obj) {}
事务中的异常
在事务中使用异常时，如果需要事务回滚，需要把异常抛出。
在 Finally 中要清理资源或使用 Try-With-Resource 语句
```
try{
    //代码省略
} catch (Exception e) {
    throw new HNException("xxx处理失败。",e);
  
}finally{
              
    try {
        if(null != conn){
            conn.disconnect();
        }
    } catch (Exception e1) {
        LoggerUtils.fmtInfo("请求完毕关闭流出现异常！[%s]", url);
    }
    try {
        if(null != outStream){
            outStream.close();
        }
    } catch (Exception e2) {
        LoggerUtils.fmtInfo("请求完毕关闭流出现异常！[%s]", url);
    }
    try {
        if(null != out){
            out.close();
        }
    } catch (Exception e3) {
        LoggerUtils.fmtInfo("请求完毕关闭流出现异常！[%s]", url);
    }
              
}
```
要逐层关闭。我们统一用的是JDK8,可以使用Try-With-Resource。

```
public void automaticallyCloseResource() {   
    File file = new File("./tmp.txt");   
    try (FileInputStream inputStream = new FileInputStream(file);)
    {
        //todo
    }
    catch (FileNotFoundException e) {
        log.error(e);
    } catch (IOException e) {
        log.error(e);
    }
}
```
自定义异常需要有对应的文档，对异常产生的原因和解决方案有具体描述。
描述信息需要与异常同步抛出，有助于了解问题严重性，更有效的分析定位服务事故。
禁止捕获 Throwable
有些错误是由 JVM 抛出的，应该在日志中能够体现出来。
禁止忽略异常
总会有其他人维护你的代码，没有异常不利于排查定位问题。
不要同时打印并抛出异常
对异常进行包装后，不能抛弃原有的异常。
丢失原始异常的 stack trace 与信息可能会导致分析困难。

最后：养成良好的编程习惯，代码中尽量减少出现异常的可能。
比如：方法可以返回null，但是不要返回一个空对象，空的集合。数据库字段要有默认值，不用null做为默认值。代码中的变量也是一样，提供给其他人，或者调用他人接口的时候，保持数据纯净，避免脏数据。如果一半的代码都是在判断空对象，空指针的话，可读性也很糟。


#Dubbo规范
Dubbo返回结果必须是DTO对象，DTO必须实现Serializable接口，并包含serialVersionUID。目前的dubbo服务没有与自有Service服务区分开，需逐步改造。
#第三方服务
调用第三方服务一般为Http方式
1、必须使用连接池，控制连接池的大小和单个路由的大小。
2、连接必须有效释放。
3、必须设置超时时间，回收资源。


#压测规范
在交付给测试之前，一定要自己做一下压测，能够暴露出的问题解决在开发环节。
申请压测的时候，可以标明自己修改的部分和调用方的接口。


#JAR包命名规范
命名格式
现有服务命名有以下格式：ks-xxx-service、ks-xxx-web、ks-xxx-center、ks-cms-xxx、ksjgs-xxx-center。ksjgs是中台服务中心的命名，ks-xxx-service是服务化、微服务的命名方式，偏向dubbo服务，ks-xxx-web偏向对用户的api服务。
目前dubbo和api服务，尚未隔离。
版本
版本号4位，可以是1.0.0.0，开发、联调、模块测试阶段带上SNAPSHOT。
如:ks-product-service-1.0.0.0-SNAPSHOT.jar,集测阶段以后去掉SNAPSHOT，ks-product-center-1.0.0.0.jar,若在集测阶段仍然有bug修复，每一次发包，均升级第四位小版本号：ks-product-center-1.0.0.1.jar
启动判断
启动的过程中会实时监测系统默认的启动标识是否打印出来，例如: `JVM running for 14.38`;  所以采用spring 框架的服务一定不要去 重新对应的 run 方法，保持默认即可！


