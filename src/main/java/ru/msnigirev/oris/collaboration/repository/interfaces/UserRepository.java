package ru.msnigirev.oris.collaboration.repository.interfaces;

import ru.msnigirev.oris.collaboration.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    String getAvatarPath(String username);

    String getUsernameByToken(String csrfToken);

    void deleteCsrfToken(String csrfToken);

    void addCsrfToken(String csrfToken, String username);

    boolean csrfTokenExists(String csrfToken);

    void registerNewUser(String username, String publicName, String email, String phoneNumber, String password);

    void addAvatar(String url, String username);

    Optional<User> getByUsername(String username);

    String getUsernameById(int id);

    int getIdByUsername(String username);
}
