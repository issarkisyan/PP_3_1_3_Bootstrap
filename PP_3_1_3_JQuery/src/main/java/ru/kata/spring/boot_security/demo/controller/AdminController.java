package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("templates/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping()
    public String readList(Model model,@ModelAttribute("user") User user, @ModelAttribute("role") Role role, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("userAdmin", userService.show(currentUser.getId()));
        //model.addAttribute("userEdit", userService.show(user.getId()));
        model.addAttribute("users", userService.readListUsers());
        return "admin/index";
    }

    @PostMapping()
    public String create (@ModelAttribute("user") User user, @ModelAttribute("role") Role role) {
        userService.createUser(user);
        roleService.createRole(role);
        //userService.addToCommonTable(user, role);
        //model.addAttribute("userPost", userService.createUser(user));
        //model.addAttribute("role",userService.createRole(role));
        //model.addAttribute("user");
        return "redirect:/templates/admin";
    }

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.show(id));
        return "admin/index";
    }

    //PathVariable("id") long id
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/templates/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/templates/admin";
    }
}
