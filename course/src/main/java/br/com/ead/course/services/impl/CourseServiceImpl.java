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

import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
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
	public void delete(CourseModel course) {
		
		List<ModuleModel> modules = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
		
		if(!modules.isEmpty()) {
			for (ModuleModel module : modules) {
				List<LessonModel> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
				if(!lessons.isEmpty()) {
					lessonRepository.deleteAll(lessons);
				}
			}			
			moduleRepository.deleteAll(modules);
		}
		courseRepository.delete(course);
	}

	@Override
	public CourseModel save(CourseModel course) {
		return courseRepository.save(course);
		
	}

	@Override
	public Optional<CourseModel> findById(UUID courseId) {		
		return courseRepository.findById(courseId);
	}

	@Override
	public Page<CourseModel> findAll(Specification<CourseModel> filter, Pageable pageable) {		
		return courseRepository.findAll(filter, pageable);
	}
}
