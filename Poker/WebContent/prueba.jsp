<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page session="true"
	import="java.util.*, juego.Baraja, juego.Carta"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Baraja.crearBaraja();
	Baraja.barajar();
	out.println(Baraja.mostrarBaraja());
	%>
</body>
</html>