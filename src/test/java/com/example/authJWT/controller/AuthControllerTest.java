package com.example.authJWT.controller;

import com.example.authJWT.model.AuthenticationRequest;
import com.example.authJWT.model.AuthenticationResponse;
import com.example.authJWT.service.UserDetailService;
import com.example.authJWT.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UserDetailService userDetailService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        when(authenticationManager.authenticate(any())).thenReturn(any());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void shouldReturnTest() throws Exception {
        // mockMvc.perform(get("/test").param("searchCriteria", searchCriteria))
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("test Successful"));
    }

    @Test
    public void createAuthenticationToken_returnToken() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .userName("test")
                .password("test")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .jwt("token").build();

        UserDetails userDetails = new User("test", "test", new ArrayList<>());
        when(userDetailService.loadUserByUsername(authenticationRequest.getUserName())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("token");

        mockMvc.perform(post("/authenticate")
                // .principal(getToken())
                .content(new ObjectMapper().writeValueAsString(authenticationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(authenticationResponse)))
                .andExpect(status().isOk());

        verify(userDetailService).loadUserByUsername(authenticationRequest.getUserName());
    }
}
