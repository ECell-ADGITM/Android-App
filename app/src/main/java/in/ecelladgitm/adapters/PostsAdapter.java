package in.ecelladgitm.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.ecelladgitm.R;
import in.ecelladgitm.activities.ArticleActivity;
import in.ecelladgitm.modelclasses.Post;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Post> articles;

    public PostsAdapter(Context context, ArrayList<Post> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_article_row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;
        Post currentArticle = articles.get(position);
        vh.heading.setText(currentArticle.getHeading());

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null)
            activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        vh.errorText.setVisibility(View.VISIBLE);
        vh.img.setVisibility(View.GONE);
        if (isConnected) {
            vh.errorText.setText(R.string.loading_image);
        } else {
            vh.errorText.setText(R.string.network_not_available);
        }
        Picasso.with(context).load(currentArticle.getImage()).into(vh.img, new Callback() {
            @Override
            public void onSuccess() {
                vh.errorText.setVisibility(View.GONE);
                vh.img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });
        vh.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("article", articles.get(vh.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener listener;
        TextView heading;
        TextView errorText;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.nameTxt);
            img = itemView.findViewById(R.id.imageView);
            errorText = itemView.findViewById(R.id.errorText);
            itemView.setOnClickListener(this);
        }

        public void setRecyclerViewClickListener(RecyclerViewClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
