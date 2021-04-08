package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    public long id;

    private Integer turn;

    @ManyToOne
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;


    @ElementCollection
    @Column(name = "salvoUbication")
    private Set<String> salvoUbication = new HashSet<>();

    public Salvo() {
    }

    public Salvo(Integer turn, GamePlayer gamePlayer, Set<String> salvoUbication) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvoUbication = salvoUbication;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Set<String> getSalvoUbication() {
        return salvoUbication;
    }

    public void setSalvoUbication(Set<String> salvoUbication) {
        this.salvoUbication = salvoUbication;
    }

}