package br.com.cefet.activehub.DTO;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    private String cpf;
    private EnderecoClienteRequestDTO endereco;
}
