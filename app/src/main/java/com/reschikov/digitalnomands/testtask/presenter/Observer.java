package com.reschikov.digitalnomands.testtask.presenter;

import com.reschikov.digitalnomands.testtask.model.data.Article;

import java.util.List;

public interface Observer {
	void add(List<Article> articles);
	void toFinish();
	void stopLoading();
}
