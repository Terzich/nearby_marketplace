package com.example.nearby.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractBaseService<E, R extends JpaRepository<E, Long>> {

    private final R repository;

    public AbstractBaseService(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public Optional<E> getById(Long id) {
        return repository.findById(id);
    }

    public E create(E entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean doesEntityExist(Long id) {
        return repository.findById(id).isPresent();
    }
}
