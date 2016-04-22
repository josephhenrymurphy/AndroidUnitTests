package com.mobiquity.androidunittests.functionaltests.espresso;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewAssertions {

    public static ViewAssertion recyclerViewShouldHaveItemsCount(int count) {
        return (view, noViewFoundException) -> {
            final RecyclerView recyclerView = (RecyclerView) view;
            final int actualCount = recyclerView.getAdapter().getItemCount();

            if (actualCount != count) {
                throw new AssertionError("RecyclerView has " + actualCount + " while expected " + count);
            }
        };
    }


    static class RecyclerViewItemAssertion<T> implements ViewAssertion {

        private int position;
        private T item;
        private RecyclerViewInteraction.ItemViewAssertion<T> itemViewAssertion;

        public RecyclerViewItemAssertion (int position, T item, RecyclerViewInteraction.ItemViewAssertion<T> itemViewAssertion) {
            this.position = position;
            this.item = item;
            this.itemViewAssertion = itemViewAssertion;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if(view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                View itemView = recyclerView.findViewHolderForLayoutPosition(position).itemView;
                itemViewAssertion.check(item, itemView, noViewFoundException);
            } else {
                throw new PerformException.Builder()
                        .withActionDescription(toString())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new IllegalArgumentException("view is not a RecyclerView"))
                        .build();
            }
        }
    }
}
