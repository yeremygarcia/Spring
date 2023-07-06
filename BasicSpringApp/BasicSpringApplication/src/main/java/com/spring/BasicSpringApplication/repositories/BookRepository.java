package com.spring.BasicSpringApplication.repositories;

import com.spring.BasicSpringApplication.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
