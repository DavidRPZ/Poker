(function(window, document, JSON) {
	'use strict';

	var url = 'ws://' + window.location.host + '/Poker/sala',
		ws = new WebSocket(url),
		mensajes = document.getElementById('conversacion'),
		boton = document.getElementById('btnEnviar'),
		nombre = document.getElementById('usuario'),
		mensaje = document.getElementById('mensaje'),
		pruebas = document.getElementById('pruebas'),
		pruebaJuego = document.getElementById('pruebaJuego'),
		numJugadores,
		rondaEmpezada = false;

	ws.onopen = onOpen;
	ws.onclose = onClose;
	ws.onmessage = onMessage;
	boton.addEventListener('click', enviar);

	pruebaJuego.addEventListener('click', juego);

	function onOpen() {
		console.log('Conectado a WebSocket');
		nuevoJugador();
	}

	function onClose() {
		console.log('Desconectado de WebSocket');
	}

	function enviar() {
		var msg = {
			accion: "chat",
			id_usuario: "1",
			id_sala: "1",
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
				var msg = obj.empezarRonda;
				pruebas.innerHTML += "Empezar ronda: " + msg + "<br>Numero de jugadores: " + numJugadores;
				rondaEmpezada = true;

				/*//cambiar esto, es para pruebas:
				id_usuario = document.getElementById('apuesta');
				
				var msg = {
					accion: "juego",
					id_usuario: id_usuario.value,
					id_sala: "1",
					nombre: nombre.value,
					mensaje: mensaje.value
				};
				ws.send(JSON.stringify(msg));*/
				break;
			case "juego":
				var msg1 = obj.carta1,
					msg2 = obj.carta2;
				pruebas.innerHTML += "<img src='img/baraja/" + msg1 + ".svg' alt='Carta' width='50px'><br>";
				pruebas.innerHTML += "<img src='img/baraja/" + msg2 + ".svg' alt='Carta' width='50px'><br>";
				break;
		}
	}

	function juego() {
		//cambiar esto, es para pruebas:
		var id_usuario = document.getElementById('apuesta');

		/*var msg = {
			accion: "juego",
			id_usuario: id_usuario.value,
			id_sala: "1",
			nombre: nombre.value,
			mensaje: mensaje.value
		};
		ws.send(JSON.stringify(msg));*/

		$.post("ServletControlador", {
			accion: "juego",
			id_usuario: id_usuario.value,
			id_sala: "1",
		}, function(responseText) { //La respuesta es el número de jugadores
			var cartas = responseText.split("/");
			var carta1 = cartas[0];
			var carta2 = cartas[1];
			pruebas.innerHTML = "<img src='img/baraja/" + carta1 + ".svg' alt='Carta' width='50px'><br>";
			pruebas.innerHTML += "<img src='img/baraja/" + carta2 + ".svg' alt='Carta' width='50px'><br>";
		});
	}

	function nuevoJugador() {
		var accion = "nuevoJugador";
		var id_usuario = "3";
		var id_sala = "3";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: id_usuario,
			id_sala: id_sala
		}, function(responseText) { //La respuesta es el número de jugadores
			numJugadores = responseText;
			comprobarNumJugadores();
		});
	}

	function comprobarNumJugadores() {
		if (numJugadores > 1) {
			//$('#div1').html('Numero de jugadores: ' + numJugadores);
			var msg = {
				accion: "empezarRonda"
			};
			ws.send(JSON.stringify(msg));

		} else {
			$('#div1').html("Esperando a mas jugadores...");
		}
	}

	function fold() {
		var apuesta = "100";
		var accion = "fold";
		var id_usuario = "1";
		var id_sala = "1";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: id_usuario,
			id_sala: id_sala
		}, function(responseText) {
			$('#div2').html(responseText);
		});
	}

	function call() {
		var apuesta = "200";
		var accion = "call";
		var id_usuario = "2";
		var id_sala = "2";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: id_usuario,
			id_sala: id_sala
		}, function(responseText) {
			$('#div3').html(responseText);
		});
	}

	function raise() {
		var apuesta = "300";
		var accion = "raise";
		var id_usuario = "3";
		var id_sala = "3";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: id_usuario,
			id_sala: id_sala
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