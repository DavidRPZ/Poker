(function(window, document, JSON) {
	'use strict';

	var url = 'ws://' + window.location.host + '/Poker/sala',
		ws = new WebSocket(url),
		mensajes = document.getElementById('conversacion'),
		boton = document.getElementById('btnEnviar'),
		nombre = document.getElementById('usuario'),
		mensaje = document.getElementById('mensaje'),
		pruebas = document.getElementById('pruebas'),
		flop = document.getElementById('flop'),
		numJugadores,
		rondaEmpezada = false,
		esHost = "false",
		idJugadores = [],
		nombresJugadores = [];

	ws.onopen = onOpen;
	ws.onclose = onClose;
	ws.onmessage = onMessage;
	boton.addEventListener('click', enviar);

	function onOpen() {
		console.log('Conectado a WebSocket');
		//nuevoJugador();
	}

	function onClose() {
		console.log('Desconectado de WebSocket');
	}

	function enviar() {
		var msg = {
			accion: "chat",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
			nombre: nombre.value,
			mensaje: mensaje.value
		};

		ws.send(JSON.stringify(msg));
	}

	function onMessage(evt) {
		var obj = JSON.parse(evt.data);
		switch (obj.accion) {
			case "chat":
				var d = new Date(),
					msg1 = '<b style="color:red;">' + d.getHours() + ':' + d.getMinutes() + '</b> - ',
					msg2 = '<b style="color:green;">' + obj.nombre + ': </b>',
					msg3 = '<b style="color:blue;">' + obj.mensaje + '</b>';
				mensajes.innerHTML += msg1 + msg2 + msg3 + '<br>';
				break;

			case "empezarRonda":
				//var msg = obj.empezarRonda;
				//pruebas.innerHTML += "Empezar ronda: " + msg + "<br>Numero de jugadores: " + numJugadores;
				//sleep(40);
				host();
				break;
			case "flop":
				var msg1 = obj.flop1,
					msg2 = obj.flop2,
					msg3 = obj.flop3;
				flop.innerHTML += "<img src='img/baraja/" + msg1 + ".svg' alt='Carta' width='50px'><br>";
				flop.innerHTML += "<img src='img/baraja/" + msg2 + ".svg' alt='Carta' width='50px'><br>";
				flop.innerHTML += "<img src='img/baraja/" + msg3 + ".svg' alt='Carta' width='50px'><br>";
				/*sleep(50);
				juego();*/

				break;
			case "todosJugadores":
				//var numJug = Object.keys(obj).length - 1;
				idJugadores.push(obj.J1);
				idJugadores.push(obj.J2);
				nombresJugadores.push(obj.nombre1);
				nombresJugadores.push(obj.nombre2);
				
				document.getElementById("J1").innerHTML += "id: " + obj.J1 + ", nombre: " + obj.nombre1 + "<br>";
				document.getElementById("J2").innerHTML += "id: " + obj.J2 + ", nombre: " + obj.nombre2 + "<br>";
				if (obj.J3 != undefined) {
					document.getElementById("J3").innerHTML += "id: " + obj.J3 + ", nombre: " + obj.nombre3 + "<br>";
					idJugadores.push(obj.J3);
					nombresJugadores.push(obj.nombre2);
				}
				if (obj.J4 != undefined) {
					document.getElementById("J4").innerHTML += "id: " + obj.J4 + ", nombre: " + obj.nombre4 + "<br>";
					idJugadores.push(obj.J4);
					nombresJugadores.push(obj.nombre4);
				}
				if (obj.J5 != undefined) {
					document.getElementById("J5").innerHTML += "id: " + obj.J5 + ", nombre: " + obj.nombre5 + "<br>";
					idJugadores.push(obj.J5);
					nombresJugadores.push(obj.nombre5);
				}
				if (obj.J6 != undefined) {
					document.getElementById("J6").innerHTML += "id: " + obj.J6 + ", nombre: " + obj.nombre6 + "<br>";
					idJugadores.push(obj.J6);
					nombresJugadores.push(obj.nombre6);
				}
				if (obj.J7 != undefined) {
					document.getElementById("J7").innerHTML += "id: " + obj.J7 + ", nombre: " + obj.nombre7 + "<br>";
					idJugadores.push(obj.J7);
					nombresJugadores.push(obj.nombre7);
				}
				if (obj.J8 != undefined) {
					document.getElementById("J8").innerHTML += "id: " + obj.J8 + ", nombre: " + obj.nombre8 + "<br>";
					idJugadores.push(obj.J8);
					nombresJugadores.push(obj.nombre8);
				}
				break;
		}


		function rondaEmpezada(empezar) {
			var accion = "rondaEmpezada";
			var rondaEmpezada = empezar;
			$.post("ServletControlador", {
				accion: accion,
				id_usuario: sessionStorage.getItem("id_usuario"),
				id_sala: sessionStorage.getItem("id_sala"),
				rondaEmpezada: rondaEmpezada
			});
		}
	}

	function juego() {
		$.post("ServletControlador", {
			accion: "juego",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala")
		}, function(responseText) {
			var cartas = responseText.split("@");
			pruebas.innerHTML += "<img src='img/baraja/" + cartas[0] + ".svg' alt='Carta' width='50px'><br>";
			pruebas.innerHTML += "<img src='img/baraja/" + cartas[1] + ".svg' alt='Carta' width='50px'><br>";
		});
	}

	function sleep(milliseconds) {
		console.log("Sleeping...");
		var start = new Date().getTime();
		for (var i = 0; i < 1e7; i++) {
			if ((new Date().getTime() - start) > milliseconds) {
				break;
			}
		}
	}

	function host() {
		var accion = "host";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala")
		}, function(responseText) { //La respuesta es el número de jugadores
			if (responseText == "true") {
				esHost = "true";
				mensajes.innerHTML = responseText;
				//sleep(40);
				/*if (esHost == "true") {
					var msg = {
						accion: "flop",
						id_sala: sessionStorage.getItem("id_sala"),
					};

					ws.send(JSON.stringify(msg));
				}*/
				if (esHost == "true") {
					var msg = {
						accion: "todosJugadores",
						id_sala: sessionStorage.getItem("id_sala"),
					};

					ws.send(JSON.stringify(msg));
				}
				else {
					mensajes.innerHTML = "No es el host";
				}
			}
			sleep(50);
			juego();
		});
	}

	function crearJugador() {
		var accion = "crearJugador";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) { //La respuesta es el número de jugadores
			numJugadores = responseText;
			comprobarNumJugadores();
		});
	}

	function nuevoJugador() {
		var accion = "nuevoJugador";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) { //La respuesta es el número de jugadores
			numJugadores = responseText;
			comprobarNumJugadores();
		});
	}

	function comprobarNumJugadores() {
		var accion = "comprobarRondaEmpezada";
		$.post("ServletControlador", {
			accion: accion,
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) {
			rondaEmpezada = responseText;
			if (numJugadores > 1 && rondaEmpezada == "false") {
				//$('#div1').html('Numero de jugadores: ' + numJugadores);
				var msg = {
					accion: "empezarRonda",
					id_usuario: sessionStorage.getItem("id_usuario"),
					id_sala: sessionStorage.getItem("id_sala"),
				};
				ws.send(JSON.stringify(msg));

			} else {
				$('#div1').html("Esperando mas jugadores...");
			}
		});

	}

	function comprobarRondaEmpezada() {
		var accion = "comprobarRondaEmpezada";
		$.post("ServletControlador", {
			accion: accion,
			id_sala: sessionStorage.getItem("id_sala")
		}, function(responseText) {
			rondaEmpezada = responseText
		});
		return rondaEmpezada;
	}

	function fold() {
		var apuesta = document.getElementById("apuesta").value;
		var accion = "fold";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div2').html(responseText);
		});
	}

	function call() {
		var apuesta = "200";
		var accion = "call";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div3').html(responseText);
		});
	}

	function raise() {
		var apuesta = "300";
		var accion = "raise";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div4').html(responseText);
		});
	}

	$("#fold").click(function() {
		fold();
	});
	$("#call").click(function() {
		call();
	});
	$("#raise").click(function() {
		raise();
	});
	$("#empezar").click(function() {
		if (window.sessionStorage) {
			//sessionStorage.setItem("id_usuario", "1");
			sessionStorage.setItem("id_usuario", document.getElementById('id_usuario').value);
			sessionStorage.setItem("id_sala", "1");
		}
		nuevoJugador();
	});

	//desactivado de momento
	/*var tiempo = 20;

	setInterval(function() {
		if (tiempo > -1) {
			$('#div5').html("Tiempo restante: " + tiempo);
			tiempo--;
		} else {
			fold();
			tiempo = 20;
		}
	}, 1000);*/
})(window, document, JSON);