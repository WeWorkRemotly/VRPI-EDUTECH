package com.vrpigroup.edtechEnrollment.controller;

import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import com.vrpigroup.edtechEnrollment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * edtechController is a class that holds the edtech controller.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a edtech controller class
 * @Email :amanrashm@gmail.com
 */

@RestController
@RequestMapping(value = "/vrpi-edtech")
public class edtechController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    /*@PostMapping(value = "/course/{courseId}/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enrollUserInCourse(@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        courseService.enrollUserInCourse(userName, courseId);
        return new ResponseEntity<>("User enrolled in the course.", HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/enrolled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserEnrolledInCourse(@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isUserEnrolledInCourse(userName, courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/enrollmentKey", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEnrollmentKey(@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.generateEnrollmentKey(userName, courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/enrollmentKey/{enrollmentKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isEnrollmentKeyValid(@PathVariable Long courseId, @PathVariable String enrollmentKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isEnrollmentKeyValid(userName, courseId, enrollmentKey), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/enrollmentKey/{enrollmentKey}/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enrollUserInCourseWithEnrollmentKey
            (@PathVariable Long courseId, @PathVariable String enrollmentKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        courseService.enrollUserInCourseWithEnrollmentKey(userName, courseId, enrollmentKey);
        return new ResponseEntity<>("User enrolled in the course.", HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/enrollmentKey/{enrollmentKey}/enrolled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserEnrolledInCourseWithEnrollmentKey
            (@PathVariable Long courseId, @PathVariable String enrollmentKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isUserEnrolledInCourseWithEnrollmentKey(userName, courseId, enrollmentKey), HttpStatus.OK);
    }*/

    //Receive payments for enrollment through Razorpay
    @PostMapping(value = "/course/{courseId}/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enrollUserInCourseWithPayment
    (@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        courseService.enrollUserInCourseWithPayment(userName, courseId);
        return new ResponseEntity<>("User enrolled in the course.", HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserEnrolledInCourseWithPayment
            (@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isUserEnrolledInCourseWithPayment(userName, courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/payment/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTransactionId
            (@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.getTransactionId(userName, courseId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/payment/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isTransactionValid
            (@PathVariable Long courseId, @PathVariable String transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isTransactionValid(userName, courseId, transactionId), HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/payment/transaction/{transactionId}/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enrollUserInCourseWith
            (@PathVariable Long courseId, @PathVariable String transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        courseService.enrollUserInCourseWith(userName, courseId, transactionId);
        return new ResponseEntity<>("User enrolled in the course.", HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}/payment/transaction/{transactionId}/enrolled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserEnrolledInCourseWith
            (@PathVariable Long courseId, @PathVariable String transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(courseService.isUserEnrolledInCourseWith(userName, courseId, transactionId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/course/{courseId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("Course deleted.", HttpStatus.OK);
    }
}