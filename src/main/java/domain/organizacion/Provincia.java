package domain.organizacion;

import domain.entities.EntidadPersistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "provincia")
public class Provincia extends EntidadPersistente {

    @Column
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    private List<Ubicacion> ubicaciones;
}
