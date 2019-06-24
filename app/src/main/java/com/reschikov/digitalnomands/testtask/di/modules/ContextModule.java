package com.reschikov.digitalnomands.testtask.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

	private final Context context;

	public ContextModule(Context context) {
		this.context = context;
	}

	@Singleton
	@Provides
	Context provideContext(){
		return context;
	}
}
