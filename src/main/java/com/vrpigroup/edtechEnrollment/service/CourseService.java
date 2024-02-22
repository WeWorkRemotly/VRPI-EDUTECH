package com.vrpigroup.edtechEnrollment.service;
import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import com.vrpigroup.edtechEnrollment.repo.CourseRepo;
import com.vrpigroup.payments.PaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

/**
 * CourseService is a class that provides the course service.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a course service class
 * @Email : amanrashm@gmail.com
 */
@Slf4j
@Service
public class CourseService {

    private final PaymentProcessor paymentProcessor;

    private final CourseRepo courseRepo;

    private final Set<String> enrolledUsers = new HashSet<>();
    /**
     * Constructor for CourseService
     * @param paymentProcessor
     * @param courseRepo
     */


    public CourseService(PaymentProcessor paymentProcessor, CourseRepo courseRepo) {
        this.paymentProcessor = paymentProcessor;
        this.courseRepo = courseRepo;
    }

    public CourseEntity getCourseById(Long courseId) {
        return courseRepo.findByCourseId(Math.toIntExact(courseId));
    }

    /*public void enrollUserInCourse(String userName, Long courseId) {
        if (courseRepo.findByCourseId(Math.toIntExact(courseId)) != null) {
            if (courseRepo.findByCourseId(Math.toIntExact(courseId)).getCoursePrice() == 0) {
                enrolledUsers.add(userName + courseId);
            } else {
                if (paymentProcessor.processPayment(userName, courseId)) {
                    enrolledUsers.add(userName + courseId);
                }
            }
        }
    }

    public Boolean isUserEnrolledInCourse(String userName, Long courseId) {
        return enrolledUsers.contains(userName + courseId);
    }

    public String generateEnrollmentKey(String userName, Long courseId) {
        return userName + courseId;
    }

    public Boolean isEnrollmentKeyValid(String userName, Long courseId, String enrollmentKey) {
        return enrollmentKey.equals(userName + courseId);
    }

    public void enrollUserInCourseWithEnrollmentKey(String userName, Long courseId, String enrollmentKey) {
        if (isEnrollmentKeyValid(userName, courseId, enrollmentKey)) {
            enrolledUsers.add(userName + courseId);
        }
    }

    public Boolean isUserEnrolledInCourseWithEnrollmentKey(String userName, Long courseId, String enrollmentKey) {
        return enrolledUsers.contains(userName + courseId);
    }*/

    public void enrollUserInCourseWithPayment(String userName, Long courseId) {
        if (courseRepo.findByCourseId(Math.toIntExact(courseId)) != null) {
            if (courseRepo.findByCourseId(Math.toIntExact(courseId)).getCoursePrice() != 0) {
                if (paymentProcessor.processPayment(userName, courseId)) {
                    enrolledUsers.add(userName + courseId);
                }
            }
        }
    }

    public Boolean isUserEnrolledInCourseWithPayment(String userName, Long courseId) {
        return enrolledUsers.contains(userName + courseId);
    }

    public String getTransactionId(String userName, Long courseId) {
        return userName + courseId;
    }

    public Boolean isTransactionValid(String userName, Long courseId, String transactionId) {
        return transactionId.equals(userName + courseId);
    }

    public boolean enrollUserInCourseWith(String userName, Long courseId, String transactionId) {
        if (isTransactionValid(userName, courseId, transactionId)) {
            enrolledUsers.add(userName + courseId);
        }
        return enrolledUsers.contains(userName + courseId);
    }

    public Boolean isUserEnrolledInCourseWith(String userName, Long courseId, String transactionId) {
        return enrolledUsers.contains(userName + courseId);
    }


    public void deleteCourse(Long courseId) {
        courseRepo.deleteById(courseId);
    }
}