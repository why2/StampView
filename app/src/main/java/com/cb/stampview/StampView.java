package com.cb.stampview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;


/**
 * 邮票效果
 */
public class StampView extends View {
    private Paint bgPaint;
    /**
     * 背景颜色
     */
    private @ColorInt
    int bgColor;
    /**
     * 圆角距离顶部距离
     */
    private float marginTop;
    /**
     * 圆角半径
     */
    private float circleRadius;
    private Path path;
    private RectF rightRectF, leftRectF;

    public StampView(Context context) {
        this(context, null);
    }

    public StampView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StampView);
        marginTop = typedArray.getDimension(R.styleable.StampView_StampViewMarginTop, SizeUtils.dp2px(100));
        circleRadius = typedArray.getDimension(R.styleable.StampView_StampViewCircleRadius, SizeUtils.dp2px(8));
        bgColor = typedArray.getColor(R.styleable.StampView_StampViewBgColor, Color.WHITE);
        typedArray.recycle();
        path = new Path();
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
        rightRectF = new RectF();
        leftRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width, marginTop);
        rightRectF.left = width - circleRadius;
        rightRectF.top = marginTop;
        rightRectF.right = width + circleRadius;
        rightRectF.bottom = marginTop + circleRadius * 2;
        path.arcTo(rightRectF, -90, -180);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, marginTop + circleRadius * 2);
        leftRectF.left = -circleRadius;
        leftRectF.top = marginTop;
        leftRectF.right = circleRadius;
        leftRectF.bottom = marginTop + circleRadius * 2;
        path.arcTo(leftRectF, 90, -180);
        path.lineTo(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawPath(path, bgPaint);
    }
}
