package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.model.ClienteAtividade;

public class ClienteAtividadeDAO extends GenericDAO<ClienteAtividade> {

    @Override
    public ClienteAtividade insert(ClienteAtividade clienteAtividade) throws SQLException {
        String sql = "INSERT INTO cliente_atividade (cliente_id, atividade_id) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, clienteAtividade.getCliente().getId());
            stmt.setInt(2, clienteAtividade.getAtividade().getId());

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                clienteAtividade.setId(rs.getInt(1));
            }
        }
        return clienteAtividade;
    }

    @Override
    public ClienteAtividade update(ClienteAtividade clienteAtividade) throws SQLException {
        String sql = "UPDATE cliente_atividade SET cliente_id = ?, atividade_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteAtividade.getCliente().getId());
            stmt.setInt(2, clienteAtividade.getAtividade().getId());
            stmt.setInt(3, clienteAtividade.getId());

            stmt.executeUpdate();
        }
        return clienteAtividade;
    }

    @Override
    public boolean delete(ClienteAtividade clienteAtividade) throws SQLException {
        String sql = "DELETE FROM cliente_atividade WHERE id = ?";
        int rowsAffected = 0;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteAtividade.getId());
            rowsAffected = stmt.executeUpdate();
        }
        return rowsAffected > 0;
    }

    @Override
    public ClienteAtividade findById(int id) throws SQLException {
        String sql = "SELECT * FROM cliente_atividade WHERE id = ?";
        ClienteAtividade clienteAtividade = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clienteAtividade = ClienteAtividade.builder()
                        .id(rs.getInt("id"))
                        .build();
            }
        }
        return clienteAtividade;
    }

    @Override
    public List<ClienteAtividade> findAll() throws SQLException {
        String sql = "SELECT * FROM cliente_atividade";
        List<ClienteAtividade> clienteAtividades = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClienteAtividade clienteAtividade = ClienteAtividade.builder()
                        .id(rs.getInt("id"))
                        .build();
                clienteAtividades.add(clienteAtividade);
            }
        }
        return clienteAtividades;
    }

    public List<ClienteAtividade> findByClienteOrAtividadeSubstring(String query) throws SQLException {
        String sql = "SELECT ca.*, c.nome AS cliente_nome, a.nome AS atividade_nome " +
                "FROM cliente_atividade ca " +
                "JOIN cliente c ON ca.cliente_id = c.id " +
                "JOIN atividade a ON ca.atividade_id = a.id " +
                "WHERE c.nome LIKE ? OR a.nome LIKE ?";
        List<ClienteAtividade> results = new ArrayList<>();
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = Cliente.builder()
                        .id(rs.getInt("cliente_id"))
                        .nome(rs.getString("cliente_nome"))
                        .build();
                Atividade atividade = Atividade.builder()
                        .id(rs.getInt("atividade_id"))
                        .nome(rs.getString("atividade_nome"))
                        .build();
                ClienteAtividade ca = ClienteAtividade.builder()
                        .id(rs.getInt("id"))
                        .cliente(cliente)
                        .atividade(atividade)
                        .build();
                results.add(ca);
            }
        }
        return results;
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM cliente_atividade WHERE id = ?";
        int rowsAffected = 0;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            rowsAffected = stmt.executeUpdate();
        }

        return rowsAffected > 0;
    }

}
