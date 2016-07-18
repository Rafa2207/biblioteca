package controlador;

import modelo.EjemplarTb;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import servicio.EjemplarTbFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.EjemplarNombrecomunTb;
import modelo.NombrecomunTb;
import modelo.TaxonomiaTb;

@Named("ejemplarTbController")
@ViewScoped
public class EjemplarTbController implements Serializable {

    @EJB
    private servicio.EjemplarTbFacade ejbFacade;
    private List<EjemplarTb> items = null, lista = null;
    private EjemplarTb selected;
    private int nombreBusqueda = 0, nombreBusqueda1 = 0;
    private String nombre,nombre1;

    public List<EjemplarTb> getLista() {
        return lista;
    }

    public void setLista(List<EjemplarTb> lista) {
        this.lista = lista;
    }

    public EjemplarTbController() {
    }

    public EjemplarTb getSelected() {
        return selected;
    }

    public void setSelected(EjemplarTb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EjemplarTbFacade getFacade() {
        return ejbFacade;
    }

    public EjemplarTb prepareCreate() {
        selected = new EjemplarTb();
        initializeEmbeddableKey();
        return selected;
    }

    public int getNombreBusqueda1() {
        return nombreBusqueda1;
    }

    public void setNombreBusqueda1(int nombreBusqueda1) {
        this.nombreBusqueda1 = nombreBusqueda1;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }
    
    public int getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(int nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EjemplarTbDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EjemplarTb> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public EjemplarTb getEjemplarTb(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<EjemplarTb> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EjemplarTb> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EjemplarTb.class)
    public static class EjemplarTbControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EjemplarTbController controller = (EjemplarTbController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ejemplarTbController");
            return controller.getEjemplarTb(getKey(value));
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
            if (object instanceof EjemplarTb) {
                EjemplarTb o = (EjemplarTb) object;
                return getStringKey(o.getEIdejemplar());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EjemplarTb.class.getName()});
                return null;
            }
        }

    }

    public String calcularTaxonomia(TaxonomiaTb tax) {
        try {
            
        if (tax.getERango() == 1) {
            return "-";
        }
        if (tax.getERango() == 2) {
            String genero = tax.getCNombre();
            return genero;
        }
        if (tax.getERango() == 3) {
            String genero = tax.getEIdniveltaxonomia().getCNombre();
            String especie = tax.getCNombre();
            return genero + ", " + especie;
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Subespecie")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String subespecie = tax.getCNombre();
            return genero + ", " + especie + " sub. " + subespecie;
        }
        if (tax.getERango() == 4 && tax.getCTipo().equals("Variedad")) {
            String genero = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre();
            String especie = tax.getEIdniveltaxonomia().getCNombre();
            String variedad = tax.getCNombre();
            return genero + ", " + especie + " var. " + variedad;
        }
        
        } catch (Exception e) {
            
        }
        return "";
    }

    public void buscar() {
        if (nombreBusqueda == 1) {
            items.clear();
            items = getFacade().EjemplaresPorNombreComun(nombre);
        } else if (nombreBusqueda == 2) {
            items.clear();
            items = getFacade().EjemplaresPorNombreCientifico(nombre);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione nombre común o científico"));
            //JsfUtil.addErrorMessage("Seleccione nombre común o científico");
        }
    }
    
    public void buscar1() {
        if (nombreBusqueda1 == 1) {
            items.clear();
            items = getFacade().EjemplaresPorNombreComun(nombre1);
        } else if (nombreBusqueda1 == 2) {
            items.clear();
            items = getFacade().EjemplaresPorNombreCientifico(nombre1);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione nombre común o científico"));
            
        }
    }

    public String calcularFamilia(TaxonomiaTb tax) {
        if (tax.getERango() == 1) {
            String fam = tax.getCNombre().toUpperCase();
            return fam;
        }
        if (tax.getERango() == 2) {
            String fam = tax.getEIdniveltaxonomia().getCNombre().toUpperCase();
            return fam;
        }
        if (tax.getERango() == 3) {
            String fam = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre().toUpperCase();
            return fam;
        }
        if (tax.getERango() == 4) {
            String fam = tax.getEIdniveltaxonomia().getEIdniveltaxonomia().getEIdniveltaxonomia().getCNombre().toUpperCase();
            return fam;
        }
        return "";
    }

    public String calculaNombres(EjemplarTb ej) {
        ej.getAgenteIdentificaEjemplarTbIDList().clear();
        ej.setAgenteIdentificaEjemplarTbIDList(getFacade().ejemplarIdentificador(ej.getEIdejemplar(), "Identificador"));
        String nombres = "", coma = "";
        if (!ej.getAgenteIdentificaEjemplarTbIDList().isEmpty()) {
            for (AgenteIdentificaEjemplarTb i : ej.getAgenteIdentificaEjemplarTbIDList()) {
                nombres = nombres + coma + i.getAgenteTb().getCIniciales();
                coma = ", ";
            }
            nombres = nombres + ".";
        } else {
            nombres = "";
        }
        return nombres;
    }
    
    public String calculaNombresRecolector(EjemplarTb ej) {
        ej.getAgenteIdentificaEjemplarTbList().clear();
        ej.setAgenteIdentificaEjemplarTbList(getFacade().ejemplarRecolector(ej.getEIdejemplar(), "Recolector"));
        String nombres = "", coma = "";
        if (!ej.getAgenteIdentificaEjemplarTbList().isEmpty()) {
            for (AgenteIdentificaEjemplarTb i : ej.getAgenteIdentificaEjemplarTbList()) {
                nombres = nombres + coma + i.getAgenteTb().getCIniciales();
                coma = ", ";
            }
            nombres = nombres + ".";
        } else {
            nombres = "";
        }
        return nombres;
    }

    public String NombresComunesLocales(EjemplarTb ej) {
        String nombre = "", coma = "";
        if (!ej.getEjemplarNombrecomunTbList().isEmpty()) {
            for (EjemplarNombrecomunTb nc : ej.getEjemplarNombrecomunTbList()) {
                nombre = nombre + coma + nc.getCNombrecomun();
                coma = ", ";
            }
            nombre = nombre + ".";
        } else {
            nombre = "";
        }

        return nombre;

    }
    
    public String NombresComunesOtros(EjemplarTb ej) {
        String nombre = "", coma = "";
        if (!ej.getEIdtaxonomia().getNombrecomunTbList().isEmpty()) {
            for (NombrecomunTb nc : ej.getEIdtaxonomia().getNombrecomunTbList()) {
                nombre = nombre + coma + nc.getCNombre();
                coma = ", ";
            }
            nombre = nombre + ".";
        } else {
            nombre = "";
        }

        return nombre;

    }

}
