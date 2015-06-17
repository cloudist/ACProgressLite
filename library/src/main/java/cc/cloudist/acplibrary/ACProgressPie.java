package cc.cloudist.acplibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ViewConstructor")
public class ACProgressPie extends ACProgressBase {

    private RectF mBackgroundRect, mPieRect;
    private Paint mBackgroundPaint, mRingPaint, mPiePaint;

    private float mBackgroundCornerRadius;
    private float mRingBorderPadding;

    private int mSpinCount = 0;
    private int mCurrentFocusIndex = 0;

    private int mSlides = 20;

    private float mSpeed;

    public ACProgressPie(Builder builder) {
        super(builder.mContext, builder.mSizeRatio);

        mBackgroundCornerRadius = builder.mBackgroundCornerRadius;
        mRingBorderPadding = builder.mRingBorderPadding;
        mSpeed = builder.mSpeed;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(builder.mBackgroundColor);
        mBackgroundPaint.setAlpha((int) (builder.mBackgroundAlpha * 255));

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStrokeWidth(builder.mRingThickness);
        mRingPaint.setColor(builder.mRingColor);
        mRingPaint.setAlpha((int) (builder.mRingAlpha * 255));
        mRingPaint.setStyle(Paint.Style.STROKE);

        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);
        mPiePaint.setColor(builder.mPieColor);
        mPiePaint.setAlpha((int) (builder.mPieAlpha * 255));

        float piePaddingValue = (builder.mRingBorderPadding + builder.mPieRingDistance) * mSize;
        mBackgroundRect = new RectF(0, 0, mSize, mSize);
        mPieRect = new RectF(0 + piePaddingValue / 2, 0 + piePaddingValue / 2, mSize - piePaddingValue / 2, mSize - piePaddingValue / 2);

    }

    public static class Builder {

        private Context mContext;

        private float mSizeRatio = 0.2f;

        private int mBackgroundColor = Color.BLACK;
        private float mBackgroundAlpha = 0.5f;
        private float mBackgroundCornerRadius = 20f;

        private int mRingColor = Color.WHITE;
        private float mRingAlpha = 0.9f;
        private float mRingBorderPadding = 0.2f;
        private int mRingThickness = 3;

        private int mPieColor = Color.WHITE;
        private float mPieAlpha = 0.9f;
        private float mPieRingDistance = 0.08f;

        private float mSpeed = 6.67f;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder bgColor(int color) {
            this.mBackgroundColor = color;
            return this;
        }

        public Builder bgAlpha(float alpha) {
            this.mBackgroundAlpha = alpha;
            return this;
        }

        public Builder bgCornerRadius(float cornerRadius) {
            this.mBackgroundCornerRadius = cornerRadius;
            return this;
        }

        public Builder ringColor(int color) {
            this.mRingColor = color;
            return this;
        }

        public Builder ringAlpha(float alpha) {
            this.mRingAlpha = alpha;
            return this;
        }

        public Builder ringBorderPadding(float padding) {
            this.mRingBorderPadding = padding;
            return this;
        }

        public Builder ringThickness(int thickness) {
            this.mRingThickness = thickness;
            return this;
        }

        public Builder pieColor(int color) {
            this.mPieColor = color;
            return this;
        }

        public Builder pieAlpha(float alpha) {
            this.mPieAlpha = alpha;
            return this;
        }

        public Builder pieRingDistance(float distance) {
            this.mPieRingDistance = distance;
            return this;
        }

        public Builder speed(float speed) {
            this.mSpeed = speed;
            return this;
        }

        public Builder sizeRatio(float ratio) {
            this.mSizeRatio = ratio;
            return this;
        }

        public ACProgressPie build() {
            return new ACProgressPie(this);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBackgroundRect, mBackgroundCornerRadius, mBackgroundCornerRadius, mBackgroundPaint);
        canvas.drawCircle(mSize / 2, mSize / 2, (mSize * (1 - mRingBorderPadding)) / 2, mRingPaint);
        canvas.drawArc(mPieRect, -90, (float) (360.0 / (mSlides - 1) * mCurrentFocusIndex), true, mPiePaint);
    }

    @Override
    public void show() {
        ACProgressPie.super.show();
        long delay = (long) (1000 / mSpeed);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mCurrentFocusIndex = mSpinCount % mSlides;
                mHandler.sendEmptyMessage(0);
                if (mCurrentFocusIndex == 0) {
                    mSpinCount = 1;
                } else {
                    mSpinCount++;
                }
            }
        }, delay, delay);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBackgroundRect = null;
        mPieRect = null;
        mBackgroundPaint = null;
        mRingPaint = null;
        mPiePaint = null;
    }

    public void showAndUpdate(int percentage) {
        mSlides = 100;
        if (mCurrentFocusIndex >= 0 && mCurrentFocusIndex < 100) {
            mCurrentFocusIndex = percentage;
        }
        invalidate();
    }

}
