package br.com.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.com.ead.course.dtos.LessonDto;
import br.com.ead.course.filters.FilterTemplate;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import br.com.ead.course.services.LessonService;
import br.com.ead.course.services.ModuleService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private ModuleService moduleService;

	
	@PostMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
			@RequestBody @Valid LessonDto lessonDto) {

		Optional<ModuleModel> moduleOptional = moduleService.findById(moduleId);

		if (!moduleOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
		} else {
			var lesson = new LessonModel();

			BeanUtils.copyProperties(lessonDto, lesson);
			lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));			
			lesson.setModule(moduleOptional.get());

			return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
		}
	}

	@DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId) {

		Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if (!lessonOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
		} else {
			lessonService.delete(lessonOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.");
		}
	}

	@PutMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> updateLesson(@RequestBody @Valid LessonDto lessonDto,
												@PathVariable(value = "moduleId") UUID moduleId, 
												@PathVariable(value = "lessonId") UUID lessonId) {

		Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if (!lessonOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
		} else {
			var lesson = lessonOptional.get();
			lesson.setTitle(lessonDto.getTitle());
			lesson.setDescription(lessonDto.getDescription());
			lesson.setVideoUrl(lessonDto.getVideoUrl());

			return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lesson));
		}
	}
	
	@GetMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId, FilterTemplate.LessonFilter filter,
            										  @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Direction.ASC) Pageable pageable) {
		
		return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(FilterTemplate.lessonModuleId(moduleId).and(filter), pageable));
	}

	@GetMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
											   @PathVariable(value = "lessonId") UUID lessonId) {

		Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

		if (!lessonOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(lessonOptional.get());
		}
	}
}
