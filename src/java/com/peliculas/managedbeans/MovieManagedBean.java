/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Movie;
import com.peliculas.sessionbeans.MovieFacade;
import com.peliculas.utils.Validaciones;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author csacanam
 */
public class MovieManagedBean implements Converter
{

    private List<Movie> movies;

    //Atributos
    private int id;
    private String nombre;
    private String actores;

    //Session Bean
    @EJB
    MovieFacade movieFacade;

    /**
     * Creates a new instance of MovieManagedBean
     */
    public MovieManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        movies = movieFacade.findAll();
    }

    public void onRowEdit(RowEditEvent event)
    {

        Movie movie = ((Movie) event.getObject());

        if (movie != null)
        {

            movieFacade.edit(movie);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Película editada", movie.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Edición anulada", ((Movie) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(Movie movie)
    {
        if (movie != null)
        {
            movies.remove(movie);

            movieFacade.remove(movie);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Película eliminada", movie.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate()
    {

        if (!Validaciones.esVacio(nombre) && !Validaciones.esVacio(actores))
        {
            Movie movie = new Movie(id, nombre, actores);

            if (!movies.contains(movie))
            {

                movies.add(movie);

                movieFacade.create(movie);

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Película creada", movie.getName());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else
            {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo crear", "Ya existe una película con ese ID");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campos vacíos", "Llena los campos para continuar");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    // GETTERS AND SETTERS
    public List<Movie> getMovies()
    {
        return movies;
    }

    public void setMovies(List<Movie> movies)
    {
        this.movies = movies;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getActores()
    {
        return actores;
    }

    public void setActores(String actores)
    {
        this.actores = actores;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {

        try
        {
            if (value.trim().equals(""))
            {
                return null;
            } else
            {
                int theId = Integer.parseInt(value);

                for (Movie movie : movies)
                {
                    if (movie.getId() == theId)
                    {
                        return movie;
                    }
                }
            }
        } catch (NumberFormatException e)
        {

        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null || value.equals(""))
        {
            return "";
        } else
        {
            return ((Movie) value).getId().toString();
        }
    }

}
