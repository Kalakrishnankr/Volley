package com.goldemo.beachpartner.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.fragments.ProfileFragment;
import com.goldemo.beachpartner.instagram.Instagram;
import com.goldemo.beachpartner.instagram.InstagramSession;
import com.goldemo.beachpartner.instagram.InstagramUser;
import com.goldemo.beachpartner.models.UserDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button btnLogin,approve,cancel;
    private ImageView loginButton,instaLogin;
    private String uname,passwd;
    private TextView tsignUp,txt_forgotPass,result;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;
    private AwesomeValidation awesomeValidation;
    private ProgressDialog progress;
    private String token;
    private UserDataModel userDataModel;
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

                        startActivity(new Intent(LoginActivity.this,TabActivity.class));

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

        mInstagram          = new Instagram(this, ApiService.CLIENT_ID, ApiService.CLIENT_SECRET, ApiService.REDIRECT_URI);

        mInstagramSession   = mInstagram.getSession();

        initActivity();


    }



    private void initActivity() {

        userName        =   (EditText) findViewById(R.id.input_username);
        password        =   (EditText) findViewById(R.id.input_password);
        btnLogin        =   (Button)   findViewById(R.id.btnLogin);
        instaLogin      =   (ImageView)findViewById(R.id.instaLogin);
        tsignUp         =   (TextView) findViewById(R.id.tSignUp);
        loginButton     =   (ImageView)findViewById(R.id.login_button);
        txt_forgotPass  =   (TextView) findViewById(R.id.forgotPass);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("Loading...");

        //Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            
            public void onClick(View view) {

                uname = userName.getText().toString().trim();
                passwd= password.getText().toString().trim();
                addValidationToViews();

                //new code direct login 21/03/2018

                /*Intent intent = new Intent(LoginActivity.this,TabActivity.class);
                startActivity(intent);
                finish();*/

                //disabled direct login 21/03/2018
                if(awesomeValidation.validate()){

                        progress.show();
                        startLoginProcess(uname,passwd);//Method for start login
                        userName.setText("");
                        password.setText("");
                        userName.requestFocus();
                }

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

                mInstagram.authorize(mAuthListener);

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
                        //Method to call reset password
                        resetPassword(user_email);
                        //Toast.makeText(getBaseContext(), "Mail will be sent to: " + user_email , Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getBaseContext(), "Please check your email" , Toast.LENGTH_SHORT).show();

                    }}
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }

        });


    }


    private void addValidationToViews() {
        awesomeValidation.addValidation(LoginActivity.this, R.id.input_username, Patterns.EMAIL_ADDRESS, R.string.error_username);
        String regx=".{5,}";
        awesomeValidation.addValidation(LoginActivity.this,R.id.input_password,regx,R.string.invalid_password);
    }


    //Method for login
    private void startLoginProcess(final String uname, final String passwd) {

            JSONObject object = new JSONObject();
            try {
                object.put("password",passwd);
                object.put("username",uname);
                object.put("rememberMe",true);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.LOGIN, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response--", response.toString());

                        if (response!=null ) {

                            try {
                                token  = response.getString("idToken").toString().trim();


                                if(token!=null && !token.isEmpty()) {
                                    progress.dismiss();
                                    JSONObject user = response.getJSONObject("user");
                                    new PrefManager(getApplicationContext()).saveUserType(user.getString("userType"));
                                    //save username password and token in shared preference
                                    new PrefManager(getApplicationContext()).saveLoginDetails(uname,passwd,token);

                                    //get Login user info
                                    getUserInfo();
                                    Intent intent = new Intent(LoginActivity.this,TabActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Please try later", Toast.LENGTH_SHORT).show();
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {

                            Log.d("Bad response--", response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = null;
                        Log.d("error--", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null){
                            switch (response.statusCode){
                                case 401:
                                    json = new String (response.data);
                                    json = trimMessage(json,"detail");
                                    if(json!=null){
                                        progress.dismiss();
                                        Toast toast = Toast.makeText(LoginActivity.this, " "+json, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                                        toast.show();
                                    }
                                    break;
                                    default:
                                        break;
                            }
                        }

                        //progressDialog.dismiss();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d("Request", objectRequest.toString());
        requestQueue.add(objectRequest);

    }

    //Method for get Login user info
    private void getUserInfo() {

        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ACCOUNT_DETAILS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response get account--", response.toString());
                        try {
                            userDataModel = new UserDataModel();
                            userDataModel.setId(response.getString("id"));
                            userDataModel.setFirstName(response.getString("firstName"));
                            userDataModel.setLastName(response.getString("lastName"));
                            userDataModel.setGender(response.getString("gender"));
                            userDataModel.setDob(response.getString("dob"));
                            userDataModel.setCity(response.getString("city"));
                            userDataModel.setPhoneNumber(response.getString("phoneNumber"));
                            new PrefManager(getApplicationContext()).saveUserDetails(response.getString("id"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = null;
                        Log.d("error--", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            switch (response.statusCode) {
                                case 401:
                                    json = new String(response.data);
                                    json = trimMessage(json, "detail");
                                    if (json != null) {
                                        Toast.makeText(LoginActivity.this, ""+json, Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization","Bearer "+token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d("Request", objectRequest.toString());
        requestQueue.add(objectRequest);

    }

    private String trimMessage(String json, String detail) {
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(detail);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    //Method for reset password
    private void resetPassword(final String user_email) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(ApiService.REQUEST_METHOD_POST, ApiService.REQUEST_PASSWORD_RESET,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response email", response);
                        Toast.makeText(getBaseContext(), "Mail will be sent to: " + user_email , Toast.LENGTH_SHORT).show();

                         //Method for reset password from
                         resetPasswordCall();
                        //Toast.makeText(LoginActivity.this, "Succes Response" + response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String jsonValue = null;
                        Log.d("ERROR","error => "+error.toString());
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null){
                            switch (response.statusCode){
                                case 400:
                                    jsonValue = new String (response.data);
                                    jsonValue = trimMessage(jsonValue,"title");
                                    if(jsonValue!=null){
                                        progress.dismiss();
                                        Toast.makeText(LoginActivity.this, " "+jsonValue, Toast.LENGTH_LONG).show();

                                    }
                                    break;
                                    default:
                                        break;
                            }
                        }

                    }
                }
        ) {
            // this is the relevant method
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody=user_email;

                return httpPostBody.getBytes();
            }

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(postRequest);
}




    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {

            finish();
            String insta_username = user.fullName;
            Toast.makeText(LoginActivity.this, "Your are logged in as : "+insta_username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,TabActivity.class);
            startActivity(intent);
//            startActivity(new Intent(LoginActivity.this, TabActivity.class));

        }

        @Override
        public void onError(String error) {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "OK. Maybe later?", Toast.LENGTH_SHORT).show();

        }
    };

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

    private void resetPasswordCall() {

        final AlertDialog.Builder alerts = new AlertDialog.Builder(this);
        LayoutInflater inflater   = LoginActivity.this.getLayoutInflater();
        View view     = inflater.inflate(R.layout.alert_dialog_box,null);
        alerts.setView(view);
        alerts.setCancelable(true);
        final AlertDialog alertDialog  = alerts.create();
        final  EditText editText_key =  (EditText) view.findViewById(R.id.edittxt_key);
        final  EditText editText_pass=  (EditText) view.findViewById(R.id.edittxt_newPassword);
        final  EditText editText_confm_pass = (EditText) view.findViewById(R.id.edittxt_confirmPassword);

        Button   btn_confirm =   (Button) view.findViewById(R.id.btn_resetOk);
        Button   btn_cancel  =   (Button) view.findViewById(R.id.btn_resetCancel);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateResetForm();
                if(awesomeValidation.validate()){

                    String keyValue = editText_key.getText().toString().trim();
                    String passWord = editText_confm_pass.getText().toString().trim();
                    alertDialog.cancel();
                    //Api for reset password
                    changePassword(keyValue,passWord);
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void changePassword(String keyValue, String passWord) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key",keyValue);
            jsonObject.put("newPassword",passWord);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.CHANGE_PASSWORD, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Success Response", response.toString());
                        Toast.makeText(LoginActivity.this, "Successfully reset your password", Toast.LENGTH_SHORT).show();

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String jsonErrorMsg = null;
                        //Log.d("error--", error.toString());
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null){
                            switch (response.statusCode){
                                case 500:
                                    jsonErrorMsg = new String (response.data);
                                    jsonErrorMsg = trimMessage(jsonErrorMsg,"title");
                                    if(jsonErrorMsg!=null){
                                        progress.dismiss();
                                        Toast.makeText(LoginActivity.this, " "+jsonErrorMsg, Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                case 401:
                                    jsonErrorMsg = new String (response.data);
                                    jsonErrorMsg = trimMessage(jsonErrorMsg,"title");
                                    if(jsonErrorMsg!=null){
                                        progress.dismiss();
                                        Toast.makeText(LoginActivity.this, " "+jsonErrorMsg, Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }

                        //progressDialog.dismiss();
                    }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                if (response.data == null || response.data.length == 0) {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return super.parseNetworkResponse(response);
                }
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d("Request", objRequest.toString());
        requestQueue.add(objRequest);

    }

    private void validateResetForm() {
        String regx_key = ".{4,}";
        awesomeValidation.addValidation(LoginActivity.this,R.id.edittxt_key,regx_key,R.string.invalid_key);
        String regx_pass= ".{8,}";
        awesomeValidation.addValidation(LoginActivity.this,R.id.edittxt_newPassword,regx_pass,R.string.hint_passord_size);
        awesomeValidation.addValidation(LoginActivity.this,R.id.edittxt_confirmPassword,R.id.edittxt_newPassword,R.string.hint_didnotmatch);
    }

}
