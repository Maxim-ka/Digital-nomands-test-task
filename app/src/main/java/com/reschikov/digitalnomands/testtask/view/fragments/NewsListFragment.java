package com.reschikov.digitalnomands.testtask.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.reschikov.digitalnomands.testtask.R;
import com.reschikov.digitalnomands.testtask.Rule;
import com.reschikov.digitalnomands.testtask.di.AppDagger;
import com.reschikov.digitalnomands.testtask.model.data.Article;
import com.reschikov.digitalnomands.testtask.presenter.NewsListPresenter;
import com.reschikov.digitalnomands.testtask.view.Switchable;
import com.reschikov.digitalnomands.testtask.view.fragments.adapters.NewsListAdapter;
import com.reschikov.digitalnomands.testtask.view.fragments.glide.ImagePreLoader;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsListFragment extends MvpAppCompatFragment implements Updated {

	private NewsListAdapter adapter;
	private Switchable switchable;
	private Unbinder unbinder;
	private final ImagePreLoader preLoader = new ImagePreLoader();
	private final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
	private final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
			if (dy < 0){
				presenter.getFirstVisiblePosition(layoutManager.findFirstVisibleItemPosition());
			} else
			if (dy > 0){
				presenter.getLastVisiblePosition(layoutManager.findLastVisibleItemPosition());
			}
		}
	};

	@BindView(R.id.recycler) RecyclerView recyclerView;

	@Inject Context context;
	@Inject	ViewPreloadSizeProvider<Article> preloadSizeProvider;

	@InjectPresenter NewsListPresenter presenter;


	@ProvidePresenter
	NewsListPresenter providePresenter(){
		NewsListPresenter presenter = new NewsListPresenter();
		AppDagger.getAppComponent().inject(presenter);
		presenter.init(preLoader);
		return presenter;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_list_news, container, false);
		unbinder = ButterKnife.bind(this, view);
		AppDagger.getAppComponent().inject(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new NewsListAdapter(presenter.getRecyclePresenter());
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.addOnScrollListener(scrollListener);
		recyclerView.addOnScrollListener(new RecyclerViewPreloader<>(Glide.with(context), preLoader, preloadSizeProvider, Rule.NUMBER_ELEMENTS_PER_PAGE));
		return view;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		switchable = (Switchable) context;
	}

	@Override
	public void update() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void scrollDown(int position) {
		adapter.notifyDataSetChanged();
		layoutManager.scrollToPositionWithOffset(position, recyclerView.getTop());
	}

	@Override
	public void scrollUp(int position) {
		adapter.notifyDataSetChanged();
		layoutManager.scrollToPositionWithOffset(position, recyclerView.getBottom());
	}

	@Override
	public void displayMessage(int position) {
		adapter.notifyItemInserted(position);
	}

	@Override
	public void displayMessage() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void goToNewsPage(String url) {
		switchable.toggleFragments(url);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
