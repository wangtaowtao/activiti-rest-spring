# activiti-rest-spring
> activit rest集成springMVC的项目，接口通过HTTP 直接访问activiti的接口，跨语言访问Activiti工作流

项目下载后修改dev.properties,数据库配置文件为自己的库数据

启动项目，可参考activiti的接口文档调取接口

## 并且自己使用HttpClient封装了大部分的activiti-rest的接口为放在了Controller中,及可以调用原生activiti-rest也可以全使用get方法调用自己封装的接口地址如:

自己修改后的接口地址
![自己的地址](https://raw.githubusercontent.com/wangtaowtao/image/master/activiti/%E5%8E%9F%E6%8E%A5%E5%8F%A3%E5%9C%B0%E5%9D%80.png)

原生的接口地址如:
![原生接口地址](https://raw.githubusercontent.com/wangtaowtao/image/master/activiti/%E8%87%AA%E5%B7%B1%E5%B0%81%E8%A3%85%E6%8E%A5%E5%8F%A3.png)

自己使用httpClient全部吧activiti-rest的接口转成了自己的接口地址，当然可以完全使用原生接口是可以的。使用请参考Activiti的用户手册第15章(REST API)
环境搭建也可以参考我的CSND博客地址
https://blog.csdn.net/javaYouCome/article/details/93738691
