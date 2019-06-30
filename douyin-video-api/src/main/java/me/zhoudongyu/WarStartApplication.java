package me.zhoudongyu;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 继承SpringBootServletInitializer,相当于使用web.xml的形式去启动部署
 *
 * @author Steve
 * @date 2019/06/30
 */
public class WarStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //使用web.xml运行应用程序，指定Application，最后启动Springboot
        return builder.sources(Application.class);
    }
}
