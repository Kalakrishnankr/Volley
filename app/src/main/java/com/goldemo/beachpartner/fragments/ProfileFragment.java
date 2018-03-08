package com.goldemo.beachpartner.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.OnClickListener;
import com.goldemo.beachpartner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ProfileFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_TAKE_GALLERY_VIDEO=1;
    private static final int REQUEST_TAKE_GALLERY_IMAGE=2;
    private TabLayout tabs;

    private ViewPager viewPager;
    private FrameLayout videoFrame;
    private ImageView imgEdit, imgVideo ,imgPlay,profile_img_editIcon,imageView1,imageView2,imageView3;
    private CircularImageView imgProfile;
    private TextView profileName,profileDesig,edit_tag,basic_info_tab,more_info_tab;
    private OnClickListener mOnClickListener;
    private VideoView videoView;
    private  Uri selectedImageUri,selectedVideoUri;
    Calendar myCalendar = Calendar.getInstance();


    private LinearLayout llMenuBasic,llMenuMore,llBasicDetails,llMoreDetails;//This menu bar only for demo purpose
    private View viewBasic,viewMore;

    private EditText editFname,editLname,editGender,editDob,editCity,editPhone;
    private EditText editExp,editPref,editPos,editHeight,editIntrest,editPlayed,editHighest,editCBVANo,editCBVAFName,editCBVALName,editWtoTravel,editHighschool,editIndoorClub,editColgClub,editColgBeach,editColgIndoor,editPoints,editRank,topfinishes_txt_2,topfinishes_txt_1,topfinishes_txt_3;
    private Button moreBtnSave,moreBtnCancel,basicBtnSave,basicBtnCancel;
    private LinearLayout btnsBottom,more_info_btns_bottom;
    LinearLayout topFinishes1_lt,topFinishes2_lt,topFinishes3_lt;
    RelativeLayout containingLt;
    private int finishCount=0;
    boolean editStatus=false;
    private Spinner spinnerExp;

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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        initActivity(view);
        return view;
    }

    private void initActivity(final View view) {

        profile_img_editIcon    =       (ImageView) view.findViewById(R.id.edit_profile_img_vid);
        btnsBottom              =       (LinearLayout) view.findViewById(R.id.btns_at_bottom);

        more_info_btns_bottom   =       (LinearLayout) view.findViewById(R.id.more_info_btns_bottom);
        //tabs        =   (TabLayout)view.findViewById(R.id.tabs);
        //viewPager   =   (ViewPager) view.findViewById(R.id.pager);
        //videoFrame  =   (FrameLayout)view.findViewById(R.id.header_cover_video);
        imgEdit                 =       (ImageView)view.findViewById(R.id.edit);

        imgProfile              =       (CircularImageView)view.findViewById(R.id.row_icon);
        profileName             =       (TextView)view.findViewById(R.id.profile_name);
        profileDesig            =       (TextView)view.findViewById(R.id.profile_designation);
        edit_tag                =       (TextView)view.findViewById(R.id.edit_text);


        imgVideo                =       (ImageView)view. findViewById(R.id.imgVideo);
        videoView               =       (VideoView)view. findViewById(R.id.videoView);
        imgPlay                 =       (ImageView)view. findViewById(R.id.imgPlay);

        /*This for demo only start*/

        llMenuBasic             =    (LinearLayout)view.findViewById(R.id.llMenuBasic);
        llMenuMore              =    (LinearLayout)view.findViewById(R.id.llMenuMore);

        basic_info_tab          =     (TextView) view.findViewById(R.id.basic_info_tab);
        more_info_tab          =     (TextView) view.findViewById(R.id.more_info_tab);

        llBasicDetails          =    (LinearLayout)view.findViewById(R.id.llBasicDetails);
        llMoreDetails           =    (LinearLayout)view.findViewById(R.id.llMoreInfoDetails);

        viewBasic               =    (View)view.findViewById(R.id.viewBasic);
        viewMore                =    (View)view.findViewById(R.id.viewMore);

        //For Basic Details

        editFname               =   (EditText)view.findViewById(R.id.txtvFname);
        editLname               =   (EditText)view.findViewById(R.id.txtvLname);
        editGender              =   (EditText)view.findViewById(R.id.txtv_gender);
        editDob                 =   (EditText)view.findViewById(R.id.txtv_dob);
        editCity                =   (EditText)view.findViewById(R.id.txtv_city);
        editPhone               =   (EditText)view.findViewById(R.id.txtv_mobileno);
//        editPassword    =   (EditText)view.findViewById(R.id.txtv_password);

        basicBtnSave    =   (Button)view.findViewById(R.id.btnsave);
        basicBtnCancel  =   (Button)view.findViewById(R.id.btncancel);


        //Fore More Deatsils

        spinnerExp         =(Spinner) view.findViewById(R.id.spinner_exp);
        spinnerExp.setEnabled(false);
        editPref        =   (EditText)view.findViewById(R.id.txtvPref);
        editPos         =   (EditText)view.findViewById(R.id.txtvPos);
        editHeight      =   (EditText)view.findViewById(R.id.txtvHeight);
        editIntrest     =   (EditText)view.findViewById(R.id.txtvIntrest);
        editPlayed      =   (EditText)view.findViewById(R.id.txtvPlayed);
        editHighest     =   (EditText)view.findViewById(R.id.txtvHighest);
        editCBVANo      =   (EditText)view.findViewById(R.id.txtvCBVANo);
        editCBVAFName   =   (EditText)view.findViewById(R.id.txtvCBVAFName);
        editCBVALName   =   (EditText)view.findViewById(R.id.txtvCBVALName);
        editWtoTravel   =   (EditText)view.findViewById(R.id.txtvWtoTravel);
        editHighschool  =   (EditText)view.findViewById(R.id.txtvHighschool);
        editIndoorClub  =   (EditText)view.findViewById(R.id.txtvIndoorClub);
        editColgClub    =   (EditText)view.findViewById(R.id.txtvColgClub);
        editColgBeach   =   (EditText)view.findViewById(R.id.txtvColgBeach);
        editColgIndoor  =   (EditText)view.findViewById(R.id.txtvColgIndoor);
        editPoints      =   (EditText)view.findViewById(R.id.txtvPoints);
        editRank        =   (EditText)view.findViewById(R.id.txtvRank);
        topfinishes_txt_1 = (EditText)view.findViewById(R.id.topfinishes_txt_1);
        topfinishes_txt_2 = (EditText)view.findViewById(R.id.topfinishes_txt_2);
        topfinishes_txt_3 = (EditText)view.findViewById(R.id.topfinishes_txt_3);

        moreBtnSave     =   (Button)view.findViewById(R.id.btn_save);
        moreBtnCancel   =   (Button)view.findViewById(R.id.btn_cancel);


        llMenuBasic.setOnClickListener(this);
        llMenuMore.setOnClickListener(this);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);

        containingLt= (RelativeLayout) view.findViewById(R.id.scroll_more);
        topFinishes1_lt= (LinearLayout) view.findViewById(R.id.topFinishes1_lt);
        topFinishes2_lt=(LinearLayout) view.findViewById(R.id.topFinishes2_lt) ;
        topFinishes3_lt=(LinearLayout) view.findViewById(R.id.topFinishes3_lt) ;

         /*This for demo only end*/



        // setupViewPager(viewPager);
        // tabs.setupWithViewPager(viewPager);

