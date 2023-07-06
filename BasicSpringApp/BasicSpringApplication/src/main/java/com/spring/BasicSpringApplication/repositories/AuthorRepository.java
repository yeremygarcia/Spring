package com.spring.BasicSpringApplication.repositories;

import com.spring.BasicSpringApplication.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
