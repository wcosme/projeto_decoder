package br.com.ead.authuser.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.repositories.UserCourseRepository;
import br.com.ead.authuser.services.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {
	
	@Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

}
