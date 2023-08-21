package domain.usuarios;

import lombok.Getter;
import lombok.Setter;
import domain.entities.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Setter
@Getter
public class Usuario extends EntidadPersistente {

    @Column
    private String nombreDeUsuario;

    @Column
    private String contrasenia;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;
}