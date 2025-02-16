package ru.msnigirev.oris.collaboration.service;

import ru.msnigirev.oris.collaboration.dto.UserDto;
import ru.msnigirev.oris.collaboration.entity.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();

    List<UserDto> getAllUsers(int offset, int size);

    User getUser(String username);

    UserDto getUserDto(String username);

    String getUsernameByToken(String csrfToken);

    String getUsernameById(int id);

    void deleteCsrfToken(String csrfToken);

    void addCsrfToken(String csrfToken, String username);

    void registerNewUser(String username, String publicName, String email, String phoneNumber, String password);

    boolean csrfTokenExists(String csrfToken);

    void addAvatar(String url, String username);

    String getAvatarPath(String username);
}
