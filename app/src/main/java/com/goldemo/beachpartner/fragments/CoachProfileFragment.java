package com.goldemo.beachpartner.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.UserDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int REQUEST_TAKE_GALLERY_IMAGE = 2;
    public UserDataModel userDataModel;
    private static boolean editStatus = false;
    private RelativeLayout profileImgLayout;
    private CircularImageView imgProfile;
    private LinearLayout llMenuBasic,llMenuMore,llBasicDetails,llMoreDetails, coachBtnsBottom,coachMore_infoBtnsBottom;
    private TextView basic_info_tab,more_info_tab,edit_tag,profileName;
    private View viewBasic,viewMore;
    private EditText editFname,editLname,editGender,editDob,editCity,editPhone,description,years_running,no_athletes,prog_offered,division;
    private Spinner spinnerCollege,program_funding,program_share_athletes;
    private Uri selectedImageUri;
    private Button basicBtnSave,basicBtnCancel,moreBtnSave,moreBtnCancel;
    private ImageView imgEdit,profile_img_editIcon;
    private String token,user_id,spinnerCollege_value,program_funding_value,program_share_athletes_value;

    Calendar myCalendar = Calendar.getInstance();

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
        View view=inflater.inflate(R.layout.fragment_coach_profile, container, false);
        initView(view);
        setUp();


        return view;


    }

    private void initView(final View view){
        profileImgLayout        = (RelativeLayout) view.findViewById(R.id.profile_img_layout);
        llMenuBasic             = (LinearLayout) view.findViewById(R.id.llCoachMenuBasic);
        llMenuMore              = (LinearLayout) view.findViewById(R.id.llCoachMenuMore);

        basic_info_tab          = (TextView) view.findViewById(R.id.coach_basic_info_tab);
        more_info_tab           = (TextView) view.findViewById(R.id.coach_more_info_tab);

        llBasicDetails          = (LinearLayout) view.findViewById(R.id.llCoachBasicDetails);
        llMoreDetails           = (LinearLayout) view.findViewById(R.id.llCoachMoreInfoDetails);

        viewBasic               = (View) view.findViewById(R.id.viewCoachBasic);
        viewMore                = (View) view.findViewById(R.id.viewCoachMore);
        imgProfile              = (CircularImageView) view.findViewById(R.id.row_icon);
        profileName             = (TextView) view.findViewById(R.id.coachName);

        //For Basic Details

        editFname               = (EditText) view.findViewById(R.id.txtvFname);
        editLname               = (EditText) view.findViewById(R.id.txtvLname);
        editGender              = (EditText) view.findViewById(R.id.txtv_gender);
        editDob                 = (EditText) view.findViewById(R.id.txtv_dob);
        editCity                = (EditText) view.findViewById(R.id.txtv_city);
        editPhone               = (EditText) view.findViewById(R.id.txtv_mobileno);
//        editPassword    =   (EditText)view.findViewById(R.id.txtv_password);

        basicBtnSave            = (Button) view.findViewById(R.id.btnsave);
        basicBtnCancel          = (Button) view.findViewById(R.id.btncancel);

        //Fore More Deatails

        spinnerCollege          = (Spinner) view.findViewById(R.id.spinner_college);
        spinnerCollege.setEnabled(false);
        description             = (EditText) view.findViewById(R.id.txtv_description);
        years_running           = (EditText) view.findViewById(R.id.txtv_years_running);
        no_athletes             = (EditText) view.findViewById(R.id.txtv_no_athletes);
        prog_offered            = (EditText) view.findViewById(R.id.txtv_prog_offered);
        division                = (EditText) view.findViewById(R.id.txtv_division);

        program_funding         = (Spinner) view.findViewById(R.id.spinner_program_funding);

        program_share_athletes   = (Spinner) view.findViewById(R.id.spinner_program_share_athletes);


        imgEdit                    =   (ImageView) view.findViewById(R.id.edit);
        edit_tag                   =   (TextView) view.findViewById(R.id.edit_text);
        profile_img_editIcon       =   (ImageView) view.findViewById(R.id.edit_profile_imgCoach);


        coachBtnsBottom         = (LinearLayout) view.findViewById(R.id.coach_btns_at_bottom);

        coachMore_infoBtnsBottom = (LinearLayout) view.findViewById(R.id.coach_more_info_btns_bottom);

        moreBtnSave              = (Button) view.findViewById(R.id.btn_save);
        moreBtnCancel            = (Button) view.findViewById(R.id.btn_cancel);

        llMenuBasic.setOnClickListener(this);
        llMenuMore.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        basicBtnCancel.setOnClickListener(this);
        moreBtnSave.setOnClickListener(this);
        moreBtnCancel.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        basicBtnSave.setOnClickListener(this);
        profile_img_editIcon.setOnClickListener(this);


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
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dialog.show();

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





//      College of coach
        List<String> college = new ArrayList<>();
        college.add("Left side");
        college.add("Right Side");
        college.add("No Preference");
        ArrayAdapter<String> prefAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, college);
        prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCollege.setAdapter(prefAdapter);
        spinnerCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
               spinnerCollege_value = spinnerCollege.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

