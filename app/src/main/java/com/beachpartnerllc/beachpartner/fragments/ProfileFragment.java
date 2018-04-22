package com.beachpartnerllc.beachpartner.fragments;

import android.Manifest;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.UserDataModel;
import com.beachpartnerllc.beachpartner.utils.FloatingActionButton;
import com.beachpartnerllc.beachpartner.utils.FloatingActionMenu;
import com.beachpartnerllc.beachpartner.utils.SimpleSSLSocketFactory;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;


public class ProfileFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_SAVEIMGTODRIVE = 3;
    private static final int REQUEST_TAKE_GALLERY_IMAGE = 2;
    private static final int PICK_IMAGE_REQUEST =0 ;
    private static final int PICK_VIDEO_REQUEST =1;
    private static final String TAG = "ProfileFragment" ;
    public static boolean isValidate = false;
    private static boolean moreUploadStatus = false;
    private static boolean editStatus = false;
    public UserDataModel userDataModel;
    public String token, user_id, spinnerTLValue, spinnerWTValue, spinnerTRValue, spinnerExpValue, spinnerPrefValue, spinnerPosValue,imageUri,videoUri;
    Calendar myCalendar = Calendar.getInstance();
    private TabLayout tabs;
    private ProgressBar progressbar;
    private ViewPager viewPager;
    private FrameLayout videoFrame;
    private ImageView imgEdit, imgVideo, imgPlay, profile_img_editIcon, imageView1, imageView2, imageView3;
    private FloatingActionMenu imgShare;
    private FloatingActionButton fabImage, fabVideo;
    private CircularImageView imgProfile;
    private TextView profileName, profileDesig, edit_tag, basic_info_tab, more_info_tab;
    private VideoView videoView;
    private Uri selectedImageUri, selectedVideoUri, screenshotUri,screenshotVideoUri;
    private byte[] multipartBody;
    private LinearLayout llMenuBasic, llMenuMore, llBasicDetails, llMoreDetails;//This menu bar only for demo purpose
    private View viewBasic, viewMore;
    private EditText editFname, editLname, editGender, editDob, editPhone;
    private AutoCompleteTextView editCity;
    private EditText editHeight, editPlayed, editCBVANo, editCBVAFName, editCBVALName, editHighschool, editIndoorClub, editColgClub, editColgBeach, editColgIndoor, editPoints, topfinishes_txt_2, topfinishes_txt_1, topfinishes_txt_3, edit_volleyRanking;
    private Button moreBtnSave, moreBtnCancel, basicBtnSave, basicBtnCancel;
    private LinearLayout btnsBottom, more_info_btns_bottom;
    private LinearLayout topFinishes1_lt, topFinishes2_lt, topFinishes3_lt;
    private RelativeLayout containingLt;
    private int finishCount = 0;
    private Spinner spinnerExp, spinnerPref, spinnerPositon, spinnerTLInterest, spinnerTourRating, spinnerWtoTravel;
    private AwesomeValidation awesomeValidation;
    private boolean saveFile;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private ArrayAdapter<String> expAdapter,prefAdapter,positionAdapter,highestRatingAdapter,tournamentInterestAdapter,distanceAdapter;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Context mContext;
    private int videoDuration;

    private Handler mUiHandler = new Handler();

    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        token   = new PrefManager(getContext()).getToken();
        user_id = new PrefManager(getContext()).getUserId();
        setUp();
        initActivity(view);

        return view;
    }


    private void initActivity(final View view) {

        btnsBottom              = (LinearLayout) view.findViewById(R.id.btns_at_bottom);
        more_info_btns_bottom   = (LinearLayout) view.findViewById(R.id.more_info_btns_bottom);
        imgEdit                 = (ImageView) view.findViewById(R.id.edit);
        profile_img_editIcon    = (ImageView) view.findViewById(R.id.edit_profile_img_vid);

        imgProfile  = (CircularImageView) view.findViewById(R.id.row_icon);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileDesig= (TextView) view.findViewById(R.id.profile_designation);
        edit_tag    = (TextView) view.findViewById(R.id.edit_text);
        progressbar = (ProgressBar)view.findViewById(R.id.progressBar);

        imgVideo    = (ImageView) view.findViewById(R.id.imgVideo);
        videoView   = (VideoView) view.findViewById(R.id.videoView);
        imgPlay     = (ImageView) view.findViewById(R.id.imgPlay);
        imgShare    = (FloatingActionMenu) view.findViewById(R.id.menu_blue);
        fabImage    = (FloatingActionButton) view.findViewById(R.id.fab_image);
        fabVideo    = (FloatingActionButton) view.findViewById(R.id.fab_video);

        llMenuBasic = (LinearLayout) view.findViewById(R.id.llMenuBasic);
        llMenuMore  = (LinearLayout) view.findViewById(R.id.llMenuMore);

        basic_info_tab  = (TextView) view.findViewById(R.id.basic_info_tab);
        more_info_tab   = (TextView) view.findViewById(R.id.more_info_tab);
        llBasicDetails  = (LinearLayout) view.findViewById(R.id.llBasicDetails);
        llMoreDetails   = (LinearLayout) view.findViewById(R.id.llMoreInfoDetails);
        viewBasic       = (View) view.findViewById(R.id.viewBasic);
        viewMore        = (View) view.findViewById(R.id.viewMore);

        //For Basic Details

        editFname   = (EditText) view.findViewById(R.id.txtvFname);
        editLname   = (EditText) view.findViewById(R.id.txtvLname);
        editGender  = (EditText) view.findViewById(R.id.txtv_gender);
        editDob     = (EditText) view.findViewById(R.id.txtv_dob);
        editCity    = (AutoCompleteTextView) view.findViewById(R.id.txtv_city);
        editPhone   = (EditText) view.findViewById(R.id.txtv_mobileno);
//        editPassword    =   (EditText)view.findViewById(R.id.txtv_password);

        basicBtnSave    = (Button) view.findViewById(R.id.btnsave);
        basicBtnCancel  = (Button) view.findViewById(R.id.btncancel);



        //Fore More Deatsils

        spinnerExp        = (Spinner) view.findViewById(R.id.spinner_exp);
        spinnerPref       = (Spinner) view.findViewById(R.id.spinner_pref);
        spinnerPositon    = (Spinner) view.findViewById(R.id.spinner_positon);
        spinnerTLInterest = (Spinner) view.findViewById(R.id.spinner_tl_interest);
        spinnerTourRating = (Spinner) view.findViewById(R.id.spinner_tour_rating);
        spinnerWtoTravel  = (Spinner) view.findViewById(R.id.spinner_Wto_travel);


        spinnerExp.setEnabled(false);
        spinnerPref.setEnabled(false);
        spinnerPositon.setEnabled(false);
        spinnerTLInterest.setEnabled(false);
        spinnerTourRating.setEnabled(false);
        spinnerWtoTravel.setEnabled(false);


        editHeight      = (EditText) view.findViewById(R.id.txtvHeight);
        editPlayed      = (EditText) view.findViewById(R.id.txtvPlayed);
        editCBVANo      = (EditText) view.findViewById(R.id.txtvCBVANo);
        editCBVAFName   = (EditText) view.findViewById(R.id.txtvCBVAFName);
        editCBVALName   = (EditText) view.findViewById(R.id.txtvCBVALName);
        editHighschool  = (EditText) view.findViewById(R.id.txtvHighschool);
        editIndoorClub  = (EditText) view.findViewById(R.id.txtvIndoorClub);
        editColgClub    = (EditText) view.findViewById(R.id.txtvColgClub);
        editColgBeach   = (EditText) view.findViewById(R.id.txtvColgBeach);
        editColgIndoor  = (EditText) view.findViewById(R.id.txtvColgIndoor);
        edit_volleyRanking = (EditText) view.findViewById(R.id.txtvRank);
        editPoints      = (EditText) view.findViewById(R.id.txtvPoints);
        topfinishes_txt_1 = (EditText) view.findViewById(R.id.topfinishes_txt_1);
        topfinishes_txt_2 = (EditText) view.findViewById(R.id.topfinishes_txt_2);
        topfinishes_txt_3 = (EditText) view.findViewById(R.id.topfinishes_txt_3);

        moreBtnSave = (Button) view.findViewById(R.id.btn_save);
        moreBtnCancel = (Button) view.findViewById(R.id.btn_cancel);


        llMenuBasic.setOnClickListener(this);
        llMenuMore.setOnClickListener(this);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);

        containingLt = (RelativeLayout) view.findViewById(R.id.scroll_more);
        topFinishes1_lt = (LinearLayout) view.findViewById(R.id.topFinishes1_lt);
        topFinishes2_lt = (LinearLayout) view.findViewById(R.id.topFinishes2_lt);
        topFinishes3_lt = (LinearLayout) view.findViewById(R.id.topFinishes3_lt);


        //autocomplete textview fp
        addLocation();

        dataAdapter     = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, stateList);

        Typeface font   = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");
        editCity.setTypeface(font);
        editCity.setAdapter(dataAdapter);

        imgShare.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgShare.isOpened()) {
                    //Toast.makeText(getActivity(), imgShare.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                imgShare.toggle(true);
            }
        });


        //share image
        fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Image", Toast.LENGTH_SHORT).show();
                if (selectedImageUri != null || userDataModel.getImageUrl()!=null ) {
                    if (selectedImageUri != null) {
                        screenshotUri = Uri.parse(String.valueOf(selectedImageUri));
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(screenshotUri)
                                .build();
                        ShareDialog.show(ProfileFragment.this,content);
                        /*Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        intent.setType("image*//*");
                        startActivity(Intent.createChooser(intent, "Share image via..."));*/
                    } else if (userDataModel.getImageUrl() != null) {
                        screenshotUri = Uri.parse(userDataModel.getImageUrl());
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(screenshotUri)
                                .build();
                        ShareDialog.show(ProfileFragment.this,content);
                    }

                } else {
                    Toast.makeText(getActivity(), "Please upload Image and share it", Toast.LENGTH_SHORT).show();
                    imgShare.close(true);
                }
            }
        });

        //Share video
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Video", Toast.LENGTH_SHORT).show();
                if (selectedVideoUri != null  || userDataModel.getVideoUrl()!=null ) {
                    if (selectedVideoUri != null) {
                        screenshotVideoUri= Uri.parse(String.valueOf(selectedVideoUri));
                        /*Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this Video");
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        intent.setType("video*//*");
                        startActivity(Intent.createChooser(intent, "Share video via..."));*/
                        ShareLinkContent contentvideo = new ShareLinkContent.Builder()
                                .setContentUrl(screenshotVideoUri)
                                .build();
                        ShareDialog.show(ProfileFragment.this,contentvideo);
                    } else if (userDataModel.getVideoUrl() != null) {
                        screenshotVideoUri = Uri.parse(userDataModel.getVideoUrl());
                        ShareLinkContent contentvideo = new ShareLinkContent.Builder()
                                .setContentUrl(screenshotVideoUri)
                                .build();
                        ShareDialog.show(ProfileFragment.this,contentvideo);
                    }


                } else {
                    Toast.makeText(getActivity(), "Please upload Video and share it", Toast.LENGTH_SHORT).show();
                    imgShare.close(true);

                }

            }
        });


         /*This for demo only end*/


        // setupViewPager(viewPager);
        // tabs.setupWithViewPager(viewPager);

