package london.app.yourmovieindexapp.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit sRetrofit;
    private static PopularMovieClient sPopularMovieClient;


    public static Retrofit getRetrofitInstance(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return sRetrofit;
    }

    public static PopularMovieClient getClient(){
        if(sPopularMovieClient == null){
            sPopularMovieClient = getRetrofitInstance().create(PopularMovieClient.class);
        }
        return sPopularMovieClient;
    }

}
