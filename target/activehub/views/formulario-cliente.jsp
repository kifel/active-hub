<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${cliente.id == null ? 'Cadastrar Cliente' : 'Editar Cliente'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cliente-form.css">
</head>
<body>
    <div class="container">
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
