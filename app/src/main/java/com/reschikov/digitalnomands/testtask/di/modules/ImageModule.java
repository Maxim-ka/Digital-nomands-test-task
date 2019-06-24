package com.reschikov.digitalnomands.testtask.di.modules;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.reschikov.digitalnomands.testtask.model.data.Article;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {

	@Singleton
	@Provides
	ViewPreloadSizeProvider<Article> provideImageUploader(){
		return new ViewPreloadSizeProvider<>();
	}
}
