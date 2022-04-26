package br.com.ead.course.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.CourseModel;

public interface CourseService {
	
	void delete(CourseModel course);
	CourseModel save(CourseModel course);
	Optional<CourseModel> findById(UUID courseId);
	Page<CourseModel> findAll(Specification<CourseModel> filter, Pageable pageable);

}
