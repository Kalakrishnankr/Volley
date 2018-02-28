package com.goldemo.beachpartner.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.OnClickListener;
import com.goldemo.beachpartner.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_TAKE_GALLERY_VIDEO=1;
    private static final int REQUEST_TAKE_GALLERY_IMAGE=2;
    private TabLayout tabs;

    private ViewPager viewPager;
    private FrameLayout videoFrame;
    private ImageView imgEdit, imgVideo ,imgPlay;
    private CircularImageView imgProfile;
    private TextView profileName,profileDesig,edit_tag;
    private OnClickListener mOnClickListener;
    private VideoView videoView;
    private  Uri selectedImageUri,selectedVideoUri;

    private LinearLayout llMenuBasic,llMenuMore,llBasicDetails,llMoreDetails;//This menu bar only for demo purpose
    private View viewBasic,viewMore;

    private EditText editFname,editLname,editGender,editDob,editCity,editPhone,editPassword;
    private EditText editExp,editPref,editPos,editHeight,editIntrest,editPlayed,editHighest,editCBVANo,editCBVAFName,editCBVALName,editWtoTravel,editHighschool,editIndoorClub,editColgClub,editColgBeach,editColgIndoor,editPoints,editYouLinks,editBioLinks,editRank;
    private Button moreBtnSave,moreBtnCancel,basicBtnSave,basicBtnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        initActivity(view);
        return view;
    }

    private void initActivity(View view) {

        //tabs        =   (TabLayout)view.findViewById(R.id.tabs);
        //viewPager   =   (ViewPager) view.findViewById(R.id.pager);
        //videoFrame  =   (FrameLayout)view.findViewById(R.id.header_cover_video);
        imgEdit         =   (ImageView)view.findViewById(R.id.edit);

        imgProfile      =   (CircularImageView)view.findViewById(R.id.row_icon);
        profileName     =   (TextView)view.findViewById(R.id.profile_name);
        profileDesig    =   (TextView)view.findViewById(R.id.profile_designation);
        edit_tag       =   (TextView)view.findViewById(R.id.edit_text);


        imgVideo        = (ImageView)view. findViewById(R.id.imgVideo);
        videoView       = (VideoView)view. findViewById(R.id.videoView);
        imgPlay         = (ImageView)view. findViewById(R.id.imgPlay);

        /*This for demo only start*/

        llMenuBasic     =    (LinearLayout)view.findViewById(R.id.llMenuBasic);
        llMenuMore      =    (LinearLayout)view.findViewById(R.id.llMenuMore);

        llBasicDetails  =    (LinearLayout)view.findViewById(R.id.llBasicDetails);
        llMoreDetails   =    (LinearLayout)view.findViewById(R.id.llMoreInfoDetails);

        viewBasic       =    (View)view.findViewById(R.id.viewBasic);
        viewMore        =    (View)view.findViewById(R.id.viewMore);

        //For Basic Details

        editFname       =   (EditText)view.findViewById(R.id.txtvFname);
        editLname       =   (EditText)view.findViewById(R.id.txtvLname);
        editGender      =   (EditText)view.findViewById(R.id.txtv_gender);
        editDob         =   (EditText)view.findViewById(R.id.txtv_dob);
        editCity        =   (EditText)view.findViewById(R.id.txtv_city);
        editPhone       =   (EditText)view.findViewById(R.id.txtv_mobileno);
        editPassword    =   (EditText)view.findViewById(R.id.txtv_password);

        basicBtnSave    =   (Button)view.findViewById(R.id.btnsave);
        basicBtnCancel  =   (Button)view.findViewById(R.id.btncancel);


        //Fore More Deatsils

        editExp         =   (EditText)view.findViewById(R.id.txtvExp);
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
        editYouLinks    =   (EditText)view.findViewById(R.id.txtvYouLinks);
        editBioLinks    =   (EditText)view.findViewById(R.id.txtvBioLinks);
        editRank        =   (EditText)view.findViewById(R.id.txtvRank);

        moreBtnSave     =   (Button)view.findViewById(R.id.btn_save);
        moreBtnCancel   =   (Button)view.findViewById(R.id.btn_cancel);


        llMenuBasic.setOnClickListener(this);
        llMenuMore.setOnClickListener(this);

         /*This for demo only end*/



       // setupViewPager(viewPager);
       // tabs.setupWithViewPager(viewPager);

        imgEdit.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
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


    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new BasicInfoFragment(),"Basic Information");
        adapter.addFragment(new MoreInfoFragment(),"More Information");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit:
                editCustomView();
                editBasicInfo();
                editMoreInfo();

                break;

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
                videoView.start();
                break;



            //Demo Only

            case R.id.llMenuBasic:

                llMoreDetails.setVisibility(View.GONE);
                llBasicDetails.setVisibility(View.VISIBLE);
                viewBasic.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewMore.setBackgroundColor(getResources().getColor(R.color.white));

                break;

            case R.id.llMenuMore:

                llBasicDetails.setVisibility(View.GONE);
                llMoreDetails.setVisibility(View.VISIBLE);
                viewMore.setBackgroundColor(getResources().getColor(R.color.blueDark));
                viewBasic.setBackgroundColor(getResources().getColor(R.color.white));

                break;


                default:
                    break;
        }
    }

    private void editCustomView() {
        imgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_active));
        edit_tag.setTextColor(getResources().getColor(R.color.btnColor));
    }


    private void editBasicInfo() {


        editFname.setEnabled(true);
        editFname.setEnabled(true);
        editLname.setEnabled(true);
        editGender.setEnabled(true);
        editDob.setEnabled(true);
        editCity.setEnabled(true);
        editPhone.setEnabled(true);
        editPassword.setEnabled(true);
    }

    private void editMoreInfo() {

        editExp.setEnabled(true);
        editPref.setEnabled(true);
        editPos.setEnabled(true);
        editHeight.setEnabled(true);
        editIntrest.setEnabled(true);
        editPlayed.setEnabled(true);
        editHighest.setEnabled(true);
        editCBVANo.setEnabled(true);
        editCBVAFName.setEnabled(true);
        editCBVALName.setEnabled(true);
        editWtoTravel.setEnabled(true);
        editHighschool.setEnabled(true);
        editIndoorClub.setEnabled(true);
        editColgClub.setEnabled(true);
        editColgBeach.setEnabled(true);
        editColgIndoor.setEnabled(true);
        editPoints.setEnabled(true);
        editYouLinks.setEnabled(true);
        editBioLinks.setEnabled(true);
        editRank.setEnabled(true);
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
                    videoView.setVisibility(View.VISIBLE);
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
