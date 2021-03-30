package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repositoryPlayer, GameRepository repositoryGame, GamePlayerRepository repositoryGamePlayer) {
		return (args) -> {

			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now());
			Game game3 = new Game(LocalDateTime.now());

			Player player1 = new Player("t.almeida@ctu.gov");
			Player player2 = new Player("j.bauer@ctu.gov");
			Player player3 = new Player("c.obrian@ctu.gov");
			Player player4 = new Player("kim_bauer@gmail.com");
			Player player5 = new Player("hernan.debenedetto@gmail.com");

			repositoryPlayer.save(player1);
			repositoryPlayer.save(player2);
			repositoryPlayer.save(player3);
			repositoryPlayer.save(player4);
			repositoryPlayer.save(player5);

			repositoryGame.save(game1);
			repositoryGame.save(game2);
			repositoryGame.save(game3);

			repositoryGamePlayer.save(new GamePlayer(LocalDateTime.now(),game1, player1));
			repositoryGamePlayer.save(new GamePlayer(LocalDateTime.now(),game1, player4));
			repositoryGamePlayer.save(new GamePlayer(LocalDateTime.now(),game2, player2));
			repositoryGamePlayer.save(new GamePlayer(LocalDateTime.now(),game2, player5));
			repositoryGamePlayer.save(new GamePlayer(LocalDateTime.now(),game3, player3));



		};
	}

}