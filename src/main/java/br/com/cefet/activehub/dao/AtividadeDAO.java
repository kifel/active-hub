package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;

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
}
