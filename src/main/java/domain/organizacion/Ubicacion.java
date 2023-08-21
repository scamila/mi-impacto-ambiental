package domain.organizacion;

import domain.entities.EntidadPersistente;
import domain.organizacion.SectorTerritorial;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ubicacion")
public class Ubicacion extends EntidadPersistente {
    @OneToOne
    private Direccion direccion;
    @Column(name = "latitud")
    private Float latitud;
    @Column(name = "longitud")
    private Float longitud;
    @ManyToOne
    private SectorTerritorial sectorTerritorial;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
