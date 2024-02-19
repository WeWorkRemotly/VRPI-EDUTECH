package com.vrpigroup.edtech.courses.controllers;

import com.vrpigroup.edtech.courses.model.Courses;
import com.vrpigroup.edtech.courses.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value= "/Enrolling-course")
public class courseEnroll {

    private final courseService courseService;

    public courseEnroll(com.vrpigroup.edtech.courses.service.courseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping("/enrolling")
    public String enrollCourse (@RequestBody Courses course){
        courseService.enrollCourse(course);
       return "Successfully enrolled for "+ course;
   }

   @PostMapping("/all-enrolled-courses")
       public List<Courses> enrolledCourse(){
       var listOfCourse = new ArrayList<Courses>();
       courseService.getEnrolledCourse(listOfCourse);
           return listOfCourse;
       }

}
