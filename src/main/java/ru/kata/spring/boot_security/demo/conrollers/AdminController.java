package ru.kata.spring.boot_security.demo.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String toolbar() {
        return "admin/toolbar";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/showAllUsers";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/update")
    public String update(@RequestParam(value = "id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        User user = userService.findById(id);

        if (user.getRoles().contains(2)) {
            return "redirect:/admin/users";
        }
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
