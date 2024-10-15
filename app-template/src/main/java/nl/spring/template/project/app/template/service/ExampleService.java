package nl.spring.template.project.app.template.service;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public ExampleService() {
    }

    public String getGreetingMessage() {
        return "hi";
    }
}
