<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>SISVENDA - Registrar-se</title>
  <style>
    * {
      box-sizing: border-box;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    body {
      background: linear-gradient(to right, #f0f4ff, #e9ecff);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .card {
      background-color: #fff;
      padding: 2.5rem;
      border-radius: 1.25rem;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 450px;
      text-align: center;
    }

    h1 {
      margin-bottom: 1.5rem;
      font-size: 1.75rem;
      color: #1e1e2f;
    }

    form {
      display: flex;
      flex-direction: column;
      gap: 1.25rem;
    }

    input {
      padding: 0.75rem 1rem;
      border: 1px solid #d1d5db;
      border-radius: 0.75rem;
      font-size: 1rem;
      transition: border-color 0.2s, box-shadow 0.2s;
    }

    input:focus {
      border-color: #6366f1;
      outline: none;
      box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.3);
    }

    button {
      background-color: #10b981;
      color: white;
      padding: 0.75rem;
      border: none;
      border-radius: 0.75rem;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:hover {
      background-color: #059669;
    }

    a {
      margin-top: 1.25rem;
      display: inline-block;
      color: #6366f1;
      text-decoration: none;
      font-size: 0.95rem;
    }

    a:hover {
      text-decoration: underline;
    }

    .msg {
      margin-top: 1rem;
      color: #dc2626;
      font-size: 0.95rem;
    }

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
    input[type="color"] {
      padding: 0.3rem;
      height: 3rem;
      border-radius: 0.75rem;
      border: 1px solid #d1d5db;
      cursor: pointer;
    }
  </style>
</head>
<body>
  <div class="card">
    <h1>Registrar-se</h1>
    <form action="${pageContext.request.contextPath}/usuario" method="post">
      <input type="text" name="nome" placeholder="Nome" required />
      <input type="text" name="login" placeholder="Login" required />
      <input type="password" name="senha" placeholder="Senha" required />

      <label style="text-align:left; font-size: 0.95rem;">Cor de fundo:</label>
      <input type="color" name="corFundo" value="#ffffff" />

      <label style="text-align:left; font-size: 0.95rem;">Cor da fonte:</label>
      <input type="color" name="corFonte" value="#000000" />

      <button type="submit">Cadastrar</button>
    </form>
    <a href="${pageContext.request.contextPath}/index.jsp">Voltar</a>
    <p class="msg">${msg}</p>
  </div>
</body>
</html>
