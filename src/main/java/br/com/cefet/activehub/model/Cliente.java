package br.com.cefet.activehub.model;

import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String matricula;

    @Default
    private Boolean isActive = true;

    private Endereco endereco;
    private List<ClienteAtividade> atividades;
}
