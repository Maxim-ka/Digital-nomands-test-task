package com.reschikov.digitalnomands.testtask.model;

import android.util.Log;

import com.reschikov.digitalnomands.testtask.Rule;
import com.reschikov.digitalnomands.testtask.model.data.Article;
import com.reschikov.digitalnomands.testtask.model.data.Reply;
import com.reschikov.digitalnomands.testtask.model.database.ArticleDao;
import com.reschikov.digitalnomands.testtask.model.net.Request;
import com.reschikov.digitalnomands.testtask.presenter.Observer;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Data {

	private Observer observer;

	@Inject ArticleDao articleDao;
	@Inject Request request;

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	public void loadNews(int page){
		loadFromBase(page);
	}
	
	private void loadFromSever(int page){
		Disposable disposable = request.toRequest(page)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> {
					if (response.isSuccessful() && response.body() != null){
						Reply reply = response.body();
						observer.add(reply.getArticles());
						insertListArticles(reply.getArticles());
					} else if (response.errorBody() != null){
						if (response.message().equals(Rule.NO_CONNECTION)) observer.stopLoading();
						else observer.toFinish();
						Log.i("loadNews: eb", response.errorBody().string());
					}
				},
				e -> {
					Log.e("loadNews: e", e.getMessage());
					observer.stopLoading();
				});
	}

	public void checkInternetConnection(int page){
		Disposable disposable = request.hasInternetConnection()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(aBoolean -> {
				if(aBoolean) loadFromSever(page);
				else observer.stopLoading();
			},
			e -> Log.e("checkInternet: e ", e.getMessage()));
	}

	private void insertListArticles(List<Article> list){
		Disposable disposable = articleDao.insert(list)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> Log.i("insertListArticles: ", "added to base"),
				e -> Log.e("insertListArticles: e", String.format("error adding to database, %s", e.getMessage())));
	}
	
	private int getMinLimit(int page){
		return page * Rule.NUMBER_ELEMENTS_PER_PAGE - Rule.NUMBER_ELEMENTS_PER_PAGE + 1;
	}

	private int getMaxLimit(int page){
		return page * Rule.NUMBER_ELEMENTS_PER_PAGE;
	}
	
	private void loadFromBase(int page){
		Disposable disposable = articleDao.getListArticle(getMinLimit(page), getMaxLimit(page))
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(articleList -> {
					if (articleList.isEmpty()) checkInternetConnection(page);
					else observer.add(articleList);
				}, e -> Log.e("loadFromBase: e", e.getMessage()));
	}
}
