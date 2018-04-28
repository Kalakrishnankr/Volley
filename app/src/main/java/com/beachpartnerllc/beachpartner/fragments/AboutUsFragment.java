package com.beachpartnerllc.beachpartner.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.ApiService;


public class AboutUsFragment extends Fragment {

    private WebView webview;
    private ProgressBar progressBar;
    private TabActivity  tabActivity;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_about_us, container, false);

        webview     = (WebView) view.findViewById(R.id.webview);
        progressBar = (ProgressBar) view.findViewById(R.id.pgbar);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof TabActivity) {
            tabActivity= (TabActivity)getActivity();
            tabActivity.setActionBarTitle("About Us");
        }

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.loadUrl(ApiService.ABOUT_US_PAGE);
        progressBar.setMax(100);
        progressBar.setProgress(1);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }
        });
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);


            }



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.GONE);
            }
        });

        webview.getSettings().setJavaScriptEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
