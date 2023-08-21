package domain.transporte;

import data.model.DistanceModel;
import domain.entities.EntidadPersistente;
import domain.organizacion.Miembro;
import domain.transporte.medios.VehiculoParticular;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="viajeEnParticular")
@Setter
@Getter
public class ViajeEnParticular extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "vehiculo_paricular_id")
    private VehiculoParticular vehiculoParticular;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;

}
