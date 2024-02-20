package com.vrpigroup.users.repositories;

import com.vrpigroup.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<UserEntity, Long> {
   /* UserEntity findByUserNameAAndEmail(String username, String email);

    UserEntity findByUserNameAndPassword(String username, String password);

    UserEntity findByUserName(String username);

    Iterable<UserEntity> findByRole(String role);*/

    Optional<UserEntity> findByEmail(String email);

    Object findByActive(boolean b);

    UserEntity findByName(String name);

    UserEntity findByPhoneNumber(String phoneNumber);
}