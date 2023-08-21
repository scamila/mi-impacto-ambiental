package domain.transporte;


import data.model.DistanceModel;
import domain.entities.EntidadPersistente;
import domain.transporte.medios.TransportePublico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "medioDeTransporte")
@Inheritance(strategy = InheritanceType.JOINED)
// Por que aca va un inheritance stratregy?
public abstract class MedioTransporte extends EntidadPersistente {

    @Column(name="fe")
    private float FE;

    @OneToOne(mappedBy = "medio")
    private Particular particular;

    @OneToOne
    private TransportePublico publico;

    public DistanceModel calcularDistancia() {
        return this.particular.calcularDistancia();
    }

    public abstract Float getFE();
}
