<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Cadastrar Atividade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
</head>
<body>
    <div class="container">
        <h1>Cadastrar Atividade</h1>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="success">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/atividade" method="post">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required />

            <label for="valor">Valor:</label>
            <input type="text" id="valor" name="valor" required />

            <label for="periodo">Período:</label>
            <select id="periodo" name="periodo" required>
                <option value="MANHA">Manhã</option>
                <option value="TARDE">Tarde</option>
                <option value="NOITE">Noite</option>
            </select>

            <button type="submit">Cadastrar</button>
        </form>
    </div>
</body>
</html>
