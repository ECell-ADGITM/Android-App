package in.ecelladgitm.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;
import in.ecelladgitm.Utilities.Constants;

public class EventRegistrationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_registration_activity);
        String registrationLink = getIntent().getStringExtra(Constants.INTENT_EXTRA_REGISTRATION_LINK);
        Pattern pattern = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");
        Matcher matcher = pattern.matcher(registrationLink);
        if (!matcher.matches())
            return;
        findViewById(R.id.text).setVisibility(View.GONE);
        WebView webView = findViewById(R.id.registration_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        progressDialog = ProgressDialog.show(this, "Please wait...", "Loading registration page", true);
        progressDialog.setCancelable(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progressDialog.dismiss();
            }
        });
        webView.loadUrl(registrationLink);
    }

}
