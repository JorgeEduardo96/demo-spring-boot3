package pt.com.example.springboot3.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.com.example.springboot3.dtos.MunicipioRecord;
import pt.com.example.springboot3.service.TestService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/municipio")
    public MunicipioRecord getMunicipio(@RequestParam String municipio) {
        return testService.getMunicipio(municipio);
    }

    @GetMapping("/ok")
    public String ok() {
        return testService.ok();
    }

}
