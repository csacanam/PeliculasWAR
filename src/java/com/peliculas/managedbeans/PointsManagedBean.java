/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Points;
import com.peliculas.sessionbeans.PointsFacade;
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


public class PointsManagedBean
{

    private List<Points> points;

    //Atributos
    private int id;
    private int puntos;

    //SessionBean
    @EJB
    PointsFacade pointsFacade;

    /**
     * Creates a new instance of PointsManagedBean
     */
    public PointsManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        points = pointsFacade.findAll();
    }

    public void onRowEdit(RowEditEvent event)
    {

        Points point = ((Points) event.getObject());

        if (point != null)
        {

            pointsFacade.edit(point);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Punto editado", point.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Edición anulada", ((Points) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onDelete(Points point)
    {
        if (point != null)
        {
            points.remove(point);

            pointsFacade.remove(point);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Punto eliminado", point.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCreate()
    {

        Points point = new Points(id, puntos);

        if (!points.contains(point))
        {

            points.add(point);

            pointsFacade.create(point);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Punto creado", point.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo crear", "Ya existe un punto con ese ID");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    // GETTERS AND SETTERS
    public List<Points> getPoints()
    {
        return points;
    }

    public void setPoints(List<Points> points)
    {
        this.points = points;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPuntos()
    {
        return puntos;
    }

    public void setPuntos(int puntos)
    {
        this.puntos = puntos;
    }

}
