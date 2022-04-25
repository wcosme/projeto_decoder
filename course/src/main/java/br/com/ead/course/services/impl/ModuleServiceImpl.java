package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.models.Lesson;
import br.com.ead.course.models.Module;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.repositories.ModuleRepository;
import br.com.ead.course.services.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public void delete(Module module) {
		List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
		if(!lessons.isEmpty()) {
			lessonRepository.deleteAll(lessons);
		}
		moduleRepository.delete(module);
	}

	@Override
	public List<Module> findAll() {		
		return moduleRepository.findAll();
	}

	@Override
	public Optional<Module> findById(UUID moduleId) {		
		return moduleRepository.findById(moduleId);
	}

	@Override
	public Module save(Module module) {
		return moduleRepository.save(module);
	}

	@Override
	public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {		
		return moduleRepository.findModuleIntoCourse(courseId, moduleId);
	}

	@Override
	public List<Module> findAllByCourse(UUID courseId) {
		return moduleRepository.findAllModulesIntoCourse(courseId);
	}

	@Override
	public Page<Module> findAllByCourse(Specification<Module> filter, Pageable pageable) {
		return moduleRepository.findAll(filter, pageable);
	}
}
