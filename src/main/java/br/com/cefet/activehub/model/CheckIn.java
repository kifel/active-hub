package br.com.cefet.activehub.model;

import java.sql.Timestamp;

import br.com.cefet.activehub.enums.CheckEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckIn {
    private int id;
    private Timestamp data;
    private Cliente cliente;
    private CheckEnum tipo;
}
