package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.util.List;

import br.com.cefet.activehub.DTO.ClienteRequestDTO;
import br.com.cefet.activehub.DTO.ClienteResponseDTO;
import br.com.cefet.activehub.DTO.EnderecoClienteRequestDTO;
import br.com.cefet.activehub.exception.ClienteAlreadyExistsException;
import br.com.cefet.activehub.service.ClienteService;
import br.com.cefet.activehub.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/clientes/*")
public class ClienteController extends HttpServlet {

    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {
        clienteService = new ClienteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                // Lista todos os clientes
                List<ClienteResponseDTO> clientes = clienteService.findAll();
                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("/views/clientes.jsp").forward(request, response);

            } else if (path.matches("/\\d+/edit")) {
                // Formulário de edição
                int id = ServletUtils.extractIdFromEditPath(path);
                ClienteResponseDTO cliente = clienteService.findById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/views/cliente-form.jsp").forward(request, response);

            } else if (path.matches("/\\d+/delete")) {
                // Exclusão
                int id = ServletUtils.extractIdFromDeletePath(path);
                clienteService.delete(id);
                response.sendRedirect(request.getContextPath() + "/clientes");

            } else if (path.matches("/\\d+/toggle")) {
                // Ativa/desativa cliente
                int id = ServletUtils.extractIdFromTogglePath(path);
                clienteService.toggleClienteStatus(id);
                response.sendRedirect(request.getContextPath() + "/clientes");

            } else if (path.equals("/novo")) {
                // Formulário de cadastro
                request.getRequestDispatcher("/views/cliente-form.jsp").forward(request, response);

            } else {
                // Detalhes
                int id = ServletUtils.extractIdFromPath(path);
                ClienteResponseDTO cliente = clienteService.findById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/views/cliente-detalhe.jsp").forward(request, response);
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        try {
            ClienteRequestDTO dto = buildClienteFromRequest(request);

            if (path != null && path.matches("/\\d+/edit")) {
                int id = ServletUtils.extractIdFromEditPath(path);
                clienteService.update(id, dto);
            } else {
                clienteService.insert(dto);
            }

            response.sendRedirect(request.getContextPath() + "/clientes");

        } catch (ClienteAlreadyExistsException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/views/cliente-form.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar o cliente.");
        }
    }

    private ClienteRequestDTO buildClienteFromRequest(HttpServletRequest request) {
        ClienteRequestDTO cliente = new ClienteRequestDTO();
        cliente.setNome(request.getParameter("nome"));
        cliente.setCpf(request.getParameter("cpf"));

        EnderecoClienteRequestDTO endereco = new EnderecoClienteRequestDTO();
        endereco.setCep(request.getParameter("cep"));
        endereco.setUf(request.getParameter("uf"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setNumero(request.getParameter("numero"));
        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setBairro(request.getParameter("bairro"));
        endereco.setComplemento(request.getParameter("complemento"));

        cliente.setEndereco(endereco);
        return cliente;
    }
}
