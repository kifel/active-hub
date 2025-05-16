package br.com.cefet.activehub.DTO;

import java.math.BigDecimal;

import br.com.cefet.activehub.enums.PeriodoEnum;

public class AtividadeRequestDTO {
    private String nome;
    private BigDecimal valor;
    private PeriodoEnum periodo;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public PeriodoEnum getPeriodo() {
        return periodo;
    }
    public void setPeriodo(PeriodoEnum periodo) {
        this.periodo = periodo;
    }
}
