package br.com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ead.course.models.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
	
	@Query(value = "select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true)
	List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

	@Query(value = "select * from tb_lessons where module_module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
	Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);

}
