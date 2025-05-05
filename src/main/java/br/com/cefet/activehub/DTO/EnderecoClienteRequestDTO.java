package br.com.cefet.activehub.DTO;

import br.com.cefet.activehub.model.Endereco;
import lombok.Data;

@Data
public class EnderecoClienteRequestDTO {
    private String cep;
    private String uf;
    private String cidade;
    private String numero;
    private String logradouro;
    private String bairro;
    private String complemento;

    public EnderecoClienteRequestDTO() {
    }

    public EnderecoClienteRequestDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.uf = endereco.getUf();
        this.cidade = endereco.getCidade();
        this.numero = endereco.getNumero();
        this.logradouro = endereco.getLogradouro();
        this.bairro = endereco.getBairro();
        this.complemento = endereco.getComplemento();
    }
}
