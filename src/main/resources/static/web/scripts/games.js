var app = new Vue({
    el: '#app',
    data: {
        games: [],
        scorePlayers: [],
        username: "",
        password: "",
        player: null,
    },
    methods: {
        getData: function() {
            fetch('api/games')
                .then(function(respuesta) {
                    return respuesta.json();
                })
                .then((data) => {
                    this.player = data.player
                    this.games = data.games;
                    this.players();
                    this.tablaScore();
                })
        },

        players: function() {
            var players = [];
            app.games.forEach(game => {
                game.gamePlayers.forEach(gamePlayer => {
                    if (!players.includes(gamePlayer.player.name)) {
                        players.push(gamePlayer.player.name)
                    }
                })
            });
            return players;

        },
        totalScore: function(name) {
            var total = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {
                    if (name == gamePlayer.player.name) {
                        total += gamePlayer.score
                    }

                }))
            return total;
        },
        totalWins: function(name) {
            var wins = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {
                    if (name == gamePlayer.player.name) {
                        wins += gamePlayer.score == 1
                    }
                }))
            return wins;
        },
        totalTies: function(name) {
            var ties = 0.0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {

                    if (name == gamePlayer.player.name) {
                        ties += gamePlayer.score == 0.5
                    }
                }))
            return ties;
        },
        totalLosses: function(name) {
            var losses = 0
            app.games.forEach(game =>
                game.gamePlayers.forEach(gamePlayer => {

                    if (name == gamePlayer.player.name) {
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
                    name: player,
                    totalScore: total,
                    winScore: win,
                    tiesScore: ties,
                    lossesScore: losses
                }
                app.scorePlayers.push(scorePlayer)
            })
        },
        logIn: function() {
            $.post("/api/login", {
                    username: app.username,
                    password: app.password
                })
                .done(function() { location.reload() })
                .fail(function() { alert("Check your data and insert it well") })
        },
        logOut: function() {
            $.post("/api/logout")
                .done(function() { location.reload() })
                .fail(function() { alert("You can't log out") })
        },
        sigIn: function() {
            $.post("/api/players", {
                    username: app.username,
                    password: app.password
                })
                .done(function() { app.logIn() })
                .fail(function() { alert("User is already in use") })
        },
        createGame: function() {
            $.post("/api/games")
                .done(function(data) { location.href = "http://localhost:8080/web/game.html?gp=" + data.gpId })
                .fail(function() { alert("Can't create the game") })
        },
        joinGame: function(gameId) {
            $.post("/api/games/" + gameId + "/players")
                .done(function(joinGpIdPlayer) { location.href = "http://localhost:8080/web/game.html?gp=" + joinGpIdPlayer.gpId })
                .fail(function() { alert("no podes ingresar al juego") })
        },

    },
    mounted: function() {
        this.getData();
    }
})