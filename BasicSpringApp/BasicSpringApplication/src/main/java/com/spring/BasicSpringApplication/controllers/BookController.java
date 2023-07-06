package com.spring.BasicSpringApplication.controllers;

import com.spring.BasicSpringApplication.models.Author;
import com.spring.BasicSpringApplication.models.Book;
import com.spring.BasicSpringApplication.repositories.AuthorRepository;
import com.spring.BasicSpringApplication.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    //CREATE
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authors);
        return "create-book.html";
    }

    @PostMapping("")
    public String createBook(@ModelAttribute("book") Book newBook) {
        bookRepository.save(newBook);
        return "redirect:/books";
    }

    //READ
    @RequestMapping("")
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book-list.html";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "book-details.html";
    }


    //UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "error.html";
        }
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "update-book.html";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "error.html";
        }
        Author author = authorRepository.findById(updatedBook.getAuthor().getId()).orElse(null);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(author);

        bookRepository.save(book);
        return "redirect:/books/{id}";
    }

    //DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return "error.html";
        }
        bookRepository.delete(book);
        return "redirect:/books";
    }
}
