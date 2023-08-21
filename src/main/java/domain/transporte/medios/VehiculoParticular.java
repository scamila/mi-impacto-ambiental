package domain.transporte.medios;

import adapters.impl.AdapterAPIUTN;
import data.model.DistanceModel;
import domain.organizacion.Direccion;
import domain.transporte.MedioTransporte;
import domain.transporte.medios.FE;
import domain.organizacion.Miembro;
import domain.transporte.Particular;
import domain.transporte.enums.TipoCombustible;
import domain.transporte.enums.TipoVehiculo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="vehiculoParticular")
@Setter
@Getter
public class VehiculoParticular extends Particular {
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;
    @Enumerated(EnumType.STRING)
    private TipoCombustible tipoCombustible;

    @OneToOne
    @JoinColumn(name="particular_id")
    private Particular particular;

    @Transient
    private List<Miembro> miembrosViajan;


    @Override
    public Float getFE() {
        FE fe = new FE();
        return fe.getVehiculoParticularFE();
    }


    public DistanceModel calcularDistancia() {
        AdapterAPIUTN adapter = new AdapterAPIUTN();

        if (this.getDirecSalida() != null && this.getDirecLlegada() != null) {
            DistanceModel distanceModel = adapter.distanciaEntre(this.getDirecSalida(), this.getDirecLlegada());

            System.out.println(distanceModel.getValor() + " " + distanceModel.getUnidad());

            return distanceModel;
        }
        else{
            DistanceModel distanceModel =  new DistanceModel();
            distanceModel.setValor("0");
            distanceModel.setUnidad("0");
            return distanceModel;
        }
    }
}
