package com.beachpartnerllc.beachpartner.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.ApiService;

import static android.app.Activity.RESULT_OK;


public class FeedBackFragment extends Fragment {
	private final static int RC_FILE_CHOOSER = 1;
	private WebView webview;
	private ProgressBar progressBar;
	private ValueCallback<Uri[]> filePathCallback;
	
	public FeedBackFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == RC_FILE_CHOOSER) {
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			
			if (result != null) {
				filePathCallback.onReceiveValue(new Uri[]{result});
			} else {
				filePathCallback.onReceiveValue(null);
			}
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
		webview = view.findViewById(R.id.webviews);
		progressBar = view.findViewById(R.id.pgbars);
		return view;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if (getActivity() instanceof TabActivity) {
			TabActivity tabActivity = (TabActivity) getActivity();
			tabActivity.setActionBarTitle("Feedback");
		}
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl(ApiService.FEEDBACK_PAGE);
		progressBar.setMax(100);
		progressBar.setProgress(1);
		
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				progressBar.setProgress(progress);
			}
			
			
			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
				FeedBackFragment.this.filePathCallback = filePathCallback;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				startActivityForResult(Intent.createChooser(i, "File Chooser"), RC_FILE_CHOOSER);
				return true;
			}
		});
		
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
				
				
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
			}
		});
		
		progressBar.setVisibility(View.INVISIBLE);
	}
}