package data.network;

import data.model.DistanceModel;
import di.NetworkModule;
import domain.organizacion.Direccion;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static util.Constants.BEARER_TOKEN;

public class GeorefService {
    private NetworkModule networkModule = NetworkModule.getInstance();

    public DistanceModel getDistance(Direccion direcSalida, Direccion direcLlegada) throws IOException {

        GeorefApiClient georefApiClient = networkModule.getRetrofit().create(GeorefApiClient.class);
        Call<DistanceModel> distanceModelCall = georefApiClient.getDistance(direcSalida.getLocalidadID(), direcSalida.getCalle(),
                direcSalida.getAltura(), direcLlegada.getLocalidadID(), direcLlegada.getCalle(), direcLlegada.getAltura(), BEARER_TOKEN);
        Response<DistanceModel> response = distanceModelCall.execute();
        return  response.body();
    }
}
