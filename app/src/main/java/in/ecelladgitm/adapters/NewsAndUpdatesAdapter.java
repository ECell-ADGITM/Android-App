package in.ecelladgitm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.ecelladgitm.R;
import in.ecelladgitm.activities.WebViewActivity;
import in.ecelladgitm.viewmodels.News;

public class NewsAndUpdatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<News> news;

    public NewsAndUpdatesAdapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_and_updates_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;
        News currentNews = news.get(vh.getAdapterPosition());
        vh.title.setText(currentNews.getTitle());
        Glide.with(context).load(currentNews.getImageUrl()).into(vh.img);
        vh.source.setText(currentNews.getSourceName());
        vh.date.setText(currentNews.getDate());
        vh.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", news.get(vh.getAdapterPosition()).getSourceUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener listener;
        TextView title;
        TextView source;
        TextView date;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.source);
            date = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.image);
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
