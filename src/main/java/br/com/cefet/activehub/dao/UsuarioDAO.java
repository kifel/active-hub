package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.cefet.activehub.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

    @Override
    public Usuario insert(Usuario entity) throws SQLException {
        String sql = "insert into usuario(nome,username, password) values(?,?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getUsername());
            stmt.setString(3, entity.getPassword());
            stmt.execute();
            stmt.close();
        } finally {
            closeConnection(conn, stmt, null);
        }
        return entity;
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username = ?";
        Usuario usuario = null;

        try (
                Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = Usuario.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            }

            rs.close();
        }

        return usuario;
    }

    @Override
    public Usuario update(Usuario entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Usuario entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Usuario findById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Usuario> findAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
