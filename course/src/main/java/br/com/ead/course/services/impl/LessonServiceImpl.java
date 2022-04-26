package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.ead.course.models.LessonModel;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public LessonModel save(LessonModel lesson) {
		return lessonRepository.save(lesson);
	}

	@Override
	public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
		return lessonRepository.findLessonIntoModule(moduleId, lessonId);
	}

	@Override
	public void delete(LessonModel lesson) {
		lessonRepository.delete(lesson);		
	}

	@Override
	public List<LessonModel> findAllByModule(UUID moduleId) {
		return lessonRepository.findAllLessonsIntoModule(moduleId);
	}

	@Override
	public Page<LessonModel> findAllByModule(Specification<LessonModel> filter, Pageable pageable) {
		return lessonRepository.findAll(filter, pageable);
	}

}
