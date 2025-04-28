package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
}
