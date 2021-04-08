package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map;
import java.util.LinkedHashMap;

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

    @RequestMapping("/games")
    public Set<Map<String, Object>> getGames() {
        return gameRepository.findAll().stream().map(this::gameDTO).collect(toSet());
    }

    @RequestMapping("/players")
    public Set<Map<String, Object>> getPlayers() {
        return playerRepository.findAll().stream().map(this::playersDTO).collect(toSet());
    }

    @GetMapping("/gamePlayers")
    public Set<Map<String, Object>> getGamePlayers() {
        return gamePlayerRepository.findAll().stream().map(this::gamePlayersDTO).collect(toSet());
    }

    @GetMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> findGamePlayer(@PathVariable long gamePlayerId) {
        return gameViewDTO(gamePlayerRepository.findById(gamePlayerId).get());
    }

    public Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("date", game.getCreationDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(this::gamePlayersDTO).collect(toSet()));

        return dto;
    }

    public Map<String, Object> playersDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());

        return dto;
    }

    public Map<String, Object> gamePlayersDTO(GamePlayer gamePlayer) {
        Map<String, Object> dtoGamePlayer = new LinkedHashMap<String, Object>();
        dtoGamePlayer.put("id", gamePlayer.getId());
        dtoGamePlayer.put("player", playersDTO(gamePlayer.getPlayer()));

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