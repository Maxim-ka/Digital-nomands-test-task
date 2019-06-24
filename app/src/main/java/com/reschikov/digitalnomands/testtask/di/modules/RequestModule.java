package com.reschikov.digitalnomands.testtask.di.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import com.reschikov.digitalnomands.testtask.Rule;
import com.reschikov.digitalnomands.testtask.di.AppDagger;
import com.reschikov.digitalnomands.testtask.model.net.Derivable;
import com.reschikov.digitalnomands.testtask.model.net.Request;
import com.reschikov.digitalnomands.testtask.model.net.ResponseInterceptor;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RequestModule {

	@Singleton
	@Provides
	ConnectivityManager connectivityManagerProvide(Context context){
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	@Singleton
	@Provides
	OkHttpClient providerOkHttpClient(){
		return new OkHttpClient.Builder()
			.addInterceptor(new ResponseInterceptor())
			.build();
	}

	@Singleton
	@Provides
	Retrofit provideRetrofit(){
		return new Retrofit.Builder()
			.baseUrl(Rule.BASE_URL)
			.client(providerOkHttpClient())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	}

	@Singleton
	@Provides
	Derivable provideDerivable(Retrofit retrofit){
		return retrofit.create(Derivable.class);
	}

	@Singleton
	@Provides
	Request provideRequest(){
		Request request = new Request();
		AppDagger.getAppComponent().inject(request);
		return request;
	}
}
