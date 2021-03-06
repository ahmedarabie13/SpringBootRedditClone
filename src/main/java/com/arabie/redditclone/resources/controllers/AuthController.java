package com.arabie.redditclone.resources.controllers;

import com.arabie.redditclone.domain.dtos.AuthenticationResponseDto;
import com.arabie.redditclone.domain.dtos.UserLoginDto;
import com.arabie.redditclone.domain.dtos.UserRegisterDto;
import com.arabie.redditclone.domain.services.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        authService.register(userRegisterDto);
        return ResponseEntity.ok("Successful User Registration");
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Account Activated Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        var response = authService.login(userLoginDto);
        return ResponseEntity.ok(response);
    }
}
