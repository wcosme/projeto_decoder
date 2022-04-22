package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ead.course.models.Course;

public interface CourseRepository extends JpaRepository<Course, UUID> {

}
