package com.example.authJWT.service;

import com.example.authJWT.entity.UserDetail;
import com.example.authJWT.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        UserDetail userDetail = userDetailRepository.findByUsername(userName);
        return new User(userDetail.getUsername(), userDetail.getPassword(), new ArrayList<>());
    }
}
