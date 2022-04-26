package br.com.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ead.authuser.models.UserCourseModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

}
