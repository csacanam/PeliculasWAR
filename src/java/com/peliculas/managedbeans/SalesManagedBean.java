/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Sales;
import com.peliculas.entities.ShowTiming;
import com.peliculas.sessionbeans.SalesFacade;
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
public class SalesManagedBean implements Converter
{

    //Lista de ventas
    private List<Sales> sales;

    //Atributos
    private int id;
    private double amount;
    private ShowTiming showTiming;

    //Session Bean
    @EJB
    SalesFacade salesFacade;

    /**
     * Creates a new instance of SalesManagedBean
     */
    public SalesManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        sales = salesFacade.findAll();
    }

    /**
     * Se invoca cuando se desea editar una venta
     *
     * @param event Evento relacionado con la edición de la fila
     */
    public void onRowEdit(RowEditEvent event)
    {

        Sales sale = ((Sales) event.getObject());

        if (sale != null)
        {

            salesFacade.edit(sale);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta editada", sale.toString());
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición anulada", ((Sales) event.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * SE invoca cuando se desea eliminar una venta
     *
     * @param sale Venta que se desea eliminar
     */
    public void onDelete(Sales sale)
    {
        if (sale != null)
        {
            sales.remove(sale);

            salesFacade.remove(sale);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta eliminada", sale.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Se invoca cuando se desea crear una nueva venta
     */
    public void onCreate()
    {

        Sales sale = new Sales(id, amount);

        if (!sales.contains(sale))
        {
            sales.add(sale);

            salesFacade.create(sale);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta creada", sale.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear", "Ya existe una venta con ese ID");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    //GETTERS AND SETTERS
    public List<Sales> getSales()
    {
        return sales;
    }

    public void setSales(List<Sales> sales)
    {
        this.sales = sales;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public ShowTiming getShowTiming()
    {
        return showTiming;
    }

    public void setShowTiming(ShowTiming showTiming)
    {
        this.showTiming = showTiming;
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

                for (Sales sale : sales)
                {
                    if (sale.getId() == theId)
                    {
                        return sale;
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
            return ((Sales) value).getId().toString();
        }
    }
}
