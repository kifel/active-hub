<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="br.com.cefet.activehub.model.Usuario" %>

<%
    String corFundo = "#ffffff";
    String corFonte = "#000000";

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
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <title>Menu</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      background-color: <%= corFundo %>;
      color: <%= corFonte %>;
      font-family: Arial, sans-serif;
      min-height: 100vh;
    }

    header {
      padding: 1rem;
      background-color: <%= corFundo %>;
      border-bottom: 1px solid <%= corFonte %>;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .usuario {
      font-weight: bold;
    }

    nav a {
      color: <%= corFonte %>;
      text-decoration: none;
      padding: 0.5rem 1rem;
      border: 1px solid <%= corFonte %>;
      border-radius: 5px;
      transition: background-color 0.3s, color 0.3s;
    }

    nav a:hover {
      background-color: <%= corFonte %>;
      color: <%= corFundo %>;
    }

    main {
      padding: 2rem;
    }

    h2 {
      border-bottom: 2px solid <%= corFonte %>;
      padding-bottom: 0.5rem;
    }

    .botoes {
      display: flex;
      flex-wrap: wrap;
      gap: 1rem;
      margin-top: 1rem;
    }

    .botao {
      background-color: transparent;
      color: <%= corFonte %>;
      border: 2px solid <%= corFonte %>;
      padding: 0.75rem 1.5rem;
      border-radius: 8px;
      text-decoration: none;
      font-weight: bold;
      transition: all 0.3s ease;
    }

    .botao:hover {
      background-color: <%= corFonte %>;
      color: <%= corFundo %>;
    }
  </style>
</head>

<body>
  <header>
    <div class="usuario">Usuário logado: <%= nomeUsuario %></div>
    <nav>
      <a href="${pageContext.request.contextPath}/index.jsp">Logout</a>
    </nav>
  </header>

  <main>
    <h2>Menu</h2>
    <div class="botoes">
      <a href="${pageContext.request.contextPath}/clientes" class="botao">Listagem de Clientes</a>
      <a href="${pageContext.request.contextPath}/atividade" class="botao">Cadastro de Atividades</a>
    </div>

    <h2>Menu JSON</h2>
    <div class="botoes">
      <a href="${pageContext.request.contextPath}/views/atividadelistarjson.html" class="botao">Listar Atividades JSON</a>
      <a href="${pageContext.request.contextPath}/views/atividadeCadastrarjson.html" class="botao">Cadastrar Atividades JSON</a>
    </div>
  </main>
</body>
</html>
