package com.reschikov.digitalnomands.testtask.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reply {

	@SerializedName("articles")
	@Expose
	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}
}
