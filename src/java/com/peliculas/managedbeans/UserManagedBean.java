/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.sessionbeans.UsuarioFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author csacanam
 */
public class UserManagedBean {

    @EJB
    UsuarioFacade usuarioFacade;

    private String name;
    private String password;

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent event) 
    {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try 
        {
            String navigateString = "/home.xhtml";
            
        // Checks if username and password are valid if not throws a ServletException
            request.login(name, password);
    
            try 
            {
                context.getExternalContext().redirect(request.getContextPath() + navigateString);
            } catch (IOException ex) 
            {
                context.addMessage(null, new FacesMessage("Error!", "Exception occured"));
            }
        } 
        catch (ServletException e)
        {
            context.addMessage(null, new FacesMessage("Error!", "The username or password you provided does not match our records."));
        }
    }

}
