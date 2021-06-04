package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


import static java.util.stream.Collectors.toList;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime localDate;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gamePlayer", cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gamePlayer", cascade = CascadeType.ALL)
    private Set<Salvo> salvos = new HashSet<>();


    public GamePlayer() {
    }

    public GamePlayer(LocalDateTime localDate, Game game, Player player) {
        this.localDate = localDate;
        this.player = player;
        this.game = game;
    }

    public void AddShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }

    public void AddSalvoes(Salvo salvo) {
        salvo.setGamePlayer(this);
        salvos.add(salvo);
    }

    public Optional<Score> getScore() {
        return player.getScore(this.game);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public void setSalvos(Set<Salvo> salvos) {
        this.salvos = salvos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayer that = (GamePlayer) o;
        return id == that.id && Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDate);
    }


    public GameStatus gameStatus() {
        if (this.getShips().isEmpty()) {
            return GameStatus.PLACE_SHIPS;
        } else {
            if (this.getPlayer2().isPresent()) {
                if (this.getPlayer2().get().getShips().isEmpty()) {
                    return GameStatus.WAIT_SHIP_OPPONENT;
                } else {
                    if (this.getSalvos().stream().noneMatch(em -> em.getTurn() == this.getSalvos().size())) {
                        return GameStatus.PLACE_YOUR_SALVOS;
                    } else {
                        if (this.getPlayer2().get().getSalvos().stream().noneMatch(em -> em.getTurn() == this.getSalvos().size())) {
                            return GameStatus.WAIT_SALVO_OPPONENT;
                        } else if (this.getSalvos().size() == this.getPlayer2().get().getSalvos().size()) {
                            List<Long> mySunks = this.getSalvos().stream().filter(x -> x.getTurn() == this.getSalvos().size()).flatMap(x -> x.getSunks().stream()).map(Ship::getId).collect(toList());
                            List<Long> player2Sunks = this.getPlayer2().get().getSalvos().stream().filter(x -> x.getTurn() == this.getSalvos().size()).flatMap(x -> x.getSunks().stream()).map(Ship::getId).collect(toList());

                            if (mySunks.size() == 5 && player2Sunks.size() == 5) {
                                return GameStatus.TIE;
                            } else if (mySunks.size() == 5) {
                                return GameStatus.WIN;
                            } else if (player2Sunks.size() == 5) {
                                return GameStatus.LOSE;
                            } else {
                                return GameStatus.PLACE_YOUR_SALVOS;
                            }
                        } else {
                            return GameStatus.PLACE_SALVOS;
                        }
                    }
                }
            } else {
                return GameStatus.WAIT_OPPONENT;
            }
        }
    }

    public Optional<GamePlayer> getPlayer2() {
        Optional<GamePlayer> player2 = this.getGame().getGamePlayers().stream().filter(player -> player.getId() != this.getId()).findFirst();
        return player2;
    }


}
