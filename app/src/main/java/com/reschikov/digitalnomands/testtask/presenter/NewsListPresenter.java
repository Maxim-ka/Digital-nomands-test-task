package com.reschikov.digitalnomands.testtask.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.reschikov.digitalnomands.testtask.Rule;
import com.reschikov.digitalnomands.testtask.model.Data;
import com.reschikov.digitalnomands.testtask.model.data.Article;
import com.reschikov.digitalnomands.testtask.view.fragments.Updated;
import com.reschikov.digitalnomands.testtask.view.fragments.adapters.Displayed;
import com.reschikov.digitalnomands.testtask.view.fragments.glide.Alterable;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<Updated> implements Observer{

	private static final int NUMBER_PAGES_IN_MEMORY = 2;
	private final RecyclePresenter recyclePresenter = new RecyclePresenter();
	private final LinkedList<Article> list = new LinkedList<>();
	private int page;
	private boolean isWayDown;
	private boolean isUp;
	private boolean isFinish;
	private boolean inSecondPartList;
	private Article article;
	private Alterable alterable;

	@Inject	Data data;

	public void init(Alterable alterable){
		data.setObserver(this);
		data.loadNews(++page);
		this.alterable = alterable;
	}

	public RecyclePresenter getRecyclePresenter() {
		return recyclePresenter;
	}

	public void getLastVisiblePosition(int position){
		if (isWayDown && position >= (list.size() - 1) - Rule.PREFETCHING){
			isWayDown = false;
			article = list.get(position);
			checkPageNumber(position);
			if (!inSecondPartList) inSecondPartList = true;
			data.loadNews(++page);
		}
	}

	private void checkPageNumber(int position){
		if (list.size() / Rule.NUMBER_ELEMENTS_PER_PAGE == NUMBER_PAGES_IN_MEMORY){
			if (position >= Rule.NUMBER_ELEMENTS_PER_PAGE && !inSecondPartList) ++page;
			if (position < Rule.NUMBER_ELEMENTS_PER_PAGE && inSecondPartList) --page;
		}
	}

	public void getFirstVisiblePosition(int position){
		if (isUp && position <= Rule.PREFETCHING){
			isUp = false;
			if (page - 1 != 0){
				article = list.get(position);
				checkPageNumber(position);
				if (inSecondPartList) inSecondPartList = false;
				data.loadNews(--page);
			}
		}
	}

	private void addEnd(List<Article> articles){
		list.addAll(articles);
		if (list.size() > NUMBER_PAGES_IN_MEMORY * Rule.NUMBER_ELEMENTS_PER_PAGE) {
			for (int i = 0; i < articles.size(); i++) {
				list.removeFirst();
			}
			if (!isUp) isUp = true;
			getViewState().scrollUp(list.indexOf(article));
		} else getViewState().update();
		alterable.toChangeListData(list);
	}

	private void addStart(List<Article> articles){
		for (int i = articles.size() - 1; i >= 0; i--) {
			list.addFirst(articles.get(i));
		}
		if (list.size() > NUMBER_PAGES_IN_MEMORY * Rule.NUMBER_ELEMENTS_PER_PAGE){
			for (int i = 0; i < articles.size(); i++) {
				list.removeLast();
			}
			if (isFinish) {
				isFinish = false;
				isWayDown = true;
			}
		}
		getViewState().scrollDown(list.lastIndexOf(article));
		alterable.toChangeListData(list);
	}

	private boolean checkReload(){
		return !list.isEmpty() && list.get(list.size() - 1).getPublishedAt().equals(Rule.REPEAT);
	}

	@Override
	public void add(List<Article> articles) {
		if (checkReload()) list.removeLast();
		if (!isWayDown && !isFinish){
			addEnd(articles);
			isWayDown = !isWayDown;
			return;
		}
		if (!isUp){
			addStart(articles);
			isUp = !isUp;
		}
	}

	@Override
	public void toFinish() {
		isFinish = true;
		--page;
	}

	@Override
	public void stopLoading() {
		if (checkReload()) return;
		Article article = new Article();
		article.setDescription(Rule.NO_CONNECTION);
		article.setPublishedAt(Rule.REPEAT);
		list.addLast(article);
		alterable.toChangeListData(list);
		if (list.size() == 1) getViewState().displayMessage();
		else getViewState().displayMessage(list.size() - 1);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		data.setObserver(null);
	}

	private class RecyclePresenter implements Bindable {

		@Override
		public Article bindView(Displayed displayed, int position) {
			Article article = list.get(position);
			displayed.show(article.getTitle(), article.getDescription(), article.getPublishedAt());
			return article;
		}

		@Override
		public int getItemCount() {
			return list.size();
		}

		@Override
		public void selectItem(int position) {
			Article article = list.get(position);
			if (!checkReload()) getViewState().goToNewsPage(article.getUrl());

		}

		@Override
		public void repeatDownload() {
			data.checkInternetConnection(page);
		}
	}
}
