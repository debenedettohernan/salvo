fetch('http://localhost:8080/api/games')
    .then(function(respuesta) {
        return respuesta.json();
    })
    .then(function(data) {

        app.games = data;
        players();
        totalScore();
        totalWins();
        totalTies()
        totalLosses()
        tablaScore();
    })

var app = new Vue({
    el: '#app',
    data: {
        games: [],
        scorePlayers: [],
    }
})

function players() {
    var players = [];
    app.games.forEach(game => {
        game.gamePlayers.forEach(gamePlayer => {

            if (!players.includes(gamePlayer.player.email)) {
                players.push(gamePlayer.player.email)
            }

        })
    });
    return players;

};


function totalScore(email) {
    var total = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {
            if (email == gamePlayer.player.email) {
                total += gamePlayer.score
            }

        }))
    return total;

};

function totalWins(email) {
    var wins = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                wins += gamePlayer.score == 1
            }
        }))
    return wins;

};

function totalTies(email) {
    var ties = 0.0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                ties += gamePlayer.score == 0.5
            }
        }))
    return ties;
};

function totalLosses(email) {
    var losses = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                losses += gamePlayer.score == 0
            }
        }))
    return losses;
};


function tablaScore() {
    var scorePlayer = []
    var totalPlayers = players()

    totalPlayers.forEach(player => {
        var total = totalScore(player)
        var win = totalWins(player)
        var ties = totalTies(player)
        var losses = totalLosses(player)

        scorePlayer = {
            email: player,
            totalScore: total,
            winScore: win,
            tiesScore: ties,
            lossesScore: losses

        }

        app.scorePlayers.push(scorePlayer)
    })

};