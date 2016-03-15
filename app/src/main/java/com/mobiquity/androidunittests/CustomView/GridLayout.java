package com.mobiquity.androidunittests.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.mobiquity.androidunittests.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * View Group Custom Layout that arranges children in grid like manner
 *
 * @author Sandeep Kuturu
 */
public class GridLayout extends ViewGroup {
    private static final String TAG = GridLayout.class.getSimpleName();
    private static final int UNEVEN_GRID_PENALTY_MULTIPLIER = 4;
    private static final int GRID_DIVIDER_PADDING = 5;
    private int mMaxColumns;
    private int mMinColumns;
    private int mMaxChildWidth;
    private int mMaxChildHeight;
    private int mChildWidthMeasureSpec;
    private int mChildHeightMeasureSpec;
    private int mRows;
    private int mCols;
    private int mVSpace;
    private int mHSpace;
    private Paint mDividerPaint;
    private int mDividerColor;
    private List<Integer> mRowYList;
    private List<Integer> mColXList;
    private OnClickListener mClickListener;
    private OnTouchListener mTouchListener;

    //    Interface that must be implement to receive the selection results
    public interface OnGridLayoutClickListener {
        public void onItemClick(View parent, View view, int position);
    }

    public GridLayout(final Context context) {
        super(context);
        init(context, null);
    }

