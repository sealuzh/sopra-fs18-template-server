package ch.uzh.ifi.seal.soprafs18.repository;

import ch.uzh.ifi.seal.soprafs18.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public interface GameRepository extends CrudRepository<Game, Long> {
	Game findByName(String name);
}
