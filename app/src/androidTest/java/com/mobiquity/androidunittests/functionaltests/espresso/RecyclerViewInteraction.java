package com.mobiquity.androidunittests.functionaltests.espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.List;

public class RecyclerViewInteraction<T>{
    private Matcher<View> viewMatcher;
    private List<T> items;

    private RecyclerViewInteraction(Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    public static <T> RecyclerViewInteraction<T> onRecyclerView(Matcher<View> viewMatcher) {
        return new RecyclerViewInteraction<>(viewMatcher);
    }

    public RecyclerViewInteraction<T> withItems(List<T> items) {
        this.items = items;
        return this;
    }

    public RecyclerViewInteraction<T> checkAllItems(ItemViewAssertion<T> assertion) {
        for(int i = 0; i < items.size(); i++) {
            System.out.println("Checking item: " +  i);
            T item = items.get(i);
            Espresso.onView(viewMatcher)
                    .perform(RecyclerViewActions.scrollToPosition(i))
                    .check(new RecyclerViewAssertions.RecyclerViewItemAssertion<>(i, item, assertion));
        }
        return this;
    }

    public interface ItemViewAssertion<T> {
        void check(T item, View view, NoMatchingViewException e);
    }








}
