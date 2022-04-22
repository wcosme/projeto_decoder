package br.com.ead.authuser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.ead.authuser.models.UserModel;

public interface UserService {
    List<UserModel> findAll();
    Optional<UserModel> findById(UUID userId);
    void delete(UserModel user);
    void save(UserModel userModel);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
	Page<UserModel> findAll(Specification<UserModel> filter, Pageable pageable);
}
