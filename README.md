# 晒酷学院
[软件测试视频教程](http://shareku.ke.qq.com/)
<p>
<a href="https://jq.qq.com/?_wv=1027&k=EQGVQd8Z">软件测试交流学习群</a>
</p>
<br/>

# 扫码加入微信学习群

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

