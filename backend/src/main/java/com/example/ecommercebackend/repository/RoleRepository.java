package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.AppRole;
import com.example.ecommercebackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
