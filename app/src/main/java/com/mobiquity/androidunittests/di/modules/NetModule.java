package com.mobiquity.androidunittests.di.modules;

import android.content.Context;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.net.interceptors.WolframInterceptor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import timber.log.Timber;

@Module(includes = ApiModule.class)
public class NetModule {

    @Provides
    @AppScope
    Retrofit provideRetrofit(Context context, OkHttpClient client) {
        String baseUrl = context.getString(R.string.wolfram_api_url);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }


    @Provides
    @AppScope
    protected OkHttpClient provideClient(Context context) {
        Interceptor wolframInterceptor = new WolframInterceptor(
                context.getString(R.string.wolfram_app_id)
        );

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(wolframInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
