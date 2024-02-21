package com.vrpigroup.edtechEnrollment.repo;

import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CourseRepo extends JpaRepository<CourseEntity, Long>{

    CourseEntity findByCourseId(int courseId);
}
