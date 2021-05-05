<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <script src="js/jquery/jquery-3.5.1.js"></script>
    <title>Sala 1</title>
</head>

<script>
    $(document).ready(function () {
        /*setInterval(function () {
            llamada();
        }, 500);

        function llamada() {
            var apuesta = "100";
            var jugada = "fold"
            var id_jugador = "1";
            var id_sala = "1";
            $.post("ServletControlador", {
            	apuesta: apuesta,
                jugada: jugada,
                id_jugador: id_jugador,
                id_sala: id_sala
            }, function (responseText) {
                $('#div1').html(responseText);
            });
        }*/

        function fold() {
            var apuesta = "100";
            var jugada = "fold"
            var id_jugador = "1";
            var id_sala = "1";
            $.post("ServletControlador", {
                apuesta: apuesta,
                jugada: jugada,
                id_jugador: id_jugador,
                id_sala: id_sala
            }, function (responseText) {
                $('#div1').html(responseText);
            });
        }

        function call() {
            var apuesta = "200";
            var jugada = "call"
            var id_jugador = "2";
            var id_sala = "2";
            $.post("ServletControlador", {
                apuesta: apuesta,
                jugada: jugada,
                id_jugador: id_jugador,
                id_sala: id_sala
            }, function (responseText) {
                $('#div2').html(responseText);
            });
        }

        function raise() {
            var apuesta = "300";
            var jugada = "raise"
            var id_jugador = "3";
            var id_sala = "3";
            $.post("ServletControlador", {
                apuesta: apuesta,
                jugada: jugada,
                id_jugador: id_jugador,
                id_sala: id_sala
            }, function (responseText) {
                $('#div3').html(responseText);
            });
        }

        $("#fold").click(function () {
            fold();
        });
        $("#call").click(function () {
            call();
        });
        $("#raise").click(function () {
            raise();
        });

        var tiempo = 20;

        setInterval(function () {
            if (tiempo > -1) {
                $('#div4').html("Tiempo restante: " + tiempo);
                tiempo--;
            } else {
                fold();
                tiempo = 20;
            }
        }, 1000);
    });
</script>

<body>
    <form action="" name="jugada" method="POST">
        <input type="text" id="apuesta" name="apuesta">
        <input type="button" id="fold" name="fold" value="fold">
        <input type="button" id="call" name="call" value="call">
        <input type="button" id="raise" name="raise" value="raise">
        <input type="button" id="prueba1" name="prueba1" value="Botón de prueba">
    </form>
    <div id="div1"></div>
    <div id="div2"></div>
    <div id="div3"></div>
    <div id="div4"></div>
    <div id="div5"></div>
</body>

</html>