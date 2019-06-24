package com.reschikov.digitalnomands.testtask.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.reschikov.digitalnomands.testtask.model.data.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface ArticleDao {

	@Query("SELECT * FROM table_articles WHERE id BETWEEN :min AND :max")
	Single<List<Article>> getListArticle(int min, int max);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<Article> articles);
}
