package com.prisma.library.library.control.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prisma.library.library.entity.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);
}
