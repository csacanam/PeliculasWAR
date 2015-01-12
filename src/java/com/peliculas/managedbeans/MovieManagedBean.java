/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Movie;
import com.peliculas.sessionbeans.MovieFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author csacanam
 */
public class MovieManagedBean {

    private List<Movie> movies;

    //Atributos
    private int id;
    private String nombre;
    private String actores;

    @EJB
    MovieFacade movieFacade;

    /**
     * Creates a new instance of MovieManagedBean
     */
    public MovieManagedBean() {
    }

    @PostConstruct
    public void init() {
        movies = movieFacade.findAll();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public void onRowEdit(RowEditEvent event) {

        Movie movie = ((Movie) event.getObject());

        if (movie != null) 
        {
            
            movieFacade.edit(movie);

            FacesMessage msg = new FacesMessage("Película editada", movie.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición anulada", ((Movie) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(Movie movie) 
    {
        if (movie != null) 
        {
            movies.remove(movie);

            movieFacade.remove(movie);

            FacesMessage msg = new FacesMessage("Película eliminada", movie.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate() {

        if (!esVacio(nombre) && !esVacio(actores)) {
            Movie movie = new Movie(id, nombre, actores);

            if (!movies.contains(movie)) 
            {

                movies.add(movie);

                movieFacade.create(new Movie(id, nombre, actores));

                FacesMessage msg = new FacesMessage("Película creada", movie.getName());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                
            } else {
                FacesMessage msg = new FacesMessage("No se puede crear", "Ya existe una película con ese ID");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        }

    }

    public boolean esVacio(String cadena) {
        if (cadena == null) {
            return true;
        }

        if (cadena.equals("")) {
            return true;
        }

        return false;
    }

}
