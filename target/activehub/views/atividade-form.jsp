<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="jakarta.servlet.http.HttpSession, br.com.cefet.activehub.model.Usuario" %>
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
    }

    .header {
      background-color: #f9fafb;
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
      color: #1e293b;
      font-weight: 500;
    }

    .btn-group {
      display: flex;
      gap: 0.5rem;
    }

    .btn-header {
      background-color: #6366f1;
      color: white;
      border: none;
      padding: 0.5rem 1rem;
      border-radius: 0.5rem;
      text-decoration: none;
      font-size: 0.95rem;
      transition: background-color 0.3s;
    }

    .btn-header:hover {
      background-color: #4f46e5;
    }

    .btn-logout {
      background-color: #ef4444;
    }

    .btn-logout:hover {
      background-color: #dc2626;
    }

    .content {
      padding: 2rem;
      margin-top: 1rem;
    }
  </style>
</head>
<body>
     <div class="header">
    <p class="usuario">
      Usuário logado:  <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        out.print(usuario != null ? usuario.getNome() : "Desconhecido");
      %>
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
