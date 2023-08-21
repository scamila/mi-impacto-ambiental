package data.network;

import data.model.DistanceModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GeorefApiClient {

    @GET("distancia")
    Call<DistanceModel> getDistance(@Query("localidadOrigenId") int localidadOrigenId,
                                    @Query("calleOrigen") String calleOrigen,
                                    @Query("alturaOrigen") String alturaOrigen,
                                    @Query("localidadDestinoId") int localidadDestinoId,
                                    @Query("calleDestino") String calleDestino,
                                    @Query("alturaDestino") String alturaDestino,
                                    @Header("Authorization") String authHeader
    );

}