    public GridLayout(final Context context, @NonNull final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GridLayout(final Context context, @NonNull final AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridLayout);
            if (typedArray != null) {
                setMaxColumns(typedArray.getInt(R.styleable.GridLayout_max_columns, 0));
                setMinColumns(typedArray.getInt(R.styleable.GridLayout_min_columns, 0));
                setDividerColor(typedArray.getColor(R.styleable.GridLayout_divider_color, 0));
                if (getMaxColumns() < 0 || getMinColumns() < 0
                        || (getMaxColumns() != 0 && getMinColumns() != 0 && getMaxColumns() < getMinColumns())) {
                    throw new ExceptionInInitializerError("Max is less than min columns");
                }
                typedArray.recycle();
            }
        }
        // Setup object to draw a divider
        setWillNotDraw(false);
        mRowYList = new ArrayList<>();
        mColXList = new ArrayList<>();
    }

    private void setEqualDimensionsToAllChildren(int parentWidth, int widthMeasureSpec, int heightMeasureSpec) {
        // Measure once to find the maximum child size.
        mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
        mChildHeightMeasureSpec = heightMeasureSpec;

        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
            mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
            if (mMaxChildWidth > parentWidth) {
                mMaxChildWidth = parentWidth;
                break;
            }
        }
        if (mMaxChildWidth == 0) {
            return;
        }
        int allowedColumns = mMaxChildWidth > 0 ? parentWidth / mMaxChildWidth : 0;

        if (getMinColumns() > 0) {
            allowedColumns = Math.max(getMinColumns(), getMaxColumns() > 0 ? Math.min(getMaxColumns(), allowedColumns) : allowedColumns);
        } else if (getMaxColumns() > 0) {
            allowedColumns = Math.min(getMaxColumns(), allowedColumns);
        }

        mMaxChildWidth = parentWidth / allowedColumns;
        mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildWidth, MeasureSpec.EXACTLY);

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
            mMaxChildHeight = Math.max(mMaxChildHeight, child.getMeasuredHeight());
        }
        mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildHeight, MeasureSpec.EXACTLY);

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
        }
    }

    private void calculateNumberOfColumns(int parentWidth, int parentHeight) {
        int visibleCount = getVisibleChildCount();

        if (visibleCount == 0) {
            return;
        }

        int bestSpaceDifference = Integer.MAX_VALUE;
        int spaceDifference;

        mCols = mMaxChildWidth > 0 ? parentWidth / mMaxChildWidth : 0;

        if (mCols != 0) {
            mRows = (int) Math.ceil((double) getVisibleChildCount() / mCols);
            calculateSpacing(parentWidth, parentHeight);

            bestSpaceDifference = Math.abs(mVSpace - mHSpace);

            if (mRows * mCols != visibleCount) {
                bestSpaceDifference *= UNEVEN_GRID_PENALTY_MULTIPLIER;
            }
            if (mRows == 1) {
                return;
            }
        }

        while (getMaxColumns() == 0 || mCols < getMaxColumns()) {
            ++mCols;
            mRows = mCols > 0 ? (int) Math.ceil((double) getVisibleChildCount() / mCols) : 0;
            calculateSpacing(parentWidth, parentHeight);

            spaceDifference = Math.abs(mVSpace - mHSpace);

            if (mRows * mCols != visibleCount) {
                spaceDifference *= UNEVEN_GRID_PENALTY_MULTIPLIER;
            }

            if (spaceDifference < bestSpaceDifference) {
                // Found a better whitespace squareness/ratio
                bestSpaceDifference = spaceDifference;
                // If we found a better whitespace squareness and there's only 1
                // row, this is the best we can do.
                if (mRows == 1) {
                    break;
                }
            } else {
                // This is a worse whitespace ratio, use the previous value of cols and exit.
                --mCols;
                mRows = mCols > 0 ? (int) Math.ceil((double) getVisibleChildCount() / mCols) : 0;
                calculateSpacing(parentWidth, parentHeight);
                break;
            }
        }

        optimizeNumberOfColumns(parentWidth);
    }

    private void calculateSpacing(int parentWidth, int parentHeight) {
        mHSpace = mCols >= 0 ? (parentWidth - mMaxChildWidth * mCols) / (mCols + 1) : 0;
        if (mHSpace < 0) {
            mHSpace = 0;
        }

        mVSpace = (parentHeight - mMaxChildHeight * mRows) / (mRows + 1);
        if (mVSpace < 0) {
            mVSpace = 0;
        }
    }

    public int getVisibleChildCount() {
        int count = getChildCount();
        int visibleCount = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            ++visibleCount;
        }
        return visibleCount;
    }

    public void setOnClickListener(final View parent, final OnGridLayoutClickListener listener) {
        mClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(parent, v, getIndexFromView(v));
            }
        };

        if (listener != null) {
            for (int i = 0; i < getChildCount(); ++i) {
                View child = getChildAt(i);
                if (child != null) {
                    child.setOnClickListener(mClickListener);
                }
            }
        }
    }

    public void setOnTouchListener(final OnTouchListener listener) {
        mTouchListener = listener;
        if (listener != null) {
            for (int i = 0; i < getChildCount(); ++i) {
                View child = getChildAt(i);
                if (child != null) {
                    child.setOnTouchListener(listener);
                }
            }
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibleChildCount() == 0 || mRowYList.isEmpty() || mDividerColor == 0) {
            return;
        }
        int visibleRight;
        //if there is only cell then mColXList would be empty. As we add the left co-ordinate of each cell in mColXList
        //and we don`t have border for the left most cell in the grid view
        if (mColXList.isEmpty()) {
            visibleRight = mHSpace + mMaxChildWidth - 1;
        } else {
            visibleRight = Collections.max(mColXList) + (mHSpace / 2) + mMaxChildWidth - 1;
        }

        // draw horizontal lines
        for (Integer y : mRowYList) {
            //Android currently has an issue while drawing a line at y = 0. For the same screen dimensions, the horizontal line is not drawn in some orientations.
            //Fixed by adding +1 to y.
            canvas.drawLine(mHSpace, y + 1, visibleRight, y + 1, mDividerPaint);
        }

        int extraCells = mRows * mCols - getVisibleChildCount();
        int lastRowOccupiedCells = mCols - extraCells;
        int visibleBottom = Collections.max(mRowYList) + mMaxChildHeight - 1;

        // draw horizontal line for the bottom line
        if (extraCells == 0) {
            canvas.drawLine(mHSpace, visibleBottom, visibleRight, visibleBottom, mDividerPaint);
        } else {
            canvas.drawLine(mHSpace, visibleBottom, lastRowOccupiedCells * (mMaxChildWidth + mHSpace), visibleBottom, mDividerPaint);
        }

        int lastRowDividerCount = 0;

        // draw vertical divider
        for (Integer x : mColXList) {
            int dividerBottom = lastRowDividerCount < lastRowOccupiedCells ? mRowYList.size() : mRowYList.size() - 1;
            for (int i = 0; i < dividerBottom; ++i) {
                int start = mRowYList.get(i) + GRID_DIVIDER_PADDING;
                int bottom = start + mMaxChildHeight - 2 * GRID_DIVIDER_PADDING;
                canvas.drawLine(x, start, x, bottom, mDividerPaint);
            }

            ++lastRowDividerCount;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");

        if (getChildCount() == 0 || getVisibleChildCount() == 0) {
            setMeasuredDimension(0, 0);
            clearData();
            return;
        }

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        setEqualDimensionsToAllChildren(parentWidth, widthMeasureSpec, heightMeasureSpec);

        if (MeasureSpec.getSize(mChildWidthMeasureSpec) == 0) {
            setMeasuredDimension(0, 0);
            clearData();
            return;
        }

        if (mMaxColumns == mMinColumns && mMinColumns != 0) {
            mCols = mMaxColumns;
            mRows = (int) Math.ceil((double) getVisibleChildCount() / mCols);
        } else {
            calculateNumberOfColumns(parentWidth, MeasureSpec.getSize(heightMeasureSpec));
        }

        setMeasuredDimension(MeasureSpec.getSize(mChildWidthMeasureSpec) * mCols, MeasureSpec.getSize(mChildHeightMeasureSpec) * mRows);
    }

    private int getIndexFromView(final View view) {
        for (int i = 0; i < getChildCount(); ++i) {
            if (view == getChildAt(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addView(@NonNull View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (mClickListener != null) {
            child.setOnClickListener(mClickListener);
        }

        if (mTouchListener != null) {
            child.setOnTouchListener(mTouchListener);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        // Reset lists
        mRowYList.clear();
        mColXList.clear();

        int visibleCount = getVisibleChildCount();
        if (visibleCount == 0) {
            return;
        }

        calculateSpacing(r - l, b - t);

        int left;
        int top;
        int col;
        int row;
        int visibleIndex = 0;
        int curRow = -1;
        int count = getChildCount();

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            row = mCols > 0 ? visibleIndex / mCols : 0;
            col = mCols > 0 ? visibleIndex % mCols : 0;
            left = mHSpace * (col + 1) + mMaxChildWidth * col;
            top = mVSpace * (row + 1) + mMaxChildHeight * row;

            if (curRow != row) {
                curRow = row;
                mRowYList.add(top - (mVSpace / 2));
            }

            if (0 == row && 0 != col) {
                mColXList.add(left - (mHSpace / 2));
            }

            child.layout(left, top, left + mMaxChildWidth, top + mMaxChildHeight);
            ++visibleIndex;
        }
    }

    public int getMaxColumns() {
        return mMaxColumns;
    }

    public void setMaxColumns(int maxColumns) {
        mMaxColumns = maxColumns;
    }

    public int getMinColumns() {
        return mMinColumns;
    }

    public void setMinColumns(int minColumns) {
        mMinColumns = minColumns;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public void setDividerColor(int color) {
        mDividerColor = color;
        if (getResources() != null && getDividerColor() != 0) {
            mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDividerPaint.setColor(getDividerColor());
            mDividerPaint.setStyle(Paint.Style.STROKE);
            mDividerPaint.setStrokeWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        }
    }

    private void clearData() {
        mRows = 0;
        mCols = 0;
        mVSpace = 0;
        mHSpace = 0;
        mRowYList.clear();
        mColXList.clear();
    }

    /**
     * This  method is used to recalculate the number of columns, So that it could arrange  child
     * views appropriately into rows and columns
     */
    private void optimizeNumberOfColumns(int parentWidth) {
        int count = getVisibleChildCount();
        if (count == 0) {
            return;
        }

        //Checks whether there are any empty cells in the last grid row
        //Checks whether we can fit the cells equally in the grid without increasing the number of rows
        //Ensures that the number of columns wont exceed minimumColumns
        if (mCols * mRows > count && count % mRows == 0 && count / mRows >= getMinColumns()) {
            mCols = count / mRows;
            setChildrenDimensions(parentWidth);
        }
    }

    /**
     * This method is used to set the the width of each child view depending on available columns and parent width
     */
    private void setChildrenDimensions(int parentWidth) {
        int count = getChildCount();
        if (count == 0 || parentWidth <= 0) {
            return;
        }

        mMaxChildWidth = mCols > 0 ? parentWidth / mCols : 0;
        mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildWidth, MeasureSpec.EXACTLY);
        mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildHeight, MeasureSpec.EXACTLY);

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
        }
    }
}