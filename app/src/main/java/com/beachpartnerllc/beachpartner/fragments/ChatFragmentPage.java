package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class ChatFragmentPage extends Fragment {

    private ImageView submitButton, emoji_btn;
    private EmojiconEditText emojicon_editText;
    private EmojIconActions emojIcon;
    private LinearLayout rootview;
    private ScrollView scrollview;
    Firebase reference1, ref;
    private String myId, ChatWith_id, ChatWith_name, myName, chatPicture;
    private int idLeft, idRight;
    private Date currentTime;
    TabActivity tabActivity;
    private static final String TAG = "ChatFragmentPage";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            Firebase.setAndroidContext(getActivity());

        }
        BpFinderModel bpFinderModel = null;
        if (getArguments() != null) {
            bpFinderModel = getArguments().getParcelable(AppConstants.CHAT_USER);
            if (bpFinderModel != null) {
                ChatWith_id    = String.valueOf(bpFinderModel.getBpf_id());
                ChatWith_name  = bpFinderModel.getBpf_firstName();
                myName = new PrefManager(getActivity()).getUserName();
                chatPicture = bpFinderModel.getBpf_imageUrl();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentTime = Calendar.getInstance().getTime();
        final Firebase myFirebaseRef = new Firebase("https://beachpartner-6cd7a.firebaseio.com/users");
        ref = myFirebaseRef.child("users");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_page, container, false);
        getConnections();
        initView(view);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity) {
            tabActivity = (TabActivity) getActivity();
            tabActivity.setActionBarTitle("Chat with " + ChatWith_name);
        }
    }

    private void initView(View view) {
        submitButton    = (ImageView) view.findViewById(R.id.sendButton);
        emoji_btn       = (ImageView) view.findViewById(R.id.emoji_btn);
        emojicon_editText = (EmojiconEditText) view.findViewById(R.id.emojicon_edit_text);
        rootview        = (LinearLayout) view.findViewById(R.id.layout1);
        scrollview      = (ScrollView) view.findViewById(R.id.scroll);

        emojIcon = new EmojIconActions(getActivity(), rootview, emojicon_editText, emoji_btn);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");

            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String newText = emojicon_editText.getText().toString();
                addMessageBox(newText,1);
                emojicon_editText.setText("");*/
                String messageText = emojicon_editText.getText().toString().trim();

                if (!messageText.equals("")&& !messageText.isEmpty() && messageText.length()!= 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("text", messageText);
                    map.put("sender_id", myId);
                    map.put("receiver_name", ChatWith_name);
                    map.put("receiver_id", ChatWith_id);
                    map.put("sender_name", myName);
                    map.put("profileImg", chatPicture);
                    map.put("date", String.valueOf(currentTime));
                    reference1.push().setValue(map);
                    // reference2.push().setValue(map);

                    ref.push().setValue(myId + ":" + ChatWith_id);

                    emojicon_editText.setText("");
                    scrollview.fullScroll(View.FOCUS_DOWN);
                }

            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("text").toString();
                String userId = map.get("sender_id").toString();
                //String receiverName= map.get("receiver_name").toString();
                //String receiverId = map.get("receiver_id").toString();
                //String senderName = map.get("sender_name").toString();
                //String profilePic = map.get("profileImg").toString();
                //String date = map.get("date").toString();
                if (userId.equals(myId)) {
                    //addMessageBox("You:-\n" + message, 1);
                    addMessageBox(message, 1);
                } else {
                    // addMessageBox(ChatWith_name + ":-\n" + message, 2);
                    addMessageBox(message, 2);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void getConnections() {

        myId = new PrefManager(getActivity()).getUserId();
        Firebase.setAndroidContext(getActivity());
        if (Integer.parseInt(myId) > Integer.parseInt(ChatWith_id)) {

            idLeft = Integer.parseInt(ChatWith_id);
            idRight = Integer.parseInt(myId);
        } else {

            idLeft = Integer.parseInt(myId);
            idRight = Integer.parseInt(ChatWith_id);
        }
        reference1 = new Firebase("https://beachpartner-6cd7a.firebaseio.com/messages/" + idLeft + "-" + idRight);
        //reference2 = new Firebase("https://beachpartner-be21e.firebaseio.com/messages/" + ChatWith_id + "-" + myId);
    }


    public void addMessageBox(String message, int type) {
        if (getActivity() != null) {
            final TextView textView = new TextView(getActivity());
            textView.setGravity(View.SCROLL_INDICATOR_BOTTOM);
            textView.setText(message);
            textView.setFocusable(true);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.weight = 1.0f;

            if (type == 1) {
                lp2.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                textView.setBackgroundResource(R.drawable.bubble_in);
            } else {
                lp2.gravity = Gravity.LEFT;
                textView.setBackgroundResource(R.drawable.bubble_out);
            }
            textView.setLayoutParams(lp2);
            rootview.addView(textView);
            //scrollview.fullScroll(View.FOCUS_DOWN);
            scrollview.post(new Runnable() {
                public void run() {
                    scrollview.fullScroll(View.FOCUS_DOWN);
                }
            });
        }

    }

}
