package ch.uzh.ifi.seal.soprafs18.web.rest;

import ch.uzh.ifi.seal.soprafs18.entity.User;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs18.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(UserResource.CONTEXT)
public class UserResource extends GenericResource {

    static final String CONTEXT = "/users";
    Logger logger = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> listUsers() {
        logger.debug("listUsers");
        return this.userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        logger.debug("addUser: " + user);
        return this.userService.addUser(user);
    }


    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUser(@PathVariable Long userId) {
        logger.debug("getUser: " + userId);
        return userRepo.findById(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@PathVariable Long userId) {
        logger.debug("login: " + userId);
        return this.userService.login(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable Long userId, @RequestParam("token") String userToken) {
        logger.debug("getUser: " + userId);
        this.userService.logout(userId, userToken);
    }
}
