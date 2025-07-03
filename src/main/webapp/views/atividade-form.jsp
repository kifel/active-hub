<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="jakarta.servlet.http.Cookie, br.com.cefet.activehub.model.Usuario" %>

<%
    String corFundo = "#ffffff"; // cor padrão fundo
    String corFonte = "#000000"; // cor padrão fonte

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("corFundo".equals(c.getName())) corFundo = c.getValue();
            if ("corFonte".equals(c.getName())) corFonte = c.getValue();
        }
    }

    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String nomeUsuario = (usuario != null) ? usuario.getNome() : "Desconhecido";
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Cadastrar Atividade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
        }

        .header {
            background-color: <%= corFundo %>;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem 2rem;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .usuario {
            font-size: 1rem;
            color: <%= corFonte %>;
            font-weight: 500;
        }

        .btn-group {
            display: flex;
            gap: 0.5rem;
        }

        .btn-header {
            background-color: <%= corFonte %>;
            color: <%= corFundo %>;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 0.5rem;
            text-decoration: none;
            font-size: 0.95rem;
            transition: background-color 0.3s, color 0.3s, border 0.3s;
            cursor: pointer;
        }

        .btn-header:hover {
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            border: 1px solid <%= corFonte %>;
        }

        .btn-logout {
            background-color: #ef4444;
            color: white;
        }

        .btn-logout:hover {
            background-color: #dc2626;
        }

        .content {
            padding: 2rem;
            margin-top: 1rem;
        }

        label {
            display: block;
            margin-top: 1rem;
            margin-bottom: 0.3rem;
        }

        input[type="text"], select {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid <%= corFonte %>;
            border-radius: 0.3rem;
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            font-size: 1rem;
        }

        button[type="submit"] {
            margin-top: 1.5rem;
            padding: 0.7rem 1.5rem;
            font-size: 1rem;
            background-color: <%= corFonte %>;
            color: <%= corFundo %>;
            border: none;
            border-radius: 0.5rem;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            border: 1px solid <%= corFonte %>;
        }

        .error {
            color: #dc2626;
            margin-top: 1rem;
        }

        .success {
            color: #16a34a;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <div class="header">
        <p class="usuario">
            Usuário logado: <%= nomeUsuario %>
        </p>
        <div class="btn-group">
            <a href="javascript:history.back()" class="btn-header">Voltar</a>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-header btn-logout">Logout</a>
        </div>
    </div>
    <div class="container content">
        <h1>Cadastrar Atividade</h1>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="success">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/atividade" method="post">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required />

            <label for="valor">Valor:</label>
            <input type="text" id="valor" name="valor" required />

            <label for="periodo">Período:</label>
            <select id="periodo" name="periodo" required>
                <option value="MANHA">Manhã</option>
                <option value="TARDE">Tarde</option>
                <option value="NOITE">Noite</option>
            </select>

            <button type="submit">Cadastrar</button>
        </form>
    </div>
</body>
</html>
