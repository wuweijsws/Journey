## Redis安装

### 环境
* 系统：Linux centOS 7.5
* Redis版本：redis-5.0.5
* Redis官网：https://redis.io/download

安装redis：
```
wget http://download.redis.io/releases/redis-5.0.5.tar.gz

tar xzf redis-5.0.5.tar.gz

cd redis-5.0.5

make

```

redis.conf修改：

以守护进程运行：`daemonize no => daemonize yes`

启用密码验证：`requirepass test123`

***

以redis.conf运行：

`./redis-cli ../redis.conf`

```
127.0.0.1:6379> set foo bar
OK
127.0.0.1:6379> get foo
"bar"
```





