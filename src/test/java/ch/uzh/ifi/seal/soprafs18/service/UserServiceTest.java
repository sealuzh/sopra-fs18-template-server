package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.Application;
import ch.uzh.ifi.seal.soprafs18.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs18.entity.Game;
import ch.uzh.ifi.seal.soprafs18.entity.User;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertNotNull;


/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private List<Game> games;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByToken("t123"));
        User user = userService.createUser("testName", "testUsername", "t123", UserStatus.ONLINE, games);
        assertNotNull(userRepository.findByToken("t123"));
        Assert.assertEquals(userRepository.findByToken("t123"), user);
    }

    @Test
    public void deleteUser() {
        User user = userService.createUser("testName2", "testUsername2", "t1232", UserStatus.ONLINE, games);
        userRepository.delete(user);
        Assert.assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
