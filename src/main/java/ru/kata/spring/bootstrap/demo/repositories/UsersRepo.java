package ru.kata.spring.bootstrap.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.bootstrap.demo.models.User;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
