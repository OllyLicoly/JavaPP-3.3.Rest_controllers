package ru.kata.spring.boot_security.demo.service;
import ru.kata.spring.boot_security.demo.entities.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findUserById(Long id);

    User findUserByUsername(String username);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Long id);


    User getCurrentUser() ;


}
