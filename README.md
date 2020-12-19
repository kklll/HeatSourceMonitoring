# HeatSourceMonitoring
HeatSourceMonitoring(热源监控系统)前后端分离项目，Vue前端，springboot做后端



### 数据怎么生成的

随机生成的数据。



### 数据如何传递

使用websocket传送实时数据。



### 数据表结构在哪

在db文件夹里面，密码是md5散列十次的结果，所以默认密码应该是123123



### 怎么运行起来

maven项目，首先你需要

```
git clone git@github.com:kklll/HeatSourceMonitoring.git
```

然后，用你的IDEA打开

然后，等他把依赖包下载好

什么？没有IDEA，那eclipse也可以，只不过你需要使用下面的命令

```
mvn install 
```

然后呢？

1. 你需要把db文件夹下面的数据库文件导入到你的数据库里面
2. 修改application.properties中所有的你的数据库的配置

然后点击

![](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201219204318099.png)

就好了，啪的一下程序就跑起来了。

那前端怎么跑起来呢？请移步到UI文件下，查看UI/heat/README.md中的描述



欢迎提ISSUES