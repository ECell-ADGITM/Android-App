package in.ecelladgitm.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import in.ecelladgitm.R;
import in.ecelladgitm.Utilities.Constants;
import in.ecelladgitm.activities.WebViewActivity;
import in.ecelladgitm.viewmodels.AboutUsViewModel;
import in.ecelladgitm.viewmodels.MainViewModel;

public class AboutUsFragment extends Fragment {

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment, container, false);
        ((TextView)view.findViewById(R.id.facebookPage)).setText(Constants.FACEBOOK_PAGE);
        ((TextView)view.findViewById(R.id.twitter)).setText(Constants.TWITTER_PAGE);
        ((TextView)view.findViewById(R.id.instagram)).setText(Constants.INSTAGRAM_PAGE);
        ((TextView)view.findViewById(R.id.linkedin)).setText(Constants.LINKED_IN);
        ((TextView)view.findViewById(R.id.youtubeChannel)).setText(Constants.YOUTUBE_CHANNEL);
        view.findViewById(R.id.terms_of_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", Constants.TERMS_OF_SERVICE);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", Constants.PRIVACY_POLICY);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.credits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", Constants.CREDITS);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Constants.EMAIL_ADDRESS));
                startActivity(Intent.createChooser(intent, "Send e-mail with"));
            }
        });
        view.findViewById(R.id.website).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEBSITE)));
            }
        });
        view.findViewById(R.id.facebookPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.FACEBOOK_PAGE));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.TWITTER_PAGE));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.INSTAGRAM_PAGE));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.linkedin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.LINKED_IN));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.youtubeChannel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.YOUTUBE_CHANNEL));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AboutUsViewModel mViewModel = ViewModelProviders.of(this).get(AboutUsViewModel.class);
        if (getActivity() == null)
            return;
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setCurrentFragmentStringID(R.string.about_us);
    }

}
