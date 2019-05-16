package com.elon.item.api;

public interface BaseService<T,K> {

    void add(T t);

    void delete(K k);

    void update(T t);

    T query(K k);

}
