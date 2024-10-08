package nl.spring.template.project.app.opera.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public")
@RestController
public class PublicRoleController {

    @GetMapping(value = "/hi", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> hi() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello");
    }

}
