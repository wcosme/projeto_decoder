package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.ead.course.models.Course;

public interface CourseService {
	
	void delete(Course course);
	Course save(Course course);
	Optional<Course> findById(UUID courseId);
	List<Course> findAll();

}
