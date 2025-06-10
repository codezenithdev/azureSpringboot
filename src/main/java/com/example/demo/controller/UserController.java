package com.example.demo.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;
    public UserController(UserRepository repo) { this.repo = repo; }

    // 1) List all
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", repo.findAll());
        return "users/list";
    }


    // 2) Show empty form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }


    // 3) Show form for edit
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        User u = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", u);
        return "users/form";
    }

    // 4) Process both create & update
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult br) {
        if (br.hasErrors()) {
            return "users/form";
        }
        repo.save(user);
        return "redirect:/users";
    }

    // 5) Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/users";
    }
}
