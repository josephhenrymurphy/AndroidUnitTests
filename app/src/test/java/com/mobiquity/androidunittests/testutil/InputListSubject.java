package com.mobiquity.androidunittests.testutil;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.Ordered;
import com.google.common.truth.SubjectFactory;
import com.mobiquity.androidunittests.calculator.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class InputListSubject extends IterableSubject<InputListSubject, Input, List<Input> > {

    public InputListSubject(FailureStrategy failureStrategy, List<Input> list) {
        super(failureStrategy, list);
    }

    private static final SubjectFactory<InputListSubject, List<Input>> FACTORY = new SubjectFactory<InputListSubject, List<Input> >() {
        @Override
        public InputListSubject getSubject(FailureStrategy fs, List<Input> target) {
            return new InputListSubject(fs, target);
        }
    };

    public static SubjectFactory<InputListSubject, List<Input>> inputList() {
        return FACTORY;
    }

    public Ordered containsExactlyInputValuesIn(Iterable<Input> required) {
        Iterator<Input> actualIter = getSubject().iterator();
        Iterator<Input> requiredIter = required.iterator();

        final String failVerb = "contains exact input values";

        while (actualIter.hasNext() && requiredIter.hasNext()) {
            Input actualElement = actualIter.next();
            Input requiredElement = requiredIter.next();

            if (!actualElement.valueEquals(requiredElement)) {
                List<Input> missing = new ArrayList<>();
                missing.add(requiredElement);
                Iterators.addAll(missing, requiredIter);

                // Extra elements that the subject had but shouldn't have.
                List<Input> extra = Lists.newArrayList();

                // Remove all actual elements from missing, and add any that weren't in missing
                // to extra.
                if (!missing.remove(actualElement)) {
                    extra.add(actualElement);
                }
                while (actualIter.hasNext()) {
                    Input item = actualIter.next();
                    if (!missing.remove(item)) {
                        extra.add(item);
                    }
                }

                if (!missing.isEmpty()) {
                    if (!extra.isEmpty()) {
                        // Subject is both missing required elements and contains extra elements
                        failWithRawMessage(
                                "Not true that %s %s <%s>. It is missing <%s> and has unexpected items <%s>",
                                getDisplaySubject(),
                                failVerb,
                                required,
                                countDuplicates(missing),
                                countDuplicates(extra));
                    } else {
                        failWithBadResults(
                                failVerb, required, "is missing", countDuplicates(missing));
                    }
                }
                if (!extra.isEmpty()) {
                    failWithBadResults(
                            failVerb, required, "has unexpected items", countDuplicates(extra));
                }
                // Since we know the iterables were not in the same order, inOrder() can just fail.
                return () -> fail("contains only these elements in order", required);
            }
        }

        if (actualIter.hasNext()) {
            failWithBadResults(
                    failVerb,
                    required,
                    "has unexpected items",
                    countDuplicates(Lists.newArrayList(actualIter)));
        } else if (requiredIter.hasNext()) {
            failWithBadResults(
                    failVerb,
                    required,
                    "is missing",
                    countDuplicates(Lists.newArrayList(requiredIter)));
        }
        return () -> {};
    }

    // From SubjectUtils
    private <T> List<Object> countDuplicates(Collection<T> items) {
        List<T> itemSet = new ArrayList<T>();
        for (T item : items) {
            if (!itemSet.contains(item)) {
                itemSet.add(item);
            }
        }
        Object[] params = new Object[itemSet.size()];
        int n = 0;
        for (T item : itemSet) {
            int count = countOf(item, items);
            params[n++] = (count > 1) ? item + " [" + count + " copies]" : item;
        }
        return Arrays.asList(params);
    }

    private <T> int countOf(T t, Iterable<T> items) {
        int count = 0;
        for (T item : items) {
            if (t == null ? (item == null) : t.equals(item)) {
                count++;
            }
        }
        return count;
    }



}
