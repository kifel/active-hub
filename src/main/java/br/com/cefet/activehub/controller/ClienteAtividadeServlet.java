package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.cefet.activehub.DTO.AtividadeResponseDTO;
import br.com.cefet.activehub.DTO.ClienteAtividadeRequestDTO;
import br.com.cefet.activehub.DTO.ClienteAtividadeResponseDTO;
import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.service.AtividadeService;
import br.com.cefet.activehub.service.ClienteAtividadeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteAtividadeServlet", urlPatterns = { "/clienteatividade" })
public class ClienteAtividadeServlet extends HttpServlet {

    private ClienteAtividadeService clienteAtividadeService;

    @Override
    public void init() throws ServletException {
        this.clienteAtividadeService = new ClienteAtividadeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        String clienteIdParam = req.getParameter("clienteId");

        try {
            if (clienteIdParam != null) {
                int clienteId = Integer.parseInt(clienteIdParam);

                // Carrega todas as atividades disponíveis
                AtividadeService atividadeService = new AtividadeService();
                List<AtividadeResponseDTO> atividades = atividadeService.findAll();

                req.setAttribute("clienteId", clienteId);
                req.setAttribute("atividades", atividades);
                req.getRequestDispatcher("/views/clienteatividade-form.jsp").forward(req, resp);
            } else {
                // Lista todas as associações cliente-atividade com filtro (modo padrão)
                List<ClienteAtividadeResponseDTO> lista = clienteAtividadeService.findAllFiltered(query);
                req.setAttribute("lista", lista);
                req.setAttribute("query", query);
                req.getRequestDispatcher("/views/clienteatividade-list.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            resp.sendError(500, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int clienteId = Integer.parseInt(req.getParameter("clienteId"));
            int atividadeId = Integer.parseInt(req.getParameter("atividadeId"));

            Cliente cliente = Cliente.builder()
                    .id(clienteId)
                    .build();

            Atividade atividade = Atividade.builder()
                    .id(atividadeId)
                    .build();

            ClienteAtividadeRequestDTO dto = new ClienteAtividadeRequestDTO();
            dto.setCliente(cliente);
            dto.setAtividade(atividade);

            clienteAtividadeService.insert(dto);

            resp.sendRedirect(req.getContextPath() + "/clientes");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "IDs inválidos");
            doGet(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Erro ao inserir associação: " + e.getMessage());
            doGet(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(400, "Parâmetro id é obrigatório");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean excluido = clienteAtividadeService.delete(id);

            if (excluido) {
                resp.setStatus(204);
            } else {
                resp.sendError(404, "Associação não encontrada para exclusão");
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID inválido");
        } catch (SQLException e) {
            resp.sendError(500, "Erro ao excluir associação: " + e.getMessage());
        }
    }
}
