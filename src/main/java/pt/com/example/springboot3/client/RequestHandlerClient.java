package pt.com.example.springboot3.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import pt.com.example.springboot3.config.feign.FeignConfiguration;

@FeignClient(value = "geoApiClient", url = "http://localhost:8081", configuration = FeignConfiguration.class)
public interface RequestHandlerClient {

    @GetMapping("/ok")
    public String ok();

}
