package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public ResponseMessage sayHello() {
        return new ResponseMessage("Hello");
    }
}
