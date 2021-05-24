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
		idUsuarios = [],
		nombresJugadores = [],
		fichasJugadores = [],
		empieza = 0,
		turno = 0,
		foldBtn = document.getElementById('fold'),
		callBtn = document.getElementById('call'),
		raiseBtn = document.getElementById('raise'),
		esFlop = false,
		esTurn = false,
		esRiver = false,
		contTurnos = 0,
		fichas = 0,
		totalApostado = 0,
		ciega_pequena = 10,
		ciega_grande = 20,
		bote = 0,
		apuestaMasGrande = ciega_grande,
		posicion;

	ws.onopen = onOpen;
	ws.onclose = onClose;
	ws.onmessage = onMessage;
	boton.addEventListener('click', enviar);

	function onOpen() {
		console.log('Conectado a WebSocket');
		foldBtn.disabled = true;
		callBtn.disabled = true;
		raiseBtn.disabled = true;
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
				sleep(50);
				var msg1 = obj.flop1,
					msg2 = obj.flop2,
					msg3 = obj.flop3;
				flop.innerHTML += "<img src='img/baraja/" + msg1 + ".svg' alt='Carta' width='50px'><br>";
				flop.innerHTML += "<img src='img/baraja/" + msg2 + ".svg' alt='Carta' width='50px'><br>";
				flop.innerHTML += "<img src='img/baraja/" + msg3 + ".svg' alt='Carta' width='50px'><br>";
				/*sleep(50);
				juego();*/

				break;
			case "turn":
				sleep(50);
				var msg = obj.turn;
				flop.innerHTML += "<img src='img/baraja/" + msg + ".svg' alt='Carta' width='50px'><br>";
				/*sleep(50);
				juego();*/

				break;
			case "river":
				sleep(50);
				var msg = obj.river
				flop.innerHTML += "<img src='img/baraja/" + msg + ".svg' alt='Carta' width='50px'><br>";
				/*sleep(50);
				juego();*/

				break;
			case "todosUsuarios":
				//var numJug = Object.keys(obj).length - 1;
				//idUsuarios.push(obj.J1);
				//idUsuarios.push(obj.J2);
				idUsuarios.push(obj.J1);
				idUsuarios.push(obj.J2);
				nombresJugadores.push(obj.nombre1);
				nombresJugadores.push(obj.nombre2);
				fichasJugadores.push(obj.F1);
				fichasJugadores.push(obj.F2);

				document.getElementById("J1").innerHTML += "id: " + obj.J1 + ", nombre: " + obj.nombre1 + "<br>";
				document.getElementById("fichasJ1").innerHTML += "fichas: " + obj.F1 + "<br>";
				document.getElementById("J2").innerHTML += "id: " + obj.J2 + ", nombre: " + obj.nombre2 + "<br>";
				document.getElementById("fichasJ2").innerHTML += "fichas: " + obj.F2 + "<br>";
				if (obj.J3 != undefined) {
					document.getElementById("J3").innerHTML += "id: " + obj.J3 + ", nombre: " + obj.nombre3 + "<br>";
					document.getElementById("fichasJ3").innerHTML += "fichas: " + obj.F3 + "<br>";
					idUsuarios.push(obj.J3);
					nombresJugadores.push(obj.nombre2);
					fichasJugadores.push(obj.F3);
				}
				if (obj.J4 != undefined) {
					document.getElementById("J4").innerHTML += "id: " + obj.J4 + ", nombre: " + obj.nombre4 + "<br>";
					document.getElementById("fichasJ4").innerHTML += "fichas: " + obj.F4 + "<br>";
					idUsuarios.push(obj.J4);
					nombresJugadores.push(obj.nombre4);
					fichasJugadores.push(obj.F4);
				}
				if (obj.J5 != undefined) {
					document.getElementById("J5").innerHTML += "id: " + obj.J5 + ", nombre: " + obj.nombre5 + "<br>";
					document.getElementById("fichasJ5").innerHTML += "fichas: " + obj.F5 + "<br>";
					idUsuarios.push(obj.J5);
					nombresJugadores.push(obj.nombre5);
					fichasJugadores.push(obj.F5);
				}
				if (obj.J6 != undefined) {
					document.getElementById("J6").innerHTML += "id: " + obj.J6 + ", nombre: " + obj.nombre6 + "<br>";
					document.getElementById("fichasJ6").innerHTML += "fichas: " + obj.F6 + "<br>";
					idUsuarios.push(obj.J6);
					nombresJugadores.push(obj.nombre6);
					fichasJugadores.push(obj.F6);
				}
				if (obj.J7 != undefined) {
					document.getElementById("J7").innerHTML += "id: " + obj.J7 + ", nombre: " + obj.nombre7 + "<br>";
					document.getElementById("fichasJ7").innerHTML += "fichas: " + obj.F7 + "<br>";
					idUsuarios.push(obj.J7);
					nombresJugadores.push(obj.nombre7);
					fichasJugadores.push(obj.F7);
				}
				if (obj.J8 != undefined) {
					document.getElementById("J8").innerHTML += "id: " + obj.J8 + ", nombre: " + obj.nombre8 + "<br>";
					document.getElementById("fichasJ8").innerHTML += "fichas: " + obj.F8 + "<br>";
					idUsuarios.push(obj.J8);
					nombresJugadores.push(obj.nombre8);
					fichasJugadores.push(obj.F8);
				}
				sessionStorage.setItem("todosUsuarios", JSON.stringify(idUsuarios));
				bote = ciega_pequena + ciega_grande;
				turnos();
				break;
			case "fold":
				turno++;
				document.getElementById("div3").innerHTML += "Fold. Turno: " + turno;
				var encontrado = idUsuarios.indexOf(obj.id_user);

				if (encontrado !== -1) {
					idUsuarios.splice(encontrado, 1);
				}
				sessionStorage.setItem("todosUsuarios", JSON.stringify(idUsuarios));
				//document.getElementById("J8").innerHTML += JSON.parse(sessionStorage.getItem("todosUsuarios"));
				turnos();
				break;
			case "call":
				turno++;
				document.getElementById("div4").innerHTML += "Call. Turno: " + turno;
				turnos();
				break;
			case "raise":
				turno++;
				document.getElementById("div5").innerHTML += "Raise. Turno: " + turno;
				turnos();
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

	function turnos() {
		if (JSON.parse(sessionStorage.getItem("todosUsuarios")).length > 1) {
			if (turno < JSON.parse(sessionStorage.getItem("todosUsuarios")).length) {
				if (contTurnos == 0) {
					for (var i = 0; i < JSON.parse(sessionStorage.getItem("todosUsuarios")).length; i++) {
						document.getElementById("totalApostadoJ" + (i + 1)).innerHTML = 0;
					}
					if (empieza > 1) {
						document.getElementById("totalApostadoJ" + empieza).innerHTML = ciega_grande;
						document.getElementById("totalApostadoJ" + (empieza - 1)).innerHTML = ciega_pequena;
					}
					else {
						if (empieza == 1) {
							document.getElementById("totalApostadoJ" + (empieza - 1)).innerHTML = ciega_grande;
							document.getElementById("totalApostadoJ" + JSON.parse(sessionStorage.getItem("todosUsuarios")).length).innerHTML = ciega_pequena;
						}
						else {
							if (empieza == 0) {
								document.getElementById("totalApostadoJ" + JSON.parse(sessionStorage.getItem("todosUsuarios")).length).innerHTML = ciega_grande;
								document.getElementById("totalApostadoJ" + (JSON.parse(sessionStorage.getItem("todosUsuarios")).length - 1)).innerHTML = ciega_pequena;
							}
						}
					}
					for (var i = 0; i < JSON.parse(sessionStorage.getItem("todosUsuarios")).length; i++) {
						if (JSON.parse(sessionStorage.getItem("todosUsuarios"))[i] == sessionStorage.getItem("id_usuario")) {
							posicion = i;
							break;
						}
					}
					if (document.getElementById("totalApostadoJ" + (posicion + 1)).textContent != undefined) {
						totalApostado = document.getElementById("totalApostadoJ" + (posicion + 1)).textContent;
						mensajes.innerHTML = "Total apostado: " + totalApostado;
					}
				}

				if (JSON.parse(sessionStorage.getItem("todosUsuarios"))[turno] == sessionStorage.getItem("id_usuario")) {
					foldBtn.disabled = false;
					callBtn.disabled = false;
					raiseBtn.disabled = false;

				}
				/*else {
					mensajes.innerHTML = "No es tu turno";
				}*/


				if (turno == empieza) {//falta agregar las apuestas call y raise
					contTurnos++;
					if (contTurnos > 1) {
						if (esHost == "true") {
							if (esFlop == false) {
								esFlop = true;
								var msg = {
									accion: "flop",
									id_sala: sessionStorage.getItem("id_sala"),
								};

								ws.send(JSON.stringify(msg));
							}
							else {
								if (esTurn == false) {
									esTurn = true;
									var msg = {
										accion: "turn",
										id_sala: sessionStorage.getItem("id_sala"),
									};

									ws.send(JSON.stringify(msg));
								}
								else {
									if (esRiver == false) {
										esRiver = true;
										var msg = {
											accion: "river",
											id_sala: sessionStorage.getItem("id_sala"),
										};

										ws.send(JSON.stringify(msg));
									}
								}
							}
						}
					}
				}
			}
			else {
				turno = 0;
				turnos();
			}
			document.getElementById("bote").innerHTML = "Bote total: " + bote;
		}
		else {
			mensajes.innerHTML = "Ganador: " + JSON.parse(sessionStorage.getItem("todosUsuarios"))[0];
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
			id_sala: sessionStorage.getItem("id_sala"),
			empieza: empieza
		}, function(responseText) { //La respuesta es el número de jugadores
			if (responseText == "true") {
				esHost = "true";
				mensajes.innerHTML = responseText;
				//sleep(40);
				/*if (esHost == "true") {
						ar msg = {
						accion: "flop",
						id_sala: sessionStorage.getItem("id_sala"),
					};

					ws.send(JSON.stringify(msg));
				}*/
				if (esHost == "true") {
					var msg = {
						accion: "todosUsuarios",
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
			/*if (idUsuarios[0] == sessionStorage.getItem("id_usuario")) {
				foldBtn.disabled = false;
				callBtn.disabled = false;
				raiseBtn.disabled = false;
			}
			else {
				mensajes.innerHTML = idUsuarios[0] + " " + sessionStorage.getItem("id_usuario");
			}*/
			//mensajes.innerHTML += Object.values(idUsuarios);
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
		/*var apuesta = document.getElementById("apuesta").value;
		var accion = "fold";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div2').html(responseText);
		});*/
		var msg = {
			accion: "fold",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		};
		ws.send(JSON.stringify(msg));
	}

	function call() {
		/*var apuesta = "200";
		var accion = "call";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div3').html(responseText);
		});*/
		var msg = {
			accion: "call",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		};
		ws.send(JSON.stringify(msg));
	}

	function raise() {
		/*var apuesta = "300";
		var accion = "raise";
		$.post("ServletControlador", {
			apuesta: apuesta,
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario")
		}, function(responseText) {
			$('#div4').html(responseText);
		});*/
		var msg = {
			accion: "raise",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		};
		ws.send(JSON.stringify(msg));
	}

	$("#fold").click(function() {
		foldBtn.disabled = true;
		callBtn.disabled = true;
		raiseBtn.disabled = true;
		fold();
	});
	$("#call").click(function() {
		foldBtn.disabled = true;
		callBtn.disabled = true;
		raiseBtn.disabled = true;
		call();
	});
	$("#raise").click(function() {
		foldBtn.disabled = true;
		callBtn.disabled = true;
		raiseBtn.disabled = true;
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