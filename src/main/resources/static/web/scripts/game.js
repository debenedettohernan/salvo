var app = new Vue({
    el: '#app',
    data: {
        games: [],
        letras: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        numeros: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        r: ["r"],
        player1: {},
        player2: {},
        shipWithLength: [
            { typeShip: "Destroyer", size: 3, },
            { typeShip: "Carrier", size: 5, },
            { typeShip: "Submarine", size: 3, },
            { typeShip: "Battleship", size: 4, },
            { typeShip: "Patrol Boat", size: 2, }
        ],
        selectOrientation: "vertical",
        shipSelected: { typeShip: "Destroyer", size: 3, },
        allLocations: [],
        shipClient: [],
        allSalvoes: [],
        salvoesClient: {
            turn: 1,
            salvoLocation: []
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
                    document.getElementById(disparosPlayer1[i].locations[j] + "r").className = "fire1";
                    document.getElementById(disparosPlayer1[i].locations[j] + "r").innerHTML = disparosPlayer1[i].turn
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
                            document.getElementById(disparosPlayer2[i].locations[j]).className = "fire0";
                            document.getElementById(disparosPlayer2[i].locations[j]).innerHTML = disparosPlayer2[i].turn;
                        }
                    }
                }
            }
        },
        postShips: function() {
            $.post({
                    url: "/api/games/players/" + gameViewParam + "/ships",
                    data: JSON.stringify(app.shipClient),
                    dataType: "text",
                    contentType: "application/json"
                })
                .done(function() {
                    alert("ships added"), location.reload();
                })
                .fail(function(jqXHR, textStatus, httpError) {
                    alert("Failed to add pet: " + textStatus + " " + httpError);
                })
        },
        postSalvo: function() {
            $.post({
                    url: "/api/games/players/" + gameViewParam + "/salvoes",
                    data: JSON.stringify(app.salvoesClient),
                    dataType: "text",
                    contentType: "application/json"
                })
                .done(function() {
                    alert("fire!"), location.reload();
                })
                .fail(function(jqXHR, textStatus, httpError) {

                    alert(textStatus + ": todavia no es tu turno pillin" + httpError);
                })
        },

        sendShip: function(letra, numero) {
            var shipClient = []
            var totalShipClient = []
            if (app.games.ships.length < 5) {

                if (app.selectOrientation == "vertical") {
                    for (j = 0; j < app.shipSelected.size; j++) {
                        for (i = 0; i < app.letras.length; i++) {
                            if (app.letras[i] == letra) {
                                shipClient.push(app.letras[i + j] + numero)
                                totalShipClient = {
                                    typeShips: app.shipSelected.typeShip,
                                    location: shipClient
                                }
                            }
                        }
                    }
                    if (!app.allLocations.some(x => totalShipClient.location.includes(x)) && (app.letras.indexOf(letra) + app.shipSelected.size) < 11 && !app.shipClient.some(type => type.typeShips == app.shipSelected.typeShip)) {
                        for (i = 0; i < totalShipClient.location.length; i++) {
                            sizeShip = totalShipClient.location[i]
                            document.getElementById(sizeShip).className = "color";
                            app.allLocations.push(sizeShip)
                        }
                        app.shipClient.push(totalShipClient)
                    }
                } else {
                    for (j = 0; j < app.shipSelected.size; j++) {
                        shipClient.push(letra + (parseInt(numero) + j))
                        totalShipClient = {
                            typeShips: app.shipSelected.typeShip,
                            location: shipClient
                        }

                    }
                    if (!app.allLocations.some(x => totalShipClient.location.includes(x)) && (Number(numero) + Number(app.shipSelected.size)) <= 11 && !app.shipClient.some(type => type.typeShips == app.shipSelected.typeShip)) {
                        for (i = 0; i < totalShipClient.location.length; i++) {
                            sizeShip = totalShipClient.location[i]
                            document.getElementById(sizeShip).className = "color";
                            app.allLocations.push(sizeShip)
                        }

                        console.log(sizeShip)
                        app.shipClient.push(totalShipClient)
                    }

                }
            }

        },
        removeShip: function() {
            for (i = 0; i < app.shipClient.length; i++) {
                for (j = 0; j < app.shipClient[i].location.length; j++) {
                    document.getElementById(app.shipClient[i].location[j]).className = "";
                }
            }
            app.allLocations = []
            app.shipClient = []
        },
        sendSalvo: function(letra, numero) {
            var turn = app.games.salvo.length + 1
            var salvoLocation = letra + numero

            if (!app.allSalvoes.includes(salvoLocation) && app.salvoesClient.salvoLocation.length <= 4 && !app.games.salvo.some(x => x.locations.includes(salvoLocation))) {
                document.getElementById(salvoLocation + "r").className = "fire1";
                app.salvoesClient.salvoLocation.push(salvoLocation)
                app.salvoesClient.turn = turn
                app.allSalvoes.push(salvoLocation)
                console.log(app.player1.id)
            }
        },
        removeSalvo: function() {
            for (i = 0; i < app.salvoesClient.salvoLocation.length; i++) {
                document.getElementById(app.salvoesClient.salvoLocation[i] + "r").className = "";
            }
            app.allSalvoes = []
            app.salvoesClient.salvoLocation = []

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

    })