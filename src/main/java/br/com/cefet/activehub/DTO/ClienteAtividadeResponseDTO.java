package br.com.cefet.activehub.DTO;

import br.com.cefet.activehub.model.ClienteAtividade;

public class ClienteAtividadeResponseDTO {
    private int id;
    private ClienteResponseDTO cliente;
    private AtividadeResponseDTO atividade;

    public ClienteAtividadeResponseDTO(ClienteAtividade ca) {
        this.id = ca.getId();
        this.cliente = new ClienteResponseDTO(ca.getCliente());
        this.atividade = new AtividadeResponseDTO(ca.getAtividade());
    }

    public int getId() {
        return id;
    }

    public ClienteResponseDTO getCliente() {
        return cliente;
    }

    public AtividadeResponseDTO getAtividade() {
        return atividade;
    }
}
