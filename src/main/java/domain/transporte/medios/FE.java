package domain.transporte.medios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FE {

    Float servicioContratadoFE = 5F;
    Float vehiculoParticularFE = 5F;
    Float transportePublicoFE = 8F;

    public Double feActividad(String actividad){
        Double fe = null;

        switch(actividad){
            case "combustion_fija":
                fe = 4.37;
                break;
            case "combustion_movil":
                fe = 3.77;
                break;
            case "electricidad_adquirida_y_consumida":
                fe = 1.5;
                break;
        }

        return fe;
    }

}
