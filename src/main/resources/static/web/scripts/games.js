var app = new Vue({
    el: '#app',
    data: {
        games: [],
        scorePlayers: [],
    },
    methods: {

        players: function() {
            var players = [];
            app.games.forEach(game => {
                game.gamePlayers.forEach(gamePlayer => {

                    if (!players.includes(gamePlayer.player.email)) {
                        players.push(gamePlayer.player.email)
                    }

                })
            });
            return players;

        },


        totalScore: function(email) {
            var total = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {
                    if (email == gamePlayer.player.email) {
                        total += gamePlayer.score
                    }

                }))
            return total;

        },

        totalWins: function(email) {
            var wins = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {

                    if (email == gamePlayer.player.email) {
                        wins += gamePlayer.score == 1
                    }
                }))
            return wins;

        },

        totalTies: function(email) {
            var ties = 0.0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {

                    if (email == gamePlayer.player.email) {
                        ties += gamePlayer.score == 0.5
                    }
                }))
            return ties;
        },

        totalLosses: function(email) {
            var losses = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {

                    if (email == gamePlayer.player.email) {
                        losses += gamePlayer.score == 0
                    }
                }))
            return losses;
        },


        tablaScore: function() {
            var scorePlayer = []
            var totalPlayers = app.players()

            totalPlayers.forEach(player => {
                var total = app.totalScore(player)
                var win = app.totalWins(player)
                var ties = app.totalTies(player)
                var losses = app.totalLosses(player)

                scorePlayer = {
                    email: player,
                    totalScore: total,
                    winScore: win,
                    tiesScore: ties,
                    lossesScore: losses

                }

                app.scorePlayers.push(scorePlayer)
            })

        }

    }
})
fetch('http://localhost:8080/api/games')
    .then(function(respuesta) {
        return respuesta.json();
    })
    .then(function(data) {

        app.games = data.games;
        app.players();
        app.tablaScore();
    })