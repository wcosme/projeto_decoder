package br.com.ead.course.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ead.course.models.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {
	
	@EntityGraph(attributePaths = {"course"})
	Module findByTitle(String title);
	
	@Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
	List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

}
