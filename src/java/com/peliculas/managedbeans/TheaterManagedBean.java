/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Theater;
import com.peliculas.sessionbeans.TheaterFacade;
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
public class TheaterManagedBean
{

    private List<Theater> theaters;

    //Atributos
    private int id;
    private int capacidad;
    private int showTimingList;

    //SessionBean
    @EJB
    TheaterFacade theaterFacade;

    /**
     * Creates a new instance of TheaterManagedBean
     */
    public TheaterManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        theaters = theaterFacade.findAll();
    }

    public void onRowEdit(RowEditEvent event)
    {

        Theater theater = ((Theater) event.getObject());

        if (theater != null)
        {

            theaterFacade.edit(theater);

            FacesMessage msg = new FacesMessage("Teatro editado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edición anulada", ((Theater) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(Theater theater)
    {
        if (theater != null)
        {
            theaters.remove(theater);

            theaterFacade.remove(theater);

            FacesMessage msg = new FacesMessage("Teatro eliminado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate()
    {

        Theater theater = new Theater(id, capacidad);

        if (!theaters.contains(theater))
        {

            theaters.add(theater);

            theaterFacade.create(theater);

            FacesMessage msg = new FacesMessage("Teatro creado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else
        {
            FacesMessage msg = new FacesMessage("No se puede crear", "Ya existe un teatro con ese ID");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    //GETTERS AND SETTERS
    public List<Theater> getTheaters()
    {
        return theaters;
    }

    public void setTheaters(List<Theater> theaters)
    {
        this.theaters = theaters;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCapacidad()
    {
        return capacidad;
    }

    public void setCapacidad(int capacidad)
    {
        this.capacidad = capacidad;
    }

    public int getShowTimingList()
    {
        return showTimingList;
    }

    public void setShowTimingList(int showTimingList)
    {
        this.showTimingList = showTimingList;
    }

}
