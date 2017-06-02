package com.example.ziomek.nimaletko.DAO;

import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

interface DAO<T> {
    long save(T type);
    void update(T type);
    void delete(T type);
    T get(long id);
    List<T> getAll();
}
