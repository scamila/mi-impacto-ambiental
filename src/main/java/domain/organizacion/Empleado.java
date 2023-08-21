package domain.organizacion;

import domain.entities.EntidadPersistente;
import domain.trayecto.Trayecto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="empleado")
@Setter
@Getter
public class Empleado extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @OneToMany(orphanRemoval = true)
    private List<Trayecto> trayectos;


    @Column
    @Enumerated(EnumType.STRING)
    private EstadoEmpleado estado;

    public EstadoEmpleado getEstado() {
        return estado;
    }

    public Float huellaTotalEnOrganizacion(Organizacion organizacion){
        return trayectos.stream().filter(trayecto -> trayecto.getOrgDestino() == organizacion).findFirst().get().huellaDeCarbonoDeTrayecto();
    }
    public void setEstado(EstadoEmpleado estado) {
        this.estado = estado;
    }
}
