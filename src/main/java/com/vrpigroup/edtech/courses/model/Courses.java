package com.vrpigroup.edtech.courses.model;

import com.vrpigroup.edtech.students.model.students;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Table(name = "COURSE_DETAILS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Courses {


    @Id
    @GeneratedValue
    private Long id;

    private String courseName;

    private String courseDescription;

    private Double coursePrice;

    private boolean isEnabled;

    @ManyToOne(optional = false)
    private students students;

}
