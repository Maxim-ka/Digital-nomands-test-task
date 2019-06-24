package com.reschikov.digitalnomands.testtask.view.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.google.android.material.button.MaterialButton;
import com.reschikov.digitalnomands.testtask.R;
import com.reschikov.digitalnomands.testtask.Rule;
import com.reschikov.digitalnomands.testtask.di.AppDagger;
import com.reschikov.digitalnomands.testtask.model.data.Article;
import com.reschikov.digitalnomands.testtask.presenter.Bindable;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter {

	private final Bindable bindable;

	@Inject Context context;
	@Inject	ViewPreloadSizeProvider<Article> preloadSizeProvider;

	public NewsListAdapter(Bindable bindable) {
		this.bindable = bindable;
		AppDagger.getAppComponent().inject(this);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
		NewsViewHolder viewHolder = new NewsViewHolder(view, bindable);
		preloadSizeProvider.setView(viewHolder.imageView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		NewsViewHolder viewHolder = (NewsViewHolder) holder;
		Article article = bindable.bindView(viewHolder, position);
		String url = article.getUrlToImage();
		Glide.with(context)
			.load(url)
			.error(android.R.drawable.ic_dialog_alert)
			.fitCenter()
			.into(viewHolder.imageView);
	}

	@Override
	public int getItemCount() {
		return bindable.getItemCount();
	}

	public static class NewsViewHolder extends RecyclerView.ViewHolder implements Displayed{

		@BindView(R.id.image) ImageView imageView;
		@BindView(R.id.headline) TextView nameView;
		@BindView(R.id.description) TextView textView;
		@BindView(R.id.date) TextView dateView;
		@BindView(R.id.repeat) MaterialButton repeatButton;

		private final Bindable bindable;

		NewsViewHolder(@NonNull View itemView, Bindable bindable) {
			super(itemView);
			this.bindable = bindable;
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(v -> this.bindable.selectItem(getAdapterPosition()));
		}

		@Override
		public void show(String name, String text, String date) {
			nameView.setText(name);
			textView.setText(text);
			if (date.equals(Rule.REPEAT)){
				dateView.setVisibility(View.INVISIBLE);
				repeatButton.setVisibility(View.VISIBLE);
				repeatButton.setText(date);
				repeatButton.setOnClickListener(v -> bindable.repeatDownload());
			} else {
				if (repeatButton.getVisibility() == View.VISIBLE){
					repeatButton.setVisibility(View.INVISIBLE);
					repeatButton.setOnClickListener(null);
					dateView.setVisibility(View.VISIBLE);
				}
				dateView.setText(date);
			}
		}
	}
}
