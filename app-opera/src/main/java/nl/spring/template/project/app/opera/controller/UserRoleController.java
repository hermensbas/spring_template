package nl.spring.template.project.app.opera.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_admin", "ROLE_user"})
@RequestMapping("/private/user")
@RestController
public class UserRoleController {

    @GetMapping(value = "/hi", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> hi() {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UserDetails user = (UserDetails) principal;

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hello " + user.getUsername());
    }

}
