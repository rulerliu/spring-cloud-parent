打jar包方式
mvn install:install-file -Dfile=D:\Java\meite-shop-workspace\xxl-sso-master\xxl-sso-core\target\xxl-sso-core-1.1.1-SNAPSHOT.jar -DgroupId=com.xuxueli -DartifactId=xxl-sso-core -Dversion=1.1.1-SNAPSHOT -Dpackaging=jar

https://github.com/xuxueli/xxl-sso  xxl-sso源码下载

http://www.xuxueli.com/xxl-sso/#/  xxl-sso官方文档

配置文件位置：application.properties

// redis 地址： 如 "{ip}"、"{ip}:{port}"、"{redis/rediss}://xxl-sso:{password}@{ip}:{port:6379}/{db}"；多地址逗号分隔
xxl.sso.redis.address=redis://127.0.0.1:6379

// 登录态有效期窗口，默认24H，当登录态有效期窗口过半时，自动顺延一个周期
xxl.sso.redis.expire.minite=1440


### 在host文件中添加以下内容0
127.0.0.1 xxlssoserver.com
127.0.0.1 xxlssoclient1.com
127.0.0.1 xxlssoclient2.com


分别运行 "xxl-sso-server" 与 "xxl-sso-web-sample-springboot"
1、SSO认证中心地址：
http://xxlssoserver.com:8900/xxl-sso-server

2、Client01应用地址：
http://xxlssoclient1.com:8902/xxl-sso-web-sample-springboot/

3、Client02应用地址：
http://xxlssoclient2.com:8902/xxl-sso-web-sample-springboot/

xxl-sso单点登录原理
1.首先访问：http://xxlssoclient1.com:8902/xxl-sso-web-sample-springboot/
拦截器XxlSsoWebFilter从ssoclient的cookie获取xxl_sso_sessionid，根据xxl_sso_sessionid获取用户信息为空
重定向到：http://xxlssoserver.com:8900/xxl-sso-server/login?redirect_url=http://xxlssoclient1.com:8902/xxl-sso-web-sample-springboot/

2.到了xxlssoserver的/login：loginCheck()从cookie中取xxl_sso_sessionid，在通过xxl_sso_sessionid去redis中取xxlUser
如果取不到xxl_sso_sessionid，再从parameter中获取，取不到，跳转到/login
如果取到了，判断版本号一致则重新存放到redis中 key：xxl_sso_sessionid#sessionId  value：xxlUser，然后放入到cookie中 key："xxl_sso_sessionid"  value：xxl_sso_sessionid

3.点击登录按钮，跳转到/xxl-sso-server/doLogin
userService.findList的mockUserList获取xxlUser(需要校验)
makeSessionid：userId_version
放入user到redis中:   key:xxl_sso_sessionid#userId  value:xxlUser
重定向:http://xxlssoclient1.com:8902/xxl-sso-web-sample-springboot/?xxl_sso_sessionid=

4.访问：http://xxlssoclient2.com:8902/xxl-sso-web-sample-springboot/
从ssoclient的cookie中获取xxl_sso_sessionid：null
重定向到：http://xxlssoserver.com:8900/xxl-sso-server/login?redirect_url=http://xxlssoclient2.com:8902/xxl-sso-web-sample-springboot/

5.到ssoserver的/login之后，获取ssoserver的cookie里面的xxl_sso_sessionid
根据xxl_sso_sessionid到redis获取xxlUser
重定向:http://xxlssoclient2.com:8902/xxl-sso-web-sample-springboot/?xxl_sso_sessionid=
