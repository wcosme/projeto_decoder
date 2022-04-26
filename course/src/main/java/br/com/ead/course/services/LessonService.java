package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.LessonModel;

public interface LessonService {

	LessonModel save(LessonModel lesson);
	Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);
	void delete(LessonModel lesson);
	List<LessonModel> findAllByModule(UUID moduleId);
	Page<LessonModel> findAllByModule(Specification<LessonModel> filter, Pageable pageable);

}
