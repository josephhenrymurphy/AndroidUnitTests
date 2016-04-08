package com.mobiquity.androidunittests.di.modules;

import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.net.services.WolframService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    @AppScope
    WolframService provideWolframService(Retrofit retrofit) {
        return retrofit.create(WolframService.class);
    }


}
