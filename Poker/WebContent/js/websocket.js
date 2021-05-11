(function (window, document, JSON) {
    'use strict';

    var url = 'ws://' + window.location.host + '/Poker/sala',
        ws = new WebSocket(url),
        mensajes = document.getElementById('conversacion'),
        boton = document.getElementById('btnEnviar'),
        nombre = document.getElementById('usuario'),
        mensaje = document.getElementById('mensaje');
    
    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onmessage = onMessage;
    boton.addEventListener('click', enviar);

    function onOpen() {
        console.log('Conectado a WebSocket');
    }

    function onClose() {
        console.log('Desconectado de WebSocket');
    }

    function enviar() {
        var msg = {
            nombre: nombre.value,
            mensaje: mensaje.value
        };

        ws.send(JSON.stringify(msg));
    }

    function onMessage(evt) {
        var obj = JSON.parse(evt.data),
			d = new Date(),
            msg1 = '<b style="color:red;">' + d.getHours() + ':' + d.getMinutes() + '</b> - ',
			msg2 = '<b style="color:green;">' + obj.nombre + ': </b>',
			msg3 = '<b style="color:blue;">' + obj.mensaje + '</b>';
        mensajes.innerHTML += msg1 + msg2 + msg3 + '<br>';
    }

})(window, document, JSON);