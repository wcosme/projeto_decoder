package br.com.ead.authuser.dtos;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ead.authuser.enums.CourseLevel;
import br.com.ead.authuser.enums.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {
	
	private UUID courseId;
	
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
