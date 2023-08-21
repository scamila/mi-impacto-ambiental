package domain.trayecto;

import data.model.DistanceModel;
import domain.entities.EntidadPersistente;
import domain.organizacion.Direccion;
import domain.organizacion.Empleado;
import domain.organizacion.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "trayecto")
public class Trayecto extends EntidadPersistente {

    @OneToOne
    private Direccion puntoPartida;
    @OneToOne
    private Direccion puntoLLegada;
    @ManyToOne
    private Organizacion orgDestino;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToMany(cascade = {CascadeType.PERSIST},orphanRemoval = true)
    @JoinColumn(name = "trayecto_id",referencedColumnName = "id")
    private List<Tramo> tramos;

    public DistanceModel getDistanciaTrayecto(){
        Float distancia = (tramos.stream().map(Tramo::getDistanciaTramoEnFloat)).reduce(0F, (a, b) -> a + b);
        DistanceModel distanceModel = new DistanceModel();
        distanceModel.setValor(String.valueOf(distancia));
        distanceModel.setUnidad("KM");
        return distanceModel;
    }

    public Float huellaDeCarbonoDeTrayecto(){
        return (tramos.stream().map(Tramo::huellaDeCarbonoPorTramo)).reduce(0F, (a, b) -> a + b);
    }

    public void agregarTramo(Tramo tramo){
        if(tramos != null) {
            tramos.add(tramo);
        }else
        {
            tramos = new ArrayList<>();
        }
    }

}
