package br.com.cefet.activehub.DTO;

import java.math.BigDecimal;

import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;
import lombok.Data;

@Data
public class AtividadeClienteResponseDTO {
    private int id;
    private String nome;
    private BigDecimal valor;
    private PeriodoEnum periodo;

    public AtividadeClienteResponseDTO(Atividade a) {
        this.id = a.getId();
        this.nome = a.getNome();
        this.valor = a.getValor();
        this.periodo = a.getPeriodo();
    }

}
