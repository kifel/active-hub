package br.com.cefet.activehub.enums;

public enum CheckEnum {
    IN("Entrada"),
    OUT("Saída");

    private final String descricao;

    CheckEnum(String descricao) {
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
    public static CheckEnum fromString(String value) {
        for (CheckEnum tipo : CheckEnum.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
