package br.com.cefet.activehub.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cefet.activehub.model.Cliente;
import lombok.Data;

@Data
public class ClienteResponseDTO {
    private int id;
    private String nome;
    private String cpf;
    private String matricula;
    private Boolean isActive;
    private EnderecoClienteResponseDTO endereco;
    private List<AtividadeClienteResponseDTO> atividades = new ArrayList<>();

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.matricula = cliente.getMatricula();
        this.isActive = cliente.getIsActive();

        if (cliente.getAtividades() != null) {
            this.atividades = cliente.getAtividades().stream()
                    .map(clienteAtividade -> new AtividadeClienteResponseDTO(clienteAtividade.getAtividade()))
                    .collect(Collectors.toList());
        } else {
            this.atividades = new ArrayList<>();
        }

        if (cliente.getEndereco() != null) {
            this.endereco = new EnderecoClienteResponseDTO(cliente.getEndereco());
        } else {
            this.endereco = null;
        }
    }

}
