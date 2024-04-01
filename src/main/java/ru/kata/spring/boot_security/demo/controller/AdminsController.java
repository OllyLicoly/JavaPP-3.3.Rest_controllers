package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    private ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/user/{id}")
    private ResponseEntity<HttpStatus> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }






//    @PostMapping("/delete{id}")
//    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
//        userService.deleteUserById(id);
//        return "redirect:/admin";
//    }


//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute("user") User user,
//                             @RequestParam("id") Long id,
//                             @RequestParam("roles") Set<Long> rolesId) {
//        Set<Role> roles = roleService.findById(rolesId);
//        user.setRoles(roles);
//        user.setPassword(user.getPassword());
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/add")
//    public String addNewUser(@ModelAttribute("user") User user,
//                             @RequestParam("roles") Set<Long> rolesId) {
//        Set<Role> roles = roleService.findById(rolesId);
//        user.setRoles(roles);
//        user.setPassword(user.getPassword());
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
}
