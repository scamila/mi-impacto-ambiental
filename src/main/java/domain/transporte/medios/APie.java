package domain.transporte.medios;

import data.model.DistanceModel;
import domain.transporte.MedioTransporte;
import domain.transporte.Particular;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "aPie")
@Inheritance(strategy = InheritanceType.JOINED)
public class APie extends Particular {

    @OneToOne
    @JoinColumn(name="particular_id")
    private Particular particular;
    @Override
    public Float getFE(){
        return 0F;
    }

    public DistanceModel calcularDistancia(){
        DistanceModel distancia = new DistanceModel();
        distancia.setUnidad("KM");
        distancia.setValor("10");
        return distancia;
    }
}
