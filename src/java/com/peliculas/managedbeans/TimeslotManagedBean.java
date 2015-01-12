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
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author csacanam
 */
public class TimeslotManagedBean
{

    private List<Timeslot> timeslots;

    //Atributos
    private int id;
    private String startTime;
    private String endTime;
    private ArrayList<ShowTiming> showTimingList;

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

    public void onRowEdit(RowEditEvent event)
    {

        Timeslot timeslot = ((Timeslot) event.getObject());

        if (timeslot != null)
        {

            timeslotFacade.edit(timeslot);

            FacesMessage msg = new FacesMessage("Timeslot editado", timeslot.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edición anulada", ((Timeslot) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(Timeslot timeslot)
    {
        if (timeslot != null)
        {
            timeslots.remove(timeslot);

            timeslotFacade.remove(timeslot);

            FacesMessage msg = new FacesMessage("Timeslot eliminado", timeslot.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate()
    {

        if (!Validaciones.esVacio(startTime) && !Validaciones.esVacio(endTime))
        {
            Timeslot timeslot = new Timeslot(id, startTime, endTime);

            if (!timeslots.contains(timeslot))
            {

                timeslots.add(timeslot);

                timeslotFacade.create(timeslot);

                FacesMessage msg = new FacesMessage("Timeslot creado", timeslot.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else
            {
                FacesMessage msg = new FacesMessage("No se puede crear", "Ya existe un timeslot con ese ID");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else
        {
            FacesMessage msg = new FacesMessage("Campos vacíos", "Llena los campos para continuar");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public ArrayList<ShowTiming> getShowTimingList()
    {
        return showTimingList;
    }

    public void setShowTimingList(ArrayList<ShowTiming> showTimingList)
    {
        this.showTimingList = showTimingList;
    }

    public List<Timeslot> getTimeslots()
    {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots)
    {
        this.timeslots = timeslots;
    }

}
