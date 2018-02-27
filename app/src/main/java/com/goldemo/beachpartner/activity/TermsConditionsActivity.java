package com.goldemo.beachpartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.goldemo.beachpartner.R;

public class TermsConditionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;
    private CheckBox tickContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsconditions);
        initActivity();
    }

    private void initActivity() {

        btnContinue     =   (Button) findViewById(R.id.btnContinue);
        tickContinue    =   (CheckBox) findViewById(R.id.checkText);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(tickContinue.isChecked()){

            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Please agree", Toast.LENGTH_SHORT).show();
        }
    }
}
