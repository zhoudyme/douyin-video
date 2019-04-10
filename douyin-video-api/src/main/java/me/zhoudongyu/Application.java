package me.zhoudongyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Steve
 * @date 2019/04/03
 */
@SpringBootApplication
@MapperScan(basePackages = "me.zhoudongyu.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
