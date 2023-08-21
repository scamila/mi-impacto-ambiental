package domain.auxiliares;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HuellasPorActividadOrganizacion {

    String organizacion;
    List<HuellasPorActividad> huellasActividad;

}
