package br.com.cefet.activehub.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cefet.activehub.DTO.ClienteAtividadeRequestDTO;
import br.com.cefet.activehub.DTO.ClienteAtividadeResponseDTO;
import br.com.cefet.activehub.dao.ClienteAtividadeDAO;
import br.com.cefet.activehub.interfaces.ICrudService;
import br.com.cefet.activehub.model.ClienteAtividade;

public class ClienteAtividadeService implements ICrudService<ClienteAtividadeRequestDTO, ClienteAtividadeResponseDTO> {

    private ClienteAtividadeDAO clienteAtividadeDAO;

    public ClienteAtividadeService() {
        this.clienteAtividadeDAO = new ClienteAtividadeDAO();
    }

    public ClienteAtividadeResponseDTO insert(ClienteAtividadeRequestDTO dto) throws SQLException {
        ClienteAtividade ca = ClienteAtividade.builder()
                .cliente(dto.getCliente())
                .atividade(dto.getAtividade())
                .build();

        ClienteAtividade caInserida = clienteAtividadeDAO.insert(ca);

        return new ClienteAtividadeResponseDTO(caInserida);
    }

    public List<ClienteAtividadeResponseDTO> findAllFiltered(String query) throws SQLException {
        if (query == null) {
            query = "";
        }
        List<ClienteAtividade> lista = clienteAtividadeDAO.findByClienteOrAtividadeSubstring(query);
        return lista.stream()
                .map(ClienteAtividadeResponseDTO::new)
                .collect(Collectors.toList());
    }

    public boolean delete(int id) throws SQLException {
        return clienteAtividadeDAO.deleteById(id);
    }

    public boolean deleteByClienteAndAtividade(int clienteId, int atividadeId) throws SQLException {
        return clienteAtividadeDAO.deleteByClienteAndAtividade(clienteId, atividadeId);
    }

    @Override
    public ClienteAtividadeResponseDTO update(int id, ClienteAtividadeRequestDTO entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ClienteAtividadeResponseDTO findById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ClienteAtividadeResponseDTO> findAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
