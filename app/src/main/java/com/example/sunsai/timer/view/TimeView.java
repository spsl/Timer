package com.example.sunsai.timer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunsai on 2016/1/27.
 */
public class TimeView extends View {

    private int mHH = 0;//时
    private int mMM = 12;//分
    private int mSS = 34;//秒
    private int mMMSS = 45;//毫秒
    private int mColor = Color.BLUE;

    private int mCenterX = 0;
    private int mCenterY = 0;

    private long mTime;//单位毫秒

    private Paint mPaint;
    Rect mRect;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attributeSet) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRect = new Rect();
        setTime(0);
    }

    public void setTime(long time) {
        mTime = time;
        mMM = (int) mTime / 1000 / 60;
        mSS = ((int) mTime / 1000) % 60;
        mMMSS = ((int) mTime % 1000) / 10;
        invalidate();
    }

    public long getTime() {
        return mTime;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMM(canvas);
        drawSS(canvas);
        drawMMSS(canvas);
    }

    private void drawMM(Canvas canvas) {
        String text = String.valueOf(mMM);
        mPaint.setColor(mColor);
        mPaint.setTextSize(getWidth() * 2 / 9);
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        int width = mRect.width();
        int height = mRect.height();
        canvas.drawText(text, getWidth()/2 - width /2 - getWidth() * 2 / 7, getHeight()/2 + height/2, mPaint);
    }

    private void drawSS(Canvas canvas) {
        String text = String.valueOf(mSS);
        if (mSS < 10) {
            text = "0" + text;
        }
        mPaint.setColor(mColor);
        mPaint.setTextSize(getWidth() * 2 /9);
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        int width = mRect.width();
        int height = mRect.height();
        canvas.drawText(text, getWidth()/2 - width /2, getHeight()/2 + height/2, mPaint);
    }

    private void drawMMSS(Canvas canvas) {
        String text = String.valueOf(mMMSS);
        if (mMMSS < 10) {
            text = "0" + text;
        }
        mPaint.setColor(mColor);
        mPaint.setTextSize(getWidth()/9);
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        int width = mRect.width();
        int height = mRect.height();
        canvas.drawText(text, getWidth()/2 + width /2 + getWidth()/9, getHeight()/2 + height, mPaint);
    }


}
