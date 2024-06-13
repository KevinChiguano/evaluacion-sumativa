package com.distribuida.servicio;


import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ServicioBookImpl implements ServicioBook{


    @Inject
    private EntityManager em;

    @Override
    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b order by b.id asc", Book.class).getResultList();
    }

    @Override
    public void insert(Book obj) {

        var tx = em.getTransaction();

        try{
            tx.begin();
            em.persist(obj);
            tx.commit();
        }catch (Exception ex){
            tx.rollback();
        }
    }
    @Override
    public void update(Book obj) {
        var tx = em.getTransaction();

        try{
            tx.begin();
            em.merge(obj);
            tx.commit();

        }catch (Exception ex){
            tx.rollback();
        }
    }

    @Override
    public void remove(Integer id) {
        var tx = em.getTransaction();

        try{
            tx.begin();

            var obj = em.getReference(Book.class, id);

            em.remove(obj);

            tx.commit();

        }catch (Exception ex){
            tx.rollback();
        }
    }
}
