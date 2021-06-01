package cn.best.approveservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("cn.best.approveservice.dao")
public class ApproveserviceApplication extends SpringBootServletInitializer{
//    public static void main(String[] args) {
////        System.setProperty("jasypt.encryptor.password", "eXRnc1NtYXJ0UGFya2luZw==");
//        SpringApplication.run(ApproveserviceApplication.class, args);
//    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApproveserviceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApproveserviceApplication.class, args);
    }
}
