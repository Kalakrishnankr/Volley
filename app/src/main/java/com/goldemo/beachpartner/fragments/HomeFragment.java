package com.goldemo.beachpartner.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.CardAdapter;
import com.goldemo.beachpartner.adpters.MessageAdapter;
import com.goldemo.beachpartner.adpters.PartnerAdapter;
import com.goldemo.beachpartner.adpters.ProfileAdapter;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile,img_send,img_received;
    private FrameLayout likesCard;
    private RecyclerView mRecyclerview,pRecyclerview,msgRecyclerview,parRecyclerview;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    PartnerAdapter partnerAdapter;
    ProfileAdapter profileAdapter;
    ArrayList<PersonModel> allSampleData;
    private TextView txt_head;
    private String user_id,user_token,userType;
    private PrefManager prefManager;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        prefManager = new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    = prefManager.getUserType();
        //getBlueBP profes
        getBluebpProfiles();
        return view;
    }


    private void initView(View view) {

        allSampleData = (ArrayList<PersonModel>) createDummyData();


        img_bpprofile   =   (ImageView) view.findViewById(R.id.img_bpfinder);
        txt_head        =   (TextView)view.findViewById(R.id.txtview_head);

        img_send        =   (ImageView)view.findViewById(R.id.imgview_send);
        img_received    =   (ImageView)view.findViewById(R.id.imgview_received);
        likesCard       =       view.findViewById(R.id.no_of_likes_card);

        mRecyclerview   =   (RecyclerView)view.findViewById(R.id.rcv);          //Recycler view for upcoming events
        msgRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_message);  //Recycler view for messages
        parRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_partners); //Recycler view for tournament requests


        img_send.setOnClickListener(this);
        img_received.setOnClickListener(this);
        likesCard.setOnClickListener(this);




        LinearLayoutManager lmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pRecyclerview = (RecyclerView) view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
        profileAdapter = new ProfileAdapter(getContext(),allSampleData);
        pRecyclerview.setAdapter(profileAdapter);
        pRecyclerview.setLayoutManager(lmnger);
        pRecyclerview.setHasFixedSize(true);


        /*My upcoming tournaments*/

        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new CardAdapter(getContext(),allSampleData);
        mRecyclerview.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerview);
        mRecyclerview.setLayoutManager(layoutmnger);
        mRecyclerview.setHasFixedSize(true);


        /*Message*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        messageAdapter  =  new MessageAdapter(getContext(),allSampleData);
        SnapHelper snaper = new PagerSnapHelper();
        snaper.attachToRecyclerView(msgRecyclerview);
        msgRecyclerview.setLayoutManager(layoutManager);
        msgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        msgRecyclerview.setAdapter(messageAdapter);
        msgRecyclerview.setHasFixedSize(true);



        /*Tournament Requests*/

        LinearLayoutManager layoutmngerTwo = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        partnerAdapter  =   new PartnerAdapter(getContext(),allSampleData);
        SnapHelper snap = new PagerSnapHelper();
        snap.attachToRecyclerView(parRecyclerview);
        parRecyclerview.setLayoutManager(layoutmngerTwo);
        parRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        parRecyclerview.setAdapter(partnerAdapter);
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        parRecyclerview.setHasFixedSize(true);


    }

    private List<PersonModel> createDummyData() {

        List<PersonModel>personModelList = new ArrayList<>();
        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        return personModelList;

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgview_send:
                txt_head.setText("Tournament Requests Sent");
                break;

            case R.id.imgview_received:
                txt_head.setText("Tournament Requests Received");
                break;
            case R.id.no_of_likes_card:
                likesDisplay();
                break;

            default:
                break;
        }
    }

    private void likesDisplay() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_no_of_likes_layout, null);


        final Button save_btn            = (Button)   alertLayout.findViewById(R.id.purchase_btn);

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());


        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));


        alert.setView(alertLayout);
        alert.setCancelable(true);



        final android.app.AlertDialog dialog = alert.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.blueDark));
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
            }
        });
        dialog.show();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


   /* Grid item spacing and padding */

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position / spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    //Api for get all blue bp profiles
    private void getBluebpProfiles() {


    }
}

