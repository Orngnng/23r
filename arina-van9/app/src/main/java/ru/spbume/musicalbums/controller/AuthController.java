package ru.spbume.musicalbums.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.spbume.musicalbums.entity.User;
import ru.spbume.musicalbums.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/albums";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!userService.register(user.getLogin(), user.getPassword())) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User loginUser,
                            HttpSession session,
                            Model model) {
        User user = userService.authenticate(loginUser.getLogin(), loginUser.getPassword());

        if (user == null) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userLogin", user.getLogin());
        return "redirect:/albums";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
