package com.goldemo.beachpartner.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.ConnectionAdapter;
import com.goldemo.beachpartner.models.DataModel;
import com.goldemo.beachpartner.models.SingleItemModel;

import java.util.ArrayList;




public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rcv_conn;
    private ConnectionAdapter adapter;
    private TextView txtv_coach,txtv_athlete;
    ArrayList<DataModel> allSampleData;

    public ConnectionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        initActivity(view);

        return view;


    }

    private void initActivity(View view) {

        allSampleData = new ArrayList<DataModel>();
        createDummyData();


        rcv_conn    =   (RecyclerView)view.findViewById(R.id.rcv_connection);
        txtv_athlete=   (TextView)view.findViewById(R.id.txtAthlete);
        txtv_coach  =   (TextView)view.findViewById(R.id.txtCoach);
        adapter     =   new ConnectionAdapter(getContext(),allSampleData);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcv_conn.setLayoutManager(layoutManager);
        rcv_conn.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rcv_conn.setItemAnimator(new DefaultItemAnimator());
        rcv_conn.setAdapter(adapter);


        activeAthletTab();

        txtv_athlete.setOnClickListener(this);
        txtv_coach.setOnClickListener(this);

    }

    // Method for Active Coach Tab
    private void activeCoachTab() {
        txtv_coach.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_coach.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_athlete.setBackgroundColor(0);
        txtv_athlete.setTextColor(getResources().getColor(R.color.white));

    }

    // Method Active Athlete TAb
    private void activeAthletTab() {

        txtv_athlete.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_athlete.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_coach.setBackgroundColor(0);
        txtv_coach.setTextColor(getResources().getColor(R.color.white));
    }


    private void createDummyData() {
        for (int i = 1; i <= 20; i++) {

            DataModel dm = new DataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 20; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.txtAthlete:
                activeAthletTab();
                break;

            case R.id.txtCoach:
                activeCoachTab();
                break;

                default:
                    break;


        }

    }


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
            int column = position % spanCount; // item column

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
}
