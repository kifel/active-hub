package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cefet.activehub.DTO.ClienteRequestDTO;
import br.com.cefet.activehub.exception.ClienteAlreadyExistsException;
import br.com.cefet.activehub.exception.ClienteNotFoundException;
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
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        clienteService = new ClienteService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Buscar todos
                List<?> clientes = clienteService.findAll();
                ServletUtils.sendResponse(response, clientes, HttpServletResponse.SC_OK);
            } else {
                // Buscar por ID
                int id = ServletUtils.extractIdFromPath(pathInfo);
                var cliente = clienteService.findById(id);
                ServletUtils.sendResponse(response, cliente, HttpServletResponse.SC_OK);
            }
        } catch (ClienteNotFoundException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (SQLException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no banco de dados.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo != null && pathInfo.contains("/toggle")) {
                int id = ServletUtils.extractIdFromTogglePath(pathInfo);
                var cliente = clienteService.toggleClienteStatus(id);
                ServletUtils.sendResponse(response, cliente, HttpServletResponse.SC_OK);
            } else {
                ClienteRequestDTO clienteDTO = objectMapper.readValue(request.getReader(), ClienteRequestDTO.class);
                var clienteCriado = clienteService.insert(clienteDTO);
                ServletUtils.sendResponse(response, clienteCriado, HttpServletResponse.SC_CREATED);
            }
        } catch (ClienteAlreadyExistsException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (SQLException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no banco de dados.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        try {
            int id = ServletUtils.extractIdFromPath(pathInfo);
            ClienteRequestDTO clienteDTO = objectMapper.readValue(request.getReader(), ClienteRequestDTO.class);
            var clienteAtualizado = clienteService.update(id, clienteDTO);
            ServletUtils.sendResponse(response, clienteAtualizado, HttpServletResponse.SC_OK);
        } catch (ClienteNotFoundException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ClienteAlreadyExistsException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (SQLException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no banco de dados.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        try {
            int id = ServletUtils.extractIdFromPath(pathInfo);
            boolean deleted = clienteService.delete(id);

            if (deleted) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                ServletUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao excluir o cliente.");
            }
        } catch (ClienteNotFoundException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (SQLException e) {
            ServletUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no banco de dados.");
        }
    }
}
