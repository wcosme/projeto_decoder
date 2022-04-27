package br.com.ead.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ead.authuser.clients.UserClient;
import br.com.ead.authuser.dtos.CourseDto;
import br.com.ead.authuser.dtos.UserCourseDto;
import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.services.UserCourseService;
import br.com.ead.authuser.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private UserService userService;

    @Autowired
    private UserCourseService userCourseService;
	
	@GetMapping("users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
    		@PathVariable(value = "userId") UUID userId,
    		@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Direction.ASC) Pageable pageable){		
		
		return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId, pageable));		
	}
	
	@PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                               @RequestBody @Valid UserCourseDto userCourseDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if(userCourseService.existsByUserAndCourseId(userModelOptional.get(), userCourseDto.getCourseId())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        UserCourseModel userCourseModel = userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseDto.getCourseId()));
        return  ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);
    }
}
