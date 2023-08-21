package domain.transporte.medios;

import data.model.DistanceModel;
import domain.transporte.medios.FE;
import domain.transporte.MedioTransporte;
import domain.transporte.Parada;
import domain.transporte.Recorrido;
import domain.transporte.enums.TipoTransportePublico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "transportePublico")
@Inheritance(strategy = InheritanceType.JOINED)
public class TransportePublico extends MedioTransporte {
    @OneToOne
    private Parada paradaInicial;
    @OneToOne
    private Parada paradaFinal;
    // @Column(name = "linea")
    // private String linea;
    // @Enumerated(EnumType.STRING)
    // private TipoTransportePublico tipoTransportePublico;
    @OneToOne
    private Recorrido recorrido;


    public void setParadaInicial(Parada paradaInicial) {
        this.paradaInicial = paradaInicial;
    }

    public void setParadaFinal(Parada paradaFinal) {
        this.paradaFinal = paradaFinal;
    }
/* 
    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setTipoTransportePublico(TipoTransportePublico tipoTransportePublico) {
        this.tipoTransportePublico = tipoTransportePublico;
    } */

    public void setRecorrido(Recorrido recorrido) {
        this.recorrido = recorrido;
    }

    @Override
    public DistanceModel calcularDistancia() {
         float distance = recorrido.distanciaEntre(paradaInicial, paradaFinal);
         DistanceModel distanceModel = new DistanceModel();
         distanceModel.setUnidad("KM");
         String distanciaEnString = String.valueOf(distance);
         distanceModel.setValor(distanciaEnString);
         System.out.println(distance);
         return distanceModel;
    }

    @Override
    public Float getFE() {
        FE fe = new FE();
        return fe.getTransportePublicoFE();
    }
}
