package br.com.cefet.activehub.DTO;

import java.math.BigDecimal;

import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;
import lombok.Data;

@Data
public class AtividadeResponseDTO {
    private int id;
    private String nome;
    private BigDecimal valor;
    private PeriodoEnum periodo;

    public AtividadeResponseDTO(Atividade atividade) {
        this.id = atividade.getId();
        this.nome = atividade.getNome();
        this.valor = atividade.getValor();
        this.periodo = atividade.getPeriodo();
    }
}
