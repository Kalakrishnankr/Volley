package com.beachpartnerllc.beachpartner.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.ApiService;

public class TermsConditionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;
    private CheckBox tickContinue;
    private WebView webTC;
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
        webTC.loadUrl(ApiService.TERMS_AND_CONDITION);

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
