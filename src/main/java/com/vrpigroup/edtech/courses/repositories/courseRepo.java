package com.vrpigroup.edtech.courses.repositories;

import com.vrpigroup.edtech.courses.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@EnableJpaRepositories
public interface courseRepo extends JpaRepository<Courses,Long> {


    void getAll(ArrayList<Courses> listOfCourse);
}
