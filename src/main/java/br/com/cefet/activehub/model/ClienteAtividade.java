package br.com.cefet.activehub.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteAtividade {
    private int id;
    private Cliente cliente;
    private Atividade atividade;
}
