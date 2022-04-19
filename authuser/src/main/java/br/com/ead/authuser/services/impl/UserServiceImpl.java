package br.com.ead.authuser.services.impl;

import br.com.ead.authuser.repositories.UserRepository;
import br.com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
}
