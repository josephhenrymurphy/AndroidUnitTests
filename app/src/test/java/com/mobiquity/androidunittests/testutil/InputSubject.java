package com.mobiquity.androidunittests.testutil;

import com.google.common.truth.FailureStrategy;
import com.google.common.truth.Subject;
import com.google.common.truth.SubjectFactory;
import com.mobiquity.androidunittests.calculator.input.Input;


public class InputSubject extends Subject<InputSubject, Input> {

    private static final SubjectFactory<InputSubject, Input> FACTORY = new SubjectFactory<InputSubject, Input>() {
        @Override
        public InputSubject getSubject(FailureStrategy fs, Input target) {
            return new InputSubject(fs, target);
        }
    };

    public static SubjectFactory<InputSubject, Input> input() {
        return FACTORY;
    }

    public InputSubject(FailureStrategy failureStrategy, Input subject) {
        super(failureStrategy, subject);
    }

    public InputSubject valueEqualTo(Input input) {
        if(!getSubject().valueEquals(input)) {
            fail("value equals", input);
        }
        return this;
    }

    public InputSubject valueNotEqualTo(Input input) {
        if(getSubject().valueEquals(input)) {
            fail("value not equal", input);
        }
        return this;
    }
}
