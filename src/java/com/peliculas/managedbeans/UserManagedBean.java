/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peliculas.managedbeans;

import com.peliculas.entities.Usuario;
import com.peliculas.sessionbeans.UsuarioFacade;
import com.peliculas.utils.CipherAlgorithms;
import com.peliculas.utils.Validaciones;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author csacanam
 */
public class UserManagedBean
{

    //Atributos
    private String name;
    private String password;
    private boolean isLogged;

    //SessionBean
    @EJB
    UsuarioFacade usuarioFacade;

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean()
    {
    }


    /**
     * Se invoca cuando algún usuario va a loguearse en el sitio 
     */
    public void login()
    {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();

        if (!Validaciones.esVacio(name) && !Validaciones.esVacio(password))
        {
            Usuario usuTemp = usuarioFacade.find(name);

            try
            {
                // El login es correcto
                if (usuTemp != null && CipherAlgorithms.decrypt(usuTemp.getPassword()).equals(password))
                {
                    try
                    {
                        ec.redirect(req.getContextPath() + "/faces/user/movies.xhtml?i=0");
                        setIsLogged(true);

                    } catch (IOException ex)
                    {
                        setIsLogged(false);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", "Intenta más tarde"));
                    }

                } else
                {
                    setIsLogged(false);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos incorrectos", "Digite correctamente su username o su contraseña"));

                }
            } catch (GeneralSecurityException | IOException ex)
            {
                setIsLogged(false);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", "Intenta más tarde"));
            }
        } else
        {
            setIsLogged(false);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos vacíos", "Digite su username y su contraseña"));
        }

    }

    /**
     * Permite cerrar sesión al usuario
     */
    public void logout()
    {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();

        if (isLogged)
        {
            setIsLogged(false);
            name = null;
            password = null;

            try
            {
                ec.redirect(req.getContextPath() + "/faces/login.xhtml");
            } catch (IOException ex)
            {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cerrar sesión", "Intente más tarde"));

            }

        }
    }

    /**
     * Permite registrar un nuevo usuario en la base de datos
     */
    public void register()
    {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        if (!Validaciones.esVacio(name) && !Validaciones.esVacio(password))
        {
            Usuario usuTemp = usuarioFacade.find(name);

            if (usuTemp == null)
            {
                String encryptedPassword;
                try
                {
                    encryptedPassword = CipherAlgorithms.encrypt(password);
                    usuarioFacade.create(new Usuario(name, encryptedPassword));
                    login();
                } catch (GeneralSecurityException | UnsupportedEncodingException ex)
                {
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo registrar", "Intente más tarde"));

                }

            } else
            {
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El username ya ha sido usado", "Intente con otro username"));
            }
        } else
        {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos vacíos", "Digite su username y su contraseña"));

        }

    }

    // GETTERS AND SETTERS
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isIsLogged()
    {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged)
    {
        this.isLogged = isLogged;
    }

}
