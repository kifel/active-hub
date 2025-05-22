<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Clientes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientes.css">
    <style>
        .cliente-table tbody tr:nth-child(2n) td {
            background-color: #f9f9f9;
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
        }
        .search-form button {
            padding: 6px 12px;
            font-size: 1em;
        }
    </style>
</head>
<body>
<div class="container">
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
                    <a class="btn btn-toggle" href="${pageContext.request.contextPath}/clienteatividade?clienteId=${cliente.id}" class="btn btn-link">Adicionar Atividade</a>
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
