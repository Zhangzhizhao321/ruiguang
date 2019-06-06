package com.dream.chat;

import com.dream.chat.component.SpringContextHolder;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 拍卖服务启动类
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-10-18
 */
@EnableSwagger2Doc
@SpringBootApplication
public class ChatApplication /* implements CommandLineRunner*/ {
	/**注册Spring Util
	 * 这里为了和上一个冲突，所以方面名为：springUtil2
	 * 实际中使用springUtil
	 */

	@Bean
	public SpringContextHolder springUtil2(){
		return new SpringContextHolder();
	}
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	/**
	 * http重定向到https
	 * @return
	 */
	/*@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}

	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		//Connector监听的http的端口号
		connector.setPort(8082);
		connector.setSecure(false);
		//监听到http的端口号后转向到的https的端口号
		connector.setRedirectPort(8443);
		return connector;
	}*/
	/*@Override
	public void run(String... args) throws Exception {
		new SimpleChatServer(8082).run();
	}*/
}
