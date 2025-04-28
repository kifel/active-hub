package br.com.cefet.activehub.model;

import java.math.BigDecimal;
import java.util.List;

import br.com.cefet.activehub.enums.PeriodoEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Atividade {
    private int id;
    private String nome;
    private BigDecimal valor;
    private List<ClienteAtividade> clientes; 
    private PeriodoEnum periodo;
}
