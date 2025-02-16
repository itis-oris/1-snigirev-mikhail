package ru.msnigirev.oris.collaboration.repository.interfaces;

import java.util.List;

public interface ProjectAdminsRepository {
    List<Integer> getAdmins(int projectId);

    List<Integer> getProjects(int userId);

    boolean addNewRelation(int projectId, int userId);
}
