package com.mobiquity.androidunittests.functionaltests;

import android.content.Context;

import com.frogermcs.androiddevmetrics.aspect.ActivityLifecycleAnalyzer;
import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;
import com.mobiquity.androidunittests.di.modules.DevModule;
import com.mobiquity.androidunittests.di.modules.NetModule;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockWebServer;

public class CalculatorFunctionalTestApplication extends CalculatorApplication {

    private MockWebServer mockWebServer;

    @Override
    protected DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .devModule(new DevModule() {
                    @Override
                    protected AndroidDevMetricsWrapper provideAndroidDevMetrics() {
                        ActivityLifecycleAnalyzer.setEnabled(false);
                        return context -> {};
                    }
                })
                .netModule(new NetModule() {
                    @Override
                    protected OkHttpClient provideClient(Context context) {
                        OkHttpClient client = super.provideClient(context);
                        client = client.newBuilder()
                                .addInterceptor(chain -> {
                                    Request request = chain.request();
                                    if(mockWebServer != null) {
                                        request = request.newBuilder()
                                                .url(mockWebServer.url("").toString())
                                                .build();
                                    }
                                    return chain.proceed(request);
                                })
                                .build();
                        return client;
                    }
                });
    }

    // Recreate application modules to clear application state for tests
    public static void clear(Context context) {
        CalculatorFunctionalTestApplication application = (CalculatorFunctionalTestApplication) context.getApplicationContext();
        application.appComponent = application.prepareAppComponent().build();
        application.appComponent.inject(application);
    }

    public static void setMockWebServer(Context context, MockWebServer mockWebServer) {
        CalculatorFunctionalTestApplication application = (CalculatorFunctionalTestApplication) context.getApplicationContext();
        application.mockWebServer = mockWebServer;
    }

}