//        imgEdit.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPlay.setOnClickListener(this);


        /*Experience Spinner*/
        List<String> experience = new ArrayList<>();
        experience.add("Please Select");
        experience.add("“Newbie” [New to the Game]");
        experience.add("1-2 years [Some Indoor/Beach Experience]");
        experience.add("2-3 years [Beach Club/Tournament Experience]");
        experience.add("3-4 years [Experienced Tournament Player]");
        experience.add("More than 4 years");

        expAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, experience);
        expAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExp.setAdapter(expAdapter);

        spinnerExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerExpValue = spinnerExp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /*Court Preference spinner*/
        spinnerPref.setOnItemSelectedListener(this);
        List<String> courtPref = new ArrayList<>();
        courtPref.add("Please Select");
        courtPref.add("Left side");
        courtPref.add("Right Side");
        courtPref.add("No Preference");
        prefAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, courtPref);
        prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPref.setAdapter(prefAdapter);
        spinnerPref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerPrefValue = spinnerPref.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /*position Spinner*/
        spinnerPositon.setOnItemSelectedListener(this);
        List<String> position = new ArrayList<>();
        position.add("Please Select");
        position.add("Primary Blocker");
        position.add("Primary Defender");
        position.add("Split Block/Defense");
        positionAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, position);
        prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPositon.setAdapter(positionAdapter);
        spinnerPositon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerPosValue = spinnerPositon.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /*Tournament Level interest spinner*/
        spinnerTLInterest.setOnItemSelectedListener(this);
        List<String> tournamentInterest = new ArrayList<>();
        tournamentInterest.add("Please Select");
        tournamentInterest.add("Novice/Social");
        tournamentInterest.add("Unrated");
        tournamentInterest.add("B");
        tournamentInterest.add("A");
        tournamentInterest.add("AA");
        tournamentInterest.add("AAA");
        tournamentInterest.add("Pro");

        tournamentInterestAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, tournamentInterest);
        prefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTLInterest.setAdapter(tournamentInterestAdapter);

        spinnerTLInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerTLValue = spinnerTLInterest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /*Tourrating Spinner*/
        spinnerTourRating.setOnItemSelectedListener(this);
        List<String> rating = new ArrayList<>();
        rating.add("Please Select");
        rating.add("PRO");
        rating.add("Open Or AAA");
        rating.add("AA");
        rating.add("A");
        rating.add("BB");
        rating.add("B");
        rating.add("C Or Novice");
        rating.add("Unrated");
        highestRatingAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, rating);
        highestRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTourRating.setAdapter(highestRatingAdapter);


        spinnerTourRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerTRValue = spinnerTourRating.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        /*Spinner for willing to travel*/
        spinnerWtoTravel.setOnItemSelectedListener(this);
        List<String> distance = new ArrayList<>();
        distance.add("Please Select");
        distance.add("Not Willing");
        distance.add("Up to 25 miles");
        distance.add("Up to 50 miles");
        distance.add("Up to 100 miles");
        distance.add("Up to 250 miles");
        distance.add("Up to 500 miles");
        distance.add("Nationwide");
        distance.add("International");
        distanceAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, distance);
        highestRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWtoTravel.setAdapter(distanceAdapter);

        spinnerWtoTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                moreUploadStatus = true;
                spinnerWTValue = spinnerWtoTravel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


