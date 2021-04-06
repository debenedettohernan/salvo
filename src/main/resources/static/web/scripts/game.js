const urlParams = new URLSearchParams(window.location.search);
const gameViewParam = urlParams.get('gp');

fetch('http://localhost:8080/api/game_view/' + gameViewParam)
    .then(function(respuesta) {
        return respuesta.json();
    })
    .then(function(data) {

        app.games = data;
        asignarUbicacion();
        pantallaJugador();

    })

var app = new Vue({
    el: '#app',
    data: {
        games: [],
        letras: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        numeros: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        player: {
            player1: { email: " " },
            player2: { email: " " },
        }

    }
})

function asignarUbicacion() {

    var nave = app.games.ships

    for (var i = 0; i < nave.length; i++) {
        for (var j = 0; j < nave[i].ubication.length; j++) {
            document.getElementById(nave[i].ubication[j]).className = "color" + i;
        }
    }
};


function pantallaJugador() {
    for (var i = 0; i < app.games.gamePlayers.length; i++) {
        if (app.games.gamePlayers[i].player.id == gameViewParam) {
            app.player.player1.email = app.games.gamePlayers[i].player.email
        } else {
            app.player.player2.email = app.games.gamePlayers[i].player.email
        }
    }
}