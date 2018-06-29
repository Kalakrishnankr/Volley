package com.beachpartnerllc.beachpartner.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileResponse.CoachProfileResponse;
import com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.CoachProfileUpdateInputData;
import com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserInputDto;
import com.beachpartnerllc.beachpartner.models.Coach.CoachProfile.CoachProfileUpdateInputData.UserProfileDto;
import com.beachpartnerllc.beachpartner.models.UserDataModel;
import com.beachpartnerllc.beachpartner.models.UserProfileModel;
import com.beachpartnerllc.beachpartner.utils.AppCommon;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.beachpartnerllc.beachpartner.utils.ServiceClass;
import com.beachpartnerllc.beachpartner.utils.UploadObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_SAVEIMGTODRIVE = 3;
    private static final int REQUEST_TAKE_GALLERY_IMAGE = 2;
    private static final int PICK_IMAGE_REQUEST = 0;
    private static final String TAG = "CoachProfile";
    private static boolean editStatus = false;
    // private static AsyncPhotoUploadTask asyncTask;
    public UserDataModel userDataModel;
    Calendar myCalendar = Calendar.getInstance();
    String mFirstName;
    String mLastName;
    String mGender;
    String mDob;
    String mCity;
    String mPhone;
    String mCollege;
    String mDescription;
    String mYearsRunning;
    String mNoOfAthletes;
    String mProgramsOffered;
    String mDivision;
    String mProgramFunding;
    String mProgramShareAthletes;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout profileImgLayout;
    private CircularImageView imgProfile;
    private LinearLayout llMenuBasic, llMenuMore, llBasicDetails, llMoreDetails, coachBtnsBottom, coachMore_infoBtnsBottom;
    private TextView basic_info_tab, more_info_tab, edit_tag, profileName;
    private View viewBasic, viewMore;
    private EditText editFname, editLname, editGender, editDob, editPhone, description, years_running, no_athletes, prog_offered, division, editCollege;
    private Spinner stateSpinner;
    private Spinner program_funding, program_share_athletes;
    private Button basicBtnSave, basicBtnCancel, moreBtnSave, moreBtnCancel;
    private ImageView imgEdit, profile_img_editIcon;
    private String token, user_id, program_funding_value, program_share_athletes_value, imgUri;
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;
    private Uri selectedImageUri;
    private String location,imageUri;
    private long maxDate;
    CoachProfileResponse userCoachModel;
    Bitmap profilePhoto = null;
    private ImageView imgBgCoach;
    private static ProgressDialog progress;
    private ServiceClass uploadService;
    private MultipartBody.Part fileImageToUpload,filevideoToUploaded;
    Bundle bundle;
    private  BpFinderModel finderModel;
    TabActivity tabActivity;



    public CoachProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoachProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoachProfileFragment newInstance(String param1, String param2) {
        CoachProfileFragment fragment = new CoachProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleyLog.DEBUG = true;
        editStatus=false;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        token = new PrefManager(getContext()).getToken();
        user_id = new PrefManager(getContext()).getUserId();
        View view = inflater.inflate(R.layout.fragment_coach_profile, container, false);
        progress = new ProgressDialog(getContext());
        initView(view);

        bundle = getArguments();
        if (bundle != null) {
            finderModel = (BpFinderModel) bundle.getSerializable(AppConstants.USER_DETAIL);
            setViewForConnectedUSer();
        }else{
            setUp();
        }

        return view;


    }

    private void setViewForConnectedUSer() {
        imgEdit.setVisibility(View.INVISIBLE);
        edit_tag.setVisibility(View.INVISIBLE);
        imgProfile.setClickable(false);
        try{
            userCoachModel = new CoachProfileResponse();
            userCoachModel.setId(finderModel.getBpf_id());
            userCoachModel.setFirstName(finderModel.getBpf_firstName());
            userCoachModel.setLastName(finderModel.getBpf_lastName());
            userCoachModel.setGender(finderModel.getBpf_gender());
            userCoachModel.setDob(finderModel.getBpf_dob());
            userCoachModel.setCity(finderModel.getBpf_city());
            userCoachModel.setPhoneNumber(finderModel.getBpf_phoneNumber());
            userCoachModel.setLangKey(finderModel.getBpf_langKey());
            userCoachModel.setLocation(finderModel.getBpf_city());
            //userDataModel.setSubscriptions(response.getString("subscriptions"));
            userCoachModel.setImageUrl(finderModel.getBpf_imageUrl());
            userCoachModel.setVideoUrl(finderModel.getBpf_videoUrl());
            userCoachModel.setUserType(finderModel.getBpf_userType());
            userCoachModel.setFcmToken(finderModel.getBpf_fcmToken());
            userDataModel.setAuthToken(finderModel.getBpf_authToken());
            userDataModel.setDeviceId(finderModel.getBpf_deviceId());
            userDataModel.setEmail(finderModel.getBpf_email());
            if (!finderModel.getUserProfile().equals(null) && !finderModel.getUserProfile().equals("null")) {

                UserProfileModel tempFinderModel = finderModel.getUserProfile();
                if (tempFinderModel != null) {
                    userCoachModel.setUserProfile(tempFinderModel);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setView();
    }


    private void initView(final View view) {
        profileImgLayout    = view.findViewById(R.id.profile_img_layout);
        llMenuBasic         = view.findViewById(R.id.llCoachMenuBasic);
        llMenuMore          = view.findViewById(R.id.llCoachMenuMore);
        imgBgCoach          = view.findViewById(R.id.img_bg_coach);
        basic_info_tab      = view.findViewById(R.id.coach_basic_info_tab);
        more_info_tab       = view.findViewById(R.id.coach_more_info_tab);

        llBasicDetails      = view.findViewById(R.id.llCoachBasicDetails);
        llMoreDetails       = view.findViewById(R.id.llCoachMoreInfoDetails);
        viewBasic           = view.findViewById(R.id.viewCoachBasic);
        viewMore            = view.findViewById(R.id.viewCoachMore);
        imgProfile          = view.findViewById(R.id.row_icon);
        profileName         = view.findViewById(R.id.coachName);

        //For Basic Details
        editFname           = view.findViewById(R.id.txtvFname);
        editLname           = view.findViewById(R.id.txtvLname);
        editGender          = view.findViewById(R.id.txtv_gender);
        editDob             = view.findViewById(R.id.txtv_dob);
        stateSpinner        = view.findViewById(R.id.txtv_city_Cprofile);
        editPhone           = view.findViewById(R.id.txtv_mobileno);
//        editPassword      = view.findViewById(R.id.txtv_password);
        basicBtnSave        = view.findViewById(R.id.btnsave);
        basicBtnCancel      = view.findViewById(R.id.btncancel);
        //Fore More Deatails
        editCollege         = view.findViewById(R.id.txtv_college);
        description         = view.findViewById(R.id.txtv_description);
        years_running       = view.findViewById(R.id.txtv_years_running);
        no_athletes         = view.findViewById(R.id.txtv_no_athletes);
        prog_offered        = view.findViewById(R.id.txtv_prog_offered);
        division            = view.findViewById(R.id.txtv_division);

        program_funding     = view.findViewById(R.id.spinner_program_funding);
        program_share_athletes = view.findViewById(R.id.spinner_program_share_athletes);
        imgEdit              = view.findViewById(R.id.edit);
        edit_tag             = view.findViewById(R.id.edit_text);
        profile_img_editIcon = view.findViewById(R.id.edit_profile_imgCoach);
        coachBtnsBottom = view.findViewById(R.id.coach_btns_at_bottom);
        coachMore_infoBtnsBottom = view.findViewById(R.id.coach_more_info_btns_bottom);
        moreBtnSave     = view.findViewById(R.id.btn_save);
        moreBtnCancel   = view.findViewById(R.id.btn_cancel);

        llMenuBasic.setOnClickListener(this);
        llMenuMore.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        basicBtnCancel.setOnClickListener(this);
        moreBtnSave.setOnClickListener(this);
        moreBtnCancel.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        profile_img_editIcon.setOnClickListener(this);

        //spinners initially disabled
        editCollege.setEnabled(false);
        program_funding.setEnabled(false);
        program_share_athletes.setEnabled(false);
        stateSpinner.setEnabled(false);
        List<String>stateList = AppConstants.getstatelist();
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
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        // Subtract 18 years
        c.add( Calendar.YEAR, -18 );
        maxDate = c.getTime().getTime();
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerDialog dialog = new DatePickerDialog(getContext(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH));
//                dialog.getDatePicker().setMaxDate(maxDate);
//                dialog.show();

                CustomDatePicker();
            }
        });

        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.gender_popup_layout, null);
                final RadioGroup radioGroup_gender = (RadioGroup) alertLayout.findViewById(R.id.gender_radio_group);
                final RadioButton radioButton_male = (RadioButton) alertLayout.findViewById(R.id.rd_1);
                final RadioButton female = (RadioButton) alertLayout.findViewById(R.id.rd_2);
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
                String titleText = "Select Gender!";
                // Initialize a new foreground color span instance
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
                // Initialize a new spannable string builder instance
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                // Apply the text color span
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                alert.setTitle(ssBuilder);
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (radioGroup_gender.getCheckedRadioButtonId() == radioButton_male.getId())
                            editGender.setText("Male");
                        else {
                            editGender.setText("Female");
                        }
                    }

                });

                final AlertDialog dialog = alert.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {

                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blueDark));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blueDark));
                    }
                });
                dialog.show();
            }

        });

        //state selection spinner
        dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, stateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(dataAdapter);
        stateSpinner.invalidate();
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                location = stateSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        funding spinner

        List<String> funding = new ArrayList<>();
        funding.add("Yes");
        funding.add("No");
        ArrayAdapter<String> fundingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, funding);
        fundingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program_funding.setAdapter(fundingAdapter);
        program_funding.invalidate();
        program_funding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                program_funding_value = program_funding.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

