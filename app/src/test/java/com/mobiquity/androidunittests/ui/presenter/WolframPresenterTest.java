package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.net.services.WolframService;
import com.mobiquity.androidunittests.testutil.TestFileUtil;
import com.mobiquity.androidunittests.ui.mvpview.WolframView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Answers.*;

import static org.junit.Assert.*;

public class WolframPresenterTest {

    @Mock(answer = RETURNS_DEEP_STUBS) WolframService wolframService;
    @Mock WolframView wolframView;
    @Captor ArgumentCaptor<Callback<WolframResponse>> responseCaptor;

    private WolframResponse mockResponse;
    private WolframPresenter wolframPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockResponse = TestFileUtil.readXmlFile(WolframResponse.class, "mock/wolfram_pi_response.xml");
        wolframPresenter = new WolframPresenter(wolframService);
    }

    @Test
    public void testStartQuery_SuccessfulQuery() {
        wolframPresenter.bind(wolframView);
        wolframPresenter.startQuery(Mockito.anyString());
        Mockito.verify(wolframService.query(Mockito.anyString()))
                .enqueue(responseCaptor.capture());

        responseCaptor.getValue().onResponse(Mockito.mock(Call.class), Response.success(mockResponse));
        Mockito.verify(wolframView).updatePods(Mockito.anyList());
    }

    @Test
    public void testStartQuery_FailedQuery() {
        wolframPresenter.bind(wolframView);
        wolframPresenter.startQuery(Mockito.anyString());
        Mockito.verify(wolframService.query(Mockito.anyString()))
                .enqueue(responseCaptor.capture());

        responseCaptor.getValue().onFailure(Mockito.mock(Call.class), Mockito.mock(Throwable.class));
        Mockito.verify(wolframView).showWolframFailure();
    }
}