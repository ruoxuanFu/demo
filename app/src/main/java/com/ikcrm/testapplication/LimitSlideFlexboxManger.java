package com.ikcrm.testapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;

/**
 * Created by furuoxuan on 2018-09-13.
 */
public class LimitSlideFlexboxManger extends FlexboxLayoutManager {

    // 尺寸的数组，[0]是宽，[1]是高
    private int[] measuredDimension = new int[2];

    // 用来比较同行/列那个item罪宽/高
    private int[] dimension;

    public LimitSlideFlexboxManger(Context context) {
        super(context);
    }

    public LimitSlideFlexboxManger(Context context, int flexDirection) {
        super(context, flexDirection);
    }

    public LimitSlideFlexboxManger(Context context, int flexDirection, int flexWrap) {
        super(context, flexDirection, flexWrap);
    }

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        //super.onMeasure(recycler, state, widthSpec, heightSpec);

        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        int width = 0;
        int height = 0;
        // item的数目
        int count = getItemCount();
        // item的列数
        int span = 5;

        // 根据行数或列数来创建数组
        dimension = new int[span];

        for (int i = 0; i < count; i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED), measuredDimension);

            // 如果是竖直的列表，计算item的高，否则计算宽度
            //Log.d("LISTENER", "position " + i + " height = " + measuredDimension[1]);
            dimension[findMinIndex(dimension)] += measuredDimension[1];
        }
        height = findMax(dimension);

        switch (widthMode) {
            // 当控件宽是match_parent时，宽度就是父控件的宽度
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (heightMode) {
            // 当控件高是match_parent时，高度就是父控件的高度
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
        }
        // 设置测量尺寸
        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        if (position < getItemCount()) {
            try {
                View view = recycler.getViewForPosition(0);//fix 动态添加时报IndexOutOfBoundsException
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), lp.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), lp.height);
                // 子view进行测量，然后可以通过getMeasuredWidth()获得测量的宽，高类似
                view.measure(childWidthSpec, childHeightSpec);
                // 将item的宽高放入数组中
                measuredDimension[0] = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                recycler.recycleView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 得到最数组中最小元素的下标
     *
     * @param array
     * @return
     */
    private int findMinIndex(int[] array) {
        int index = 0;
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
