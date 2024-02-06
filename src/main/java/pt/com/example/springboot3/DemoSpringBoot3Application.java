package pt.com.example.springboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import pt.com.example.springboot3.config.ApiConfigProperties;

@SpringBootApplication
@EnableFeignClients
public class DemoSpringBoot3Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBoot3Application.class, args);
    }

}
