package com.vrpigroup.annotations.deletedBy;

import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import com.vrpigroup.users.model.UserEntity;
import jakarta.persistence.PreRemove;
import org.springframework.stereotype.Component;

@Component
public class DeletedBy {

    private UserEntity userEntity;

    @PreRemove
    public void preRemove(CourseEntity entity) {
        entity.setCourseDeletedBy(userEntity.getName());
    }
}
