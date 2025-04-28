package br.com.cefet.activehub.service;

import java.sql.SQLException;
import java.util.List;

import br.com.cefet.activehub.DTO.ClienteRequestDTO;
import br.com.cefet.activehub.DTO.ClienteResponseDTO;
import br.com.cefet.activehub.dao.ClienteDAO;
import br.com.cefet.activehub.dao.EnderecoDAO;
import br.com.cefet.activehub.interfaces.ICrudService;

public class ClienteService implements ICrudService<ClienteRequestDTO, ClienteResponseDTO> {

    private ClienteDAO clienteDAO;
    private EnderecoDAO enderecoDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
        this.enderecoDAO = new EnderecoDAO();
    }

    @Override
    public ClienteResponseDTO insert(ClienteRequestDTO entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public ClienteResponseDTO update(ClienteRequestDTO entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(ClienteRequestDTO entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public ClienteResponseDTO findById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ClienteResponseDTO> findAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}