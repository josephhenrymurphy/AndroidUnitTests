package com.mobiquity.androidunittests.net.interceptors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import okhttp3.Interceptor;
import okhttp3.Request;

import static com.google.common.truth.Truth.*;

public class WolframInterceptorTest {

    private WolframInterceptor interceptor;
    private static final String WOLFRAM_APP_ID = "wolfram_app_id";

    @Captor ArgumentCaptor<Request> requestArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interceptor = new WolframInterceptor(WOLFRAM_APP_ID);

    }

    @Test
    public void testIntercept_AddQueryParameters() throws Exception{
        Interceptor.Chain chain = Mockito.mock(Interceptor.Chain.class);
        Mockito.when(chain.request()).thenReturn(new Request.Builder().url("https://dummyurl").build());
        interceptor.intercept(chain);

        Mockito.verify(chain).proceed(requestArgumentCaptor.capture());
        Request interceptedRequest = requestArgumentCaptor.getValue();

        assertThat(interceptedRequest.url()).isNotNull();
        assertThat(interceptedRequest.url().queryParameterNames()).containsAllOf("appid", "format");
        assertThat(interceptedRequest.url().queryParameter("appid")).isEqualTo(WOLFRAM_APP_ID);
        assertThat(interceptedRequest.url().queryParameter("format")).isEqualTo("plaintext");
    }

}