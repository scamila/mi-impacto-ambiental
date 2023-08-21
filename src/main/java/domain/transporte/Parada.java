package domain.transporte;

import domain.entities.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "parada")
public class Parada extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "distanciaAnterior")
    private float distanciaAnterior;
    @Column(name = "distanciaSiguiente")
    private float distanciaSiguiente;

}
