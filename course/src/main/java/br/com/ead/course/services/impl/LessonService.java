package br.com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.course.repositories.LessonRepository;

@Service
public class LessonService implements br.com.ead.course.services.LessonService {
	
	@Autowired
	private LessonRepository lessonRepository;

}
