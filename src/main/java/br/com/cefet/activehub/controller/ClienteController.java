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
        String query = request.getParameter("q"); // parâmetro da pesquisa

        try {
            if (path == null || path.equals("/")) {
                List<ClienteResponseDTO> clientes;

                if (query != null && !query.trim().isEmpty()) {
                    // Pesquisa clientes pelo termo
                    clientes = clienteService.findByNameContaining(query.trim());
                } else {
                    // Lista todos os clientes
                    clientes = clienteService.findAll();
                }

                request.setAttribute("clientes", clientes);
                request.setAttribute("searchQuery", query); // para manter o termo na view, opcional
                request.getRequestDispatcher("/views/clientes.jsp").forward(request, response);

            } else if (path.matches("/\\d+/edit")) {
                int id = ServletUtils.extractIdFromEditPath(path);
                ClienteResponseDTO cliente = clienteService.findById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/views/formulario-cliente.jsp").forward(request, response);

            } else if (path.matches("/\\d+/toggle")) {
                int id = ServletUtils.extractIdFromTogglePath(path);
                clienteService.toggleClienteStatus(id);
                response.sendRedirect(request.getContextPath() + "/clientes");

            } else if (path.equals("/novo")) {
                request.getRequestDispatcher("/views/formulario-cliente.jsp").forward(request, response);

            } else if (path.matches("/\\d+")) {
                int id = ServletUtils.extractIdFromPath(path);
                ClienteResponseDTO cliente = clienteService.findById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/views/cliente-detalhe.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno do servidor.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        try {
            ClienteRequestDTO dto = buildClienteFromRequest(request);

            if (path != null && path.matches("/\\d+")) {
                int id = Integer.parseInt(path.substring(1));
                clienteService.update(id, dto);
            } else {
                clienteService.insert(dto);
            }

            response.sendRedirect(request.getContextPath() + "/clientes");

        } catch (ClienteAlreadyExistsException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/formulario-cliente.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar o cliente.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        System.out.println("doDelete chamado com path: " + req.getPathInfo());
        try {
            if (path != null && path.matches("/\\d+")) {
                int id = ServletUtils.extractIdFromPath(path);
                clienteService.delete(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao deletar cliente.");
        }
    }

    private ClienteRequestDTO buildClienteFromRequest(HttpServletRequest request) {
        ClienteRequestDTO cliente = new ClienteRequestDTO();

        // Campos do cliente
        cliente.setNome(request.getParameter("nome"));
        cliente.setCpf(request.getParameter("cpf"));

        // Campos do endereço
        EnderecoClienteRequestDTO endereco = new EnderecoClienteRequestDTO();
        endereco.setCep(request.getParameter("cep"));
        endereco.setUf(request.getParameter("uf"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setBairro(request.getParameter("bairro"));
        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setNumero(request.getParameter("numero"));
        endereco.setComplemento(request.getParameter("complemento"));

        cliente.setEndereco(endereco);

        return cliente;
    }

}
