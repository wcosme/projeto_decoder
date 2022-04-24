package br.com.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.models.Module;
import br.com.ead.course.services.ModuleService;

@RestController
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@GetMapping
	public ResponseEntity<List<Module>> getAllmodules(){
		return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAll());		
	}
	
	@GetMapping("/{moduleId}")
	public ResponseEntity<Object> getOnemodule(@PathVariable(value = "moduleId") UUID moduleId){
		
		Optional<Module> moduleOptional = moduleService.findById(moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(moduleOptional.get());
		}
	}

	@PostMapping
	public ResponseEntity<Module> savemodule(@RequestBody @Valid moduleDto moduleDto) {

		var module = new Module();

		BeanUtils.copyProperties(moduleDto, module);
		module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		module.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
	}

	@DeleteMapping("/{moduleId}")
	public ResponseEntity<Object> deletemodule(@PathVariable(value = "moduleId") UUID moduleId) {

		Optional<Module> moduleOptional = moduleService.findById(moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found.");
		} else {
			moduleService.delete(moduleOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("module deleted successfully.");
		}
	}

	@PutMapping("/{moduleId}")
	public ResponseEntity<Object> Updatemodule(@RequestBody @Valid moduleDto moduleDto, @PathVariable(value = "moduleId") UUID moduleId) {
		
		Optional<Module> moduleOptional = moduleService.findById(moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found.");
		} else {
			var module = moduleOptional.get();
			module.setName(moduleDto.getName());
			module.setDescription(moduleDto.getDescription());
			module.setImageUrl(moduleDto.getImageUrl());
			module.setmoduleStatus(moduleDto.getmoduleStatus());
			module.setmoduleLevel(moduleDto.getmoduleLevel());
			module.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));			
			
			return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
		}
	}

}
