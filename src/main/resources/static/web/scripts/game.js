var app = new Vue({
    el: '#app',
    data: {
        games: [],
        letras: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        numeros: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        r: ["r"],
        player1: {},
        player2: {},
        shipsGp: [],
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
                if (app.games.gamePlayers[i].id == gameViewParam) {
                    app.player1 = app.games.gamePlayers[i].player
                } else {
                    app.player2 = app.games.gamePlayers[i].player
                }
            }
        },
        ubicacionDisparos: function() {

            var disparosPlayer1 = app.games.salvo.filter(slv => slv.player == app.player1.id)
            var disparosPlayer2 = app.games.salvo.filter(slv => slv.player == app.player2.id)

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
            var disparosPlayer2 = app.games.salvo.filter(slv => slv.player == app.player2.id)

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
        },
        jsonWithShips: function() {
            for (i = 0; i < app.games.ships.length; i++) {
                app.shipsGp.push({
                    "type": app.games.ships[i].type,
                    "locations": app.games.ships[i].locations

                })
            }

        },
        postShips: function() {
            $.post({
                    url: "/api/games/players/" + gameViewParam + "/ships",
                    data: JSON.stringify(
                        [
                            { "typeShips": "Destroyer", "location": ["A1", "B1", "C1"] },
                            { "typeShips": "Carrier", "location": ["I5", "I6"] },
                            { "typeShips": "Submarine", "location": ["F8", "F9", "F10"] },
                            { "typeShips": "Battleship", "location": ["A6", "B6", "C6", "D6"] },
                            { "typeShips": "Patrol Boat", "location": ["H5", "H6"] }
                        ]
                    ),
                    dataType: "text",
                    contentType: "application/json"
                })
                .done(function() {
                    alert("ships added"), location.reload();
                })
                .fail(function(jqXHR, status, httpError) {
                    alert("Failed to add pet: " + textStatus + " " + httpError);
                })
        }
    }
})


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
        app.jsonWithShips();

    })