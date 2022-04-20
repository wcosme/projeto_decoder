package br.com.ead.authuser.services;

import br.com.ead.authuser.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();
    Optional<UserModel> findById(UUID userId);
    void delete(UserModel user);
    void save(UserModel userModel);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
