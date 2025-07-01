package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.cefet.activehub.DTO.AtividadeDTO;
import br.com.cefet.activehub.DTO.ClienteDTO;
import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.model.ClienteAtividade;
import br.com.cefet.activehub.utils.AtividadeMapper;

public class AtividadeDAO extends GenericDAO<Atividade> {

    @Override
    public Atividade insert(Atividade atividade) throws SQLException {
        String sql = "INSERT INTO atividade (nome, valor, periodo) VALUES (?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, atividade.getNome());
            stmt.setBigDecimal(2, atividade.getValor());
            stmt.setString(3, atividade.getPeriodo().name()); // Armazenando o nome do enum PeriodoEnum

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                atividade.setId(rs.getInt(1));
            }
        }
        return atividade;
    }

    @Override
    public Atividade update(Atividade atividade) throws SQLException {
        String sql = "UPDATE atividade SET nome = ?, valor = ?, periodo = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, atividade.getNome());
            stmt.setBigDecimal(2, atividade.getValor());
            stmt.setString(3, atividade.getPeriodo().name());
            stmt.setInt(4, atividade.getId());

            stmt.executeUpdate();
        }
        return atividade;
    }

    @Override
    public boolean delete(Atividade atividade) throws SQLException {
        String sql = "DELETE FROM atividade WHERE id = ?";
        int rowsAffected = 0;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, atividade.getId());
            rowsAffected = stmt.executeUpdate();
        }
        return rowsAffected > 0;
    }

    @Override
    public Atividade findById(int id) throws SQLException {
        String sql = "SELECT * FROM atividade WHERE id = ?";
        Atividade atividade = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                atividade = Atividade.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .valor(rs.getBigDecimal("valor"))
                        .periodo(PeriodoEnum.valueOf(rs.getString("periodo"))) // Convertendo para o enum PeriodoEnum
                        .build();
            }
        }
        return atividade;
    }

    @Override
    public List<Atividade> findAll() throws SQLException {
        String sql = "SELECT * FROM atividade";
        List<Atividade> atividades = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Atividade atividade = Atividade.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .valor(rs.getBigDecimal("valor"))
                        .periodo(PeriodoEnum.valueOf(rs.getString("periodo")))
                        .build();
                atividades.add(atividade);
            }
        }
        return atividades;
    }

    public List<AtividadeDTO> findAllJson() throws SQLException {
        String sql = """
                    SELECT
                        a.id AS atividade_id, a.nome AS atividade_nome, a.valor, a.periodo,
                        ca.id AS cliente_atividade_id,
                        c.id AS cliente_id, c.nome AS cliente_nome, c.cpf, c.matricula, c.is_active
                    FROM atividade a
                    LEFT JOIN cliente_atividade ca ON ca.atividade_id = a.id
                    LEFT JOIN cliente c ON ca.cliente_id = c.id
                """;

        Map<Integer, Atividade> atividadeMap = new HashMap<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int atividadeId = rs.getInt("atividade_id");

                Atividade atividade = atividadeMap.computeIfAbsent(atividadeId, id -> {
                    try {
                        return Atividade.builder()
                                .id(atividadeId)
                                .nome(rs.getString("atividade_nome"))
                                .valor(rs.getBigDecimal("valor"))
                                .periodo(
                                        rs.getString("periodo") != null ? PeriodoEnum.valueOf(rs.getString("periodo"))
                                                : null)
                                .clientes(new ArrayList<>())
                                .build();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                int clienteAtividadeId = rs.getInt("cliente_atividade_id");
                if (!rs.wasNull()) {
                    Cliente cliente = Cliente.builder()
                            .id(rs.getInt("cliente_id"))
                            .nome(rs.getString("cliente_nome"))
                            .cpf(rs.getString("cpf"))
                            .matricula(rs.getString("matricula"))
                            .isActive(rs.getObject("is_active") != null ? rs.getBoolean("is_active") : true)
                            .build();

                    ClienteAtividade clienteAtividade = ClienteAtividade.builder()
                            .id(clienteAtividadeId)
                            .cliente(cliente)
                            .atividade(atividade)
                            .build();

                    atividade.getClientes().add(clienteAtividade);
                }
            }
        }

        // Agora converte a coleção de Atividade para DTO usando o Mapper
        return atividadeMap.values().stream()
                .map(AtividadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AtividadeDTO> findByNameJson(String nome) throws SQLException {
        String sql = "SELECT " +
                "  a.id AS atividade_id, a.nome AS atividade_nome, a.valor, a.periodo, " +
                "  ca.id AS cliente_atividade_id, " +
                "  c.id AS cliente_id, c.nome AS cliente_nome, c.cpf, c.matricula, c.is_active " +
                "FROM atividade a " +
                "LEFT JOIN cliente_atividade ca ON ca.atividade_id = a.id " +
                "LEFT JOIN cliente c ON ca.cliente_id = c.id " +
                "WHERE a.nome LIKE ? " +
                "ORDER BY a.id";

        Map<Integer, AtividadeDTO> atividadesMap = new LinkedHashMap<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int atividadeId = rs.getInt("atividade_id");
                    AtividadeDTO atividade = atividadesMap.get(atividadeId);
                    if (atividade == null) {
                        atividade = AtividadeDTO.builder()
                                .id(atividadeId)
                                .nome(rs.getString("atividade_nome"))
                                .valor(rs.getBigDecimal("valor"))
                                .periodo(PeriodoEnum.valueOf(rs.getString("periodo")))
                                .clientes(new ArrayList<>())
                                .build();
                        atividadesMap.put(atividadeId, atividade);
                    }

                    int clienteId = rs.getInt("cliente_id");
                    if (clienteId > 0) { // tem cliente associado
                        ClienteDTO cliente = ClienteDTO.builder()
                                .id(clienteId)
                                .nome(rs.getString("cliente_nome"))
                                .cpf(rs.getString("cpf"))
                                .matricula(rs.getString("matricula"))
                                .build();
                        atividade.getClientes().add(cliente);
                    }
                }
            }
        }

        return new ArrayList<>(atividadesMap.values());
    }
}
