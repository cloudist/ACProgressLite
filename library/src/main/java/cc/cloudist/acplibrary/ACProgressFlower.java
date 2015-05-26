package cc.cloudist.acplibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.components.FlowerDataCalc;
import cc.cloudist.acplibrary.components.PetalCoordinate;

@SuppressLint("ViewConstructor")
public class ACProgressFlower extends ACProgressBase {

    private RectF mBackgroundRect;
    private Paint mBackgroundPaint, mPetalPaint;

    private float mBackgroundCornerRadius;
    private int mPetalCount;

    private List<PetalCoordinate> mPetalCoordinates;
    private int[] mPetalColors;

    private int mFocusPetalIndex = 0;
    private int mSpinCount = 0;
    private int mDirection;
    private float mSpeed;

    private ACProgressFlower(Builder builder) {
        super(builder.mContext, builder.mSizeRatio);

        mBackgroundCornerRadius = builder.mBackgroundCornerRadius;
        mPetalCount = builder.mPetalCount;
        mDirection = builder.mDirection;
        mSpeed = builder.mSpeed;

        mBackgroundRect = new RectF(0, 0, mSize, mSize);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(builder.mBackgroundColor);
        mBackgroundPaint.setAlpha((int) (builder.mBackgroundAlpha * 255));

        mPetalPaint = new Paint();
        mPetalPaint.setAntiAlias(true);
        mPetalPaint.setStrokeWidth(builder.mPetalThickness);
        mPetalPaint.setStrokeCap(Paint.Cap.ROUND);

        FlowerDataCalc calc = new FlowerDataCalc(builder.mPetalCount);
        mPetalCoordinates = calc.getSegmentsCoordinates(mSize, (int) (builder.mBorderPadding * mSize), (int) (builder.mCenterPadding * mSize), builder.mPetalCount);
        mPetalColors = calc.getSegmentsColors(builder.mThemeColor, builder.mFadeColor, builder.mPetalCount, (int) (builder.mPetalAlpha * 255));
    }

    public static class Builder {

        public static final int DIRECT_CLOCKWISE = 0;
        public static final int DIRECT_ANTI_CLOCKWISE = 1;

        private Context mContext;

        private int mPetalCount = 12;
        private float mSizeRatio = 0.25f;

        private int mThemeColor = Color.WHITE;
        private int mFadeColor = Color.DKGRAY;
        private int mBackgroundColor = Color.BLACK;

        private float mPetalAlpha = 0.5f;
        private float mBackgroundAlpha = 0.5f;

        private int mDirection = DIRECT_CLOCKWISE;
        private float mSpeed = 9.0f;

        private float mBorderPadding = 0.55f;
        private float mCenterPadding = 0.27f;

        private int mPetalThickness = 9;

        private float mBackgroundCornerRadius = 20f;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder petalThickness(int thickness) {
            this.mPetalThickness = thickness;
            return this;
        }

        public Builder borderPadding(float padding) {
            this.mBorderPadding = padding;
            return this;
        }

        public Builder centerPadding(float padding) {
            this.mCenterPadding = padding;
            return this;
        }

        public Builder petalCount(int petalCount) {
            this.mPetalCount = petalCount;
            return this;
        }

        public Builder bgCornerRadius(float cornerRadius) {
            this.mBackgroundCornerRadius = cornerRadius;
            return this;
        }

        public Builder themeColor(int themeColor) {
            this.mThemeColor = themeColor;
            return this;
        }

        public Builder fadeColor(int fadeColor) {
            this.mFadeColor = fadeColor;
            return this;
        }

        public Builder bgColor(int bgColor) {
            this.mBackgroundColor = bgColor;
            return this;
        }

        public Builder petalAlpha(float alpha) {
            this.mPetalAlpha = alpha;
            return this;
        }

        public Builder bgAlpha(float alpha) {
            this.mBackgroundAlpha = alpha;
            return this;
        }

        public Builder direction(int direction) {
            this.mDirection = direction;
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

        public ACProgressFlower build() {
            return new ACProgressFlower(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBackgroundRect, mBackgroundCornerRadius, mBackgroundCornerRadius, mBackgroundPaint);
        PetalCoordinate coordinate;
        for (int i = 0; i < mPetalCoordinates.size(); i++) {
            coordinate = mPetalCoordinates.get(i);
            int index = (mFocusPetalIndex + i) % mPetalCoordinates.size();
            mPetalPaint.setColor(mPetalColors[index]);
            canvas.drawLine(coordinate.getStartX(), coordinate.getStartY(), coordinate.getEndX(), coordinate.getEndY(), mPetalPaint);
        }
    }

    @Override
    public void show() {
        super.show();
        long delay = (long) (1000 / mSpeed);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int result = (int) (mSpinCount % mPetalCount);
                if (mDirection == Builder.DIRECT_CLOCKWISE) {
                    mFocusPetalIndex = result;
                } else {
                    mFocusPetalIndex = mPetalCount - 1 - result;
                }
                mHandler.sendEmptyMessage(0);
                if (result == 0) {
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
        mBackgroundPaint = null;
        mPetalPaint = null;
        mBackgroundRect = null;
        if (mPetalCoordinates != null) {
            mPetalCoordinates.clear();
        }
        mPetalColors = null;
        mPetalCoordinates = null;
        mPetalColors = null;
    }

}
