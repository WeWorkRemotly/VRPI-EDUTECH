package com.vrpigroup.edtechEnrollment.model;

import com.vrpigroup.users.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_Id")
    private Long courseId;

    @Column(name = "course_name")
    @Size(min = 3, max = 50, message = "Course name must be between 3 and 50 characters")
    private String courseName;

    @Column(name = "course_description")
    @Size(min = 3, max = 500, message = "Course description must be between 3 and 500 characters")
    private String courseDescription;

    @Column(name = "course_price")
    @Size(min = 3, max = 50, message = "Course price must be between 3 and 6 characters")
    private Double coursePrice;

    @Column(name = "course_duration")
    @Size(min = 3, max = 50, message = "Course duration must be between 3 and 50 characters")
    private Integer courseDuration;

    @Column(name = "course_rating")
    @Size(min = 3, max = 50, message = "Course rating must be between 3 and 50 characters")
    private Double courseRating;

    @Column(name = "course_category")
    @Size(min = 3, max = 50, message = "Course category must be between 3 and 50 characters")
    private String courseCategory;

    @Column(name = "course_image")
    @Size(min = 3, max = 50, message = "Course image must be between 3 and 50 characters")
    private String courseImage;

    @Column(name = "course_video")
    @Size(min = 3, max = 50, message = "Course video must be between 3 and 50 characters")
    private String courseVideo;

    @Column(name = "course_status")
    @Size(min = 3, max = 50, message = "Course status must be between 3 and 50 characters")
    private String courseStatus;

    @Column(name = "course_created_by")
    @Size(min = 3, max = 50, message = "Course created by must be between 3 and 50 characters")
    private String courseCreatedBy;

    @Column(name = "course_created_date")
    @CreatedDate
    private String courseCreatedDate;

    @Column(name = "course_updated_by")
    @LastModifiedBy
    private String courseUpdatedBy;

    @Column(name = "course_updated_date")
    @LastModifiedDate
    private String courseUpdatedDate;

    @Column(name = "course_deleted_by")
    private String courseDeletedBy;

    @Column(name = "course_deleted_date")
    private String courseDeletedDate;

    @ManyToOne(optional = false)
    private UserEntity userEntities;

}
