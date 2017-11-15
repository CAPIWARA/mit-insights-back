package br.com.capiwara.service;

import br.com.capiwara.entity.User;
import br.com.capiwara.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        userRepository.save(user);
        return findByEmail(user.getEmail());
    }
}
