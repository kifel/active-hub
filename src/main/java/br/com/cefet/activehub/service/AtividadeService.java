package br.com.cefet.activehub.service;

import java.sql.SQLException;
import java.util.List;

import br.com.cefet.activehub.DTO.AtividadeRequestDTO;
import br.com.cefet.activehub.DTO.AtividadeResponseDTO;
import br.com.cefet.activehub.dao.AtividadeDAO;
import br.com.cefet.activehub.interfaces.ICrudService;
import br.com.cefet.activehub.model.Atividade;

public class AtividadeService implements ICrudService<AtividadeRequestDTO, AtividadeResponseDTO> {

    private AtividadeDAO atividadeDAO;

    public AtividadeService() {
        this.atividadeDAO = new AtividadeDAO();
    }

    public AtividadeResponseDTO insert(AtividadeRequestDTO dto) throws SQLException {
        Atividade atividade = Atividade.builder()
                .nome(dto.getNome())
                .valor(dto.getValor())
                .periodo(dto.getPeriodo())
                .build();

        Atividade atividadeInserida = atividadeDAO.insert(atividade);

        return new AtividadeResponseDTO(atividadeInserida);
    }

    @Override
    public AtividadeResponseDTO update(int id, AtividadeRequestDTO entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public AtividadeResponseDTO findById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<AtividadeResponseDTO> findAll() throws SQLException {
        List<Atividade> atividades = atividadeDAO.findAll();
        return atividades.stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }
}
