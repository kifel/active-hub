package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.model.Endereco;

public class EnderecoDAO extends GenericDAO<Endereco> {

    @Override
    public Endereco insert(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getUf());
            stmt.setString(6, endereco.getCep());

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                endereco.setId(rs.getInt(1));
            }
        } finally {
            closeConnection(conn, stmt, null);
        }
        return endereco;
    }

    @Override
    public Endereco update(Endereco endereco) throws SQLException {
        String sql = "UPDATE endereco SET rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getUf());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, endereco.getId());

            stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
        return endereco;
    }

    @Override
    public void delete(Endereco endereco) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, endereco.getId());
            stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
    }

    @Override
    public Endereco findById(int id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE id = ?";
        Endereco endereco = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                endereco = Endereco.builder()
                        .id(rs.getInt("id"))
                        .logradouro(rs.getString("rua"))
                        .numero(rs.getString("numero"))
                        .bairro(rs.getString("bairro"))
                        .cidade(rs.getString("cidade"))
                        .uf(rs.getString("estado"))
                        .cep(rs.getString("cep"))
                        .build();
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return endereco;
    }

    @Override
    public List<Endereco> findAll() throws SQLException {
        String sql = "SELECT * FROM endereco";
        List<Endereco> enderecos = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Endereco endereco = Endereco.builder()
                        .id(rs.getInt("id"))
                        .logradouro(rs.getString("rua"))
                        .numero(rs.getString("numero"))
                        .bairro(rs.getString("bairro"))
                        .cidade(rs.getString("cidade"))
                        .uf(rs.getString("estado"))
                        .cep(rs.getString("cep"))
                        .build();
                enderecos.add(endereco);
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return enderecos;
    }
}
