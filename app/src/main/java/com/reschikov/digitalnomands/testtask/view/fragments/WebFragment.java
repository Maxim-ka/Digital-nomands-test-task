package com.reschikov.digitalnomands.testtask.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reschikov.digitalnomands.testtask.R;

public class WebFragment extends Fragment {

	private static final String KEY_URL = "url";

	public static WebFragment newInstance(String url){
		WebFragment fragment = new WebFragment();
		Bundle args = new Bundle();
		args.putString(KEY_URL, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		WebView webView = (WebView) inflater.inflate(R.layout.fragment_web, container, false);
		if (getArguments() != null){
			String url = getArguments().getString(KEY_URL);
			webView.loadUrl(url);
		}
		return webView;
	}
}