//        funding spinner

        List<String> funding = new ArrayList<>();
        funding.add("Yes");
        funding.add("No");
        ArrayAdapter<String> fundingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, funding);
        fundingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program_funding.setAdapter(fundingAdapter);
        program_funding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                program_funding_value   =  program_funding.getSelectedItem().toString();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_TAKE_GALLERY_IMAGE) {
                selectedImageUri = data.getData();
                //String selectedImagePath = getPath(selectedImageUriImg);
                if (selectedImageUri != null) {
                    imgProfile.setImageURI(selectedImageUri);

                }

            }

//            JSONObject jsonObject = new JSONObject();
//            try {
//                Toast.makeText(getContext(), "Uploading...", Toast.LENGTH_SHORT).show();
//                jsonObject.put("profileImg",selectedImageUri);
//                jsonObject.put("userId",user_id);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            //Method for uploading profilePic & profile video
//            uploadFiles(jsonObject);
        }
    }

    public void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDob.setText(sdf.format(myCalendar.getTime()));
    }



    private void editBasicInfo() {

        //Profile image edit icon active

        imgProfile.setClickable(true);
        coachBtnsBottom.setVisibility(View.VISIBLE);
        coachMore_infoBtnsBottom.setVisibility(View.VISIBLE);
        profile_img_editIcon.setVisibility(View.VISIBLE);

        editFname.setEnabled(true);
        editFname.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editLname.setEnabled(true);
        editLname.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editGender.setEnabled(true);
        editGender.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editDob.setEnabled(true);
        editDob.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCity.setEnabled(true);
        editCity.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPhone.setEnabled(true);
        editPhone.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

//        editPassword.setEnabled(true);
//        editPassword.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

    }
    private void editMoreInfo(){
        spinnerCollege.setEnabled(true);
        spinnerCollege.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

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

        profile_img_editIcon.setVisibility(View.VISIBLE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
        edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
    }

    private void InfoSave(){
        imgProfile.setClickable(false);
        profile_img_editIcon.setVisibility(View.GONE);
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

        editCity.setEnabled(false);
        editCity.setBackground(null);

        editPhone.setEnabled(false);
        editPhone.setBackground(null);

        //MoreInfo

        spinnerCollege.setEnabled(false);
        spinnerCollege.setBackground(null);

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




        JSONObject jsonObjectMore = new JSONObject();
        try {
            jsonObjectMore.put("cbvaFirstName", null);
            jsonObjectMore.put("cbvaLastName", null);
            jsonObjectMore.put("cbvaPlayerNumber",null);
            jsonObjectMore.put("collage",spinnerCollege_value.toString().trim());
            jsonObjectMore.put("collageClub",null);
            jsonObjectMore.put("collegeBeach", null);
            jsonObjectMore.put("collegeIndoor", null);
            jsonObjectMore.put("courtSidePreference", null);
            jsonObjectMore.put("description",description.getText().toString().trim());
            jsonObjectMore.put("division",division.getText().toString().trim());
            jsonObjectMore.put("experience", null);
            jsonObjectMore.put("fundingStatus",null);
            jsonObjectMore.put("height", null);
            jsonObjectMore.put("highSchoolAttended", null);
            jsonObjectMore.put("highestTourRatingEarned", null);
            jsonObjectMore.put("indoorClubPlayed", null);
            jsonObjectMore.put("numOfAthlets",no_athletes.getText().toString().trim());
            jsonObjectMore.put("position", null);
            jsonObjectMore.put("programsOffered",prog_offered.getText().toString().trim());
            jsonObjectMore.put("shareAthlets",program_share_athletes_value.toString().trim());
            jsonObjectMore.put("topFinishes", null);
            jsonObjectMore.put("totalPoints", null);
            jsonObjectMore.put("tournamentLevelInterest",null);
            jsonObjectMore.put("toursPlayedIn", null);
            jsonObjectMore.put("usaVolleyballRanking", null);
            jsonObjectMore.put("userId", user_id);
            jsonObjectMore.put("willingToTravel", null);
            jsonObjectMore.put("yearsRunning",years_running.getText().toString().trim());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        postUserMoreDetails(jsonObjectMore);


    }

    private void postUserMoreDetails(JSONObject jsonObjectMore) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.POST_USER_MORE_INFO, jsonObjectMore,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Toast.makeText(getActivity(), "User Details Posted Successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

    }

    private void InfoCancelChange(){
        imgProfile.setClickable(false);
        profile_img_editIcon.setVisibility(View.GONE);
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

        editCity.setEnabled(false);
        editCity.setBackground(null);

        editPhone.setEnabled(false);
        editPhone.setBackground(null);

        //MoreInfo

        spinnerCollege.setEnabled(false);
        spinnerCollege.setBackground(null);

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

    }
    private void setUp() {
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
            public Map<String, String> getHeaders()  {
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
        if (userDataModel != null) {
            profileName.setText(userDataModel.getFirstName()+userDataModel.getLastName());
            editLname.setText(userDataModel.getLastName());
            editFname.setText(userDataModel.getFirstName());
            editGender.setText(userDataModel.getGender());
            editCity.setText(userDataModel.getCity());
            //Long value to date conversion
            SimpleDateFormat dft = new SimpleDateFormat("MMM dd, yyyy");
            long dob       = Long.parseLong(userDataModel.getDob());
            Date date_dob  = new Date(dob);
            editDob.setText(dft.format(date_dob));
            editPhone.setText(userDataModel.getPhoneNumber());
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
        switch (view.getId()) {

            case R.id.row_icon:
                if (editStatus) {
                    Intent intent1 = new Intent();
                    intent1.setType("image/*");
                    intent1.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent1, "Select Image"), REQUEST_TAKE_GALLERY_IMAGE);
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
                editStatus = !editStatus;
                break;
            case R.id.btncancel:
                InfoCancelChange();
                editStatus = !editStatus;
                break;
            case R.id.btn_save:
                InfoSave();
                editStatus = !editStatus;
                break;
            case R.id.btn_cancel:
                InfoCancelChange();
                editStatus = !editStatus;
                break;
            case R.id.edit:
                editStatus = !editStatus;


                if (editStatus) {
                    editCustomView();
                    editBasicInfo();
                    editMoreInfo();


                    profile_img_editIcon.setVisibility(View.VISIBLE);
                    imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
                    edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
                } else {

                    InfoCancelChange();

                }
                break;
            case R.id.edit_profile_imgCoach:
                if (editStatus) {
                    Intent intent1 = new Intent();
                    intent1.setType("image/*");
                    intent1.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent1, "Select Image"), REQUEST_TAKE_GALLERY_IMAGE);
                }



            default:
                break;
        }
    }

}
