package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.connections.PrefManager;

import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static com.goldemo.beachpartner.instagram.util.Cons.TAG;


public class ChatFragmentPage extends Fragment {

    private ImageView submitButton,emoji_btn;
    private EmojiconEditText emojicon_editText;
    private EmojIconActions emojIcon;
    private LinearLayout rootview;
    private ScrollView scrollview;
    Firebase reference1, reference2,ref;
    String myId,ChatWith_id,ChatWith_name,myName;
    private int idLeft,idRight;

   /* public ChatFragmentPage() {
        // Required empty public constructor
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        Bundle bundle = this.getArguments();
        if(bundle != null){
            ChatWith_id  = bundle.getString("personId");
            ChatWith_name= bundle.getString("personName");
            myName       = bundle.getString("myName");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Message");

        final Firebase myFirebaseRef = new Firebase("https://beachpartner-be21e.firebaseio.com/users");
        ref = myFirebaseRef.child("users");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat_page, container, false);
        getConnections();
        initView(view);

        return view;
    }



    private void initView(View view) {
        submitButton    =   (ImageView) view.findViewById(R.id.sendButton);
        emoji_btn       =   (ImageView) view.findViewById(R.id.emoji_btn);
        emojicon_editText =   (EmojiconEditText) view.findViewById(R.id.emojicon_edit_text);
        rootview        =   (LinearLayout) view.findViewById(R.id.layout1) ;
        scrollview      =   (ScrollView) view.findViewById(R.id.scroll) ;

        emojIcon    = new EmojIconActions(getActivity(),rootview,emojicon_editText,emoji_btn);
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
                String messageText = emojicon_editText.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("text", messageText);
                    map.put("sender_id", myId);
                    map.put("receiver_name",ChatWith_name);
                    map.put("receiver_id",ChatWith_id);
                    map.put("sender_name",myName);
                    reference1.push().setValue(map);
                   // reference2.push().setValue(map);

                    ref.push().setValue(myId+":"+ChatWith_id);

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
                String userId  = map.get("sender_id").toString();
                String receiverName= map.get("receiver_name").toString();
                String receiverId = map.get("receiver_id").toString();
                String senderName = map.get("sender_name").toString();
                if(userId.equals(myId)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(ChatWith_id + ":-\n" + message, 2);
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

        myId = new PrefManager(getContext()).getUserId();
        Firebase.setAndroidContext(getActivity());
        if(Integer.parseInt(myId) >Integer.parseInt(ChatWith_id)){

            idLeft = Integer.parseInt(ChatWith_id);
            idRight = Integer.parseInt(myId);
        }else {

            idLeft = Integer.parseInt(myId);
            idRight = Integer.parseInt(ChatWith_id);
        }
        reference1 = new Firebase("https://beachpartner-be21e.firebaseio.com/messages/" + idLeft + "-" + idRight);
        //reference2 = new Firebase("https://beachpartner-be21e.firebaseio.com/messages/" + ChatWith_id + "-" + myId);
    }


    public void addMessageBox(String message, int type){
        if(getActivity()!=null){
            TextView textView = new TextView(getActivity());
            textView.setText(message);
            textView.setFocusable(true);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.weight = 1.0f;

            if(type == 1) {
                lp2.gravity = Gravity.RIGHT;
                textView.setBackgroundResource(R.drawable.bubble_in);
            }
            else{
                lp2.gravity = Gravity.LEFT;
                textView.setBackgroundResource(R.drawable.bubble_out);
            }
            textView.setLayoutParams(lp2);
            rootview.addView(textView);
            scrollview.fullScroll(View.FOCUS_DOWN);
        }

    }

}
