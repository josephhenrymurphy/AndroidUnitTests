package com.mobiquity.androidunittests.net.interceptors;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WolframInterceptor implements Interceptor {

    private String appId;

    public WolframInterceptor(String appId) {
        this.appId = appId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("appid", appId)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
