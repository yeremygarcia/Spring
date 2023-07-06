package com.spring.BasicSpringApplication.controllers;

import com.spring.BasicSpringApplication.models.Author;
import com.spring.BasicSpringApplication.repositories.AuthorRepository;
import com.spring.BasicSpringApplication.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    //CREATE
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "create-author.html";
    }

    @PostMapping("")
    public String createAuthor(@ModelAttribute("author") Author newAuthor) {
        authorRepository.save(newAuthor);
        return "redirect:/authors";
    }

    //READ
    @GetMapping("")
    public String getAllAuthors(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author-list.html";
    }

    @GetMapping("/{id}")
    public String getAuthorById(@PathVariable("id") Long id, Model model) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return "error.html";
        }
        model.addAttribute("author", author);
        return "author-details.html";
    }

    //UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return "error.html";
        }
        model.addAttribute("author", author);
        return "update-author.html";
    }

    @PostMapping("{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute("author") Author updatedAuthor) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return "error.html";
        }
        author.setName(updatedAuthor.getName());
        authorRepository.save(author);
        return "redirect:/authors/{id}";
    }

    //DELETE
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return "error.html";
        }
        authorRepository.delete(author);
        return "redirect:/authors";
    }

}
