package com.example.springsecuritybase.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-25
 */

@RestController
public class SessionController {

    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String sessionInvalid() {
        return "session失效";
    }
}
