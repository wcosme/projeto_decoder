package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.Module;

public interface ModuleService {
	
	void delete(Module module);
	List<Module> findAll();
	Optional<Module> findById(UUID moduleId);
	Module save(Module module);
	Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);
	List<Module> findAllByCourse(UUID courseId);
	Page<Module> findAllByCourse(Specification<Module> filter, Pageable pageable);

}
