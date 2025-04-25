<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Cliente</title>
    <!-- Estilos globais -->
    <link rel="stylesheet" type="text/css" href="../css/global.css">
    <!-- Estilos específicos para o cadastro de cliente -->
    <link rel="stylesheet" type="text/css" href="../css/cadastro-cliente.css">
</head>
<body>
    <h1>Cadastro de Cliente</h1>
    <form action="cliente" method="post">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required="required" placeholder="Nome completo" /><br><br>

        <label for="matricula">Matrícula:</label>
        <input type="text" id="matricula" name="matricula" required="required" placeholder="Matrícula do cliente" /><br><br>

        <label for="isActive">Ativo:</label>
        <input type="checkbox" id="isActive" name="isActive" checked /><br><br>

        <h3>Endereço</h3>
        <label for="cep">CEP:</label>
        <input type="text" id="cep" name="cep" required="required" placeholder="CEP" /><br><br>

        <label for="uf">UF:</label>
        <input type="text" id="uf" name="uf" required="required" placeholder="UF" /><br><br>

        <label for="cidade">Cidade:</label>
        <input type="text" id="cidade" name="cidade" required="required" placeholder="Cidade" /><br><br>

        <label for="logradouro">Logradouro:</label>
        <input type="text" id="logradouro" name="logradouro" required="required" placeholder="Logradouro" /><br><br>

        <label for="numero">Número:</label>
        <input type="text" id="numero" name="numero" required="required" placeholder="Número" /><br><br>

        <label for="bairro">Bairro:</label>
        <input type="text" id="bairro" name="bairro" required="required" placeholder="Bairro" /><br><br>

        <label for="complemento">Complemento:</label>
        <input type="text" id="complemento" name="complemento" placeholder="Complemento" /><br><br>

        <input type="submit" value="Cadastrar Cliente" />
    </form>

    <br>
    <a href="../index.jsp">Voltar para a página inicial</a>
</body>
</html>
