package br.com.cefet.activehub.DTO;

import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;

public class ClienteAtividadeRequestDTO {
    private Cliente cliente;
    private Atividade atividade;

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Atividade getAtividade() {
        return atividade;
    }
    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }
}
