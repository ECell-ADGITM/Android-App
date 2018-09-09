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
import in.ecelladgitm.adapters.PostsAdapter;
import in.ecelladgitm.modelclasses.Post;
import in.ecelladgitm.viewmodels.MainViewModel;
import in.ecelladgitm.viewmodels.PostsViewModel;

public class PostsFragment extends Fragment {

    private ArrayList<Post> posts;
    private PostsAdapter postsAdapter;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_fragment, container, false);
        posts = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter(getContext(), posts);
        recyclerView.setAdapter(postsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts1) {
                int size = posts.size();
                posts.addAll(posts1.subList(size, posts1.size()));
                postsAdapter.notifyItemRangeInserted(size, posts1.size() - size);
            }
        });
        if (getActivity() == null)
            return;
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setCurrentFragmentStringID(R.string.posts);
    }

}
