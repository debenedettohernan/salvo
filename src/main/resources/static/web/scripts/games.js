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
    })

var app = new Vue({
    el: '#app',
    data: {
        games: []
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

        return players;
    });
    console.log(players)
}


function totalScore(email) {
    var total = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {
            if (email == gamePlayer.player.email) {
                total += gamePlayer.score
            }

        }))
}

function totalWins(email) {
    var wins = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                wins += gamePlayer.score == 1
            }
        }))
    console.log(wins)
}

function totalTies(email) {
    var ties = 0.0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                ties += gamePlayer.score == 0.5
            }
        }))
    console.log(ties)
}

function totalLosses(email) {
    var losses = 0
    app.games.forEach(game =>
        game.gamePlayers.forEach(gamePlayer => {

            if (email == gamePlayer.player.email) {
                losses += gamePlayer.score == 0
            }
        }))
    console.log(losses)
}