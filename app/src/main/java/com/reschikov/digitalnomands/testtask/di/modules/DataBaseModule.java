package com.reschikov.digitalnomands.testtask.di.modules;

import android.content.Context;
import androidx.room.Room;

import com.reschikov.digitalnomands.testtask.di.AppDagger;
import com.reschikov.digitalnomands.testtask.model.Data;
import com.reschikov.digitalnomands.testtask.model.database.ArticleDao;
import com.reschikov.digitalnomands.testtask.model.database.DataBase;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

	@Singleton
	@Provides
	DataBase provideDataBase(Context context){
		return Room.databaseBuilder(context, DataBase.class, "Articles").build();
	}

	@Singleton
	@Provides
	ArticleDao provideArticleDao(DataBase dataBase){
		return dataBase.getArticleDao();
	}

	@Singleton
	@Provides
	Data provideData(){
		Data data = new Data();
		AppDagger.getAppComponent().inject(data);
		return data;
	}
}
