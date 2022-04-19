package br.com.ead.authuser.services;

import br.com.ead.authuser.models.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> findAll();
}
