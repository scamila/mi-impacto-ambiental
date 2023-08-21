package domain.trayecto;

import data.model.DistanceModel;
import domain.entities.EntidadPersistente;
import domain.organizacion.Direccion;
import domain.transporte.MedioTransporte;
import domain.transporte.medios.APie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "tramo")
public class Tramo extends EntidadPersistente {

    @OneToOne
    private Direccion puntoPartida;
    @OneToOne
    private Direccion puntoDestino;
    @OneToOne
    private MedioTransporte medioTransporte;

    @ManyToOne
    private Trayecto trayecto;
    /*
    @Column(name="distancia")
    private Float distancia;
    * */

    public DistanceModel getDistanciaTramo(){
        return medioTransporte.calcularDistancia();
    }

    public Float getDistanciaTramoEnFloat(){
        return Float.parseFloat(getDistanciaTramo().getValor());
    }

    public Float huellaDeCarbonoPorTramo(){
        return this.getDistanciaTramoEnFloat() * this.medioTransporte.getFE();
    }

}
