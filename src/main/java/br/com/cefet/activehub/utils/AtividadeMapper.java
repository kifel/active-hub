package br.com.cefet.activehub.utils;

import java.util.List;
import java.util.stream.Collectors;

import br.com.cefet.activehub.DTO.AtividadeDTO;
import br.com.cefet.activehub.DTO.ClienteDTO;
import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.model.ClienteAtividade;

public class AtividadeMapper {
    public static AtividadeDTO toDTO(Atividade atividade) {
        return AtividadeDTO.builder()
                .id(atividade.getId())
                .nome(atividade.getNome())
                .valor(atividade.getValor())
                .periodo(atividade.getPeriodo())
                .clientes(mapClientes(atividade.getClientes()))
                .build();
    }

    private static List<ClienteDTO> mapClientes(List<ClienteAtividade> clienteAtividades) {
        if (clienteAtividades == null)
            return List.of();

        return clienteAtividades.stream()
                .map(ClienteAtividade::getCliente)
                .map(AtividadeMapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    private static ClienteDTO toClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .matricula(cliente.getMatricula())
                .build();
    }
}
