package domain.organizacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import domain.entities.EntidadPersistente;
import domain.trayecto.Trayecto;
import domain.usuarios.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="miembro")
@Setter
@Getter
public class Miembro extends EntidadPersistente {

    @Column(name= "nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="numeroDeDocumento")
    private String numeroDeDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDeDocumento")
    private TipoDeDocumento tipoDeDocumento;


    @OneToOne
    private Usuario usuario;

    @Transient
    private List<PuestoDeTrabajo> trabajos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Trayecto> trayectos;

    public Float huellaTotalEnOrganizacion(Organizacion organizacion){
        Float valor;
        try{
            valor = trayectos.stream().filter(trayecto -> Objects.equals(trayecto.getOrgDestino().getRazonSocial(), organizacion.getRazonSocial())).findFirst().orElse(null).huellaDeCarbonoDeTrayecto();
        }
        catch(Exception e){
            valor = 0F;
        }

        return valor;
        /*
        if(trayectos.stream().filter(trayecto -> Objects.equals(trayecto.getOrgDestino().getRazonSocial(), organizacion.getRazonSocial())).findFirst().orElse(null).huellaDeCarbonoDeTrayecto() != null)
            {
                return trayectos.stream().filter(trayecto -> Objects.equals(trayecto.getOrgDestino().getRazonSocial(), organizacion.getRazonSocial())).findFirst().get().huellaDeCarbonoDeTrayecto();
            }
        else {
            return 0F;
        }*/

    }

    public Float impactoSobreOrganizacion(Organizacion organizacion){
        Float huellaTotalOrganizacion = organizacion.huellaTotalAnual(String.valueOf(LocalDate.now().getYear())) ;
        return (huellaTotalEnOrganizacion(organizacion)*20*12/huellaTotalOrganizacion)*100;
    }
}
