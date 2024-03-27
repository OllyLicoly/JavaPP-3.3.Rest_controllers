package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
//@Controller
@RequestMapping("/api/user")
public class UsersController {
    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("id") long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/current")
//    public ResponseEntity<User> getCurrentUser(Principal principal) {
//        return new ResponseEntity<>(userService.findUserByUsername(principal.getName()), HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.findUserById(id));
//        }
//
//    @GetMapping(value = "/current")
//    public String showUserPage(Principal principal, Model model) {
//        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
//        return "userprofile";
//    }
}
