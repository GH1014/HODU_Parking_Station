package com.example.ev_android.ui.cctv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ev_android.R;
import com.example.ev_android.databinding.FragmentCctvBinding;

public class CctvFragment extends Fragment {

    private FragmentCctvBinding binding;
    private WebView webView;
    private WebSettings webSettings;

    private Button webButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCctvBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        webView = (WebView) root.findViewById(R.id.cctvWeb);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;}" +
                " img{width:100%25;} div{overflow: hidden;} </style></head><body><div><img src='http://192.168.0.100:8090/?action=stream'/></div></body></html>" ,"text/html",  "UTF-8");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}