package nl.spring.template.project.app.opera.controller;

import io.micrometer.observation.annotation.Observed;
import nl.spring.template.project.app.opera.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("public/example")
@RestController
public class ExampleController {

    private final ExampleService exampleService;

    @Autowired
    public ExampleController(
        final ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping(value = "/greeting", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getGreeting() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(exampleService.getGreetingMessage());
    }

}
