package br.com.ead.course.dtos;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ead.course.enuns.CourseLevel;
import br.com.ead.course.enuns.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {
	
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	
	@NotNull
	private String imageUrl;
	
	@NotNull
	private CourseStatus courseStatus;
	
	@NotNull
	private UUID userInstructor;
	
	@NotNull
	private CourseLevel courseLevel;

}
