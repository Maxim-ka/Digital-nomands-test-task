package com.reschikov.digitalnomands.testtask.model.net;

import com.reschikov.digitalnomands.testtask.model.data.Reply;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Derivable {
	@GET("everything")
	Single<Response<Reply>> getListNews(@Query("q") String q, @Query("from") String from,
	                                   @Query("sortBy") String sortBy, @Query("apiKey") String key,
	                                   @Query("page") int page);
}
