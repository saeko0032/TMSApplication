package com.example.fukuisaeko.tmsapplication;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

/**
 * Created by saeko on 9/6/2017.
 */

public class MedicineInfoFragment extends WebViewFragment {

    private WebView mWebView;
    private boolean mIsWebViewAvailable;
    private String mUrl = null;

    public MedicineInfoFragment(){
    }

    public MedicineInfoFragment(String url){
        super();
        mUrl = url;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(getActivity());
        mWebView.setOnKeyListener(new View.OnKeyListener(){


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }

        });
        mWebView.setWebViewClient(new InnerWebViewClient()); // forces it to open in app
//       // View result = inflater.inflate(R.layout.fragment_sample,container,false);
//        View result = super.onCreateView(inflater,container,savedInstanceState);
//        WebView webview = getWebView();
//        webview.loadUrl("https://www.shionogi.co.jp/med/zaikei/drug_a/qdv9fu000000ay3a.html");
//        webview.getSettings().setJavaScriptEnabled(true);
//
//        return result;
        mWebView.setWebViewClient(new InnerWebViewClient()); // forces it to open in app
        mWebView.loadUrl(mUrl);
        mIsWebViewAvailable = true;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        return mWebView;
    }

    public void loadUrl(String url) {
        if (mIsWebViewAvailable) getWebView().loadUrl(mUrl = url);
        else Log.w("ImprovedWebViewFragment", "WebView cannot be found. Check the view and fragment have been loaded.");
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }

    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
