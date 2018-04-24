package com.beachpartnerllc.beachpartner.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.ApiService;

public class TermsConditionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;
    private CheckBox tickContinue;
    private WebView webTC;
    private ProgressBar pbar;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean value = prefs.getBoolean("condition",false);
        if(!value){
            setContentView(R.layout.activity_termsconditions);
            initActivity();
        }
        else {
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initActivity() {

        btnContinue     =   (Button) findViewById(R.id.btnContinue);
        tickContinue    =   (CheckBox) findViewById(R.id.checkText);
        webTC           =   (WebView) findViewById(R.id.webview_tc);
        pbar            =   (ProgressBar) findViewById(R.id.pgbar);

        //webTC.loadUrl(ApiService.TERMS_AND_CONDITION);
        webTC.getSettings().setJavaScriptEnabled(true);
        webTC.getSettings().setSupportZoom(true);
        webTC.getSettings().setBuiltInZoomControls(true);
        webTC.loadUrl(ApiService.TERMS_AND_CONDITION);
        pbar.setMax(100);
        pbar.setProgress(1);

        webTC.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                pbar.setProgress(progress);
            }
        });
        webTC.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbar.setVisibility(View.VISIBLE);


            }



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                pbar.setVisibility(View.GONE);
            }
        });

        webTC.getSettings().setJavaScriptEnabled(true);
        pbar.setVisibility(View.INVISIBLE);


        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(tickContinue.isChecked()){

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
            editor.putBoolean("condition",tickContinue.isChecked());
            editor.apply();

            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Please agree", Toast.LENGTH_SHORT).show();
        }
    }
}