//        Buttons click action for saving
        basicBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoSave();
                editStatus = !editStatus;
            }
        });
        moreBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoSave();
                editStatus = !editStatus;
            }
        });

        basicBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoCancelChange();
                editStatus = !editStatus;
            }
        });
        moreBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoCancelChange();
                editStatus = !editStatus;
            }
        });

        //edit profile button(ImageView)
        imgEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editStatus = !editStatus;


                if (editStatus) {
                    editCustomView();
                    editBasicInfo();
                    editMoreInfo();
                    //imgVideo.setVisibility(View.VISIBLE);
                    //profile_img_editIcon.setVisibility(View.VISIBLE);
                    imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
                    edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
                } else {

                    InfoCancelChange();

                }
            }
        });


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

        editPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toursPlayed();
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
        });*/

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
       /* imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });
        */

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                finishCount += 1;
                if (finishCount == 1) {
                    imageView2.setVisibility(View.VISIBLE);
                    topFinishes2_lt.setVisibility(View.VISIBLE);

                } else if (finishCount == 2) {
                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);

                    topFinishes2_lt.setVisibility(View.VISIBLE);
                    topFinishes3_lt.setVisibility(View.VISIBLE);

                } else {
                    topFinishes2_lt.setVisibility(View.GONE);
                    topFinishes3_lt.setVisibility(View.GONE);
                }

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCount -= 1;
                topFinishes2_lt.setVisibility(View.GONE);
                if (finishCount < 1) {
                    imageView1.setVisibility(View.VISIBLE);
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCount -= 1;
                topFinishes3_lt.setVisibility(View.GONE);
                if (finishCount < 2) {
                    imageView1.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        // adapter.addFragment(new BasicInfoFragment(),"Basic Information");
        //adapter.addFragment(new MoreInfoFragment(),"More Information");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        menus.add(imgShare);



       /* menuYellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text;
                if (opened) {
                    text = "Menu opened";
                } else {
                    text = "Menu closed";
                }
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });*/

        /*fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);*/

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabEdit.show(true);
            }
        }, delay + 150);

        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                    Toast.makeText(getActivity(), menuRed.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menuRed.toggle(true);
            }
        });*/

        createCustomAnimation();
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        /*ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuGreen.getMenuIconView().setImageResource(menuGreen.isOpened()
                        ? R.drawable.ic_close : R.drawable.ic_star);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menuGreen.setIconToggleAnimatorSet(set);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgVideo:
                //boolean videoPermission = checkExternalDrivePermission(PICK_VIDEO_REQUEST);
                // if (videoPermission){
                //videoBrowse();

                Intent videoPicker= getPickImageIntent(getActivity().getApplicationContext(),"videoIntent");
                if(videoPicker!=null){
                    Intent imagePicker= getPickImageIntent(getActivity().getApplicationContext(),"imageIntent");
                    if(imagePicker!=null){

                        // getPickImageIntent(getActivity().getApplicationContext(),"imageIntent");

                        Intent chooseImageIntent =  getPickImageIntent(getActivity().getApplicationContext(),"videoIntent");
                        startActivityForResult(chooseImageIntent, PICK_VIDEO_REQUEST);

                    }
                }
                break;
            case R.id.row_icon:
                if (editStatus) {
                    //  boolean imagePermission = checkExternalDrivePermission(PICK_IMAGE_REQUEST);
                    // if (imagePermission) {
                    // imageBrowse();

                    Intent imagePicker= getPickImageIntent(getActivity().getApplicationContext(),"imageIntent");
                    if(imagePicker!=null){

                        // getPickImageIntent(getActivity().getApplicationContext(),"imageIntent");

                        Intent chooseImageIntent =  getPickImageIntent(getActivity().getApplicationContext(),"imageIntent");
                        startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);

                    }

                    // }
                }
                break;
            case R.id.imgPlay:
                videoView.setVisibility(View.VISIBLE);
                playVideo();
                // videoView.start();
                break;

            //Demo Only

            case R.id.llMenuBasic:

                llMoreDetails.setVisibility(View.GONE);
                llBasicDetails.setVisibility(View.VISIBLE);
                viewBasic.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewMore.setBackgroundColor(getResources().getColor(R.color.white));
                basic_info_tab.setTextColor(getResources().getColor(R.color.blueDark));
                more_info_tab.setTextColor(getResources().getColor(R.color.darkGrey));


                break;

            case R.id.llMenuMore:

                llBasicDetails.setVisibility(View.GONE);
                llMoreDetails.setVisibility(View.VISIBLE);
                viewMore.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewBasic.setBackgroundColor(getResources().getColor(R.color.white));
                more_info_tab.setTextColor(getResources().getColor(R.color.blueDark));
                basic_info_tab.setTextColor(getResources().getColor(R.color.darkGrey));

                break;

            /*case R.id.menu_blue:

                break;*/



           /* case R.id.btnsave:
                InfoSave();
                break;

            case R.id.btn_save:
                InfoSave();
                break;*/


            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo() {
        videoView.start();
        progressbar.setVisibility(View.VISIBLE);
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                    progressbar.setVisibility(View.GONE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                    progressbar.setVisibility(View.VISIBLE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                    videoView.setVisibility(View.GONE);
                    progressbar.setVisibility(View.GONE);
                    imgPlay.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.setVisibility(View.GONE);
                imgPlay.setVisibility(View.VISIBLE);

            }
        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void videoBrowse() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent, PICK_VIDEO_REQUEST);
    }


    private boolean checkExternalDrivePermission(final int type){

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)) || (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
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
            case PICK_IMAGE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                   // imageBrowse();

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
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PICK_VIDEO_REQUEST:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                  //  videoBrowse();

                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary otherwise video upload functionality fails ");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_VIDEO_REQUEST);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_SAVEIMGTODRIVE:{
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
            default:{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    ////











    private void editCustomView() {

        imgVideo.setVisibility(View.VISIBLE);
        profile_img_editIcon.setVisibility(View.VISIBLE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
        edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
    }


    public void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDob.setText(sdf.format(myCalendar.getTime()));
    }


    public void toursPlayed() {
        final CharSequence[] items = {" AVP ", " AVP First ", " AAU ", "USAV Juniors", "USAV Adults", "VolleyAmerica National Rankings"};
// arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();

        final AlertDialog dialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Select-Tours Played in")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String selectedTours = null;
                        for (int i = 0; i < seletedItems.size(); i++) {
                            selectedTours = (String) (items[(int) seletedItems.get(i)]);
                        }
                        editPlayed.setText(selectedTours);

                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blueDark));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blueDark));
            }
        });

        dialog.show();

    }


    private void editBasicInfo() {

        //Profile image edit icon active

        imgProfile.setClickable(true);
        imgVideo.setClickable(true);
        btnsBottom.setVisibility(View.VISIBLE);
        more_info_btns_bottom.setVisibility(View.VISIBLE);
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

    private void editMoreInfo() {


        spinnerExp.setEnabled(true);
        spinnerExp.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));


        spinnerPref.setEnabled(true);
        spinnerPref.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        spinnerPositon.setEnabled(true);
        spinnerPositon.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editHeight.setEnabled(true);
        editHeight.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));


        spinnerTLInterest.setEnabled(true);
        spinnerTLInterest.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPlayed.setEnabled(true);
        editPlayed.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        spinnerTourRating.setEnabled(true);
        spinnerTourRating.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVANo.setEnabled(true);
        editCBVANo.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVAFName.setEnabled(true);
        editCBVAFName.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVALName.setEnabled(true);
        editCBVALName.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        spinnerWtoTravel.setEnabled(true);
        spinnerWtoTravel.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editHighschool.setEnabled(true);
        editHighschool.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editIndoorClub.setEnabled(true);
        editIndoorClub.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editColgClub.setEnabled(true);
        editColgClub.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editColgBeach.setEnabled(true);
        editColgBeach.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editColgIndoor.setEnabled(true);
        editColgIndoor.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPoints.setEnabled(true);
        editPoints.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        edit_volleyRanking.setEnabled(true);
        edit_volleyRanking.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));


        topfinishes_txt_1.setEnabled(true);
        topfinishes_txt_1.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));
        imageView1.setVisibility(View.VISIBLE);

        topfinishes_txt_2.setEnabled(true);
        topfinishes_txt_2.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));
        imageView2.setVisibility(View.VISIBLE);

        topfinishes_txt_3.setEnabled(true);
        topfinishes_txt_3.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));
        imageView3.setVisibility(View.VISIBLE);


    }

    //Saving information after edit
    public void InfoSave() {

    /*validating feilds*/

        if (!validate()) {

            imgProfile.setClickable(false);
            profile_img_editIcon.setVisibility(View.GONE);
            imgVideo.setVisibility(View.GONE);
            btnsBottom.setVisibility(View.GONE);
            imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            edit_tag.setTextColor(getResources().getColor(R.color.imgBacgnd));
            more_info_btns_bottom.setVisibility(View.GONE);
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

//        editPassword.setEnabled(false);
//        editPassword.setBackground(null);


            //MoreInfo
            spinnerExp.setEnabled(false);
            spinnerExp.setBackground(null);

            spinnerPref.setEnabled(false);
            spinnerPref.setBackground(null);

            spinnerPositon.setEnabled(false);
            spinnerPositon.setBackground(null);

            editHeight.setEnabled(false);
            editHeight.setBackground(null);


            spinnerTLInterest.setEnabled(false);
            spinnerTLInterest.setBackground(null);

            editPlayed.setEnabled(false);
            editPlayed.setBackground(null);

            spinnerTourRating.setEnabled(false);
            spinnerTourRating.setBackground(null);

            editCBVANo.setEnabled(false);
            editCBVANo.setBackground(null);

            editCBVAFName.setEnabled(false);
            editCBVAFName.setBackground(null);

            editCBVALName.setEnabled(false);
            editCBVALName.setBackground(null);

            spinnerWtoTravel.setEnabled(false);
            spinnerWtoTravel.setBackground(null);

            editHighschool.setEnabled(false);
            editHighschool.setBackground(null);

            editIndoorClub.setEnabled(false);
            editIndoorClub.setBackground(null);

            editColgClub.setEnabled(false);
            editColgClub.setBackground(null);

            editColgBeach.setEnabled(false);
            editColgBeach.setBackground(null);

            editColgIndoor.setEnabled(false);
            editColgIndoor.setBackground(null);

            editPoints.setEnabled(false);
            editPoints.setBackground(null);

            edit_volleyRanking.setEnabled(false);
            edit_volleyRanking.setBackground(null);

            imageView1.setVisibility(View.GONE);
            topfinishes_txt_1.setEnabled(false);
            topfinishes_txt_1.setBackground(null);


            topfinishes_txt_2.setEnabled(false);
            topfinishes_txt_2.setBackground(null);
            imageView2.setVisibility(View.GONE);

            topfinishes_txt_3.setEnabled(false);
            topfinishes_txt_3.setBackground(null);
            imageView3.setVisibility(View.GONE);
            String dateOb = editDob.getText().toString().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date dateDOB  = new Date(dateOb);
            JSONObject object = new JSONObject();
            try {
                //object.put("activated",true);
                object.put("firstName", editFname.getText().toString().trim());
                object.put("lastName", editLname.getText().toString().trim());
                object.put("gender", editGender.getText().toString().trim());
                object.put("dob",dateFormat.format(dateDOB));
                object.put("location", editCity.getText().toString().trim());
                object.put("phoneNumber", editPhone.getText().toString().trim());
                object.put("imageUrl",userDataModel.getImageUrl().trim());
                object.put("videoUrl",userDataModel.getVideoUrl().trim());
                object.put("userType",userDataModel.getUserType().trim());
                object.put("langKey",userDataModel.getLangKey().trim());
                object.put("fcmToken",userDataModel.getFcmToken().trim());
                object.put("email",userDataModel.getEmail().trim());
                object.put("deviceId",userDataModel.getDeviceId().trim());
                object.put("authToken",userDataModel.getAuthToken().trim());
                object.put("city",userDataModel.getLocation().trim());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsonObjectMore = new JSONObject();
            try {

                jsonObjectMore.put("cbvaFirstName", editCBVAFName.getText().toString().trim());
                jsonObjectMore.put("cbvaLastName", editCBVALName.getText().toString().trim());
                jsonObjectMore.put("cbvaPlayerNumber", editCBVANo.getText().toString().trim());
                jsonObjectMore.put("collage",0);
                jsonObjectMore.put("collageClub", editColgClub.getText().toString().trim());
                jsonObjectMore.put("collegeBeach", editColgBeach.getText().toString().trim());
                jsonObjectMore.put("collegeIndoor", editColgIndoor.getText().toString().trim());
                jsonObjectMore.put("courtSidePreference", spinnerPrefValue.trim());
                jsonObjectMore.put("description",0);
                jsonObjectMore.put("division",0);
                jsonObjectMore.put("experience", spinnerExpValue.trim());
                jsonObjectMore.put("fundingStatus",0);
                jsonObjectMore.put("height", editHeight.getText().toString().trim());
                jsonObjectMore.put("highSchoolAttended", editHighschool.getText().toString().trim());
                jsonObjectMore.put("highestTourRatingEarned", spinnerTRValue.trim());
                jsonObjectMore.put("indoorClubPlayed", editIndoorClub.getText().toString().trim());
                jsonObjectMore.put("numOfAthlets",0);
                jsonObjectMore.put("position", spinnerPosValue.trim());
                jsonObjectMore.put("programsOffered",0);
                jsonObjectMore.put("shareAthlets",0);
                jsonObjectMore.put("topFinishes", topfinishes_txt_1.getText().toString().trim()+","+topfinishes_txt_2.getText().toString().trim()+","+topfinishes_txt_3.getText().toString().trim());
                jsonObjectMore.put("totalPoints", editPoints.getText().toString().trim());
                jsonObjectMore.put("tournamentLevelInterest", spinnerTLValue.trim());
                jsonObjectMore.put("toursPlayedIn", editPlayed.getText().toString().trim());
                jsonObjectMore.put("usaVolleyballRanking", edit_volleyRanking.getText().toString().trim());
                jsonObjectMore.put("willingToTravel", spinnerWTValue.trim());
                jsonObjectMore.put("yearsRunning",0);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //update user fields
            JSONObject jsonallobj =new JSONObject();
            try {
                jsonallobj.put("userInputDto",object);
                jsonallobj.put("userProfileDto",jsonObjectMore);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateAllUserDetails(jsonallobj);


        }


    }


    private boolean validate() {
        isValidate = false;
        if (editFname.getText().toString().trim().matches("")) {
            editFname.setError("Please enter your First name");
            isValidate = true;
        } else if (editLname.getText().toString().trim().matches("")) {
            editLname.setError("Please enter your Last name");
            isValidate = true;
        } else if (editGender.getText().toString().trim().matches("")) {
            editGender.setError("Please Choose Gender");
            isValidate = true;
        } else if (editCity.getText().toString().trim().matches("")) {
            editCity.setError("Please enter your city");
            isValidate = true;
        } else if (editPhone.getText().toString().trim().matches("")) {
            editPhone.setError("Please enter your Mobile no");
            isValidate = true;
        } else if (editDob.getText().toString().trim().matches("")) {
            editDob.setError("Please enter your dob");
            isValidate = true;
        }
//        else if(selectedImageUri == null){
//            Toast.makeText(getActivity(), "Please upload a picture", Toast.LENGTH_SHORT).show();
//            isValidate = true;
//        }else if(selectedVideoUri == null){
//            Toast.makeText(getActivity(), "Please upload a Video", Toast.LENGTH_SHORT).show();
//            isValidate = true;
//        }
        return isValidate;
    }


    private void validateFeilds() {
        awesomeValidation.addValidation(getActivity(), R.id.txtvFname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtvLname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtv_gender, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.gendererror);
        awesomeValidation.addValidation(getActivity(), R.id.txtv_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cityerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtv_mobileno, "^[1-9]{2}[0-9]{8}$", R.string.mobilerror);
    }


    public void InfoCancelChange() {

        profile_img_editIcon.setVisibility(View.GONE);
        imgVideo.setVisibility(View.GONE);
        btnsBottom.setVisibility(View.GONE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
        edit_tag.setTextColor(getResources().getColor(R.color.imgBacgnd));
        more_info_btns_bottom.setVisibility(View.GONE);
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

//        editPassword.setEnabled(false);
//        editPassword.setBackground(null);


        //MoreInfo
        spinnerExp.setEnabled(false);
        spinnerExp.setBackground(null);

        spinnerPref.setEnabled(false);
        spinnerPref.setBackground(null);

        spinnerPositon.setEnabled(false);
        spinnerPositon.setBackground(null);

        editHeight.setEnabled(false);
        editHeight.setBackground(null);


        spinnerTLInterest.setEnabled(false);
        spinnerTLInterest.setBackground(null);

        editPlayed.setEnabled(false);
        editPlayed.setBackground(null);

        spinnerTourRating.setEnabled(false);
        spinnerTourRating.setBackground(null);

        editCBVANo.setEnabled(false);
        editCBVANo.setBackground(null);

        editCBVAFName.setEnabled(false);
        editCBVAFName.setBackground(null);

        editCBVALName.setEnabled(false);
        editCBVALName.setBackground(null);

        spinnerWtoTravel.setEnabled(false);
        spinnerWtoTravel.setBackground(null);

        editHighschool.setEnabled(false);
        editHighschool.setBackground(null);

        editIndoorClub.setEnabled(false);
        editIndoorClub.setBackground(null);

        editColgClub.setEnabled(false);
        editColgClub.setBackground(null);

        editColgBeach.setEnabled(false);
        editColgBeach.setBackground(null);

        editColgIndoor.setEnabled(false);
        editColgIndoor.setBackground(null);

        editPoints.setEnabled(false);
        editPoints.setBackground(null);

        edit_volleyRanking.setEnabled(false);
        edit_volleyRanking.setBackground(null);


        topfinishes_txt_1.setEnabled(false);
        imageView1.setVisibility(View.GONE);
        topfinishes_txt_1.setBackground(null);

        topfinishes_txt_2.setEnabled(false);
        imageView2.setVisibility(View.GONE);
        topfinishes_txt_1.setBackground(null);

        topfinishes_txt_3.setEnabled(false);
        imageView3.setVisibility(View.GONE);
        topfinishes_txt_1.setBackground(null);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //tabHost.addTab(tabHost.newTabSpec("basicInfo").setIndicator("Basic Information").setContent());
    //tabHost.addTab(tabHost.newTabSpec("moreInfo").setIndicator("More Information").setContent());


    // TODO: Rename method, update argument and hook method into UI event

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                //Uri picUri = data.getData();

                 selectedImageUri = data.getData();//Uri.parse(data.getExtras().get("data").toString());//data.getData();//data.getExtras().get("data");
                if (selectedImageUri != null) {
                    File imgfile = new File(String.valueOf(selectedImageUri));
                    // Get length of file in bytes

                    if (fileSize(imgfile.length()) <= 4) {
                        imageUri = getPath(selectedImageUri);
                        imgProfile.setImageURI(selectedImageUri);
                    } else {
                        Toast.makeText(getActivity(), "Image size is too large", Toast.LENGTH_SHORT).show();
                    }

                }

            }
            else if(requestCode == PICK_VIDEO_REQUEST){
                Intent intent = new Intent();
                intent.setType("video/*");
                Uri picUri = data.getData();
                selectedVideoUri = data.getData();  //Uri.parse(data.getExtras().get("data").toString());//data.getExtras().get("data");
                if (selectedVideoUri != null) {

                    File file = new File(String.valueOf(getPath(selectedVideoUri)));

                    if (fileSize(file.length()) <= 30&&videoDuration <= 30) {
                        videoUri = getPath(selectedVideoUri);
                        imgVideo.setVisibility(View.VISIBLE);
                        imgPlay.setVisibility(View.VISIBLE);
                        videoView.setVideoURI(Uri.parse(String.valueOf(selectedVideoUri)));
                    } else {
                        Toast.makeText(getActivity(), "Aw!! Video is too large", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "duration"+videoDuration, Toast.LENGTH_SHORT).show();
                    }
                }

            }


        }

        if (imageUri != null || videoUri != null) {

            //Method for uploading profilePic & profile video
            if(videoUri==null){
                uploadImgFiles(imageUri,user_id);
            }
            else{
                
                uploadVideoFiles(videoUri, user_id);
            }

        }
    }

    //Method for check the size of the selected file
    private float fileSize(long fileLength){
        long fileSizeInBytes = fileLength;
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1024;

        return fileSizeInMB;
    }

    //Api for upload profile image and video
    private void uploadFiles(final String imagePath, final String videoPath, final String userId) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
//                    HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//                    SSLSocketFactory sslFactory = new SimpleSSLSocketFactory(null);
//                    sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                    //throws ParseException, IOException
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://beachpartner.com/storage/uploadProfileData");
//                    sslFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);

                    FileBody videoFile = new FileBody(new File(videoPath));
                    FileBody imageFile = new FileBody(new File(imagePath));
                    StringBody user_Id = new StringBody(userId);

                    MultipartEntity reqEntity = new MultipartEntity();
                    reqEntity.addPart("profileImg", imageFile);
                    reqEntity.addPart("profileVideo", videoFile);
                    reqEntity.addPart("userId", user_Id);
                    httppost.setEntity(reqEntity);

//                    // Register the HTTP and HTTPS Protocols. For HTTPS, register our custom SSL Factory object.
//                    SchemeRegistry registry = new SchemeRegistry();
////                    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//                    registry.register(new Scheme("https", sslFactory, 443));

                    // DEBUG
                    System.out.println( "executing request " + httppost.getRequestLine( ) );
                    HttpResponse response = httpclient.execute( httppost );
                    HttpEntity resEntity = response.getEntity( );

                    // DEBUG
                    System.out.println( response.getStatusLine( ) );
                    if (resEntity != null) {
                        System.out.println( EntityUtils.toString( resEntity ) );
                    } // end if

                    if (resEntity != null) {
                        resEntity.consumeContent( );
                    } // end if

                    httpclient.getConnectionManager( ).shutdown( );
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


    }

    private void uploadImgFiles(final String imagePath,final String userId) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                    HttpClient httpclient = new DefaultHttpClient();

                    SchemeRegistry registry = new SchemeRegistry();
                    SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                    socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                    registry.register(new Scheme("https", socketFactory, 443));

//                    SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
//                    DefaultHttpClient httpClient = new DefaultHttpClient(mgr, httpclient.getParams());

                    HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);


                    //throws ParseException, IOException


                    HttpPost httppost = new HttpPost("https://www.beachpartner.com/api/storage/uploadProfileData");

                    FileBody imageFile = new FileBody(new File(imagePath));
                    StringBody user_Id = new StringBody(userId);

                    MultipartEntity reqEntity = new MultipartEntity();
                    reqEntity.addPart("profileImg", imageFile);
                    reqEntity.addPart("userId", user_Id);
                    httppost.setEntity(reqEntity);




                    // DEBUG
