/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Movie;
import com.peliculas.entities.ShowTiming;
import com.peliculas.entities.Theater;
import com.peliculas.entities.Timeslot;
import com.peliculas.sessionbeans.ShowTimingFacade;
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
public class ShowTimingManagedBean
{

    private List<ShowTiming> showTimings;

    //Atributos
    private int id;
    private int day;
    private Timeslot timeslot;
    private Theater theater;
    private Movie movie;

    //Session Bean
    @EJB
    ShowTimingFacade showTimingFacade;

    /**
     * Creates a new instance of ShowTimingManagedBean
     */
    public ShowTimingManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        showTimings = showTimingFacade.findAll();
    }

    public void onRowEdit(RowEditEvent event)
    {

        ShowTiming showTiming = ((ShowTiming) event.getObject());

        if (showTiming != null)
        {

            showTimingFacade.edit(showTiming);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Show timing editado", showTiming.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Edición anulada", ((ShowTiming) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(ShowTiming showTiming)
    {
        if (showTiming != null)
        {
            showTimings.remove(showTiming);

            showTimingFacade.remove(showTiming);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Show timing eliminado", showTiming.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate()
    {

        ShowTiming showTiming = new ShowTiming(id, day);

        if (!showTimings.contains(showTiming))
        {
            //Agregar otros atributos (foráneos)
            if (this.movie != null && this.theater != null && this.timeslot != null)
            {
                showTiming.setMovieId(movie);

                showTiming.setTheaterId(theater);

                showTiming.setTimingId(timeslot);

                //Guardar en la base de datos
                showTimingFacade.create(showTiming);
                showTimings.add(showTiming);

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Show timing creado", showTiming.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else
            {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo crear", "Los campos no pueden estar vacíos");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo crear", "Ya existe un show Timing con ese ID");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    // GETTERS AND SETTERS
    public List<ShowTiming> getShowTimings()
    {
        return showTimings;
    }

    public void setShowTimings(List<ShowTiming> showTimings)
    {
        this.showTimings = showTimings;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public Timeslot getTimeslot()
    {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot)
    {
        this.timeslot = timeslot;
    }

    public Theater getTheater()
    {
        return theater;
    }

    public void setTheater(Theater theater)
    {
        this.theater = theater;
    }

    public Movie getMovie()
    {
        return movie;
    }

    public void setMovie(Movie movie)
    {
        this.movie = movie;
    }

}
