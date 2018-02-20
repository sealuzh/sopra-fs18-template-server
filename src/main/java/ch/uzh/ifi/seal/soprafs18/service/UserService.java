package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs18.entity.Game;
import ch.uzh.ifi.seal.soprafs18.entity.User;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * /**
 * Created by LucasPelloni on 26.01.18.
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(String name, String username, String token, UserStatus status, List<Game> games) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setToken(token);
        newUser.setStatus(status);
        newUser.setGames(games);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User login(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setToken(UUID.randomUUID().toString());
            user.setStatus(UserStatus.ONLINE);
            user = userRepository.save(user);
            return user;
        }
        return null;
    }

    public void logout(Long userId, String userToken) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent() && userOptional.get().getToken().equals(userToken)) {
            User user = userOptional.get();
            user.setStatus(UserStatus.OFFLINE);
            userRepository.save(user);
        }
    }

    public User addUser(User user) {
        user.setStatus(UserStatus.OFFLINE);
        user.setToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }


    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id); //TODO check if user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        }
    }
}
