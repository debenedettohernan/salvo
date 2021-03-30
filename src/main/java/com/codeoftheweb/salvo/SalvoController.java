package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/games")
    public Set<Map<String, Object>> getGames() {
        return gameRepository.findAll().stream().map(this::gameDTO).collect(toSet());
    }

    @RequestMapping("/players")
    public Set<Map<String, Object>> getPlayers() {
        return playerRepository.findAll().stream().map(this::playersDTO).collect(toSet());
    }

    @RequestMapping("/gamePlayers")
    public Set<Map<String, Object>> getGamePlayers() {
        return gamePlayerRepository.findAll().stream().map(this::gamePlayersDTO).collect(toSet());
    }

    public Map<String, Object> gameDTO(Game game ) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("date", game.getCreationDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(this::gamePlayersDTO).collect(toSet()));

        return dto;
    }

    public Map<String, Object> playersDTO(Player player ) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email",player.getUserName());

        return dto;
    }

    public Map<String, Object> gamePlayersDTO(GamePlayer gamePlayer) {
        Map<String, Object> dtoGamePlayer = new LinkedHashMap<String, Object>();
        dtoGamePlayer.put("id", gamePlayer.getId());
        dtoGamePlayer.put("player", playersDTO(gamePlayer.getPlayer()));

        return dtoGamePlayer;

    }
}