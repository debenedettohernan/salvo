package com.codeoftheweb.salvo;

import com.sun.istack.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        Map<String , Object > dto = new LinkedHashMap<String, Object>();
        if(!isGuest(authentication)){
            Player authenticationPlayer = playerRepository.findByUserName(authentication.getName());
            dto.put("player",playersDTO(authenticationPlayer));
        }
        else{
            dto.put("player", (null));
        }
        dto.put("games", gameRepository.findAll().stream().map(this::gameDTO).collect(toSet()));
        return dto;
    }

    @GetMapping("/gamePlayers")
    public Set<Map<String, Object>> getGamePlayers() {
        return gamePlayerRepository.findAll().stream().map(this::gamePlayersDTO).collect(toSet());
    }

    @GetMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> findGamePlayer(@PathVariable long gamePlayerId) {
        return gameViewDTO(gamePlayerRepository.findById(gamePlayerId).get());
    }

    @PostMapping("/players")
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);

        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(username) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/players")
    public List<Map<String,Object>> getPlayer(){
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
        dtoGamePlayer.put("score",gamePlayer.getScore().map(Score::getScore).orElse(null));

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



}
