package com.example.authJWT.service;

import com.example.authJWT.entity.UserDetail;
import com.example.authJWT.repository.UserDetailRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserDetailServiceTest {

    @InjectMocks
    private UserDetailService userDetailService;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void whenFindUserByUsername_shouldReturnUserDetail() {
        UserDetail userDetail = UserDetail.builder().username("test").password("test").build();
        when(userDetailRepository.findByUsername(userDetail.getUsername())).thenReturn(userDetail );

        UserDetails userDetails = userDetailService.loadUserByUsername(userDetail.getUsername());
        assert(userDetails.getUsername()).equals(userDetail.getUsername());
        assert(userDetails.getPassword()).equals(userDetail.getPassword());
    }
}
