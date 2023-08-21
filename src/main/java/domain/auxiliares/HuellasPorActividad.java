package domain.auxiliares;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HuellasPorActividad {


    String actividad;
    String huellaCarbono;


    public HuellasPorActividad(String actividad, String huellaCarbono){
        this.actividad = actividad;
        this.huellaCarbono = huellaCarbono;
    }

    public HuellasPorActividad(){}
}
