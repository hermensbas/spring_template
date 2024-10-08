package nl.spring.template.project.app.opera.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("public/account")
@RestController
public class AccountController {

    @GetMapping(value = "/logon", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> logon() {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UserDetails user = (UserDetails) principal;

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello " + user.getUsername());
    }

}
