package cn.best.approveservice.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileNotFoundException;

@Configuration
public class WebImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         */
        try {
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:" + ResourceUtils.getURL("classpath:").getPath() + "static/imges");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
