package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.servicio.ServicioBook;
import com.google.gson.Gson;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {

            WebServer.builder()
                    .port(8080)
                    .routing(routes -> {
                        routes.get("/books", (req, res) -> {

                            ServicioBook bookService = container.select(ServicioBook.class).get();

                            List<Book> books = bookService.findAll();
                            Gson gson = new Gson();
                            String jsonBooks = gson.toJson(books);
                            res.send(jsonBooks);
                        });

                        routes.get("/books/{id}", (req, res) -> {

                            ServicioBook bookService = container.select(ServicioBook.class).get();


                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));

                            if (id != null) {
                                Book book = bookService.findById(id);
                                if (book != null) {
                                    Gson gson = new Gson();
                                    String jsonBook = gson.toJson(book);
                                    res.send(jsonBook);
                                } else {
                                    res.status(404).send("Libro no encontrado");
                                }
                            } else {
                                res.status(400).send("Se requiere el id");
                            }
                        });

                        routes.post("/books", (req, res) -> {
                            String body = req.content().as(String.class);

                            Gson gson = new Gson();
                            Book newBook = gson.fromJson(body, Book.class);

                            ServicioBook bookService = container.select(ServicioBook.class).get();
                            bookService.insert(newBook);

                            res.status(201).send("Libro creado correctamente");
                        });

                        routes.put("/books/{id}", (req, res) -> {
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            if (id != null) {
                                String body = req.content().as(String.class);
                                Gson gson = new Gson();
                                Book updatedBook = gson.fromJson(body, Book.class);
                                updatedBook.setId(id);
                                ServicioBook bookService = container.select(ServicioBook.class).get();
                                bookService.update(updatedBook);
                                res.send("Libro actualizado correctamente");
                            } else {
                                res.status(400).send("Se requiere el id");
                            }
                        });

                        routes.delete("/books/{id}", (req, res) -> {
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            if (id != null) {
                                ServicioBook bookService = container.select(ServicioBook.class).get();
                                bookService.remove(id);
                                res.send("Libro eliminado correctamente");
                            } else {
                                res.status(400).send("Se requiere el id");
                            }
                        });



                    })
                    .build()
                    .start();

            System.out.println("Server started on port 8080");

            // Mantener el hilo principal a la espera para que el servidor web siga ejecutándose
            Thread.currentThread().join();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}