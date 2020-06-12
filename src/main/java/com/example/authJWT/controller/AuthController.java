package com.example.authJWT.controller;

import com.example.authJWT.model.AuthenticationRequest;
import com.example.authJWT.model.AuthenticationResponse;
import com.example.authJWT.service.UserDetailService;
import com.example.authJWT.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserDetailService userDetailService;
    private JWTUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailService userDetailService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUserName());
        return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
    }
}
