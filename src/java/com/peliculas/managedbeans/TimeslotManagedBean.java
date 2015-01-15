/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.ShowTiming;
import com.peliculas.entities.Timeslot;
import com.peliculas.sessionbeans.TimeslotFacade;
import com.peliculas.utils.Validaciones;
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
public class TimeslotManagedBean implements Converter
{

    //Lista de timeslots
    private List<Timeslot> timeslots;

    //Atributos
    private int id;
    private String startTime;
    private String endTime;
    private int selectedTimeslot;

    //Session Bean
    @EJB
    TimeslotFacade timeslotFacade;

    public TimeslotManagedBean()
    {

    }

    @PostConstruct
    public void init()
    {
        timeslots = timeslotFacade.findAll();
    }

    /**
     * Se invoca cuando se desea editar un timeslot
     *
     * @param event Evento relacionado con la edición de la fila
     */
    public void onRowEdit(RowEditEvent event)
    {

        Timeslot timeslot = ((Timeslot) event.getObject());

        if (timeslot != null)
        {

            timeslotFacade.edit(timeslot);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Timeslot editado", timeslot.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    /**
     * Se invoca cuando se cancela la edición de un punto
     *
     * @param event Evento relacionado con la cancelación de la edición
     */
    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición anulada", ((Timeslot) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Se invoca cuando se elimina un timeslot
     *
     * @param timeslot Timeslot que se desea eliminar
     */
    public void onDelete(Timeslot timeslot)
    {
        if (timeslot != null)
        {
            timeslots.remove(timeslot);

            timeslotFacade.remove(timeslot);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Timeslot eliminado", timeslot.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Se invoca cuando se desea crear un timeslot en la base de datos
     */
    public void onCreate()
    {

        if (!Validaciones.esVacio(startTime) && !Validaciones.esVacio(endTime))
        {
            Timeslot timeslot = new Timeslot(id, startTime, endTime);

            if (!timeslots.contains(timeslot))
            {

                timeslots.add(timeslot);

                timeslotFacade.create(timeslot);

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Timeslot creado", timeslot.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else
            {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear", "Ya existe un timeslot con ese ID");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos vacíos", "Llena los campos para continuar");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    /**
     * Obtener todos los ShowTimings del Timeslot
     *
     * @param timeslotId Id del timeslot
     * @return Lista de Show Timings del Timeslot
     */
    public List<ShowTiming> getShowTimings(int timeslotId)
    {
        Timeslot timeslot = timeslotFacade.find(timeslotId);
        if (timeslot != null)
        {
            return timeslot.getShowTimingList();
        } else
        {
            return new ArrayList<>();
        }

    }

    //GETTERS AND SETTERS
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public List<Timeslot> getTimeslots()
    {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots)
    {
        this.timeslots = timeslots;
    }

    public int getSelectedTimeslot()
    {
        return selectedTimeslot;
    }

    public void setSelectedTimeslot(int selectedTimeslot)
    {
        this.selectedTimeslot = selectedTimeslot;
    }

    public TimeslotFacade getTimeslotFacade()
    {
        return timeslotFacade;
    }

    public void setTimeslotFacade(TimeslotFacade timeslotFacade)
    {
        this.timeslotFacade = timeslotFacade;
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

                for (Timeslot timeslot : timeslots)
                {
                    if (timeslot.getId() == theId)
                    {
                        return timeslot;
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
            return ((Timeslot) value).getId().toString();
        }
    }

}
