<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="jakarta.servlet.http.HttpSession, br.com.cefet.activehub.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${cliente.id == null ? 'Cadastrar Cliente' : 'Editar Cliente'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cliente-form.css">

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
      Usuário logado: <%
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
        <header>
            <h1>${cliente.id == null ? 'Cadastrar Cliente' : 'Editar Cliente'}</h1>
        </header>

        <form action="${pageContext.request.contextPath}/clientes${cliente.id != null ? '/' : ''}${cliente.id != null ? cliente.id : ''}" method="POST">
            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="${cliente.nome}" required />
            </div>

            <div class="form-group">
                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" value="${cliente.cpf}" required />
            </div>

            <div class="form-group">
                <label for="cep">CEP:</label>
                <input type="text" id="cep" name="cep" value="${cliente.endereco != null ? cliente.endereco.cep : ''}" required />
            </div>

            <div class="form-group">
                <label for="uf">UF:</label>
                <input type="text" id="uf" name="uf" value="${cliente.endereco != null ? cliente.endereco.uf : ''}" required />
            </div>

            <div class="form-group">
                <label for="cidade">Cidade:</label>
                <input type="text" id="cidade" name="cidade" value="${cliente.endereco != null ? cliente.endereco.cidade : ''}" required />
            </div>

            <div class="form-group">
                <label for="bairro">Bairro:</label>
                <input type="text" id="bairro" name="bairro" value="${cliente.endereco != null ? cliente.endereco.bairro : ''}" required />
            </div>

            <div class="form-group">
                <label for="logradouro">Logradouro:</label>
                <input type="text" id="logradouro" name="logradouro" value="${cliente.endereco != null ? cliente.endereco.logradouro : ''}" required />
            </div>

            <div class="form-group">
                <label for="numero">Número:</label>
                <input type="text" id="numero" name="numero" value="${cliente.endereco != null ? cliente.endereco.numero : ''}" required />
            </div>

            <div class="form-group">
                <label for="complemento">Complemento:</label>
                <input type="text" id="complemento" name="complemento" value="${cliente.endereco != null ? cliente.endereco.complemento : ''}" />
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">${cliente.id == null ? 'Cadastrar' : 'Atualizar'}</button>
                <a href="${pageContext.request.contextPath}/clientes" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>
