<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Atividade</title>
    <!-- Estilos globais -->
    <link rel="stylesheet" type="text/css" href="../css/global.css">
    <!-- Estilos específicos para o cadastro de atividade -->
    <link rel="stylesheet" type="text/css" href="../css/cadastro-atividade.css">
</head>
<body>
    <h1>Cadastro de Atividade</h1>
    <form action="atividade" method="post">
        <label for="nome">Nome da Atividade:</label>
        <input type="text" id="nome" name="nome" required="required" placeholder="Nome da atividade" /><br><br>

        <label for="valor">Valor:</label>
        <input type="text" id="valor" name="valor" required="required" placeholder="Valor da atividade" /><br><br>

        <label for="periodo">Período:</label>
        <select id="periodo" name="periodo">
            <option value="MORNING">Manhã</option>
            <option value="AFTERNOON">Tarde</option>
            <option value="EVENING">Noite</option>
        </select><br><br>

        <input type="submit" value="Cadastrar Atividade" />
    </form>

    <br>
    <a href="../index.jsp">Voltar para a página inicial</a>
</body>
</html>
