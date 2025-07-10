import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;

public class AtividadeTest {

    @Test
    public void deveCalcularTotalDoPacoteSemFicarNegativo() {
        // Arrange
        Atividade atividade1 = Atividade.builder()
                .valor(BigDecimal.valueOf(75.00))
                .nome("Natação")
                .periodo(PeriodoEnum.MANHA)
                .build();

        Atividade atividade2 = Atividade.builder()
                .valor(BigDecimal.valueOf(45.00))
                .nome("Natação")
                .periodo(PeriodoEnum.NOITE)
                .build();

        Atividade atividade3 = Atividade.builder()
                .valor(BigDecimal.valueOf(5.00))
                .nome("Natação")
                .periodo(PeriodoEnum.TARDE)
                .build();

        List<Atividade> atividades = List.of(atividade1, atividade2, atividade3);

        // Act
        BigDecimal total = atividades.stream()
                .map(Atividade::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Assert
        assertNotNull(total);
        assertTrue(total.compareTo(BigDecimal.ZERO) >= 0, "O valor total não pode ser negativo");
        assertEquals(BigDecimal.valueOf(125.00), total);
    }

    @Test
    public void deveLancarExcecaoQuandoValorDaAtividadeForNegativo() {
        // Arrange
        Atividade atividadeNegativa = Atividade.builder()
                .valor(BigDecimal.valueOf(-10.00))
                .nome("Yoga")
                .periodo(PeriodoEnum.MANHA)
                .build();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validarAtividade(atividadeNegativa);
        });

        assertEquals("Valor da atividade não pode ser negativo", exception.getMessage());
    }

    // Simulação da validação
    private void validarAtividade(Atividade atividade) {
        if (atividade.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor da atividade não pode ser negativo");
        }
    }
}
