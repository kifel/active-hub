<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="jakarta.servlet.http.Cookie, br.com.cefet.activehub.model.Usuario" %>

<%
    // Leitura dos cookies corFundo e corFonte
    String corFundo = "#ffffff"; // padrão branco
    String corFonte = "#000000"; // padrão preto

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
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Lista de Clientes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientes.css" />

    <style>
        /* Aplicando as cores do cookie */
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

        /* Tabela - usando um background leve baseado na corFundo */
        .cliente-table tbody tr:nth-child(2n) td {
            background-color: <%= corFundo.equals("#ffffff") ? "#f9f9f9" : "#dddddd" %>;
            font-size: 0.9em;
            padding-left: 30px;
        }

        .search-form {
            margin-bottom: 1em;
        }

        .search-form input[type="text"] {
            padding: 5px;
            font-size: 1em;
            width: 250px;
            border: 1px solid <%= corFonte %>;
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            border-radius: 0.3rem;
        }

        .search-form button {
            padding: 6px 12px;
            font-size: 1em;
            background-color: <%= corFonte %>;
            color: <%= corFundo %>;
            border: none;
            border-radius: 0.3rem;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
        }

        .search-form button:hover {
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            border: 1px solid <%= corFonte %>;
        }

        /* Botões da tabela */
        .btn {
            padding: 0.3rem 0.7rem;
            margin: 0 0.2rem;
            border-radius: 0.3rem;
            border: none;
            font-size: 0.9rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-primary {
            background-color: <%= corFonte %>;
            color: <%= corFundo %>;
        }

        .btn-primary:hover {
            background-color: <%= corFundo %>;
            color: <%= corFonte %>;
            border: 1px solid <%= corFonte %>;
        }

        .btn-edit {
            background-color: #2563eb;
            color: white;
        }

        .btn-edit:hover {
            background-color: #1e40af;
        }

        .btn-delete {
            background-color: #ef4444;
            color: white;
        }

        .btn-delete:hover {
            background-color: #dc2626;
        }

        .btn-toggle {
            background-color: #6b7280;
            color: white;
        }

        .btn-toggle:hover {
            background-color: #4b5563;
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
        <header>
            <h1>Clientes</h1>
            <a href="${pageContext.request.contextPath}/clientes/novo" class="btn btn-primary">Cadastrar Novo Cliente</a>
        </header>

        <form method="get" action="${pageContext.request.contextPath}/clientes" class="search-form">
            <input type="text" name="q" value="${searchQuery != null ? searchQuery : ''}" placeholder="Pesquisar cliente..."/>
            <button type="submit">Buscar</button>
        </form>

        <table class="cliente-table" cellspacing="0" cellpadding="8" border="1" width="100%">
            <thead>
            <tr>
                <th>Nome</th>
                <th>CPF</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cliente" items="${clientes}">
                <tr>
                    <td>${cliente.nome}</td>
                    <td>${cliente.cpf}</td>
                    <td>${cliente.isActive ? "Ativo" : "Inativo"}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/clientes/${cliente.id}/edit" class="btn btn-edit">Editar</a>
                        <form method="post" action="${pageContext.request.contextPath}/clientes/${cliente.id}" onsubmit="return confirm('Tem certeza que deseja excluir este cliente?');" style="display:inline;">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit" class="btn btn-delete">Excluir</button>
                        </form>
                        <button class="btn btn-toggle" onclick="toggleStatus(${cliente.id})">${cliente.isActive ? 'Bloquear' : 'Desbloquear'}</button>
                        <a class="btn btn-toggle" href="${pageContext.request.contextPath}/clienteatividade?clienteId=${cliente.id}">Adicionar Atividade</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <strong>Atividades:</strong>
                        <c:if test="${not empty cliente.atividades}">
                            <ul>
                                <c:forEach var="ca" items="${cliente.atividades}">
                                    <li>
                                        ${ca.nome} - Valor: R$ ${ca.valor} - Período: ${ca.periodo}
                                        <form method="post" action="${pageContext.request.contextPath}/clienteatividade" onsubmit="return confirm('Tem certeza que deseja excluir esta atividade?');" style="display:inline;">
                                            <input type="hidden" name="_method" value="DELETE">
                                            <input type="hidden" name="clienteId" value="${cliente.id}">
                                            <input type="hidden" name="atividadeId" value="${ca.id}">
                                            <button type="submit" class="btn btn-delete">Excluir</button>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <c:if test="${empty cliente.atividades}">
                            <em>Sem atividades cadastradas</em>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="modal" class="modal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
         background-color: rgba(0,0,0,0.5); justify-content:center; align-items:center;">
        <div class="modal-content" style="background:#fff; padding:20px; border-radius:8px; max-width:320px; text-align:center;">
            <h3>Tem certeza que deseja realizar esta ação?</h3>
            <button id="confirmAction" class="btn btn-danger" style="margin-right:10px;">Confirmar</button>
            <button id="cancelAction" class="btn btn-secondary">Cancelar</button>
        </div>
    </div>

    <script>
        const contextPath = '${pageContext.request.contextPath}';
        let modal = document.getElementById('modal');
        let confirmButton = document.getElementById('confirmAction');
        let cancelButton = document.getElementById('cancelAction');
        let currentAction = null;

        function toggleStatus(id) {
            currentAction = () => { window.location.href = contextPath + `/clientes/` + id + `/toggle`; };
            modal.style.display = 'flex';
        }

        confirmButton.onclick = function() {
            if (currentAction) currentAction();
            modal.style.display = 'none';
        };

        cancelButton.onclick = function() {
            modal.style.display = 'none';
        };
    </script>
</body>
</html>
