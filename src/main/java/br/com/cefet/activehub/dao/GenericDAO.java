package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class GenericDAO<T> {
    // Método para inserir um objeto no banco
    public abstract T insert(T entity) throws SQLException;

    // Método para atualizar um objeto no banco
    public abstract T update(T entity) throws SQLException;

    // Método para deletar um objeto no banco
    public abstract boolean delete(T entity) throws SQLException;

    // Método para buscar um objeto pelo seu ID
    public abstract T findById(int id) throws SQLException;

    // Método para listar todos os objetos
    public abstract List<T> findAll() throws SQLException;

    // Método para fechar a conexão
    protected void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
