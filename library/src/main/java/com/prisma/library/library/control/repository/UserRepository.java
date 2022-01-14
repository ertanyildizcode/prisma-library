package com.prisma.library.library.control.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prisma.library.library.entity.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByNameAndFirstName(String name, String firstName);
}
