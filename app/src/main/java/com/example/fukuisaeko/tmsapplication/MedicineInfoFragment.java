package com.example.fukuisaeko.tmsapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

/**
 * Created by saeko on 9/6/2017.
 */

public class MedicineInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_sample,container,false);
//        WebView webview = getWebView();
//        webview.loadUrl("https://www.shionogi.co.jp/med/zaikei/drug_a/qdv9fu000000ay3a.html");
//        webview.getSettings().setJavaScriptEnabled(true);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
