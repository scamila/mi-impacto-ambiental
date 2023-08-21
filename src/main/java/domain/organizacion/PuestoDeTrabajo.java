package domain.organizacion;

import domain.entities.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="puestoDeTrabajo")
@Setter
@Getter
public class PuestoDeTrabajo extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
}
