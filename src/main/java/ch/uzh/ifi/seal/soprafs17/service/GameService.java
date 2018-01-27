package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.web.rest.GameResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            return game;
        }
        return null;

    }

    public void startGame(Long gameId, String userToken) {
        Game game = gameRepository.findOne(gameId);
        User owner = userRepository.findByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: implement the logic for starting the game
        }
    }

    public void stopGame(Long gameId, String userToken) {
        Game game = gameRepository.findOne(gameId);
        User owner = userRepository.findByToken(userToken);
        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: implement the logic for stopping the game
        }
    }

    public List<Move> listMoves(Long gameId) {
        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            return game.getMoves();
        }

        return null;
    }

    public void addMove(Move move) {
        // TODO Mapping into Move + execution of move
    }

    public Move getMove(Long gameId, Integer moveId) {
        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            return game.getMoves().get(moveId);
        }
        return null;
    }

    public List<User> listPlayers(Long gameId) {
        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            return game.getPlayers();
        }

        return null;
    }

    public String addPlayer(Long gameId, String userToken) {
        Game game = gameRepository.findOne(gameId);
        User player = userRepository.findByToken(userToken);

        if (game != null && player != null
                && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);
            this.logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size() - 1);
        } else {
            this.logger.error("Error adding player with token: " + userToken);
        }
        return null;
    }

    public User getPlayer(Long gameId, Integer playerId) {
        Game game = gameRepository.findOne(gameId);
        return game.getPlayers().get(playerId);
    }
}