//        athletes shared between indoor and beach spinner

        List<String> athlete_sharing = new ArrayList<>();
        athlete_sharing.add("Yes");
        athlete_sharing.add("No");
        ArrayAdapter<String> athlete_sharingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, athlete_sharing);
        athlete_sharingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program_share_athletes.setAdapter(athlete_sharingAdapter);
        program_share_athletes.invalidate();
        program_share_athletes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                program_share_athletes_value = program_share_athletes.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



    }


    private void CustomDatePicker(){


        LayoutInflater inflater = getLayoutInflater();
        View datealertLayout = inflater.inflate(R.layout.custom_date_picker_dialog, null);

        final DatePicker dp     =          (DatePicker) datealertLayout.findViewById(R.id.datePicker_custom);
        final Button  okBtn     =          (Button)   datealertLayout.findViewById(R.id.okBtn);
        final Button  cancelBtn =           (Button) datealertLayout.findViewById(R.id.cancel_button);


        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());



        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));



        dp.setMaxDate(maxDate);
        dp.init(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Do something when the date changed from date picker object

                // Create a Date variable/object with user chosen date

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            }
        });

        alert.setView(datealertLayout);
        alert.setCancelable(true);


        final android.app.AlertDialog dialog = alert.create();


        dialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLabel();
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String set_date = sdf.format(myCalendar.getTime());
        editDob.setText(set_date);
    }


    private void editBasicInfo() {

        //Profile image edit icon active

        imgProfile.setClickable(true);
        coachBtnsBottom.setVisibility(View.VISIBLE);
        coachMore_infoBtnsBottom.setVisibility(View.VISIBLE);
        //profile_img_editIcon.setVisibility(View.VISIBLE);

        editFname.setEnabled(true);
        editFname.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editLname.setEnabled(true);
        editLname.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editGender.setEnabled(true);
        editGender.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editDob.setEnabled(true);
        editDob.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        stateSpinner.setEnabled(true);
        stateSpinner.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPhone.setEnabled(true);
        editPhone.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

//        editPassword.setEnabled(true);
//        editPassword.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

    }

    private void editMoreInfo() {
        editCollege.setEnabled(true);
        editCollege.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        description.setEnabled(true);
        description.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        years_running.setEnabled(true);
        years_running.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        no_athletes.setEnabled(true);
        no_athletes.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        prog_offered.setEnabled(true);
        prog_offered.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        division.setEnabled(true);
        division.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        program_funding.setEnabled(true);
        program_funding.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        program_share_athletes.setEnabled(true);
        program_share_athletes.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

    }

    private void editCustomView() {

        //profile_img_editIcon.setVisibility(View.VISIBLE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
        edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
    }

    private void InfoSave() {
        Log.d(TAG, "SAVE CLICKED");

        if (validateForms()){
            //imgProfile.setClickable(false);
            //profile_img_editIcon.setVisibility(View.GONE);
            coachBtnsBottom.setVisibility(View.GONE);
            imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            edit_tag.setTextColor(getResources().getColor(R.color.imgBacgnd));
            coachMore_infoBtnsBottom.setVisibility(View.GONE);
            //BasicInfo

            editFname.setEnabled(false);
            editFname.setBackground(null);

            editLname.setEnabled(false);
            editLname.setBackground(null);

            editGender.setEnabled(false);
            editGender.setBackground(null);

            editDob.setEnabled(false);
            editDob.setBackground(null);

            stateSpinner.setEnabled(false);
            stateSpinner.setBackground(null);

            editPhone.setEnabled(false);
            editPhone.setBackground(null);

            //MoreInfo

            editCollege.setEnabled(false);
            editCollege.setBackground(null);

            description.setEnabled(false);
            description.setBackground(null);


            years_running.setEnabled(false);
            years_running.setBackground(null);


            no_athletes.setEnabled(false);
            no_athletes.setBackground(null);


            prog_offered.setEnabled(false);
            prog_offered.setBackground(null);


            division.setEnabled(false);
            division.setBackground(null);


            program_funding.setEnabled(false);
            program_funding.setBackground(null);


            program_share_athletes.setEnabled(false);
            program_share_athletes.setBackground(null);


            Date date = null;
            Date dateLong = null;
            String stringDate = null;
            try {
                date = new SimpleDateFormat("MM-dd-yyyy").parse(editDob.getText().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);  //2018-04-25T05:29:19.777Z
                stringDate = dateFormat.format(date);
                dateLong = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CoachProfileUpdateInputData profileUpdateInputData = new CoachProfileUpdateInputData();
            UserInputDto userInputDto = new UserInputDto();
            UserProfileDto userProfileDto = new UserProfileDto();
            userInputDto.setAuthToken(token);
            userInputDto.setCity(mCity);
            userInputDto.setParentUserId("1");
            userInputDto.setDob(stringDate); // FIXME : CHANGE TO ORIGINAL VALUE
            userInputDto.setFirstName(mFirstName);
            userInputDto.setLastName(mLastName);
            userInputDto.setPhoneNumber(mPhone);
            userInputDto.setImageUrl(userCoachModel.getImageUrl());
            userInputDto.setGender(editGender.getText().toString());
            userInputDto.setUserType(AppConstants.USER_TYPE_COACH);

            userProfileDto.setCbvaFirstName(mFirstName);
            userProfileDto.setCbvaLastName(mLastName);
            userProfileDto.setCbvaPlayerNumber("");
            userProfileDto.setCollage(mCollege);
            userProfileDto.setCollageClub("");
            userProfileDto.setCollegeBeach("");
            userProfileDto.setCollegeIndoor("");
            userProfileDto.setCourtSidePreference("");
            userProfileDto.setDescription(mDescription);
            userProfileDto.setDivision(mDivision);
            userProfileDto.setExperience("");
            userProfileDto.setFundingStatus("");
            userProfileDto.setHeight("");
            userProfileDto.setHighSchoolAttended("");
            userProfileDto.setHighestTourRatingEarned("");
            userProfileDto.setIndoorClubPlayed("");
            userProfileDto.setNumOfAthlets(mNoOfAthletes);
            userProfileDto.setPosition("");
            userProfileDto.setProgramsOffered(mProgramsOffered);
            userProfileDto.setFundingStatus(mProgramFunding);
            userProfileDto.setShareAthlets(mProgramShareAthletes);
            userProfileDto.setTopFinishes("");
            userProfileDto.setTotalPoints("");
            userProfileDto.setTournamentLevelInterest("");
            userProfileDto.setToursPlayedIn("");
            userProfileDto.setUsaVolleyballRanking("");
            userProfileDto.setWillingToTravel("");
            userProfileDto.setYearsRunning(mYearsRunning);

            profileUpdateInputData.setUserInputDto(userInputDto);
            profileUpdateInputData.setUserProfileDto(userProfileDto);

            JSONObject _jsonObjectMore = new JSONObject();
            try {
                _jsonObjectMore = new JSONObject(new Gson().toJson(profileUpdateInputData));
                Log.e(TAG,_jsonObjectMore.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG,token);
            postUserMoreDetails(_jsonObjectMore);

        }
        else{
            editStatus = true;
            imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
            edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
            edit_tag.setText("Save Profile");
            editCustomView();
            editBasicInfo();
            editMoreInfo();
        }



    }

    private boolean validateForms() {
        boolean mFormValidation = AppCommon.FORM_VALID;


        mFirstName = getValue(editFname);
        mLastName = getValue(editLname);
        mGender = getValue(editGender);
        mDob = getValue(editDob);
        mCity = stateSpinner.getSelectedItem().toString();
        mPhone = getValue(editPhone);

        mCollege = getValue(editCollege);
        mDescription = getValue(description);
        mYearsRunning = getValue(years_running);
        mNoOfAthletes = getValue(no_athletes);
        mProgramsOffered = getValue(prog_offered);
        mDivision = getValue(division);
        mProgramFunding = program_funding.getSelectedItem().toString();
        mProgramShareAthletes = program_share_athletes.getSelectedItem().toString();

        if (TextUtils.isEmpty(mFirstName)){
            editFname.setError("Please enter your first name");
            mFormValidation = AppCommon.FORM_INVALID;
        }else {
            if (!Pattern.matches(AppCommon.VALID_STRING,mFirstName)){
                editFname.setError(getResources().getString(R.string.nameerror));
                mFormValidation = AppCommon.FORM_INVALID;
            }
        }
        if (TextUtils.isEmpty(mLastName)){
            editLname.setError("Please enter your last name");
            mFormValidation = AppCommon.FORM_INVALID;
        }else {
            if (!Pattern.matches(AppCommon.VALID_STRING,mLastName)){
                editLname.setError(getResources().getString(R.string.lnameerror));
                mFormValidation = AppCommon.FORM_INVALID;
            }
        }
        if (TextUtils.isEmpty(mGender)){
            editGender.setError(getResources().getString(R.string.gendererror));
            mFormValidation = AppCommon.FORM_INVALID;
        }
        if (TextUtils.isEmpty(mDob)){
            editGender.setError("Please choose date of birth");
            mFormValidation = AppCommon.FORM_INVALID;
        }

        if (TextUtils.isEmpty(mPhone)){
            editPhone.setError("Please enter your phone number");
            mFormValidation = AppCommon.FORM_INVALID;
        }else {
            if (!AppCommon.isValidMobileNumber(mPhone)){
                editPhone.setError("Please enter valid phone number");
                mFormValidation = AppCommon.FORM_INVALID;
            }
        }



        return mFormValidation;
    }
    private void reloadFragment() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.loadCoachProfileFragment();
        }
    }

    private String getValue(EditText view) {
        return view.getText().toString().trim();
    }

    private void postUserMoreDetails(JSONObject jsonObjectMore) {
        if (!progress.isShowing()) {
            progress.setTitle("Loading");

            progress.setMessage("Please wait while we save your data");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

            progress.show();
        }
        Log.d(TAG,jsonObjectMore.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, ApiService.UPDATE_USER_PROFILE +""+user_id, jsonObjectMore,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            if(getActivity()!=null ){
                                editStatus = false;
                                edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
                                edit_tag.setText("Edit Profile");
                                if (userCoachModel.getImageUrl() != null) {
                                    File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/" + userCoachModel.getImageUrl().substring(userCoachModel.getImageUrl().lastIndexOf('/') + 1));

                                    if (myFile.exists()) {
                                        Glide.with(CoachProfileFragment.this).load(myFile.getAbsolutePath()).into(imgProfile);


                                    }
                                    // if((userDataModel.getImageUrl().substring(userDataModel.getImageUrl().lastIndexOf('/') + 1).equals()){

                                    //  }
                                    //  Glide.with(ProfileFragment.this).load(userDataModel.getImageUrl()).into(imgProfile);
                                } else {
                                    imgProfile.setImageResource(R.drawable.ic_person);
                                }
                                progress.dismiss();
                                Toast.makeText(getActivity(), "Successfully updated your details", Toast.LENGTH_SHORT).show();
                                reloadFragment();
                            }

                        }
                        else{
                            progress.dismiss();
                            Toast.makeText(getActivity(), "Failed to update your details", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                String json = null;
                editStatus = false;
                edit_tag.setText("Edit Profile");
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "detail");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
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
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        tabActivity = (TabActivity)getActivity();
        tabActivity.getSupportActionBar().setTitle(finderModel.getBpf_firstName()+"'s profile");
    }

    private void InfoCancelChange() {
        imgProfile.setClickable(false);
        //profile_img_editIcon.setVisibility(View.GONE);
        coachBtnsBottom.setVisibility(View.GONE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        edit_tag.setTextColor(getResources().getColor(R.color.imgBacgnd));
        edit_tag.setText("Edit Profile");
        coachMore_infoBtnsBottom.setVisibility(View.GONE);
        //BasicInfo

        editFname.setEnabled(false);
        editFname.setBackground(null);

        editLname.setEnabled(false);
        editLname.setBackground(null);

        editGender.setEnabled(false);
        editGender.setBackground(null);

        editDob.setEnabled(false);
        editDob.setBackground(null);

        stateSpinner.setEnabled(false);
        stateSpinner.setBackground(null);

        editPhone.setEnabled(false);
        editPhone.setBackground(null);

        //MoreInfo

        editCollege.setEnabled(false);
        editCollege.setBackground(null);

        description.setEnabled(false);
        description.setBackground(null);


        years_running.setEnabled(false);
        years_running.setBackground(null);


        no_athletes.setEnabled(false);
        no_athletes.setBackground(null);


        prog_offered.setEnabled(false);
        prog_offered.setBackground(null);


        division.setEnabled(false);
        division.setBackground(null);


        program_funding.setEnabled(false);
        program_funding.setBackground(null);


        program_share_athletes.setEnabled(false);
        program_share_athletes.setBackground(null);

        editStatus = false;

    }

    private void setUp() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ACCOUNT_DETAILS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response get account--", response.toString());
                        try {
                            Gson gson = new Gson();
                            userCoachModel = gson.fromJson(response.toString(),CoachProfileResponse.class);

                            userDataModel = new UserDataModel();
                            userDataModel.setId(response.getString("id"));
                            userDataModel.setFirstName(response.getString("firstName"));
                            userDataModel.setLastName(response.getString("lastName"));
                            userDataModel.setGender(response.getString("gender"));
                            userDataModel.setDob(response.getString("dob"));
                            userDataModel.setCity(response.getString("city"));
                            userDataModel.setPhoneNumber(response.getString("phoneNumber"));
                            userDataModel.setImageUrl(response.getString("imageUrl"));
                            //new PrefManager(getActivity()).saveUserDetails(response.getString("id"));
                            setView();
                            //editFname.setText(userDataModel.getFirstName());
                            //editLname.setText(userDataModel.getLastName());

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
                                        Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", objectRequest.toString());
        requestQueue.add(objectRequest);

    }

    private void setView() {
        if(getActivity()!=null){
            if (userCoachModel !=null){
                profileName.setText(userCoachModel.getFirstName()+" "+userCoachModel.getLastName());
                editFname.setText(userCoachModel.getFirstName());
                editLname.setText(userCoachModel.getLastName());
                editGender.setText(userCoachModel.getGender());
                location = userCoachModel.getCity().trim();
                if (location != null) {
                    int positions = dataAdapter.getPosition(location);
                    stateSpinner.setSelection(positions);
                }
            }
            Log.i(TAG,"PROFILE_IMAGE_LOAD");

            if (userCoachModel.getImageUrl() != null && !userCoachModel.getImageUrl().equalsIgnoreCase("null")) {
                String imageName = userCoachModel.getImageUrl().substring(userCoachModel.getImageUrl().lastIndexOf('/') + 1);
                String[] imagePathArray = imageName.split("-");
                if (imagePathArray != null && imagePathArray.length > 0) {
                    String exactImageName = imagePathArray[imagePathArray.length-1];
                    Log.d("Image----",exactImageName);
                    Log.d("filename---", userCoachModel.getImageUrl().substring(userCoachModel.getImageUrl().lastIndexOf('/') + 1));
                    File myProfileImageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/" + "image" + "/" + exactImageName);

                    if (myProfileImageFile.exists()) {
                        Glide.with(CoachProfileFragment.this).load(myProfileImageFile.getAbsolutePath()).into(imgProfile);
                        Glide.with(getContext()).load(myProfileImageFile.getAbsolutePath()).into(imgBgCoach);
                    } else {
                        Glide.with(CoachProfileFragment.this).load(userCoachModel.getImageUrl()).into(imgProfile);
                        Glide.with(getContext()).load(userCoachModel.getImageUrl()).into(imgBgCoach);
                        new DownloadFileFromURL(exactImageName, "image",userCoachModel.getImageUrl()).execute();
                    }
                }

            } else {
                imgProfile.setImageResource(R.drawable.ic_person);
            }

            Log.i(TAG,"PROFILE_IMAGE_LOAD_END");
            //Long value to date conversion
            SimpleDateFormat dft = new SimpleDateFormat("MM-dd-yyyy");
            if (userCoachModel.getDob() != null){
                try {
                    long dob = Long.parseLong(String.valueOf(userCoachModel.getDob()));
                    Date date_dob = new Date(dob);
                    editDob.setText(dft.format(date_dob));
                    myCalendar.setTime(date_dob);
                }catch (Exception e){

                }
            }

            editPhone.setText(userCoachModel.getPhoneNumber());
            if(userCoachModel.getUserProfile()!=null){
                if (userCoachModel.getUserProfile().getUpf_collage() !=null || !userCoachModel.getUserProfile().getUpf_collage().equalsIgnoreCase("null"))
                    editCollege.setText(userCoachModel.getUserProfile().getUpf_collage());

                if(userCoachModel.getUserProfile().getUpf_description() != null){
                    if(!userCoachModel.getUserProfile().getUpf_description().equals("null")){
                        description.setText(userCoachModel.getUserProfile().getUpf_description().toString());
                    }
                }
                if(userCoachModel.getUserProfile().getUpf_yearsRunning() != null || userCoachModel.getUserProfile().getUpf_yearsRunning().equalsIgnoreCase("null"))
                    years_running.setText(userCoachModel.getUserProfile().getUpf_yearsRunning().toString());

                if(userCoachModel.getUserProfile().getUpf_numOfAthlets() != null || userCoachModel.getUserProfile().getUpf_numOfAthlets().equalsIgnoreCase("null"))
                    no_athletes.setText(userCoachModel.getUserProfile().getUpf_numOfAthlets().toString());

                if(userCoachModel.getUserProfile().getUpf_programsOffered() != null || userCoachModel.getUserProfile().getUpf_programsOffered().equalsIgnoreCase("null"))
                    prog_offered.setText(userCoachModel.getUserProfile().getUpf_programsOffered().toString());

                if(userCoachModel.getUserProfile().getUpf_division() != null || userCoachModel.getUserProfile().getUpf_division().equalsIgnoreCase("null"))
                    division.setText(userCoachModel.getUserProfile().getUpf_division().toString());

                if(userCoachModel.getUserProfile().getUpf_fundingStatus() != null || userCoachModel.getUserProfile().getUpf_fundingStatus().equalsIgnoreCase("null"))
                {
                    if (userCoachModel.getUserProfile().getUpf_fundingStatus().equals("Yes"))
                        program_funding.setSelection(0);
                    else
                        program_funding.setSelection(1);
                }

                if (userCoachModel.getUserProfile().getUpf_shareAthlets() != null || userCoachModel.getUserProfile().getUpf_shareAthlets().equalsIgnoreCase("null")){
                    if (userCoachModel.getUserProfile().getUpf_shareAthlets().equals("Yes"))
                        program_share_athletes.setSelection(0);
                    else
                        program_share_athletes.setSelection(1);
                }
            }


        }





    /*    years_running.setEnabled(false);
        no_athletes.setEnabled(false);
        prog_offered.setEnabled(false);
        division.setEnabled(false);
        program_funding.setEnabled(false);
        program_share_athletes.setEnabled(false);*/

    /*    if (userDataModel != null) {
            profileName.setText(userDataModel.getFirstName() + userDataModel.getLastName());
            editLname.setText(userDataModel.getLastName());
            editFname.setText(userDataModel.getFirstName());
            editGender.setText(userDataModel.getGender());
            editCity.setText(userDataModel.getCity());

            if (userDataModel.getImageUrl() != null) {
                Glide.with(CoachProfileFragment.this).load(userDataModel.getImageUrl()).into(imgProfile);
            } else {
                imgProfile.setImageResource(R.drawable.ic_person);
            }
            //Long value to date conversion
            SimpleDateFormat dft = new SimpleDateFormat("MMM dd, yyyy");
            long dob = Long.parseLong(userDataModel.getDob());
            Date date_dob = new Date(dob);
            editDob.setText(dft.format(date_dob));
            editPhone.setText(userDataModel.getPhoneNumber());
        }*/
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        private String fileName;
        private String fileType;
        private String f_url;


        public DownloadFileFromURL(String fileName, String fileType, String f_url) {
            this.fileName = fileName;
            this.fileType = fileType;
            this.f_url = f_url;
        }

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

           /* pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... url) {

            if (getActivity() != null) {
                File parentDirectory = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));

                if (!parentDirectory.exists()) {
                    File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
                    wallpaperDirectory.mkdirs();
                }
                if (fileType.equals("image")) {


                    File profileImageDir = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/" + "image");
                    //File imageDirectory=null;
                    if (!profileImageDir.exists()) {
                        new File(parentDirectory, "image").mkdir();

                        downloadProfileImageAndVideo(fileName, fileType, f_url);

                    } else {
                        String[] children = profileImageDir.list();
                        for (int i = 0; i < children.length; i++) {
                            new File(profileImageDir, children[i]).delete();
                        }

                        downloadProfileImageAndVideo(fileName, fileType, f_url);

                    }


                }
            }
            return null;

        }

        private void downloadProfileImageAndVideo(String fileName, String fileType, String... f_url) {
            int count;
            if(getActivity()!=null) {
                try {
                    String root = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/" + fileType + "/";

                    System.out.println("Downloading");
                    URL url = new URL(f_url[0]);

                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file

                    OutputStream output = new FileOutputStream(root + fileName);
                    byte data[] = new byte[1024];

                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;

                        // writing data to file
                        output.write(data, 0, count);

                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }
        }
        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");

            //pDialog.dismiss();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK || resultCode == -1) {

            if (requestCode == PICK_IMAGE_REQUEST) {

                if (data.hasExtra("data")) {
                    profilePhoto = (Bitmap) data.getExtras().get("data");
                    try {
                        profilePhoto = handleSamplingAndRotationBitmap(getContext(), getImageUri(getApplicationContext(), profilePhoto));
                    } catch (Exception e) {
                        Log.e(TAG, "COVERT_ERROR");
                    }finally {
                        selectedImageUri = getImageUri(getApplicationContext(), profilePhoto);
                    }

                } else {
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP


                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    // File finalFile = new File(getRealPathFromURI(tempUri));


                    //Uri picUri = data.getData();
                    selectedImageUri = data.getData();
                }
                if (selectedImageUri != null) {

                    File imgfile = new File(getRealPathFromURI(selectedImageUri));
                    //File imgfile = new File(String.valueOf(selectedImageUri));
                    // Get length of file in bytes

                    if (fileSize(imgfile.length()) <= 4) {
                        imageUri = getRealPathFromURI(selectedImageUri);
                        Glide.with(CoachProfileFragment.this).load(getRealPathFromURI(selectedImageUri)).into(imgProfile);
                        Glide.with(CoachProfileFragment.this).load(getRealPathFromURI(selectedImageUri)).into(imgBgCoach);

                        createDirectoryAndSaveFile(selectedImageUri, imgfile.getName(), "image");

                    } else {
                        Toast.makeText(getActivity(), "Image size is too large", Toast.LENGTH_SHORT).show();
                    }
                }
                if (imageUri != null ) {
                    uploadFiles(imageUri, user_id);
                    if(editStatus){
                        InfoSave();
                    }
                }


            }
            if (imageUri != null ) {
                uploadFiles(imageUri, user_id);
            }

        }
    }


    private void writeImageToDirectory(String fileName, Uri uri) {
        Bitmap mBitmap = null;
        File file = null;

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        file = new File(new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/" + "image"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }


    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }


    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     *                  method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private boolean checkExternalDrivePermission(final int type) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 21: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent chooseImageIntent = getPickImageIntent(getActivity().getApplicationContext(), "imageIntent");
                    chooseImageIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);

                    startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);

                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary otherwise image upload functionality fails ");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                }
                return;
            }
            case REQUEST_SAVEIMGTODRIVE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary otherwise video upload functionality fails ");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SAVEIMGTODRIVE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    //Method for check the size of the selected file
    private float fileSize(long fileLength) {
        long fileSizeInBytes = fileLength;
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1024;

        return fileSizeInMB;
    }

    //Api for upload profile image and video
    private void uploadFiles(final String imagePath, final String userId) {
        if (!progress.isShowing()) {
            progress.setTitle("Loading");
            progress.setMessage("Please wait while we save your data");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

            progress.show();
        }
        // START AsyncTask
       /* asyncTask = new AsyncPhotoUploadTask(imagePath, userId);
        asyncTask.setListener(new AsyncPhotoUploadTask.PhotoAsyncTaskListener() {
            @Override
            public void onPhotoAsyncTaskFinished(HttpEntity value) {
                if (value != null) {
                    imageUri = null;
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Successfully updated your details", Toast.LENGTH_LONG).show();
                    reloadFragment();

                }
            }
        });

        asyncTask.execute();*/

        //Upload image using retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        // Change base URL to your upload server URL.
        uploadService = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceClass.class);

        uploadVideoSever(imagePath,userId);

    }

    private void uploadVideoSever(String imgPath,String user_id ) {
        {

            File videoFile =null;
            File imageFile = null;
            String videoPath=null;


            if (imgPath != null) {
                imageFile = new File(imgPath);
                //Glide.with(ProfileFragment.this).load(imageFile.getAbsolutePath()).into(imgProfile);
            }
            if (videoPath != null) {
                videoFile = new File(videoPath);
                //playVideoFromFile(Uri.fromFile(videoFile.getAbsoluteFile()));
            }
            if (imageFile != null) {
                RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                fileImageToUpload = MultipartBody.Part.createFormData("profileImg", imageFile.getName(), mFile);
            }
            if (videoFile != null) {
                RequestBody mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), videoFile);
                filevideoToUploaded = MultipartBody.Part.createFormData("profileVideo", videoFile.getName(), mFile1);

            }
            RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), user_id);

            Call<UploadObject> fileUpload = uploadService.uploadMultiFile(fileImageToUpload,filevideoToUploaded,descBody);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                    reloadFragment();
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), "Successfully updated your details", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    // progressDialog.dismiss();

                    Log.d(TAG, "Error " + t.getMessage());
                }

            });


        }

    }



    private String trimMessage(String json, String detail) {
        String trimmedString = null;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(detail);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    @Override
    public void onClick(View view) {
        String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        switch (view.getId()) {

            case R.id.row_icon:

                if (!hasPermissions(getActivity(), PERMISSIONS)) {
                    requestPermissions(PERMISSIONS, 21);
                } else {
                    Intent chooseImageIntent = getPickImageIntent(getActivity().getApplicationContext(), "imageIntent");
                    chooseImageIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);

                    startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);
                }
                break;


            case R.id.llCoachMenuBasic:

                llMoreDetails.setVisibility(View.GONE);
                llBasicDetails.setVisibility(View.VISIBLE);
                viewBasic.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewMore.setBackgroundColor(getResources().getColor(R.color.white));
                basic_info_tab.setTextColor(getResources().getColor(R.color.blueDark));
                more_info_tab.setTextColor(getResources().getColor(R.color.darkGrey));

                break;

            case R.id.llCoachMenuMore:

                llBasicDetails.setVisibility(View.GONE);
                llMoreDetails.setVisibility(View.VISIBLE);
                viewMore.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewBasic.setBackgroundColor(getResources().getColor(R.color.white));
                more_info_tab.setTextColor(getResources().getColor(R.color.blueDark));
                basic_info_tab.setTextColor(getResources().getColor(R.color.darkGrey));

                break;

            case R.id.btnsave:
                InfoSave();
//                editStatus = !editStatus;
                break;
            case R.id.btncancel:
                InfoCancelChange();
//                editStatus = !editStatus;
                break;
            case R.id.btn_save:
                InfoSave();
//                editStatus = !editStatus;
                break;
            case R.id.btn_cancel:
                InfoCancelChange();
//                editStatus = !editStatus;
                break;
            case R.id.edit:
                if (!editStatus) {
                    editCustomView();
                    editBasicInfo();
                    editMoreInfo();

                    imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
                    edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
                    edit_tag.setText("Save Profile");
                    editStatus = !editStatus;
                } else {
                    // InfoCancelChange();
                    InfoSave();

                    imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                    edit_tag.setText("Edit Profile");
                    break;
                }
//            case R.id.edit_profile_imgCoach:
//                if (editStatus) {
//                    if (!hasPermissions(getActivity(), PERMISSIONS)) {
//                        requestPermissions(PERMISSIONS, 21);
//                    } else {
//                        Intent chooseImageIntent = getPickImageIntent(getActivity().getApplicationContext(), "imageIntent");
//                        chooseImageIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
//
//                        startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);
//                    }
//                }


            default:
                break;
        }
    }


    public Intent getPickImageIntent(Context context,String type) {
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();




        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");


        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePictureIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), "");
            // context.getString(R.string.pick_image_intent_text));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }
        return true;
    }
    // method for add intent to arraylist
    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
            //Log.d(TAG, "Intent: " + intent.getAction() + " package: " + packageName);
        }
        return list;
    }


    //method to write profile image and video into a local file
    private void createDirectoryAndSaveFile(Uri uri, String fileName, String fileType) {


        File direct = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));


        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
            wallpaperDirectory.mkdirs();
        }
        if (fileType.equals("image")) {
            File profileImageDir = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/" + "image");
            //File imageDirectory=null;
            if (!profileImageDir.exists()) {
                new File(direct, "image").mkdir();

                writeImageToDirectory(fileName, uri);
            } else {
                String[] children = profileImageDir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(profileImageDir, children[i]).delete();
                }

                writeImageToDirectory(fileName, uri);

            }

        }
    }

}