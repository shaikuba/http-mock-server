# 晒酷学院
[软件测试视频教程](http://shareku.ke.qq.com/)
<p>
<a href="https://jq.qq.com/?_wv=1027&k=EQGVQd8Z">软件测试交流学习群</a>
</p>
<br/>

# 扫码加入微信学习交流群

![avatar](img/weiwei.png)

# Mock Server Tools
***
Mock server used to support for testing as substitution of the real server.
## Features

- HTTP protocol api mock service
- HTTPS protocol api mock service, certificates are stored in the securities directory

## Where and What form are the Mock Data Store?
- Commons
    > All types of mock service can be stored into DB(Mysql), Mongo, File, but for the different store type mush have one
    data loader which should implements MockDataLoader, and explicit specify the implementation with property mock.server.http.data-loader
- Http(s) mock service
- Soap mock service

## Steps for using ssl mock server

1.modify the application.yml
```yaml
spring:
  profiles:
    active: dev
```
    
2.run the command in IDEA
```maven
mvn clean compile -Pdev
```

3.modify the mysql name in application-dev.yml
```yaml
urdataSource-1l: jdbc:mysql://localhost:3306/mock-server?serverTimezone=UTC&useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&useSSL=false
```

4.if your password forgotten
4.1 run the below command firstly after stop the mysql service by manual
```cmd
C:\Program Files\MySQL\MySQL Server 5.7\bin>mysqld --defaults-file="C:\ProgramData\MySQL\MySQL Server 5.7\my.ini" --console --skip-grant-tables
```

4.2 enter mysql console
```cmd
C:\Program Files\MySQL\MySQL Server 5.7\bin>mysql -uroot
```

4.3 update root's password to 123456
```cmd
mysql> update user SET `authentication_string` = PASSWORD('root');
Query OK, 4 rows affected, 1 warning (0.00 sec)
Rows matched: 4  Changed: 4  Warnings: 1
```

```cmd
mysql> update user set authentication_string='123456' where user='root';
Query OK, 2 rows affected (0.00 sec)
Rows matched: 2  Changed: 2  Warnings: 0
```

```cmd
mysql> select user,host,authentication_string from user;
+-----------+-----------+-------------------------------------------+
| user      | host      | authentication_string                     |
+-----------+-----------+-------------------------------------------+
| root      | localhost | 123456                                    |
| mysql.sys | localhost | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| user      | %         | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| root      | %         | 123456                                    |
+-----------+-----------+-------------------------------------------+
4 rows in set (0.00 sec)
```

5.enter http://localhost:8080/ in your web browser

