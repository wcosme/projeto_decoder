package br.com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.course.repositories.LessonRepository;
import br.com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	
	@Autowired
	private LessonRepository lessonRepository;

}