//        imgEdit.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPlay.setOnClickListener(this);

        spinnerExp.setOnItemSelectedListener(this);

        List<String> experience = new ArrayList<>();
        experience.add("“Newbie” New to the Game");
        experience.add("1-2 years Some Indoor/Beach Experience Services");
        experience.add("2-3 years Beach Club/Tournament Experience");
        experience.add("3-4 years Experienced Tournament Player");
        experience.add("5+ years Multiple Top Finishes/Ranked player");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, experience);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerExp.setAdapter(dataAdapter);




//        Buttons click action for saving
        basicBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoSave();
                editStatus=!editStatus;
            }
        });
        moreBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoSave();
                editStatus=!editStatus;
            }
        });

        basicBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoCancelChange();
                editStatus=!editStatus;
            }
        });
        moreBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoCancelChange();
                editStatus=!editStatus;
            }
        });

        //edit profile button(ImageView)
        imgEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editStatus=!editStatus;


                if(editStatus){
                    editCustomView();
                    editBasicInfo();
                    editMoreInfo();


                    imgVideo.setVisibility(View.VISIBLE);
                    profile_img_editIcon.setVisibility(View.VISIBLE);
                    imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
                    edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
                }
                else {

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
                final RadioButton radioButton_male       = (RadioButton)  alertLayout.findViewById(R.id.rd_1);
                final RadioButton female                 = (RadioButton)  alertLayout.findViewById(R.id.rd_2);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Select Gender");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(radioGroup_gender.getCheckedRadioButtonId()==radioButton_male.getId())
                            editGender.setText("Male");
                        else{
                            editGender.setText("Female");
                        }
                    }

                });

                AlertDialog dialog = alert.create();
                dialog.show();

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
                finishCount+=1;
                if(finishCount==1){
                    imageView2.setVisibility(View.VISIBLE);
                    topFinishes2_lt.setVisibility(View.VISIBLE);

                }
                else if(finishCount==2){
                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);

                    topFinishes2_lt.setVisibility(View.VISIBLE);
                    topFinishes3_lt.setVisibility(View.VISIBLE);

                }
                else{
                    topFinishes2_lt.setVisibility(View.GONE);
                    topFinishes3_lt.setVisibility(View.GONE);
                }

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCount-=1;
                topFinishes2_lt.setVisibility(View.GONE);
                if(finishCount<1){
                    imageView1.setVisibility(View.VISIBLE);
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCount-=1;
                topFinishes3_lt.setVisibility(View.GONE);
                if(finishCount<2){
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
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imgVideo:
                Intent intent= new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
                break;
            case R.id.row_icon:
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1,"Select Image"),REQUEST_TAKE_GALLERY_IMAGE);
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

    private void playVideo() {
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.setVisibility(View.GONE);
                imgPlay.setVisibility(View.VISIBLE);

            }
        });
    }

    private void editCustomView() {

        imgVideo.setVisibility(View.VISIBLE);
        profile_img_editIcon.setVisibility(View.VISIBLE);
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
        edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
    }


    public void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDob.setText(sdf.format(myCalendar.getTime()));
    }



    private void editBasicInfo() {

        //Profile image edit icon active

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


        editPref.setEnabled(true);
        editPref.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPos.setEnabled(true);
        editPos.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editHeight.setEnabled(true);
        editHeight.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));


        editIntrest.setEnabled(true);
        editIntrest.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editPlayed.setEnabled(true);
        editPlayed.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editHighest.setEnabled(true);
        editHighest.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVANo.setEnabled(true);
        editCBVANo.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVAFName.setEnabled(true);
        editCBVAFName.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editCBVALName.setEnabled(true);
        editCBVALName.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

        editWtoTravel.setEnabled(true);
        editWtoTravel.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));

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


        editRank.setEnabled(true);
        editRank.setBackground(getResources().getDrawable(R.drawable.edit_test_bg));



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
    public void InfoSave(){

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

        editPref.setEnabled(false);
        editPref.setBackground(null);

        editPos.setEnabled(false);
        editPos.setBackground(null);

        editHeight.setEnabled(false);
        editHeight.setBackground(null);


        editIntrest.setEnabled(false);
        editIntrest.setBackground(null);

        editPlayed.setEnabled(false);
        editPlayed.setBackground(null);

        editHighest.setEnabled(false);
        editHighest.setBackground(null);

        editCBVANo.setEnabled(false);
        editCBVANo.setBackground(null);

        editCBVAFName.setEnabled(false);
        editCBVAFName.setBackground(null);

        editCBVALName.setEnabled(false);
        editCBVALName.setBackground(null);

        editWtoTravel.setEnabled(false);
        editWtoTravel.setBackground(null);

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


        editRank.setEnabled(false);
        editRank.setBackground(null);

        imageView1.setVisibility(View.GONE);
        topfinishes_txt_1.setEnabled(false);
        topfinishes_txt_1.setBackground(null);


        topfinishes_txt_2.setEnabled(false);
        topfinishes_txt_2.setBackground(null);
        imageView2.setVisibility(View.GONE);

        topfinishes_txt_3.setEnabled(false);
        topfinishes_txt_3.setBackground(null);
        imageView3.setVisibility(View.GONE);


    }


    public void InfoCancelChange(){

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

        editPref.setEnabled(false);
        editPref.setBackground(null);

        editPos.setEnabled(false);
        editPos.setBackground(null);

        editHeight.setEnabled(false);
        editHeight.setBackground(null);


        editIntrest.setEnabled(false);
        editIntrest.setBackground(null);

        editPlayed.setEnabled(false);
        editPlayed.setBackground(null);

        editHighest.setEnabled(false);
        editHighest.setBackground(null);

        editCBVANo.setEnabled(false);
        editCBVANo.setBackground(null);

        editCBVAFName.setEnabled(false);
        editCBVAFName.setBackground(null);

        editCBVALName.setEnabled(false);
        editCBVALName.setBackground(null);

        editWtoTravel.setEnabled(false);
        editWtoTravel.setBackground(null);

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


        editRank.setEnabled(false);
        editRank.setBackground(null);

        topfinishes_txt_1.setEnabled(false);
        imageView1.setVisibility(View.GONE);

        topfinishes_txt_2.setEnabled(false);
        imageView2.setVisibility(View.GONE);

        topfinishes_txt_3.setEnabled(false);
        imageView3.setVisibility(View.GONE);


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                selectedVideoUri = data.getData();
                // String filemanagerstring = selectedVideoUri.getPath();
                // String selectedVideoPath = getPath(selectedVideoUri);
                if (selectedVideoUri != null) {

                    imgVideo.setVisibility(View.GONE);
                    //videoView.setVisibility(View.VISIBLE);
                    imgPlay.setVisibility(View.VISIBLE);
                    videoView.setVideoURI(Uri.parse(String.valueOf(selectedVideoUri)));

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
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
