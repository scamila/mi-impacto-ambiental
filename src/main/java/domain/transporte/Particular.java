package domain.transporte;

import adapters.AdapterDistancia;
import adapters.impl.AdapterAPIUTN;
import data.model.DistanceModel;
import domain.entities.EntidadPersistente;
import domain.organizacion.Direccion;
import domain.transporte.medios.APie;
import domain.transporte.medios.Bicicleta;
import domain.transporte.medios.ServicioContratado;
import domain.transporte.medios.VehiculoParticular;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "particular")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Particular extends MedioTransporte {
    @OneToOne
    private Direccion direcSalida;
    @OneToOne
    private Direccion direcLlegada;

    @Transient
    private AdapterDistancia adapterDistancia;
    private Float FE;
    
    @OneToOne
    @JoinColumn(name="medio_id")
    private MedioTransporte medio;

    @OneToOne(mappedBy = "particular")
    private ServicioContratado servicioContratado;

    @OneToOne(mappedBy = "particular")
    private APie apie;

    @OneToOne(mappedBy = "particular")
    private VehiculoParticular vehiculoParticular;

    @OneToOne(mappedBy = "particular")
    private Bicicleta bicicleta;


    public Direccion getDirecSalida() {
        return direcSalida;
    }

    public void setDirecSalida(Direccion direcSalida) {
        this.direcSalida = direcSalida;
    }

    public Direccion getDirecLlegada() {
        return direcLlegada;
    }

    public void setDirecLlegada(Direccion direcLlegada) {
        this.direcLlegada = direcLlegada;
    }

    public abstract Float getFE();
    @Override
    public DistanceModel calcularDistancia() {
        return vehiculoParticular.calcularDistancia();
    }

    public void setAdapterDistancia(AdapterDistancia adapterDistancia) {
        this.adapterDistancia = adapterDistancia;
    }
}
