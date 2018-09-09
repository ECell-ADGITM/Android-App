package in.ecelladgitm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.ecelladgitm.R;
import in.ecelladgitm.adapters.NewsAndUpdatesAdapter;
import in.ecelladgitm.viewmodels.MainViewModel;
import in.ecelladgitm.viewmodels.News;
import in.ecelladgitm.viewmodels.NewsAndUpdatesViewModel;

public class NewsAndUpdatesFragment extends Fragment {

    private ArrayList<News> news;
    private NewsAndUpdatesAdapter newsAndUpdatesAdapter;

    public static NewsAndUpdatesFragment newInstance() {
        return new NewsAndUpdatesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_and_updates_fragment, container, false);
        news = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAndUpdatesAdapter = new NewsAndUpdatesAdapter(getContext(), news);
        recyclerView.setAdapter(newsAndUpdatesAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsAndUpdatesViewModel newsAndUpdatesViewModel = ViewModelProviders.of(this).get(NewsAndUpdatesViewModel.class);
        newsAndUpdatesViewModel.getNewsListMutableLiveData().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news1) {
                int size = news.size();
                news.addAll(news1.subList(size, news1.size()));
                newsAndUpdatesAdapter.notifyItemRangeInserted(size, news1.size() - size);
            }
        });
        if (getActivity() == null)
            return;
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setCurrentFragmentStringID(R.string.news_and_updates);
    }

}
