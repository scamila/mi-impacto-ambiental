package adapters.impl;

import adapters.AdapterDistancia;
import data.model.DistanceModel;
import data.network.GeorefService;
import domain.organizacion.Direccion;

import java.io.IOException;

public class AdapterAPIUTN implements AdapterDistancia {

    private GeorefService georefService = new GeorefService();

    @Override
    public DistanceModel distanciaEntre(Direccion direcSalida, Direccion direcLlegada) {
        try {
            return georefService.getDistance(direcSalida, direcLlegada);
        } catch (IOException e) {
            System.out.println("---ERROR: " + e.getMessage()+ "---");
            return new DistanceModel();
        }
    }

}
