package pt.com.example.springboot3.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.com.example.springboot3.config.feign.FeignConfiguration;
import pt.com.example.springboot3.dtos.MunicipioRecord;

@FeignClient(value = "geoApiClient", url = "https://json.geoapi.pt/", configuration = FeignConfiguration.class)
public interface GeoApiClient {

    @GetMapping(value = "/municipio/{municipio}")
    MunicipioRecord getMunicipio(@PathVariable String municipio);

}
