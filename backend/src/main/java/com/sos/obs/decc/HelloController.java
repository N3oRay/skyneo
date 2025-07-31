package com.sos.obs.decc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    @GetMapping("/api/")
    public String helloapi() {
        return "Notice API:";
    }


    @GetMapping("/api/hello")
    public String hello() {
        return "Dovahkiin says: Hello, World!";
    }

}
