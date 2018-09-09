package in.ecelladgitm.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;
import in.ecelladgitm.fragments.PostViewerFragment;

public class PostViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_viewer_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostViewerFragment.newInstance())
                    .commitNow();
        }
    }

}
