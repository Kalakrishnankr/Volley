package com.beachpartnerllc.beachpartner.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.ApiService;

import static android.app.Activity.RESULT_OK;


public class FeedBackFragment extends Fragment {
	private final static int RC_FILE_CHOOSER = 1;
	private static final String TAG ="FeedBAckFragment" ;
	private WebView webview;
	private ProgressBar progressBar;
	private ValueCallback<Uri[]> filePathCallback;
	private FrameLayout topFrame;

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
		//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
		webview = view.findViewById(R.id.webviews);
		progressBar = view.findViewById(R.id.pgbars);
		topFrame = view.findViewById(R.id.topFrame);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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

		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int height = 0;
				int conditionHeight = 0;
				if(view.getRootView().getHeight()>1920 && view.getRootView().getHeight()<=2220){
					height = 900;
					conditionHeight =500;
				}
				if(view.getRootView().getHeight()>1280 && view.getRootView().getHeight()<=1920){
					height = 800;
					conditionHeight =500;
				}
				else if(view.getRootView().getHeight()>800 && view.getRootView().getHeight()<=1280){
					height = 450;
					conditionHeight =400;
				}
				else if(view.getRootView().getHeight()<=800){
					height = 280;
					conditionHeight =100;

				}
				Rect r = new Rect();
				view.getWindowVisibleDisplayFrame(r);
				if (view.getRootView().getHeight() - (r.bottom - r.top) > conditionHeight) { // if more than 100 pixels, its probably a keyboard...
					//onKeyboardShow()
					view.scrollTo(0,webview.getHeight()-height);
				} else {
					Log.d(TAG, "Keyboard Closed");
					view.scrollTo(0, 0);
				}
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