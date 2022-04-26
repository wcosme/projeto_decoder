package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.ModuleModel;

public interface ModuleService {
	
	void delete(ModuleModel module);
	List<ModuleModel> findAll();
	Optional<ModuleModel> findById(UUID moduleId);
	ModuleModel save(ModuleModel module);
	Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);
	List<ModuleModel> findAllByCourse(UUID courseId);
	Page<ModuleModel> findAllByCourse(Specification<ModuleModel> filter, Pageable pageable);

}
