<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page session="true"
	import="java.util.*, juego.*, bd.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Baraja.crearBaraja();
	//Baraja.barajar();
	for (int i = 0; i < Baraja.barajaSize(); i++) {
		out.println("<img src='img/baraja/" + Baraja.mostrarBaraja(i) + ".svg' alt='Carta' width='50px'>");
	}
	Baraja.vaciarBaraja();
	%>
</body>
</html>