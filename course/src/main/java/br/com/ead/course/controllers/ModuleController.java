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
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.dtos.ModuleDto;
import br.com.ead.course.models.Course;
import br.com.ead.course.models.Module;
import br.com.ead.course.services.CourseService;
import br.com.ead.course.services.ModuleService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courses/{courseId}/modules")
	public ResponseEntity<List<Module>> getAllModules(@PathVariable(value = "courseId") UUID courseId){
		return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(courseId));		
	}
	
	@GetMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId){
		
		Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(moduleOptional.get());
		}
	}

	@PostMapping("/courses/{courseId}/modules")
	public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid ModuleDto moduleDto) {
		
		Optional<Course> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			var module = new Module();

			BeanUtils.copyProperties(moduleDto, module);
			module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
			module.setCourse(courseOptional.get());

			return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
		}		
	}

	@DeleteMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {

		Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
		} else {
			moduleService.delete(moduleOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.");
		}
	}

	@PutMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<Object> updateModule(@RequestBody @Valid ModuleDto moduleDto, 
												@PathVariable(value = "courseId") UUID courseId, 
												@PathVariable(value = "moduleId") UUID moduleId) {
		
		Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("module not found.");
		} else {
			var module = moduleOptional.get();
			module.setTitle(moduleDto.getTitle());
			module.setDescription(moduleDto.getDescription());
			
			return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
		}
	}
}
