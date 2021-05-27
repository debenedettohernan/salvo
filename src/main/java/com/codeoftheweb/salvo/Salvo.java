package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Column(name = "salvoLocation")
    private Set<String> salvoLocation = new HashSet<>();

    public Salvo() {
    }

    public Salvo(Integer turn, GamePlayer gamePlayer, Set<String> salvoLocation) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvoLocation = salvoLocation;
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

    public Set<String> getSalvoLocation() {
        return salvoLocation;
    }

    public void setSalvoLocation(Set<String> salvoLocation) {
        this.salvoLocation = salvoLocation;
    }

    public Optional<GamePlayer> getPlayer2(){
        Optional<GamePlayer> player2 = gamePlayer.getGame().getGamePlayers().stream().filter(player -> player.getId() != gamePlayer.getId()).findFirst();
        return player2;
    }

    public Set<String> getHits(){
        Set<String> totalHits = new HashSet<>();
        if(getPlayer2().isPresent()){
            Set<String> locationsShips = getPlayer2().get().getShips().stream().flatMap(ship -> ship.getLocation().stream()).collect(Collectors.toSet());
           locationsShips.retainAll(salvoLocation);
            totalHits = locationsShips;
        }
        return totalHits;
    }
    public Set<Ship> getSunks() {
        Set<Ship> shipSunks = new HashSet<>();
        Set<String> hitsLocacion = gamePlayer.getSalvos().stream().filter(salvo -> salvo.turn <= this.getTurn()).flatMap(salvoHit -> salvoHit.getHits().stream()).collect(Collectors.toSet());
        if (getPlayer2().isPresent()) {

            shipSunks = getPlayer2().get().getShips().stream().filter(ship -> hitsLocacion.containsAll(ship.getLocation())).collect(Collectors.toSet());
        }

        return shipSunks;
    }
}
