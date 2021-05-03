package com.codeoftheweb.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")

public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    @RequestMapping("/games")
    public Map<String, Object> getAllGames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (!isGuest(authentication)) {
            Player authenticationPlayer = playerRepository.findByUserName(authentication.getName());
            dto.put("player", playersDTO(authenticationPlayer));
        } else {
            dto.put("player", (null));
        }
        dto.put("games", gameRepository.findAll().stream().map(this::gameDTO).collect(toSet()));
        return dto;
    }

    @PostMapping("/games")
    public ResponseEntity<Map<String, Object>> newGame(Authentication authentication) {
        if (!isGuest(authentication)) {
            LocalDateTime date = LocalDateTime.now();

            Player authenticationPlayer = playerRepository.findByUserName(authentication.getName());

            Game game = new Game(date);

            GamePlayer gamePlayer = new GamePlayer(date, game, authenticationPlayer);

            gameRepository.save(game);

            gamePlayerRepository.save(gamePlayer);

            return new ResponseEntity<>(makeMap("gpId", gamePlayer.getId()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(makeMap("error", "You must log in with your account to create a game"), HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/games/{gameId}/players")
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameId, Authentication authentication) {
        if (!isGuest(authentication)) {
            Player authenticationPlayer = playerRepository.findByUserName(authentication.getName());

            Optional<Game> game = gameRepository.findById(gameId);

            if (game.isEmpty()) {

                return new ResponseEntity<>(makeMap("error", "There is no such game"), HttpStatus.FORBIDDEN);
            } else {
                if (game.get().getGamePlayers().size() == 1) {
                    if (game.get().getGamePlayers().stream().anyMatch(gamePlayer1 -> gamePlayer1.getPlayer().getId() != authenticationPlayer.getId())) {

                        GamePlayer gamePlayer = new GamePlayer(LocalDateTime.now(), game.get(), authenticationPlayer);

                        gamePlayerRepository.save(gamePlayer);

                        return new ResponseEntity<>(makeMap("gpId", gamePlayer.getId()), HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>(makeMap("error", "You cannot enter the game that you created yourself"), HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>(makeMap("error", "The game is full"), HttpStatus.UNAUTHORIZED);
                }
            }
        } else {
            return new ResponseEntity<>(makeMap("error", "You are not identified"), HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/gamePlayers")
    public Set<Map<String, Object>> getGamePlayers() {
        return gamePlayerRepository.findAll().stream().map(this::gamePlayersDTO).collect(toSet());
    }
    @GetMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> gameView(@PathVariable Long gamePlayerId, Authentication
            authentication) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "You must log in with your account to play"), HttpStatus.UNAUTHORIZED);
        } else {
            Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
            Player player = playerRepository.findByUserName(authentication.getName());
            if (gamePlayer.isEmpty()) {
                return new ResponseEntity<>(makeMap("error", "The game does not exist"), HttpStatus.NOT_FOUND);
            } else if (gamePlayer.get().getPlayer().getId() != player.getId()) {
                return new ResponseEntity<>(makeMap("error", "You can only see your ships"), HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(gameViewDTO(gamePlayer.get()), HttpStatus.OK);
        }
    }

    @PostMapping("/players")
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (playerRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/players")
    public List<Map<String, Object>> getPlayer() {
        return playerRepository.findAll().stream().map(this::playersDTO).collect(Collectors.toList());
    }


    public Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("date", game.getCreationDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(this::gamePlayersDTO).collect(toSet()));
        return dto;
    }

    public Map<String, Object> playersDTO(Player player) {
        Map<String, Object> dtoPlayer = new LinkedHashMap<String, Object>();
        dtoPlayer.put("id", player.getId());
        dtoPlayer.put("name", player.getUserName());
        return dtoPlayer;
    }

    public Map<String, Object> gamePlayersDTO(GamePlayer gamePlayer) {
        Map<String, Object> dtoGamePlayer = new LinkedHashMap<String, Object>();
        dtoGamePlayer.put("id", gamePlayer.getId());
        dtoGamePlayer.put("player", playersDTO(gamePlayer.getPlayer()));
        dtoGamePlayer.put("score", gamePlayer.getScore().map(Score::getScore).orElse(null));
        return dtoGamePlayer;
    }

    public Map<String, Object> shipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getTypeShips());
        dto.put("locations", ship.getLocation());
        return dto;
    }

    public Map<String, Object> salvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getSalvoLocation());
        return dto;
    }

    public Map<String, Object> gameViewDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("date", gamePlayer.getGame().getCreationDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(this::gamePlayersDTO).collect(toSet()));
        dto.put("ships", gamePlayer.getShips().stream().map(this::shipDTO).collect(toSet()));
        dto.put("salvo", gamePlayer.getGame().getGamePlayers().stream().flatMap(i -> i.getSalvos().stream().map(this::salvoDTO)).collect(toSet()));
        return dto;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
