package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;



@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repositoryPlayer, GameRepository repositoryGame, GamePlayerRepository repositoryGamePlayer, ShipRepository repositoryShip) {
		return (args) -> {

			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now());
			Game game3 = new Game(LocalDateTime.now());

			Player player1 = new Player("t.almeida@ctu.gov");
			Player player2 = new Player("j.bauer@ctu.gov");
			Player player3 = new Player("c.obrian@ctu.gov");
			Player player4 = new Player("kim_bauer@gmail.com");
			Player player5 = new Player("hernan.debenedetto@gmail.com");

			GamePlayer gamePlayer1 = new GamePlayer(LocalDateTime.now(),game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer(LocalDateTime.now(),game1, player2);
			GamePlayer gamePlayer3 = new GamePlayer(LocalDateTime.now(),game2, player3);
			GamePlayer gamePlayer4 = new GamePlayer(LocalDateTime.now(),game2, player4);
			GamePlayer gamePlayer5 = new GamePlayer(LocalDateTime.now(),game3, player5);

			Ship ship1 = new Ship("Carrier",gamePlayer1, List.of("A2","B2","C2","D2","E2"));
			Ship ship2 = new Ship("Battleship",gamePlayer1,List.of("A1","B1","C1","D1"));
			Ship ship3 = new Ship("Submarine",gamePlayer1, List.of("F4","F5","F6"));
			Ship ship4 = new Ship("Destroyer", gamePlayer1,List.of("A3","A4","A5"));
			Ship ship5 = new Ship("Patrol Boat", gamePlayer1,List.of("C3","C4"));

			Ship ship6 = new Ship("Carrier",gamePlayer2, List.of("A1","B1","C1","D1","E1"));
			Ship ship7 = new Ship("Battleship",gamePlayer2,List.of("A2","B2","C2","D2"));
			Ship ship8 = new Ship("Submarine",gamePlayer2, List.of("F5","F6","F7"));
			Ship ship9 = new Ship("Destroyer", gamePlayer2,List.of("A4","A5","A6"));
			Ship ship10 = new Ship("Patrol Boat", gamePlayer2,List.of("C4","C5"));

			Ship ship11 = new Ship("Carrier",gamePlayer3, List.of("A3","B3","C3","D3","E3"));
			Ship ship12 = new Ship("Battleship",gamePlayer3,List.of("A4","B4","C4","D4"));
			Ship ship13 = new Ship("Submarine",gamePlayer3, List.of("F6","F7","F8"));
			Ship ship14 = new Ship("Destroyer", gamePlayer3,List.of("A5","A6","A7"));
			Ship ship15 = new Ship("Patrol Boat", gamePlayer3,List.of("C5","C6"));

			Ship ship16 = new Ship("Carrier",gamePlayer4, List.of("A4","B4","C4","D4","E4"));
			Ship ship17 = new Ship("Battleship",gamePlayer4,List.of("A5","B5","C5","D5"));
			Ship ship18 = new Ship("Submarine",gamePlayer4, List.of("F7","F8","F9"));
			Ship ship19 = new Ship("Destroyer", gamePlayer4,List.of("A6","A7","A8"));
			Ship ship20 = new Ship("Patrol Boat", gamePlayer4,List.of("C6","C7"));

			Ship ship21 = new Ship("Carrier",gamePlayer5, List.of("A5","B5","C5","D5","E5"));
			Ship ship22 = new Ship("Battleship",gamePlayer5,List.of("A6","B6","C6","D6"));
			Ship ship23 = new Ship("Submarine",gamePlayer5, List.of("F8","F9","F10"));
			Ship ship24 = new Ship("Destroyer", gamePlayer5,List.of("A7","A8","A9"));
			Ship ship25 = new Ship("Patrol Boat", gamePlayer5,List.of("C7","C8"));

			repositoryPlayer.save(player1);
			repositoryPlayer.save(player2);
			repositoryPlayer.save(player3);
			repositoryPlayer.save(player4);
			repositoryPlayer.save(player5);

			repositoryGame.save(game1);
			repositoryGame.save(game2);
			repositoryGame.save(game3);

			repositoryGamePlayer.save(gamePlayer1);
			repositoryGamePlayer.save(gamePlayer2);
			repositoryGamePlayer.save(gamePlayer3);
			repositoryGamePlayer.save(gamePlayer4);
			repositoryGamePlayer.save(gamePlayer5);

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



		};
	}

}