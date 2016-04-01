# pengzz.webserver
第二次培训习题

项目说明：
1，这是一个支持静态文本和断点续传下载功能的简易服务器
2，服务器启动地址：HttpServer\src\main\java\com\succez\server\launcher\Launcher.java
3，服务器通过eclipse工程导入，即可运行（eclipse开发的配置环境是jdk1.8，m2e，junit，slf4j）
4，项目模块划分：启动器，监听器，解析器，分析处理模块（下载器，目录展示，文件预览）

开发技术：
1，socket编程（serversocket，socket）
2，http协议（200 ok， 206 partial content），range content-range content-length content-type content-dispotion 
3，线程池（多线程）

可优化方向：
1，多线程可使用lamda表达式精简表达。
2，工程架构，类抽象可以更具有通用服务器的抽象特性。
3，当IO效率无法提升时，可以精简内存使用（控制堆栈变量）和控制cpu时间（小算法对集合的增删改查）小范围内再次提升程序效率。
4，可以考虑使用socket NIO代替socket+线程池处理。
5，可以自定义异常类，监视程序。
6，通过请求文件类型，分析返回对应http的content-type，可将这部分抽象为专用类或模块。
7，根据request即可分析得到应答头信息，request类可以进一步抽象，避免下层类处理response。