//                    HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
                    System.out.println( "executing request " + httppost.getRequestLine( ) );
                    HttpResponse response = httpclient.execute( httppost );
                    HttpEntity resEntity = response.getEntity( );


                    // DEBUG
                    System.out.println( response.getStatusLine( ) );
                    if (resEntity != null) {
                        System.out.println( EntityUtils.toString( resEntity ) );
                    } // end if

                    if (resEntity != null) {
                        resEntity.consumeContent( );
                    } // end if

                    httpclient.getConnectionManager( ).shutdown( );
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


    }




    private void uploadVideoFiles(final String videoPath, final String userId) {


        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    new AsyncTask<String, String, HttpEntity>() {
                        @Override
                        protected HttpEntity doInBackground(String... params) {
                            try {


                                FileBody videoFile = new FileBody(new File(videoPath));

                                StringBody user_Id = new StringBody(userId);

                                MultipartEntity reqEntity = new MultipartEntity();

                                reqEntity.addPart("profileVideo", videoFile);
                                reqEntity.addPart("userId", user_Id);


                                HttpEntity result = uploadToServer(reqEntity);
                                return result;

                                // DEBUG
                                //                    System.out.println( response.getStatusLine( ) );
                                //                    if (resEntity != null) {
                                //                        System.out.println(  resEntity  );
                                //                    } // end if
                                //
                                //                    if (resEntity != null) {
                                //                        resEntity.consumeContent( );
                                //                    } // end if
                                //
                                //                    httpclient.getConnectionManager( ).shutdown( );
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(HttpEntity result) {
                            try {
                                if (result != null) {
                                    System.out.println( EntityUtils.toString( result ) );
                                } // end if

                                if (result != null) {
                                    result.consumeContent( );
                                    progressbar.setVisibility(View.GONE);
                                } // end if

                                int success, failure;
                                // success = resultJson.getInt("success");
                                //failure = resultJson.getInt("failure");
                                //  Toast.makeText(getContext(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                //                    Toast.makeText(getContext(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();


    }

    HttpEntity uploadToServer(MultipartEntity reqEntity) throws IOException {
        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        try {
            progressbar.setVisibility(View.VISIBLE);
            SSLSocketFactory sslFactory = new SimpleSSLSocketFactory(null);
            sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            //throws ParseException, IOException
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(new URI("https://www.beachpartner.com/api/storage/uploadProfileData"));
//            sslFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            // Register the HTTP and HTTPS Protocols. For HTTPS, register our custom SSL Factory object.
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sslFactory, 443));

            httppost.setEntity(reqEntity);



            // DEBUG
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);


            HttpEntity resEntity = response.getEntity();
            while(resEntity==null){

            }
            return resEntity;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
                            userDataModel.setLangKey(response.getString("langKey"));
                            userDataModel.setLocation(response.getString("city"));
                            //userDataModel.setSubscriptions(response.getString("subscriptions"));
                            userDataModel.setImageUrl(response.getString("imageUrl"));
                            userDataModel.setVideoUrl(response.getString("videoUrl"));
                            userDataModel.setUserType(response.getString("userType"));
                            userDataModel.setFcmToken(response.getString("fcmToken"));
                            userDataModel.setAuthToken(response.getString("authToken"));
                            userDataModel.setDeviceId(response.getString("deviceId"));
                            userDataModel.setEmail(response.getString("email"));
                            if (!response.getString("userProfile").equals(null) && !response.getString("userProfile").equals("null") ) {

                                JSONObject obj = new JSONObject(response.getString("userProfile"));
                                if (obj != null && obj.length() != 0) {
                                    userDataModel.setHeight(obj.getString("height"));
                                    userDataModel.setCbvaPlayerNumber(obj.getString("cbvaPlayerNumber"));
                                    userDataModel.setCbvaFirstName(obj.getString("cbvaFirstName"));
                                    userDataModel.setCbvaLastName(obj.getString("cbvaLastName"));
                                    userDataModel.setToursPlayedIn(obj.getString("toursPlayedIn"));
                                    userDataModel.setTotalPoints(obj.getString("totalPoints"));
                                    userDataModel.setHighSchoolAttended(obj.getString("highSchoolAttended"));
                                    userDataModel.setCollageClub(obj.getString("collageClub"));
                                    userDataModel.setIndoorClubPlayed(obj.getString("indoorClubPlayed"));
                                    userDataModel.setCollegeIndoor(obj.getString("collegeIndoor"));
                                    userDataModel.setCollegeBeach(obj.getString("collegeBeach"));
                                    userDataModel.setTournamentLevelInterest(obj.getString("tournamentLevelInterest"));
                                    userDataModel.setHighestTourRatingEarned(obj.getString("highestTourRatingEarned"));
                                    userDataModel.setExperience(obj.getString("experience"));
                                    userDataModel.setCourtSidePreference(obj.getString("courtSidePreference"));
                                    userDataModel.setPosition(obj.getString("position"));
                                    userDataModel.setWillingToTravel(obj.getString("willingToTravel"));
                                    userDataModel.setUsaVolleyballRanking(obj.getString("usaVolleyballRanking"));
                                    userDataModel.setTopFinishes(obj.getString("topFinishes"));
                                    userDataModel.setCollage(obj.getString("collage"));
                                    userDataModel.setDescription(obj.getString("description"));
                                    userDataModel.setYearsRunning(obj.getString("yearsRunning"));
                                    userDataModel.setNumOfAthlets(obj.getString("numOfAthlets"));
                                    userDataModel.setProgramsOffered(obj.getString("programsOffered"));
                                    userDataModel.setDivision(obj.getString("division"));
                                    userDataModel.setFundingStatus(obj.getString("fundingStatus"));
                                    userDataModel.setShareAthlets(obj.getString("shareAthlets"));
                                }
                            }


                            //new PrefManager(getActivity()).saveUserDetails(response.getString("id"));
                            setViews();



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
            public Map<String, String> getHeaders() throws AuthFailureError {
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


    //Get User Details Api

/*
    private void updateUserDetails(JSONObject object) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_PUT, ApiService.UPDATE_USER_DETAILS, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Toast.makeText(getActivity(), "User Details Updated", Toast.LENGTH_SHORT).show();
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
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

    }
*/

    //Update User Details

    private void updateAllUserDetails(JSONObject object) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_PUT, ApiService.UPDATE_USER_PROFILE + user_id, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "User Details Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

    }

    @SuppressLint("NewApi")
    private void setViews() {
        if (getActivity() != null) {
            if (userDataModel != null) {
                //set basic informations of user
                if (userDataModel.getImageUrl() != null) {
                    Glide.with(ProfileFragment.this).load(userDataModel.getImageUrl()).into(imgProfile);
                }else {
                    imgProfile.setImageResource(R.drawable.ic_person);
                }
                if (userDataModel.getVideoUrl() != null) {
                    videoView.setVideoURI(Uri.parse(userDataModel.getVideoUrl()));
                    videoView.setVisibility(View.VISIBLE);
                    videoView.start();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            long milliseconds=videoView.getDuration();
                            videoDuration = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
                            mp.setVolume(0, 0);
                            mp.setLooping(true);
                        }
                    });
                }
                 /*   videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                            if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                                progressbar.setVisibility(View.GONE);
                            }
                            if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                                progressbar.setVisibility(View.VISIBLE);
                            }
                            if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                                videoView.setVisibility(View.GONE);
                                progressbar.setVisibility(View.GONE);
                                imgPlay.setVisibility(View.VISIBLE);
                            }
                            return false;
                        }
                    });
                }
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.setVisibility(View.GONE);
                        progressbar.setVisibility(View.GONE);
                        imgPlay.setVisibility(View.VISIBLE);
                    }
                });*/
                profileName.setText(userDataModel.getFirstName().trim());
                profileDesig.setText(userDataModel.getUserType().trim());
                editLname.setText(userDataModel.getLastName().trim());
                editFname.setText(userDataModel.getFirstName().trim());
                editGender.setText(userDataModel.getGender().trim());
                editCity.setText(userDataModel.getCity().trim());
                //Long value to date conversion
                SimpleDateFormat dft = new SimpleDateFormat("MMM dd, yyyy");
                long dob       = Long.parseLong(userDataModel.getDob());
                Date date_dob  = new Date(dob);
                editDob.setText(dft.format(date_dob));
                editPhone.setText(userDataModel.getPhoneNumber());
                //set More information
                editHeight.setText(userDataModel.getHeight());
                editCBVAFName.setText(userDataModel.getCbvaFirstName());
                editCBVALName.setText(userDataModel.getCbvaLastName());
                editCBVANo.setText(userDataModel.getCbvaPlayerNumber());
                editColgClub.setText(userDataModel.getCollageClub());
                editColgBeach.setText(userDataModel.getCollegeBeach());
                editColgIndoor.setText(userDataModel.getCollegeIndoor());
                editHighschool.setText(userDataModel.getHighSchoolAttended());
                editIndoorClub.setText(userDataModel.getIndoorClubPlayed());
                editPoints.setText(userDataModel.getTotalPoints());
                editPlayed.setText(userDataModel.getToursPlayedIn());
                edit_volleyRanking.setText(userDataModel.getUsaVolleyballRanking());
                String topFinishes = userDataModel.getTopFinishes();
           /* if (!topFinishes.equals("null")) {
                List<String> finishes = Arrays.asList(topFinishes.split(","));
                if(finishes.size()>0){
                    topfinishes_txt_1.setText(finishes.get(0));
                    topfinishes_txt_2.setText(finishes.get(1));
                    topfinishes_txt_3.setText(finishes.get(2));
                }
            }*/

                String courSidePref = userDataModel.getCourtSidePreference();
                if(courSidePref != null){
                    int courtPos = prefAdapter.getPosition(courSidePref);
                    spinnerPref.setSelection(courtPos);
                }
                String exp = userDataModel.getExperience();
                if(exp != null){
                    int exper = expAdapter.getPosition(exp);
                    spinnerExp.setSelection(exper);
                }
                String highestTER  = userDataModel.getHighestTourRatingEarned();
                if(highestTER != null){
                    int hter = highestRatingAdapter.getPosition(highestTER);
                    spinnerTourRating.setSelection(hter);
                }
                String tourIntrest = userDataModel.getTournamentLevelInterest();
                if(tourIntrest != null){
                    int tIL = tournamentInterestAdapter.getPosition(tourIntrest);
                    spinnerTLInterest.setSelection(tIL);
                }
                String pos = userDataModel.getPosition();
                if (pos != null){
                    int positions = positionAdapter.getPosition(pos);
                    spinnerPositon.setSelection(positions);
                }
                String wTot = userDataModel.getWillingToTravel();
                if (wTot != null) {
                    int willingTotravel = distanceAdapter.getPosition(wTot);
                    spinnerWtoTravel.setSelection(willingTotravel);
                }

            }
        }

    }

    public void addLocation() {
        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia");
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland");
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri");
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey");
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina");
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconsin WI");
        stateList.add("Wyoming WY");

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
    //Camera Functionality

    //Intent Chooser

    public  Intent getPickImageIntent(Context context,String type) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};

        if(!hasPermissions(getActivity(),type,intentList, PERMISSIONS)){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        else{


            if (type.equals("videoIntent")){
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);


                if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // takeVideoIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                     takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30000);
                     takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,2097152L);// 10*1024*1024 = 10MB  10485760L
                    //startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);

                }
                intentList = addIntentsToList(context, intentList, pickIntent);
                intentList = addIntentsToList(context, intentList, takeVideoIntent);

            }else if(type.equals("imageIntent")){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentList = addIntentsToList(context, intentList, pickIntent);
                intentList = addIntentsToList(context, intentList, takePictureIntent);
        /*if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }*/
            }

        }


        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),"hi");
            // context.getString(R.string.pick_image_intent_text));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }



    public boolean hasPermissions(Context context,String type,List<Intent> intentList, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (type.equals("videoIntent")){
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);


                        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            // takeVideoIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            // takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30000);
                            // takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                            takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,2097152L);// 10*1024*1024 = 10MB  10485760L
                            //startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);

                        }
                        intentList = addIntentsToList(context, intentList, pickIntent);
                        intentList = addIntentsToList(context, intentList, takeVideoIntent);

                    }else if(type.equals("imageIntent")){
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intentList = addIntentsToList(context, intentList, pickIntent);
                        intentList = addIntentsToList(context, intentList, takePictureIntent);
        /*if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }*/
                    }

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


