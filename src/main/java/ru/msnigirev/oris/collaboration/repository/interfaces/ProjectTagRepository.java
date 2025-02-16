package ru.msnigirev.oris.collaboration.repository.interfaces;

public interface ProjectTagRepository {
    String getById(int id);
    int getByName(String name);
    void add(String name);
}

