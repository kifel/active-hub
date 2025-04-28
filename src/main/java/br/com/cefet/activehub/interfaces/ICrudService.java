package br.com.cefet.activehub.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface ICrudService<T, R> {
    R insert(T entity) throws SQLException;

    R update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    R findById(int id) throws SQLException;

    List<R> findAll() throws SQLException;
}
