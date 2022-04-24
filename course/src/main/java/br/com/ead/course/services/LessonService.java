package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.ead.course.models.Lesson;

public interface LessonService {

	Lesson save(Lesson lesson);
	Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);
	void delete(Lesson lesson);
	List<Lesson> findAllByModule(UUID moduleId);

}
