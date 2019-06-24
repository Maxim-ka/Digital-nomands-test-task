package com.reschikov.digitalnomands.testtask.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.reschikov.digitalnomands.testtask.model.data.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
	public abstract ArticleDao getArticleDao();
}
