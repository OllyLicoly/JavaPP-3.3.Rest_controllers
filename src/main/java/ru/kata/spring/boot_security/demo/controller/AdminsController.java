package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;


@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserServiceImp userService;
    private final RoleServiceImp roleService;

    @Autowired
    public AdminsController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    @GetMapping("/update")
    public String updateUser(@RequestParam("id") Long id, Model model ){
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "userupdate";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        user.setPassword(user.getPassword());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "useradd";
    }

    @PostMapping("/add")
    public String addNewUser(@ModelAttribute("user") User user,
                             Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        user.setPassword(user.getPassword());
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
