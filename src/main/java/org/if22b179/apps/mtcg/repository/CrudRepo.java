package org.if22b179.apps.mtcg.repository;

import java.sql.SQLException;

public interface CrudRepo<T, ID> {
    void save(T entity) throws SQLException;
    T findById(ID id) throws SQLException;
    T findByName(ID id) throws SQLException;
    void update(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException ;

}