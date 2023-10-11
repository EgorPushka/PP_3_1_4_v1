package ru.kata.spring.bootstrap.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.bootstrap.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> indexUsers();
    void add(User user);
    void delete(int id);
    void edit(User user);
    User getById(int id);
    User findByUsername(String name);
    UserDetails loadUserByUsername(String username);
}
