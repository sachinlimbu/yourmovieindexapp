package london.app.yourmovieindexapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import london.app.yourmovieindexapp.MovieDetails;
import london.app.yourmovieindexapp.R;
import london.app.yourmovieindexapp.model.popular.PopularMovieModel;
import london.app.yourmovieindexapp.model.popular.Result;
import london.app.yourmovieindexapp.network.Constants;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";


    private PopularMovieModel mPopularMovieModels;
    private Context mContext;
    private Result mResult;


    public RecyclerViewAdapter(PopularMovieModel popularMovieModels, Context context ) {
        mPopularMovieModels = popularMovieModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popularmovieslistactivity,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> getResult = mPopularMovieModels.getResults();

        if(getResult != null){
            holder.mTitle.setText(mPopularMovieModels.getResults().get(position).getTitle());
            Double rateMovie = mPopularMovieModels.getResults().get(position).getVoteAverage();
            float final_rating = (float) (rateMovie/2);
            holder.mRatingBar.setRating(final_rating);

//        mResult.getPosterPath()

            String fullMovieUrlPath = Constants.IMAGE_PATH + mPopularMovieModels.getResults().get(position).getPosterPath();
            Picasso.get().load(fullMovieUrlPath)
                    .into(holder.mMovieImage);

            holder.mParentLayout.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MovieDetails.class);
                intent.putExtra(Constants.MOVIE_ID,mPopularMovieModels.getResults().get(position).getId());
                mContext.startActivity(intent);

            });
        }else{
            Toast.makeText(mContext, R.string.invalid_adapter,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return mPopularMovieModels.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        ImageView mPosterPath;
        TextView mTitle;
        RatingBar mRatingBar;
        ImageView mMovieImage;
        RelativeLayout mParentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            mPosterPath = itemView.findViewById(R.id.id_poster_path);
            mTitle = itemView.findViewById(R.id.id_title);
            mRatingBar = itemView.findViewById(R.id.id_ratingBar);
            mMovieImage = itemView.findViewById(R.id.id_MovieImage);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
