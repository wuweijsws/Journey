# SpringBoot集成Sentry

> 上文讲了linux系统下sentry安装的步骤,传送门：[Sentry安装](../sentry安装.md)
> 本文讲述`Sentry`的使用以及在`SpringBoot`项目中集成`Sentry`的正确姿势

## 1.在Sentry中创建好项目
创建一个项目test-java-service  
![](sentry-use/new_project.png)

进入项目主页点击设置：  
![](sentry-use/project_home.png)

进入设置页面直接点击`客户端密钥(DSN)`,可以看到为我们生成了一个默认的客户端密钥  
![](sentry-use/DSN_KEY.png)

点击`配置`按钮进入密钥详情页，获取到公钥、私钥以及项目的Id：  
![](sentry-use/key_detail.png)

这样我们就可以根据配置规则获取到这个项目的DSN地址，配置规则：  
```
#配置规则：协议://公钥:私钥@你安装的sentry访问地址/项目Id
dsn = 'http://public:secret@example.com/project-id'

```
按照图中给出的信息，我们就可以获取到test-java-service的DSN地址为：
```jshelllanguage

http://8299859d9dc5427ba05a652ca6b0a96f:249f628d6c7f493fb7b5fa0e652318b0@127.0.0.1:9000/5

```
## 2.SpringBoot集成Sentry

添加maven依赖：  
```xml
        <!-- sentry -->
        <dependency>
            <groupId>io.sentry</groupId>
            <artifactId>sentry</artifactId>
            <version>1.7.16</version>
        </dependency>

        <dependency>
            <groupId>io.sentry</groupId>
            <artifactId>sentry-logback</artifactId>
            <version>1.7.16</version>
        </dependency>

        <dependency>
            <groupId>io.sentry</groupId>
            <artifactId>sentry-spring</artifactId>
            <version>1.7.16</version>
        </dependency>
```

resources目录下增加资源文件sentry.properties:  
```properties
#创建的项目的client keys
dsn=http://4ee3632dd8cc4d0d8d3a10eef8e38b94:f79af3ffaf0e4070afa3863c62404fb7@47.92.4.80:9000/4

#应用的包名
stacktrace.app.packages=com.bbyl.forum

#上报频率 1是全部都上报
sample.rate=1

#运用环境 dev test gamma prod
environment=test
```
启动类`BootApplication`增加一个bean定义：

```java
    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }
```

logback.xml中增加sentry日志组件：

```xml
    <!-- Sentry异常上报 -->
    <appender name="SENTRY" class="io.sentry.logback.SentryAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="SENTRY"/>
    </root>
```
这样项目运行期间产生的异常、ERROR级别日志将会上报到sentry控制台。

## 3.java -jar启动参数

当我们在服务器上以`java -jar`命令启动项目时候，需要显示指定sentry相关参数，示例如下：

```jshelllanguage

DSN=`sed '/^dsn=/!d;s/.*=//' $CONF_DIR/sentry.properties`
echo 'DSN='$DSN

DSN_PKG=`sed '/^stacktrace.app.packages=/!d;s/.*=//' $CONF_DIR/sentry.properties`
echo 'DSN_PKG='$DSN_PKG

DSN_RATE=`sed '/^sample.rate=/!d;s/.*=//' $CONF_DIR/sentry.properties`
echo 'DSN_RATE='$DSN_RATE

DSN_ENV=`sed '/^environment=/!d;s/.*=//' $CONF_DIR/sentry.properties`
echo 'DSN_ENV='$DSN_ENV

nohup java -Dsentry.dsn=$DSN -Dsentry.stacktrace.app.packages=$DSN_PKG -Dsentry.sample.rate=$DSN_RATE -Dsentry.environment=$DSN_ENV -jar

```
## 4.Sentry+钉钉告警

sentry控制台选中WebHooks，输入一个地址，当有告警信息时候，会发送通知到这个地址：
![](sentry-use/webhooks.png)

那么我们就可以写一个中间件，当有告警信息过来时候，我们转化为钉钉告警群的指定钩子：

```java
/**
 * sentry通知
 * @Author: java
 * @Date: 2019-07-05 17:32
 */
@RestController
@Slf4j
public class SentryNotificationController {

    private static final String DING_TALK_HOOKS = "https://oapi.dingtalk.com/robot/send?access_token=56cbf4c8869eebd240d5sadcae21926182b4671sad2350397ad39dfa2c2167a62sa227ce35de";

    private static final String SENTRY_ALERTS_PIC = "https://s1.sentry-cdn.com/_static/fc5334ea06d8f24e1360cc65466a19cd/sentry/dist/java.svg";

    public static final String SENTRY_BASE_URL = "http://127.0.0.1:9000";

    @PostMapping("/notification")
    public void notification(@RequestBody SentryAlertsReq sentryAlertsReq) {
        Gson gson = new Gson();
        log.info("获取到sentry的消息:{}", gson.toJson(sentryAlertsReq));

        DingTalkLink dingTalkLink = DingTalkLink.builder()
                .title("【"+ sentryAlertsReq.getLevel() + "】" + sentryAlertsReq.getMessage())
                .text(sentryAlertsReq.getCulprit())
                .messageUrl(SENTRY_BASE_URL + sentryAlertsReq.getUrl())
                .picUrl(SENTRY_ALERTS_PIC)
                .build();

        DingTalkReq dingTalkReq = DingTalkReq.builder().msgtype("link").link(dingTalkLink).build();

        OkhttpUtil.sendPost(DING_TALK_HOOKS, gson.toJson(dingTalkReq), true, null);
    }
}
```
***
至此，sentry集成完毕了，sentry能够将线上系统运行的异常、错误日志实时的反馈到开发人员。


