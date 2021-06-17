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
		numJugadores = 0,
		rondaEmpezada = false,
		esHost = "false",
		permitir = true,
		idUsuarios = [],
		copiaIdUsuarios = [],
		nombresJugadores = [],
		fichasJugadores = [],
		idsPredefinidos = ["37933", "37955", "37973", "37991", "38009", "38027", "38045", "38063"],
		empieza = 0,
		empieza2 = 0,
		turno = empieza,
		foldBtn = document.getElementById('fold'),
		callBtn = document.getElementById('call'),
		raiseBtn = document.getElementById('raise'),
		esFlop = false,
		esTurn = false,
		esRiver = false,
		contTurnos = 0,
		fichas = 0,
		totalApostado = 0,
		totalApostado2 = 0,
		ciega_pequena = 10,
		ciega_grande = 20,
		bote = 0,
		apuestaMasGrande = ciega_grande,
		posicion,
		posicion2,
		nuevaApuesta = 0,
		numUsuarios = 0,
		mostrarFlop = true,
		cuentaAtras,
		allIn = false,
		tiempo = 20;

	ws.onopen = onOpen;
	ws.onclose = onClose;
	ws.onmessage = onMessage;
	boton.addEventListener('click', enviar);
	document.getElementById("btnEnviar").disabled = true;

	function onOpen() {
		console.log('Conectado a WebSocket');
		foldBtn.disabled = true;
		callBtn.disabled = true;
		raiseBtn.disabled = true;
		if (window.sessionStorage) {
			sessionStorage.setItem("id_sala", "1");
		}
		if (!rondaEmpezada) {
			nuevoJugador();
		}
	}

	function onClose() {
		console.log('Desconectado de WebSocket');
		//borrarJugadores();
	}

	function enviar() {
		var msg = {
			accion: "chat",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
			//nombre: nombre.value,
			//nombre: sessionStorage.getItem("id_usuario"),
			nombre: sessionStorage.getItem("nombre_usuario"),
			mensaje: mensaje.value
		};

		ws.send(JSON.stringify(msg));
	}

	function onMessage(evt) {
		var obj = JSON.parse(evt.data);
		switch (obj.accion) {
			case "chat":
				var d = new Date(),
					msg1 = '<b>' + d.getHours() + ':' + d.getMinutes() + '</b> - ',
					msg2 = '<b>' + obj.nombre + ': </b>',
					msg3 = '<b>' + obj.mensaje + '</b>';
				mensajes.innerHTML += msg1 + msg2 + msg3 + '<br>';
				mensajes.scrollTop = mensajes.scrollHeight;
				break;

			case "empezarRonda":
				//var msg = obj.empezarRonda;
				//pruebas.innerHTML += "Empezar ronda: " + msg + "<br>Numero de jugadores: " + numJugadores;
				rondaEmpezada = false;
				empieza = parseFloat(obj.empieza);
				turno = empieza;
				esHost = "false";
				//esHost = "false";
				//sleep(40);
				host();
				break;
			case "flop":
				if (!rondaEmpezada) {
					setTimeout(function() {
						var msg1 = obj.flop1,
							msg2 = obj.flop2,
							msg3 = obj.flop3;
						flop.innerHTML = "<img src='img/baraja/" + msg1 + ".svg' alt='Carta' width='45px'>";
						flop.innerHTML += "<img src='img/baraja/" + msg2 + ".svg' alt='Carta' width='45px'>";
						flop.innerHTML += "<img src='img/baraja/" + msg3 + ".svg' alt='Carta' width='45px'>";

					}, 100);
				}
				break;
			case "turn":
				if (!rondaEmpezada) {
					setTimeout(function() {
						var msg = obj.turn;
						flop.innerHTML += "<img src='img/baraja/" + msg + ".svg' alt='Carta' width='45px'>";
					}, 100);
				}
				break;
			case "river":
				if (!rondaEmpezada) {
					setTimeout(function() {
						var msg = obj.river
						flop.innerHTML += "<img src='img/baraja/" + msg + ".svg' alt='Carta' width='45px'><br>";
					}, 100);

				}
				break;
			case "todosUsuarios":
				if (!rondaEmpezada) {
					//var numJug = Object.keys(obj).length - 1;
					//idUsuarios.push(obj.J1);
					//idUsuarios.push(obj.J2);
					idUsuarios.push(obj.J1);
					idUsuarios.push(obj.J2);
					nombresJugadores.push(obj.nombre1);
					nombresJugadores.push(obj.nombre2);
					fichasJugadores.push(obj.F1);
					fichasJugadores.push(obj.F2);

					document.getElementById("nombreJ1").innerHTML = obj.nombre1;
					document.getElementById("fichasJ1").innerHTML = 'Fichas: ' + obj.F1;
					document.getElementById("J1").style.display = 'block';
					document.getElementById("fichasJ1").style.display = 'block';
					document.getElementById("nombreJ1").style.display = 'block';
					document.getElementById("nombreJ2").innerHTML = obj.nombre2;
					document.getElementById("fichasJ2").innerHTML = 'Fichas: ' + obj.F2;
					document.getElementById("J2").style.display = 'block';
					document.getElementById("fichasJ2").style.display = 'block';
					document.getElementById("nombreJ2").style.display = 'block';
					if (obj.J3 != undefined) {
						document.getElementById("nombreJ3").innerHTML = obj.nombre3;
						document.getElementById("fichasJ3").innerHTML = 'Fichas: ' + obj.F3;
						document.getElementById("J3").style.display = 'block';
						document.getElementById("fichasJ3").style.display = 'block';
						document.getElementById("nombreJ3").style.display = 'block';
						idUsuarios.push(obj.J3);
						nombresJugadores.push(obj.nombre3);
						fichasJugadores.push(obj.F3);
					}
					else {
						document.getElementById("J3").style.display = 'none';
						document.getElementById("fichasJ3").style.display = 'none';
						document.getElementById("nombreJ3").style.display = 'none';
					}
					if (obj.J4 != undefined) {
						document.getElementById("nombreJ4").innerHTML = obj.nombre4;
						document.getElementById("fichasJ4").innerHTML = 'Fichas: ' + obj.F4;
						document.getElementById("J4").style.display = 'block';
						document.getElementById("fichasJ4").style.display = 'block';
						document.getElementById("nombreJ4").style.display = 'block';
						idUsuarios.push(obj.J4);
						nombresJugadores.push(obj.nombre4);
						fichasJugadores.push(obj.F4);
					}
					else {
						document.getElementById("J4").style.display = 'none';
						document.getElementById("fichasJ4").style.display = 'none';
						document.getElementById("nombreJ4").style.display = 'none';
					}
					if (obj.J5 != undefined) {
						document.getElementById("nombreJ5").innerHTML = obj.nombre5;
						document.getElementById("fichasJ5").innerHTML = 'Fichas: ' + obj.F5;
						document.getElementById("J5").style.display = 'block';
						document.getElementById("fichasJ5").style.display = 'block';
						document.getElementById("nombreJ5").style.display = 'block';
						idUsuarios.push(obj.J5);
						nombresJugadores.push(obj.nombre5);
						fichasJugadores.push(obj.F5);
					}
					else {
						document.getElementById("J5").style.display = 'none';
						document.getElementById("fichasJ5").style.display = 'none';
						document.getElementById("nombreJ5").style.display = 'none';
					}
					if (obj.J6 != undefined) {
						document.getElementById("nombreJ6").innerHTML = obj.nombre6;
						document.getElementById("fichasJ6").innerHTML = 'Fichas: ' + obj.F6;
						document.getElementById("J6").style.display = 'block';
						document.getElementById("fichasJ6").style.display = 'block';
						document.getElementById("nombreJ6").style.display = 'block';
						idUsuarios.push(obj.J6);
						nombresJugadores.push(obj.nombre6);
						fichasJugadores.push(obj.F6);
					}
					else {
						document.getElementById("J6").style.display = 'none';
						document.getElementById("fichasJ6").style.display = 'none';
						document.getElementById("nombreJ6").style.display = 'none';
					}
					if (obj.J7 != undefined) {
						document.getElementById("nombreJ7").innerHTML = obj.nombre7;
						document.getElementById("fichasJ7").innerHTML = 'Fichas: ' + obj.F7;
						document.getElementById("J7").style.display = 'block';
						document.getElementById("fichasJ7").style.display = 'block';
						document.getElementById("nombreJ7").style.display = 'block';
						idUsuarios.push(obj.J7);
						nombresJugadores.push(obj.nombre7);
						fichasJugadores.push(obj.F7);
					}
					else {
						document.getElementById("J7").style.display = 'none';
						document.getElementById("fichasJ7").style.display = 'none';
						document.getElementById("nombreJ7").style.display = 'none';
					}
					if (obj.J8 != undefined) {
						document.getElementById("nombreJ8").innerHTML = obj.nombre8;
						document.getElementById("fichasJ8").innerHTML = 'Fichas: ' + obj.F8;
						document.getElementById("J8").style.display = 'block';
						document.getElementById("fichasJ8").style.display = 'block';
						document.getElementById("nombreJ8").style.display = 'block';
						idUsuarios.push(obj.J8);
						nombresJugadores.push(obj.nombre8);
						fichasJugadores.push(obj.F8);
					}
					else {
						document.getElementById("J8").style.display = 'none';
						document.getElementById("fichasJ8").style.display = 'none';
						document.getElementById("nombreJ8").style.display = 'none';
					}
					bote = ciega_pequena + ciega_grande;
					numUsuarios = idUsuarios.length;
					copiaIdUsuarios = idUsuarios;
					document.getElementById("btnEnviar").disabled = false;

					var pos;
					for (var i = 0; i < numUsuarios; i++) {
						document.getElementById("J" + (i + 1)).innerHTML = "<img src='img/baraja/card_back.svg' alt='Carta' width='45px'><img src='img/baraja/card_back.svg' alt='Carta' width='45px'>";
						if (sessionStorage.getItem("id_usuario") == idUsuarios[i]) {
							pos = i;
						}
					}
					sessionStorage.setItem("nombre_usuario", nombresJugadores[pos]);
					setTimeout(function() {
						//sleep(100);
						juego(pos);
					}, 100);

				}
				break;
			case "fold":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					document.getElementById("nombreJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("nombreJ" + (turno + 1)).style.color = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.color = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.color = "black";
					turno++;
					var encontrado = idUsuarios.indexOf(obj.id_user);

					if (encontrado !== -1) {
						//idUsuarios.splice(encontrado, 1);
						idUsuarios[encontrado] = "0";
					}
					numUsuarios--;
					empieza2++;
					//document.getElementById("J8").innerHTML += idUsuarios;

					turnos();
				}
				break;
			case "call":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					document.getElementById("nombreJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("nombreJ" + (turno + 1)).style.color = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.color = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.color = "black";
					turno++;
					var msg1 = obj.id_usuario,
						msg2 = parseFloat(obj.apuesta);
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] == msg1) {
							posicion2 = i;
							break;
						}
					}
					fichasJugadores[posicion2] -= msg2;
					bote += msg2;
					document.getElementById("bote").innerHTML = "Bote total: " + bote;
					if (msg1 == sessionStorage.getItem("id_usuario")) {
						if (document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent != undefined) {
							totalApostado = parseFloat(document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent);
							totalApostado += msg2;
							document.getElementById("fichasJ" + (posicion2 + 1)).innerHTML = "fichas: " + fichasJugadores[posicion2] + "<br>";
							document.getElementById("totalApostadoJ" + (posicion2 + 1)).innerHTML = totalApostado;
						}
					}
					else {
						if (document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent != undefined) {
							totalApostado2 = parseFloat(document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent);
							totalApostado2 += msg2;
							document.getElementById("fichasJ" + (posicion2 + 1)).innerHTML = "fichas: " + fichasJugadores[posicion2] + "<br>";
							document.getElementById("totalApostadoJ" + (posicion2 + 1)).innerHTML = totalApostado2;
						}
					}
					turnos();
				}
				break;
			case "raise":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					document.getElementById("nombreJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.borderColor = "black";
					document.getElementById("nombreJ" + (turno + 1)).style.color = "black";
					document.getElementById("totalApostadoJ" + (turno + 1)).style.color = "black";
					document.getElementById("fichasJ" + (turno + 1)).style.color = "black";
					turno++;
					var msg1 = obj.id_usuario,
						msg2 = parseFloat(obj.apuesta);
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] == msg1) {
							posicion2 = i;
							break;
						}
					}
					fichasJugadores[posicion2] -= msg2;
					bote += msg2;
					document.getElementById("bote").innerHTML = "Bote total: " + bote;
					if (msg1 == sessionStorage.getItem("id_usuario")) {
						if (document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent != undefined) {
							totalApostado = parseFloat(document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent);
							totalApostado += msg2;
							document.getElementById("fichasJ" + (posicion2 + 1)).innerHTML = "fichas: " + fichasJugadores[posicion2] + "<br>";
							document.getElementById("totalApostadoJ" + (posicion2 + 1)).innerHTML = totalApostado;
							apuestaMasGrande = totalApostado;
						}
					}
					else {
						if (document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent != undefined) {
							totalApostado2 = parseFloat(document.getElementById("totalApostadoJ" + (posicion2 + 1)).textContent);
							totalApostado2 += msg2;
							document.getElementById("fichasJ" + (posicion2 + 1)).innerHTML = "fichas: " + fichasJugadores[posicion2] + "<br>";
							document.getElementById("totalApostadoJ" + (posicion2 + 1)).innerHTML = totalApostado2;
							apuestaMasGrande = totalApostado2;
						}
					}
					turnos();
				}
				break;
			case "ganadorFold":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					foldBtn.disabled = true;
					callBtn.disabled = true;
					raiseBtn.disabled = true;
					var str = "";
					str += "Ganador: ";
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] != "0") {
							str += nombresJugadores[i];
						}
					}

					document.getElementById("tiempo").innerHTML = str;
					setTimeout(function() {
						rondaTerminada();
					}, 6000);
				}
				break;
			case "comprobarGanador":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					foldBtn.disabled = true;
					callBtn.disabled = true;
					raiseBtn.disabled = true;
					var msg1, msg2, prop, numGanadores, str;
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] != "0") {
							prop = "J" + idUsuarios[i] + "C1";
							msg1 = obj[prop];
							//msg1 = obj["J" + idUsuarios[i] + "C1"];
							document.getElementById("J" + (i + 1)).innerHTML = "<img src='img/baraja/" + msg1 + ".svg' alt='Carta' width='45px'>";
							prop = "J" + idUsuarios[i] + "C2";
							msg2 = obj[prop];
							//msg2 = obj["J" + idUsuarios[i] + "C2"];
							document.getElementById("J" + (i + 1)).innerHTML += "<img src='img/baraja/" + msg2 + ".svg' alt='Carta' width='45px'><br>";
						}
					}
					numGanadores = parseFloat(obj.numGanadores)
					str = "";

					str += "Ganador: ";
					for (var i = 0; i < numGanadores; i++) {
						prop = "G" + (i + 1);
						for (var j = 0; j < nombresJugadores.length; j++) {
							if (idUsuarios[j] == obj[prop]) {
								str += nombresJugadores[j] + ", ";
							}
						}

					}
					str = str.substr(0, str.length - 2) + " con " + obj.jugadaGanadora;
					document.getElementById("tiempo").innerHTML = str;

					//sleep(6000);
					setTimeout(function() {
						rondaTerminada();
					}, 6000);
					//rondaTerminada();
				}
				break;
			case "rondaTerminada":
				if (!rondaEmpezada) {
					clearInterval(cuentaAtras);
					empieza++;
					if (empieza == (idUsuarios.length)) {
						empieza = 0;
					}

					numJugadores = 0,
						rondaEmpezada = false,
						idUsuarios = [],
						copiaIdUsuarios = [],
						nombresJugadores = [],
						fichasJugadores = [],
						permitir = false,
						turno = empieza,
						esFlop = false,
						esTurn = false,
						esRiver = false,
						contTurnos = 0,
						fichas = 0,
						totalApostado = 0,
						totalApostado2 = 0,
						ciega_pequena = 10,
						ciega_grande = 20,
						bote = 0,
						apuestaMasGrande = ciega_grande,
						posicion,
						posicion2,
						nuevaApuesta = 0,
						numUsuarios = 0,
						foldBtn.disabled = true,
						callBtn.disabled = true,
						raiseBtn.disabled = true,
						mostrarFlop = true,
						allIn = false,
						flop.innerHTML = "",
						empieza2 = empieza,
						tiempo = 20;
					document.getElementById("bote").innerHTML = "";
					//sleep(600);
					//sleep(100);
					setTimeout(function() {
						if (esHost == "true") {
							nuevoJugador();
						}
					}, 100);

				}
				break;
		}
	}

	function turnos() {
		setTimeout(function() {
			if (numUsuarios > 1) {
				if (turno < idUsuarios.length) {
					if (idUsuarios[turno] == "0") {
						turno++;
						turnos();
					}
					else {
						if (contTurnos == 0) {
							for (var i = 0; i < idUsuarios.length; i++) {
								document.getElementById("totalApostadoJ" + (i + 1)).innerHTML = 0;
							}
							if (empieza > 1) {
								document.getElementById("totalApostadoJ" + empieza).innerHTML = ciega_grande;
								document.getElementById("totalApostadoJ" + (empieza - 1)).innerHTML = ciega_pequena;
							}
							else {
								if (empieza == 1) {
									document.getElementById("totalApostadoJ" + empieza).innerHTML = ciega_grande;
									document.getElementById("totalApostadoJ" + idUsuarios.length).innerHTML = ciega_pequena;
								}
								else {
									if (empieza == 0) {
										document.getElementById("totalApostadoJ" + idUsuarios.length).innerHTML = ciega_grande;
										document.getElementById("totalApostadoJ" + (idUsuarios.length - 1)).innerHTML = ciega_pequena;
									}
								}
							}
							for (var i = 0; i < idUsuarios.length; i++) {
								if (idUsuarios[i] == sessionStorage.getItem("id_usuario")) {
									posicion = i;
									break;
								}
							}
							if (document.getElementById("totalApostadoJ" + (posicion + 1)).textContent != undefined) {
								totalApostado = document.getElementById("totalApostadoJ" + (posicion + 1)).textContent;
								if (fichasJugadores[posicion] == 0) {
									allIn = true;
								}
							}
						}

						if (apuestaMasGrande == totalApostado) {
							callBtn.value = "check";
						}
						else {
							callBtn.value = "call";
						}
						if (apuestaMasGrande >= fichasJugadores[posicion]) {
							raiseBtn.value = "all in";
							allIn = true;
						}
						else {
							raiseBtn.value = "raise";
						}
						if (idUsuarios[turno] == sessionStorage.getItem("id_usuario")) {
							document.getElementById("apuesta").min = (apuestaMasGrande - totalApostado + 1);
							document.getElementById("apuesta").max = fichasJugadores[posicion];
							document.getElementById("rangeApuesta").min = (apuestaMasGrande - totalApostado + 1);
							document.getElementById("rangeApuesta").max = fichasJugadores[posicion];
							foldBtn.disabled = false;
							callBtn.disabled = false;
							raiseBtn.disabled = false;
						}

						document.getElementById("nombreJ" + (turno + 1)).style.borderColor = "red";
						document.getElementById("totalApostadoJ" + (turno + 1)).style.borderColor = "red";
						document.getElementById("fichasJ" + (turno + 1)).style.borderColor = "red";
						document.getElementById("nombreJ" + (turno + 1)).style.color = "red";
						document.getElementById("totalApostadoJ" + (turno + 1)).style.color = "red";
						document.getElementById("fichasJ" + (turno + 1)).style.color = "red";


						for (var i = 0; i < idUsuarios.length; i++) {
							if (document.getElementById("totalApostadoJ" + (i + 1)).textContent != undefined) {
								if (idUsuarios[i] != "0" && document.getElementById("totalApostadoJ" + (i + 1)).textContent != apuestaMasGrande) {
									mostrarFlop = false;
									break;
								}
								else {
									mostrarFlop = true;
								}
							}
						}
						temporizador();
						if (turno == empieza || turno == empieza2) {
							contTurnos++;
							if (contTurnos > 1) {
								if (mostrarFlop == true || allIn == true && idUsuarios[turno] != "0") {
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
												else {
													comprobarGanador();
												}
											}
										}
									}
								}
							}
						}
					}
				}
				else {
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] != "0") {
							turno = i;
							break;
						}
					}
					//turno = 0;
					turnos();
				}
				document.getElementById("bote").innerHTML = "Bote total: " + bote;
			}
			else {
				if (esHost == "true") {
					for (var i = 0; i < idUsuarios.length; i++) {
						if (idUsuarios[i] != "0") {
							ganadorFold(idUsuarios[i]);
							break;
						}
					}
				}
			}
		}, 100);

	}

	function temporizador() {
		tiempo = 20;
		cuentaAtras = setInterval(function() {
			if (tiempo > 0) {
				$('#tiempo').html("Tiempo restante: " + tiempo);
				tiempo--;
			} else {
				if (idUsuarios[turno] == sessionStorage.getItem("id_usuario")) {
					foldBtn.disabled = true;
					callBtn.disabled = true;
					raiseBtn.disabled = true;
					fold();
				}
			}
		}, 1000);
	}

	function juego(pos) {
		$.post("ServletControlador", {
			accion: "juego",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala")
		}, function(responseText) {
			var cartas = responseText.split("@");
			document.getElementById("J" + (pos + 1)).innerHTML = "<img src='img/baraja/" + cartas[0] + ".svg' alt='Carta' width='45px'>";
			document.getElementById("J" + (pos + 1)).innerHTML += "<img src='img/baraja/" + cartas[1] + ".svg' alt='Carta' width='45px'>";
			document.getElementById("J" + (pos + 1)).style.display = 'block';
			document.getElementById("fichasJ" + (pos + 1)).style.display = 'block';
			document.getElementById("nombreJ" + (pos + 1)).style.display = 'block';
			turnos();
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
		esHost = "false";
		var accion = "host";
		empieza += "";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
			empieza: empieza
		}, function(responseText) { //La respuesta es el número de jugadores
			if (responseText == "true") {
				esHost = "true";
				if (esHost == "true") {
					var msg = {
						accion: "todosUsuarios",
						id_sala: sessionStorage.getItem("id_sala"),
						empieza: empieza
					};

					ws.send(JSON.stringify(msg));
				}
			}
		});
	}

	//comprobarRondaEmpezada -> nuevoJugador -> comprobarNumJugadores -> empezarRonda -> host -> todosUsuarios -> juego
	function comprobarGanador() {
		if (esHost == "true") {
			bote += "";
			var msg = {
				accion: "comprobarGanador",
				id_usuario: sessionStorage.getItem("id_usuario"),
				id_sala: sessionStorage.getItem("id_sala"),
				bote: bote
			};
			ws.send(JSON.stringify(msg));
		}
	}

	function ganadorFold(id_user) {
		bote += "";
		id_user += "";
		var msg = {
			accion: "ganadorFold",
			id_usuario: id_user,
			id_sala: sessionStorage.getItem("id_sala"),
			bote: bote
		};
		ws.send(JSON.stringify(msg));
	}

	function nuevoJugador() {
		var accion = "nuevoJugador";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) { //La respuesta es el número de jugadores
			numJugadores = parseFloat(responseText);
			//alert(sessionStorage.getItem("id_usuario"));
			if (sessionStorage.getItem("id_usuario") == "undefined" || sessionStorage.getItem("id_usuario") == undefined || sessionStorage.getItem("id_usuario") == "") {
				sessionStorage.setItem("id_usuario", idsPredefinidos[numJugadores - 1]);
			}
			if (numJugadores > 1 && numJugadores <= 8) {
				comprobarRondaEmpezada();
			} else {
				if (numJugadores < 2) {
					$('#bote').html("Esperando mas jugadores...");
					borrarJugadores();
				}
				else {
					$('#bote').html("Se ha alcanzado el maximo de jugadores...");
				}
			}
		});
	}

	function comprobarNumJugadores() {
		if (permitir || esHost == "true") {
			empieza += "";
			var msg = {
				accion: "empezarRonda",
				id_usuario: sessionStorage.getItem("id_usuario"),
				id_sala: sessionStorage.getItem("id_sala"),
				empieza: empieza
			};
			ws.send(JSON.stringify(msg));
		}
	}

	function comprobarRondaEmpezada() {
		var accion = "comprobarRondaEmpezada";
		$.post("ServletControlador", {
			accion: accion,
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) {
			//mensajes.innerHTML = responseText;
			if (responseText == "false") {
				rondaEmpezada = false;
			}
			else {
				rondaEmpezada = true;
			}
			if (!rondaEmpezada) {
				comprobarNumJugadores();
			} else {
				$('#bote').html("Ya existe una partida en curso... Espera a que termine esta ronda para poder jugar");
			}
		});
	}

	function fold() {
		var msg = {
			accion: "fold",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala")
		};
		ws.send(JSON.stringify(msg));
	}

	function call() {
		nuevaApuesta = (apuestaMasGrande - totalApostado);
		if (nuevaApuesta > fichasJugadores[posicion]) {
			nuevaApuesta = fichasJugadores[posicion];
		}
		nuevaApuesta += "";
		var msg = {
			accion: "call",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
			apuesta: nuevaApuesta
		};
		ws.send(JSON.stringify(msg));
	}

	function raise() {
		nuevaApuesta = document.getElementById("apuesta").value + "";
		var msg = {
			accion: "raise",
			id_usuario: sessionStorage.getItem("id_usuario"),
			id_sala: sessionStorage.getItem("id_sala"),
			apuesta: nuevaApuesta
		};
		ws.send(JSON.stringify(msg));
	}

	function rondaTerminada() {
		if (esHost == "true") {
			//setTimeout(function() {
			borrarJugadores();
			//}, 100);
			var msg = {
				accion: "rondaTerminada",
				id_usuario: sessionStorage.getItem("id_usuario"),
				id_sala: sessionStorage.getItem("id_sala"),
			};
			ws.send(JSON.stringify(msg));
		}
	}

	function borrarJugadores() {
		var accion = "borrarJugadores";
		$.post("ServletControlador", {
			accion: accion,
			id_sala: sessionStorage.getItem("id_sala"),
		}, function(responseText) {
			//console.log("Sala cerrada");
		});

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
		if (document.getElementById("apuesta").value > fichasJugadores[posicion]) {
			mensajes.innerHTML += "No puedes apostar tantas fichas..."
		}
		else {
			if (document.getElementById("apuesta").value < (apuestaMasGrande - totalApostado)) {
				mensajes.innerHTML += "La apuesta es demasiado pequeña..."
			}
			else {
				foldBtn.disabled = true;
				callBtn.disabled = true;
				raiseBtn.disabled = true;
				raise();
			}
		}
	});
	/*$("#empezar").click(function() {
		if (window.sessionStorage) {
			sessionStorage.setItem("id_usuario", document.getElementById('id_usuario').value);
			sessionStorage.setItem("id_sala", "1");
		}
		if (!rondaEmpezada) {
			nuevoJugador();
		}
	});*/

})(window, document, JSON);