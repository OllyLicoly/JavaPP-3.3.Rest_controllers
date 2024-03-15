package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String printUsers(ModelMap model, Principal principal) {
        model.addAttribute("userAuth", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "users";
    }

//    @PostMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUserById(id);
//        return "redirect:/admin";
//    }

    @PostMapping("/delete{id}")
    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


//    @GetMapping("/update")
//    public String updateUser(@RequestParam("id") Long id, Model model ){
//        model.addAttribute("user", userService.findUserById(id));
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "userupdate";
//    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("id") Long id,
                             @RequestParam("roles") Set<Long> rolesId) {
        Set<Role> roles = roleService.findById(rolesId);
        user.setRoles(roles);
        user.setPassword(user.getPassword());
        userService.updateUser(user);
        return "redirect:/admin";
    }

//    @GetMapping("/add")
//    public String addUser(Model model){
//        model.addAttribute("user", new User());
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "useradd";
//    }

//    @PostMapping("/add")
//    public String addNewUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

    @PostMapping("/add")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam("roles") Set<Long> rolesId) {
        Set<Role> roles = roleService.findById(rolesId);
        user.setRoles(roles);
        user.setPassword(user.getPassword());
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
