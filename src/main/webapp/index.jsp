<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session != null) {
        session.invalidate();
    }
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>ActiveHub - Login</title>
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
      max-width: 400px;
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
      background-color: #6366f1;
      color: white;
      padding: 0.75rem;
      border: none;
      border-radius: 0.75rem;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:hover {
      background-color: #4f46e5;
    }

    a {
      margin-top: 1rem;
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
  </style>
</head>
<body>
  <div class="card">
    <h1>ActiveHub - Login</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
      <input type="text" name="login" placeholder="Login" required />
      <input type="password" name="senha" placeholder="Senha" required />
      <button type="submit">Logar</button>
    </form>
    <a href="${pageContext.request.contextPath}/views/registrar.jsp">Registrar-se</a>
    <p class="msg">${msg}</p>
  </div>
</body>
</html>
