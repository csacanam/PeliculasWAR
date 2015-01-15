/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.ShowTiming;
import com.peliculas.entities.Theater;
import com.peliculas.sessionbeans.TheaterFacade;
import java.util.ArrayList;
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
public class TheaterManagedBean implements Converter
{

    //Lista de teatros
    private List<Theater> theaters;

    //Atributos
    private int id;
    private int capacidad;
    private int showTimingList;
    private int selectedTheater;

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

    /**
     * Se invoca cuando se desea editar un teatro
     *
     * @param event Evento relacionado con la edición de la fila
     */
    public void onRowEdit(RowEditEvent event)
    {

        Theater theater = ((Theater) event.getObject());

        if (theater != null)
        {

            theaterFacade.edit(theater);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Teatro editado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    /**
     * Se invoca cuando se cancela la edición de la fila
     *
     * @param event Evento relacionado con la cancelación de la edición
     */
    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición anulada", ((Theater) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Se invoca cuando se desea eliminar un teatro
     *
     * @param theater Teatro que se desea eliminar
     */
    public void onDelete(Theater theater)
    {
        if (theater != null)
        {
            theaters.remove(theater);

            theaterFacade.remove(theater);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Teatro eliminado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Se invoca cuando se desea crear un nuevo teatro
     */
    public void onCreate()
    {

        Theater theater = new Theater(id, capacidad);

        if (!theaters.contains(theater))
        {

            theaters.add(theater);

            theaterFacade.create(theater);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Teatro creado", theater.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear", "Ya existe un teatro con ese ID");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    /**
     * Permite obtener la lista de Show Timings de un teatro
     *
     * @param theaterId Id del teatro
     * @return Lista de Show Timings del teatro
     */
    public List<ShowTiming> getShowTimings(int theaterId)
    {
        Theater theater = theaterFacade.find(theaterId);
        if (theater != null)
        {
            return theater.getShowTimingList();
        } else
        {
            return new ArrayList<>();
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

    public int getSelectedTheater()
    {
        return selectedTheater;
    }

    public void setSelectedTheater(int selectedTheater)
    {
        this.selectedTheater = selectedTheater;
    }

    //Métodos del Converter - Permiten editar cuando hay un ComboBox de la entidad
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

                for (Theater theater : theaters)
                {
                    if (theater.getId() == theId)
                    {
                        return theater;
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
            return ((Theater) value).getId().toString();
        }
    }

}
