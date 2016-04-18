package controlador;

import modelo.BibliotecaTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.BibliotecaTbFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("bibliotecaTbController")
@SessionScoped
public class BibliotecaTbController implements Serializable {

    @EJB
    private servicio.BibliotecaTbFacade ejbFacade;
    private List<BibliotecaTb> items = null;
    private BibliotecaTb selected;

    public BibliotecaTbController() {
    }

    public BibliotecaTb getSelected() {
        return selected;
    }

    public void setSelected(BibliotecaTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BibliotecaTbFacade getFacade() {
        return ejbFacade;
    }

    public BibliotecaTb prepareCreate() {
        selected = new BibliotecaTb();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlebiblioteca").getString("BibliotecaTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlebiblioteca").getString("BibliotecaTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlebiblioteca").getString("BibliotecaTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BibliotecaTb> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlebiblioteca").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlebiblioteca").getString("PersistenceErrorOccured"));
            }
        }
    }

    public BibliotecaTb getBibliotecaTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<BibliotecaTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BibliotecaTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BibliotecaTb.class)
    public static class BibliotecaTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BibliotecaTbController controller = (BibliotecaTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bibliotecaTbController");
            return controller.getBibliotecaTb(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BibliotecaTb) {
                BibliotecaTb o = (BibliotecaTb) object;
                return getStringKey(o.getEIdbiblioteca());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BibliotecaTb.class.getName()});
                return null;
            }
        }

    }
    
    public String muestraHistoria(){
        String txt="";
        BibliotecaTb h = new BibliotecaTb();
        h = getFacade().Buscar();
        txt= h.getMHistoria();
        return txt;
    }
    
    public String muestraInfo(){
        String txt="";
        BibliotecaTb h = new BibliotecaTb();
        h = getFacade().Buscar();
        txt=h.getMInformacion();
        return txt;
    }
    public String muestraMision(){
        String txt="";
        BibliotecaTb h = new BibliotecaTb();
        h = getFacade().Buscar();
        txt= h.getMMision();
        return txt;
    }
    public String muestraVision(){
        String txt="";
        BibliotecaTb h = new BibliotecaTb();
        h = getFacade().Buscar();
        txt= h.getMVision();
        return txt;
    }
    public String muestraObjetivo(){
        String txt="";
        BibliotecaTb h = new BibliotecaTb();
        h = getFacade().Buscar();
        txt= h.getMObjetivo();
        return txt;
    }

}
