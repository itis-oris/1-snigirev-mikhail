package ru.msnigirev.oris.collaboration.repository.interfaces;

import ru.msnigirev.oris.collaboration.entity.Project;
import ru.msnigirev.oris.collaboration.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectRepository{

    int getMaxId();

    void create(Project data);

    Optional<Project> getById(Integer id);

    List<Project> getAll(int offset, int size);

    List<Project> searchByName(String name);

    void addAvatar(String avatarUrl, int id);
}
