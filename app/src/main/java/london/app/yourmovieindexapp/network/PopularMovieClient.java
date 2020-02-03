package london.app.yourmovieindexapp.network;

import london.app.yourmovieindexapp.model.popular.PopularMovieModel;
import london.app.yourmovieindexapp.model.popular.detail.DetailModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PopularMovieClient {

    @GET("movie/popular?")
    Call<PopularMovieModel> getRepository(@Query("api_key") String key, @Query("page") Integer pageNum);

    @GET("movie/{detailid}")
    Call<DetailModel> getRepositoryDetail(@Path("detailid") Integer movieId, @Query("api_key") String key);

}
