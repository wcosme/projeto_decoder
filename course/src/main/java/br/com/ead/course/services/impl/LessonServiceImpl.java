package br.com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.course.models.Lesson;
import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public Lesson save(Lesson lesson) {
		return lessonRepository.save(lesson);
	}

	@Override
	public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
		return lessonRepository.findLessonIntoModule(moduleId, lessonId);
	}

	@Override
	public void delete(Lesson lesson) {
		lessonRepository.delete(lesson);		
	}

	@Override
	public List<Lesson> findAllByModule(UUID moduleId) {
		return lessonRepository.findAllLessonsIntoModule(moduleId);
	}

}
