package com.train;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(ServerApplication.class, args);
        try {
            Environment env = application.getEnvironment();
            String serverPort = env.getProperty("server.port");
            String contextPath = Optional
                    .ofNullable(env.getProperty("server.servlet.context-path"))
                    .filter(StrUtil::isNotBlank)
                    .orElse("/");
            String hostAddress = "localhost";
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                StaticLog.warn("The host name could not be determined, using `localhost` as fallback");
            }
            String[] profiles = env.getActiveProfiles();
            log.info("""
                    
                    ----------------------------------------------------------
                    \t\
                    Application is running!\s
                    \t\
                    Active profiles: {}
                    \t\
                    Local: \t\thttp://localhost:{}{}
                    \t\
                    External: \thttp://{}:{}{}
                    \t\
                    Doc: \t\thttp://{}:{}{}doc.html
                    ----------------------------------------------------------
                    \t""", String.join(",", profiles), serverPort, contextPath, hostAddress, serverPort, contextPath, hostAddress, serverPort, contextPath.endsWith("/") ? contextPath : contextPath + "/");
        } catch (Exception e) {
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }
}