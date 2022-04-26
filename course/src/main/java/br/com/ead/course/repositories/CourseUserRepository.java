package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ead.course.models.CourseUserModel;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {

}
