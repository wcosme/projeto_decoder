package br.com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.ead.course.models.Module;

public interface ModuleService {
	
	void delete(Module module);
	List<Module> findAll();
	Optional<Module> findById(UUID moduleId);
	Module save(Module module);

}
