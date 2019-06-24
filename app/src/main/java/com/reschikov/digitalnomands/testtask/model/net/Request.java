package com.reschikov.digitalnomands.testtask.model.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.reschikov.digitalnomands.testtask.model.data.Reply;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Request {

	private static final String Q = "android";
	private static final String FROM = "2019-04-00";
	private static final String SORT_BY = "publishedAt";
	private static final String KEY = "26eddb253e7840f988aec61f2ece2907";

	@Inject Derivable derivable;
	@Inject ConnectivityManager connectivityManager;

	public Single<Response<Reply>> toRequest(int page){
		return (derivable.getListNews(Q, FROM, SORT_BY, KEY, page)).subscribeOn(Schedulers.io());
	}

	public Single<Boolean> hasInternetConnection(){
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return Single.just(networkInfo != null && networkInfo.isConnected()).subscribeOn(Schedulers.io());
	}
}
