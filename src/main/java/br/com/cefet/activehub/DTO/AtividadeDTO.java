package br.com.cefet.activehub.DTO;

import java.math.BigDecimal;
import java.util.List;

import br.com.cefet.activehub.enums.PeriodoEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtividadeDTO {
    private int id;
    private String nome;
    private BigDecimal valor;
    private PeriodoEnum periodo;
    private List<ClienteDTO> clientes;
}
