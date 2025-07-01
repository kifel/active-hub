<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="br.com.cefet.activehub.model.Usuario" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Active Hub - Menu Simples Branco</title>
  <style>
    body {
      margin: 0;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: #fafafa;
      color: #222;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      user-select: none;
    }

    header {
      background: #f9f9f9;
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
      padding: 1rem 2rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    header .usuario {
      font-weight: 600;
      font-size: 1.1rem;
      color: #444;
    }

    header nav a {
      color: #555;
      text-decoration: none;
      font-weight: 600;
      padding: 0.4rem 1rem;
      border-radius: 6px;
      border: 2px solid transparent;
      transition: border-color 0.3s ease, color 0.3s ease;
    }

    header nav a:hover,
    header nav a:focus {
      border-color: #2563eb;
      color: #2563eb;
      outline: none;
    }

    main {
      flex-grow: 1;
      max-width: 700px;
      margin: 3rem auto;
      padding: 0 1rem;
      display: flex;
      flex-direction: column;
      gap: 3rem;
    }

    section {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    section h2 {
      margin: 0 0 0.75rem 0;
      font-weight: 700;
      font-size: 1.8rem;
      color: #222;
      border-bottom: 2px solid #2563eb;
      padding-bottom: 0.3rem;
      user-select: text;
    }

    .botoes {
      display: flex;
      gap: 1.5rem;
      justify-content: start;
      flex-wrap: wrap;
    }

    .botao {
      background-color: #2563eb;
      color: white;
      font-weight: 700;
      font-size: 1.15rem;
      padding: 0.8rem 2.2rem;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgb(37 99 235 / 0.4);
      text-decoration: none;
      user-select: none;
      transition: background-color 0.3s ease, box-shadow 0.3s ease;
      flex-grow: 1;
      max-width: 220px;
      text-align: center;
      display: inline-block;
    }

    .botao:hover,
    .botao:focus {
      background-color: #1e40af;
      box-shadow: 0 6px 15px rgb(30 64 175 / 0.6);
      outline: none;
    }

    @media (max-width: 480px) {
      .botoes {
        flex-direction: column;
        gap: 1rem;
      }
      .botao {
        max-width: 100%;
        width: 100%;
      }
    }
  </style>
</head>

<body>
  <header role="banner">
    <div class="usuario" aria-label="Usuário logado">
      Usuário logado: <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        out.print(usuario != null ? usuario.getNome() : "Desconhecido");
      %>
    </div>
    <nav>
      <a href="${pageContext.request.contextPath}/index.jsp" role="link" aria-label="Logout">Logout</a>
    </nav>
  </header>

  <main role="main">
    <section aria-labelledby="menu-title">
      <h2 id="menu-title">Menu</h2>
      <div class="botoes">
        <a href="${pageContext.request.contextPath}/clientes" class="botao" role="button">Listagem de Clientes</a>
        <a href="${pageContext.request.contextPath}/atividade" class="botao" role="button">Listagem de Atividades</a>
      </div>
    </section>

    <section aria-labelledby="menujson-title">
      <h2 id="menujson-title">Menu JSON</h2>
      <div class="botoes">
        <a href="${pageContext.request.contextPath}/views/atividadelistarjson.html" class="botao" role="button">Listar Atividades JSON</a>
        <a href="${pageContext.request.contextPath}/views/atividadeCadastrarjson.html" class="botao" role="button">Cadastrar Atividades JSON</a>
      </div>
    </section>
  </main>
</body>

</html>
