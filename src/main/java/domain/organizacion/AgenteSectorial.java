package domain.organizacion;

import domain.entities.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agenteSectorial")
public class AgenteSectorial extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @OneToOne(fetch = FetchType.LAZY)
    private SectorTerritorial sectorEncargado;
    public Float huellaPorSector(List<Organizacion> organizaciones){
        return organizaciones.stream().map(organizacion -> organizacion.getHuellaAnual()).reduce(0F, Float::sum);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SectorTerritorial getSectorEncargado() {
        return sectorEncargado;
    }

    public void setSectorEncargado(SectorTerritorial sectorEncargado) {
        this.sectorEncargado = sectorEncargado;
    }
}
