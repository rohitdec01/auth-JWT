package com.example.authJWT.repository;

import com.example.authJWT.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    UserDetail findByUsername(String userName);
}
