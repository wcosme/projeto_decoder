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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.course.dtos.CourseDto;
import br.com.ead.course.filters.FilterTemplate;
import br.com.ead.course.models.CourseModel;
import br.com.ead.course.services.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping
	public ResponseEntity<Page<CourseModel>> getAllCourses(FilterTemplate.CourseFilter filter,
			@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Direction.ASC) Pageable pageable) {
		
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(filter, pageable));
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {

		Optional<CourseModel> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
		}
	}

	@PostMapping
	public ResponseEntity<CourseModel> saveCourse(@RequestBody @Valid CourseDto courseDto) {

		var course = new CourseModel();

		BeanUtils.copyProperties(courseDto, course);
		course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

		Optional<CourseModel> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			courseService.delete(courseOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.");
		}
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<Object> UpdateCourse(@RequestBody @Valid CourseDto courseDto,
			@PathVariable(value = "courseId") UUID courseId) {

		Optional<CourseModel> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			var course = courseOptional.get();
			course.setName(courseDto.getName());
			course.setDescription(courseDto.getDescription());
			course.setImageUrl(courseDto.getImageUrl());
			course.setCourseStatus(courseDto.getCourseStatus());
			course.setCourseLevel(courseDto.getCourseLevel());
			course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

			return ResponseEntity.status(HttpStatus.OK).body(courseService.save(course));
		}
	}
}
