package london.app.yourmovieindexapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import london.app.yourmovieindexapp.model.popular.Result;
import london.app.yourmovieindexapp.model.popular.detail.DetailModel;
import london.app.yourmovieindexapp.network.Constants;
import london.app.yourmovieindexapp.network.PopularMovieClient;
import london.app.yourmovieindexapp.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    private ImageView detail_image;
    private TextView detail_title;
    private TextView detail_overview;
    private RatingBar detail_rating;

    private static final String TAG = "MovieDetails";
    private static final Integer DEFAULT_VALUE = 0;

    private boolean shouldShowError = true;
    private LinearLayout error_container_linear_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        detail_image = findViewById(R.id.id_detail_poster);
        detail_title = findViewById(R.id.id_detail_title);
        detail_overview = findViewById(R.id.id_detail_overview);
        detail_rating = findViewById(R.id.id_detail_rating_bar);
        error_container_linear_detail = findViewById(R.id.error_container_linear_detail);

        setTitle(R.string.move_detail);

        Integer detailMovieId = getIntent().getIntExtra(Constants.MOVIE_ID,DEFAULT_VALUE);

        if(detailMovieId == null){
            showError();
        }else{
            PopularMovieClient detail_client = RetrofitInstance.getClient();

            Call<DetailModel> detailCall = detail_client.getRepositoryDetail(detailMovieId,Constants.API_KEY);

            detailCall.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                    showContent(response);
                }
                @Override
                public void onFailure(Call<DetailModel> call, Throwable t) {
                    Log.i(TAG, getString(R.string.on_Failure)+t.getMessage());
                }
            });
        }
    }

    private void showContent(Response<DetailModel> response) {
        DetailModel detailModel = response.body();
        detail_title.setText(detailModel.getTitle());
        detail_overview.setText(detailModel.getOverview());

        float voteAverage = (float) (detailModel.getVoteAverage() / 2);
        detail_rating.setRating(voteAverage);

        Picasso.get().load(Constants.IMAGE_PATH + detailModel.getPosterPath()
        ).into(detail_image);
    }

    private void showError(){
        if(shouldShowError){
            error_container_linear_detail.setVisibility(View.VISIBLE);

            detail_image.setVisibility(View.GONE);
            detail_title.setVisibility(View.GONE);
            detail_overview.setVisibility(View.GONE);
            detail_rating.setVisibility(View.GONE);
        }
    }

}
