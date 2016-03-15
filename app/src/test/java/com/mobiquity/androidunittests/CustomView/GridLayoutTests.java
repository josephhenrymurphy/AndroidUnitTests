package com.mobiquity.androidunittests.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mob.android.test.helper.TestUtils;
import com.mobiquity.androidunittests.BuildConfig;
import com.mobiquity.androidunittests.R;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResName;
import org.robolectric.shadows.RoboAttributeSet;
import org.robolectric.shadows.ShadowContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sandeep Kuturu
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.mobiquity.androidunittests")
public class GridLayoutTests {
    public final String PKG = RuntimeEnvironment.application.getBaseContext().getPackageName();

    @BeforeClass
    public static void ClassSetup() {
        System.gc();
    }

    @Test
    public void testConstructor_AttributeSetNull() {
        new GridLayout(RuntimeEnvironment.application.getBaseContext());
    }

    @Test
    public void testConstructor_MaxColumnsDefined() throws NoSuchFieldException, IllegalAccessException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        int maxColumnsVariable = (Integer) TestUtils.getVariable(gridLayout, "mMaxColumns");
        TypedArray attributeSet = RuntimeEnvironment.application.obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        assertEquals(4, maxColumnsVariable);
        assertEquals(4, maxColumnAttribute);
    }

    @Test
    public void testConstructor_DefaultAttributeSet() {
        RoboAttributeSet attrs = defaultAttrList();
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        TypedArray attributeSet = RuntimeEnvironment.application.getApplicationContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        assertEquals(0, maxColumnAttribute);
    }

    @Test
    public void testConstructor_WithDefStyle() {
        RoboAttributeSet attrs = defaultAttrList();
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs, 0);
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        assertEquals(0, maxColumnAttribute);
    }

    @Test
    public void testConstructor_WithDefStyle_AndMaxColumns() throws NoSuchFieldException, IllegalAccessException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        int maxColumnsVariable = (Integer) TestUtils.getVariable(gridLayout, "mMaxColumns");
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        assertEquals(4, maxColumnsVariable);
        assertEquals(4, maxColumnAttribute);
    }

    @Test
    public void testInit_MaxColumnsSet() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);

        assertNull(TestUtils.getVariable(gridLayout, "mDividerPaint"));
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        List<Integer> rowYList = (List<Integer>) TestUtils.getVariable(gridLayout, "mRowYList");
        List<Integer> colXList = (List<Integer>) TestUtils.getVariable(gridLayout, "mColXList");

        assertEquals(4, maxColumnAttribute);
        assertEquals(0, rowYList.size());
        assertEquals(0, colXList.size());
    }

    @Test
    public void testInit_NullTypedArray() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createNullAttributeSet();
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);

        assertNull(TestUtils.getVariable(gridLayout, "mDividerPaint"));
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        List<Integer> rowYList = (List<Integer>) TestUtils.getVariable(gridLayout, "mRowYList");
        List<Integer> colXList = (List<Integer>) TestUtils.getVariable(gridLayout, "mColXList");

        assertEquals(0, maxColumnAttribute);
        assertEquals(0, rowYList.size());
        assertEquals(0, colXList.size());
    }

    @Test
    public void testInit_DefaultAttributeSet() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = defaultAttrList();
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs, 0);

        assertNull(TestUtils.getVariable(gridLayout, "mDividerPaint"));
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        List<Integer> rowYList = (List<Integer>) TestUtils.getVariable(gridLayout, "mRowYList");
        List<Integer> colXList = (List<Integer>) TestUtils.getVariable(gridLayout, "mColXList");

        assertEquals(0, maxColumnAttribute);
        assertEquals(0, rowYList.size());
        assertEquals(0, colXList.size());
    }

    @Test(expected = InvocationTargetException.class)
    public void testInit_MaxLessThanMinColumns() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        RoboAttributeSet attrs = defaultAttrList();
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs, 0));
        Mockito.doReturn(2).when(gridLayout).getMaxColumns();
        Mockito.doReturn(5).when(gridLayout).getMinColumns();
        TestUtils.invokePrivateMethod(gridLayout, "init", new Class[]{Context.class, AttributeSet.class}, RuntimeEnvironment.application.getApplicationContext(), attrs);
    }

    @Test
    public void testInit_AttributeSetNull() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        AttributeSet attrs = null;
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());

        assertNull(TestUtils.getVariable(gridLayout, "mDividerPaint"));
        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        List<Integer> rowYList = (List<Integer>) TestUtils.getVariable(gridLayout, "mRowYList");
        List<Integer> colXList = (List<Integer>) TestUtils.getVariable(gridLayout, "mColXList");

        assertEquals(0, maxColumnAttribute);
        assertEquals(0, rowYList.size());
        assertEquals(0, colXList.size());
    }

    @Test
    public void testcalculateNumberOfColumns_mHSpaceIsZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(3).when(gridLayout).getMaxColumns();
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 400);
        TestUtils.invokePrivateMethod(gridLayout, "calculateNumberOfColumns", new Class[]{int.class, int.class}, 0, 500);
        assertEquals(1, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void testcalculateNumberOfColumns_mVspaceIsZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(3).when(gridLayout).getMaxColumns();
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mMaxChildHeight", 400);
        TestUtils.invokePrivateMethod(gridLayout, "calculateNumberOfColumns", new Class[]{int.class, int.class}, 500, 0);
        assertEquals(3, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void testAddView_listenerNotNull() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        View.OnClickListener listener = Mockito.mock(View.OnClickListener.class);
        TestUtils.setVariable(gridLayout, "mClickListener", listener);
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        gridLayout.addView(view, -1, new ViewGroup.LayoutParams(0, 0));
        assertNotNull(TestUtils.getVariable(view, "mListenerInfo"));
    }

    @Test
    public void testOnLayout_childIsNull() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        TestUtils.invokePrivateMethod(gridLayout, "onLayout",
                new Class[]{boolean.class, int.class, int.class, int.class, int.class}, false, 0, 0, 2000, 2000);
        assertTrue(((List) TestUtils.getVariable(gridLayout, "mRowYList")).isEmpty());
    }

    @Test
    public void testOnLayout_childIsGone() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        View child = new TextView(RuntimeEnvironment.application.getBaseContext());
        child.setVisibility(View.GONE);
        Mockito.doReturn(child).when(gridLayout).getChildAt(1);
        TestUtils.invokePrivateMethod(gridLayout, "onLayout",
                new Class[]{boolean.class, int.class, int.class, int.class, int.class}, false, 0, 0, 2000, 2000);
        assertTrue(((List) TestUtils.getVariable(gridLayout, "mRowYList")).isEmpty());
    }

    @Test
    public void testOnLayout_hasOneChild() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        Mockito.doReturn(1).when(gridLayout).getChildCount();
        View child = new TextView(RuntimeEnvironment.application.getBaseContext());
        child.setVisibility(View.GONE);
        Mockito.doReturn(child).when(gridLayout).getChildAt(1);
        TestUtils.invokePrivateMethod(gridLayout, "onLayout",
                new Class[]{boolean.class, int.class, int.class, int.class, int.class}, false, 0, 0, 2000, 2000);
        assertTrue(((List) TestUtils.getVariable(gridLayout, "mRowYList")).isEmpty());
    }

    @Test
    public void testOnMeasure_AddingEqualDimensionChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addEqualDimensionChildren(gridLayout);

        TestUtils.invokePrivateMethod(gridLayout, "onMeasure", new Class[]{int.class, int.class}, 500, 500);

        for (int i = 0; i < gridLayout.getVisibleChildCount() - 1; i++) {
            assertEquals(gridLayout.getChildAt(i).getMeasuredHeight(), gridLayout.getChildAt(i + 1).getMeasuredHeight());
            assertEquals(gridLayout.getChildAt(i).getMeasuredWidth(), gridLayout.getChildAt(i + 1).getMeasuredWidth());
        }
    }

    @Test
    public void testOnMeasure_ChildVisibilityGone() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        addChildren_VisibleGoneCase(gridLayout);
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mMaxColumns", 3);
        TestUtils.setVariable(gridLayout, "mMinColumns", 3);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);
        assertEquals(3, (int) TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void testOnMeasure_childWidthIsZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        addChildren_VisibleGoneCase(gridLayout);
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 0, 0);
        assertEquals(0, (int) TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void testOnMeasure_hasMaxAndMinColumnsTheSame() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addChildren_VisibleGoneCase(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        for (int i = 0; i < gridLayout.getVisibleChildCount() - 1; i++) {
            if (gridLayout.getChildAt(i).getVisibility() != View.GONE) {
                assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
                assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
            }
        }
    }

    @Test
    public void testOnMeasure_ZeroChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        assertEquals(0, gridLayout.getMeasuredWidth());
        assertEquals(0, gridLayout.getMeasuredHeight());
        assertEquals(0, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testOnMeasure_CheckSameDimensionChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        for (int i = 0; i < gridLayout.getVisibleChildCount(); i++) {
            assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
            assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
        }
    }

    @Test
    public void testOnMeasure_CheckDimensionDifferentChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addRandomDimensionChildren(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        for (int i = 0; i < gridLayout.getVisibleChildCount(); i++) {
            assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
            assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
        }
    }

    @Test
    public void testOnMeasure_AddMatchParentDimensionChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addMatchParentDimensionChildren(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        for (int i = 0; i < gridLayout.getVisibleChildCount(); i++) {
            assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
            assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
        }
    }

    @Test
    public void testOnMeasure_ForcedZeroColumns() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);

        TestUtils.setVariable(gridLayout, "mCols", 0);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        assertEquals(0, TestUtils.getVariable(gridLayout, "mRows"));
    }

    @Test
    public void testOnMeasure_AllChildrenNotVisible() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addChildrenNotVisible(gridLayout);

        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 500, 500);

        assertEquals(0, TestUtils.getVariable(gridLayout, "mRows"));
    }

    @Test
    public void testGetVisibleCount_PositiveChildCount() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addEqualDimensionChildren(gridLayout);
        assertEquals(6, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testGetVisibleCount_AllChildrenVisibilityGone() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addChildrenNotVisible(gridLayout);
        assertEquals(0, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testGetVisibleCount_AllChildrenVisibilityInVisible() {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());

        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child1.setVisibility(View.INVISIBLE);
        gridLayout.addView(Child1);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child2.setVisibility(View.INVISIBLE);
        gridLayout.addView(Child2);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child3.setVisibility(View.INVISIBLE);
        gridLayout.addView(Child3);

        assertEquals(3, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testGetVisibleCount_AllChildrenVisibilityVisible() {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());

        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child1.setVisibility(View.VISIBLE);
        gridLayout.addView(Child1);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child2.setVisibility(View.VISIBLE);
        gridLayout.addView(Child2);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child3.setVisibility(View.VISIBLE);
        gridLayout.addView(Child3);

        assertEquals(3, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testGetVisibleCount_DifferentVisibleStates() {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());

        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child1.setVisibility(View.VISIBLE);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child2.setVisibility(View.INVISIBLE);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child3.setVisibility(View.GONE);

        gridLayout.addView(Child1);
        gridLayout.addView(Child2);
        gridLayout.addView(Child3);
        assertEquals(2, gridLayout.getVisibleChildCount());
    }

    @Test
    public void testSetGridCellListener_NullListener() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        gridLayout.setOnClickListener(null, null);
        for (int i = 0; i < gridLayout.getVisibleChildCount(); ++i) {
            assertNull(Shadows.shadowOf(gridLayout.getChildAt(i)).getOnClickListener());
        }
    }

    @Test
    public void testSetGridCellListener_MockListener() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout.OnGridLayoutClickListener mClickListener = Mockito.mock(GridLayout.OnGridLayoutClickListener.class);
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        gridLayout.setOnClickListener(new View(RuntimeEnvironment.application.getBaseContext()), mClickListener);
        assertNotNull(TestUtils.getVariable(gridLayout, "mClickListener"));
    }

    @Test
    public void testSetGridCellListener_ChildVisibleGone() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout.OnGridLayoutClickListener mClickListener = Mockito.mock(GridLayout.OnGridLayoutClickListener.class);
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addChildren_VisibleGoneCase(gridLayout);
        gridLayout.setOnClickListener(new View(RuntimeEnvironment.application.getBaseContext()), mClickListener);
        assertNotNull(TestUtils.getVariable(gridLayout, "mClickListener"));
    }

    @Test
    public void testSetGridCellListener_ChildNull_AndListenerNull() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        GridLayout.OnGridLayoutClickListener clickListener = null;
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        gridLayout.setOnClickListener(new View(RuntimeEnvironment.application.getBaseContext()), clickListener);
        for (int i = 0; i < gridLayout.getVisibleChildCount(); i++) {
            assertNull(Shadows.shadowOf(gridLayout.getChildAt(i)).getOnClickListener());
        }
    }

    @Test
    public void testSetonClickHandler_NullCLickHandler() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        gridLayout.setOnClickListener(null);
        for (int i = 0; i < gridLayout.getVisibleChildCount(); i++) {
            assertNull(Shadows.shadowOf(gridLayout.getChildAt(i)).getOnClickListener());
        }
    }

    @Test
    public void testSetOnClickHandler_MockClickHandler() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout.OnGridLayoutClickListener mClickListener = Mockito.mock(GridLayout.OnGridLayoutClickListener.class);
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);

        gridLayout.setOnClickListener(new View(RuntimeEnvironment.application.getBaseContext()), mClickListener);

        assertNotNull(gridLayout.getChildAt(0));
        assertNotNull(TestUtils.getVariable(gridLayout, "mClickListener"));
    }

    @Test
    public void setOnTouchListener_nullChild() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        gridLayout.setOnTouchListener(null);
        for (int i = 0; i < gridLayout.getVisibleChildCount(); ++i) {
            assertNull(Shadows.shadowOf(gridLayout.getChildAt(i)).getOnTouchListener());
        }
    }

    @Test
    public void setOnTouchListener() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addEqualDimensionChildren(gridLayout);
        gridLayout.setOnTouchListener(Mockito.mock(View.OnTouchListener.class));
        assertNotNull(gridLayout.getChildAt(0));
        assertNotNull(TestUtils.getVariable(gridLayout, "mTouchListener"));
    }


    private RoboAttributeSet createAttrListWithMaxColumns(int max_columns) {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "max_columns"), String.valueOf(max_columns), PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        return shadowCtx.createAttributeSet(attrs, GridLayout.class);
    }

    private RoboAttributeSet defaultAttrList() {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "cardtitle"), "cardtitle", PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        return shadowCtx.createAttributeSet(attrs, GridLayout.class);
    }

    private RoboAttributeSet createNullAttributeSet() {
        final List<Attribute> attrs = new ArrayList<>();
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        return shadowCtx.createAttributeSet(attrs, GridLayout.class);
    }

    private void addEqualDimensionChildren(GridLayout inGridLayoutLayout) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ViewGroup.LayoutParams lp;
        lp = new ViewGroup.LayoutParams(300, 300);
        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child1.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child1);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child2.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child2);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child3.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child3);

        View Child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child4.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child4);

        View Child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child5.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child5);

        View Child6 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        Child6.setLayoutParams(lp);
        inGridLayoutLayout.addView(Child6);
    }

    private void addChildren_VisibleGoneCase(GridLayout inGridLayoutLayout) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child1, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child1);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child2, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child2);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child3, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        Child3.setVisibility(View.GONE);
        inGridLayoutLayout.addView(Child3);

        View Child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child4, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child4);

        View Child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child5, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child5);

        View Child6 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child6, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child6);
    }

    private void addRandomDimensionChildren(GridLayout inGridLayoutLayout) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        View Child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child1, "setMeasuredDimension", new Class[]{int.class, int.class}, 300, 300);
        inGridLayoutLayout.addView(Child1);

        View Child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child2, "setMeasuredDimension", new Class[]{int.class, int.class}, 310, 315);
        inGridLayoutLayout.addView(Child2);

        View Child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child3, "setMeasuredDimension", new Class[]{int.class, int.class}, 320, 325);
        inGridLayoutLayout.addView(Child3);

        View Child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child4, "setMeasuredDimension", new Class[]{int.class, int.class}, 330, 335);
        inGridLayoutLayout.addView(Child4);

        View Child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child5, "setMeasuredDimension", new Class[]{int.class, int.class}, 340, 345);
        inGridLayoutLayout.addView(Child5);

        View Child6 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        TestUtils.invokePrivateMethod(Child6, "setMeasuredDimension", new Class[]{int.class, int.class}, 350, 355);
        inGridLayoutLayout.addView(Child6);
    }

    private void addMatchParentDimensionChildren(GridLayout inGridLayoutLayout) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ViewGroup.LayoutParams lp;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View child = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child.setLayoutParams(lp);
        inGridLayoutLayout.addView(child);

        View child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child1.setLayoutParams(lp);
        inGridLayoutLayout.addView(child1);

        View child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child2.setLayoutParams(lp);
        inGridLayoutLayout.addView(child2);

        View child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child3.setLayoutParams(lp);
        inGridLayoutLayout.addView(child3);


        View child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child4.setLayoutParams(lp);
        inGridLayoutLayout.addView(child4);

        View child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child5.setLayoutParams(lp);
        inGridLayoutLayout.addView(child5);
    }

    private void addChildrenNotVisible(GridLayout inGridLayoutLayout) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ViewGroup.LayoutParams lp;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View child = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child.setLayoutParams(lp);
        child.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child);

        View child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child1.setLayoutParams(lp);
        child1.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child1);

        View child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child2.setLayoutParams(lp);
        child2.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child2);

        View child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child3.setLayoutParams(lp);
        child3.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child3);

        View child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child4.setLayoutParams(lp);
        child4.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child4);

        View child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child5.setLayoutParams(lp);
        child5.setVisibility(View.GONE);
        inGridLayoutLayout.addView(child5);
    }

    @Test
    public void testOnDraw_Default() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Bitmap bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        gridLayout.setLayoutParams(new ViewGroup.LayoutParams(2000, 2000));

        ViewGroup.LayoutParams lp;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View child = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child.setLayoutParams(lp);
        gridLayout.addView(child);

        View child1 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child1.setLayoutParams(lp);
        gridLayout.addView(child1);

        View child2 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child2.setLayoutParams(lp);
        gridLayout.addView(child2);

        View child3 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child3.setLayoutParams(lp);
        gridLayout.addView(child3);

        View child4 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child4.setLayoutParams(lp);
        gridLayout.addView(child4);

        View child5 = new TextView(RuntimeEnvironment.application.getBaseContext(), null);
        child5.setLayoutParams(lp);
        gridLayout.addView(child5);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 2000, 2000);
        TestUtils.invokePrivateMethod(gridLayout, "onLayout",
                new Class[]{boolean.class, int.class, int.class, int.class, int.class}, false, 0, 0, 2000, 2000);

        TestUtils.invokePrivateMethod(gridLayout, "onDraw",
                new Class[]{Canvas.class}, canvas);

        assertNotNull(canvas);
    }

    @Test
    public void testOnDraw_ZeroChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Bitmap bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        gridLayout.setLayoutParams(new ViewGroup.LayoutParams(2000, 2000));

        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 2000, 2000);
        TestUtils.invokePrivateMethod(gridLayout, "onLayout",
                new Class[]{boolean.class, int.class, int.class, int.class, int.class}, false, 0, 0, 2000, 2000);
        TestUtils.invokePrivateMethod(gridLayout, "onDraw",
                new Class[]{Canvas.class}, canvas);

        assertNotNull(canvas);
    }

    @Test
    public void testOnDraw_emptyList() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Bitmap bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        gridLayout.setLayoutParams(new ViewGroup.LayoutParams(2000, 2000));

        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mRowYList", new ArrayList<>());
        TestUtils.invokePrivateMethod(gridLayout, "onDraw", new Class[]{Canvas.class}, canvas);
        assertNotNull(canvas);
    }

    @Test
    public void testOnDraw_noDivider() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Bitmap bitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        gridLayout.setLayoutParams(new ViewGroup.LayoutParams(2000, 2000));

        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        List<Integer> list = new ArrayList<>();
        list.add(5);
        TestUtils.setVariable(gridLayout, "mRowYList", list);
        TestUtils.setVariable(gridLayout, "mDividerColor", 0);

        TestUtils.invokePrivateMethod(gridLayout, "onDraw", new Class[]{Canvas.class}, canvas);
        assertNotNull(canvas);
    }

    @Test
    public void testGetIndexFromView_hasView() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(view).when(gridLayout).getChildAt(1);
        assertEquals(1, TestUtils.invokePrivateMethod(gridLayout, "getIndexFromView", new Class[]{View.class}, view));
    }

    @Test
    public void testGetIndexFromView_noView() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        assertEquals(-1, TestUtils.invokePrivateMethod(gridLayout, "getIndexFromView", new Class[]{View.class}, view));
    }

    @Test
    public void testsetEqualDimensionsToAllChildren_hasGoneChildren() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        view.setVisibility(View.GONE);
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(view).when(gridLayout).getChildAt(1);
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 100);
        TestUtils.invokePrivateMethod(gridLayout, "setEqualDimensionsToAllChildren", new Class[]{int.class, int.class, int.class}, 200, 200, 200);
        assertEquals(0, TestUtils.getVariable(gridLayout, "mMaxChildHeight"));
        assertEquals(100, TestUtils.getVariable(gridLayout, "mMaxChildWidth"));
    }

    @Test
    public void testsetEqualDimensionsToAllChildren_childWidthGreaterParentWidth() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(view).when(gridLayout).getChildAt(1);
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 300);
        TestUtils.invokePrivateMethod(gridLayout, "setEqualDimensionsToAllChildren", new Class[]{int.class, int.class, int.class}, 200, 200, 200);
        assertEquals(0, TestUtils.getVariable(gridLayout, "mMaxChildHeight"));
        assertEquals(200, TestUtils.getVariable(gridLayout, "mMaxChildWidth"));
    }

    @Test
    public void testsetEqualDimensionsToAllChildren_minColumnsGreaterThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(3).when(gridLayout).getMinColumns();
        Mockito.doReturn(view).when(gridLayout).getChildAt(1);
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 300);
        TestUtils.invokePrivateMethod(gridLayout, "setEqualDimensionsToAllChildren", new Class[]{int.class, int.class, int.class}, 200, 200, 200);
        assertEquals(0, TestUtils.getVariable(gridLayout, "mMaxChildHeight"));
        assertEquals(66, TestUtils.getVariable(gridLayout, "mMaxChildWidth"));
    }

    @Test
    public void testsetEqualDimensionsToAllChildren_maxColumnsLessThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        View view = new TextView(RuntimeEnvironment.application.getApplicationContext());
        Mockito.doReturn(6).when(gridLayout).getChildCount();
        Mockito.doReturn(view).when(gridLayout).getChildAt(1);
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 300);
        TestUtils.invokePrivateMethod(gridLayout, "setEqualDimensionsToAllChildren", new Class[]{int.class, int.class, int.class}, 200, 200, 200);
        assertEquals(0, TestUtils.getVariable(gridLayout, "mMaxChildHeight"));
        assertEquals(200, TestUtils.getVariable(gridLayout, "mMaxChildWidth"));
    }

    @Test
    public void optimizeNumberOfColumns_Valid() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mCols", 4);
        TestUtils.setVariable(gridLayout, "mRows", 2);
        TestUtils.invokePrivateMethod(gridLayout, "optimizeNumberOfColumns", new Class[]{int.class}, 600);
        assertEquals(3, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void optimizeNumberOfColumns_primeNumberOfCells() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(7).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mCols", 4);
        TestUtils.setVariable(gridLayout, "mRows", 2);
        TestUtils.invokePrivateMethod(gridLayout, "optimizeNumberOfColumns", new Class[]{int.class}, 600);
        assertEquals(4, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void optimizeNumberOfColumns_allowedColumnsLessThanMinimumColumns() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        Mockito.doReturn(4).when(gridLayout).getMinColumns();
        TestUtils.setVariable(gridLayout, "mCols", 4);
        TestUtils.setVariable(gridLayout, "mRows", 2);
        TestUtils.invokePrivateMethod(gridLayout, "optimizeNumberOfColumns", new Class[]{int.class}, 600);
        assertEquals(4, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void optimizeNumberOfColumns_ChildCountZero() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(0).when(gridLayout).getVisibleChildCount();
        Mockito.doReturn(0).when(gridLayout).getMinColumns();
        TestUtils.setVariable(gridLayout, "mCols", 4);
        TestUtils.setVariable(gridLayout, "mRows", 2);
        TestUtils.invokePrivateMethod(gridLayout, "optimizeNumberOfColumns", new Class[]{int.class}, 600);
        assertEquals(4, TestUtils.getVariable(gridLayout, "mCols"));
        assertEquals(2, TestUtils.getVariable(gridLayout, "mRows"));
    }

    @Test
    public void setChildrenDimensions_Valid() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addEqualDimensionChildren(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 1200, 1200);
        TestUtils.setVariable(gridLayout, "mCols", 3);
        TestUtils.invokePrivateMethod(gridLayout, "setChildrenDimensions", new Class[]{int.class}, 1200);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        assertEquals(400, gridLayout.getChildAt(0).getMeasuredWidth());

        for (int i = 0; i < gridLayout.getChildCount() - 1; i++) {
            if (gridLayout.getChildAt(i).getVisibility() != View.GONE) {
                assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
                assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
            }
        }
    }

    @Test
    public void setChildrenDimensions_ChildCountZero() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(0).when(gridLayout).getVisibleChildCount();
        TestUtils.invokePrivateMethod(gridLayout, "setChildrenDimensions", new Class[]{int.class}, 1200);
        assertNull(gridLayout.getChildAt(0));
    }

    @Test
    public void setChildrenDimensions_ColsZero() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(0);
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs));
        Mockito.doReturn(0).when(gridLayout).getVisibleChildCount();
        TestUtils.invokePrivateMethod(gridLayout, "setChildrenDimensions", new Class[]{int.class}, 1200);
        assertNull(gridLayout.getChildAt(0));
    }

    @Test
    public void setChildrenDimensions_ParentWidthNegative() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext());
        addEqualDimensionChildren(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 1200, 1200);
        TestUtils.setVariable(gridLayout, "mCols", 3);
        TestUtils.invokePrivateMethod(gridLayout, "setChildrenDimensions", new Class[]{int.class}, -1200);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        assertNotEquals(400, gridLayout.getChildAt(0).getMeasuredWidth());

        for (int i = 0; i < gridLayout.getChildCount() - 1; i++) {
            if (gridLayout.getChildAt(i).getVisibility() != View.GONE) {
                assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
                assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
            }
        }
    }

    @Test
    public void setChildrenDimensions_ChildVisibilityGone() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createAttrListWithMaxColumns(4);
        GridLayout gridLayout = new GridLayout(RuntimeEnvironment.application.getBaseContext(), attrs);
        addChildren_VisibleGoneCase(gridLayout);
        TestUtils.invokePrivateMethod(gridLayout, "onMeasure",
                new Class[]{int.class, int.class}, 1200, 1200);
        TestUtils.setVariable(gridLayout, "mCols", 3);
        TestUtils.invokePrivateMethod(gridLayout, "setChildrenDimensions", new Class[]{int.class}, 1200);

        int childWidth = gridLayout.getChildAt(0).getMeasuredWidth();
        int childHeight = gridLayout.getChildAt(0).getMeasuredHeight();

        assertEquals(400, gridLayout.getChildAt(0).getMeasuredWidth());

        for (int i = 0; i < gridLayout.getChildCount() - 1; i++) {
            if (gridLayout.getChildAt(i).getVisibility() != View.GONE) {
                assertEquals(childWidth, gridLayout.getChildAt(i).getMeasuredWidth());
                assertEquals(childHeight, gridLayout.getChildAt(i).getMeasuredHeight());
            }
        }
    }

    @Test
    public void calculateNumberOfColumns_MColsValid() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(3).when(gridLayout).getMaxColumns();
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mMaxChildHeight", 400);
        TestUtils.setVariable(gridLayout, "mCols", 3);
        TestUtils.invokePrivateMethod(gridLayout, "calculateNumberOfColumns", new Class[]{int.class, int.class}, 500, 0);
        assertEquals(3, TestUtils.getVariable(gridLayout, "mCols"));
    }

    @Test
    public void gridLayout_NullTypedArray() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        RoboAttributeSet attrs = createNullAttributeSet();
        Context context = Mockito.spy(RuntimeEnvironment.application.getBaseContext());
        Mockito.doReturn(null).when(context).obtainStyledAttributes(attrs, R.styleable.GridLayout);
        GridLayout gridLayout = new GridLayout(context, attrs);

        TypedArray attributeSet = RuntimeEnvironment.application.getBaseContext().obtainStyledAttributes(attrs, R.styleable.GridLayout);
        int maxColumnAttribute = attributeSet.getInt(R.styleable.GridLayout_max_columns, 0);
        List<Integer> rowYList = (List<Integer>) TestUtils.getVariable(gridLayout, "mRowYList");
        List<Integer> colXList = (List<Integer>) TestUtils.getVariable(gridLayout, "mColXList");

        assertEquals(0, maxColumnAttribute);
        assertEquals(0, rowYList.size());
        assertEquals(0, colXList.size());
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void gridLayout_MaxColumnsNegative() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "max_columns"), String.valueOf(-4), PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        RoboAttributeSet attributeSet = shadowCtx.createAttributeSet(attrs, GridLayout.class);
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attributeSet);
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void gridLayout_MinColumnsNegative() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "min_columns"), String.valueOf(-4), PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        RoboAttributeSet attributeSet = shadowCtx.createAttributeSet(attrs, GridLayout.class);
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attributeSet);
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void gridLayout_MaxColumnsLessThanMinColumns() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "min_columns"), String.valueOf(6), PKG));
        attrs.add(new Attribute(new ResName(PKG, "attr", "max_columns"), String.valueOf(4), PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        RoboAttributeSet attributeSet = shadowCtx.createAttributeSet(attrs, GridLayout.class);
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attributeSet);
    }

    @Test(expected = ExceptionInInitializerError.class)
    public void gridLayout_MaxColumnsMinColumnsNegative() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        final List<Attribute> attrs = new ArrayList<>();
        attrs.add(new Attribute(new ResName(PKG, "attr", "min_columns"), String.valueOf(-4), PKG));
        attrs.add(new Attribute(new ResName(PKG, "attr", "max_columns"), String.valueOf(-4), PKG));
        final ShadowContext shadowCtx = Shadows.shadowOf(RuntimeEnvironment.application);
        RoboAttributeSet attributeSet = shadowCtx.createAttributeSet(attrs, GridLayout.class);
        new GridLayout(RuntimeEnvironment.application.getBaseContext(), attributeSet);
    }

    @Test
    public void calculateNumberOfColumns_ParentWidthLessNumberOfChildAllowed() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        GridLayout gridLayout = Mockito.spy(new GridLayout(RuntimeEnvironment.application.getBaseContext()));
        Mockito.doReturn(3).when(gridLayout).getMaxColumns();
        Mockito.doReturn(6).when(gridLayout).getVisibleChildCount();
        TestUtils.setVariable(gridLayout, "mMaxChildWidth", 400);
        TestUtils.setVariable(gridLayout, "mCols", 3);
        TestUtils.invokePrivateMethod(gridLayout, "calculateNumberOfColumns", new Class[]{int.class, int.class}, 500, 0);
        assertEquals(2, TestUtils.getVariable(gridLayout, "mCols"));
    }
}