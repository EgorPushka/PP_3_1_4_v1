package ru.kata.spring.bootstrap.demo.services;

import ru.kata.spring.bootstrap.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllUsers();
    void save(Role role);
    void deleteById(int id);
    Role showUserById(int id);

}
