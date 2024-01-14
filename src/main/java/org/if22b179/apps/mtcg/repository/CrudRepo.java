package org.if22b179.apps.mtcg.repository;

import java.util.Optional;

public interface CrudRepo<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    T update(T entity);
    void deleteById(ID id);

}