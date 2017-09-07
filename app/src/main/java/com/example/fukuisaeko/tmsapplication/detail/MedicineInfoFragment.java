package com.example.fukuisaeko.tmsapplication.detail;


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

import com.example.fukuisaeko.tmsapplication.R;

/**
 * Created by saeko on 9/6/2017.
 */

public class MedicineInfoFragment extends Fragment {
    private String infoUrl;

    public MedicineInfoFragment() {

    }

    public MedicineInfoFragment(String infoUrl) {
        super();
        this.infoUrl = infoUrl;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medicineinfo, container, false);
        WebView wv = (WebView) v.findViewById(R.id.webPage);
        //"https://www.drugs.com/international/aimix-hd.html"
        wv.loadUrl(infoUrl);
        return v;
    }
}
