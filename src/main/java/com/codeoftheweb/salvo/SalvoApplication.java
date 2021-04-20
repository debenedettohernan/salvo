package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SalvoApplication.class);
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public CommandLineRunner initData(PlayerRepository repositoryPlayer, GameRepository repositoryGame, GamePlayerRepository repositoryGamePlayer, ShipRepository repositoryShip, SalvoRepository repositorySalvo, ScoreRepository respositoryScore) {
        return (args) -> {

            Game game1 = new Game(LocalDateTime.now());
            Game game2 = new Game(LocalDateTime.now());
            Game game3 = new Game(LocalDateTime.now());
            Game game4 = new Game(LocalDateTime.now());
            Game game5 = new Game(LocalDateTime.now());
            Game game6 = new Game(LocalDateTime.now());

            Player player1 = new Player("t.almeida@ctu.gov", "mole");
            Player player2 = new Player("j.bauer@ctu.gov","24" );
            Player player3 = new Player("c.obrian@ctu.gov","42");
            Player player4 = new Player("kim_bauer@gmail.com", "kb");
            Player player5 = new Player("hernan.debenedetto@gmail.com", "hernan");

            GamePlayer gamePlayer1 = new GamePlayer(LocalDateTime.now(), game1, player1);
            GamePlayer gamePlayer2 = new GamePlayer(LocalDateTime.now(), game1, player2);
            GamePlayer gamePlayer3 = new GamePlayer(LocalDateTime.now(), game2, player3);
            GamePlayer gamePlayer4 = new GamePlayer(LocalDateTime.now(), game2, player4);
            GamePlayer gamePlayer5 = new GamePlayer(LocalDateTime.now(), game3, player5);
            GamePlayer gamePlayer6 = new GamePlayer(LocalDateTime.now(), game3, player1);
            GamePlayer gamePlayer7 = new GamePlayer(LocalDateTime.now(), game4, player2);
            GamePlayer gamePlayer8 = new GamePlayer(LocalDateTime.now(), game4, player1);
            GamePlayer gamePlayer9 = new GamePlayer(LocalDateTime.now(), game5, player5);
            GamePlayer gamePlayer10 = new GamePlayer(LocalDateTime.now(), game5, player3);
            GamePlayer gamePlayer11 = new GamePlayer(LocalDateTime.now(), game6, player5);

            Ship ship1 = new Ship("Carrier", gamePlayer1, List.of("A2", "B2", "C2", "D2", "E2"));
            Ship ship2 = new Ship("Battleship", gamePlayer1, List.of("A1", "B1", "C1", "D1"));
            Ship ship3 = new Ship("Submarine", gamePlayer1, List.of("F4", "F5", "F6"));
            Ship ship4 = new Ship("Destroyer", gamePlayer1, List.of("A3", "A4", "A5"));
            Ship ship5 = new Ship("Patrol Boat", gamePlayer1, List.of("C3", "C4"));

            Ship ship6 = new Ship("Carrier", gamePlayer2, List.of("A1", "B1", "C1", "D1", "E1"));
            Ship ship7 = new Ship("Battleship", gamePlayer2, List.of("A2", "B2", "C2", "D2"));
            Ship ship8 = new Ship("Submarine", gamePlayer2, List.of("F5", "F6", "F7"));
            Ship ship9 = new Ship("Destroyer", gamePlayer2, List.of("A4", "A5", "A6"));
            Ship ship10 = new Ship("Patrol Boat", gamePlayer2, List.of("C4", "C5"));

            Ship ship11 = new Ship("Carrier", gamePlayer3, List.of("A3", "B3", "C3", "D3", "E3"));
            Ship ship12 = new Ship("Battleship", gamePlayer3, List.of("A4", "B4", "C4", "D4"));
            Ship ship13 = new Ship("Submarine", gamePlayer3, List.of("F6", "F7", "F8"));
            Ship ship14 = new Ship("Destroyer", gamePlayer3, List.of("A5", "A6", "A7"));
            Ship ship15 = new Ship("Patrol Boat", gamePlayer3, List.of("C5", "C6"));

            Ship ship16 = new Ship("Carrier", gamePlayer4, List.of("A4", "B4", "C4", "D4", "E4"));
            Ship ship17 = new Ship("Battleship", gamePlayer4, List.of("A5", "B5", "C5", "D5"));
            Ship ship18 = new Ship("Submarine", gamePlayer4, List.of("F7", "F8", "F9"));
            Ship ship19 = new Ship("Destroyer", gamePlayer4, List.of("A6", "A7", "A8"));
            Ship ship20 = new Ship("Patrol Boat", gamePlayer4, List.of("C6", "C7"));

            Ship ship21 = new Ship("Carrier", gamePlayer5, List.of("A5", "B5", "C5", "D5", "E5"));
            Ship ship22 = new Ship("Battleship", gamePlayer5, List.of("A6", "B6", "C6", "D6"));
            Ship ship23 = new Ship("Submarine", gamePlayer5, List.of("F8", "F9", "F10"));
            Ship ship24 = new Ship("Destroyer", gamePlayer5, List.of("A7", "A8", "A9"));
            Ship ship25 = new Ship("Patrol Boat", gamePlayer5, List.of("C7", "C8"));

            Salvo salvo1 = new Salvo(1, gamePlayer1, Set.of("B5", "C5", "F1"));
            Salvo salvo2 = new Salvo(2, gamePlayer1, Set.of("F2", "D5"));
            Salvo salvo3 = new Salvo(1, gamePlayer2, Set.of("B4", "B5", "B6"));
            Salvo salvo4 = new Salvo(2, gamePlayer2, Set.of("E1", "H3", "A2"));
            Salvo salvo5 = new Salvo(1, gamePlayer3, Set.of("G6", "H6", "A4"));
            Salvo salvo6 = new Salvo(2, gamePlayer3, Set.of("A2", "A3", "D8"));
            Salvo salvo7 = new Salvo(1, gamePlayer4, Set.of("B4", "C6", "J9"));
            Salvo salvo8 = new Salvo(2, gamePlayer4, Set.of("J8", "F6", "J9"));
            Salvo salvo9 = new Salvo(1, gamePlayer5, Set.of("B1", "B2", "B3"));
            Salvo salvo10 = new Salvo(2, gamePlayer5, Set.of("C4", "F6", "A9"));
            Salvo salvo11 = new Salvo(1, gamePlayer6, Set.of("A4", "B4", "C4"));
            Salvo salvo12 = new Salvo(2, gamePlayer6, Set.of("A8", "E4", "E9"));
            Salvo salvo13 = new Salvo(1, gamePlayer7, Set.of("J4", "B2", "A4"));
            Salvo salvo14 = new Salvo(2, gamePlayer7, Set.of("J5", "C1", "C2"));
            Salvo salvo15 = new Salvo(1, gamePlayer8, Set.of("D3", "D1", "D2"));
            Salvo salvo16 = new Salvo(2, gamePlayer8, Set.of("D8", "F3", "F10"));
            Salvo salvo17 = new Salvo(1, gamePlayer9, Set.of("A10", "B4", "J4"));
            Salvo salvo18 = new Salvo(2, gamePlayer9, Set.of("B4", "A1", "A10"));
            Salvo salvo19 = new Salvo(1, gamePlayer10, Set.of("C2", "C6", "C10"));
            Salvo salvo20 = new Salvo(2, gamePlayer10, Set.of("A1", "C1", "F1"));
            Salvo salvo21 = new Salvo(1, gamePlayer11, Set.of("B10", "C10", "D10"));
            Salvo salvo22 = new Salvo(2, gamePlayer11, Set.of("A5", "B6", "C8"));



            Score score1 = new Score(LocalDateTime.now(),game1,player1, 0.0);
            Score score2 = new Score(LocalDateTime.now(),game1,player2, 1.0);
            Score score3 = new Score(LocalDateTime.now(),game2,player3, 0.5);
            Score score4 = new Score(LocalDateTime.now(),game2,player4, 0.5);
            Score score5 = new Score(LocalDateTime.now(),game3,player5, 1.0);
            Score score6 = new Score(LocalDateTime.now(),game3,player1, 0.0);
            Score score7 = new Score(LocalDateTime.now(),game4,player2, 0.5);
            Score score8 = new Score(LocalDateTime.now(),game4,player1, 0.5);
            Score score9 = new Score(LocalDateTime.now(),game5,player5, 1.0);
            Score score10 = new Score(LocalDateTime.now(),game5,player3, 0.0);




            repositoryPlayer.save(player1);
            repositoryPlayer.save(player2);
            repositoryPlayer.save(player3);
            repositoryPlayer.save(player4);
            repositoryPlayer.save(player5);

            repositoryGame.save(game1);
            repositoryGame.save(game2);
            repositoryGame.save(game3);
            repositoryGame.save(game4);
            repositoryGame.save(game5);
            repositoryGame.save(game6);

            repositoryGamePlayer.save(gamePlayer1);
            repositoryGamePlayer.save(gamePlayer2);
            repositoryGamePlayer.save(gamePlayer3);
            repositoryGamePlayer.save(gamePlayer4);
            repositoryGamePlayer.save(gamePlayer5);
            repositoryGamePlayer.save(gamePlayer6);
            repositoryGamePlayer.save(gamePlayer7);
            repositoryGamePlayer.save(gamePlayer8);
            repositoryGamePlayer.save(gamePlayer9);
            repositoryGamePlayer.save(gamePlayer10);
            repositoryGamePlayer.save(gamePlayer11);

            repositoryShip.save(ship1);
            repositoryShip.save(ship2);
            repositoryShip.save(ship3);
            repositoryShip.save(ship4);
            repositoryShip.save(ship5);
            repositoryShip.save(ship6);
            repositoryShip.save(ship7);
            repositoryShip.save(ship8);
            repositoryShip.save(ship9);
            repositoryShip.save(ship10);
            repositoryShip.save(ship11);
            repositoryShip.save(ship12);
            repositoryShip.save(ship13);
            repositoryShip.save(ship14);
            repositoryShip.save(ship15);
            repositoryShip.save(ship16);
            repositoryShip.save(ship17);
            repositoryShip.save(ship18);
            repositoryShip.save(ship19);
            repositoryShip.save(ship20);
            repositoryShip.save(ship21);
            repositoryShip.save(ship22);
            repositoryShip.save(ship23);
            repositoryShip.save(ship24);
            repositoryShip.save(ship25);

            repositorySalvo.save(salvo1);
            repositorySalvo.save(salvo2);
            repositorySalvo.save(salvo3);
            repositorySalvo.save(salvo4);
            repositorySalvo.save(salvo5);
            repositorySalvo.save(salvo6);
            repositorySalvo.save(salvo7);
            repositorySalvo.save(salvo8);
            repositorySalvo.save(salvo9);
            repositorySalvo.save(salvo10);
            repositorySalvo.save(salvo11);
            repositorySalvo.save(salvo12);
            repositorySalvo.save(salvo13);
            repositorySalvo.save(salvo14);
            repositorySalvo.save(salvo15);
            repositorySalvo.save(salvo16);
            repositorySalvo.save(salvo17);
            repositorySalvo.save(salvo18);
            repositorySalvo.save(salvo19);
            repositorySalvo.save(salvo20);
            repositorySalvo.save(salvo21);
            repositorySalvo.save(salvo22);

            respositoryScore.save(score1);
            respositoryScore.save(score2);
            respositoryScore.save(score3);
            respositoryScore.save(score4);
            respositoryScore.save(score5);
            respositoryScore.save(score6);
            respositoryScore.save(score7);
            respositoryScore.save(score8);
            respositoryScore.save(score9);
            respositoryScore.save(score10);


        };
    }
    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        PlayerRepository playerRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(inputName-> {
                Player player = playerRepository.findByUserName(inputName);
                if (player != null) {
                    return new User(player.getUserName(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + inputName);
                }
            });
        }
    }
}