<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="jakarta.servlet.http.HttpSession, br.com.cefet.activehub.model.Usuario" %>
<html>
<head>
    <title>Associar Atividade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">

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
        <h1>Associar Atividade ao Cliente</h1>

        <form method="post" action="${pageContext.request.contextPath}/clienteatividade">
            <input type="hidden" name="clienteId" value="${clienteId}" />

            <label for="atividadeId">Escolha uma Atividade:</label>
            <select name="atividadeId" required>
                <c:forEach var="atividade" items="${atividades}">
                    <option value="${atividade.id}">
                        ${atividade.nome} - R$ ${atividade.valor} (${atividade.periodo})
                    </option>
                </c:forEach>
            </select>

            <button type="submit" class="btn btn-primary">Vincular</button>
            <a href="${pageContext.request.contextPath}/clientes" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
