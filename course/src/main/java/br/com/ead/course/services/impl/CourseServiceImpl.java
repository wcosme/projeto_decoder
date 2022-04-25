package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.models.Course;
import br.com.ead.course.models.Lesson;
import br.com.ead.course.models.Module;
import br.com.ead.course.repositories.CourseRepository;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.repositories.ModuleRepository;
import br.com.ead.course.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LessonRepository lessonRepository;

	@Transactional
	@Override
	public void delete(Course course) {
		
		List<Module> modules = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
		
		if(!modules.isEmpty()) {
			for (Module module : modules) {
				List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
				if(!lessons.isEmpty()) {
					lessonRepository.deleteAll(lessons);
				}
			}			
			moduleRepository.deleteAll(modules);
		}
		courseRepository.delete(course);
	}

	@Override
	public Course save(Course course) {
		return courseRepository.save(course);
		
	}

	@Override
	public Optional<Course> findById(UUID courseId) {		
		return courseRepository.findById(courseId);
	}

	@Override
	public Page<Course> findAll(Specification<Course> filter, Pageable pageable) {		
		return courseRepository.findAll(filter, pageable);
	}
}
