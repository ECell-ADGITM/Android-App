package in.ecelladgitm.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;
import in.ecelladgitm.modelclasses.Post;

public class ArticleActivity extends AppCompatActivity {
    ImageView image;
    TextView heading;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        Post article = (Post) getIntent().getSerializableExtra("article");
        setContentView(R.layout.article_activity);
        image = findViewById(R.id.article_image);
        heading = findViewById(R.id.heading);
        content = findViewById(R.id.content);
        heading.setText(article.getHeading());
        content.setText(Html.fromHtml(article.getContent()));
        Picasso.with(getApplicationContext()).load(article.getImage()).into(image, new Callback() {
            @Override
            public void onSuccess() {
                image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });
    }
}
