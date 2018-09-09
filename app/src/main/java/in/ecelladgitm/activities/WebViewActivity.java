package in.ecelladgitm.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;

public class WebViewActivity extends AppCompatActivity {


    ProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        progressDialog = ProgressDialog.show(this, "Please wait...", "Loading the page", true);
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
                progressDialog.cancel();
            }
        });
        webView.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.cancel();
    }
}
