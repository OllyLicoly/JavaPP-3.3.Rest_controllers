package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

//@RestController
@Controller
@RequestMapping("/api/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public String printUsers(ModelMap model
//            , Principal principal
    ) {
//        model.addAttribute("userAuth", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "users";
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> allUsers() {
//        List<User> userList = userService.getAllUsers();
//        return ResponseEntity.ok(userList);
//    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.findUserById(id));
//    }
//
//
//    @PostMapping("/users")
//    public User addNewUser(@RequestBody User user) {
////        User temp = user;
////        user.setRoles((Set<Role>) roleService.getAllRoles());
//        userService.saveUser(user);
//        return user;
//    }
//
//    @PutMapping("/users")
//    public User updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//        return user;
//    }
//
////    @DeleteMapping("/users/{id}")
//    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    private String deleteUser(@PathVariable Long id){
//        userService.deleteUserById(id);
//        return "User with ID " + id + " was deleted";
//    }




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
