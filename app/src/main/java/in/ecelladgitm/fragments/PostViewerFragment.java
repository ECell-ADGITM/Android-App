package in.ecelladgitm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import in.ecelladgitm.R;
import in.ecelladgitm.viewmodels.PostViewerViewModel;


public class PostViewerFragment extends Fragment {

    private PostViewerViewModel mViewModel;

    public static PostViewerFragment newInstance() {
        return new PostViewerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_viewer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostViewerViewModel.class);
        // TODO: Use the ViewModel
    }

}
