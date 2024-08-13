package ru.kata.spring.boot_security.demo.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String user() {
        return "user/user";
    }

    @GetMapping("/info")
    public String getUser(@AuthenticationPrincipal User user, Model model) {
        User userFromDB = userService.findById(user.getId());
        model.addAttribute("user", userFromDB);
        return "user/userInfo";
    }

    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") User user, @AuthenticationPrincipal User userFromDB) {
        userService.update(userFromDB.getId(), user);
        return "redirect:/user/info";
    }

    @DeleteMapping("/delete")
    public String delete(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return "redirect:/";
    }
}
