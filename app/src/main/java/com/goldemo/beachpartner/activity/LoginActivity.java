package com.goldemo.beachpartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button btnLogin;
    private ImageView fbLogin,instaLogin;
    private String uname,passwd;
    private TextView tsignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActivity();

    }

    private void initActivity() {

        userName = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button)   findViewById(R.id.btnLogin);
        fbLogin  = (ImageView)findViewById(R.id.fbLogin);
        instaLogin=(ImageView)findViewById(R.id.instaLogin);
        tsignUp  = (TextView) findViewById(R.id.tSignUp);


        //Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uname = userName.getText().toString().trim();
                passwd= password.getText().toString().trim();

                startLoginProcess();
                /*if(uname!=null && userName.getText().toString().length()!=0){
                    if(isValidateUserName(uname)){
                        if(passwd!=null && password.getText().toString().length()!=0){

                            if((uname.equals("admin@gmail.com")) &&(passwd.equals("admin"))){
                                //start Login
                                startLoginProcess();
                            }else {
                                Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(LoginActivity.this, "Please enter valid username", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        //Fb login click
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Insta Login
        instaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //SignUp here
        tsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "SignUp", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }


    //Method for login
    private void startLoginProcess() {
        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(getApplicationContext(),TabActivity.class);
        startActivity(intent);
    }

    public final static boolean isValidateUserName(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
