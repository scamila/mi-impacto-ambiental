package domain.organizacion;

import domain.entities.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sector")
@Setter
@Getter
public class Sector extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @Transient
    private List<Miembro> miembrosSector;

    @Column(name = "proporcionHuella")
    private Double proporcionHuella;

    public int cantidadMiembrosSector(){
        //Rever metodo
        return miembrosSector.size();
    }

}
