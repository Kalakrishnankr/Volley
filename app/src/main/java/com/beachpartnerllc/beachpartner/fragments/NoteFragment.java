package com.beachpartnerllc.beachpartner.fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.NotesAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.NoteDataModel;
import com.beachpartnerllc.beachpartner.utils.SaveNoteInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NoteFragment extends Fragment implements SaveNoteInterface{


    private RecyclerView rcVNotes;
    private NotesAdapter adapter;
    private ArrayList<NoteDataModel> noteList = new ArrayList<>();
    private Button addNewBtn;
    private String personId,myID,personName,myToken;
    private ProgressBar progressBar;
    private TextView txtv_nonotes;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog dialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (bundle != null) {
            personId  = bundle.getString("personId");
            personName= bundle.getString("personName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_note, container, false);
        initViews(view);

        myID    = new PrefManager(getContext()).getUserId();
        myToken = new PrefManager(getContext()).getToken();
        //Get all notes for a particular person;
        noteList.clear();
        getNotes();

        return view;
    }


    private void initViews(View view) {

        rcVNotes    = view.findViewById(R.id.rcv_notes);
        addNewBtn   = view.findViewById(R.id.addNew);
        //progressBar = view.findViewById(R.id.progress_note);
        txtv_nonotes = view.findViewById(R.id.txtv_nonotes);

        dialog      = new ProgressDialog(getContext(),ProgressDialog.THEME_HOLO_DARK);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // set title and message
        dialog.setTitle("Please wait");
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);


       // adapter     =   new NotesAdapter(getContext(),noteList,this);
        layoutManager = new GridLayoutManager(getContext(),1);
        rcVNotes.setLayoutManager(layoutManager);
        rcVNotes.addItemDecoration(new NoteFragment.GridSpacingItemDecoration(1, dpToPx(10), true));
        rcVNotes.setItemAnimator(new DefaultItemAnimator());

        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteList.clear();
                createNote();

                //Toast.makeText(getActivity(), "List"+noteList.size(), Toast.LENGTH_SHORT).show();
                //createDummyData();



            }
        });
    }


    private void createNote() {
        JSONObject object = new JSONObject();
        try {
            object.put("note"," ");
            object.put("toUserId",personId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*NoteDataModel dm = new NoteDataModel();
        dm.setHeaderTitle("");
        dm.setNotes("");
        noteList.add(dm);*/
        dialog.show();
        postNote(object);

    }


    /*private void createDummyData() {

        long currentTime;
        NoteDataModel dm = new NoteDataModel();
        dm.setHeaderTitle("");
        dm.setNotes("");
        currentTime  = System.currentTimeMillis();
        dm.setTimestamp(new Date().getTime());
        noteList.add(dm);
    }*/

    //Api for get all notes
    private void getNotes() {
        noteList.clear();
        //progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GETALL_NOTE_FROM + myID + "/to/" + personId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("getNoteResponse",response.toString());
                        if (response != null ) {
                            for(int i=0;i<response.length();i++){
                                try {
                                    txtv_nonotes.setVisibility(View.GONE);
                                    JSONObject object = response.getJSONObject(i);
                                    NoteDataModel noteDataModel = new NoteDataModel();
                                    noteDataModel.setNote_id(object.getString("id"));
                                    noteDataModel.setNotes(object.getString("note"));
                                    noteList.add(0,noteDataModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setNoteViews();
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
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myToken);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("responseGetNote",requestQueue.toString());
        requestQueue.add(arrayRequest);

    }

    private void setNoteViews() {
        dialog.cancel();
        //progressBar.setVisibility(View.GONE);
        if(noteList.size()>0){
            adapter  = new NotesAdapter(getContext(),noteList,this);
            rcVNotes.setAdapter(adapter);
            rcVNotes.setLayoutManager(layoutManager);
            rcVNotes.getLayoutManager().scrollToPosition(noteList.size()-1);
            adapter.notifyDataSetChanged();
        }else {
            txtv_nonotes.setVisibility(View.VISIBLE);
        }
    }

    //Method for creating a note


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void save(String text) {


    }



    //Method for post note
    private void postNote(JSONObject object) {
        //noteList.clear();
        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.CREATE_NOTE, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respone",response.toString());
                if (response != null) {
                    getNotes();
                    Toast.makeText(getActivity(), "Note Created", Toast.LENGTH_SHORT).show();

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
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myToken);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.d("requestCreateNote",queue.toString());
        queue.add(request);

    }

    @Override
    public void removeNote(String note_id) {
        //Api for deleting note
        //progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_DELETE, ApiService.DELETE_NOTE + note_id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("responseDelete",response.toString());
                        if (response != null) {
                            if(getActivity()!=null){
                                getNotes();
                                Toast.makeText(getActivity(), "Note deleted Successfully", Toast.LENGTH_SHORT).show();
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
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myToken);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.d("deleteNoteRequest",queue.toString());
        queue.add(jsonObjectRequest);
    }

    @Override
    public void update(String text,String noteId) {
        JSONObject object = new JSONObject();
        try {
            object.put("note",text);
            object.put("toUserId",personId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateNote(object,noteId);
    }
    //Update  note Api
    private void updateNote(JSONObject object, String noteId) {
        dialog.show();
        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_PUT, ApiService.UPDATE_NOTE + noteId, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    if(getActivity()!=null){
                        dialog.cancel();
                        Toast.makeText(getActivity(), "Successfully Updated Note", Toast.LENGTH_SHORT).show();
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
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myToken);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.d("updateNoteRequest",queue.toString());
        queue.add(objectRequest);
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
}
