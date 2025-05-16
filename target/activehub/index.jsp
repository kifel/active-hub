<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Active Hub - Página Inicial</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>

<body>
    <div class="index-container">
        <h1>Bem-vindo ao Active Hub!</h1>
        <a href="${pageContext.request.contextPath}/clientes" class="btn">Ir para a Listagem de Clientes</a>
        <a href="${pageContext.request.contextPath}/atividade" class="btn">Ir para a Listagem de Atividades</a>
    </div>
</body>

</html>
