package domain.transporte;

import domain.entities.EntidadPersistente;
import domain.organizacion.Miembro;
import domain.transporte.medios.ServicioContratado;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="viajeEnServicio")
@Setter
@Getter
public class ViajeEnServicio extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "servicio_contratado_id")
    private ServicioContratado servicioContratado;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;


}
