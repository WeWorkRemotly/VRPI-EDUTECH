package com.vrpigroup.users.repositories;

import com.vrpigroup.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    boolean findByUserNameAndEmail(String name, String email);

    UserEntity findByUserName(String username);

    UserEntity findByResetToken(String token);

    UserEntity findByEmail(String email);

}