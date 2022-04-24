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

import br.com.ead.course.dtos.CourseDto;
import br.com.ead.course.models.Course;
import br.com.ead.course.services.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<List<Course>> getAllCourses(){
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());		
	}
	
	@GetMapping("/{courseId}")
	public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId){
		
		Optional<Course> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
		}
	}

	@PostMapping
	public ResponseEntity<Course> saveCourse(@RequestBody @Valid CourseDto courseDto) {

		var course = new Course();

		BeanUtils.copyProperties(courseDto, course);
		course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

		Optional<Course> courseOptional = courseService.findById(courseId);

		if (!courseOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
		} else {
			courseService.delete(courseOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.");
		}
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<Object> UpdateCourse(@RequestBody @Valid CourseDto courseDto, @PathVariable(value = "courseId") UUID courseId) {
		
		Optional<Course> courseOptional = courseService.findById(courseId);

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
