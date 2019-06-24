package com.reschikov.digitalnomands.testtask.model.net;

import android.util.Log;
import com.reschikov.digitalnomands.testtask.Rule;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {

	private static final int CODE = 524;

	@NotNull
	@Override
	public Response intercept(@NotNull Interceptor.Chain chain) {
		Request request = chain.request();
		try {
			return chain.proceed(request);
		}catch (UnknownHostException e){
			Log.e("intercept: uhE", e.getMessage());

		}catch (IOException e){
			Log.e("intercept: ioE", e.getMessage());
		}
		return new Response.Builder()
			.request(request)
			.protocol(Protocol.HTTP_1_1)
			.code(CODE)
			.message(Rule.NO_CONNECTION)
			.body(ResponseBody.create(MediaType.get("text/plain"), Rule.NO_CONNECTION))
			.build();
	}
}
