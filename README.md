# spring-cloud-start

这是一个快速开始分布式实践的项目。该项目会持续集成分布式各种能力（Hystrix、Ribbon、OpenFeign、Bus、Hmily、RabbitMQ…），同时集成各种SDK能力（短信、邮件、pdf、推送、微信…）。文档请查看[Wiki](https://github.com/huaPeiLiang/spring-cloud-start/wiki)

#### 写在前面

当前仓库已变更为提供学习使用。您还是可以使用它作为您项目的开始，但这一做法并不推荐。了解到人们更多的是需要一套完整且简洁明了的架构，而非是集成了很多相同功能的架构集合。所以将这一仓库变更为学习使用，如需获取一套适合自己且完整的分布式微服务架构请移步至[fast-cloud](https://github.com/huaPeiLiang/fast-cloud)。我将在这里提供解决方案。

#### 你能用它干什么？

* 快速开始分布式学习：下载代码，参照wiki。就可以轻松并快速的学习分布式。未来针对某一能力将集成各种解决方案，可以直观的去了解它们。
* 快速开始分布式开发：请移步至[fast-cloud](https://github.com/huaPeiLiang/fast-cloud)。

#### 项目构架图

![image](https://raw.githubusercontent.com/wiki/huaPeiLiang/spring-cloud-start/img/ReadMe-1.png)

#### 使用的技术

注册中心：Eureka

配置中心：Config

配置自动更新：Bus

服务间调用：OpenFeign

负载均衡：Ribbon

断路器：Hystrix

消息中间件：RabbitMQ

分布式事务：Hmily、TX-LCN

搜索引擎：Elasticsearch

存储：Mysql+MongoDB+Redis



力量有限，任何建议和疑问请联系我，感谢！

联系方式：martin.hua@foxmail.com
