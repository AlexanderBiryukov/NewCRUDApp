package org.com.biryukov.crudproject.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);

    List<T> getAll();

    T save(T en);

    T update(T en);

    void deleteById(ID id);
}
