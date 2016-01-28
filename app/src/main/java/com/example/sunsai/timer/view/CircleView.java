package com.example.sunsai.timer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.sunsai.timer.R;

/**
 * Created by sunsai on 2016/1/24.
 */
public class CircleView extends View {

    private Paint mPaint;
    private int mCircleColor;//圆环经过的颜色
    private int mBackGroundColor;//圆环没有经过的背景颜色
    private int mPointColor;//那个小点的颜色

    private float mCenterX;//圆环的中心坐标X
    private float mCenterY;//圆环的中心坐标Y
    private float mRadius;//圆环的半径
    private float mCircleWidth;//圆环的环的宽度
    private float mPointRadius;//那个小点的半径

    private RectF mRectF;//花圆弧的四边形
    private float mAngle = 0;//圆弧的角度

    private Rect mRect = new Rect();


    public CircleView(Context context) {
        this(context, null);
    }


    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();

        /**
         * 设置去锯齿
         */
        mPaint.setAntiAlias(true);

        /**
         * 初始化所有的属性
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mCenterX = typedArray.getDimension(R.styleable.CircleView_centerX, 0.0f);
        mCenterY = typedArray.getDimension(R.styleable.CircleView_centerY, 0.0f);
        mCircleColor = typedArray.getColor(R.styleable.CircleView_circleColor, Color.RED);
        mBackGroundColor = typedArray.getColor(R.styleable.CircleView_backgroundColor, Color.GREEN);
        mCircleWidth = typedArray.getDimension(R.styleable.CircleView_circleWidth, 0.0f);
        mRadius = typedArray.getDimension(R.styleable.CircleView_radius, 0.0f);
        mPointColor = typedArray.getColor(R.styleable.CircleView_pointColor, Color.BLUE);
        mPointRadius = typedArray.getDimension(R.styleable.CircleView_pointRadius, 30);
        mAngle = typedArray.getFloat(R.styleable.CircleView_angle, 0);

        mRectF = new RectF();
        mRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRing(canvas);
        drawArc(canvas);
        drawPoint(canvas);
    }


    //改变圆弧的角度
    public void changeAngle(float angle) {
        mAngle = angle;
        invalidate();
    }

    // 也可以按照进度来设置
    public void changeProgress(float progress) {
        changeAngle(progress * 360.0f / 100.0f );
    }

    /**
     * 画有颜色的圆弧
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        Paint.Style style = mPaint.getStyle();
        int color = mPaint.getColor();
        float width = mPaint.getStrokeWidth();

        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 270, mAngle, false, mPaint);

        mPaint.setStyle(style);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
    }


    /**
     * 画背景的圆环
     * @param canvas
     */
    private void drawRing(Canvas canvas) {
        int color = mPaint.getColor();
        Paint.Style style = mPaint.getStyle();
        float width = mPaint.getStrokeWidth();

        mPaint.setColor(mBackGroundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

        mPaint.setColor(color);
        mPaint.setStyle(style);
        mPaint.setStrokeWidth(width);
    }


    /**
     * 画那个小点
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        float x = rngleToX(mAngle + 270, mCenterX, mRadius);
        float y = rngleToY(mAngle + 270, mCenterY, mRadius);

        Paint.Style style = mPaint.getStyle();
        int color = mPaint.getColor();

        mPaint.setColor(mPointColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, mPointRadius, mPaint);

        mPaint.setColor(color);
        mPaint.setStyle(style);
    }


    /**
     *
     * @param angle
     * @param centerx
     * @param r
     * @return
     */
    private float rngleToX(float angle, float centerx, float r) {
        return centerx + r * (float) Math.cos(angle * Math.PI / 180);
    }

    /**
     *
     * @param angle
     * @param centerx
     * @param r
     * @return
     */
    private float rngleToY(float angle, float centerx, float r) {
        return centerx + r * (float) Math.sin(angle * Math.PI / 180);
    }

}
