package ru.kata.spring.bootstrap.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.bootstrap.demo.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
