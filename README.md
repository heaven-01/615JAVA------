## 元宠物培养系统操作指南
**前提**：请确认本项目是否完整地解压并保存在IDEA新建的项目列表当中，这与接下来是否能完整地运行整个项目至关重要。
### 操作步骤：
1. **添加数据库：点开电脑中的Navicat**：(若电脑没安装MySQL和Navicat,请依照[参考链接1]( https://www.bilibili.com/video/BV1CR4y1m7Hy/?share_source=copy_web&vd_source=e09a99dfd81aafd284cba135e58ae94e)与[参考链接2](https://www.bilibili.com/video/BV1Kr4y1i7ru/?p=3&share_source=copy_web&vd_source=e09a99dfd81aafd284cba135e58ae94e)的步骤执行,注册),进入到Navicat的界面，鼠标右击左边列表下的文件SQL组，新建数据库取名{任意名字}，然后右击新建数据库，点击**运行SQL文件**,点击" **...** " 号，从这边添加我们呢附带的SQL文件
2. **添加mysql-connecter-j.jar文件为库**：回到IDEA新建项目，点击右击左边列表【lib】-【mysql-connecter-j.jar8.3.0】,点击添加为库(英文版为 Add as the library)，点击重构，再点击确定。
3. **JAVA连接数据库**：点击**scr**目录下的**database.properties**文件格式如下：



**jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/{你的数据库名}?useSSL=false&serverTimezone=UTC
jdbc.username=root
jdbc.password={你的密码}
jdbc.initialSize=5
jdbc.maxActive=10**

对相应内容进行替换
4.执行【scr】-【com】-【petstore】的Main.java函数。
