package di;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static util.Constants.BASE_URL;

public class NetworkModule {

    private static NetworkModule instance = null;
    private Retrofit retrofit;

    private NetworkModule() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkModule getInstance() {
        if (instance == null) {
            instance = new NetworkModule();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
