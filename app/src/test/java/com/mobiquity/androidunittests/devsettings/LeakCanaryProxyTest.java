package com.mobiquity.androidunittests.devsettings;

import android.app.Application;

import com.mobiquity.androidunittests.testutil.ReflectionUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.google.common.truth.Truth.*;

@PrepareForTest({LeakCanary.class, RefWatcher.class})
@RunWith(PowerMockRunner.class)
public class LeakCanaryProxyTest {

    @Mock Application application;
    private LeakCanaryProxy leakCanaryProxy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        leakCanaryProxy = new LeakCanaryProxy(application);
        PowerMockito.mockStatic(LeakCanary.class);
        PowerMockito.when(LeakCanary.install(Mockito.any(Application.class)))
                .thenReturn(RefWatcher.DISABLED);
    }

    @Test
    public void testInit_ShouldStartLeakCanary() {
        leakCanaryProxy.init();
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void testInit_RefWatcherIsNotNull() {
        leakCanaryProxy.init();
        RefWatcher refWatcher = getRefWatcherThroughReflection();
        assertThat(refWatcher).isNotNull();
    }

    @Test
    public void watch_ShouldWatchObjectReferenceIfLeakCanaryInstalled() {
        leakCanaryProxy.init();
        RefWatcher refWatcherSpy = PowerMockito.spy(RefWatcher.DISABLED);
        setWatcherThroughReflection(refWatcherSpy);

        Object watchedRef = new Object();
        leakCanaryProxy.watch(watchedRef);
        Mockito.verify(refWatcherSpy).watch(watchedRef);
    }

    @Test
    public void watch_ShouldNoOpIfLeakCanaryNotInstalled() {
        RefWatcher refWatcher = getRefWatcherThroughReflection();

        Object watchedRef = new Object();
        leakCanaryProxy.watch(watchedRef);
        assertThat(refWatcher).isNull();
    }

    private RefWatcher getRefWatcherThroughReflection(){
        return ReflectionUtil.getField(
                LeakCanaryProxy.class,
                leakCanaryProxy,
                "refWatcher"
        );
    }

    private void setWatcherThroughReflection(RefWatcher refWatcher){
        ReflectionUtil.setField(
                LeakCanaryProxy.class,
                leakCanaryProxy,
                "refWatcher",
                refWatcher
        );
    }

}