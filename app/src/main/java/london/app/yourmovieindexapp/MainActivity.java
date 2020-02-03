package london.app.yourmovieindexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import london.app.yourmovieindexapp.adapter.RecyclerViewAdapter;
import london.app.yourmovieindexapp.model.popular.PopularMovieModel;
import london.app.yourmovieindexapp.network.Constants;
import london.app.yourmovieindexapp.network.PopularMovieClient;
import london.app.yourmovieindexapp.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private  RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private static final String TAG = "MainActivity";
    private static final Integer PAGE_NUMBER = 2;

    private boolean shouldShowError = true;
    private LinearLayout mErrorLinearLayout;
    private TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mErrorLinearLayout = findViewById(R.id.error_container_linear);
        mErrorTextView = findViewById(R.id.error_text_view_new);
        setTitle(R.string.popular_movie);

        PopularMovieClient client = RetrofitInstance.getClient();

        Call<PopularMovieModel> call = client.getRepository(Constants.API_KEY,PAGE_NUMBER);

            call.enqueue(new Callback<PopularMovieModel>() {
                @Override
                public void onResponse(Call<PopularMovieModel> call, Response<PopularMovieModel> response) {
                    PopularMovieModel popularMovieModel = response.body();

                    if(popularMovieModel == null){
                        showError();
                    }else{
                        mRecyclerViewAdapter = new RecyclerViewAdapter(popularMovieModel,MainActivity.this);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    }
                }

                @Override
                public void onFailure(Call<PopularMovieModel> call, Throwable t) {
                    Log.i(TAG, getString(R.string.on_Failure)+t.getMessage());
                }
            });
        }
    private void showError(){
        if(shouldShowError){
            Toast.makeText(getApplicationContext(),getString(R.string.error_handling_details),Toast.LENGTH_SHORT).show();
        }
    }
}
