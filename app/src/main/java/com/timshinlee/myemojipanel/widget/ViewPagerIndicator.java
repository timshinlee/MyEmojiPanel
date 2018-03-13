package com.timshinlee.myemojipanel.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import com.timshinlee.myemojipanel.entity.EmojiEntity;
import com.timshinlee.myemojipanel.util.EmojiHelper;

/**
 * Created by Administrator on 2018/3/12.
 */

public class ViewPagerIndicator extends View {
    private static final String TAG = "ViewPagerIndicator";
    private int mCount;
    private int mCurrentPosition;
    private Paint mPaint;
    private static final int DOT_RADIUS_NORMAL_DEFAULT = Utils.dp2px(5);
    private int mDotRadiusNormal = DOT_RADIUS_NORMAL_DEFAULT;
    private static final int DOT_RADIUS_SELECTED_DEFAULT = Utils.dp2px(7);
    private int mDotRadiusSelected = DOT_RADIUS_SELECTED_DEFAULT;
    private static final int DOT_SPACE_DEFAULT = Utils.dp2px(8);
    private int mDotSpace = DOT_SPACE_DEFAULT;
    private static final int DOT_SELECTED_COLOR = Color.BLACK;
    private int mDotColorSelected = DOT_SELECTED_COLOR;
    private static final int DOT_NORMAL_COLOR = Color.GRAY;
    private int mDotColorNormal = DOT_NORMAL_COLOR;

    public ViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int newWidthMeasureSpec = widthMeasureSpec;
        int newHeightMeasureSpec = heightMeasureSpec;
        if (widthMode != MeasureSpec.EXACTLY) {
            final int totalWidth = (mCount + 1) * (mDotSpace + mDotRadiusSelected);
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            final int totalHeight = mDotRadiusSelected * 2;
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, mDotRadiusSelected);
        for (int i = 0; i < mCount; i++) {
            canvas.translate(mDotSpace + mDotRadiusSelected, 0);
            int currentRadius;
            if (i == mCurrentPosition) {
                currentRadius = mDotRadiusSelected;
                mPaint.setColor(mDotColorSelected);
            } else {
                currentRadius = mDotRadiusNormal;
                mPaint.setColor(mDotColorNormal);
            }
            canvas.drawCircle(0, 0, currentRadius, mPaint);
        }
        canvas.restore();
    }

    public void bindViewPager(ViewPager viewPager) {
        mCount = viewPager.getAdapter().getCount();
        if (mCount > 0) {
            refreshIndicator(0);
        }
        requestLayout();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshIndicator(position);
                requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refreshIndicator(int position) {
        final EmojiEntity entity = EmojiHelper.getInstance().getEntity(position);
        final Pair<Integer, Integer> pageRange = entity.getPageRange();
        mCount = pageRange.second - pageRange.first + 1;
        mCurrentPosition = position - pageRange.first;
    }


    public static class Utils {
        public static int dp2px(int dpValue) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
        }
    }
}
