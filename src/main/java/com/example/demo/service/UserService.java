package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public User create(User u) { return repo.save(u); }

    public List<User> findAll() { return repo.findAll(); }

    public User findById(Long id) {
        try {
            return repo.findById(id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public User update(Long id, User u) {
        User existing = findById(id);
        existing.setFirstName(u.getFirstName());
        existing.setLastName(u.getLastName());
        existing.setEmail(u.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.delete(findById(id));
    }
}
