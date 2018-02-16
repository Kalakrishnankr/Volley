package com.goldemo.beachpartner.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.goldemo.beachpartner.R;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button btnLogin,approve,cancel;
    ;
    private ImageView loginButton,instaLogin;
    private String uname,passwd;
    private TextView tsignUp,txt_forgotPass,result;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        initActivity();


    }



    private void initActivity() {

        userName    = (EditText) findViewById(R.id.input_username);
        password    = (EditText) findViewById(R.id.input_password);
        btnLogin    = (Button)   findViewById(R.id.btnLogin);
        instaLogin  =(ImageView)findViewById(R.id.instaLogin);
        tsignUp     = (TextView) findViewById(R.id.tSignUp);
        loginButton = (ImageView) findViewById(R.id.login_button);
        txt_forgotPass=(TextView) findViewById(R.id.forgotPass);

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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
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

        //forget password
        txt_forgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_forgot_password, null);

                result = (EditText) alertLayout.findViewById(R.id.editTextDialogUserInput);

                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Reset Password");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                 @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Reset cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_email = result.getText().toString().trim();
                    if(isValidateUserName(user_email)){
                        Toast.makeText(getBaseContext(), "Mail will be sent to: " + user_email , Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getBaseContext(), "Please check your email" , Toast.LENGTH_SHORT).show();

                    }}
                });
                AlertDialog dialog = alert.create();
                dialog.show();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Intent intent = new Intent(LoginActivity.this, TabActivity.class);
                        intent.putExtra("userProfile", json_object.toString());
                        startActivity(intent);
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

}
