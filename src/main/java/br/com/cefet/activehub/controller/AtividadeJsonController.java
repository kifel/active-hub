package br.com.cefet.activehub.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import br.com.cefet.activehub.DTO.AtividadeDTO;
import br.com.cefet.activehub.DTO.AtividadeInput;
import br.com.cefet.activehub.dao.AtividadeDAO;
import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/atividadejson")
public class AtividadeJsonController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String nomeFiltro = request.getParameter("nome"); // pode ser null

        AtividadeDAO atividadeDao = new AtividadeDAO();
        List<AtividadeDTO> atividades = null;
        try {
            System.out.println("ant filtro");
            if (nomeFiltro != null && !nomeFiltro.isBlank()) {
                 System.out.println("filtro");
                atividades = atividadeDao.findByNameJson(nomeFiltro.trim());
            } else {
                System.out.println("Sem filtro");
                atividades = atividadeDao.findAllJson();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(atividades);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ler o JSON do body
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        Gson gson = new Gson();

        AtividadeInput input = null;
        try {
            input = gson.fromJson(jsonBody.toString(), AtividadeInput.class);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().print(gson.toJson(Map.of("error", "JSON inválido")));
            return;
        }

        if (input == null || input.nome == null || input.nome.isBlank()
                || input.valor == null || input.periodo == null || input.periodo.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().print(gson.toJson(Map.of("error", "Campos obrigatórios faltando")));
            return;
        }

        Atividade novaAtividade = Atividade.builder()
                .nome(input.nome)
                .valor(BigDecimal.valueOf(input.valor))
                .periodo(PeriodoEnum.valueOf(input.periodo))
                .build();

        AtividadeDAO dao = new AtividadeDAO();
        try {
            dao.insert(novaAtividade);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter()
                    .print(gson.toJson(Map.of("error", "Erro ao salvar atividade", "details", e.getMessage())));
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(gson.toJson(Map.of("message", "Atividade criada com sucesso")));
    }

}
