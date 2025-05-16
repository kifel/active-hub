package br.com.cefet.activehub.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import br.com.cefet.activehub.DTO.ClienteRequestDTO;
import br.com.cefet.activehub.DTO.ClienteResponseDTO;
import br.com.cefet.activehub.dao.ClienteDAO;
import br.com.cefet.activehub.dao.EnderecoDAO;
import br.com.cefet.activehub.exception.ClienteAlreadyExistsException;
import br.com.cefet.activehub.exception.ClienteNotFoundException;
import br.com.cefet.activehub.interfaces.ICrudService;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.model.Endereco;
import br.com.cefet.activehub.utils.RandomUtil;

public class ClienteService implements ICrudService<ClienteRequestDTO, ClienteResponseDTO> {

    private ClienteDAO clienteDAO;
    private EnderecoDAO enderecoDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
        this.enderecoDAO = new EnderecoDAO();
    }

    private void verifyCpfDuplicado(String cpf) throws SQLException, ClienteAlreadyExistsException {
        if (clienteDAO.existsByCpf(cpf)) {
            throw new ClienteAlreadyExistsException("Já existe um cliente com o CPF: " + cpf);
        }
    }

    @Override
    public ClienteResponseDTO findById(int id) throws SQLException, ClienteNotFoundException {
        Cliente cliente = clienteDAO.findById(id);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        return new ClienteResponseDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findAll() throws SQLException {
        return Optional.ofNullable(clienteDAO.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(ClienteResponseDTO::new)
                .toList();
    }

    @Override
    public ClienteResponseDTO insert(ClienteRequestDTO entity) throws SQLException, ClienteAlreadyExistsException {
        this.verifyCpfDuplicado(entity.getCpf());

        Endereco endereco = Endereco.builder()
                .cep(entity.getEndereco().getCep())
                .uf(entity.getEndereco().getUf())
                .cidade(entity.getEndereco().getCidade())
                .numero(entity.getEndereco().getNumero())
                .logradouro(entity.getEndereco().getLogradouro())
                .bairro(entity.getEndereco().getBairro())
                .complemento(entity.getEndereco().getComplemento())
                .build();

        Endereco enderecoInserido = null;
        Cliente clienteInserido = null;

        try {
            enderecoInserido = this.enderecoDAO.insert(endereco);

            String matriculaGerada = RandomUtil.generateMatricula();

            Cliente cliente = Cliente.builder()
                    .nome(entity.getNome())
                    .cpf(entity.getCpf())
                    .matricula(matriculaGerada)
                    .isActive(true)
                    .endereco(enderecoInserido)
                    .build();

            clienteInserido = this.clienteDAO.insert(cliente);
        } catch (SQLException e) {
            // Se ocorrer erro no cadastro do cliente, excluir o endereço
            if (enderecoInserido != null) {
                this.enderecoDAO.delete(enderecoInserido); // Excluir o endereço inserido
            }
            throw e; // Re-throw the exception
        }

        // Retornar o DTO do cliente inserido
        return new ClienteResponseDTO(clienteInserido);
    }

    @Override
    public ClienteResponseDTO update(int id, ClienteRequestDTO entity)
            throws SQLException, ClienteNotFoundException, ClienteAlreadyExistsException {
        Cliente clienteToUpdade = this.clienteDAO.findById(id);
        if (clienteToUpdade == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        if (!clienteToUpdade.getCpf().equals(entity.getCpf())) {
            this.verifyCpfDuplicado(entity.getCpf());
        }

        Endereco enderecoAtualizado = Endereco.builder()
                .id(clienteToUpdade.getEndereco().getId())
                .cep(entity.getEndereco().getCep())
                .uf(entity.getEndereco().getUf())
                .cidade(entity.getEndereco().getCidade())
                .numero(entity.getEndereco().getNumero())
                .logradouro(entity.getEndereco().getLogradouro())
                .bairro(entity.getEndereco().getBairro())
                .complemento(entity.getEndereco().getComplemento())
                .build();

        this.enderecoDAO.update(enderecoAtualizado);

        // Atualizar o cliente
        clienteToUpdade.setNome(entity.getNome());
        clienteToUpdade.setCpf(entity.getCpf());
        clienteToUpdade.setEndereco(enderecoAtualizado);

        Cliente clienteAtualizado = this.clienteDAO.update(clienteToUpdade);

        return new ClienteResponseDTO(clienteAtualizado);
    }

    @Override
    public boolean delete(int id) throws SQLException, ClienteNotFoundException {
        Cliente cliente = this.clienteDAO.findById(id);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        if (cliente.getAtividades() != null && !cliente.getAtividades().isEmpty()) {
            throw new SQLException("Não é possível excluir o cliente. Existem atividades vinculadas a ele.");
        }

        boolean clienteDeletado = false;
        boolean enderecoDeletado = false;

        clienteDeletado = this.clienteDAO.delete(cliente);

        if (cliente.getEndereco() != null) {
            try {
                enderecoDeletado = this.enderecoDAO.delete(cliente.getEndereco());
            } catch (SQLException e) {
                System.err.println("Erro ao tentar excluir o endereço do cliente ID " + id + ": " + e.getMessage());
            }
        }

        return clienteDeletado && enderecoDeletado;
    }

    public ClienteResponseDTO toggleClienteStatus(int id) throws SQLException, ClienteNotFoundException {
        Cliente cliente = this.clienteDAO.findById(id);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o ID: " + id);
        }

        boolean novoStatus = !cliente.getIsActive();
        cliente.setIsActive(novoStatus);
        Cliente clienteAtualizado = this.clienteDAO.update(cliente);

        return new ClienteResponseDTO(clienteAtualizado);
    }

    public List<ClienteResponseDTO> findByNameContaining(String nome) throws SQLException {
        return Optional.ofNullable(clienteDAO.findByNameContaining(nome))
                .orElse(Collections.emptyList())
                .stream()
                .map(ClienteResponseDTO::new)
                .toList();
    }

}