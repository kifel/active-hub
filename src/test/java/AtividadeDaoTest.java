import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cefet.activehub.dao.AtividadeDAO;
import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;

public class AtividadeDaoTest {

    private AtividadeDAO dao;

    @BeforeEach
    public void setup() {
        dao = new AtividadeDAO();
    }

    @Test
    public void deveInserirBuscarAtualizarEExcluirAtividade() throws SQLException {
        // Inserir
        Atividade novaAtividade = Atividade.builder()
                .nome("Boxe")
                .valor(BigDecimal.valueOf(80))
                .periodo(PeriodoEnum.NOITE)
                .build();

        Atividade inserida = dao.insert(novaAtividade);
        assertNotNull(inserida);
        assertTrue(inserida.getId() > 0);

        // Buscar por ID
        Atividade buscada = dao.findById(inserida.getId());
        assertNotNull(buscada);
        assertEquals("Boxe", buscada.getNome());

        // Alterar
        buscada.setNome("Boxe Avançado");
        buscada.setValor(BigDecimal.valueOf(90));
        Atividade atualizada = dao.update(buscada);
        assertEquals("Boxe Avançado", atualizada.getNome());
        assertEquals(BigDecimal.valueOf(90), atualizada.getValor());

        // Buscar novamente e conferir se atualizou
        Atividade reBuscada = dao.findById(buscada.getId());
        assertEquals("Boxe Avançado", reBuscada.getNome());
        assertEquals(0, atualizada.getValor().compareTo(new BigDecimal("90.00")));

        // Excluir
        boolean excluido = dao.delete(reBuscada);
        assertTrue(excluido);

        // Verificar que foi excluído
        Atividade apagada = dao.findById(reBuscada.getId());
        assertNull(apagada);
    }
}
