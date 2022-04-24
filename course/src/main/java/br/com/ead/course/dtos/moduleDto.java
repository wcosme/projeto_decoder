package br.com.ead.course.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class moduleDto {
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String description;

}