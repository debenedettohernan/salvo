package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    public long id;

    private String typeShips;

    @ManyToOne
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "ubication")
    private List<String> ubication = new ArrayList<>();

    public Ship() {
    }

    public Ship(String typeShips, GamePlayer gamePlayer, List<String> ubication) {
        this.typeShips = typeShips;
        this.gamePlayer = gamePlayer;
        this.ubication = ubication;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeShips() {
        return typeShips;
    }

    public void setTypeShips(String typeShips) {
        this.typeShips = typeShips;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getUbication() {
        return ubication;
    }

    public void setUbication(List<String> ubication) {
        this.ubication = ubication;
    }
}
