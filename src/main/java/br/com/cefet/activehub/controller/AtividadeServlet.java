package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import br.com.cefet.activehub.DTO.AtividadeRequestDTO;
import br.com.cefet.activehub.DTO.AtividadeResponseDTO;
import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.service.AtividadeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AtividadeServlet", urlPatterns = { "/atividade" })
public class AtividadeServlet extends HttpServlet {

    private AtividadeService atividadeService;

    @Override
    public void init() throws ServletException {
        this.atividadeService = new AtividadeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Exibir o formulário JSP para criar atividade
        req.getRequestDispatcher("/views/atividade-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String valorStr = req.getParameter("valor");
        String periodo = req.getParameter("periodo");

        try {
            // Converter para BigDecimal
            BigDecimal valor = new BigDecimal(valorStr);
            PeriodoEnum periodoEnum = PeriodoEnum.valueOf(periodo.toUpperCase());

            AtividadeRequestDTO dto = new AtividadeRequestDTO();
            dto.setNome(nome);
            dto.setValor(valor);
            dto.setPeriodo(periodoEnum);

            AtividadeResponseDTO atividadeCriada = atividadeService.insert(dto);

            req.setAttribute("atividade", atividadeCriada);
            req.setAttribute("message", "Atividade criada com sucesso!");
            req.getRequestDispatcher("/views/atividade-form.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Valor inválido");
            req.getRequestDispatcher("/views/atividade-form.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Erro ao inserir atividade: " + e.getMessage());
            req.getRequestDispatcher("/views/atividade-form.jsp").forward(req, resp);
        }
    }
}
