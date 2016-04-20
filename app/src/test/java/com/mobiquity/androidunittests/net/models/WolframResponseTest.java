package com.mobiquity.androidunittests.net.models;

import com.mobiquity.androidunittests.testutil.TestFileUtil;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.core.Persister;

import static com.google.common.truth.Truth.assertThat;

public class WolframResponseTest {

    WolframResponse response;

    @Before
    public void setUp() throws Exception {
        String fileContent = TestFileUtil.readFile("mock/wolfram_pi_response.xml");
        response = new Persister().read(WolframResponse.class, fileContent);
    }

    @Test
    public void testGetPods() {
        assertThat(response.getPods()).hasSize(5);

        WolframResponse.Pod firstPod = response.getPods().get(0);
        assertThat(firstPod.getTitle()).isEqualTo("Input");
        assertThat(firstPod.getSubpods()).hasSize(1);

        WolframResponse.Subpod firstSubpod = firstPod.getSubpods().get(0);
        assertThat(firstSubpod.getText()).isEqualTo("π");
    }

    @Test
    public void testPods_GetTitle() {
        WolframResponse.Pod firstPod = response.getPods().get(0);
        assertThat(firstPod.getTitle()).isEqualTo("Input");
    }

    @Test
    public void testPods_CompareTo() {
        WolframResponse.Pod firstPod = response.getPods().get(0);
        WolframResponse.Pod secondPod = response.getPods().get(1);
        WolframResponse.Pod thirdPod = response.getPods().get(2);

        assertThat(firstPod).isLessThan(secondPod);
        assertThat(thirdPod).isGreaterThan(secondPod);
    }




}