package org.com.biryukov.CRUDproject.Repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);

    List<T> getAll();

    void save(T en);

    void update(T en);

    void deleteById(ID id);
}
