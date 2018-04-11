package com.goldemo.beachpartner.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.goldemo.beachpartner.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment  implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView webViewRegistration;
    String Url;
    private ImageView closeButton;
    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private int progressStatus=0;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Url= getArguments().getString("Url");
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        init(view);
        renderWebPage(Url);


        closeButton.setOnClickListener(this);
        return view;
    }

    private void init(View view){
        webViewRegistration =   view.findViewById(R.id.registration_webView);
        closeButton =view.findViewById(R.id.close_btn);
        progressBar =view.findViewById(R.id.progressBar1);



    }


    protected void renderWebPage(String urlToRender){
        webViewRegistration.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished

            }

        });

        /*
            WebView
                A View that displays web pages. This class is the basis upon which you can roll your
                own web browser or simply display some online content within your Activity. It uses
                the WebKit rendering engine to display web pages and includes methods to navigate
                forward and backward through a history, zoom in and out, perform text searches and more.

            WebChromeClient
                 WebChromeClient is called when something that might impact a browser UI happens,
                 for instance, progress updates and JavaScript alerts are sent here.
        */
        webViewRegistration.setWebChromeClient(new WebChromeClient(){
            /*
                public void onProgressChanged (WebView view, int newProgress)
                    Tell the host application the current progress of loading a page.

                Parameters
                    view : The WebView that initiated the callback.
                    newProgress : Current page loading progress, represented by an integer
                        between 0 and 100.
            */
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
//                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Enable the javascript
        webViewRegistration.getSettings().setJavaScriptEnabled(true);
        // Render the web page
        webViewRegistration.loadUrl(urlToRender);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_btn:
                getActivity().onBackPressed();
                break;


            default:
                break;
        }

    }





}
