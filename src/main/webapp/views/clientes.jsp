<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Clientes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientes.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Clientes</h1>
            <a href="${pageContext.request.contextPath}/clientes/novo" class="btn btn-primary">Cadastrar Novo Cliente</a>
        </header>

        <table class="cliente-table">
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
                            <!-- Alterado o link para o formato correto -->
                            <a href="${pageContext.request.contextPath}/clientes/${cliente.id}/edit" class="btn btn-edit">Editar</a>
                            <button class="btn btn-delete" onclick="confirmDelete(${cliente.id})">Excluir</button>
                            <button class="btn btn-toggle" onclick="toggleStatus(${cliente.id})">${cliente.isActive ? 'Desbloquear' : 'Bloquear'}</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal de Confirmação para Excluir ou Bloquear/Desbloquear -->
    <div id="modal" class="modal">
        <div class="modal-content">
            <h3>Tem certeza que deseja realizar esta ação?</h3>
            <button id="confirmAction" class="btn btn-danger">Confirmar</button>
            <button id="cancelAction" class="btn btn-secondary">Cancelar</button>
        </div>
    </div>

    <script>
        let modal = document.getElementById('modal');
        let confirmButton = document.getElementById('confirmAction');
        let cancelButton = document.getElementById('cancelAction');
        let currentAction = null;

        function confirmDelete(id) {
            currentAction = () => { window.location.href = '${pageContext.request.contextPath}/clientes/' + id + '/delete'; };
            modal.style.display = 'flex';
        }

        function toggleStatus(id) {
            currentAction = () => { window.location.href = '${pageContext.request.contextPath}/clientes/' + id + '/toggle'; };
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
