package br.com.cefet.activehub.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {
    private int id;
    private String nome;
    private String cpf;
    private String matricula;
}
