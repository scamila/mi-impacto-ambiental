package domain.transporte.medios;

import data.model.DistanceModel;
import domain.transporte.MedioTransporte;
import domain.transporte.Particular;

import javax.persistence.*;

@Entity
@Table(name = "bicicleta")
@Inheritance(strategy = InheritanceType.JOINED)
public class Bicicleta extends Particular {

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
