package br.com.cefet.activehub.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Endereco {
    private int id;
    private String cep;
    private String uf;
    private String cidade;
    private String numero;
    private String logradouro;
    private String bairro;
    private String complemento;
    private Cliente cliente;
}
