package ru.spbume.musicalbums.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbume.musicalbums.entity.User;
import ru.spbume.musicalbums.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String login, String password) {
        if (userRepository.existsByLogin(login)) {
            return false;
        }
        User user = new User(login, password);
        userRepository.save(user);
        return true;
    }

    public User authenticate(String login, String password) {
        return userRepository.findByLogin(login)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    public User getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }
}
