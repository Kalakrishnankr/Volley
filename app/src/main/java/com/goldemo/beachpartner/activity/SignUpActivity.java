package com.goldemo.beachpartner.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.connections.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by seq-kala on 7/2/18.
 */

@TargetApi(Build.VERSION_CODES.N)
public class SignUpActivity extends AppCompatActivity{

    private static final int REQUEST_TAKE_GALLERY_VIDEO=1;
    private static final int REQUEST_TAKE_GALLERY_IMAGE=2;
    private ImageView imgVideo,imgProfile,imgPlay;
    private VideoView videoView;

    private EditText user_fname,user_lname,user_dob,user_email,user_password,user_confPasswd,user_location,user_mobileno;
    private String userName,lastName,dob,email,pass,confnPass,location,mobileno,android_id;
    private Button btnsignUp,user_male,user_female;
    private AwesomeValidation awesomeValidation;
    private  Uri selectedImageUri,selectedVideoUri;

    Calendar myCalendar = Calendar.getInstance();
    private static String sex=null;
    private LinearLayout llogin;
    RadioGroup userTypeRadio;
    RadioButton rb;
    String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        initActivity();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //Toast.makeText(this, "Device Id"+android_id, Toast.LENGTH_SHORT).show();

    }


    private void initActivity() {

        user_fname      = (EditText)  findViewById(R.id.input_firstname);
        user_lname      = (EditText)  findViewById(R.id.input_lastname);
        user_dob        = (EditText)  findViewById(R.id.input_dob);
        user_male       = (Button)    findViewById(R.id.btnMale);
        user_female     = (Button)    findViewById(R.id.btnFemale);
        user_email      = (EditText)  findViewById(R.id.input_email);
        user_password   = (EditText)  findViewById(R.id.input_password);
        user_confPasswd = (EditText)  findViewById(R.id.input_confirm_password);
        user_location   = (EditText)  findViewById(R.id.input_city);
        user_mobileno   = (EditText)  findViewById(R.id.input_mobile);
        btnsignUp       = (Button)    findViewById(R.id.btnSignUp);
        llogin          = (LinearLayout) findViewById(R.id.login);
        userTypeRadio   = (RadioGroup) findViewById(R.id.user_type);

        userTypeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb =(RadioButton) findViewById(checkedId);
                userType    = rb.getText().toString();
                //Toast.makeText(SignUpActivity.this, "you cliked"+userType, Toast.LENGTH_SHORT).show();

            }


        });

        user_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex="male";
                user_male.setTextColor(getResources().getColor(R.color.colorPrimary));
                user_female.setTextColor(getResources().getColor(R.color.white));
            }
        });

        user_female.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                sex="female";
                user_female.setTextColor(getResources().getColor(R.color.colorPrimary));
                user_male.setTextColor(getResources().getColor(R.color.white));
            }
        });

        llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Browse video from gallery
        /*imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent();
                intent.setType("video*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);

            }
        });
*/
        //browse profile picture from  gallery
        /*imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUEST_TAKE_GALLERY_IMAGE);
            }
        });*/

        //play video
        /*imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });*/

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //dob date
        user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dialog.show();

            }
        });

        //SignUp here
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName     = user_fname.getText().toString().trim();
                lastName     = user_lname.getText().toString().trim();
                dob          = user_dob.getText().toString().trim();
                email        = user_email.getText().toString().trim();
                pass         = user_password.getText().toString().trim();
                confnPass    = user_confPasswd.getText().toString().trim();
                location     = user_location.getText().toString().trim();
                mobileno     = user_mobileno.getText().toString().trim();


                addValidationToViews();//Method for validate feilds
                if(awesomeValidation.validate()){
                    if(sex!=null){
                        submitForm();

                    }else {
                        Toast.makeText(SignUpActivity.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                    }

                }

                if(awesomeValidation.validate()){
                    if(userType!=null){
                        submitForm();

                    }else {
                        Toast.makeText(SignUpActivity.this, "Please select Your UserType", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



    }


    private void addValidationToViews() {
        //adding validation to edittexts
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_firstname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_lastname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_dob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.doberror);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_mobile, "^[2-9]{2}[0-9]{8}$", R.string.mobilerror);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cityerror);
        String regx=".{8,}";
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_password,regx, R.string.invalid_password);
        awesomeValidation.addValidation(SignUpActivity.this, R.id.input_confirm_password,R.id.input_password , R.string.invalid_confirmpassword);
        //awesomeValidation.addValidation(this, R.id.profile_pic, "^null|$", R.string.error_your_id);

    }

    private void submitForm() {


        JSONObject object = new JSONObject();
        try {
            object.put("firstname",userName);
            object.put("lastname",lastName);
            object.put("dob",dob);
            object.put("gender",sex);
            object.put("email",email);
            object.put("password",pass);
            object.put("location",location);
            object.put("phoneNumber",mobileno);
            object.put("loginType",1);
            object.put("deviceId",android_id);
            object.put("authToken","");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jo= new JSONObject();
        try {
            jo.put("usertype",1);
            jo.put("userDetails",object);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.SIGNUP, jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response--", response.toString());

                        try {
                            String statusCode = response.getString("statusCode").toString().trim();
                            if(response!=null && statusCode.equals("000")){

                                Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error--", error.toString());
                        Toast toast = Toast.makeText(SignUpActivity.this, "Couldn't connect to Server", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
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



    public void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        user_dob.setText(sdf.format(myCalendar.getTime()));
    }


    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                selectedVideoUri = data.getData();
               // String filemanagerstring = selectedVideoUri.getPath();
               String selectedVideoPath = getPath(selectedVideoUri);
                if (selectedVideoPath != null) {

                    imgVideo.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    imgPlay.setVisibility(View.VISIBLE);
                    videoView.setVideoURI(Uri.parse(selectedVideoPath));

                }
            }
            if(requestCode == REQUEST_TAKE_GALLERY_IMAGE){
                 selectedImageUri = data.getData();
                //String selectedImagePath = getPath(selectedImageUriImg);
                if (selectedImageUri != null) {
                    imgProfile.setImageURI(selectedImageUri);

                }

            }
        }
    }*/

    // UPDATED!
    /*public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }*/

}
