package com.distribuida.servicio;



import com.distribuida.db.Book;

import java.util.List;

public interface ServicioBook {

    Book findById(Integer id);

    List<Book> findAll();

    void insert(Book obj);

    void update(Book obj);

    void remove(Integer id);
}
