package adapters;

import data.model.DistanceModel;
import domain.organizacion.Direccion;

public interface AdapterDistancia {

    DistanceModel distanciaEntre(Direccion direcSalida, Direccion direcLlegada);

}
