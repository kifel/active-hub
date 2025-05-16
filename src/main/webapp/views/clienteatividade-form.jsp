<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Associar Atividade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
</head>
<body>
    <div class="container">
        <h1>Associar Atividade ao Cliente</h1>

        <form method="post" action="${pageContext.request.contextPath}/clienteatividade">
            <input type="hidden" name="clienteId" value="${clienteId}" />

            <label for="atividadeId">Escolha uma Atividade:</label>
            <select name="atividadeId" required>
                <c:forEach var="atividade" items="${atividades}">
                    <option value="${atividade.id}">
                        ${atividade.nome} - R$ ${atividade.valor} (${atividade.periodo})
                    </option>
                </c:forEach>
            </select>

            <button type="submit" class="btn btn-primary">Vincular</button>
            <a href="${pageContext.request.contextPath}/clientes" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
