package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.MyConnectionAdapter;
import com.goldemo.beachpartner.adpters.MyTeamAdapter;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;
import java.util.List;


public class ConnectionsTabFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rlTop,rlTeam;
    private RecyclerView rcviewConn,rcviewTeam;
    private MyConnectionAdapter  mConnectionAdapter;
    private MyTeamAdapter myTeamAdapter;
    private ImageView upDownToggle;

    ArrayList<PersonModel> dummyData;


    private BottomSheetBehavior bottomSheetBehavior;

    public ConnectionsTabFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_connections_tab, container, false);
        initActivity(view);

        dummyData   = (ArrayList<PersonModel>) createDummyData();

        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        //adapter for connections
        mConnectionAdapter  =   new MyConnectionAdapter(getContext(),dummyData);
        rcviewConn.setAdapter(mConnectionAdapter);
        rcviewConn.setLayoutManager(layoutmnger);
       // rcviewConn.setHasFixedSize(true);


        //adapter for creating my team
        LinearLayoutManager mnger = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        myTeamAdapter       =   new MyTeamAdapter(getContext(),dummyData);
        rcviewTeam.setAdapter(myTeamAdapter);
        rcviewTeam.setLayoutManager(mnger);
        rcviewTeam.setHasFixedSize(true);








        return view;
    }

    private void initActivity(View view) {

       // rlTop       =       (RelativeLayout)view.findViewById(R.id.rlayoutTop);
        rlTeam      =       (RelativeLayout)view.findViewById(R.id.rlayout_myteam);

        rcviewConn  =       (RecyclerView)view.findViewById(R.id.rview_connections);
        rcviewTeam  =       (RecyclerView)view.findViewById(R.id.rcview_myteam);

        upDownToggle    =   (ImageView)view.findViewById(R.id.upDown);


        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet));



            // Capturing the callbacks for bottom sheet
                    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(View bottomSheet, int newState) {

                            /*if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                                bottomSheetHeading.setText(getString(R.string.text_collapse_me));
                            } else {
                                bottomSheetHeading.setText(getString(R.string.text_expand_me));
                            }*/

                            // Check Logs to see how bottom sheets behaves
                            switch (newState) {
                                case BottomSheetBehavior.STATE_COLLAPSED:
                                    Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                                    upDownToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                                    break;
                                case BottomSheetBehavior.STATE_DRAGGING:
                                    Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                                    break;
                                case BottomSheetBehavior.STATE_EXPANDED:
                                    Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                                    upDownToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                                    break;
                                case BottomSheetBehavior.STATE_HIDDEN:
                                    Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                                    break;
                                case BottomSheetBehavior.STATE_SETTLING:
                                    Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                                    break;
                            }
                        }


                        @Override
                        public void onSlide(View bottomSheet, float slideOffset) {

                        }
                    });
    }





    public List<PersonModel> createDummyData() {

        List<PersonModel> personlList = new ArrayList<>();
        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));
        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));


        return personlList;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case R.id.btnExpand:
                if(BottomSheetBehavior.STATE_EXPANDED==3){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
*/

            default:
                break;
        }
    }






}
