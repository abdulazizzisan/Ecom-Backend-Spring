package com.kolu.ecombackend.auth.repository;

import com.kolu.ecombackend.auth.model.Roles;
import com.kolu.ecombackend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByRole(Roles role);
}
