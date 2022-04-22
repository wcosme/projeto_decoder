package br.com.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.ead.authuser.dtos.UserDto;
import br.com.ead.authuser.filters.FilterTemplate;
import br.com.ead.authuser.models.UserModel;
import br.com.ead.authuser.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(FilterTemplate.UserFilter filter,
    													@PageableDefault(page = 0, size = 10, sort = "userId", direction = Direction.ASC) Pageable pageable){
    	
    	Page<UserModel> userPage = userService.findAll(filter, pageable);
    	
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }else {
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted sucess.");
        }
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "userId") UUID userId, 
    										 @RequestBody @Validated(UserDto.UserView.UserPut.class)
    										 @JsonView(UserDto.UserView.UserPut.class) UserDto dto){
        Optional<UserModel> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }else {
            var userModel = userOptional.get();
            userModel.setFullName(dto.getFullName());
            userModel.setPhoneNumber(dto.getPhoneNumber());
            userModel.setCpf(dto.getCpf());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            
            userService.save(userModel);
            
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
    
    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> UpdatePassword(@PathVariable(value = "userId") UUID userId, 
    											 @RequestBody @Validated(UserDto.UserView.PasswordPut.class) 
    											 @JsonView(UserDto.UserView.PasswordPut.class) UserDto dto){
        Optional<UserModel> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }if(!userOptional.get().getPassword().equals(dto.getOldPassword())) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        }else {
            var userModel = userOptional.get();
            userModel.setPassword(dto.getPassword());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            
            userService.save(userModel);
            
            return ResponseEntity.status(HttpStatus.OK).body("Password update successfully!");
        }
    }
    
    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> UpdateImage(@PathVariable(value = "userId") UUID userId, 
    										  @RequestBody @Validated(UserDto.UserView.ImagePut.class)  
    										  @JsonView(UserDto.UserView.ImagePut.class) UserDto dto){
        Optional<UserModel> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }else {
            var userModel = userOptional.get();
            userModel.setImageUrl(dto.getImageUrl());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            
            userService.save(userModel);
            
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }    
}
