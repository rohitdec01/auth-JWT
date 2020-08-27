package com.example.authJWT.repository;

import com.example.authJWT.AuthJwtApplication;
import com.example.authJWT.entity.UserDetail;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthJwtApplication.class)
public class UserDetailRepositoryTest {

    @Autowired
    //@Mock
    private UserDetailRepository userDetailRepository;

    @AfterEach
    public void tearDown() {
        userDetailRepository.deleteAllInBatch();
    }

    @Test
    public void findByUsername() {
        userDetailRepository.deleteAllInBatch();

        UserDetail userDetail = UserDetail.builder().username("test1").password("test").build();
        userDetailRepository.save(userDetail);

        assertThat(userDetailRepository.findByUsername(userDetail.getUsername()))
                .isNotNull();
        assertThat(userDetailRepository.findByUsername(userDetail.getUsername()).getUsername())
                .isEqualTo(userDetail.getUsername());
        /*assertThat(userDetailRepository.findAll())
                .containsExactly(userDetail);*/
    }

}
