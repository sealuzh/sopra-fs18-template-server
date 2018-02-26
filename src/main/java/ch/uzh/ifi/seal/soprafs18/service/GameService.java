package ch.uzh.ifi.seal.soprafs18.service;

import ch.uzh.ifi.seal.soprafs18.GameConstants;
import ch.uzh.ifi.seal.soprafs18.entity.Game;
import ch.uzh.ifi.seal.soprafs18.entity.Move;
import ch.uzh.ifi.seal.soprafs18.entity.User;
import ch.uzh.ifi.seal.soprafs18.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs18.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs18.web.rest.GameResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lucas Pelloni on 26.01.18.
 * Service class for managing games, which contains the logic and all DB calls.
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final GameRepository gameRepository;

    private final UserRepository userRepository;

    private final String CONTEXT = "/games";

    Logger logger = LoggerFactory.getLogger(GameResource.class);

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }


    public List<Game> listGames() {
        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);
        return result;
    }

    public String addGame(Game game, String userToken) {
        User owner = userRepository.findByToken(userToken);
        if (owner != null) {
            // TODO Mapping into Game
            game = gameRepository.save(game);

            return CONTEXT + "/" + game.getId();
        }
        return null;
    }

    public Game getGame(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        return game.orElse(null);
    }

    public void startGame(Long gameId, String userToken) {
        Optional<Game> game = gameRepository.findById(gameId);
        User owner = userRepository.findByToken(userToken);

        if (owner != null && game.isPresent() && game.get().getOwner().equals(owner.getUsername())) {
            // TODO: implement the logic for starting the game
        }
    }

    public void stopGame(Long gameId, String userToken) {
        Optional<Game> game = gameRepository.findById(gameId);
        User owner = userRepository.findByToken(userToken);
        if (owner != null && game.isPresent() && game.get().getOwner().equals(owner.getUsername())) {
            // TODO: implement the logic for stopping the game
        }
    }

    public List<Move> listMoves(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            return game.get().getMoves();
        }
        return null;
    }

    public void addMove(Move move) {
        // TODO Mapping into Move + execution of move
    }

    public Move getMove(Long gameId, Integer moveId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            return game.get().getMoves().get(moveId);
        }
        return null;
    }

    public List<User> listPlayers(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            return game.get().getPlayers();
        }

        return null;
    }

    public String addPlayer(Long gameId, String userToken) {
        Optional<Game> game = gameRepository.findById(gameId);
        User player = userRepository.findByToken(userToken);

        if (game.isPresent() && player != null
                && game.get().getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.get().getPlayers().add(player);
            this.logger.debug("Game: " + game.get().getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.get().getPlayers().size() - 1);
        } else {
            this.logger.error("Error adding player with token: " + userToken);
        }
        return null;
    }

    public User getPlayer(Long gameId, Integer playerId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            return game.get().getPlayers().get(playerId);
        }
        return null;
    }
}
