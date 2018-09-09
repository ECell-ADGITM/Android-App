package in.ecelladgitm.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;
import in.ecelladgitm.Utilities.Constants;
import in.ecelladgitm.modelclasses.Event;


// Activity to show full details and available actions for available events

public class CurrentEventActivity extends AppCompatActivity {
    private Event currentEvent = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_event_activity);
        currentEvent = (Event) getIntent().getSerializableExtra("current_event");
        ImageView eventPoster = findViewById(R.id.current_event_poster);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        TextView eventDescription = findViewById(R.id.event_description);
        Picasso.with(this).load(currentEvent.getFeaturedPhoto()).into(eventPoster);
        toolbarTitle.setText(currentEvent.getEventName());
        eventDescription.setText(Html.fromHtml(currentEvent.getAboutEvent()));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_event_page:
                if (currentEvent.getFbLink() == null || currentEvent.getFbLink().length() < 1) {
                    Toast.makeText(this, "Event page not available", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity((new Intent(Intent.ACTION_VIEW)).setData(Uri.parse(currentEvent.getFbLink())));
                break;
            case R.id.insta_page:
                if (Constants.INSTAGRAM_PAGE == null || Constants.INSTAGRAM_PAGE.length() < 1) {
                    Toast.makeText(this, "Instagram page not available", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity((new Intent(Intent.ACTION_VIEW)).setData(Uri.parse(Constants.INSTAGRAM_PAGE)));
                break;
            case R.id.twitter_page:
                if (Constants.TWITTER_PAGE == null || Constants.TWITTER_PAGE.length() < 1) {
                    Toast.makeText(this, "Twitter page not available", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity((new Intent(Intent.ACTION_VIEW)).setData(Uri.parse(Constants.TWITTER_PAGE)));
                break;
            case R.id.youtube_page:
                Toast.makeText(getApplicationContext(), "No YouTube channel right now...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phone_number:
                if (currentEvent.getOrganiserContactNumber() == null || currentEvent.getOrganiserContactNumber().length() < 1) {
                    Toast.makeText(this, "Number not available", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CurrentEventActivity.this, new String[]{"android.permission.CALL_PHONE"}, 0);
                    return;
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentEvent.getOrganiserContactNumber())));
                break;
            case R.id.share_event:
                if (currentEvent.getFbLink() == null || currentEvent.getFbLink().length() < 1) {
                    Toast.makeText(this, "Event page not available", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, currentEvent.getFbLink());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if permission was granted, make a call
                    onClick(findViewById(R.id.phone_number));
                }
            }
        }
    }

}
