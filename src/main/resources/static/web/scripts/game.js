var app = new Vue({
    el: '#app',
    data: {
        games: [],
        letras: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        numeros: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        r: ["r"],
        player: {
            player1: {},
            player2: {},
        }

    },
    methods: {
        asignarUbicacion: function() {

            var nave = app.games.ships

            for (var i = 0; i < nave.length; i++) {
                for (var j = 0; j < nave[i].locations.length; j++) {
                    document.getElementById(nave[i].locations[j]).className = "color";
                }
            }
        },
        pantallaJugador: function() {
            for (var i = 0; i < app.games.gamePlayers.length; i++) {
                if (app.games.gamePlayers[i].player.id == gameViewParam) {
                    app.player.player1 = app.games.gamePlayers[i].player
                } else {
                    app.player.player2 = app.games.gamePlayers[i].player
                }
            }
        },

        ubicacionDisparos: function() {

            var disparosPlayer1 = app.games.salvo.filter(slv => slv.player == app.player.player1.id)
            var disparosPlayer2 = app.games.salvo.filter(slv => slv.player == app.player.player2.id)

            for (var i = 0; i < disparosPlayer1.length; i++) {
                for (var j = 0; j < disparosPlayer1[i].locations.length; j++) {
                    document.getElementById(disparosPlayer1[i].locations[j] + 'r').className = "fire1";
                    document.getElementById(disparosPlayer1[i].locations[j] + 'r').innerHTML = disparosPlayer1[i].turn
                }
            }
            for (var i = 0; i < disparosPlayer2.length; i++) {
                for (var j = 0; j < disparosPlayer2[i].locations.length; j++) {

                    document.getElementById(disparosPlayer2[i].locations[j]).className = "fire1";
                    document.getElementById(disparosPlayer2[i].locations[j]).innerHTML = disparosPlayer2[i].turn
                }
            }
        },

        disparosAcertados: function() {
            var disparosPlayer2 = app.games.salvo.filter(slv => slv.player == app.player.player2.id)

            for (i = 0; i < disparosPlayer2.length; i++) {
                for (j = 0; j < disparosPlayer2[i].locations.length; j++) {
                    for (k = 0; k < app.games.ships.length; k++) {

                        if (app.games.ships[k].locations.includes(disparosPlayer2[i].locations[j])) {
                            document.getElementById(disparosPlayer2[i].locations[j]).className = "fire0 ";
                            document.getElementById(disparosPlayer2[i].locations[j]).innerHTML = disparosPlayer2[i].turn;
                        }
                    }
                }
            }

        }

    }
})

// ARREGLAR TODO PARA QUE LAS FUNCIONES QUEDEN EN METODOS Y CORREJIR COSAS PARA QUE LEA BIEN EL VUE 

const urlParams = new URLSearchParams(window.location.search);
const gameViewParam = urlParams.get('gp');

fetch('http://localhost:8080/api/game_view/' + gameViewParam)
    .then(function(respuesta) {
        return respuesta.json();
    })
    .then(function(data) {

        app.games = data;
        app.asignarUbicacion();
        app.pantallaJugador();
        app.ubicacionDisparos();
        app.disparosAcertados();

    })