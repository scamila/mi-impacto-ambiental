package domain.organizacion;
import domain.entities.EntidadPersistente;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "sectorTerritorial")
public class SectorTerritorial extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY)
    private  List<Organizacion> organizacionesEnSector;

    @OneToMany(mappedBy = "sectorTerritorial")
    private List<Ubicacion> ubicacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Organizacion> getOrganizacionesEnSector() {
        return organizacionesEnSector;
    }
}
