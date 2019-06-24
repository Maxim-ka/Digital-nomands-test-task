package com.reschikov.digitalnomands.testtask.di;

import android.app.Application;

import com.reschikov.digitalnomands.testtask.di.modules.ContextModule;
import com.reschikov.digitalnomands.testtask.di.modules.DataBaseModule;
import com.reschikov.digitalnomands.testtask.di.modules.ImageModule;
import com.reschikov.digitalnomands.testtask.di.modules.RequestModule;

public class AppDagger extends Application {

	private static AppComponent appComponent;

	public static AppComponent getAppComponent() {
		return appComponent;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appComponent = generateAppComponent();
	}

	private AppComponent generateAppComponent(){
		return DaggerAppComponent
			.builder()
			.contextModule(new ContextModule(getApplicationContext()))
			.requestModule(new RequestModule())
			.imageModule(new ImageModule())
			.dataBaseModule(new DataBaseModule())
			.build();
	}
}
