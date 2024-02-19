package com.vrpigroup.edtech.students.model;

import com.vrpigroup.edtech.courses.model.Courses;
import com.vrpigroup.users.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "Students")
@NoArgsConstructor
@AllArgsConstructor
public class students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 3, message = "Roll number can't exceed 3 characters")
    private String rollNumber;

    @Column(nullable = false)
    @Size(max = 10, message = "Batch can't exceed 10 characters")
    @Pattern(regexp = "[A-Z]{1}[0-9]{1}", message = "Kindly follow batch patterns like A1")
    private String batch;

    @Column(nullable = false)
    @Size(max = 50, message = "Major can't exceed 50 characters")
    private String major;

    @Column(nullable = false)
    private int totalCredits;

    @Column(nullable = false)
    @Size(max = 50, message = "Academic advisor's name can't exceed 50 characters")
    private String academicAdvisor;

    @OneToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private Set<Courses> courses;
}
