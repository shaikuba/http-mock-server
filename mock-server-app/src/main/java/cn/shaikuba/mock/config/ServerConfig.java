package cn.shaikuba.mock.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Value("${mock.server.http.port}")
    private int httpPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHttpConnector());

//        tomcat.addConnectorCustomizers((connector)->{
//            ProtocolHandler protocolHandler = connector.getProtocolHandler();
//            if(protocolHandler instanceof Http11NioProtocol){
//                Http11NioProtocol http11NioProtocol = (Http11NioProtocol)protocolHandler;
//                http11NioProtocol.setKeepAliveTimeout(60000);//millisecond
//            }
//        });
        return tomcat;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(httpPort);
        //connector.setSecure(false);
        connector.setURIEncoding("UTF-8");
        //connector.setRedirectPort(8443);
        return connector;
    }
}