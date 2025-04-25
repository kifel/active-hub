package br.com.cefet.activehub.enums;

public enum PeriodoEnum {
    MANHA("Manhã"),
    TARDE("Tarde"),
    NOITE("Noite");

    private final String descricao;

    PeriodoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    // Método para buscar o enum pelo nome
    public static PeriodoEnum fromString(String value) {
        for (PeriodoEnum periodo : PeriodoEnum.values()) {
            if (periodo.name().equalsIgnoreCase(value)) {
                return periodo;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
