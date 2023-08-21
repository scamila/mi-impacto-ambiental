package domain.transporte.medios;

import data.model.DistanceModel;
import domain.transporte.MedioTransporte;
import domain.transporte.medios.FE;
import domain.organizacion.Miembro;
import domain.transporte.Particular;
import lombok.Getter;
import lombok.Setter;

import javax.mail.Part;
import javax.persistence.*;
import java.util.List;
@Entity
@Setter
@Getter
@Table(name = "servicioContratado")
@Inheritance(strategy = InheritanceType.JOINED)
public class ServicioContratado extends Particular {
    @Column(name = "nombreServicio")
    private String nombreServicio;

    @OneToOne
    @JoinColumn(name="particular_id")
    private Particular particular;

    @Transient
    private List<Miembro> miembrosViajan;

    @Override
    public Float getFE() {
        FE fe = new FE();
        return fe.getServicioContratadoFE();
    }

    public DistanceModel calcularDistancia(){
        DistanceModel distancia = new DistanceModel();
        distancia.setUnidad("KM");
        distancia.setValor("10");
        return distancia;
    }


    // En caso de que el FE lo tenga el admin, se podria hacer un constructor que le pida al admin y lo inicialice
}
