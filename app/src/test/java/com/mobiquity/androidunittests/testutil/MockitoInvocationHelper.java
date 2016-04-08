package com.mobiquity.androidunittests.testutil;

import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.invocation.InvocationMatcher;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.Invocation;
import org.mockito.verification.VerificationMode;

import java.util.List;

public class MockitoInvocationHelper {

    private static class LastInvocation implements VerificationMode {

        @Override
        public void verify(VerificationData data) {
            List<Invocation> invocations = data.getAllInvocations();
            InvocationMatcher matcher = data.getWanted();
            Invocation invocation = invocations.get(invocations.size() - 1);
            if (!matcher.matches(invocation)) {
                throw new MockitoException("Last call did not match!" +
                        "\nWanted: " + matcher.toString() + "\n But got: " + invocation.toString());
            }
        }
    }

    public static LastInvocation onlyLastInvocation() {
        return new LastInvocation();
    }
}
