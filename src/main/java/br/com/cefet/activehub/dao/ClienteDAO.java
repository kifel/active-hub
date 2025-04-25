package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.model.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

    @Override
    public void insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, matricula, isActive, endereco_id) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getMatricula());
            stmt.setBoolean(3, cliente.getIsActive());
            stmt.setInt(4, cliente.getEndereco().getId()); // Supondo que o endereço já está registrado

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
        } finally {
            closeConnection(conn, stmt, null);
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, matricula = ?, isActive = ?, endereco_id = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getMatricula());
            stmt.setBoolean(3, cliente.getIsActive());
            stmt.setInt(4, cliente.getEndereco().getId());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
    }

    @Override
    public Cliente findById(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        Cliente cliente = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = Cliente.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .matricula(rs.getString("matricula"))
                        .isActive(rs.getBoolean("isActive"))
                        .build();
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return cliente;
    }

    @Override
    public List<Cliente> findAll() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Cliente cliente = Cliente.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .matricula(rs.getString("matricula"))
                        .isActive(rs.getBoolean("isActive"))
                        .build();
                clientes.add(cliente);
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return clientes;
    }
}