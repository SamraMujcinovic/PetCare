package ba.unsa.etf.nwt.adopt_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
    //Value and GetMapping for config server
    @Value("${my.variable: default value}")
    private String variable;

    @GetMapping("/configserver/test")
    public String getVariable() {
        return "RETURNED VARIABLE: " + variable;
    }
}
