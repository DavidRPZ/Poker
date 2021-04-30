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
        $("#fold").click(function () {
            $.post("ServletControlador", {
                jugada: "fold"
            }, function (responseText) {
                $('#div1').html(responseText);
            });
        });
        $("#call").click(function () {
            $.post("ServletControlador", {
                jugada: "call"
            }, function (responseText) {
                $('#div1').html(responseText);
            });
        });
        $("#raise").click(function () {
            $.post("ServletControlador", {
                jugada: "raise"
            }, function (responseText) {
                $('#div1').html(responseText);
            });
        });

        /* function ajax() {
            var req = new XMLHttpRequest();

            req.onreadystatechange = function () {
                if (req.readyState == 4 && req.status == 200) {
                    document.getElementById('div1').innerHTML = req.responseText;
                }
            }

            req.open('POST', 'servletControlador.java', true);
            req.send();
        }

        setInterval(function () {
            ajax();
        }, 1000);*/

    });

    /* function fold() {
        var juego = "fold";
        var peticion = $.ajax({
            url: "servletControlador.java",
            type: "POST",
            async: true, // no es obligario es asincrono por defecto
            data: { //Variable que vamos a mandar al servidor
                juego: juego
            },
            success: function () { //cuando nos devuelve una respuesta favorable entra en el success
                $("#div1").html(peticion.responseText);
            }
        })
    }; */
</script>

<body>
    <form action="" name="jugada" method="POST">
        <input type="number" id="apuesta" name="apuesta">
        <input type="button" id="fold" name="fold" value="fold">
        <input type="button" id="call" name="call" value="call">
        <input type="button" id="raise" name="raise" value="raise">
    </form>
    <div id="div1"></div>
</body>

</html>