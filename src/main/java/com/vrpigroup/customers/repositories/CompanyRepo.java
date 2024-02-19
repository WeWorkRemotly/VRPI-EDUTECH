package com.vrpigroup.customers.repositories;

import com.vrpigroup.customers.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyModel,Long> {
    CompanyModel findByEmail(String email);

    boolean findByUserNameAndEmail(String fullName, String email);

    CompanyModel findByResetToken(String token);
}
