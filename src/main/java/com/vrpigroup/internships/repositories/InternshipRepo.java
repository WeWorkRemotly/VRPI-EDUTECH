package com.vrpigroup.internships.repositories;

import com.vrpigroup.internships.model.InternshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipRepo extends JpaRepository<InternshipModel, Long> {
    boolean findByUserNameAndEmail(String name, String email);

    InternshipModel findByEmail(String email);

    InternshipModel findByResetToken(String token);
}
