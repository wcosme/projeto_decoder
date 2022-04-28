package br.com.ead.course.dtos;

import java.util.UUID;

import br.com.ead.course.enuns.UserStatus;
import br.com.ead.course.enuns.UserType;
import lombok.Data;

@Data
public class UserDto {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
