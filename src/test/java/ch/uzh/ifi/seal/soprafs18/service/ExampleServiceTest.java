package ch.uzh.ifi.seal.soprafs18.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import ch.uzh.ifi.seal.soprafs18.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class ExampleServiceTest {

	@Autowired
	ExampleService exampleService;
	
	@Test
	public void testDoLogic() {
		assertThat(exampleService.doLogic("a", "b"), is("ab"));
	}
}
