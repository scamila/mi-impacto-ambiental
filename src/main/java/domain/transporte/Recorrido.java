package domain.transporte;

import domain.entities.EntidadPersistente;
import domain.transporte.enums.TipoTransportePublico;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "recorrido")
public class Recorrido extends EntidadPersistente {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorrido_id", referencedColumnName = "id")
    List<Parada> paradas = new ArrayList<>();

    //@Column(name = "descripcion")
    //private String descripcion;

    @Column(name = "linea")
    private String linea;
    
    @Enumerated(EnumType.STRING)
    private TipoTransportePublico tipoTransportePublico;

    public float distanciaEntre(Parada paradaInicial, Parada paradaFinal) {
        if(paradas.contains(paradaFinal) && paradas.contains(paradaFinal)) {
            int indexI = paradas.indexOf(paradaInicial);
            int indexF = paradas.indexOf(paradaFinal);
            List<Parada> subList;
            if(indexI <= indexF){
                subList = paradas.subList(indexI, indexF);
            }
            else{
                subList = paradas.subList(indexF,indexI);
            }
            return subList.stream().map(Parada::getDistanciaSiguiente).reduce(0F, (a, b) -> a + b);

        } else {
            System.out.println("Una o ambas paradas no se encuentran en el recorrido");
            return 0F;
        }
    }

    public void agregarParadaDespuesDe(Parada parada, Parada paradaNueva) {
        if(paradas.contains(parada)) {
            int index = paradas.indexOf(parada);
            paradas.add(index+1, paradaNueva);
        } else {
            System.out.println("Parada no encontrada en recorrido");
        }
    }

    public void agregarParadaAntesDe(Parada parada, Parada paradaNueva) {
        if(paradas.contains(parada)) {
            int index = paradas.indexOf(parada);
            paradas.add(index, paradaNueva);
        } else {
            System.out.println("Parada no encontrada en recorrido");
        }
    }

    public void agregarParadaInicial(Parada parada){
        if(paradas.isEmpty()) {
            paradas.add(parada);
        }
        else{
            System.out.println("No se puede agregar la parada ya que no es la inicial");
        }
    }

    public void quitarParada(Parada parada){

        if(paradas.contains(parada)) {
            /*
            int index = paradas.indexOf(parada);
            Parada paradaAnterior = paradas.get(index - 1);
            Parada paradaSiguiente = paradas.get(index + 1);
            float distanciaEntreAnteriorYSiguiente = parada.getDistanciaAnterior() + parada.getDistanciaSiguiente();

            paradaAnterior.setDistanciaSiguiente(distanciaEntreAnteriorYSiguiente);
            paradaSiguiente.setDistanciaAnterior(distanciaEntreAnteriorYSiguiente);
            */
            paradas.remove(parada);
        }
        else{
            System.out.println("No se puede quitar una parada que no existe");
        }



    }
}
