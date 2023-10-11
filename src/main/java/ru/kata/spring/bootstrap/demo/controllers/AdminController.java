package ru.kata.spring.bootstrap.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.bootstrap.demo.models.Role;
import ru.kata.spring.bootstrap.demo.models.User;
import ru.kata.spring.bootstrap.demo.repositories.RoleRepo;
import ru.kata.spring.bootstrap.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final RoleRepo roleRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private static final String REDIRECT_USERS_PAGE = "redirect:/admin";

    @Autowired
    public AdminController(UserService userService, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping(value = "/admin")
//    public String getSecret(ModelMap modelMap) {
//        modelMap.addAttribute("users", userService.indexUsers());
//        return "/admin";
//    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        List<User> allUsers = userService.indexUsers();
        User user = new User();
        User user1 = userService.findByUsername(principal.getName());
        model.addAttribute("newUser",user); //новый юзер для формы
        model.addAttribute("userPrincipal",user1); //авторизованный юзер
        model.addAttribute("allUsers", allUsers); //список всех юзеров
        model.addAttribute("allRoles", user1.getRoles()); //список всех ролей авторизованного юзера
        model.addAttribute("AllRolesBD", roleRepo.findAll()); //список всех ролей в БД
        return "/admin2";
    }

    @GetMapping("/users/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/user";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> roles = roleRepo.findAll();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "/new";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        User user = userService.getById(id);
        model.addAttribute("user", user);

        List<Role> roles = roleRepo.findAll();
        model.addAttribute("roles", roles);

        return "/edit";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {

        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        userService.edit(user);
        return REDIRECT_USERS_PAGE;
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        userService.add(user);

        return REDIRECT_USERS_PAGE;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return REDIRECT_USERS_PAGE;
    }

}