/*
*  Methods for convert the camera image
*
* */


    public static Bitmap getImageFromResult(Context context, int resultCode,
                                            Intent imageReturnedIntent) {
        Log.d(TAG, "getImageFromResult, resultCode: " + resultCode);
        Bitmap bm = null;
        File imageFile = getTempFile(context);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage;
            boolean isCamera = (imageReturnedIntent == null ||
                    imageReturnedIntent.getData() == null  ||
                    imageReturnedIntent.getData().toString().contains(imageFile.toString()));
            if (isCamera) {     /** CAMERA **/
                selectedImage = Uri.fromFile(imageFile);
            } else {            /** ALBUM **/
                selectedImage = imageReturnedIntent.getData();
            }
            Log.d(TAG, "selectedImage: " + selectedImage);

            bm = getImageResized(context, selectedImage);
            int rotation = getRotation(context, selectedImage, isCamera);
            bm = rotate(bm, rotation);
        }
        return bm;
    }


    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), "BpProfileImage");
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }

    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        Log.d(TAG, options.inSampleSize + " sample method bitmap ... " +
                actuallyUsableBitmap.getWidth() + " " + actuallyUsableBitmap.getHeight());

        return actuallyUsableBitmap;
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     **/
    private static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm = null;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i]);
            Log.d(TAG, "resizer: new bitmap width = " + bm.getWidth());
            i++;
        } while (bm.getWidth() < 500 && i < sampleSizes.length);
        return bm;
    }


    private static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;
        if (isCamera) {
            rotation = getRotationFromCamera(context, imageUri);
        } else {
            rotation = getRotationFromGallery(context, imageUri);
        }
        Log.d(TAG, "Image rotation: " + rotation);
        return rotation;
    }

    private static int getRotationFromCamera(Context context, Uri imageFile) {
        int rotate = 0;
        try {

            context.getContentResolver().notifyChange(imageFile, null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static int getRotationFromGallery(Context context, Uri imageUri) {
        int result = 0;
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
        } catch (Exception e) {
            //Do nothing
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }//End of try-catch block
        return result;
    }


    private static Bitmap rotate(Bitmap bm, int rotation) {
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap bmOut = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            return bmOut;
        }
        return bm;
    }

    private class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}