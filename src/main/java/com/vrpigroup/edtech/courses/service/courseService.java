package com.vrpigroup.edtech.courses.service;
import com.vrpigroup.edtech.courses.model.Courses;
import com.vrpigroup.edtech.courses.repositories.courseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class courseService {

    @Autowired
    private courseRepo courseRepo;

    public void enrollCourse(Courses course){
        courseRepo.save(course);
    }

    public void getEnrolledCourse(ArrayList<Courses> listOfCourse) {
        courseRepo.getAll(listOfCourse);
    }
}
