package com.reschikov.digitalnomands.testtask.view.fragments.glide;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.reschikov.digitalnomands.testtask.di.AppDagger;
import com.reschikov.digitalnomands.testtask.model.data.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ImagePreLoader implements ListPreloader.PreloadModelProvider<Article>, Alterable {

	private final List<Article> list = new ArrayList<>();

	@Inject Context context;

	public ImagePreLoader() {
		AppDagger.getAppComponent().inject(this);
	}

	@NonNull
	@Override
	public List<Article> getPreloadItems(int position) {
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		Article article = list.get(position);
		return Collections.singletonList(article);
	}

	@Nullable
	@Override
	public RequestBuilder<?> getPreloadRequestBuilder(@NonNull Article article) {
		return Glide.with(context)
			.load(article.getUrlToImage())
			.error(android.R.drawable.ic_dialog_alert)
			.fitCenter();
	}

	@Override
	public void toChangeListData(List<Article> articles) {
		if (!list.isEmpty()) list.clear();
		list.addAll(articles);
	}
}
