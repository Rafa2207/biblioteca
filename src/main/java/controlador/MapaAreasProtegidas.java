package controlador;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import modelo.AreaprotegidaTb;
import org.primefaces.event.map.OverlaySelectEvent;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
public class MapaAreasProtegidas implements Serializable {

    private Marker marker;
    private MapModel punto;
    @EJB
    private servicio.AreaprotegidaTbFacade Facade;

    @PostConstruct
    public void init() {
        punto = new DefaultMapModel();
        for (AreaprotegidaTb areapunto : Facade.findAll() ) {
            LatLng coord1 = new LatLng(areapunto.getDLatituddecimal(), areapunto.getDLongituddecimal());
            punto.addOverlay(new Marker(coord1,areapunto.getCNombre()));
        }
    }

    public MapModel getPunto() {
        return punto;
    }

    public void setPunto(MapModel punto) {
        this.punto = punto;
    }
        
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }
    
    public Marker getMarker() {
        return marker;
    }
}
