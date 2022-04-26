package br.com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.course.repositories.CourseUserRepository;

@Service
public class CourseUserServiceImpl {
	
	@Autowired
	private CourseUserRepository courseUserRepository;

}
